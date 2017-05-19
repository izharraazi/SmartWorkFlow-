<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<!--[if lt IE 7 ]><html lang="en" class="ie6 ielt7 ielt8 ielt9"><![endif]--><!--[if IE 7 ]><html lang="en" class="ie7 ielt8 ielt9"><![endif]--><!--[if IE 8 ]><html lang="en" class="ie8 ielt9"><![endif]--><!--[if IE 9 ]><html lang="en" class="ie9"> <![endif]--><!--[if (gt IE 9)|!(IE)]><!--> 
<html lang="en"><!--<![endif]--> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

		<title>Projects - Smart-WorkFlow</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
			
		<spring:url value="/resources/css/bootstrap.min.css" var="boot" />
		<link href="${boot}" rel="stylesheet" />
		
		<spring:url value="/resources/css/bootstrap-responsive.min.css" var="bootResp" />
		<link href="${bootResp}" rel="stylesheet" />
		
		<spring:url value="/resources/css/site.css" var="site" />
		<link href="${site}" rel="stylesheet" />
		
		<c:set var="context" value="${pageContext.request.contextPath}" />
		
		<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
	<%--<form:form class="form-horizontal" method="post"  modelAttribute="inviteUsers" action="${context}/user/invite">--%>
	<div class="container">
			<div class="navbar">
				<div class="navbar-inner">
					<div class="container">
						<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </a> <a class="brand" href="#">Smart-WorkFlow</a>
						<div class="nav-collapse">
							<!--<ul class="nav"> 
								
								</ul> -->
							
							<%--<ul class="nav pull-right">--%>
								<%--<li>--%>
									<%--<c:set var="loggedInUserId" value="{dashboard.userid}" />--%>
									<%--<a id="dashboardLink" href="${context}/dashboard">{dashboard.name}</a>--%>
								<%--</li>--%>
								<%--<li>--%>
									<%--<a href="login.html">Logout</a>--%>
								<%--</li>--%>
							<%--</ul>--%>
						</div>
					</div>
				</div>
			</div>
			 <div class="span9">
					<%-- <h1>
						Dashboard
					</h1>
						<div class="well summary">
						<ul>
							<li>
							<c:set var="pro" value="${dashboard.projects}" />
								<a href="#"><span class="count">${fn:length(pro)}</span> Projects</a>
							</li>
							<li>
								<a href="#"><span class="count">27</span> Tasks</a>
							</li>
						<!-- 	<li>
								<a href="#"><span class="count">7</span> Messages</a>
							</li>
							<li class="last">
								<a href="#"><span class="count">5</span> Files</a>
							</li> -->
						</ul>
					</div> --%>
			
			
			<div class="row">
				<div class="span1">
			<!-- 		<div class="well" style="padding: 8px 0;">
						<ul class="nav nav-list">
					
						</ul>
					</div>
			 -->	</div>
			 
				
				<div class="span9">
					<h1>
						Invite Users
					</h1>
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>
									User Name
								</th>
								<th>
									User Email
								</th>
							</tr>
						</thead>
						<tbody>
							
						<c:forEach items="${inviteUsers.listUsers}" var="item">
    						
						
							<tr>
								<td>
									${item.name}
								</td>
								<td>
									${item.email}
								</td>
								<
								<td>
									<a href="${context}/user/invite/email/${item.email}">
										<button>Invite User</button>
									</a>
								</td>
							</tr>
						</c:forEach>	
						</tbody>
					</table>
					<%----%>
					<%--<div class="form-actions">--%>
						<%--<button type="submit" class="btn btn-primary" >Invite</button>--%>
					<%--</div>--%>
					
					<%--</form:form>	--%>
					
				</div>
			</div>
		</div>
		
		<spring:url value="/resources/js/jquery.min.js" var="jqueryMin" />
		<spring:url value="/resources/js/bootstrap.min.js" var="bootJs" />
		<spring:url value="/resources/js/site.js" var="siteJs" />
		
		<script src="${jqueryMin}"></script>
		<script src="${bootJs}"></script>
		<script src="${siteJs}"></script>
		
		<script>
		
		
		
		// Attach a submit handler to the form
		$( "#cancelBtn" ).click(function( event ) {
			event.preventDefault();
			$( "#error" ).hide();
			$(".toggle-link").click();
		});
		$( "#new-project" ).submit(function( event ) {
		 
		  // Stop form from submitting normally
		  event.preventDefault();
		 
		  var url = "${pageContext.request.contextPath}/project/${loggedInUserId}";

		  alert(url + " -- " + $("#new_title").val() + " -- " + $("#new_desc").val() );
		  
		// Send the data using post
		  var posting = $.post( url,  { title: $("#new_title").val(), description : $("#new_desc").val() } );
		 
		  // Put the results in a div
		  posting.complete(function( data ) {
		    
			  
			  if( data.status == 200 ){
				  location.reload();
			  }else{
				  $( "#error" ).text("Can't add project.");  
			  }
			  	
		    	
		  });
		});


		</script>
	
</body>
</html>