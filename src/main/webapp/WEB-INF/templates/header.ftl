	<div class="row">
	<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="col-md-12">
			<ul class="nav nav-tabs">
				<li style=" top:0px;">
					<a href="/wenda/">
            		<img src="/wenda/static/images/log_icon.jpg" class="img-rounded img-responsive" 
                 	alt="喵问答">
        			</a>
				</li>

				
				<li style=" top:6px;">
					<a href="/wenda/">首页</a>
				</li>
				
				<#if user??>
				<li style="top:6px;">
					<a href="/wenda/timeline/${user.id}">动态</a>
				</li>
				<li style=" top:6px;">
					<a href="/wenda/user/${user.id}">我的主页</a>
				</li>
				
				</#if>
				
				<li class="pull-right">
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="站内搜索">
					</div>
					<button type="submit" class="btn btn-default">搜索</button>
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
						<button type="button" id="regloginButton" class="btn btn-default" ><span>登录/注册</span></button>
					</form>
				</li>
				</#if>
				<li class="dropdown pull-right" style="top:8px;right:8px">
					<button id="requireQuestion" class="btn btn-primary"><span>提问</span></button>
				</li>
			</ul>
		</div>
		</div>
		</nav>
	</div>
	<div class="modal fade" tabindex="-1" id="QuestionModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					 
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						×
					</button>
					<h4 class="modal-title" id="myModalLabel">
						写下您的问题：
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
					<form id="question">
						<label for="title" style="margin-bottom:10px">标题:</label>
						<textarea id="title" name="title" class="form-control" placeholder="标题" rows="2" style="margin-bottom:20px"><#if cache_title??>${cache_title}</#if></textarea>
						<label for="title" style="margin-bottom:10px">内容(可选):</label>
						<textarea id="content" name="content" class="form-control" placeholder="内容" rows="7"><#if cache_content??>${cache_content}</#if></textarea>
					</form>
					</div>
				</div>
				<div class="modal-footer">
					<div id="submitSuccess" class="alert alert-success" style="display:none">问题提交成功。</div>
					<div id="submitUnlogin" class="alert alert-danger" style="display:none">您已登出，请重新登录。</div>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						关闭
					</button> 
					<button type="button" id="questionSubmit" class="btn btn-primary">
						提交
					</button>
					
				</div>
			</div>
		</div>
	</div>
	<div class="row" style="margin-top:50px">
	</div>

	
	