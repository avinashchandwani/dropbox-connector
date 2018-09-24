<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File</title>
<spring:url value="/resources/css/mainstyle.css" var="css" />
<link rel="stylesheet" href="${css}">
</head>
<body>
	<div>
		<form action="${pageContext.request.contextPath}/uploadFileAction" method="post"
			enctype="multipart/form-data">
			<table class="table" align="right">
				<caption>Select the file you want to upload</caption>
				<tr><td>${successmessage}</td></tr>
				<tr><td>${failuremessage}</td></tr>
				<tr>
					<td>Select File To Upload</td>
					<td><input type="file" name="file"></td>
				</tr>
				<tr>
					<td><input type="hidden" id="path" name="path"
						value="${currentPath}" /></td>
				</tr>
				<tr>

					<td><input type="submit" value="Upload" class="button" /></td>
				</tr>
			</table>

		</form>
	</div>
</body>
</html>