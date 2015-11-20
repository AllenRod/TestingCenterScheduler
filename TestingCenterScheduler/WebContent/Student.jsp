<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	
	<c:if test="${empty user}">
		<jsp:forward page="/index.jsp"></jsp:forward>
	</c:if>

	<c:if test="${(not empty user) and (user.role ne 'student')}">
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
				<a class="navbar-brand" href="Student.jsp" style="color: #fff;">Testing
					Center</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">
						${user.firstName} ${user.lastName}<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"> Settings</a>
						</li>
						<li class="divider"></li>
						<li>
								<form action="Login" method="GET">
								<a>
									<input type="submit" value="Log Out" name="logout" style="background-color: Transparent; border: none;">
								</a>
								</form>
						</li>
					</ul>
				</li>
			</ul>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li><a href="Student.jsp" style="color: #fff;">View Appointments</a></li>
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
							Appointments <small>Home</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active">Home</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<c:set var="action" value="getRequests" scope="session" />
					<form action="StudentHome" method="GET">
						<button type="submit" class="btn btn-primary">
							View Open Requests
						</button>
					</form>
					<table class ="table">
							<tr>
								<th>Exam Name</th>
								<th>Instructor</th>
								<th>Test Time</th>
								<th>Seat Number</th>
								<th>Status</th>
								<th>Cancel</th>
							</tr>
							<c:forEach items="${appointments}" var="appointments">    
    						<tr class="success">
								<td>${appointments.request.examName}</td>
								<td>${appointments.request.instructorNetID}</td>
								<td>${appointments.timeStart}</td>
								<td>${appointments.seatNum}</td>
								<c:choose>
									<c:when test="${appointments.status eq 'taken'}">
										<td><font color="green">${appointments.status}</font>
									</c:when>
									<c:when test="${appointments.status eq 'missed'}">
										<td><font color="red">${appointments.status}</font>
									</c:when>
									<c:when test="${appointments.status eq 'pending'}">
										<td><font color="grey">${appointments.status}</font>
									</c:when>
									<c:otherwise>
										<td>${appointments.status}</td>
									</c:otherwise>
								</c:choose>
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
