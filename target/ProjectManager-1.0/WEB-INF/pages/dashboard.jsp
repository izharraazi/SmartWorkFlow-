<%@page import="com.sjsu.project.vo.UserVo"%>
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
		
		<spring:url value="/resources/js/jquery.min.js" var="jqueryMin" />
		<spring:url value="/resources/js/bootstrap.min.js" var="bootJs" />
		<spring:url value="/resources/js/site.js" var="siteJs" />
		<spring:url value="/resources/js/jquery.validate.min.js" var="jqueryValidateMin" />
		<script src="${jqueryMin}"></script>
		<script src="${bootJs}"></script>
		<script src="${siteJs}"></script>
		<script src="${jqueryValidateMin}"></script>
		
		<c:set var="context" value="${pageContext.request.contextPath}" />
		<%UserVo userVo = (UserVo) session.getAttribute("User");
		    String userName = userVo.getUserName();
		    long userId = userVo.getUserId();
		%>
		
		<c:set var="loggedInUserId" value="<%=userId%>" />
		<c:set var="loggedInUserName" value="<%=userName%>" />
		<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
		
		<style type="text/css">
			select.error, textarea.error, input.error, label.error {
    			color:#FF0000;
			}
		</style>
</head>
<body>
	<form:form class="form-horizontal" method="post" modelAttribute="dashboard" action="/"> 
	
	<div class="container">
			<div class="navbar">
				<div class="navbar-inner">
					<div class="container">
						<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </a> <a class="brand" href="#">Smart-WorkFlow</a>
						<div class="nav-collapse">
							
							
							<ul class="nav pull-left">
								<li>
									<a id="dashboardLink" href="${context}/dashboard">${dashboard.name}</a>
								</li>
								<li>
									<a href="login.html" href="${context}/login">Logout</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			
			
			<div class="span9">
					<h3>
						Dashboard
					</h3>
						<div class="well summary">
						<ul>
							<li>
							<c:set var="pro1" value="${dashboard.ownedProjects}" />
							<c:set var="pro2" value="${dashboard.participantProjects}" />
							<c:set var="tasks" value="${dashboard.listTasks}" />
								<a href="#projects"> Projects <span class="count">${fn:length(pro1) + fn:length(pro2) }</span> </a>
							</li>
							<li>
								<a href="#tasks"> Tasks <span class="count">${fn:length(tasks)}</span> </a>
							</li>
						</ul>
					</div>
			</div>
			
			<div class="span9">
					<h3>
						My Projects
					</h3>
					<table class="table table-bordered table-striped" id="projects">
						<thead>
							<tr>
								<th>
									Title
								</th>
								<th>
									Description
								</th>
								<th>
									Role
								</th>
								<th>
									State
								</th> 
								<th>
									Invite People
								</th>
							</tr>
						</thead>
						<tbody>
						
						<c:if test="${empty dashboard.ownedProjects || empty dashboard.participantProjects}">
   							<tr class="ownerRowClass" ></tr>
   							<tr class="ownerRowClass" ></tr>
						</c:if>	
							
						<c:forEach items="${dashboard.ownedProjects}" var="item">
    						
							<tr class="ownerRowClass">
								<td>
									<a href="${context}/project/${item.projectId}">  ${item.title} </a>
								</td>
								<td>
									${item.desc}
								</td>
								<td>
									Owner
								</td>
								<td>
									<span class="badge">${item.state}</span>
								</td>
								<td>
									<a href="${context}/user/invite/${item.projectId}">  Invite </a>
								</td>
							</tr>
						</c:forEach>	
						
						<c:forEach items="${dashboard.participantProjects}" var="item">
    						<tr>
								<td>
									<a href="${context}/project/${item.projectId}">  ${item.title} </a>
								</td>
								<td>
									${item.desc}
								</td>
								<td>
									Participant
								</td>
								<td>
									<span class="badge">${item.state}</span>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					</div>
					</div>
					
					</form:form>	
					
					<!--  Section to add Project -->
					<div class="container">
						<div class="span9">
							<a class="toggle-link" href="#new-project"><i class="icon-plus"></i> New Project</a>
							
							<div id="error" style="color: red"></div>
							
							<form id="new-project" class="form-horizontal hidden">
								<fieldset>
									<legend>New Project</legend>
									<div class="control-group">
										<label class="control-label" for="input01">Project Title</label>
										<div class="controls">
											<input type="text" class="input-xlarge" id="new_title" name="new_title"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="textarea">Project Description</label>
										<div class="controls">
											<textarea class="input-xlarge" id="new_desc" name="new_desc" rows="3"></textarea>
										</div>
									</div>
									<div class="form-actions">
										<button type="submit" class="btn btn-primary">Create</button> <button class="btn" id="cancelBtn" >Cancel</button>
									</div>
								</fieldset>
							</form>	
						</div>
					</div>
					
					<script>
					
					
						$("#new-project").validate({
						   rules: {
							   new_title: "required",
							   new_desc: "required"
						   }
						});
					
					
					// Create New Project AJAX
					// Attach a submit handler to the form
					$( "#cancelBtn" ).click(function( event ) {
						event.preventDefault();
						$( "#error" ).hide();
						$(".toggle-link").click();
					});
					
					$( "#new-project" ).submit(function( event ) {
					 
					  	// Stop form from submitting normally
						event.preventDefault();
						if( $("#new-project").valid() ){
							
							var url = "${pageContext.request.contextPath}/project/${loggedInUserId}";
							
						 	// Send the data using post
						 	var posting = $.post( url,  { title: $("#new_title").val(), description : $("#new_desc").val() } );
							
						 	var title = $("#new_title").val();
						 	var desc = $("#new_desc").val();
						 	
						 	// Put the results in a div
						  	posting.complete(function( data ) {
						    
							  if( data.status == 200 ){
								  
								  var newRow = '<tr class="ownerRowClass"><td><a href="${context}/project/'+data.responseText+'">'+title+'</a></td><td>'+desc+'</td><td>Owner</td>a<td><span class="badge">Planning</span></td><td><a href="${context}/user/invite/'+data.responseText+'">Invite</a></td></tr>';
								  $('.ownerRowClass:last').after(newRow);
								  $( "#cancelBtn" ).click();
								  
							  }else{
								  $( "#error" ).text(data.responseText);  
							  }
							});
						}
					 	
					});
					</script>
					
					<div class="container">
					<div class="span9">
					<h3>
						My Assigned Tasks
					</h3>
					<table class="table table-bordered table-striped" id="tasks">
						<thead>
							<tr>
								<th>
									Project Title
								</th>
								<th>
									Project State
								</th>
								<th>
									Task Title
								</th>
								<th>
									Description
								</th>
								<th>
									Task State
								</th>
								<th>
									Estimate
								</th> 
								<th>
									Actual
								</th>
							</tr>
						</thead>
						<tbody>
						
						<c:forEach items="${dashboard.listTasks}" var="item">
    						
							<tr>
								<td>
									<a href="${context}/project/${item.project.projectId}">  ${item.project.title} </a>
								</td>
								<td>
									<span class="badge">${item.project.state}</span>
								</td>
								<td>
									<a href="${context}/task/${item.taskId}">  ${item.title} </a>
								</td>
								<td>
									${item.desc}
								</td>
								<td>
									<span class="badge">${item.state}</span>
								</td>
								<td>
									${item.estimate}
								</td>
								<td>
									${item.actual}
								</td>
							</tr>
						</c:forEach>	
						
						</tbody>
					</table>
					</div>
					
				</div>	
			
</body>
</html>