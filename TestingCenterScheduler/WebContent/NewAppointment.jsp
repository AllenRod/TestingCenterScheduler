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
                            New Appointment
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <a href="Student.jsp"> Home</a> > New Appointment 
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				<div class="div-spacing">
					<form class="form-inline" action="StudentHome" method="POST">
						<div class="form-group">
							<label for="Aclass">Available Exams: </label> <select
								class="form-control" id="Aclass" name="Aclass">
								<c:forEach items="${exams}" var="exam">
									<option value="${exam.examName}"></option>
								</c:forEach>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Start Time">Start Time: </label> <input type="number"
								class="form-control input-sm" id="Asmon" name="Asmon" min="1"
								max="12" placeholder="Month" style="width: 100px;" required>

							<input type="number" class="form-control input-sm" id="Asday"
								name="Asday" min="1" max="31" placeholder="Day"
								style="width: 100px;" required>

							<select name="Astime" class="form-control input-sm"
								style="width: 100px">
								<c:forEach begin="0" end="23" var="val">
									<option>${val}:00</option>
									<option>${val}:30</option>
								</c:forEach>
							</select>
						</div>
						<br>
						<c:set var="action" value="newAppointment" scope="session" />
						<a href="Student.jsp" class="btn btn-default"
							style="background: #DDD; color: #980100;">Cancel</a>
						<input type="submit" value="Submit Appointment"
							class="btn btn-default"
							style="background: #980100; color: #FFF;">
					</form>
				</div>
			
				<!-- OP -->
				<table>
					<c:forEach items="${requests}" var="request">
						<tr>
							<td>${request.examName}</td>
						</tr>
					</c:forEach>
				</table>
				
				
			</div>
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