<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<c:if test="${empty user}">
		<jsp:forward page="/index.jsp"></jsp:forward>
	</c:if>

	<c:if test="${not empty user}">
		<c:set var="cor" value="false" />
		<c:forEach items="${user.role}" var="role">
			<c:if test="${role eq 'admin'}">
				<c:set var="cor" value="true"/>
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
				<a class="navbar-brand" href="Admin.jsp" style="color: #fff;">Testing
					Center</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">
						${user.firstName} ${user.lastName}<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Settings</a>
						</li>
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
							Appointments <small>All Appointments</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active"><a href="Admin.jsp">Home</a> >
								Appointments</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<form class="form-inline">
						<a href="AdminNewAppointment.jsp" class="btn btn-default" style="margin-bottom: 10px;"> New Appointment </a>
						<div style="margin-bottom: 10px; float: right;">
							<select class="form-control">
								<option>Student NetID</option>
								<option>Class</option>
								<option>Exam ID</option>
								<option>Status</option>
							</select>
							<div class="input-group">
								<input type="text" class="form-control" placeholder="Search"
									name="srch-term" id="srch-term">
								<div class="input-group-btn">
									<button class="btn btn-default" type="submit">Search</button>
								</div>
							</div>
						</div>
					</form>
					<c:choose>
					<c:when test="${returnVal == true}">
						<div class="row">
							<div class="col-lg-12">
								<div class="alert alert-success">Checkin successful!</div>
							</div>
						</div>
					</c:when>
					<c:when test="${returnVal == false}">
						<div class="row">
							<div class="col-lg-12">
								<div class="alert alert-danger">Checkin failed!</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<c:remove var="returnVal" scope="session" />
					<form action=AdministratorHome method=POST>
					<table class="table">
						<tr>
							<th>Exam Name</th>
							<th>Instructor</th>
							<th>Student Name</th>
							<th>Student NetID</th>
							<th>ExamID</th>
							<th>Test Time</th>
							<th>Seat Number</th>
							<th>Status</th>
							<th>Checkin</th>
						</tr>
						<c:forEach items="${appointments}" var="apps">
							<tr class="success">
								<td onClick="editAppointment(this);" id="<c:out value="${apps.user.netID}"/>_<c:out value="${apps.request.examIndex}"/>_<c:out value="${apps.user.term.termID}"/>"><font color="blue">${apps.request.examName}</font></td>
								<td>${apps.request.instructorNetID}</td>
								<td>${apps.user.firstName}${apps.user.lastName}</td>
								<td>${apps.user.netID}</td>
								<td>${apps.request.examIndex}</td>
								<td>${apps.timeStart}</td>
								<td>${apps.seatNum}</td>
								<c:choose>
									<c:when test="${apps.status eq 'taken'}">
										<td><font color="green">${apps.status}</font>
									</c:when>
									<c:when test="${apps.status eq 'missed'}">
										<td><font color="red">${apps.status}</font>
									</c:when>
									<c:when test="${apps.status eq 'pending'}">
										<td><font color="grey">${apps.status}</font>
									</c:when>
									<c:otherwise>
										<td>${apps.status}</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${apps.status eq 'taken' || apps.status eq 'missed'}">
										<td><input type="checkbox" name="" value="" disabled/></td>
									</c:when>
									<c:otherwise>
										<td><input type="checkbox" name="checkin" value="${apps.user.netID}:${apps.request.examIndex}:${apps.user.term.termID}" /></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</table>
					<input type="submit" class="btn btn-primary" name="checkin_btn" value="Checkin"/>
					</form>
				</div>
			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
	<script type ="text/javascript">
                function editAppointment(ele){
        			window.location = '/TestingCenterScheduler/EditAppointment.jsp?Appointment='+ele.id;
        		}
    </script>
	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

</body>

</html>
