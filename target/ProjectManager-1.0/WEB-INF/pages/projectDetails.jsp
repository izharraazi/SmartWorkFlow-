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
		
		
		<%
			UserVo userVo = (UserVo) session.getAttribute("User");
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
	<form:form class="form-horizontal" method="post" modelAttribute="projectDetails" action="/"> 
	<div class="container">
			<div class="navbar">
				<div class="navbar-inner">
					<div class="container">
						<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </a> <a class="brand" href="#">Smart-WorkFlow</a>
						<div class="nav-collapse">
							
							
							<ul class="nav pull-left">
								<li>
									<a id="dashboardLink" href="${context}/dashboard">${loggedInUserName}</a>
								</li>
								<li>
									<a href="login.html">Logout</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
					
			
			<div class="span9">
					<h3>
						Project Details
					</h3>
					
					<div>
						
						<div id="error" style="color: red"></div>
						
						<div>
							<b>Project Id :</b><span id="Id_projectId" >${projectDetails.projectId}</span>
						</div>
						
						
						
						<c:set var="projectIdPost" value="${projectDetails.projectId}" />
						
						<div>
							<b>Title :</b> ${projectDetails.title}
						</div>
						<div>
							<b>Owner :</b> 	<c:if test="${projectDetails.isOwner == 'Y'}">
											Yes
										</c:if>
										<c:if test="${projectDetails.isOwner != 'Y'}">
											No
										</c:if>
						</div>
						<div>
							<b>Description</b> : ${projectDetails.title}
						</div>
						<div>
							<b>State</b> : <span class="badge"><span id="Id_state">${projectDetails.state}</span></span>
							<c:set var="statePost" value="${projectDetails.state}" />
						</div>
						
						<form id="new-project" class="form-horizontal hidden">
						
							<div class="form-actions">
								
								<button class="btn" id="backBtn" >Back</button>
								
								<c:if test="${projectDetails.state == 'Planning' || projectDetails.state == 'Ongoing' }">
									<button class="btn btn-primary" id="move" >Move Project to Next State</button>
									<c:if test="${projectDetails.isOwner == 'Y'}">
										<button class="btn btn-primary" id="cancelBtn" >Cancel Project</button>	
									</c:if>
								</c:if>	 
								
							</div>
						
					</form>	
					
					</div>
				</div>	
					<div class="span9">
					
					<h3>
						Tasks
					</h3>
					
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>
									Title
								</th>
								<th>
									Description
								</th>
								<th>
									Assigned to
								</th>
								<th>
									State
								</th> 
								<!-- <th>
									Edit
								</th> -->
								<th>
									Expected
								</th>
								<th>
									Actual
								</th>
							</tr>
						</thead>
						<tbody>
						
						<c:if test="${empty projectDetails.listTasks}">
   							<tr class="ownerRowClass" ></tr>
   							<tr class="ownerRowClass" ></tr>
						</c:if>
							
						<c:forEach items="${projectDetails.listTasks}" var="item">
    						
							<tr class="ownerRowClass">
								<td>
									<a href="${context}/task/${item.taskId}">  ${item.title} </a>
								</td>
								<td>
									${item.desc}
								</td>
								<td>
									${item.assignee.name}
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
					

			
			<script>
			
			//For Back button handling
			$("#backBtn").click(function(event) {
				event.preventDefault();
				var a = "${pageContext.request.contextPath}/dashboard";
	            window.location.replace(a);
			});
			
			$("#move").click(function(event) {
			
			    event.preventDefault();
			
			    var url = "${pageContext.request.contextPath}/project/changeState";
			
			    // Send the data using post
			    var posting = $.post(url, {
			        userId: ${loggedInUserId},
			        projectId: ${projectIdPost},
			        state: "${statePost}"
			    });
			
			    // Put the results in a div
			    posting.complete(function(data) {
			        if (data.status == 200) {
			            var a = "${pageContext.request.contextPath}/project/${projectDetails.projectId}";
			            window.location.replace(a);
			        } else {
			            $("#error").text(data.responseText);
			            $("#error").show();
			        }
			
			
			    });
			});
			
			// When Project is Cancelled
			$("#cancelBtn").click(function(event) {
			
			    event.preventDefault();
			
			    var url = "${pageContext.request.contextPath}/project/changeState";
			
			    // Send the data using post
			    var posting = $.post(url, {
			        userId: ${loggedInUserId},
			        projectId: ${projectIdPost},
			        state: "Cancelled"
			    });
			
			    // Put the results in a div
			    posting.complete(function(data) {
			        if (data.status == 200) {
			            var a = "${pageContext.request.contextPath}/project/${projectDetails.projectId}";
			            window.location.replace(a);
			        } else {
			            $("#error").text(data.responseText);
			        }
			    });
			});			
			</script>
			
			<!--  Section to add new Task -->
					<div class="container">
						<div class="span9">
							<a class="toggle-link" href="#new-task"><i class="icon-plus"></i> New Task</a>
							
							<div id="error-task" style="color: red"></div>
							
							<form id="new-task" class="form-horizontal hidden">
								<fieldset>
									<legend>New Task</legend>
									<div class="control-group">
										<label class="control-label" for="input01">Task Title</label>
										<div class="controls">
											<input type="text" class="input-xlarge" id="new_title" name="new_title"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="textarea">Task Description</label>
										<div class="controls">
											<textarea class="input-xlarge" id="new_desc" name="new_desc" rows="3"></textarea>
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label" for="dropdown">Assigned To</label>
										<div class="controls">
										<select id="IdListUsers">
										    <c:forEach var="item" items="${projectDetails.listUsers}">
										        <option value="${item.userId}">${item.name}</option>
										    </c:forEach>
										</select>
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label" for="input02">Expected</label>
										<div class="controls">
											<input type="text" class="input-xlarge" id="new_expected" name="new_expected" />
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label" for="input03">Actual</label>
										<div class="controls">
											<input type="text" class="input-xlarge" id="new_actual" />
										</div>
									</div>
									
									<div class="form-actions">
										<button type="submit" class="btn btn-primary">Create</button> <button class="btn" id="cancelBtn-task" >Cancel</button>
									</div>
								</fieldset>
							</form>	
						</div>
					</div>
					
					<script>
					
					$("#new-task").validate({
					   rules: {
						   new_title: "required",
						   new_desc: "required",
						   new_expected: {
							   required : true,
							   number: true
						   }
					   },
						message: {
							new_expected: {
								   number: "Please enter valid integer."
							   }
						}
					});
					
					
					
					
					// Create New Task AJAX
					$( "#cancelBtn-task" ).click(function( event ) {
						event.preventDefault();
						$( "#error-task" ).hide();
						$("#new_title").val("");
					 	$("#new_desc").val("");
					 	$("#new_expected").val("");
					 	$("#new_actual").val("");
					 	 $("#error").hide();
						$(".toggle-link").click();
					});
					
					$( "#new-task" ).submit(function( event ) {
					 
					  	// Stop form from submitting normally
						event.preventDefault();
						$("#error").hide();
					  	console.log( $('#IdListUsers :selected').text() );
					  	console.log( $('#IdListUsers :selected').val() );
					  	
					  	if( $("#new-task").valid() ){
					  		
					  		var url = "${pageContext.request.contextPath}/task/${projectDetails.projectId}";
						 	
						 	// Send the data using post
						 	var posting = $.post( url,{ 
						 		title: $("#new_title").val(), 
						 		description : $("#new_desc").val(),
						 		userId : $('#IdListUsers :selected').val(),
						 		estimate: $("#new_expected").val(),
						 		actual:	$("#new_actual").val()
						 	});
							
						 	var title = $("#new_title").val();
						 	var desc = $("#new_desc").val();
						 	var state = "Assigned";
						 	var assignedTo = $('#IdListUsers :selected').text();
						 	var expected = $("#new_expected").val();
						 	var actual = $("#new_actual").val();
						 	
						 	// Put the results in a div
						  	posting.complete(function( data ) {
						    
							  if( data.status == 200 ){
								  
								  var newRow = '<tr class="ownerRowClass"><td><a href="${context}/task/'+data.responseText+'">'+title+'</a></td><td>'+desc+'</td><td>'+assignedTo+'</td><td><span class="badge">'+state+'</span></td><td>'+expected+'</td><td>'+actual+'</td></tr>';
								  
								  $('.ownerRowClass:last').after(newRow);
								  $( "#cancelBtn-task" ).click();
								  
							  }else{
								  
								  $( "#error-task" ).text(data.responseText);  
							  }
							});
					  	}
					  	
					});
					</script>
			</form:form>
</body>
</html>