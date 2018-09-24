<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="upload.jsp"%>
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
			<table class="table">
				<caption>${currentPath}</caption>
				<tr>
					<th>Name</th>
					<th>Type</th>
					<th>Action</th>
				</tr>

				<c:forEach var="dbr" items="${dropBoxRecord}">
					<tr>
						<td><a
							href="${pageContext.request.contextPath}/getContentsOfFolder/?pathValue=${dbr.parentPath}&currentFolder=${dbr.fileName}&fileType=${dbr.type}">${dbr.fileName}</a>
						</td>
						<td>${dbr.type}</td>
						<c:if test="${dbr.type == 'file'}">
							<td><a
								href="${pageContext.request.contextPath}/downloadFile/?pathValue=${dbr.parentPath}${dbr.fileName}&fileType=${dbr.type}">Download</a></td>
						</c:if>
					</tr>
				</c:forEach>
				<tr>
					<td><input type="hidden" id="path" name="path"
						value="${currentPath}" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>