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
					data-toggle="dropdown">
						${user.firstName} ${user.lastName}<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"> Settings</a>
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
					<li><a href="Student.jsp" style="color: #fff;">Upcoming Exams</a></li>
					<li><a href="StudentAppointments.jsp" style="color: #fff;">View Appointments</a></li>
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
							Appointments <small>My Appointments</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active"> <a href= "Student.jsp">Home</a> > Appointments</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<a href="#" class="btn btn-default" style="margin-bottom:10px;" onclick = "newRequest()">
						New Appointment
					</a>
					<table class ="table">
							<tr>
								<th>Class</th>
								<th>Exam Name</th>
								<th>Instructor</th>
								<th>ExamID</th>
								<th>Test Time</th>
								<th>Seat Number</th>
								<th>Status</th>
							</tr><!--
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