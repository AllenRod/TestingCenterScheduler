<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Exam Information</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/core.css" rel="stylesheet">

</head>

<body>

	<c:if test="${empty user}">
		<jsp:forward page="/index.jsp"></jsp:forward>
	</c:if>

	<c:if test="${(not empty user) and (user.role ne 'instr')}">
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
                            Exam Information
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <a href="Instructor.jsp"> Home</a> > <a href="Exams.jsp"> Exams</a> > Exam Information
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
                <c:set var="isADHOC" value="false" />
					<c:forEach var="requests" items="${nrequests}">
					  <c:if test="${requests.examIndex eq param.RequestID}">
					    <c:set var="isADHOC" value="true" />
					  </c:if>
					</c:forEach>
                
                <c:if test="${isADHOC eq true}">
					<c:forEach var="requests" items="${nrequests}">
					  <c:if test="${requests.examIndex eq param.RequestID}">
					    <c:set var="request" value="${requests}" scope="page"/>
					  </c:if>
					</c:forEach>
				</c:if>
                
                <c:if test="${isADHOC eq false}">
					<c:forEach var="requests" items="${crequests}">
					  <c:if test="${requests.examIndex eq param.RequestID}">
					    <c:set var="request" value="${requests}" scope="page"/>
					  </c:if>
					</c:forEach>
				</c:if>
				
                <c:set var="dateTimeParts" value="${fn:split(request.timeStart, ' ')}" />
                <c:set var="smonth" value="${dateTimeParts[1]}" />
                <c:set var="sday" value="${dateTimeParts[2]}" />
                <c:set var="syear" value="${dateTimeParts[5]}" />
                
				<c:set var="stime" value="${dateTimeParts[3]}" />
                
                <c:set var="dateTimeParts" value="${fn:split(request.timeEnd, ' ')}" />
                <c:set var="emonth" value="${dateTimeParts[1]}" />
                <c:set var="eday" value="${dateTimeParts[2]}" />
                <c:set var="eyear" value="${dateTimeParts[5]}" />
                
				<c:set var="etime" value="${dateTimeParts[3]}" />
                                
				<div class="div-spacing">
					<div class="form-inline">
					
						<div class="form-group">
							<label>REQUEST/EXAM ID: </label> 
								<i> ${param.RequestID}</i> 
						</div>
						<br>
						<div class="form-group">
							<label>Request Type: </label> 
								<c:if test="${isADHOC eq true}">
									<i> AD_HOC</i> 
								</c:if>
								<c:if test="${isADHOC eq false}">
					    			<i> CLASS</i> 
					  			</c:if>
						</div>
						<br>
						<div class="form-group">
							<label>ClassID: </label>
								<c:if test="${isADHOC eq true}">
									<i> Non-Class Exam</i>
								</c:if>
								<c:if test="${isADHOC eq false}">
					    			<i> ${request.course.classID}</i>
					  			</c:if>
						</div>
						<br>
						<div class="form-group">
							<label>Term: </label>
								<c:if test="${isADHOC eq true}">
									<i> No Term</i>
								</c:if>
								<c:if test="${isADHOC eq false}">
					    			<i> ${request.course.term.termID}</i>
					  			</c:if>
						</div>
						<br>
						<div class="form-group">
							<label>Exam Name: </label>
							<i> ${request.examName}</i>
						</div>
						<br>
						<div class="form-group">
							<label>Test Duration: </label>
							<i> ${request.testDuration}</i>
						</div>
						<br>
						<div class="form-group">
							<label>Start Time: </label>
							<i> ${smonth} ${sday}, ${syear} at ${stime}</i>
						</div>
						<br>
						<div class="form-group">
							<label>End Time: </label>
							<i> ${emonth} ${eday}, ${eyear} at ${etime}</i>
						</div>
						<br>
						<div class="form-group">
							<c:if test="${isADHOC eq true}">
							<label for="Rlist">Student Information:</label>
							<br>
								<textarea id="Rlist" name="Rlist" class="form-control" rows="7" cols="50" readonly><c:out value="${request.rosterList}"/></textarea>
							</c:if>
							<c:if test="${isADHOC eq false}">
							<c:set var="action" value="getExamAppointments" scope="session" />
							<c:if test="${empty returnVal}">
							<form action="InstructorHome" method="POST">
								<input type="hidden" name="EID" value="${param.RequestID}">
								<input type="hidden" name="CID" value="${request.course.classID}">
					    		<input type="submit" value="Load Appointment Information"
								class="btn btn-default"	style="background: #FFF; color: #000;">
					    	</form>
					    	</c:if>
					    	<c:if test="${not empty returnVal}">
								<div>
									<label>Total Seats Reserved: </label> ${returnVal2}
									<br>
									<label>Total Appointments: </label> ${fn:length(returnVal)}
									<table class="table table-striped table-hover" onload="hideButt()">
									<tr>
									<th>Student NetID</th>
									<th>Seat No.</th>
									<th>Appointment Time</th>
									<th>Status</th>
									</tr>
									<c:forEach var="app" items="${returnVal}">
									<c:if test="${app.status eq 'taken'}">
									<tr class="success">
									  	<td>${app.user.netID}</td>
									  	<td>${app.seatNum}</td>
									  	<td>${app.timeStart}</td>
									  	<td><font color="green">${app.status}</font></td>
									</tr>
									</c:if>
									<c:if test="${app.status eq 'missed'}">
									<tr class="danger">
									  	<td>${app.user.netID}</td>
									  	<td>${app.seatNum}</td>
									  	<td>${app.timeStart}</td>
									  	<td><font color="red">${app.status}</font></td>
									</tr>
									</c:if>
									<c:if test="${app.status ne 'taken' && app.status ne 'missed'}">
									<tr>
									  	<td>${app.user.netID}</td>
									  	<td>${app.seatNum}</td>
									  	<td>${app.timeStart}</td>
									  	<td>${app.status}</td>
									</tr>
									</c:if>
									</c:forEach>
									</table>
									<c:remove var="returnVal" scope="session" />
									<c:remove var="returnVal2" scope="session" />
								</div>
							</c:if>
					  		</c:if>
						</div>
						<br>
						<a href="Exams.jsp" class="btn btn-default"
							style="background: #DDD; color: #980100; margin-top: 5px">Back</a>
					</div>
				</div>
			</div>
		</div>
	</div>	
	<!-- /.container-fluid -->
	<script type ="text/javascript">
	</script>
	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

	<script>

	</script>
</body>

</html>
