var loginStatus;//标识是否登录
var isSubmitSuccess =false;
var currentPath;
var loginReturnPath;
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
 	window.location.replace(loginReturnPath);
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
	if(!isSubmitSuccess&&loginStatus.code!=999);
	else window.location.replace("/wenda/");
})

$("#questionSubmit").click(function(){
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
	});