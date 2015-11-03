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
	<div class="container-fluid">

		<!-- Page Heading -->
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">
					View Requests <small>Requests</small>
				</h1>
			</div>
		</div>
		<!-- /.row -->
		<div class="row">
			<div id="form">
				<form action="InstructorHome" method="POST">
					<table>
						<tr>
							<th>Request Type:</th>
							<td><select class="form-control input-sm" id="Rtype"
								name="Rtype">
									<option value="CLASS" selected>CLASS</option>
									<option value="AD_HOC">AD_HOC</option>
							</select></td>
						</tr>
						<tr>
							<th>ClassID: <br></th>
							<td><select class="form-control input-sm" id="Rclass"
								name="Rclass">
									<c:forEach items="${courses}" var="courses">
										<option value="${courses.classID}">${courses.classID}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<th>Exam Name: <br></th>
							<td><input type="text" id="Rname" name="Rname" size="16"
								style="width: 250px; margin-bottom: 15px; margin-left: 10px;"
								required /></td>
						</tr>
						<tr>
							<th>Test Duration: <br></th>
							<td><input type="text" id="Rtime" name="Rtime" size="16"
								style="width: 250px; margin-bottom: 15px; margin-left: 10px;"
								required /></td>
						</tr>
						<tr>
							<th>Start Date: <br></th>
							<td><input type="text" id="Rstart" name="Rstart" size="16"
								style="width: 250px; margin-bottom: 15px; margin-left: 10px;"
								required /></td>
						</tr>
						<tr>
							<th>End Date: <br></th>
							<td><input type="text" id="Rend" name="Rend" size="16"
								style="width: 250px; margin-bottom: 15px; margin-left: 10px;"
								required /></td>
						</tr>
						<tr>
							<td><c:set var="action" value="newRequest" scope="session"/></td>
							<td><input type="submit" value="Submit Request"
								class="btn btn-lg btn-dark" style="background: #980100"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

</body>

</html>
