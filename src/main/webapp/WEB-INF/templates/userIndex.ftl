<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>用户首页</title>

   

    <link href="/wenda/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/wenda/static/css/style.css" rel="stylesheet">
    
    <style>
    .row-margin-top {
    margin-top: 20px;
	}	
    </style>
    <script src="/wenda/static/js/jquery.min.js"></script>
    <script src="/wenda/static/js/bootstrap.min.js"></script>
    <script src="/wenda/static/js/scripts.js"></script>
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
			<div class="col-md-2 text-center">
				<div class="text-center">
					<img alt="用户头像" class="img-rounded" src="${owner.headUrl}">
				</div>
				<br>
				
			</div>
			<div class="col-md-8">
				<h2>
				<strong>${owner.username}</strong>
				</h2>
				<p style="word-break: break-all;word-wrap: break-word;font-size:2em;">
					简介
				</p>
				<a>
					查看详细资料
				</a>
				<div class="pull-right">
					<ul class="list-inline">
						<#if canSendMessage??>
						<li>
							<button type="button" class="btn btn-primary btn-lg">关注他</button> 
						</li>
						<li>	
							<button type="button" id="sendMessageRequire"  class="btn btn-primary btn-lg">发私信</button>
						</li>
						<#else>
						<li>
							<a type="button" class="btn btn-primary btn-lg">修改资料</a> 
						</li>
						<li>
							<a  href="/wenda/user/${user.id}/messages" type="button" class="btn btn-primary btn-lg">查看私信</a> 
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
			
			<div class="tabbable" id="tabs" style="margin-left:10px;font-size:2em">
				<hr>
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
				</ul>
				<div class="tab-content">
					<div class="tab-pane" id="activities">
						<p>
							activity
						</p>
					</div>
					<div class="tab-pane active"  id="answers">
					<p style="margin-top:15px"><strong>${discribe}</strong></p>
						<#if voList??>
						<#list voList as vo>
						<hr>
						<p>
							<a href="/wenda/question/${vo.question.id}" style="color:black"><em><h2>${vo.question.title}</h2></em></a>
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
								<span style="color:gray"> 28人赞同 </span>
								<span style="color:gray"> &nbsp发布于:${vo.comment.createDate?string('yyyy年MM月dd日  HH:mm:ss')}</span>
								
								<p style="word-break: break-all;word-wrap: break-word; font-size:1em;">
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
					</div>
					
				</div>
			</div>
		</div>
		<div class="col-md-1">
		</div>
	</div>

    <script>
    	var lastClick;
    	var ownerId = $("#ownerId").text();
    	
    
    	
    	
    	$("#sendMessageRequire").click(function(){
	    	 
	        if(loginStatus.code==0)
	        {  
	        	$("#sendMessageSuccess").attr("style","display:none");
	          	$("#sendMessage").fadeIn();
	          	$("#sendMessageRequire").attr("data-target","#messageModal");
	          	$("#sendMessageRequire").attr("data-toggle","modal");
	          	$("#messageContent").val("");
	        }
	    });
	    
	    $("#sendMessage").click(function(){
			$.ajax({  
	                type: "POST",  
	                url:"/wenda/addMessage/user/"+ownerId,  
	                data:$('#messageForm').serialize(),// 序列化表单值  
	                dataType:"json",
	                async: false,  
	                error: function() {  
	                    alert("网络异常");  
	                },  
	                success: function(json) {  
	                    loginStatus = eval(json);
	                    if(loginStatus.code==0)
	                    {
	                    	$("#sendMessageSuccess").fadeIn();
	                    	$("#sendMessage").fadeOut();
	                    }else if(loginStatus.code==999)
	                    {
	                   		$("#sendMessageUnlogin").fadeIn();
	                    	$("#sendMessage").fadeOut();
	                    }
	                }  
	            });  
			});
	    
    	$("#requireQustions").click(function(){
    		 if(lastClick==$(this).attr("id"));
    		 else
    		 {
    			 lastClick=$(this).attr("id");
	    		 var questionsResponse;
	    		 $.ajax({  
		                type: "GET",  
		                url:"/wenda/user/"+ownerId+"/questions",  
		                dataType:"json",
		                async: false,  
		                error: function() {  
		                    alert("网络异常");  
		                },
		                success: function(json) {  
		                    questionsResponse=eval(json);
		                }
			           });
			     if(questionsResponse.code==3)
			     {
			    	$("#questions").html(function(i,origText){
			     	return origText+"<p>该用户还没有提过问题</p>";
			     	});
			     }else
			     {
			     	for(a in questionsResponse.questionArray)
			     	{
			     		$("#questions").html(function(i,origText){
			     			return origText+'<p><a href="/wenda/question/' +questionsResponse.questionArray[a].id+ '" style="color:black"><em><h2>'
			     			+questionsResponse.questionArray[a].title
			     			+'</h2></em></a><span style="color:gray">发布于:'+questionsResponse.questionArray[a].createdDate+'</span></p><hr>';
			     		});
			     
			    	}
			     }
			}
    	});
    </script>
  </body>
</html>