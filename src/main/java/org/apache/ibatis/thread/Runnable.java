package org.apache.ibatis.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.NestedIOException;

/**
 * 刷新使用进程
 * 
 * @author 黄建峰
 * @date 2017年10月23日 下午2:11:31
 */
public class Runnable implements java.lang.Runnable {

	public static Logger log = Logger.getLogger(Runnable.class);

	private String location;
	private Configuration configuration;

	/**
	 * 上一次刷新时间
	 */
	private Long beforeTime = 0L;
	/**
	 * 是否执行刷新
	 */
	private static boolean refresh = false;
	/**
	 * xml文件夹匹配字符串，需要根据需要修改
	 */
	private static String mappingPath = "com/paulandcode/dao";
	/**
	 * 延迟刷新秒数
	 */
	private static int delaySeconds = 10;
	/**
	 * 休眠时间
	 */
	private static int sleepSeconds = 1;
	/**
	 * 是否启动自动刷新功能
	 */
	private static boolean enabled = true;

	public static boolean isRefresh() {
		return refresh;
	}

	public Runnable(String location, Configuration configuration) {
		this.location = location.replaceAll("\\\\", "/");
		this.configuration = configuration;
	}

	@Override
	public void run() {
		location = location.substring("file [".length(), location.lastIndexOf(mappingPath) + mappingPath.length());
		beforeTime = System.currentTimeMillis();
		log.debug("[location] " + location);
		log.debug("[configuration] " + configuration);
		if (enabled) {
			start(this);
		}
	}

	public void start(final Runnable runnable) {
		ThreadFactory myThreadFactory = Executors.defaultThreadFactory();
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
				new LinkedBlockingDeque<java.lang.Runnable>(128), myThreadFactory);
		java.lang.Runnable myRunable = new java.lang.Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(delaySeconds * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refresh = true;
				System.out.println("====================================================================");
				System.out.println("================== Enabled refresh mybatis mapper ==================");
				System.out.println("====================================================================");
				while (true) {
					try {
						runnable.refresh(location, beforeTime);
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						// 每1秒刷新一次mapper文件
						Thread.sleep(sleepSeconds * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		threadPool.execute(myRunable);
	}

	/**
	 * 执行刷新
	 * 
	 * @param filePath
	 *            刷新目录
	 * @param beforeTime
	 *            上次刷新时间
	 * @throws NestedIOException
	 *             解析异常
	 * @throws FileNotFoundException
	 *             文件未找到
	 */
	public void refresh(String filePath, Long beforeTime) throws Exception {
		// 本次刷新时间
		Long refrehTime = System.currentTimeMillis();
		List<File> refreshs = this.getRefreshFile(new File(filePath), beforeTime);
		if (refreshs.size() > 0) {
			log.debug("refresh files:" + refreshs.size());
		}
		for (File refresh : refreshs) {
			System.out.println("Refresh file: " + mappingPath.replaceAll("/", "\\\\")
					+ StringUtils.substringAfterLast(refresh.getAbsolutePath(), mappingPath.replaceAll("/", "\\\\")));
			log.debug("refresh file:" + refresh.getAbsolutePath());
			log.debug("refresh filename:" + refresh.getName());
			SqlSessionFactoryBean.refresh(new FileInputStream(refresh), refresh.getAbsolutePath(), configuration);
		}
		// 如果刷新了文件，则修改刷新时间，否则不修改
		if (refreshs.size() > 0) {
			this.beforeTime = refrehTime;
		}
	}

	/**
	 * 获取需要刷新的文件列表
	 * 
	 * @param dir
	 *            目录
	 * @param beforeTime
	 *            上次刷新时间
	 * @return 刷新文件列表
	 */
	public List<File> getRefreshFile(File dir, Long beforeTime) {
		List<File> refreshs = new ArrayList<File>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// 利用递归,将目录下及子目录下所有(需要刷新的)文件加入刷新文件列表
				refreshs.addAll(this.getRefreshFile(file, beforeTime));
			} else if (file.isFile()) {
				if (this.check(file, beforeTime)) {
					refreshs.add(file);
				}
			} else {
				System.out.println("error file." + file.getName());
			}
		}
		return refreshs;
	}

	/**
	 * 判断文件是否需要刷新
	 * 
	 * @param file
	 *            文件
	 * @param beforeTime
	 *            上次刷新时间
	 * @return 需要刷新返回true，否则返回false
	 */
	public boolean check(File file, Long beforeTime) {
		return file.lastModified() > beforeTime;
	}

}
