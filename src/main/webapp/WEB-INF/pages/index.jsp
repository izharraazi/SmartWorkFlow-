<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<spring:url value="/resources/css/site.css" var="mainCss" />
	<link href="${mainCss}" rel="stylesheet" />
	
	<script type="text/javascript">
	
	function fnSubmit(){
		window.location = "localhost:8080/ProjectManager-1.0/dashboard";
	}
	</script>

</head>
<body>
<h1>Maven + Spring MVC Web Project Example</h1>
 
<h3>Message : </h3>
<h3>Counter : </h3>	
<button onclick="fnSubmit()"></button>
</body>
</html>
