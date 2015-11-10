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

	<h1 style="text-align: center;">Requests</h1>
	<div>
		<form action="InstructorHome" method="POST">
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<th style="padding-Bottom: 5px;">Request Type:</th>
					<td style="padding-Bottom: 5px;"><select
						class="form-control input-sm" id="Rtype" name="Rtype">
							<option value="CLASS" selected>CLASS</option>
							<option value="AD_HOC">AD_HOC</option>
					</select></td>
				</tr>
				<tr>
					<th style="padding-Bottom: 5px;">ClassID: <br></th>
					<td style="padding-Bottom: 5px;"><select
						class="form-control input-sm" id="Rclass" name="Rclass">
							<c:forEach items="${courses}" var="courses">
								<option value="${courses.classID}">${courses.classID}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th style="padding-Bottom: 5px;">Exam Name: <br></th>
					<td style="padding-Bottom: 5px;"><input id="Rname" name="Rname"
						class="form-control input-sm" type="text" placeholder="Exam Name" required></td>
				</tr>
				<tr>
					<th>Test Duration: <br></th>
					<td style="padding-Bottom: 5px;"><input id="Rduration" name="Rduration"
						class="form-control input-sm" type="text" placeholder="Duration" required></td>
				</tr>
				<tr>
					<th>Start Time: <br></th>
					<td style="padding-Bottom: 5px; overflow: hidden; text-align: left; valign: top; whitespace: nowrap;">
						<select name="Rsmon" class="form-control" style="width:100px">
							<c:forEach begin="1" end="12" var="val">
								<option> ${val}</option>
							</c:forEach>
						</select>
						/
						<select name="Rsday" class="form-control" style="width:100px">
							<c:forEach begin="1" end="31" var="val">
								<option> ${val}</option>
							</c:forEach>
						</select>
						
						<select name="Rstime" class="form-control" style="width:100px">
							<c:forEach begin="0" end="23" var="val">
								<option> ${val}:00</option>
								<option> ${val}:15</option>
								<option> ${val}:30</option>
								<option> ${val}:45</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>End Date: <br></th>
					<td style="padding-Bottom: 5px;">
					<select name="Remon" class="form-control" style="width:100px">
							<c:forEach begin="1" end="12" var="val">
								<option> ${val}</option>
							</c:forEach>
						</select>
						/
						<select name="Reday" class="form-control" style="width:100px">
							<c:forEach begin="1" end="31" var="val">
								<option> ${val}</option>
							</c:forEach>
						</select>
						
						<p>&emsp;</p>
						<select name="Retime" class="form-control" style="width:100px">
							<c:forEach begin="0" end="23" var="val">
								<option> ${val}:00</option>
								<option> ${val}:15</option>
								<option> ${val}:30</option>
								<option> ${val}:45</option>
							</c:forEach>
						</select>			
					</td>
				</tr>
				<tr>
					<td><c:set var="action" value="newRequest" scope="session" /></td>
					<td><input type="submit" value="Submit Request"
						class="btn btn-lg btn-dark"
						style="background: #980100; color: #FFF;"></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- /.container-fluid -->
	<script>
		
	</script>
	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

</body>

</html>
