<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <nav class="navbar navbar-inverse navbar-fixed-top navbar-custom" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="Instructor.jsp" style="color:#fff;">Testing Center</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.firstName} ${user.lastName}<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"> Settings</a>
                        </li>
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
                    <li>
                        <a href="Instructor.jsp" style="color:#fff;">Overview</a>
                    </li>
                    <li>
                        <a href="Requests.jsp" style="color:#fff;"> View Requests</a>
                    </li>
                    <li>
                        <a href="Exams.jsp" style="color:#fff;"> View Scheduled Exams</a>
                    </li>
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
                                <a href="Instructor.jsp"> Home</a> > Requests
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				<div class="row">
				<c:choose>
				    <c:when test="${returnVal == 'New Request Success' || returnVal == 'Update Success' || returnVal == 'Delete Success'}">
				        <div class="row">
							<div class="col-lg-12">
								<div class="alert alert-success">
									${returnVal}
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
					<a href="NewRequest.jsp" class="btn btn-default" style="margin-bottom:10px;">
						New Request
					</a>
					<table class ="table">
							<tr>
								<th>ClassID</th>
								<th>Test Name</th>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Duration</th>
								<th>RequestID</th>
								<th>Status</th>
							</tr>
							<c:forEach items="${crequests}" var="requests">
							<tr class="success">
								<td onClick="editRequest(this);" id="<c:out value="${requests.examIndex}"/>"> <font color="blue" >
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
							</tr>
							</c:forEach>
						</table>
						<table class ="table">
							<tr>
								<th>ClassID</th>
								<th>Test Name</th>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Duration</th>
								<th>RequestID</th>
								<th>Status</th>
							</tr>
							<c:forEach items="${nrequests}" var="requests">   
							<tr class="info">
								<td onClick="editRequest(this);" id="<c:out value="${requests.examIndex}"/>"><font color="blue">
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

    <script type ="text/javascript">
                function editRequest(ele){
        			window.location = '/TestingCenterScheduler/EditRequest.jsp?RequestID='+ele.id;
        		}
    </script>
    
    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>


</body>

</html>
