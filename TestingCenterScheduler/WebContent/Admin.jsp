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

<title>Admin Home</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/core.css" rel="stylesheet">

</head>

<body>

	<c:if test="${empty user}">
		<jsp:forward page="/index.jsp"></jsp:forward>
	</c:if>

	<c:if test="${not empty user}">
		<c:set var="cor" value="false" />
		<c:forEach items="${user.role}" var="role">
			<c:if test="${role eq 'admin'}">
				<c:set var="cor" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${cor eq 'false'}">
			<jsp:forward page="/index.jsp"></jsp:forward>
		</c:if>
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
				<a class="navbar-brand" href="Admin.html" style="color: #fff;">Testing
					Center</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><i class="fa fa-user"></i>
						${user.firstName} ${user.lastName}<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Settings</a></li>
						<c:forEach items="${user.role}" var="role">
							<c:if test="${role eq 'instr'}">
								<li><a href="InstructorHome">Instructor Page</a></li>
							</c:if>
							<c:if test="${role eq 'student'}">
								<li><a href="StudentHome">Student Page</a></li>
							</c:if>
						</c:forEach>
						<li class="divider"></li>
						<li>
							<form action="Login" method="GET">
								<a><input type="submit" value="Log Out" name="logout"
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
							Home <small>Overview</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active">
							<li>Home</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->
				<div class="row">
					<div class="col-lg-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Recent Requests</h3>
							</div>
							<div class="panel-body">
								<div class="list-group">
									<a href="#" class="list-group-item"> <span class="badge">2
											minutes ago</span> <b>ReqID:534141</b> CSE114.01 <i>Midterm 2</i> <font
										color="grey">Pending</font>
									</a> <a href="#" class="list-group-item"> <span class="badge">4
											minutes ago</span> <b>ReqID:534140</b> BUS101.01 <i>Quiz 2</i> <font
										color="grey">Pending</font>
									</a> <a href="#" class="list-group-item"> <span class="badge">23
											minutes ago</span> <b>ReqID:534139</b> AMS210.02 <i>Midterm 2</i> <font
										color="grey">Pending</font>
									</a> <a href="#" class="list-group-item"> <span class="badge">46
											minutes ago</span> <b>ReqID:534138</b> MCAT <i>MCAT</i> <font
										color="grey">Pending</font>
									</a> <a href="#" class="list-group-item"> <span class="badge">1
											hour ago</span> <b>ReqID:534137</b> MUS119.12 <i>Quiz 6</i> <font
										color="green">Approved</font>
									</a> <a href="#" class="list-group-item"> <span class="badge">2
											hours ago</span> <b>ReqID:534136</b> ECO112.01 <i>Midterm 1</i> <font
										color="red">Denied</font>
									</a> <a href="#" class="list-group-item"> <span class="badge">1
											day ago</span> <b>ReqID:534135</b> EGL100.01 <i>Reading quiz</i> <font
										color="green">Approved</font>
									</a> <a href="#" class="list-group-item"> <span class="badge">2
											days ago</span> <b>ReqID:534134</b> BME201.01 <i>Practice Test 1</i>
										<font color="green">Approved</font>
									</a>
								</div>
								<div class="text-right">
									<a href="#">View All Requests <i
										class="fa fa-arrow-circle-right"></i></a>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Recent Appointments</h3>
							</div>
							<div class="panel-body">
								<div class="list-group">
									<a href="#" class="list-group-item"> <b>AppointID:885556545</b>
										ReqID:534138 <i>SFeng</i>
									</a> <a href="#" class="list-group-item"> <b>AppointID:885556544</b>
										ReqID:534139 <i>MWong</i>
									</a> <a href="#" class="list-group-item"> <b>AppointID:885556543</b>
										ReqID:534140 <i>AAnderson</i>
									</a> <a href="#" class="list-group-item"> <b>AppointID:885556542</b>
										ReqID:534130 <i>BClark</i>
									</a> <a href="#" class="list-group-item"> <b>AppointID:885556541</b>
										ReqID:534132 <i>HLee</i>
									</a> <a href="#" class="list-group-item"> <b>AppointID:885556540</b>
										ReqID:534135 <i>HAdam</i>
									</a> <a href="#" class="list-group-item"> <b>AppointID:885556539</b>
										ReqID:534122 <i>EMadison</i><font color="red">
											Superfluous</font>
									</a> <a href="#" class="list-group-item"> <b>AppointID:885556538</b>
										ReqID:534138 <i>PDoe</i>
									</a>
								</div>
								<div class="text-right">
									<a href="#">View All Appointments <i
										class="fa fa-arrow-circle-right"></i></a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-4">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Today's Statistics</h3>
							</div>
							<div class="panel-body">
								<div class="list-group">
									<!-- TODO: IMPLEMENT WAY TO HANDLE STATISTICS-->
								</div>
								<div class="text-right">
									<a href="#"> View Testing Center Usage Report <i
										class="fa fa-arrow-circle-right"></i></a>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-8">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Some other briefing
									table/chart/diagram</h3>
							</div>
							<div class="panel-body">
								<div class="list-group"></div>
								<div class="text-right">
									<a href="#"> <i class="fa fa-fw fa-check"></i> Click here
										for toast <i class="fa fa-arrow-circle-right"></i></a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

</body>

</html>
