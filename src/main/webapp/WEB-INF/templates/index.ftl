<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>首页</title>

  
    <link href="/wenda/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/wenda/static/css/style.css" rel="stylesheet">
    <style>
    .row-margin-top {
    margin-top: 5px;
	}	
    </style>
  </head>
  
  <body  style="font-size:100%;">

<div class="container-fluid">
	<span class="hidden" id="page">${page}</span>
	<span class="hidden" id="hasMore">${hasMore}</span>
    <#include "header.ftl">
    <#if voList??>
    <#list voList as vo>
	<div class="row row-margin-top">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
			<div class="text-center">
				<a href="/wenda/user/${vo.user.id}">
					<img alt="用户头像" class="img-rounded headimgSize" src="${vo.user.headUrl}">
				</a>
			</div>
			
			
		</div>
		<div class="col-md-8 well" style="background-color:rgba(0,0,0,0.0);">
			<h5><strong>
				${vo.question.title}
			</strong></h5>
			<hr>
			<a href="/wenda/user/${vo.user.id}/answers" style="font-size:1.3em">${vo.user.username}</a>
			<p style="word-break:break-all;word-wrap:break-word;font-size:1.3em">
				${vo.question.content}
			</p>
			<p class="text-right">
				<a class="btn" href="/wenda/question/${vo.question.id}">查看全部(${vo.question.commentCount}条回答) »</a>
			</p>
			
		</div>
		<div class="col-md-2">
		</div>
	</div>
	</#list>
	<#else>
	<div class="row row-margin-top">
		<div class="col-md-2">
		</div>
		<div class="col-md-8 " style="margin-top:20px">
			
			<hr>
			<h2 class="text-info">
				没有更多问题了！
			</h2>
		</div>
	</div>	
	</#if>
	<div class="row">
		<div class="col-md-1">
		</div>
		<div class="col-md-1">
		</div>
		<div class="col-md-10 pull-left">
			<ul class="pagination">
				<li id="li_prev">
					<a href="#" id="prev">Prev</a>
				</li>
				<li id="page_li_1">
					<a href="/wenda/?page=1" id="page_1">1</a>
				</li>
				<li id="page_li_2">
					<a href="/wenda/?page=2" id="page_2">2</a>
				</li>
				<li id="page_li_3">
					<a href="/wenda/?page=3" id="page_3">3</a>
				</li>
				<li id="page_li_4">
					<a href="/wenda/?page=4" id="page_4">4</a>
				</li>
				<li id="page_li_5">
					<a href="/wenda/?page=5" id="page_5">5</a>
				</li>
				
				
				<li id="li_next">
					<a href="#"  id="next">Next</a>
				</li>
			</ul>
		</div>
	</div>
</div>

    <script src="/wenda/static/js/jquery.min.js"></script>
    <script src="/wenda/static/js/bootstrap.min.js"></script>
    <script src="/wenda/static/js/header.js"></script>
    <script src="/wenda/static/js/index.js"></script>
  </body>
</html>