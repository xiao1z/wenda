var loginStatus;//标识是否登录
var isSubmitSuccess =false;
var currentPath;
var loginReturnPath;
var userId;
$(document).ready(function(){
		//查询用户是否登录
		$.ajax({  
            type: "POST",  
            url:"/wenda/identify/login",  
            dataType:"json",
            async: false,  
            error: function() {  
                alert("网络异常");  
            },
            success: function(json) {  
                loginStatus = eval(json);
                userId=loginStatus.msg;
            }
       });
       currentPath=window.location.pathname;
       loginReturnPath="/wenda/reglogin?next="+currentPath;
    
      if(loginStatus.code==999)
      {
       $("#requireQuestion").popover({
		    trigger:"focus", 
		    placement:"bottom",
		    html:true,
		    content:'<p style="width:100px;">您还未登录！</p><p>请先<a href="'
		    +loginReturnPath+
		    '">登录</a></p>'
		}); 
      }
	});
 $("#regloginButton").click(function(){
 	window.location.href=loginReturnPath;
 });
$("#requireQuestion").click(function(){
    if(loginStatus.code==0)
    {  
    	$("#submitSuccess").attr("style","display:none");
      	$("#questionSubmit").fadeIn();
      	$("#requireQuestion").attr("data-target","#QuestionModal");
      	$("#requireQuestion").attr("data-toggle","modal");
      	if(!isSubmitSuccess);
      	else
      	{
          	$("#title").val("");
          	$("#content").val("");
        }
    }
});

$('#QuestionModal').on('hide.bs.modal', function () {
	if(isSubmitSuccess&&loginStatus.code==0)
	{
		window.location.replace("/wenda");
	}
	else if(!isSubmitSuccess&&loginStatus.code==999)
	{
		$.ajax({  
            type: "POST",  
            url:"/wenda/question/addCache/"+userId,  
            data:$('#question').serialize(),// 序列化表单值  
            dataType:"json",
            async: false,  
            error: function() {  
                alert("网络异常");  
            },   
        });
		window.location.replace("/wenda/reglogin");
	}
})

$("#questionSubmit").click(function(){
	if($("#title").val()=="");
	else
	{
		$.ajax({  
            type: "POST",  
            url:"/wenda/question/add",  
            data:$('#question').serialize(),// 序列化表单值  
            dataType:"json",
            async: false,  
            error: function() {  
                alert("网络异常");  
            },  
            success: function(json) {  
                loginStatus = eval(json);
                if(loginStatus.code==0)
                {
                	$("#submitSuccess").fadeIn();
                	$("#questionSubmit").fadeOut();
                	isSubmitSuccess=true;
                }else if(loginStatus.code==999)
                {
               		$("#submitUnlogin").fadeIn();
                	$("#questionSubmit").fadeOut();
                }
            }  
        });
	}
});