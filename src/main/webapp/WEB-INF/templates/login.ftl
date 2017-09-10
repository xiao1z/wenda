<!DOCTYPE html>
<html lang="en">
  <head>
  	<Link Rel="SHORTCUT ICON" href="/wenda/static/img/favicon.ico">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>登录</title>

   
    <link href="/wenda/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/wenda/static/css/style.css" rel="stylesheet">
    
	<style>
	body 
	{
		background-color:#B0E2FF;
	}
	#captcha1{
            width: 300px;
            display: inline-block;
        }
	#wait1{
            text-align: left;
            color: #666;
            margin: 0;
        }
	</style>
  </head>
  <body>
    <div class="container-fluid">
	<br>
	<br>
	<br>
	<div class="row">
		<div class="col-md-5">
		</div>
		<div class="col-md-4" >
			<img alt="log" style="width:160px;height:160px;" src="/wenda/static/img/log.jpg" class="img-circle">
		</div>
		<div class="col-md-3">
		</div>
	</div>
	<div>
	<br>
	<br>
	<br>
	</div>
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-4">
			<form class="form-horizontal" role="form" method="post" id="regloginForm">
			
				<#if next??>
				<input type="hidden" name="next" value="${next}">
				</#if>
				<#if error??>
				<div class="form-group">
				<label for="regloginForm" class="col-sm-6 control-label">
					<font color="red">${error}</font>
				</label>
				</div>
				</#if>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<div id="notice1" class="alert alert-danger alert-dismissable" style="display:none;margin-top:10px">
						<button type="button" class="close" data-dismiss="alert"
								aria-hidden="true">
							&times;
						</button>
						请先完成验证
						</div>
					</div>
				</div>
				
				<div class="form-group">
					
					<label for="inputEmail3" class="col-sm-2 control-label">
						邮箱
					</label>
					<div class="col-sm-10">
						<input type="email" class="form-control" id="username" name="username">
					</div>
					
				</div>
				<div class="form-group">
					 
					<label for="inputPassword3" class="col-sm-2 control-label">
						密码
					</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" id="password" name="password">
					</div>
					
				</div>
				<div class="form-group">
					 
					<label class="col-sm-2 control-label">验证</label>
					<div id="captcha1" class="col-sm-10">
						<p id="wait1" class="form-control">正在加载验证码......</p>
					</div>
					
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<div class="checkbox">
							 
							<label>
								<input type="checkbox" name="rememberMe"> 记住登录
							</label>
							
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						 
						<button type="submit" class="btn btn-default" onclick="form=document.getElementById('regloginForm');form.action='/wenda/login/'">
							登录
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="submit" class="btn btn-default" onclick="form=document.getElementById('regloginForm');form.action='/wenda/reg/'">
							注册
						</button>
					</div>
				</div>
				
				
			</form>
		</div>
		<div class="col-md-4">
		</div>
	</div>
</div>

<script src="/wenda/static/js/jquery.min.js"></script>
<script src="/wenda/static/js/bootstrap.min.js"></script>
<!-- 引入 gt.js，既可以使用其中提供的 initGeetest 初始化函数 -->
<script src="/wenda/static/js/gt.js"></script>
<script>
    var handler1 = function (captchaObj) {
        $("button.btn-default").click(function (e) {
            var result = captchaObj.getValidate();
            if (!result) {
                $("#notice1").fadeIn();
                setTimeout(function () {
                    $("#notice1").fadeOut();
                }, 3000);
                e.preventDefault();
            }
        });
        // 将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
        captchaObj.appendTo("#captcha1");
        captchaObj.onReady(function () {
            $("#wait1").hide();
        });
    };
    $.ajax({
        url: "/wenda/verify?t=" + (new Date()).getTime(), // 加随机数防止缓存
        type: "get",
        dataType: "json",
        success: function (data) {
            // 调用 initGeetest 初始化参数
            // 参数1：配置参数
            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
                offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
                product: "float", // 产品形式，包括：float，popup
                width: "100%"
            }, handler1);
        }
    });
</script>

  </body>
</html>