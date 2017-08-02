var lastClick;
var ownerId = $("#ownerId").text();
var userInfo;
function initFileInput(ctrlName,uploadUrl) 
{      
    var control = $('#' + ctrlName);   
    control.fileinput({  
        language: 'zh', //设置语言  
        uploadUrl: uploadUrl,  //上传地址  
        uploadAsync:true, 
        showCaption: true, 
        showUpload: false,//是否显示上传按钮 
        showRemove: true,//是否显示删除按钮 
        showCaption: true,//是否显示输入框 
        showPreview:true, 
        showCancel:true,  
        maxFileCount: 1, 
        initialPreviewShowDelete:true, 
        previewSettings:
        {
        	 image: {width: "200px", height: "160px"}
        },
        allowedPreviewTypes: ['image'],  
        allowedFileTypes: ['image'],  
        allowedFileExtensions:  ['jpg', 'png'],  
        maxFileSize : 20000,    
    });
}  

$(function () {  
    var path="/wenda/user/"+ownerId+"/headImg";  
    initFileInput("headImg",path);  
    
    $("#briefIntroduction").val($("#briefIntroductionContent").text());
}) 

$("#uploadHeadImg").click(function(){

	$("#headImg").fileinput('upload');
});

$('#headImg').on('fileuploaded', function(event,data) {
	//加？是为了防止浏览器使用缓存而不更新图片
	$("#ownerHeadUrl").attr("src",function(){return data.response.headUrl+"?"});
	$("#uploadHeadImageSuccess").fadeIn();
	$("#uploadHeadImg").fadeOut();
});



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


$("#alterInfoRequire").click(function(){
	
	$.ajax({  
        type: "GET",  
        url:"/wenda/user/"+ownerId+"/info", 
        dataType:"json",
        async: false,  
        error: function() {  
            alert("您已登出，请先登录");
            window.location.reload();
        },  
        success: function(json) {  
        	userInfo = eval(json);
        }  
    });
	$("#description").val(userInfo.description);
	$("#location").val(userInfo.location);
	if(userInfo.sex=='F')
		$("#female").attr("checked",true);
	if(userInfo.sex=='M')
		$("#male").attr("checked",true);
	if(userInfo.sex=='S')
		$("#secrecy").attr("checked",true);

    if(loginStatus.code==0)
    {
    	$("#alterMessageSuccess").attr("style","display:none");
    	$("#alterInfo").fadeIn();
      	$("#alterInfoRequire").attr("data-target","#infoModal");
      	$("#alterInfoRequire").attr("data-toggle","modal");
    }
});

$("#infoDisplayRequire").click(function(){
	$.ajax({  
        type: "GET",  
        url:"/wenda/user/"+ownerId+"/info", 
        dataType:"json",
        async: false,  
        error: function() {  
            alert("网络异常");  
        },  
        success: function(json) {  
        	userInfo = eval(json);
        	$("#descriptionDisplay").text(userInfo.description);
        	$("#locationDisplay").text(userInfo.location);
        	if(userInfo.sex=='F')
        		$("#sexDisplay").text("女");
        	if(userInfo.sex=='M')
        		$("#sexDisplay").text("男");
        	if(userInfo.sex=='S')
        		$("#sexDisplay").text("保密");
          	$("#infoDisplayRequire").attr("data-target","#infoDisplayModal");
          	$("#infoDisplayRequire").attr("data-toggle","modal");
        }  
    });
	
});


$("#uploadHeadImageRequire").click(function(){
	 
    if(loginStatus.code==0)
    {  
    	$('#headImg').fileinput('refresh');
    	$("#uploadHeadImageSuccess").attr("style","display:none");
      	$("#uploadHeadImg").fadeIn();
      	$("#uploadHeadImageRequire").attr("data-target","#headImgModal");
      	$("#uploadHeadImageRequire").attr("data-toggle","modal");
    }
});

$("#alterInfo").click(function(){
	$.ajax({  
            type: "POST",  
            url:"/wenda/user/"+ownerId+"/info",  
            data:$('#infoForm').serialize(),// 序列化表单值  
            dataType:"json",
            async: false,  
            error: function() {  
            	alert("您已登出，请先登录");
                window.location.reload(); 
            },  
            success: function(json) {  
                loginStatus = eval(json);
                if(loginStatus.code==0)
                {
                	$("#briefIntroductionDisplayInModal").text($("#briefIntroduction").val());
                	$("#briefIntroductionDisplay").text($("#briefIntroduction").val());
                	$("#alterMessageSuccess").fadeIn();
                	$("#alterInfo").fadeOut();
                }else if(loginStatus.code==999)
                {
               		$("#alterMessageUnlogin").fadeIn();
                	$("#alterInfo").fadeOut();
                }
            }  
        });  
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
                    if(questionsResponse.code==2)
	           	     {
	           	    	$("#questionsContent").html(function(i,origText){
	           	     	return "<p>该用户还没有提过问题</p>";
	           	     	});
	           	     }else
	           	     {
	           	    	 $("#questionsContent").html("");
	           	     	for(a in questionsResponse.questionArray)
	           	     	{
	           	     		$("#questionsContent").html(function(i,origText){
	           	     			return origText+ '<p><a href="/wenda/question/' +questionsResponse.questionArray[a].id+ '" style="color:black"><em><h3>'
	           	     			+questionsResponse.questionArray[a].title
	           	     			+'</h3></em></a><span style="color:gray">发布于:'+questionsResponse.questionArray[a].createdDate+'</span></p><hr>';
	           	     		});
	           	     
	           	    	}
	           	     }
                }
	       });
	}
});

