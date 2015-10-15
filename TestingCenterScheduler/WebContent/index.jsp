<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Stony Brook Testing Center</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/testingCenter.css" rel="stylesheet">

<link
	href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<style>
</style>
</head>

<body>

	<!-- Navigation -->
	<a id="menu-toggle" href="#" class="btn btn-dark btn-lg toggle"><i
		class="fa fa-bars"></i></a>
	<nav id="sidebar-wrapper">
		<ul class="sidebar-nav">
			<a id="menu-close" href="#"
				class="btn btn-light btn-lg pull-right toggle"><i
				class="fa fa-times"></i></a>
			<li class="sidebar-brand"><a href="#top" onclick=$("#menu-close").click(); >Testing
					Center</a></li>
			<li><a href="#top" onclick=$("#menu-close").click(); >Home</a></li>
			<li><a href="#logo" onclick=$("#menu-close").click(); >Sign
					in</a></li>
			<li><a href="#service" onclick=$("#menu-close").click(); >Hours</a>
			</li>
			<li><a href="#contact" onclick=$("#menu-close").click(); >Contact</a>
			</li>
		</ul>
	</nav>

	<!-- Header -->
	<header id="top" class="header">
		<div class="text-vertical-center">
			<h1>Stony Brook Testing Center</h1>
			<h3>Beta</h3>
			<br> <a href="#logo" class="btn btn-dark btn-lg">Sign in</a>
		</div>
	</header>

	<!-- Callout -->
	<aside class="callout" id="logo"></aside>

	<!-- About -->
	<section class="about">
		<div class="container">
			<%
			if(request.getAttribute("error") != null){
			%>
				<div class="row">
					<div class="col-lg-12">
						<div class="alert alert-danger">
							<%=((String)request.getAttribute("error")) %>
						</div>
					</div>
				</div>
			<%
			request.removeAttribute("error");
			}%>
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2>Sign in</h2>
					<div id="form">
						<form action="Login" method="POST">
							<table>
								<tr>
									<th>NetID:</th>
									<td><input type="text" id="user" name="user" size="16"
										style="width: 250px; margin-bottom: 15px; margin-left: 10px;" /></td>
									<td id="userText"></td>
								</tr>
								<tr>
									<th>Password: <br></th>
									<td><input type="password" id="password" name="password"
										size="16"
										style="width: 250px; margin-bottom: 15px; margin-left: 10px;" /></td>
									<td id="pswText" class="tdProp"></td>
								</tr>
								<tr>
									<td><a href="index.html"
										style="font-size: 12px; color: blue;">Forgot password?</a></td>
								</tr>
								<tr>
									<td></td>
									<!--<td rowspan = "2"><input type="submit" value = "Sign In" class="btn btn-lg btn-dark" style="background: #980100" /></td>-->
									<td><input type="submit" value="Sign In"
										class="btn btn-lg btn-dark" style="background: #980100"></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
			<!-- /.row -->
		</div>
		<!-- /.container -->
	</section>

	<!-- Services -->
	<!-- The circle icons use Font Awesome's stacked icon classes. For more information, visit http://fontawesome.io/examples/ -->
	<section id="service" class="services bg-primary">
		<div class="container">
			<div class="row text-center">
				<div class="col-lg-10 col-lg-offset-1">
					<h2>Testing Hours Fall 2015</h2>
					<hr class="small">
					<div id="form2">
						<table style="border-collapse: separate; border-spacing: 10px;">
							<tr>
								<th>Monday - Thursday:</th>
								<td>8:00am - 6:00pm</td>
							</tr>
							<tr>
								<th>Friday:</th>
								<td>8:00am - 4:00pm</td>
							</tr>
							<tr>
								<th>Saturday:</th>
								<td>Closed</td>
							</tr>
							<tr>
								<th>Sunday:</th>
								<td>Closed</td>
							</tr>
						</table>
					</div>
					<!-- /.row (nested) -->
				</div>
				<!-- /.col-lg-10 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.container -->
	</section>

	<!-- Call to Action -->
	<section id="contact" class="contact">
		<aside class="call-to-action bg-primary">
			<div class="container">
				<div class="row">
					<div class="col-lg-12 text-center">
						<h3>Find out more</h3>
						<a href="http://www.stonybrook.edu/sb/calendars.shtml"
							class="btn btn-lg btn-light"> University Calendars </a> <a
							href="http://www.stonybrook.edu/commcms/academic_success/students/study_spaces/frey-study.html"
							class="btn btn-lg btn-dark" style="background: #980100"> Frey
							Hall </a>
					</div>
				</div>
			</div>
		</aside>
	</section>
	<!-- Footer -->
	<footer>
		<div class="container">
			<div class="row">
				<div class="col-lg-10 col-lg-offset-1 text-center">
					<p>
						Division of Information Technology<br>Stony Brook University
					</p>
					<ul class="list-unstyled">
						<li><i class="fa fa-phone fa-fw"></i> (631) 632-9800</li>
						<li><i class="fa fa-envelope-o fa-fw"></i> <a
							href="mailto:service.stonybrook.edu">service.stonybrook.edu</a></li>
					</ul>
					<br>
					<hr class="small">
					<p class="text-muted">Copyright &copy; Team Five 2015</p>
				</div>
			</div>
		</div>
	</footer>

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script>
    // Closes the sidebar menu
    $("#menu-close").click(function(e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    // Opens the sidebar menu
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    // Scrolls to the selected menu item on the page
    $(function() {
        $('a[href*=#]:not([href=#])').click(function() {
            if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') || location.hostname == this.hostname) {

                var target = $(this.hash);
                target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
                if (target.length) {
                    $('html,body').animate({
                        scrollTop: target.offset().top
                    }, 1000);
                    return false;
                }
            }
        });
    });
    </script>

</body>

</html>
