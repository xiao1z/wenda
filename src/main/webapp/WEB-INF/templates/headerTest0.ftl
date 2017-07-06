<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>header</title>

    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">

    <link href="/wenda/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/wenda/static/css/style.css" rel="stylesheet">
  </head>
  <body>

    <div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<ul class="nav nav-tabs">
				<li style="left:100px;top:0px;">
					<a href="#">
            		<img src="/wenda/static/images/log_icon.jpg" class="img-rounded img-responsive" 
                 	alt="喵问答">
        			</a>
				</li>
				<li class="active" style="left:100px;top:6px;">
					<a href="#">主页</a>
				</li>
				<li style="left:100px;top:6px;">
					<a href="#">Profile</a>
				</li>
				<li class="disabled" style="left:100px;top:6px;">
					<a href="#">Messages</a>
				</li>
				<li class="pull-right">
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">提交</button>
				</form>
				</li>
				
				<#if user??>
				<li class="dropdown pull-right">
					 <a href="#" data-toggle="dropdown" class="dropdown-toggle">Dropdown<strong class="caret"></strong></a>
					<ul class="dropdown-menu">
						<li>
							<a href="#">Action</a>
						</li>
						<li>
							<a href="#">Another action</a>
						</li>
						<li>
							<a href="#">Something else here</a>
						</li>
						<li class="divider">
						</li>
						<li>
							<a href="#">Separated link</a>
						</li>
					</ul>
				</li>
				<#else>
				<li class="dropdown pull-right" style="top:8px">
					<form>
						<button type="submit" class="btn btn-default" formaction="/wenda/reglogin"><span>登录/注册</span></button>
					</form>
				</li>
				</#if>
				<li class="dropdown pull-right" style="top:8px;right:8px">
					<form>
						<button type="submit" class="btn btn-info"><span>提问</span></button>
					</form>
				</li>
			</ul>
		</div>
	</div>
</div>
 	<script src="/wenda/static/js/jquery.min.js"></script>
    <script src="/wenda/static/js/bootstrap.min.js"></script>
    <script src="/wenda/static/js/scripts.js"></script>
  </body>
</html>