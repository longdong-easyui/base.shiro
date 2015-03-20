<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>登录</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.0.3-dist/dist/css/bootstrap.css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/bootstrap-3.0.3-dist/dist/js/bootstrap.js"></script>

<script type="text/javascript">
	function regFun(){
		window.location.href="${pageContext.request.contextPath}/toRegisterPage"
	}
	
</script>
</head>
<body>



	<div class="container">
		<c:if test="${error!=null}">
		<div class="alert alert-warning alert-dismissable">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<strong>Error!</strong> ${error}
		</div>
		</c:if>
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6">
				<div class="row">
					<div class="panel panel-default ">
						<div class="panel-heading ">
							<h3 class="panel-title">登录</h3>
						</div>
						<div class="panel-body">
							<form class="form-horizontal" role="form" action="" method="post">
								<div class="form-group">
									<label for="inputUserName" class="col-sm-2 control-label">UserName</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="inputUserName"
											name="username"
											placeholder="UserName" value="<shiro:principal/>">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword3" class="col-sm-2 control-label">Password</label>
									<div class="col-sm-10">
										<input type="password" class="form-control"
											name="password"
											id="inputPassword3" placeholder="Password">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<div class="checkbox">
											<label> <input type="checkbox" name="rememberMe"
												value="true"> Remember me
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="submit" class="btn btn-default">登录</button>
										<button onclick="regFun()" type="button" class="btn btn-default">注册</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-3"></div>
		</div>


	</div>
</body>
</html>