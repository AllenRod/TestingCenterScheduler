<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
				<a class="navbar-brand" href="Instructor.jsp" style="color: #fff;">Testing
					Center</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">${user.firstName} ${user.lastName}<b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"> Settings</a></li>
						<li class="divider"></li>
						<li><a>
								<form action="Login" method="GET">
									<input type="submit" value="Log Out"
										style="background-color: Transparent; border: none;">
								</form>
						</a></li>
					</ul></li>
			</ul>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li><a href="Instructor.jsp" style="color: #fff;">Overview</a>
					</li>
					<li><a href="Requests.jsp" style="color: #fff;"> View
							Requests</a></li>
					<li><a href="Exams.jsp" style="color: #fff;"> View
							Scheduled Exams</a></li>
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
                            Edit Request
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <a href="Instructor.jsp"> Home</a> > <a href="Requests.jsp"> Requests</a> > Edit Request 
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
                <c:set var="isADHOC" value="false" />
					<c:forEach var="requests" items="${nrequests}">
					  <c:if test="${requests.examIndex eq param.RequestID}">
					    <c:set var="isADHOC" value="true" />
					  </c:if>
					</c:forEach>
                
                <c:if test="${isADHOC eq true}">
					<c:forEach var="requests" items="${nrequests}">
					  <c:if test="${requests.examIndex eq param.RequestID}">
					    <c:set var="request" value="${requests}" scope="page"/>
					  </c:if>
					</c:forEach>
				</c:if>
                
                <c:if test="${isADHOC eq false}">
					<c:forEach var="requests" items="${crequests}">
					  <c:if test="${requests.examIndex eq param.RequestID}">
					    <c:set var="request" value="${requests}" scope="page"/>
					  </c:if>
					</c:forEach>
				</c:if>
				
                <c:set var="dateTimeParts" value="${fn:split(request.timeStart, ' ')}" />
                <c:set var="smonth" value="${dateTimeParts[1]}" />
                <c:set var="sday" value="${dateTimeParts[2]}" />
                
                <c:choose>             
	                <c:when test="${smonth eq 'Jan'}">
	                	<c:set var="smonth" value="1" />
	                </c:when>
	                <c:when test="${smonth eq 'Feb'}">
	                	<c:set var="smonth" value="2" />
	                </c:when>
	                <c:when test="${smonth eq 'Mar'}">
	                	<c:set var="smonth" value="3" />
	                </c:when>
	                <c:when test="${smonth eq 'Apr'}">
	                	<c:set var="smonth" value="4" />
	                </c:when>
	                <c:when test="${smonth eq 'May'}">
	                	<c:set var="smonth" value="5" />
	                </c:when>
	                <c:when test="${smonth eq 'Jun'}">
	                	<c:set var="smonth" value="6" />
	                </c:when>
	                <c:when test="${smonth eq 'Jul'}">
	                	<c:set var="smonth" value="7" />
	                </c:when>
	                <c:when test="${smonth eq 'Aug'}">
	                	<c:set var="smonth" value="8" />
	                </c:when>
	                <c:when test="${smonth eq 'Sep'}">
	                	<c:set var="smonth" value="9" />
	                </c:when>
	                <c:when test="${smonth eq 'Oct'}">
	                	<c:set var="smonth" value="10" />
	                </c:when>
	                <c:when test="${smonth eq 'Nov'}">
	                	<c:set var="smonth" value="11" />
	                </c:when>
	                <c:when test="${smonth eq 'Dec'}">
	                	<c:set var="smonth" value="12" />
	                </c:when>
	            </c:choose>
                
				<c:set var="time" value="${dateTimeParts[3]}" />
                <c:set var="timeParts" value="${fn:split(time, ':')}" />
                <c:set var="shour" value="${timeParts[0]}" />
                <c:set var="smin" value="${timeParts[1]}" />
                
                <c:set var="dateTimeParts" value="${fn:split(request.timeEnd, ' ')}" />
                <c:set var="emonth" value="${dateTimeParts[1]}" />
                <c:set var="eday" value="${dateTimeParts[2]}" />
                
                <c:choose>             
	                <c:when test="${emonth eq 'Jan'}">
	                	<c:set var="emonth" value="1" />
	                </c:when>
	                <c:when test="${emonth eq 'Feb'}">
	                	<c:set var="emonth" value="2" />
	                </c:when>
	                <c:when test="${emonth eq 'Mar'}">
	                	<c:set var="emonth" value="3" />
	                </c:when>
	                <c:when test="${emonth eq 'Apr'}">
	                	<c:set var="emonth" value="4" />
	                </c:when>
	                <c:when test="${emonth eq 'May'}">
	                	<c:set var="emonth" value="5" />
	                </c:when>
	                <c:when test="${emonth eq 'Jun'}">
	                	<c:set var="emonth" value="6" />
	                </c:when>
	                <c:when test="${emonth eq 'Jul'}">
	                	<c:set var="emonth" value="7" />
	                </c:when>
	                <c:when test="${emonth eq 'Aug'}">
	                	<c:set var="emonth" value="8" />
	                </c:when>
	                <c:when test="${emonth eq 'Sep'}">
	                	<c:set var="emonth" value="9" />
	                </c:when>
	                <c:when test="${emonth eq 'Oct'}">
	                	<c:set var="emonth" value="10" />
	                </c:when>
	                <c:when test="${emonth eq 'Nov'}">
	                	<c:set var="emonth" value="11" />
	                </c:when>
	                <c:when test="${emonth eq 'Dec'}">
	                	<c:set var="emonth" value="12" />
	                </c:when>
	            </c:choose>
                
				<c:set var="time" value="${dateTimeParts[3]}" />
                <c:set var="timeParts" value="${fn:split(time, ':')}" />
                <c:set var="ehour" value="${timeParts[0]}" />
                <c:set var="emin" value="${timeParts[1]}" />
                
                <c:if test="${isADHOC eq true}">
                	<c:set var="studentList" value="${fn:split(request.rosterList,' ')}"/>
                </c:if>
                
				<div class="div-spacing">
					<form class="form-inline" action="InstructorHome" method="POST">
						<div class="form-group">
							<label for="Rtype">Request Type:</label> <select
								class="form-control" id="Rtype" name="Rtype" disabled>
								<c:if test="${isADHOC eq true}">
									<option value="AD_HOC" selected>AD_HOC</option>
								</c:if>
								<c:if test="${isADHOC eq false}">
					    			<option value="CLASS" selected>CLASS</option>
					  			</c:if>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Rclass">ClassID: </label> <select
								class="form-control" id="Rclass" name="Rclass" disabled>
								<c:if test="${isADHOC eq true}">
									<option selected>Non-Class Exam</option>
								</c:if>
								<c:if test="${isADHOC eq false}">
					    			<option value="${request.course.classID}" selected>${request.course.classID}</option>
					  			</c:if>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Rterm">Term: </label> <select
								class="form-control input-sm" id="Rterm" name="Rterm" disabled>
								<c:if test="${isADHOC eq true}">
									<option selected>No Term</option>
								</c:if>
								<c:if test="${isADHOC eq false}">
					    			<option value="${request.term.termID}" selected>${request.term.termID}</option>
					  			</c:if>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Rname">Exam Name: </label> <input type="text"
								class="form-control input-sm" id="Rname" name="Rname"
								placeholder="Exam Name" value = "${request.examName}">
						</div>
						<br>
						<div class="form-group">
							<label for="Rduration">Test Duration: </label> <input
								type="number" class="form-control input-sm" id="Rduration"
								name="Rduration" min="1" placeholder="Duration" value = "${request.testDuration}" required>
						</div>
						<br>
						<div class="form-group">
							<label for="Rsmon">Start Time: </label> <input type="number"
								class="form-control input-sm" id="Rsmon" name="Rsmon" min="1"
								max="12" placeholder="Month" style="width: 100px;" value="${smonth}" required>

							<input type="number" class="form-control input-sm" id="Rsday"
								name="Rsday" min="1" max="31" placeholder="Day"
								style="width: 100px;" value="${sday}" required>

							<select id="Rstime" name="Rstime" class="form-control input-sm"
								style="width: 100px">
								<c:forEach begin="0" end="23" var="val">
						             <c:choose>             
						                <c:when test="${val eq shour}">
						                	<c:if test="${smin eq '30'}">
						                		<option>${val}:00</option>
												<option selected>${val}:30</option>
						                	</c:if>
						                	<c:if test="${smin eq '00'}">
						                		<option selected>${val}:00</option>
												<option>${val}:30</option>
						                	</c:if>
						                </c:when>
						                <c:otherwise>
						                	<option>${val}:00</option>
											<option>${val}:30</option>
						                </c:otherwise>
						            </c:choose>
								</c:forEach>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Remon">End Time: </label> <input type="number"
								class="form-control input-sm" id="Remon" name="Remon" min="1"
								max="12" placeholder="Month" style="width: 100px;" value="${emonth}"required>
								
							<input type="number" class="form-control input-sm" id="Reday"
								name="Reday" min="1" max="31" placeholder="Day"
								style="width: 100px;" value="${eday}"required>

							</select> <select name="Retime" class="form-control input-sm" style="width: 100px">
								<c:forEach begin="0" end="23" var="val">
									<c:choose>             
						                <c:when test="${val eq ehour}">
						                	<c:if test="${emin eq '30'}">
						                		<option>${val}:00</option>
												<option selected>${val}:30</option>
						                	</c:if>
						                	<c:if test="${emin eq '00'}">
						                		<option selected>${val}:00</option>
												<option>${val}:30</option>
						                	</c:if>
						                </c:when>
						                <c:otherwise>
						                	<option>${val}:00</option>
											<option>${val}:30</option>
						                </c:otherwise>
						            </c:choose>
								</c:forEach>
							</select>
						</div>
						<br>
						<div class="form-group">
							<label for="Rlist">Student List (non-Stony Brook course
								exam Only):</label>
							<br>
							<c:if test="${isADHOC eq true}">
									<textarea id="Rlist" name="Rlist" class="form-control" rows="3"> 
									<c:forEach var="student" items="${studentList}">
									<c:out value="${student}"/>
									</c:forEach>
									</textarea>
							</c:if>
							<c:if test="${isADHOC eq false}">
					    			<textarea id="Rlist" name="Rlist" class="form-control" rows="3"
								disabled></textarea>
					  		</c:if>
						</div>
						<br>
						<c:set var="action" value="editRequest" scope="session" />
						<a href="Requests.jsp" class="btn btn-default"
							style="background: #DDD; color: #980100;">Cancel</a>
						<c:if test="${request.status ne 'pending'}">
						<input type="submit" value="This request cannot be changed"
							class="btn btn-default"
							style="background: #980100; color: #FFF;" disabled>
						</c:if>
						<c:if test="${request.status eq 'pending'}">
						<input type="submit" value="Edit"
							class="btn btn-default"
							style="background: #980100; color: #FFF;">
						</c:if>
					</form>
				</div>
			</div>
		</div>
	</div>
				
	<!-- /.container-fluid -->

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

	<script>

	</script>
</body>

</html>
