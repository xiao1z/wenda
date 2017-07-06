	<div class="row">
	<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="col-md-12">
			<ul class="nav nav-tabs">
				<li style=" top:0px;">
					<a href="#">
            		<img src="/wenda/static/images/log_icon.jpg" class="img-rounded img-responsive" 
                 	alt="喵问答">
        			</a>
				</li>
				<li class="active" style=" top:6px;">
					<a href="#">主页</a>
				</li>
				<li style=" top:6px;">
					<a href="#">Profile</a>
				</li>
				<li class="disabled" style=" top:6px;">
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
				<li class="dropdown pull-right" style="top:6px">
					 <a href="#" data-toggle="dropdown" class="dropdown-toggle">${user.username}<strong class="caret"></strong></a>
					<ul class="dropdown-menu"style="min-width:100%">
						<li>
							<a href="/wenda/logout">退出</a>
						</li>
						<li class="disabled">
							<a href="#">action</a>
						</li>
						<li class="disabled">
							<a href="#">action</a>
						</li>
						<li class="divider">
						</li>
						<li class="disabled">
							<a href="#">action</a>
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
		</nav>
	</div>
	
	<div class="row" style="margin-top:50px">
	</div>