$("#requireCollections").click(function(){
	 if(lastClick==$(this).attr("id"));
	 else
	 {
		 lastClick=$(this).attr("id");
		 var collectionsResponse;
		 $.ajax({  
               type: "GET",  
               url:"/wenda/follow/questions/"+ownerId,  
               dataType:"json",
               async: false,  
               error: function() {  
                   alert("网络异常");  
               },
               success: function(json) {  
            	   collectionsResponse=eval(json);
               }
	           });
	     if(collectionsResponse.code==2)
	     {
	    	$("#collectionsContent").html(function(i,origText){
	     	return "<p>该用户还没有收藏过问题</p>";
	     	});
	     }else
	     {
	    	 $("#collectionsContent").html("");
	     	for(a in collectionsResponse.questionArray)
	     	{
	     		$("#collectionsContent").html(function(i,origText){
	     			return origText+  '<p><a href="/wenda/question/' +collectionsResponse.questionArray[a].id+ '" style="color:black"><em><h3>'
	     			+collectionsResponse.questionArray[a].title
	     			+'</h3></em></a><span style="color:gray">发布于:'+collectionsResponse.questionArray[a].createdDate+'</span></p><hr>';
	     		});
	     
	    	}
	     }
	}
});

$("#requireFollows").click(function(){
	 if(lastClick==$(this).attr("id"));
	 else
	 {
		 lastClick=$(this).attr("id");
		 var followsResponse;
		 $.ajax({  
              type: "GET",  
              url:"/wenda/follow/users/"+ownerId,  
              dataType:"json",
              async: false,  
              error: function() {  
                  alert("网络异常");  
              },
              success: function(json) {  
           	   followsResponse=eval(json);
	           	if(followsResponse.code==2)
		   	     {
		   	    	$("#followsContent").html(function(i,origText){
		   	     	return "<p>该用户还没有关注过其他人</p>";
		   	     	});
		   	     }else
		   	     {
		   	    	 $("#followsContent").html("");
		   	     	for(a in followsResponse.userArray)
		   	     	{
		   	     		$("#followsContent").html(function(i,origText){
		   	     			//followsResponse.userArray[a]
		   	     			return origText+  
		   	     			'<div class="row"><div class="col-md-1"><a href="/wenda/user/'+followsResponse.userArray[a].id+
		   	     			'"><img alt="关注人头像" class="img-rounded headimgSize" src="'
		   	     			+followsResponse.userArray[a].headUrl+'"></a></div><div class="col-md-8"><a href="/wenda/user/'
		   	     			+followsResponse.userArray[a].id+'"><strong style="margin-left:20px">'+followsResponse.userArray[a].username+
		   	     			'<strong></a></div></div><hr>';
		   	     		});
		   	    	}
		   	     }
	         }
	    });
	}
});

$("#followToggle").click(function(){
	 if(loginStatus.code==999)
	 {
		 alert("请先登录");
	 }
	 else
	 {
		 var followTableId = $("#followTableId").text();
		 //未关注
		 if(followTableId=="")
		 {
			 $.ajax({  
	               type: "POST",  
	               url:"/wenda/follow/user/"+ownerId,  
	               dataType:"json",
	               async: false,  
	               error: function() {  
	                   alert("网络异常");  
	               },
	               success: function(json) {  
	                   loginStatus = eval(json);
	                   if(loginStatus.code==0||loginStatus.code==3)
	                   {
	                	   $("#followTableId").text(loginStatus.msg);
	                	   $("#followToggle").text("取消关注");
	                   }else if(loginStatus.code==999)
	                   {
	                	   alert("未登录");
	                   }
	                   
	               }
	          });
		 }
		 //否则
		 else
		 {
			 $.ajax({  
	               type: "POST",  
	               url:"/wenda/follow/cancel/"+followTableId,  
	               dataType:"json",
	               async: false,  
	               error: function() {  
	                   alert("网络异常");  
	               },
	               success: function(json) {  
	                   loginStatus = eval(json);
	                   if(loginStatus.code==0)
	                   {
	                	   $("#followTableId").text("");
	                	   $("#followToggle").text("关注Ta");
	                   }else if(loginStatus.code==999)
	                   {
	                	   alert("未登录");
	                   }
	               }
	          });
		 }
    }
});

$("#requireFollowers").click(function(){
	 if(lastClick==$(this).attr("id"));
	 else
	 {
		 lastClick=$(this).attr("id");
		 var followersResponse;
		 $.ajax({  
             type: "GET",  
             url:"/wenda/follow/followers/"+ownerId,  
             dataType:"json",
             async: false,  
             error: function() {  
                 alert("网络异常");  
             },
             success: function(json) {  
            	 followersResponse=eval(json);
	           	if(followersResponse.code==2)
		   	     {
		   	    	$("#followersContent").html(function(i,origText){
		   	     	return "<p>该用户暂时还没有粉丝</p>";
		   	     	});
		   	     }else
		   	     {
		   	    	 $("#followersContent").html("");
		   	     	for(a in followersResponse.userArray)
		   	     	{
		   	     		$("#followersContent").html(function(i,origText){
		   	     			//followsResponse.userArray[a]
		   	     			return origText+  
		   	     			'<div class="row"><div class="col-md-1"><a href="/wenda/user/'+followersResponse.userArray[a].id+
		   	     			'"><img alt="粉丝头像" class="img-rounded headimgSize" src="'
		   	     			+followersResponse.userArray[a].headUrl+'"></a></div><div class="col-md-8"><a href="/wenda/user/'
		   	     			+followersResponse.userArray[a].id+'"><strong style="margin-left:20px">'+followersResponse.userArray[a].username+
		   	     			'<strong></a></div></div><hr>';
		   	     		});
		   	    	}
		   	     }
	         }
	    });
	}
});

