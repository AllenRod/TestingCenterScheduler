<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>CSE114.01</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="css/plugins/morris.css" rel="stylesheet">

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
                <a class="navbar-brand" href="Instructor.html" style="color:#fff;">Testing Center</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="index.html"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li id = "current">
                        <a href="Instructor.html" style="color:#fff;"><i class="fa fa-fw fa-dashboard"></i> View Courses</a>
                    </li>
                    <li>
                        <a href="Requests.html" style="color:#fff;"><i class="fa fa-fw fa-desktop"></i>View Requests</a>
                    </li>
                    <li>
                        <a href="Exams.html" style="color:#fff;"><i class="fa fa-fw fa-table"></i>View Scheduled Exams</a>
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
                            CSE114 <small>Courses</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> <a href="Instructor.html"> Courses</a> > CSE114
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				<div class="row">
                    <div class="col-sm-12">
						<div class="panel panel-success">
                            <div class="panel-heading">
                                <h3 class="panel-title">Upcomming Exams</h3>
                            </div>
                            <div class="panel-body">
                            <a href="#">Midterm 1</a> <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info</i>
							</div>
							<div class="panel-body">
                            <a href="#">Quiz 4</a> <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info</i>
							</div>
							<div class="panel-body">
                            <a href="#">Quiz 5</a> <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info</i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <ul class="list-group">
                            <li class="list-group-item active">Recent Requests <a href="#" class="btn btn-default" style="float:right; margin-top:-6px"> New Request</a></li>
							<a href="#" class="list-group-item">Midterm 2 <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info Status: <font color="#000">Pending</font></i></a> 
							<a href="#" class="list-group-item">Quiz 5 <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info Status: <font color="green">Approved</font></i></a>
                            <a href="#" class="list-group-item">Quiz 4 <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info Status: <font color="green">Approved</font></i></a>
							<a href="#" class="list-group-item">Midterm 1 <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info Status: <font color="green">Approved</font></i></a>
                            <a href="#" class="list-group-item">Quiz 3 <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info Status: Completed</i></a>
                            <a href="#" class="list-group-item">Quiz 2 <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info Status: Completed</i></a>
                            <a href="#" class="list-group-item">Quiz 1 <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info Status: Completed</i></a>
                        </ul>
                    </div>
                </div>
				<div class="row">
                    <!-- /.col-sm-4 -->
                    <div class="col-sm-12">
                        <div class="list-group">
                            <a class="list-group-item active">Past Exams</a>
							<a href="#" class="list-group-item">Quiz 3 <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info</a>
                            <a href="#" class="list-group-item">Quiz 2 <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info</a>
                            <a href="#" class="list-group-item">Quiz 1 <i style="color:#d3d3d3;">info info info info info info info info info info info info info info info</a>
                        </div>
                    </div>
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

    <!-- Morris Charts JavaScript -->
    <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script>

</body>

</html>
