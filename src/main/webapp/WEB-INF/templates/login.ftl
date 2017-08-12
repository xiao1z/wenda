<!DOCTYPE html>
<html lang="en">
  <head>
  	<Link Rel="SHORTCUT ICON" href="/wenda/static/img/favicon.ico">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>登录</title>

   
    <link href="/wenda/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/wenda/static/css/style.css" rel="stylesheet">

	<style>
	body 
	{
		background-color:#B0E2FF;
	}
	
	</style>
  </head>
  <body>
    <div class="container-fluid">
	<br>
	<br>
	<br>
	<div class="row">
		<div class="col-md-5">
		</div>
		<div class="col-md-4" >
			<img alt="log" style="width:160px;height:160px;" src="/wenda/static/img/log.jpg" class="img-circle">
		</div>
		<div class="col-md-3">
		</div>
	</div>
	<div>
	<br>
	<br>
	<br>
	</div>
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-4">
			<form class="form-horizontal" role="form" method="post" id="regloginForm">
			
				<#if next??>
				<input type="hidden" name="next" value="${next}">
				</#if>
				<#if error??>
				<div class="form-group">
				<label for="regloginForm" class="col-sm-6 control-label">
					<font color="red">${error}</font>
				</label>
				</div>
				</#if>
				
				<div class="form-group">
					 
					<label for="inputEmail3" class="col-sm-2 control-label">
						邮箱
					</label>
					<div class="col-sm-10">
						<input type="email" class="form-control" id="username" name="username">
					</div>
					
				</div>
				<div class="form-group">
					 
					<label for="inputPassword3" class="col-sm-2 control-label">
						密码
					</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" id="password" name="password">
					</div>
					
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<div class="checkbox">
							 
							<label>
								<input type="checkbox" name="rememberMe"> 记住登录
							</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						 
						<button type="submit" class="btn btn-default" onclick="form=document.getElementById('regloginForm');form.action='/wenda/login/'">
							登录
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="submit" class="btn btn-default" onclick="form=document.getElementById('regloginForm');form.action='/wenda/reg/'">
							注册
						</button>
					</div>
				</div>
			</form>
		</div>
		<div class="col-md-4">
		</div>
	</div>
</div>


  </body>
</html>