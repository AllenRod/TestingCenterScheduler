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
					<li><a href="Admin.jsp" style="color: #fff;">Home</a></li>
					<li><a href="AdminUtilization.jsp" style="color: #fff;">Testing Center Usage Report</a></li>
					<li><a href="CenterHours.jsp" style="color: #fff;" >Testing Center Information</a></li>
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
				<div>
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
						<select class="form-control" id="select">				
							<c:forEach items="${infolist}" var="info" varStatus="i"> 
    							<option value="${i.index}">${info.term.termID} ${info.term.termSeason}_${info.term.termYear}</option>
							</c:forEach>
							<option value="newForm" selected>New Info</option>
						</select>
					</div>
				</div>
				
				<c:forEach items="${infolist}" var="info" varStatus="i">
				<!-- /.row -->
				<div class="row formTable" id="table${i.index}" style="display:none;">
				<div class="col-lg-12">
					<form action="AdministratorHome" method="POST" >
					<table class = "info-Table">
						<tr>
							<th>Term:</th>
							<td><input type="text" class="form-control" id="term" name="term" value="${info.term.termID}" readonly/></td>
						</tr>
						<tr>
							<th>Open Hours:</th>
							<td>
							<table class="info-table"  style="width:100%">
								<tr>
									<th>Monday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="mono"></label>
										<select name="mono" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										 <label class="sr-only" for="monc"></label>
										<select name="monc" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Tuesday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="tueo"></label>
										<select name="tueo" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										 <label class="sr-only" for="tuec"></label>
										<select name="tuec" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Wednesday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="wedo"></label>
										<select name="wedo" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										 <label class="sr-only" for="wedc"></label>
										<select name="wedc" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Thursday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="thuo"></label>
										<select name="thuo" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										 <label class="sr-only" for="thuc"></label>
										<select name="thuc" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Friday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="frio"></label>
										<select name="frio" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										 <label class="sr-only" for="fric"></label>
										<select name="fric" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Saturday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="sato"></label>
										<select name="sato" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										<label class="sr-only" for="satc"></label>
										<select name="satc" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Sunday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="suno"></label>
										<select name="suno" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										<label class="sr-only" for="sunc"></label>
										<select name="sunc" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<th>Gap Time:</th>
							<td>
								<div class="form-group">
									<label class="sr-only" for="gaptime">Gap Time:</label> <input
										type="number" class="form-control input-sm" id="gaptime"
										name="gaptime" min = "0" max = "30" placeholder="Minutes" value = "${info.gapTime}" required>
								</div>
							</td>
						</tr>
						<tr>
							<th>Closing Dates:</th>
							<td><input type="text" class="form-control" id="closing" name="closing" value="${info.closedDate}"/></td>
						</tr>
						<tr>
							<th>Reserve Time:</th>
							<td><input type="text" class="form-control" id="reserve" name="reserve" value="${info.reserveTime}"/></td>
						</tr>
						<tr>
							<th>Reminder Interval:</th>
							<td><input type="text" class="form-control" id="reminder" name="reminder" value="${info.reminderInterval}" required/></td>
						</tr>
						<tr>
							<th>Seats:</th>
							<td><input type="text" class="form-control" id="seat" name="seat" value="${info.seats}" required/></td>
						</tr>
						<tr>
							<th>Set-a-side Seats:</th>
							<td><input type="text" class="form-control" id="setseat" name="setseat" value="${info.setAsideSeats}" required/></td>
						</tr>
					</table>
					<input type="submit" class="btn btn-primary" name="edit" value="Submit"/>
					</form>
				</div>
			</div>
				
			</c:forEach>
			<div class="row formTable" id="newForm">
				<div class="col-lg-12">
					<form action="AdministratorHome" method="POST" >
					<table class = "info-Table">
						<tr>
							<th>Term:</th>
							<td><input type="text" class="form-control" id="term" name="term" placeholder="Enter term" required/></td>
						</tr>
						<tr>
							<th>Open Hours:</th>
							<td>
							<table class="info-table"  style="width:100%">
								<tr>
									<th>Monday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="mono"></label>
										<select name="mono" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										 <label class="sr-only" for="monc"></label>
										<select name="monc" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Tuesday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="tueo"></label>
										<select name="tueo" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										 <label class="sr-only" for="tuec"></label>
										<select name="tuec" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Wednesday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="wedo"></label>
										<select name="wedo" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										 <label class="sr-only" for="wedc"></label>
										<select name="wedc" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Thursday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="thuo"></label>
										<select name="thuo" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										 <label class="sr-only" for="thuc"></label>
										<select name="thuc" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Friday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="frio"></label>
										<select name="frio" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										 <label class="sr-only" for="fric"></label>
										<select name="fric" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Saturday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="sato"></label>
										<select name="sato" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										<label class="sr-only" for="satc"></label>
										<select name="satc" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
								<tr>
									<th>Sunday:</th>
									<td>
									<div class="form-inline">
										<div class="form-group">
										<label class="sr-only" for="suno"></label>
										<select name="suno" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										 to 
										<label class="sr-only" for="sunc"></label>
										<select name="sunc" class="form-control" style="width:100px">
											<c:forEach begin="0" end="23" var="val">
											    <option> ${val}:00</option>
											    <option> ${val}:30</option>
											</c:forEach>
											<option selected>Closed</option>
										</select>
										</div>
									</div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<th>Gap Time:</th>
							<td>
								<div class="form-group">
									<label class="sr-only" for="gaptime">Gap Time:</label> <input
										type="number" class="form-control input-sm" id="gaptime"
										name="gaptime" min = "0" max = "30" placeholder="Minutes" required>
								</div>
							</td>
						</tr>
						
						<tr>
							<th>Closing Dates:</th>
							<td><input type="text" class="form-control" id="closing" name="closing" placeholder="Enter closing date range"/></td>
						</tr>
						<tr>
							<th>Reserve Time:</th>
							<td><input type="text" class="form-control" id="reserve" name="reserve" placeholder="Enter reserve time"/></td>
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
	
	<script>
		$(function() {
			$('#select').change(function(){
				var term = $('#select').find(":selected").val();
				if (term == "newForm") {
					$('.formTable').hide();
					$('#newForm').show();
				} else {
					$('.formTable').hide();
					$('#table'+term).show();
				}
			});
		});	
	</script>

</body>

</html>
