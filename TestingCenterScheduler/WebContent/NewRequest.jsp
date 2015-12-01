<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Instructor Requests</title>

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
			<c:if test="${role eq 'instr'}">
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
				<a class="navbar-brand" href="Instructor.jsp" style="color: #fff;">Testing
					Center</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">${user.firstName} ${user.lastName}<b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"> Settings</a></li>
						<c:forEach items="${user.role}" var="role">
							<c:if test="${role eq 'admin'}">
								<li><a href="AdministratorHome">Admin Page</a></li>
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
								</form>
						</li>
					</ul></li>
			</ul>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li><a href="Instructor.jsp" style="color: #fff;">Overview</a>
					</li>
					<li><a href="Requests.jsp" style="color: #fff;"> View
							Requests</a></li>
					<li><a href="Exams.jsp" style="color: #fff;"> View
							Scheduled Exams</a></li>
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
                            New Request
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <a href="Instructor.jsp"> Home</a> > <a href="Requests.jsp"> Requests</a> > New Request 
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				<div class="div-spacing">
					<form class="form-inline" action="InstructorHome" method="POST">
						<div class="form-group">
							<label for="Rtype">Request Type:</label> <select
								class="form-control" id="Rtype" name="Rtype">
								<option value="CLASS" selected>CLASS</option>
								<option value="AD_HOC">AD_HOC</option>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Rclass">ClassID: </label> <select
								class="form-control" id="Rclass" name="Rclass">
								<c:forEach items="${courses}" var="courses">
									<option value="${courses.classID}">${courses.subject}${courses.catalogNum}-${courses.section}_${courses.term.termID}</option>
								</c:forEach>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Rterm">Term: </label> <select
								class="form-control input-sm" id="Rterm" name="Rterm">
								<c:forEach items="${termlist}" var="term">
									<option value="${term.termID}">${term.termID}
										${term.termSeason}_${term.termYear}</option>
								</c:forEach>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Rname">Exam Name: </label> <input type="text"
								class="form-control input-sm" id="Rname" name="Rname"
								placeholder="Exam Name">
						</div>
						<br>
						<div class="form-group">
							<label for="Rduration">Test Duration: </label> <input
								type="number" class="form-control input-sm" id="Rduration"
								name="Rduration" min="1" placeholder="Duration" required>
						</div>
						<br>
						<div class="form-group">
							<label for="Rsmon">Start Time: </label> <input type="number"
								class="form-control input-sm" id="Rsmon" name="Rsmon" min="1"
								max="12" placeholder="Month" style="width: 100px;" required>

							<input type="number" class="form-control input-sm" id="Rsday"
								name="Rsday" min="1" max="31" placeholder="Day"
								style="width: 100px;" required>

							<select name="Rstime" class="form-control input-sm"
								style="width: 100px">
								<c:forEach begin="0" end="23" var="val">
									<option>${val}:00</option>
									<option>${val}:30</option>
								</c:forEach>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Remon">End Time: </label> <input type="number"
								class="form-control input-sm" id="Remon" name="Remon" min="1"
								max="12" placeholder="Month" style="width: 100px;" required>
							<input type="number" class="form-control input-sm" id="Reday"
								name="Reday" min="1" max="31" placeholder="Day"
								style="width: 100px;" required>

							<select name="Retime" class="form-control input-sm" style="width: 100px">
								<c:forEach begin="0" end="23" var="val">
									<option>${val}:00</option>
									<option>${val}:30</option>
								</c:forEach>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Rlist">Student List (non-Stony Brook course
								exam Only):</label>
							<br>
							<textarea id="Rlist" name="Rlist" class="form-control" rows="7" cols="50" disabled></textarea>
						</div>
						<br>
						<c:set var="action" value="newRequest" scope="session" />
						<a href="Requests.jsp" class="btn btn-default"
							style="background: #DDD; color: #980100;">Cancel</a>
						<input type="submit" value="New Request"
							class="btn btn-default"
							style="background: #980100; color: #FFF;">
					</form>
				</div>
			</div>
		
			<div class="randomTryOUtOP">
				<table class ="table">
					<c:forEach items="${requests}" var="request">
							<tr>
								<td>${request.examIndex}</td>
								<td>${request.examName}</td>
								<td>${request.timeStart}</td>
								<td>${request.timeEnd}</td>
								<td>${request.testDuration}</td>
								<td>${request.instructorNetID}</td>
								<td>${request.status}</td>
							</tr>	
					</c:forEach>
					</table>
			</div>
		
		</div>

	
	
	</div>
				
				<!-- /.container-fluid -->

				<!-- jQuery -->
				<script src="js/jquery.js"></script>

				<!-- Bootstrap Core JavaScript -->
				<script src="js/bootstrap.min.js"></script>

				<script>
					$(function() {
						$('#Rtype').change(function() {
							var type = $('#Rtype').find(":selected").val();
							if (type == "AD_HOC") {
								$('#Rclass').prop('disabled', true);
								$('#Rlist').prop('disabled', false);
							} else {
								$('#Rclass').prop('disabled', false);
								$('#Rlist').prop('disabled', true);
							}
						});
					});
				</script>
</body>

</html>
