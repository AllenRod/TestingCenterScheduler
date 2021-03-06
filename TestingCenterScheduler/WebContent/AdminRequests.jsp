<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Admin Requests</title>

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
					data-toggle="dropdown"> ${user.firstName} ${user.lastName}<b
						class="caret"></b></a>
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
								<a>
									<input type="submit" value="Log Out" name="logout"
										style="background-color: Transparent; border: none;">
								</a>
								</form></li>
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
							View Requests <small>Requests</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active"><a href="Admin.jsp"> Home</a> > Requests
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->
				<div class="row">
					<c:choose>
						<c:when test="${returnVal2 == true}">
							<div class="row">
								<div class="col-lg-12">
									<div class="alert alert-success">${returnVal1}Successful</div>
								</div>
							</div>
						</c:when>
						<c:when test="${returnVal2 == false}">
							<div class="row">
								<div class="col-lg-12">
									<div class="alert alert-danger">${returnVal1}Failure</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
					<div class=".col-md-6 .col-md-offset-3">
						<h2>StonyBrook Exam Requests</h2>
					</div>
					<div class="modal fade" id="utilization" tabindex="-1" role="dialog" aria-labelledby="idkModalLabel" aria-hidden="true">
						<div class="modal-dialog">
						    <div class="modal-content">
								<div class="modal-header" id="modalContent"></div>
								<div class="modal-footer">
									    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								</div>
						    </div>
						</div>
					</div>
						
					<table class="table">
						<tr>
							<th>ClassID</th>
							<th>Test Name</th>
							<th>Start Date</th>
							<th>End Date</th>
							<th>Duration</th>
							<th>RequestID</th>
							<th>Status</th>
							<th>Utilization</th>
							<th>Approve/Deny</th>
						</tr>
						<c:forEach items="${crequests}" var="requests">
							<tr class="success" onClick="editRequest(this);"
								id="<c:out value="${requests.examIndex}"/>">
								<td><font color="blue"> ${requests.course.classID}</font></td>
								<td>${requests.examName}</td>
								<td>${requests.timeStart}</td>
								<td>${requests.timeEnd}</td>
								<td>${requests.testDuration}</td>
								<td>${requests.examIndex}</td>
								<c:choose>
									<c:when test="${requests.status eq 'approved'}">
										<td><font color="green">${requests.status}</font></td>
									</c:when>
									<c:when test="${requests.status eq 'denied'}">
										<td><font color="red">${requests.status}</font></td>
									</c:when>
									<c:when test="${requests.status eq 'pending'}">
										<td><font color="grey">${requests.status}</font></td>
									</c:when>
									<c:otherwise>
										<td>${requests.status}</td>
									</c:otherwise>
								</c:choose>
								
								<c:choose>
								<c:when test = "${requests.status eq 'approved' || requests.status eq 'denied'}">
								<td><button type="button" class="btn btn-primary" disabled>View Utilization</button></td>
								<td>
									<form action = AdministratorHome method = POST>
										<button type=submit class="btn btn-success disabled" name="request_approve" value="${requests.examIndex}">Approve</button>
									</form> 
									<form action = AdministratorHome method = POST>
										<button type="submit" class="btn btn-danger disabled" name="request_deny" value="${requests.examIndex}">Deny</button>
									</form>
								</td>
								</c:when>
								<c:when test = "${requests.status eq 'pending'}">
								<td><button type="button" class="btn btn-primary" onClick="loadContent(this)" id="${requests.examIndex}">View Utilization</button></td>
								<td>
									<form action = AdministratorHome method = POST>
										<button type=submit class="btn btn-success" name="request_approve" value="${requests.examIndex}">Approve</button>
									</form> 
									<form action = AdministratorHome method = POST>
										<button type="submit" class="btn btn-danger" name="request_deny" value="${requests.examIndex}">Deny</button>
									</form>
								</td>
								</c:when>
								</c:choose>
							</tr>
						</c:forEach>
					</table>
					<div class=".col-md-6 .col-md-offset-3">
						<h2>Non-StonyBrook Exam Requests</h2>
					</div>
					<table class="table">
						<tr>
							<th>ClassID</th>
							<th>Test Name</th>
							<th>Start Date</th>
							<th>End Date</th>
							<th>Duration</th>
							<th>RequestID</th>
							<th>Status</th>
							<th>Utilization</th>
							<th>Approve/Deny</th>
						</tr>
						<c:forEach items="${nrequests}" var="requests">
							<tr class="info" onClick="editRequest(this);"
								id="<c:out value="${requests.examIndex}"/>">
								<td><font color="blue"> Non-Class Exam</font></td>
								<td>${requests.examName}</td>
								<td>${requests.timeStart}</td>
								<td>${requests.timeEnd}</td>
								<td>${requests.testDuration}</td>
								<td>${requests.examIndex}</td>
								<c:choose>
									<c:when test="${requests.status eq 'approved'}">
										<td><font color="green">${requests.status}</font></td>
									</c:when>
									<c:when test="${requests.status eq 'denied'}">
										<td><font color="red">${requests.status}</font></td>
									</c:when>
									<c:when test="${requests.status eq 'pending'}">
										<td><font color="grey">${requests.status}</font></td>
									</c:when>
									<c:otherwise>
										<td>${requests.status}</td>
									</c:otherwise>
								</c:choose>
								
								<c:choose>
								<c:when test = "${requests.status eq 'approved' || requests.status eq 'denied'}">
								<td><button type="button" class="btn btn-primary" disabled>View Utilization</button></td>
								<td>
								<form action = AdministratorHome method = POST>
										<button type=submit class="btn btn-success disabled" name="request_approve" value="${requests.examIndex}">Approve</button>
									</form> 
									<form action = AdministratorHome method = POST>
										<button type="submit" class="btn btn-danger disabled" name="request_deny" value="${requests.examIndex}">Deny</button>
									</form>
									</td>
								</c:when>
								<c:when test = "${requests.status eq 'pending'}">
								<td><button type="button" class="btn btn-primary" onClick="loadContent(this)" id="${requests.examIndex}">View Utilization</button></td>
								<td>
									<form action = AdministratorHome method = POST>
										<button type=submit class="btn btn-success" name="request_approve" value="${requests.examIndex}">Approve</button>
									</form> 
									<form action = AdministratorHome method = POST>
										<button type="submit" class="btn btn-danger" name="request_deny" value="${requests.examIndex}">Deny</button>
									</form>
								</td>
								</c:when>
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
	<script>
	function loadContent(ele) {
		window.open("/TestingCenterScheduler/EmptyPage.jsp?requestID="+ele.id);
	}
	</script>
	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>


</body>

</html>
