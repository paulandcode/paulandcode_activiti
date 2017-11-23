package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 黄建峰
 * @date 2017年10月13日 下午3:30:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 若配置文件在WEB_INF下,则这么写:"file:src/main/webapp/WEB-INF/applicationContext.xml"
//需要把spring-mvc.xml中的注释junit加载配置文件报错的两句话注释掉
@ContextConfiguration({"classpath:config/spring-*.xml"})
public class ServiceTest {

	@Test
	public void testStartProcess() {
		
	}

}