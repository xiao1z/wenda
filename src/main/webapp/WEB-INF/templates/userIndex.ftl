<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>用户首页</title>

   
	
    <link href="/wenda/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/wenda/static/css/style.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/wenda/static/css/fileinput.min.css" />
    <style>
    .row-margin-top {
    margin-top: 20px;
	}	
    </style>
   
  </head>
  
  <body style="font-size:100%;">
	<span id="ownerId" class="hidden">${owner.id}</span>
    <div class="container-fluid">
    
    <#include "header.ftl">
    
    <div class="row" style="margin-bottom:10px;margin-top:30px">
   		<div class="col-md-1">
		</div>
		<div class="col-md-1">
		</div>
		<div class="col-md-9">
			<div class="col-md-2">
				<div>
					<a href="/wenda/user/${owner.id}">
						<img alt="用户头像" id="ownerHeadUrl"  style="width:130px;height:130px;" class="img-rounded" src="${owner.headUrl}">
					</a>
				</div>
				<br>
			</div>
			<div class="col-md-8">
				<h3>
				<strong>${owner.username}</strong>
				</h3> 
				<#if owner.briefIntroduction??>
					<p id="briefIntroductionDisplay" style="word-break: break-all;word-wrap: break-word;font-size:1.5em;">
						${owner.briefIntroduction}
					</p>
				<#else>
					<p id="briefIntroductionDisplay" style="word-break: break-all;word-wrap: break-word;font-size:1.5em;">
						(暂无简介)
					</p>
				</#if>
				<br>
				<a id="infoDisplayRequire" class="btn">
					查看详细资料
				</a>
				<div class="modal fade" tabindex="-2" id="infoDisplayModal" role="dialog"  aria-hidden="true">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								 
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									×
								</button>
								<h4 class="modal-title">
									详细资料：
								</h4>
							</div>
							<div class="modal-body">
								<form id="infoFormDisplay" class="form-horizontal">
								  <div class="form-group">
								    <label for="nickname" class="col-sm-3 control-label">昵称:</label>
								    <div class="col-sm-7">
								    	<span id="nicknameDisplay" class="form-control">${owner.nickname}</span>
								    													    	
								    </div>
								  </div>
								  
								  <div class="form-group">
								    <label class="col-sm-3 control-label">性别:</label>
								    <div class="col-sm-7">
										<span id="sexDisplay" class="form-control">女</span>
								    </div>
								  </div>
								  
								   <div class="form-group">
								    <label for="briefIntroduction" class="col-sm-3 control-label">简介:</label>
								    <div class="col-sm-7">
									    <span id="briefIntroductionDisplayInModal" class="form-control">
									    <#if owner.briefIntroduction??>${owner.briefIntroduction}
									    </#if>
									    </span>
								    </div>
								  </div>
								  
								  <div class="form-group">
								    <label for="description" class="col-sm-3 control-label">详细介绍:</label>
								    <div class="col-sm-7">
								    	<span id="descriptionDisplay" class="form-control">详细介绍</span>
								    </div>
								  </div>
								  
								  <div class="form-group">
								    <label for="location" class="col-sm-3 control-label">居住地:</label>
								    <div class="col-sm-7">
								      <span id="locationDisplay" class="form-control">居住地</span>
								    </div>
								  </div>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button> 
							</div>
						</div>
					</div>
				</div>
				<br>
				<div class="pull-right">
					<ul class="list-inline">
						<#if canSendMessage??>
							<#if followTableId??>
								<span class="hidden" id="followTableId">${followTableId}</span>
								<button id="followToggle" type="button" class="btn btn-primary">
									取消关注
								</button>
							<#else>
								<span class="hidden" id="followTableId"></span>
								<button id="followToggle" type="button" class="btn btn-primary">
									关注 Ta
								</button>
							</#if>
							
							<li>	
								<button type="button" id="sendMessageRequire"  class="btn btn-primary  ">发私信</button>
							</li>
						<#else>
							<li>
								<a type="button" id="uploadHeadImageRequire" class="btn btn-primary  ">上传头像</a> 
							</li>
							<div class="modal fade" tabindex="-2" id="headImgModal" role="dialog"  aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											 
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
												×
											</button>
											<h4 class="modal-title">
												上传头像：
											</h4>
										</div>
										<div class="modal-body">
											<form id="messageForm" class="form-horizontal">
											    <div class="form-group">
											    <label class="col-sm-3 control-label">头像:</label>
											   
											    <div class="col-sm-7">
												    <input id="headImg" name="headImg" type="file" data-show-caption="true">  
		            								<p class="help-block">支持jpg、jpeg、png格式</p>
		            							</div>
		            							</div>
		            						</form>
		            					  
                								
										</div>
										<div class="modal-footer">
											<div id="uploadHeadImageSuccess" class="alert alert-success" style="display:none">上传成功。</div>
											<div id="uploadHeadImageUnlogin" class="alert alert-danger" style="display:none">您已登出，请重新登录。</div>
											<button type="button" class="btn btn-default" data-dismiss="modal">
												关闭
											</button> 
											<button type="button" id="uploadHeadImg" class="btn btn-primary">
												上传
											</button>
										</div>
									</div>
								</div>
							</div>
							
							
							<li>
								<a type="button" id="alterInfoRequire" class="btn btn-primary  ">修改资料</a> 
							</li>
							<div class="modal fade" tabindex="-2" id="infoModal" role="dialog"  aria-hidden="true">
								<div class="modal-dialog modal-lg">
									<div class="modal-content">
										<div class="modal-header">
											 
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
												×
											</button>
											<h4 class="modal-title">
												修改资料：
											</h4>
										</div>
										<div class="modal-body">
											<form id="infoForm" class="form-horizontal">
											  <div class="form-group">
											    <label for="nickname" class="col-sm-3 control-label">昵称:</label>
											    <div class="col-sm-7">
											    	<input type="text" class="form-control" name="nickname" value="${owner.nickname}" id="nickname" placeholder="请输入昵称（可选）" />
											    													    	
											    </div>
											  </div>
											  
											  <div class="form-group">
											    <label class="col-sm-3 control-label">性别:</label>
											    <div class="col-sm-7">
													<label class="radio-inline">
													  <input type="radio" name="sex" id="male" value="male" > 男
													</label>
													<label class="radio-inline">
													  <input type="radio" name="sex" id="female" value="female"> 女
													</label>
													<label class="radio-inline">
													  <input type="radio" name="sex" id="secrecy" value="secrecy"> 保密
													</label>
											    </div>
											  </div>
											  
											   <div class="form-group">
											    <label for="briefIntroduction" class="col-sm-3 control-label">简介:</label>
											    <span class="hidden" id="briefIntroductionContent"><#if owner.briefIntroduction??>${owner.briefIntroduction}</#if></span>
											    <div class="col-sm-7">
											    	<textarea id="briefIntroduction" name="briefIntroduction" class="form-control" placeholder="一句话描述自己(可选)(不超过64个字符)" rows="2">
											    	</textarea>
											    </div>
											  </div>
											  
											  <div class="form-group">
											    <label for="description" class="col-sm-3 control-label">详细介绍:</label>
											    <div class="col-sm-7">
											    	<textarea id="description" name="description" class="form-control" placeholder="描述一下自己（可选）" rows="4"></textarea>
											    </div>
											  </div>
											  
											  <div class="form-group">
											    <label for="location" class="col-sm-3 control-label">居住地:</label>
											    <div class="col-sm-7">
											      <input type="text" id="location" class="form-control" name="location" placeholder="居住地（可选）">
											    </div>
											  </div>
											  
											</form>
										</div>
										<div class="modal-footer">
											<div id="alterMessageSuccess" class="alert alert-success" style="display:none">修改成功。</div>
											<div id="alterMessageUnlogin" class="alert alert-danger" style="display:none">您已登出，请重新登录。</div>
											<button type="button" class="btn btn-default" data-dismiss="modal">
												关闭
											</button> 
											<button type="button" id="alterInfo" class="btn btn-primary">
												修改
											</button>
										</div>
									</div>
								</div>
							</div>
								
							<li>
								<a  href="/wenda/user/${user.id}/messages" type="button" class="btn btn-primary ">查看私信</a> 
							</li>
						</#if>
					</ul>
				</div>
				<div class="modal fade" tabindex="-2" id="messageModal" role="dialog" aria-labelledby="messageLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								 
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									×
								</button>
								<h4 class="modal-title" id="myModalLabel">
									私信内容：
								</h4>
							</div>
							<div class="modal-body">
								<div class="form-group">
								<form id="messageForm">
									<label for="title" style="margin-bottom:10px">内容:</label>
									<textarea id="messageContent" name="messageContent" class="form-control" placeholder="内容" rows="7"></textarea>
								</form>
								</div>
							</div>
							<div class="modal-footer">
								<div id="sendMessageSuccess" class="alert alert-success" style="display:none">发送成功。</div>
								<div id="sendMessageUnlogin" class="alert alert-danger" style="display:none">您已登出，请重新登录。</div>
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button> 
								<button type="button" id="sendMessage" class="btn btn-primary">
									发送
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-1">
		</div>
	</div>	
    <div class="row">
   		<div class="col-md-1">
		</div>
		<div class="col-md-1">
		</div>
		<div class="col-md-9">
			<div class="row">
				<div class="col-md-10 well" style="background-color:rgba(224,238,224,0.0);">
					<div class="tabbable" id="tabs" style="margin-left:10px;font-size:1.5em">
						
						<ul class="nav nav-tabs" >
							<li>
								<a href="#activities" data-toggle="tab">动态</a>
							</li>
							<li class="active">
								<a id="requireAnswers" href="#answers" data-toggle="tab">回答</a>
							</li>
							<li>
								<a id="requireQustions" href="#questions" data-toggle="tab">问题</a>
							</li>
							<li>
								<a id="requireCollections" href="#collections" data-toggle="tab">收藏</a>
							</li>
							<li>
								<a id="requireFollows" href="#follows" data-toggle="tab">关注</a>
							</li>
							<li>
								<a id="requireFollowers" href="#followers" data-toggle="tab">粉丝</a>
							</li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane" id="activities">
								<p style="margin-top:15px"><strong>${discribe}动态</strong></p>
								<hr>
								<div id="activitiesContent">
								</div>
							</div>
							<div class="tab-pane active"  id="answers">
								<p style="margin-top:15px"><strong>${discribe}回答</strong></p>
								<#if voList??>
								<#list voList as vo>
								<hr>
								<p>
									<a href="/wenda/question/${vo.question.id}" style="color:black"><em><h4>${vo.question.title}</h4></em></a>
								</p>
								<div class="row">
									<div class="col-md-1">
										<div class="text-center" style="border:0px">
											<img alt="用户头像" class="img-rounded commentUserimgSize" src="${owner.headUrl}">	
											<br>
											<div style="margin-top:10px" >
												<a href="/wenda/user/${owner.id}/answers"><span style="word-break: break-all;word-wrap: break-word;">
												${owner.username}
												</span></a>
											</div>
										</div>
									</div>
									<div class="col-md-8">
										<span style="color:gray"> &nbsp发布于:${vo.comment.createDate?string('yyyy年MM月dd日  HH:mm:ss')}</span>
										
										<p style="word-break: break-all;word-wrap: break-word;">
											${vo.comment.content}
										</p>
									</div>
								
								</div>
								</#list>
								<#else>
									<p>
									<hr>
									该用户还没有回答过问题
									</p>
								</#if>
								
							</div>
							<div class="tab-pane" id="questions">
								<p style="margin-top:15px"><strong>${discribe}提问</strong></p>
								<hr>
								<div id="questionsContent">
								</div>
							</div>
							<div class="tab-pane" id="collections">
								<p style="margin-top:15px"><strong>${discribe}收藏</strong></p>
								<hr>
								<div id="collectionsContent">
								</div>
							</div>
							<div class="tab-pane" id="follows">
								<p style="margin-top:15px"><strong>${discribe}关注</strong></p>
								<hr>
								<div id="followsContent">
								</div>
							</div>
							<div class="tab-pane" id="followers">
								<p style="margin-top:15px"><strong>${discribe}粉丝</strong></p>
								<hr>
								<div id="followersContent">
								</div>
							</div>
						</div>
					</div>
				</div>

				
				
			</div>
		</div>
		<div class="col-md-1">
		</div>
	</div>
 	<script src="/wenda/static/js/jquery.min.js"></script>
    <script src="/wenda/static/js/bootstrap.min.js"></script>
    <script src="/wenda/static/js/header.js"></script>
    <script src="/wenda/static/js/userIndex.js"></script>
	<script type="text/javascript" src="/wenda/static/js/fileinput.min.js"></script>
	<script type="text/javascript" src="/wenda/static/js/fileinput_locale_zh.js"></script>
    
  </body>
</html>