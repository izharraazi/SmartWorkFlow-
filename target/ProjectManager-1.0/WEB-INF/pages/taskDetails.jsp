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
	<form:form class="form-horizontal" method="post" modelAttribute="taskDetails" id="main-form" action="/"> 
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
						Task Details
					</h3>
					
					<div>
						
						<div id="error" style="color: red"></div>
						
						<%-- <div>
							<b>Task Id :</b><span id="Id_taskId" >${taskDetails.taskId}</span>
						</div> --%>
						
						<div>
							<b>Project title :</b> ${taskDetails.project.title} 
							<b>Project State : </b> <span class="badge">${taskDetails.project.state}</span>
						</div>
						
						<div>
							<b>Task title :</b> ${taskDetails.title}
						</div>
						
						<div>
							<b>Description :</b> ${taskDetails.desc} 	
						</div>
						
						<div id="idUserDivOld">
							<b>Assigned To :</b> ${taskDetails.assignedUser.name} 	
						</div>
						
						<div id="idUserDiv">
							<b>Select Assignee</b> : 
							<select id="IdListUsers">
										    <c:forEach var="item" items="${taskDetails.listUsers}">
										        <option value="${item.userId}">${item.name}</option>
										    </c:forEach>
							</select>
						</div>
						
						<div>
							<b>Task state</b> : <span class="badge"><span id="Id_state">${taskDetails.state}</span></span>
							<c:set var="statePost" value="${taskDetails.state}" />
						</div>
						
						<div>
							<b>Expected</b> : ${taskDetails.expected}
						</div>
						
						<div>
							<b>Actual</b> : 
							<span id="idActualOld"> ${taskDetails.actual} </span> 
							<span id="idActual"> <input type="text" id="idActualValue" name="actualHours"> </span>
						</div>
						
						
						
						
						
							<div id="idBtns" class="form-actions">
								
								<button class="btn" id="backBtn" >Back</button>
								
								<c:if test="${taskDetails.state ne 'Finished' && taskDetails.state ne 'Cancelled'}">
								
									<c:if test="${ taskDetails.assignedUser.userId == loggedInUserId && taskDetails.state == 'Assigned' && taskDetails.project.state == 'Ongoing' }">
										<button class="btn btn-primary" id="startTaskBtn" >Start Task</button>
									</c:if>	
									
									<c:if test="${ taskDetails.assignedUser.userId == loggedInUserId && taskDetails.state == 'Started' && taskDetails.project.state == 'Ongoing' }">
										<button class="btn btn-primary" id="finishTaskBtn" >Finish Task</button>
									</c:if>	
									
									<c:if test="${taskDetails.ownerOfProject == 'Y' && taskDetails.project.state == 'Ongoing' }">
										<button class="btn btn-primary" id="cancelTaskBtn" >Cancel Task</button>
									</c:if>
									
									<c:if test="${ (taskDetails.assignedUser.userId == loggedInUserId || taskDetails.ownerOfProject == 'Y' )&& taskDetails.project.state == 'Planning' }">
										<button class="btn btn-primary" id="deleteTaskBtn" >Delete Task</button>
									</c:if>
									
									<c:if test="${taskDetails.project.state == 'Ongoing' || taskDetails.project.state == 'Planning' }">
										<button class="btn btn-primary" id="changeAssigneeBtn" >Change Assignee</button>
									</c:if>
								
								</c:if>
									
								
							</div>
							
							<div id="idCurrent" class="form-actions">
								
								<button class="btn btn-primary" id="idProceedBtn" >Proceed</button>
								<button class="btn" id="idCancelBtn" >Cancel</button>
								
							</div>
							
							<div id="idCurrentChange" class="form-actions">
								
								<button class="btn btn-primary" id="idProceedChangeBtn" >Proceed</button>
								<button class="btn" id="idCancelChangeBtn" >Cancel</button>
								
							</div>
						
					</div>
				</div>	
					
				</div>	
					
					

			
		<script>
		
		$("#main-form").validate({
			   rules: {
				   actualHours: {
					   required: true,
					   number: true
				   }
			   },
			   message: {
				   number:"Please enter a valid integer."
			   }
		});
			
		$(document).ready(function(){
			$('#idActual').hide();
			$('#idCurrent').hide();
			$('#idCurrentChange').hide();
			$('#idUserDiv').hide();
		});
			
		//For Back button handling
		$("#backBtn").click(function(event) {
			event.preventDefault();
			var a = "${pageContext.request.contextPath}/project/${taskDetails.project.projectId}";
            window.location.replace(a);
		});	
		
		
			// Start task
			$("#startTaskBtn").click(function(event) {
			
			    event.preventDefault();
				var url = "${pageContext.request.contextPath}/task/${taskDetails.taskId}/${loggedInUserId}";
			
			    // Send the data using post
			    var posting = $.post(url);
			
			    // Put the results in a div
			    posting.complete(function(data) {
			        if (data.status == 200) {
			            var a = "${pageContext.request.contextPath}/task/${taskDetails.taskId}";
			            window.location.replace(a);
			        } else {
			            $("#error").text(data.responseText);
			        }
				});
			});
			
			// ------------------- Start: FINISH task --------------------------
			// on Finsh button pressed
			$("#finishTaskBtn").click(function(event) {
				event.preventDefault();
				$('#idActualOld').hide();
				$('#idActual').show();
				$('#idBtns').hide();
				$('#idCurrent').show();
			});
			
			// on Finshed button pressed and Cancelled 
			$("#idCancelBtn").click(function(event) {
				event.preventDefault();
				$('#idActualOld').show();
				$('#idActual').hide();
				$('#idActual').val("");
				$('#idBtns').show();
				$('#idCurrent').hide();
			});
			
			// on Finish button pressed and Continued
			$("#idProceedBtn").click(function(event) {
				
			    event.preventDefault();
				
			    if( $("#main-form").valid() ){
			    	
			    	var url = "${pageContext.request.contextPath}/task/${taskDetails.taskId}/${loggedInUserId}";
					
				    // Send the data using post
				    var posting = $.post(url, {
				    	actual: $('#idActualValue').val()
				    });
				
				    // Put the results in a div
				    posting.complete(function(data) {
				        if (data.status == 200) {
				            var a = "${pageContext.request.contextPath}/task/${taskDetails.taskId}";
				            window.location.replace(a);
				        } else {
				            $("#error").text(data.responseText);
				        }
					});
			    }
			    
			});
			
			// ------------------- End: FINISH task --------------------------
			
			// on Cancel button pressed
			$("#cancelTaskBtn").click(function(event) {
				
			    event.preventDefault();
				var url = "${pageContext.request.contextPath}/task/delete/${loggedInUserId}/${taskDetails.taskId}/";
			
			    // Send the data using post
			    var posting = $.post(url);
			
			    // Put the results in a div
			    posting.complete(function(data) {
			        if (data.status == 200) {
			            var a = "${pageContext.request.contextPath}/task/${taskDetails.taskId}";
			            window.location.replace(a);
			        } else {
			            $("#error").text(data.responseText);
			        }
				});
			});
			
			// on Delete button pressed
			$("#deleteTaskBtn").click(function(event) {
				
			    event.preventDefault();
				var url = "${pageContext.request.contextPath}/task/delete/${loggedInUserId}/${taskDetails.taskId}/";
			
			    // Send the data using post
			    var posting = $.post(url);
			
			    // Put the results in a div
			    posting.complete(function(data) {
			        if (data.status == 200) {
			            var a = "${pageContext.request.contextPath}/project/${taskDetails.project.projectId}";
			            window.location.replace(a);
			        } else {
			            $("#error").text(data.responseText);
			        }
				});
			});
			
			
			// ------------------- Start: CHANGE ASSIGNEE task --------------------------
			// on Change Assignee button pressed
			$("#changeAssigneeBtn").click(function(event) {
				event.preventDefault();
				$('#idUserDivOld').hide();
				$('#idUserDiv').show();
				$('#idBtns').hide();
				$('#idCurrentChange').show();
				$('#idCurrent').hide();
			});
			
			// on Change Assignee  pressed and Cancelled 
			$("#idCancelChangeBtn").click(function(event) {
				event.preventDefault();
				$('#idUserDivOld').show();
				$('#idUserDiv').hide();
				$('#idBtns').show();
				$('#idCurrent').hide();
				$('#idCurrentChange').hide();
			});
			
			// on Change Assignee pressed and Continued
			$("#idProceedChangeBtn").click(function(event) {
				
			    event.preventDefault();
			    var a = $('#IdListUsers :selected').val();
				var url = "${pageContext.request.contextPath}/task/change/${taskDetails.taskId}/" + a;
			
			    // Send the data using post
			    var posting = $.post(url);
			
			    // Put the results in a div
			    posting.complete(function(data) {
			        if (data.status == 200) {
			            var a = "${pageContext.request.contextPath}/task/${taskDetails.taskId}";
			            window.location.replace(a);
			        } else {
			            $("#error").text(data.responseText);
			        }
				});
			});
			
			// ------------------- End: FINISH task --------------------------
			
		</script>
		
		</form:form>
</body>
</html>