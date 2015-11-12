<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Admin Utilization</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/core.css" rel="stylesheet">

</head>

<body>

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
					data-toggle="dropdown"></i>
						${user.firstName} ${user.lastName}<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Settings</a>
						</li>
						<li class="divider"></li>
						<li>
							<a>
								<form action="Login" method="GET">
									<input type="submit" value="Log Out" style="background-color: Transparent; border: none;">
								</form>	
							</a>
						</li>
					</ul>
				</li>
			</ul>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li><a href="Admin.jsp" style="color: #fff;">Home</a></li>
					<li><a href="AdminUtilization.jsp" style="color: #fff;">Testing Center Usage Report</a></li>
					<li><a href="CenterHours.jsp" style="color: #fff;">Testing Center Information</a></li>
					<li><a href="ImportData.jsp" style="color: #fff;">Import Data</a></li>
					<li><a href="AdminRequests.jsp" style="color: #fff;">View All Requests</a></li>
					<li><a href="AdminAppointments.jsp" style="color: #fff;">View All Appointments</a></li>
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
							Utilization <small>View for a data range</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active"> <a href= "Admin.jsp">Home</a> > Test Center Usage Report</li>
						</ol>
					</div>
				</div>
			<div class="div-spacing">
				<form class="form-inline" action="AdministratorHome" method="POST" >
					
					<div class="form-group">
						<label for="termVal">Choose a term: </label> <select
							class="form-control" id="termVal" name="termVal">
							<c:forEach items="${termlist}" var="term">
								<option value="${term.termID}">${term.termID}
									${term.termSeason}_${term.termYear}</option>
							</c:forEach>
						</select>
					</div>
					<br>
					
					<div class="form-group">
							<label for="startmon">Start Date: </label> <input type="number"
								class="form-control input-sm" id="startmon" name="startmon" min="1"
								max="12" placeholder="Month" style="width: 100px;" required>

							<input type="number" class="form-control input-sm" id="startday"
								name="startday" min="1" max="31" placeholder="Day"
								style="width: 100px;" required>
								
					</div>
					<br>
					<div class="form-group">
							<label for="endmon">End Date: </label> <input type="number"
								class="form-control input-sm" id="endmon" name="endmon" min="1"
								max="12" placeholder="Month" style="width: 100px;" required>

							<input type="number" class="form-control input-sm" id="endday"
								name="endday" min="1" max="31" placeholder="Day"
								style="width: 100px;" required>
								
					</div>
					<br>
					<input type="submit" class="btn btn-primary" name="utilization" value="Submit"/>
				</form>
				<c:choose>
					<c:when test ="${returnVal == 'Start date is after end date!'}">
						<div class="row">
							<div class="col-lg-12">
								<div class="alert alert-danger">
									${returnVal}
								</div>
							</div>
						</div>
					</c:when>
					<c:when test="${not empty returnVal}">
						<div class="row">
							<div class="col-lg-12">
								<c:forEach items="${returnVal}" var="uti"> 
									${uti}
								</c:forEach>
							</div>
						</div>
					</c:when>
					 <c:otherwise>
				     </c:otherwise>
				</c:choose>
				<c:remove var="returnVal" scope="session" />
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

</body>

</html>
