<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            View Requests <small>Requests</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <a href="Admin.jsp"> Home</a> > Requests
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				<div class="row">
				<c:choose>
				    <c:when test="${returnVal == 'Data import succeeds'}">
				        <div class="row">
							<div class="col-lg-12">
								<div class="alert alert-success">
									Request Successfully Processed
								</div>
							</div>
						</div>
				    </c:when>
				    <c:when test="${not empty returnVal}">
				        <div class="row">
							<div class="col-lg-12">
								<div class="alert alert-danger">
									${returnVal}
								</div>
							</div>
						</div>
				    </c:when>
				    <c:otherwise>
				    </c:otherwise>
				</c:choose>

					<div class=".col-md-6 .col-md-offset-3">
						<h2>StonyBrook Exam Requests</h2>
					</div>
					<table class ="table">
							<tr>
								<th>ClassID</th>
								<th>Test Name</th>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Duration</th>
								<th>RequestID</th>
								<th>Status</th>
								<th>Approve/Deny</th>
							</tr>
							<c:forEach items="${crequests}" var="requests">
							<tr class="success" onClick="editRequest(this);" id = "<c:out value="${requests.examIndex}"/>">
								<td><font color="blue">
										${requests.course.classID}</font></td>
								<td>${requests.examName}</td>
								<td>${requests.timeStart}</td>
								<td>${requests.timeEnd}</td>
								<td>${requests.testDuration}</td>
								<td>${requests.examIndex}</td>
								<c:choose>
									<c:when test="${requests.status eq 'approved'}">
										<td><font color="green">${requests.status}</font>
									</c:when>
									<c:when test="${requests.status eq 'denied'}">
										<td><font color="red">${requests.status}</font>
									</c:when>
									<c:when test="${requests.status eq 'pending'}">
										<td><font color="grey">${requests.status}</font>
									</c:when>
									<c:otherwise>
										<td>${requests.status}</font>
									</c:otherwise>
								</c:choose>
								<td><!--                TODO                             --></td>
							</tr>
							</c:forEach>
						</table>
						<div class=".col-md-6 .col-md-offset-3">
							<h2>Non-StonyBrook Exam Requests</h2>
						</div>
						<table class ="table">
							<tr>
								<th>ClassID</th>
								<th>Test Name</th>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Duration</th>
								<th>RequestID</th>
								<th>Status</th>
								<th>Approve/Deny</th>
							</tr>
							<c:forEach items="${nrequests}" var="requests">   
							<tr class="info" onClick="editRequest(this);" id="<c:out value="${requests.examIndex}"/>">
								<td><font color="blue">
										Non-Class Exam</font></td>
								<td>${requests.examName}</td>
								<td>${requests.timeStart}</td>
								<td>${requests.timeEnd}</td>
								<td>${requests.testDuration}</td>
								<td>${requests.examIndex}</td>
								<c:choose>
									<c:when test="${requests.status eq 'approved'}">
										<td><font color="green">${requests.status}</font>
									</c:when>
									<c:when test="${requests.status eq 'denied'}">
										<td><font color="red">${requests.status}</font>
									</c:when>
									<c:when test="${requests.status eq 'pending'}">
										<td><font color="grey">${requests.status}</font>
									</c:when>
									<c:otherwise>
										<td>${requests.status}</font>
									</c:otherwise>
								</c:choose>
								
							
							<td><!--                TODO                             --></td>
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