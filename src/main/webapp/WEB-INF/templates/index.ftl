<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>主页</title>

    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">

    <link href="/wenda/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/wenda/static/css/style.css" rel="stylesheet">
    <style>
    .row-margin-top {
    margin-top: 20px;
	}	
    </style>
    <script src="/wenda/static/js/jquery.min.js"></script>
  </head>
  
  <body>

    <div class="container-fluid">
    <#include "header.ftl">
    <#list voList as vo>
	<div class="row row-margin-top">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
			<div class="text-center">
					<img alt="用户头像" class="img-rounded headimgSize" src="${vo.user.headUrl}">
			</div>
			<div class="text-center">		
					<span class="badge badgeCss">100</span>
			</div>
			
		</div>
		<div class="col-md-9">
			<h4>
				${vo.question.title}
			</h4>
			<a href="/wenda/user/${vo.user.id}">${vo.user.username}</a>
			<p style="word-break:break-all;word-wrap:break-word;">
				${vo.question.content}
			</p>
			<p>
				<a class="btn" href="/wenda/question/${vo.question.id}">查看全部 »</a>
			</p>
			<div>
				<hr>
			</div>
		</div>
		<div class="col-md-1">
		</div>
	</div>
	</#list>
	<div class="row">
		<div class="col-md-1">
		</div>
		<div class="col-md-11 pull-left">
			<ul class="pagination">
				<li>
					<a href="#">Prev</a>
				</li>
				<li class="active">
					<a href="#">1</a>
				</li>
				<li>
					<a href="#">2</a>
				</li>
				<li>
					<a href="#">3</a>
				</li>
				<li>
					<a href="#">4</a>
				</li>
				<li>
					<a href="#">5</a>
				</li>
				<li>
					<a href="#">6</a>
				</li>
				<li>
					<a href="#">7</a>
				</li>
				
				<li>
					<a href="#">Next</a>
				</li>
			</ul>
		</div>
	</div>
</div>

    
    <script src="/wenda/static/js/bootstrap.min.js"></script>
    <script src="/wenda/static/js/scripts.js"></script>
  </body>
</html>