
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title>File Viewer</title>
<spring:url value="/resources/css/submenustyle.css" var="css" />
<link rel="stylesheet" href="${css}">

</head>
<body>
	<h2 class="menu-bar">File Viewer</h2>
	<div id="navigation">
		<ul class="top-level">
			<li><a
				href="${pageContext.request.contextPath}/accounts/accountshome">Home</a></li>
			<li><a href="#">Events</a><span>&gt;&gt;</span>
				<ul class="sub-level">
					<li><a href="#">Add New Event</a></li>
					<li><a href="#">Remove Event</a></li>
					<li><a href="#">Search Event</a></li>
				</ul></li>
			<li><a href="#">Hierarchy Levels</a><span>&gt;&gt;</span>
				<ul class="sub-level">
					<li><a href="#">New Hierachies</a><span>&gt;&gt;</span>
						<ul class="sub-level">
							<li><a href="#">Zone</a></li>
							<li><a href="#">Kshetra</a></li>
							<li><a href="#">Branch</a></li>
							<li><a href="${pageContext.request.contextPath}/newUnit">Unit</a></li>
						</ul></li>
					<li><a href="#">Geography Hierarchies</a><span>&gt;&gt;</span>
						<ul class="sub-level">
							<li><a href="#">New geography Hierarchy</a></li>
							<li><a href="#">Map Geography Hierarchy</a></li>
						</ul></li>
				</ul></li>
				<li><a href="#">Sewadal Member Entries</a><span>&gt;&gt;</span>
				<ul class="sub-level">
					<li><a href="#">New Sewadal Entry</a><span>&gt;&gt;</span>
						<ul class="sub-level">
							<li><a href="#">Single</a></li>
							<li><a href="${pageContext.request.contextPath}/basicDataExcelUpload">Bulk (Excel)</a></li>
						</ul></li>
					<li><a href="#">Sewadal Member Update</a><span>&gt;&gt;</span>
						<ul class="sub-level">
							<li><a href="#">Upload Bulk History</a></li>
							<li><a href="#">Convert To Permanent</a></li>
							<li><a href="#">Promotion</a></li>
							<li><a href="#">Transfer</a></li>
							<li><a href="#">Deletion</a></li>
							<li><a href="#">Retirement</a></li>
						</ul></li>
				</ul></li>
				<li><a href="#">Sewadal Member Attendance</a><span>&gt;&gt;</span>
				<ul class="sub-level">
					<li><a href="#">Attendance Entry</a></li>
					<li><a href="#">Attendance Report</a></li>
				</ul></li>
		<li><a href="#">Administration Tasks</a><span>&gt;&gt;</span>
			<ul class="sub-level">
				<li><a href="#">Add User</a></li>
				<li><a href="${pageContext.request.contextPath}/accounts/metadataForm">Add Meta-Data</a></li>
				<li><a href="${pageContext.request.contextPath}/accounts/config">System Configuration</a></li>
				<li><a href="${pageContext.request.contextPath}/accounts/TakeBackUp">Take Backup of the System</a></li>
				<li><a href="#">System Restore</a></li>
			</ul></li>
		<li><a href="${pageContext.request.contextPath}/logout">LogOut</a></li>
		</ul>
	</div>
</body>
</html>
