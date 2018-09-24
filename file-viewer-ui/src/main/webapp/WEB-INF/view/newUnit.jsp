<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="menu.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>New Unit</title>
<spring:url value="/resources/css/mainstyle.css" var="css" />
<link rel="stylesheet" href="${css}">
</head>
<body>
	<div class="container">
		<div>
			<form method="POST" action="newUnitSave"
				enctype="multipart/form-data">
				<table class="table">
					<caption>Enter the details and press Save</caption>
					<tr>
						<td>Unit Number</td>
						<td><input type="text" name="unitNumber" /></td>
					</tr>
					<tr>
						<td>Unit Name</td>
						<td><input type="text" name="unitName" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Save" class="button" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>