<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Index</title>
</head>
<body>
	<h1>
		<a href="sys/getUserByName?name=paul">nice</a>
	</h1>
	form上传附件:
	</br>
	<form action="sys/fileUpload" enctype="multipart/form-data" method="post">
		<input type="file" name="file" />
		<input type="submit" value="上传" />
	</form>
	ajax上传附件:
	</br>
	<form id="uploadForm">
		<input type="file" name="file" />
		<input type="button" value="上传" onclick="upload()" />
	</form>
	<script src="${pageContext.request.contextPath}/static/js/jquery-2.2.4.min.js"></script>
	<script type="text/javascript">
		function upload() {
			var formData = new FormData($("#uploadForm")[0]);
			$.ajax({
				url : 'sys/fileUpload',
				type : 'POST',
				data : formData,
				dataType : 'json',
				async : true,
				cache : false,
				contentType : false,
				processData : false,
				success : function(data) {
					if (data.code == 1) {
						alert("上传成功,附件路径为:" + data.fileURL);
					}
					if (data.code == 0) {
						alert("上传失败,错误信息为:" + data.message);
					}
				},
				error : function(data) {
					alert("上传失败.");
				}
			});
		}
	</script>
</body>
</html>