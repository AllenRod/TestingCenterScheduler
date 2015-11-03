<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Admin Home</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/core.css" rel="stylesheet">

<link rel="stylesheet" href="css/bootstrap-datepicker.min.css" />
<link rel="stylesheet" href="css/bootstrap-datepicker3.min.css" />

<script src="js/bootstrap-datepicker.min.js"></script>
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
				<a class="navbar-brand" href="Admin.html" style="color: #fff;">Testing
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
					<li><a href="Admin.jsp" style="color: #fff;">Testing Center Usage Report</a></li>
					<li><a href="#" style="color: #fff;" >Testing Center Information</a></li>
					<li><a href="ImportData.jsp" style="color: #fff;">Import Data</a></li>
					<li><a href="#" style="color: #fff;">View All Requests</a></li>
					<li><a href="#" style="color: #fff;">View All Appointments</a></li>
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
							Testing Center <small>Information</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active"> <a href= "Admin.jsp">Home</a> > Testing Center Information</li>
						</ol>
					</div>
				</div>
				
				<div class="row">
					<div class="col-sm-6">
						<ul class="list-group">
						<c:if test="${empty info}">
   							You have no courses
						</c:if>
						
						<c:forEach items="${info}" var="info">    
    						${info.term}<button class="btn btn-primary" id="edit${info.term}">Edit</button>
						</c:forEach>
							
						</ul>
					</div>
				</div>
				
				<button class="btn btn-success" id="new">New Term</button>
				
				<!-- /.row -->
				<div class="row" style="display:none;">
				
					<form action="Administrator" method="POST" >
					<table class = "info-Table">
						<tr>
							<th>Term:</th>
							<td><input type="text" class="form-control" id="term" name="term" placeholder="Enter term" required/></td>
						</tr>
						<tr>
							<th>Gap Time:</th>
							<td><input type="text" class="form-control" id="gaptime" name="gaptime" placeholder="Enter gap time" required/></td>
						</tr>
						<tr>
							<th>Open Hours:</th>
							<td>
							<table class="info-table"  style="width:70%">
								<tr>
									<th>Monday:</th>
									<td><select name="mono" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
										 to 
										<select name="monc" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>Tuesday:</th>
									<td><select name="tueo" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
										 to 
										<select name="tuec" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>Wednesday:</th>
									<td><select name="wedo" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
										 to 
										<select name="wedc" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>Thursday:</th>
									<td><select name="thuo" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
										 to 
										<select name="thuc" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>Friday:</th>
									<td><select name="frio" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
										 to 
										<select name="fric" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>Saturday:</th>
									<td><select name="sato" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
										 to 
										<select name="satc" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>Sunday:</th>
									<td><select name="suno" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
										 to 
										<select name="sunc" class="form-control" style="width:100px">
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00am</option>
											    <option> ${val}:30am</option>
											</c:forEach>
											<c:forEach begin="1" end="12" var="val">
											    <option> ${val}:00pm</option>
											    <option> ${val}:30pm</option>
											</c:forEach>
										</select>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<th>Closing Dates:</th>
							<td><input type="text" class="form-control" id="closing" name="closing" placeholder="Enter closing date range" required/></td>
						</tr>
						<tr>
						<tr>
							<th>Reserve Time:</th>
							<td><input type="text" class="form-control" id="reserve" name="reserve" placeholder="Enter reserve time" required/></td>
						</tr>
						<tr>
							<th>Reminder Interval:</th>
							<td><input type="text" class="form-control" id="reminder" name="reminder" placeholder="Enter reminder interval" required/></td>
						</tr>
						<tr>
							<th>Seats:</th>
							<td><input type="text" class="form-control" id="seat" name="seat" placeholder="Enter seats" required/></td>
						</tr>
						<tr>
							<th>Set-a-side Seats:</th>
							<td><input type="text" class="form-control" id="setseat" name="setseat" placeholder="Enter seat aside seats" required/></td>
						</tr>
					</table>
					<input type="submit" class="btn btn-primary" name="edit" value="Submit"/>
					</form>
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