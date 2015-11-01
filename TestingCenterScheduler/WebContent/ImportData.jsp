<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Import .csv files</title>
	
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
					<li><a href="#" style="color: #fff;">Testing Center Usage Report</a></li>
					<li><a href="#" style="color: #fff;">Testing Center Information</a></li>
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
							Import Data <small>Importing CSV files</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active"> <a href= "Admin.jsp">Home</a> > Import Data</li>
						</ol>
					</div>
				</div>
				
				<c:choose>
				    <c:when test="${returnVal == 'All data imports succeed'}">
				        <div class="row">
							<div class="col-lg-12">
								<div class="alert alert-success">
									${returnVal}
								</div>
							</div>
						</div>
						document.getElementById('submit').disabled = false;
				    </c:when>
				    <c:when test="${not empty returnVal}">
				        <div class="row">
							<div class="col-lg-12">
								<div class="alert alert-danger">
									${returnVal}
								</div>
							</div>
						</div>
						document.getElementById('submit').disabled = false;
				    </c:when>
				    <c:otherwise>
				    </c:otherwise>
				</c:choose>
				<c:remove var="returnVal" scope="session" />
				
				<form action="LoadCSV" method="POST">
					<p>
					CSV Type: 
					<select name="table" >
						<option value = "roster" selected> Roster</option>
						<option value = "user" > User</option>
						<option value = "class" > Class</option>
					</select>
					</p>
					<p>
					Choose your .csv file <br>
					<input type="file" name="file" size="50" required>
					</p>
					<div>
						<input id = "submit" type="submit" value="Send" onClick="this.disabled=true; this.value='Uploading...';">
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>