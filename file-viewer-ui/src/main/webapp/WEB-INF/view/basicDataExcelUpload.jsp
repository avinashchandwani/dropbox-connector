<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="menu.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Upload Receivables Excel Sheet</title>
<spring:url value="/resources/css/mainstyle.css" var="css" />
<link rel="stylesheet" href="${css}">
</head>
<body>
	<div class="container">
		<div>
			<form method="POST" action="basicDataExcelUploadAction"
				enctype="multipart/form-data">
				<table class="table">
					<caption>Select the excel sheet you want to upload and
						press upload</caption>
					<tr>
						<td>Unit Number</td>
						<td><select name="unitNumber" class="dropdown">
								<c:forEach var="uno" items="${unitlist}">
									<option value="${uno.id}">${uno.number}-${uno.levelName}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td>Select Excel File</td>
						<td><input type="file" name="file" accept=".xls"
							required="required"></td>
					</tr>
					<tr>
						<td><input type="submit" value="Upload" class="button" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>