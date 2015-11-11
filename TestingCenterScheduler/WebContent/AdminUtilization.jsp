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

<title>Admin Home</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="css/plugins/morris.css" rel="stylesheet">

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
				<a class="navbar-brand" href="Admin.html" style="color: #fff;">Testing
					Center</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><i class="fa fa-user"></i>
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
					<li><a href="#" style="color: #fff;">View All Requests</a></li>
					<li><a href="#" style="color: #fff;">View All Appointments</a></li>
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
		
				<form action="AdministratorHome" method="POST" >
				
					<div class="row">
						<div class="col-md-6">
							<p>Choose a term</p>
							<select class="form-control" name="termVal">				
								<c:forEach items="${termlist}" var="term"> 
	    							<option value="${term.termID}">${term.termID} ${term.termSeason}_${term.termYear}</option>
								</c:forEach>
							</select> 
						</div>
					</div>
				
					<div class="row">
						<div class = "col-md-6">
							<p>Start Date</p>
	
							<select name="startmon" class="form-control" style="width:100px">
									<c:forEach begin="1" end="12" var="val">
										<option> ${val}</option>
									</c:forEach>
								</select>
								<p>/</p>
								<select name="startday" class="form-control" style="width:100px">
									<c:forEach begin="1" end="31" var="val">
										<option> ${val}</option>
									</c:forEach>
								</select>
							</div>
						</div>
				
						<div class="row">
							<div class = "col-md-6">
								<p>End Date</p>
		
								<select name="endmon" class="form-control" style="width:100px">
										<c:forEach begin="1" end="12" var="val">
											<option> ${val}</option>
										</c:forEach>
									</select>
									<p>/</p>
									<select name="endday" class="form-control" style="width:100px">
										<c:forEach begin="1" end="31" var="val">
											<option> ${val}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						
					<div>
						<input type="submit" class="btn btn-primary" name="utilization" value="Submit"/>
					</div>
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
