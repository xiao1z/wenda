<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>动态</title>

  
    <link href="/wenda/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/wenda/static/css/style.css" rel="stylesheet">
    <style>
    .row-margin-top {
    margin-top: 10px;
	}	
    </style>
    <#macro like>     
    	<font size="+2">Hello !</font>     
	</#macro>  
	
	<#macro follow>     
    	<font size="+2">Hello !</font>     
	</#macro>
	
	<#macro raiseComment feed>     
    	<strong>您关注的人 <a href="/wenda/user/${feed.actorId}">${feed.actorName}</a>
    	 回答了题目 <a href="/wenda/question/${feed.comment.entityId}#subCommentContent_${feed.commentId}">
    	 ${feed.questionTitle}</strong></a>:
    	 <hr>
    	 <p>
    	 	${feed.comment.content}
    	 </p>
    	 <p class="text-right">
				<a class="btn" href="/wenda/question/${feed.comment.entityId}#subCommentContent_${feed.commentId}">查看详情 »</a>
		</p>
	</#macro>
    
    <#macro raiseQuestion>     
    	<font size="+2">Hello !</font>     
	</#macro>
	
	<#macro newAnwser>     
    	<font size="+2">Hello !</font>     
	</#macro>
  </head>
  
  <body>

  <div class="container-fluid">
    <#include "header.ftl">
	<#if feeds??>
    <#list feeds as feed>
	<div class="row row-margin-top">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
			<#if feed.actorHeadUrl??>
			<div class="text-center">
				<a href="/wenda/user/${feed.actorId}">
					<img alt="用户头像" class="img-rounded headimgSize" src="${feed.actorHeadUrl}">
				</a>
			</div>
			<div class="text-center">		
					<span class="badge badgeCss">100</span>
			</div>
			</#if>
		</div>
		<div class="col-md-8 well" style="background-color:rgba(0,0,0,0.0);">
			<#if feed.type==3>
				<@raiseComment feed=feed/> 
			</#if>
		</div>
		<div class="col-md-2">
		</div>
	</div>
	</#list>
	<#else>
		<div class="row">
			<div class="col-md-2">
			</div>
			<div class="col-md-8">
			<hr>
			<h3 class="text-info">
				您还没有关注他人或者收藏问题
			</h3>
			</div>
			<div class="col-md-2">
			</div>
		</div>
	</#if>
</div>

    <script src="/wenda/static/js/jquery.min.js"></script>
    <script src="/wenda/static/js/bootstrap.min.js"></script>
    <script src="/wenda/static/js/header.js"></script>
  </body>
</html>