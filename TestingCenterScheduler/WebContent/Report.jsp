<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Generate Reports For a Term</title>

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
			<c:if test="${role eq 'admin'}">
				<c:set var="cor" value="true"/>
			</c:if>
		</c:forEach>
		<c:if test="${cor eq 'false'}">
			<jsp:forward page="/index.jsp"></jsp:forward>
		</c:if>
	</c:if>
	
	<div id="page-wrapper">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
					<c:forEach items="${returnVal}" var="report"> 
						<li>${report}</li>
					</c:forEach>
				</div>
			</div>
			<c:remove var="returnVal" scope="session" />
		</div>
	</div>
</body>
</html>