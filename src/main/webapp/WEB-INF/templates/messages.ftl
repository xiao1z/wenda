<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>消息中心</title>


    <link href="/wenda/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/wenda/static/css/style.css" rel="stylesheet">
    


  </head>
  <body style="font-size:100%">

    <div class="container-fluid">
    <#include "header.ftl">
    	<#if voList??>
		<#list voList as vo>
		<div class="row" >
			<div class="col-md-1">
			</div>
			<div class="col-md-1">
			</div>
			
			<div class="col-md-9">
				<div class="row">
					<div class="col-md-1" >
						<hr>
						<div class="chat-headbox">
	                        <span class="msg-num">
	                            ${vo.message.id}
	                        </span>
	                        <a class="list-head">
	                            <img alt="头像" src="${vo.interlocutor.headUrl}">
	                        </a>
	                    </div>
					</div>
					<div class="col-md-9">
						<hr>
						<div style="min-height:90px">
							<p>
								<span  style="color:gray;font-size:1.7em">
									${vo.action}
								</span>
								<#if vo.isSystem>
									<span style="color:red">${vo.interlocutor.username}</span>
								<#else>
									<a href="/wenda/user/${vo.interlocutor.id}">${vo.interlocutor.username}</a>
								</#if>
								<span class="pull-right" style="color:gray;font-size:1.7em">
									${vo.message.createDate?string('yyyy年MM月dd日  HH:mm:ss')}
								</span>
							</p>
							<p class="word" style="font-size:2em">
							 	${vo.message.content}
							</p>
							<p class="text-right">
								<a href="/wenda/user/${user.id}/messages/${vo.message.conversationId}"><span style="font-size:1.7em">查看更多 »</span></a>
							</p>
						</div>
					</div>
				</div>
			</div>
			
			
			<div class="col-md-1">
			</div>
		</div>
		</#list>
		<#else>
		<div class="row" >
			<div class="col-md-1">
			</div>
			<div class="col-md-1">
			</div>
			
			<div class="col-md-9">
			<hr>
				<h3 class="text-info">
					暂时没有消息。
				</h3>
			</div>
			<div class="col-md-1">
			</div>
		</div>	
		</#if>
	</div>
	<script src="/wenda/static/js/jquery.min.js"></script>
    <script src="/wenda/static/js/bootstrap.min.js"></script>
    <script src="/wenda/static/js/header.js"></script>
    
  </body>
</html>