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

<title>Student Courses</title>

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
				<a class="navbar-brand" href="Student.jsp" style="color: #fff;">Testing
					Center</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> ${user.firstName} ${user.lastName}<b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"> Settings</a></li>
						<li class="divider"></li>
						<li><a>
								<form action="Login" method="GET">
									<input type="submit" value="Log Out"
										style="background-color: Transparent; border: none;">
								</form>
						</a></li>
					</ul></li>
			</ul>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li><a href="Student.jsp" style="color: #fff;">Upcoming
							Exams</a></li>
					<li><a href="StudentAppointments.jsp" style="color: #fff;">View
							Appointments</a></li>
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
							Home <small>Upcoming Exams</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active">Home</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<table class="table">
						<tr>
							<th>Class</th>
							<th>Exam Name</th>
							<th>Instructor</th>
							<th>ExamID</th>
							<th>Exam Start Date</th>
							<th>Exam End Date</th>
							<th>Status</th>
						</tr>
						<!--
							<c:forEach items="${requests}" var="requests">    
    						<tr class="success">
								<td><font color="blue">${requests.course.classID}</font></td>
								<td>${requests.examName}</td>
								<td>${requests.timeStart}</td>
								<td>${requests.timeEnd}</td>
								<td>${requests.testDuration}</td>
								<td>${requests.examIndex}</td>
								<td><font color="green">${requests.status}</font></th>
							</tr>
							</c:forEach>-->
					</table>
				</div>

				<div class="row">
					Messing Around
					<table class="table">
						<tr>
							<th>Seat No.</th>
							<c:forEach begin="0" end="23" var="val">
								<td>${val}:00</td>
								<td>${val}:30</td>
							</c:forEach>
						</tr>
						<c:forEach items="${timeslot}" var="entry">
							<c:set var="appArray" scope="session" value="${entry.value}" />

							<tr>
								<th><c:out value="${entry.key}" /></th>

								<c:forEach items="${appArray}" var="app">
									<td><c:choose>
											<c:when test="${app == -1}">
												Unavailable
											</c:when>
											<c:when test="${app == 0}">
												Open
											</c:when>
											<c:when test="${app > 0}">
												Appointment Request ID:<c:out value="${app}" />
											</c:when>
										</c:choose></td>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>
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
