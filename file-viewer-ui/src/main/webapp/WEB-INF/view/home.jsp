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
	<br>
	<div>
		<div>
		<form method="POST" action="login">
			<table class="table">
			<caption>Enter the details</caption>
				<tr><td>Access Key(Drop Box)</td><td><input type="text" name="dropBoxAccessKey" /></td></tr>
				<tr><td>Client Id</td><td><input type="text" name="clientId" /></td></tr>
				<tr><td><input type="submit" value="Login" class="button" /></td></tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>
