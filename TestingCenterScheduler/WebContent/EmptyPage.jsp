<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/core.css" rel="stylesheet">

</head>

<body>

	<div id="wrapper">
		<form action="AdministratorHome" method="POST">
		<input type="hidden" name="RID" value="${param.requestID}">
		<input type="submit" name = "UtilAction" value="with" class="btn btn-default" style="background: #980100; color: #FFF;">
		<input type="submit" name = "UtilAction" value="without" class="btn btn-default" style="background: #980100; color: #FFF;">
		</form>
		<c:choose>
					<c:when test ="${returnVal == 'Start date is after end date!'}">
						<div class="row">
							<div class="col-lg-12">
								<div class="alert alert-danger">
									${returnVal}
								</div>
							</div>
						</div>
					</c:when>
					<c:when test="${not empty returnVal}">
						<div class="row">
							<div class="col-lg-12">
								<c:forEach items="${returnVal}" var="uti"> 
									<li>${uti}</li>
								</c:forEach>
							</div>
						</div>
					</c:when>
					 <c:otherwise>
				     </c:otherwise>
				</c:choose>
				<c:remove var="returnVal" scope="session" />
	</div>
	<!-- /#wrapper -->
		
	<script type="text/javascript">
	
	</script>
	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

</body>

</html>
