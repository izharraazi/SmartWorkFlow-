<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%><!DOCTYPE html>
<html lang="en">
<style>
body {
    background-image: url("resources/assets/img/backgrounds/1.jpg");
    background-color: #cccccc;
}
.error {
    font-size:9px;
    color:red
}

.valid {
    
}
</style>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Smart Work flow</title>

        <!-- CSS -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link rel="stylesheet" href="resources/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/assets/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="resources/assets/css/form-elements.css">
        <link rel="stylesheet" href="resources/assets/css/style.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <!-- Favicon and touch icons -->
        <link rel="shortcut icon" href="resources/assets/ico/favicon.png">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="resources/assets/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="resources/assets/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="resources/assets/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="resources/assets/ico/apple-touch-icon-57-precomposed.png">
		
		<spring:url value="/resources/js/jquery.min.js" var="jqueryMin" />
		<spring:url value="/resources/js/bootstrap.min.js" var="bootJs" />
		<spring:url value="/resources/js/site.js" var="siteJs" />
		<spring:url value="/resources/js/jquery.validate.min.js" var="jqueryValidateMin" />
		<script src="${jqueryMin}"></script>
		<script src="${bootJs}"></script>
		<script src="${siteJs}"></script>
		<script src="${jqueryValidateMin}"></script>
		
		
		<style type="text/css">
			select.error, textarea.error, input.error, label.error {
    			color:#FF0000;
			}
		</style>
    </head>

    <body>

        <!-- Top content -->
        <div class="top-content">
        	<c:set var="context" value="${pageContext.request.contextPath}" />
            <div class="">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
						
                            <h1><strong>SmartWorkflow </strong> </h1>
                            <div class="description">
                            	<p>
                            	</p>
	                            	SmartWorkflow is an intuitive online project management tool enabling
											teams to increase productivity using cloud, collaboration.
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        	<div class="form-top">
                        		<div class="form-top-left">
                        		<h2></h2>
                        			<h3>Login</h3>
                        			<h4 align="center"><font color="green"><c:out value="${mesg}"/></font></h4>
                            		<p>Enter your Email and password to log on:</p>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-key"></i>
                        		</div>
                        		
                        		
                            </div>
                            <div class="form-bottom">
			                    <form id="mainForm" role="form" action="${context}/login" method="post" class="login-form">
			                    	<div class="form-group">
			                    		<label class="sr-only" for="email">Email</label>
			                        	<input type="text" name="email" id ="email" placeholder="Email..." class="form-username form-control">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="password">Password</label>
			                        	<input type="password" name="password" placeholder="Password..." class="form-password form-control" id="password">
			                        </div>
			                         <div>
			                         <h4 align="center"><font color="red"><c:out value="${error}"/></font></h4>
                        		
                        		</div>
			                        <button type="submit" class="btn" id="signInBtn">Sign in!</button>
			                        <div style="text-align:center">
			                        	Or
			                        </div>
			                       
			                        <h3 style="text-align:center" class='text-muted'> <a href="register">Sign Up!!</a></h3>
			                        
			                        
			                    </form>
		                    </div>
                        </div>
                    </div>
                    <!-- <div class="row">
                        <div class="col-sm-6 col-sm-offset-4 form-box">
                        <div class="form-top-left">
                        			<h3 class='text-muted'> <a href="register">Sign Up!!</a></h3>
                        			
                        		</div>
                        		
                    </div>
                    </div> -->
                  </div>
					
                </div>
            </div>
            
      


        <!-- Javascript -->
        <!-- <script src="resources/assets/js/jquery-1.11.1.min.js"></script>
        <script src="resources/assets/bootstrap/js/bootstrap.min.js"></script> -->
        <script src="resources/assets/js/jquery.backstretch.min.js"></script>
        <!-- <script src="resources/assets/js/scripts.js"></script> -->
        
        
        <!--[if lt IE 10]>
            <script src="resources/assets/js/placeholder.js"></script>
        <![endif]-->
		
		<script>
			
		
		
			 $("#mainForm").validate({
				  rules: {
					  email: {
						  required:true,
						  email:true
					  },
					  password: "required"
				   },
				   message:{
					   email:{
						   email:"Please provide valid email."
					   }
				   }
				   
			});	 
		 
		 
		 $('#signInBtn').on('click', function(e) {
			    if(!$("#mainForm").valid())
			    	e.preventDefault();
			    console.log();
			});
		
		

		
		</script>
    </body>

</html>