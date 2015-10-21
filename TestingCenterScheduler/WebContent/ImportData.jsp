<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Import .csv files</title>
</head>
<body>
<form action="LoadCSV" method="POST">
<p>
Is this for a roster, user, or class?<br>
<input type="text" name="table" size="30">
</p>
<p>
Choose your .csv file <br>
<input type="file" name="fileName" size="50">
</p>
<div>
<input type="submit" value="Send">
</div>
</form>
</body>
</html>