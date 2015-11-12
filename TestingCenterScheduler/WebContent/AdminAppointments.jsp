<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Appointments Manager</title>

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
							Appointments <small>All Appointments</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active"> <a href= "Admin.jsp">Home</a> > Appointments</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<form class="form-inline">
						<a href="#" class="btn btn-default" style="margin-bottom:10px;" onclick = "newAppointment()">
							New Appointment
						</a>
						<div style="margin-bottom:10px; float:right;">
							<select class="form-control">
								<option>Student NetID</option>
								<option>Class</option>
								<option>Exam ID</option>
								<option>Status</option>
							</select>
							<div class="input-group">
					            <input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
					            <div class="input-group-btn">
					                <button class="btn btn-default" type="submit">Search</button>
					            </div>
					        </div>
				        </div>
			        </form>
					<table class ="table">
							<tr>
								<th>Class</th>
								<th>Exam Name</th>
								<th>Instructor</th>
								<th>Student Name</th>
								<th>Student NetID</th>
								<th>ExamID</th>
								<th>Test Time</th>
								<th>Seat Number</th>
								<th>Status</th>
							</tr>
							<c:forEach items="{appointments}" var="apps"> 
    						<tr class="success">
								<td><font color="blue">${apps.request.course.classID}</font></td>
								<td>${apps.request.examName}</td>
								<td>${apps.request.instructorNetID}</td>
								<td>${apps.user.firstName + apps.user.lastName}</td>
								<td>${apps.user.netID}</td>
								<td>${apps.request.examIndex}</td>
								<td>${apps.timeStart}</td>
								<td>${apps.seatNum}</td>
								<td>${apps.status}</td>
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
