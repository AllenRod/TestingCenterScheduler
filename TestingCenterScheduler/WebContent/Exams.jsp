<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Instructor Exams</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom CSS -->
    <link href="css/core.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

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
                    <li>
                        <a href="Instructor.jsp" style="color:#fff;"> View Courses</a>
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
                            View Scheduled Exams <small>Exams</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <a href="Instructor.jsp"> Home</a> > Exams
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				<div class="row">
					<table class="table table-striped table-hover">
							<tr>
								<th>ClassID</th>
								<th>Test Name</th>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Duration</th>
								<th>Students Taken</th>
								<th>Status</th>
							</tr>
							<c:forEach items="${requests}" var="requests">    
	    						<c:if test="${requests.status ne 'denied'}">
		    						<c:if test="${requests.status ne 'pending'}">
			    						<tr class="success">
											<td><font color="blue">${requests.course.classID}</font></td>
											<td>${requests.examName}</td>
											<td>${requests.timeStart}</td>
											<td>${requests.timeEnd}</td>
											<td>${requests.testDuration}</td>
											<td>${fn:length(requests.appointment)}</td>
											<td><font color="green">${requests.status}</font></th>
										</tr>
									</c:if>
								</c:if>
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