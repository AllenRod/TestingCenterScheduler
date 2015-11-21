<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<script>
	$(document).ready(function() {
		var cur = $('#select').find(":selected").val();
		$('#slot' + cur).show();
	});

	$(function() {
		$('#select').change(function() {
			var id = $('#select').find(":selected").val();
			$('.formDiv').hide();
			$('#slot' + id).show();
		});
	});
	
	function pickSlot(data) {
		$('#Aslot').val(data);
	}
	
</script>

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
					data-toggle="dropdown"> ${user.firstName} ${user.lastName}<b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"> Settings</a></li>
						<li class="divider"></li>
						<li>
							<form action="Login" method="GET">
								<a> <input type="submit" value="Log Out" name="logout"
									style="background-color: Transparent; border: none;">
								</a>
							</form>
						</li>
					</ul></li>
			</ul>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li><a href="Student.jsp" style="color: #fff;">View
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
						<h1 class="page-header">New Appointment</h1>
						<ol class="breadcrumb">
							<li class="active"><a href="Student.jsp"> Home</a> > New
								Appointment</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->
				<div class="div-spacing">
					<form class="form-inline" action="StudentHome" method="POST">
						<div class="form-group">
							<label for="Areq">Request ID: </label> <input type="text"
								class="form-control input-sm" name="AreqID" value="${AreqID}"
								readonly />
						</div>
						<div class="form-group">
							<label for="Atimestart">Start Time: </label> <input type="text"
								class="form-control input-sm" name="Atime" id="Aslot"
								placeholder="Pick a time slot in drop down" readonly />
						</div>
						<select id="select" class="form-control">
							<c:forEach items="${timeSlot}" var="slotEntry">
								<fmt:formatDate pattern="D" value="${slotEntry.key}" var="key" />
								<option value="${key}">
									<fmt:formatDate type="date" value="${slotEntry.key}" />
								</option>
							</c:forEach>
						</select>
						<c:forEach items="${timeSlot}" var="slotEntry">
							<fmt:formatDate pattern='D' value='${slotEntry.key}' var="key" />
							<div id="slot${key}" class="formDiv" style="display: none">
								<h5>
									<fmt:formatDate type="date" value="${slotEntry.key}" />
								</h5>
								<table class="table table-striped">
									<tr>
										<th>Time</th>
										<td>Open Seat Number</td>
										<td>Choose Slot</td>
									</tr>
									<c:forEach items="${slotEntry.value}" var="slot">
										<tr>
											<th><fmt:formatDate type="time" value="${slot.key}" /></th>
											<td>${slot.value}</td>
											<td>
												<button type="button" value="${slot.key}"
													class="btn btn-primary" onClick="pickSlot(this.value);">Pick
													Slot</button>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:forEach>

						<c:set var="action" value="newAppointment" scope="session" />
						<a href="Student.jsp" class="btn btn-default"
							style="background: #DDD; color: #980100;">Cancel</a> <input
							type="submit" value="Submit Appointment" class="btn btn-default"
							style="background: #980100; color: #FFF;">

					</form>
				</div>

			</div>
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->


</body>

</html>