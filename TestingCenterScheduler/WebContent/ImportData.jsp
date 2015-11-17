<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Import .csv Files</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/core.css" rel="stylesheet">

</head>

<body>

	<c:if test="${empty user}">
		<jsp:forward page="/index.jsp"></jsp:forward>
	</c:if>

	<c:if test="${(not empty user) and (user.role ne 'admin')}">
		<jsp:forward page="/index.jsp"></jsp:forward>
	</c:if>
	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top navbar-custom"
			role="navigation">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="Admin.jsp" style="color: #fff;">Testing
					Center</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> ${user.firstName} ${user.lastName}<b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Settings</a></li>
						<li class="divider"></li>
						<li>
								<form action="Login" method="GET">
								<a>
									<input type="submit" value="Log Out" name="logout"
										style="background-color: Transparent; border: none;">
								</a>
								</form>
						</li>
					</ul></li>
			</ul>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li><a href="Admin.jsp" style="color: #fff;">Home</a></li>
					<li><a href="AdminUtilization.jsp" style="color: #fff;">Testing
							Center Usage Report</a></li>
					<li><a href="CenterHours.jsp" style="color: #fff;">Testing
							Center Information</a></li>
					<li><a href="ImportData.jsp" style="color: #fff;">Import
							Data</a></li>
					<li><a href="AdminRequests.jsp" style="color: #fff;">View
							All Requests</a></li>
					<li><a href="AdminAppointments.jsp" style="color: #fff;">View
							All Appointments</a></li>
					<li><a href="AdminReport.jsp" style="color: #fff;">Generate
							Reports</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</nav>
		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							Import Data <small>Importing CSV files</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active"><a href="Admin.jsp">Home</a> > Import
								Data</li>
						</ol>
					</div>
				</div>

				<c:choose>
					<c:when test="${returnVal == 'All data imports succeed'}">
						<div class="row">
							<div class="col-lg-12">
								<div class="alert alert-success">${returnVal}</div>
							</div>
						</div>
					</c:when>
					<c:when test="${not empty returnVal}">
						<div class="row">
							<div class="col-lg-12">
								<div class="alert alert-danger">${returnVal}</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<c:remove var="returnVal" scope="session" />

				<form class="form-horizontal" action="LoadCSV" method="POST">

					<div class="form-group">
						<label for="termVal">Choose a term: </label> <select
							class="form-control input-sm" name="termVal">
							<c:forEach items="${termlist}" var="term">
								<option value="${term.termID}">${term.termID}
									${term.termSeason}_${term.termYear}</option>
							</c:forEach>
						</select>
					</div>

					<div class="form-group">
						<label for="table">CSV Type: </label> <select name="table"
							class="form-control input-sm">
							<option value="roster" selected>Roster</option>
							<option value="user">User</option>
							<option value="class">Class</option>
						</select>
					</div>

					<div class="form-group">
						<label for="file">Choose your .csv file </label> <input
							type="file" name="file" size="50" onClick="enableButton()"
							required>
					</div>

					<input class="btn btn-primary" id="submit" type="submit"
						value="Send"
						onClick="this.disabled=true; this.value='Uploading...';">
				</form>
			</div>
		</div>
	</div>
	<script>
		function enableButton() {
			document.getElementById("submit").disabled = false;
			document.getElementById("submit").value = "Send";
		}
	</script>

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

</body>
</html>