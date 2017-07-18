<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${question.title}</title>

   

	
    <link href="/wenda/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/wenda/static/css/style.css" rel="stylesheet">
    
   
  </head>
  <body style="font-size:100%;">
  	

    <div class="container-fluid" id="container">
    <#include "header.ftl">
    <span id="questionId" class="hidden">${question.id}</span>
	<div class="row" style="margin-top:10px">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
		</div>
		<div class="col-md-8">
			 <span class="label label-info">Label</span>
			 <span class="label label-info">Label</span>
			 <span class="label label-info">Label</span>
			 <span class="label label-info">Label</span>
			 <a href="#" class="btn" type="button">Button</a>
		</div>
		<div class="col-md-1">
		</div>
	</div>
	<div class="row">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
			<div class="text-center">
					<img alt="用户头像" class="img-rounded headimgSize" src="${asker.headUrl}">
			</div>
			<div class="text-center">		
					<span class="badge badgeCss">100</span>
			</div>
		</div>
		<div class="col-md-8">
			<h2>
				<strong>${question.title}</strong>
			</h2>
			<p style="word-break: break-all;word-wrap: break-word;font-size:2em;">
				${question.content}
			</p>
		</div>
		<div class="col-md-1">
		</div>
	</div>
	<div class="row">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
		</div>
		<div class="col-md-8">
			<#if user?? && user.id == question.userId>
			<#else>
				<#if followTableId??>
					<span class="hidden" id="followTableId">${followTableId}</span>
					<button id="collectToggle" type="button" class="btn btn-primary">
						取消收藏
					</button>
				<#else>
					<span class="hidden" id="followTableId"></span>
					<button id="collectToggle" type="button" class="btn btn-primary">
						收藏
					</button>
				</#if>
			</#if>
			<div class="btn-group">
				 
				<button class="btn btn-default">
					Action
				</button> 
				<button data-toggle="dropdown" class="btn btn-default dropdown-toggle">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li>
						<a href="#">Action</a>
					</li>
					<li class="disabled">
						<a href="#">Another action</a>
					</li>
					<li class="divider">
					</li>
					<li>
						<a href="#">Something else here</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="col-md-1">
		</div>
	</div>
	<div class="row" style="margin-top:20px">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
		</div>
		<div class="col-md-8">
			<span><strong style="color:#0080FF">9</strong>人关注该问题</span>
		</div>
		<div class="col-md-1">
		</div>
	</div>
	<div class="row" style="margin-top:10px">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
		</div>
		<div class="col-md-8">
			<img alt="Bootstrap Image Preview" src="http://lorempixel.com/140/140/" class="img-thumbnail" style="width:40px;height:40px">
			<img alt="Bootstrap Image Preview" src="http://lorempixel.com/140/140/" class="img-thumbnail" style="width:40px;height:40px">
			<img alt="Bootstrap Image Preview" src="http://lorempixel.com/140/140/" class="img-thumbnail" style="width:40px;height:40px">
		</div>
		<div class="col-md-1">
		</div>
	</div>
	<#if voList??>
	<#list voList as vo>
		<div class="row" style="_height:100px; min-height:100px ">

			<div class="col-md-1">
			</div>
			<div class="col-md-1">
				
				<div class="row">
				<a id="like_${vo.comment.id}" class="<#if vo.isLike == "yes">btn-info <#else>btn-default </#if> btn btn-sm pull-right" style="margin-top:25px">
					<span class="glyphicon glyphicon glyphicon-thumbs-up"></span>
					<br>
					<small id="likeCount_${vo.comment.id}">${vo.likeCount}</small>
				</a>
				</div>
				<div class="row">
				<a id="dislike_${vo.comment.id}" class="<#if vo.isLike == "no">btn-info <#else>btn-default </#if>btn btn-sm pull-right" style="margin-top:5px">
					<span class="glyphicon 	glyphicon glyphicon-thumbs-down"></span>
					<br>
					<small></small>
				</a>
				</div>
			</div>
			<div class="col-md-8">
				<hr>
				<div class="col-md-1">
					<div class="text-center" style="border:0px">
						<img alt="用户头像" class="img-rounded commentUserimgSize" src="${vo.user.headUrl}">	
						<br>
						<div style="margin-top:10px" >
							<a href="/wenda/user/${vo.user.id}"><span style="word-break: break-all;word-wrap: break-word;">
							${vo.user.username}
							</span></a>
						</div>
					</div>
				</div>
				<div class="col-md-8">
					<span style="color:gray">发布于${vo.comment.createDate?string('yyyy年MM月dd日  HH:mm:ss')}</span>
					
					<p style="word-break: break-all;word-wrap: break-word; font-size:2em;">
						${vo.comment.content}
					</p>
				</div>
			
			</div>
			<div class="col-md-1">
			</div>
		</div>
		
		<div class="row" style="_height:100px; min-height:0px ">
			<div class="col-md-1">
			</div>
			<div class="col-md-1">
			</div>
			<div class="col-md-8">
				<div class="col-md-1">
				</div>
				<div class="col-md-10" style="background-color:rgba(224,238,224,0.6);line-height:1.5">
					<br>
					<#if vo.subVoList??>
					<#list vo.subVoList as subVo>
					<span style="color:gray"> &nbsp发布于：${subVo.subComment.createDate?string('yyyy年MM月dd日  HH:mm:ss')}</span>
					<br>
					<p><a href="/wenda/user/${subVo.subUser.id}">${subVo.subUser.username}：</a>${subVo.subComment.content}</p>
					<br>
					</#list>
					</#if>
					<span class="hidden" id="commentId_${vo.comment.id}">${vo.comment.id}</span>
					<textarea  type="text" id="subCommentContent_${vo.comment.id}" name="content" class="form-control" style="overflow-x:visible;overflow-y:visible;" placeholder="请输入您的观点" rows="1"></textarea>
					<br>
					<div id="submitUnlogin_comment_inComment_${vo.comment.id}" class="alert alert-danger" style="display:none;margin-top:10px">发布评论需要登录，请先登录！</div>
					<button type="button" id="subCommentCommit_${vo.comment.id}" class="btn btn-primary pull-right">回复</button>
					<hr>
				</div>
			</div>
			<div class="col-md-1">
			</div>
		</div>
	</#list>
	<#else>
	<div class="row" style="margin-top:10px">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
		</div>
		<div class="col-md-8">
			<hr>
			<p style="word-break: break-all;word-wrap: break-word;">
				暂时还没有回答，赶紧抢沙发吧！
			</p>
			<hr>
		</div>
		<div class="col-md-1">
		</div>
	</div>
	</#if>
	<div class="row" style="margin-top:20px">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
		</div>
		<div class="col-md-8">
			<hr>
			<div id="editor">
			</div>
			<div id="submitUnlogin_comment" class="alert alert-danger" style="display:none;margin-top:10px">发布回答需要登录，请先登录！</div>
		    <div class="pull-right" style="margin-top:10px">
		   		<button id="commentSubmit" type="button" class="btn btn-primary btn-lg">提交回答</button>
		    </div>
		</div>
		<div class="col-md-1">
		</div>
	</div>
	
	<div class="row" id="footer">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
		</div>
		<div class="col-md-8" style="margin-top:30px">
			<hr>
			<hr>
		</div>
		<div class="col-md-1">
		</div>
	</div>
</div>
 	<script src="/wenda/static/js/jquery.min.js"></script>
    <script src="/wenda/static/js/bootstrap.min.js"></script>
    <script src="/wenda/static/js/header.js"></script>
	<script type="text/javascript" src="/wenda/static/js/wangEditor.min.js"></script>
	<script src="/wenda/static/js/questionDetail.js"></script>
    
  </body>
</html>