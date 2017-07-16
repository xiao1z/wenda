var E = window.wangEditor;
var editor = new E('#editor');
editor.customConfig.uploadImgShowBase64 = true;
editor.customConfig.zIndex = 0
editor.create();


$(document).ready(function(){
	
});

//id属性以subCommentCommit开始的所有button标签 
$("button[id^='subCommentCommit_']").click(function(){
	 var commentId = $(this).attr("id").substr(17);
	 var textAreaId = "subCommentContent_"+commentId;
	 var content_ = $("#"+textAreaId).val();
	 if(content_==""||loginStatus.code==999);
	 else
	 {
	   	 $.ajax({  
                type: "POST",  
                url:"/wenda/comment/comment/"+commentId,  
                dataType:"json",
                data:{"content":content_},
                async: false,  
                error: function() {  
                    alert("网络异常");  
                },
                success: function(json) {  
                    loginStatus = eval(json);
                }
           });
    }
    if(loginStatus.code==0)
    {
    	location.reload(true);  
    }else if(loginStatus.code==999)
    {
   		$("#submitUnlogin_comment_inComment_"+commentId).fadeIn();
    }
	
});

//id属性以like开始的所有a标签 
$("a[id^='like_']").click(function(){
	 if($(this).hasClass("btn-info"));
	 else
	 {
    	 var commentId = $(this).attr("id").substr(5);
    	 
    	 if(loginStatus.code==999);
   		 else
   		 {
		   	 $.ajax({  
	                type: "POST",  
	                url:"/wenda/like/comment/"+commentId,  
	                dataType:"json",
	                async: false,  
	                error: function() {  
	                    alert("网络异常");  
	                },
	                success: function(json) {  
	                    loginStatus = eval(json);
	                }
	           });
	    }
	    
	    if(loginStatus.code==0)
        {
        	$("#likeCount_"+commentId).text(loginStatus.msg); 
        	$(this).addClass("btn-info");
        	$("#dislike_"+commentId).removeClass("btn-info");
        }else if(loginStatus.code==999)
        {
       		alert("登录后才能赞踩哦");
        }
    }
	
});

 $("a[id^='dislike_']").click(function(){
 	 if($(this).hasClass("btn-info"));
	 else
	 {
    	 var commentId = $(this).attr("id").substr(8);
    	 
    	 if(loginStatus.code==999);
   		 else
   		 {
		   	 $.ajax({  
	                type: "POST",  
	                url:"/wenda/dislike/comment/"+commentId,  
	                dataType:"json",
	                async: false,  
	                error: function() {  
	                    alert("网络异常");  
	                },
	                success: function(json) {  
	                    loginStatus = eval(json);
	                }
	           });
	    }
	    
	    if(loginStatus.code==0)
        {
        	$("#likeCount_"+commentId).text(loginStatus.msg); 
        	$("#like_"+commentId).removeClass("btn-info");
        	$(this).addClass("btn-info");
        }else if(loginStatus.code==999)
        {
       		alert("登录后才能赞踩哦");
        }
    }
	
});

$("#commentSubmit").click(function(){
	 var questionId = $("#questionId").text();
	 var content = editor.txt.text();
	 if(content==""||loginStatus.code==999);
	 else
	 {
	   	 $.ajax({  
                type: "POST",  
                url:"/wenda/comment/question/"+questionId,  
                dataType:"json",
                data:{"content":content},
                async: false,  
                error: function() {  
                    alert("网络异常");  
                },
                success: function(json) {  
                    loginStatus = eval(json);
                }
           });
    }
    if(loginStatus.code==0)
    {
    	location.reload(true);  
    }else if(loginStatus.code==999)
    {
   		$("#submitUnlogin_comment").fadeIn();
    }
	
});

$("#collectToggle").click(function(){
	 if(loginStatus.code==999)
	 {
		 alert("请先登录")
	 }
	 else
	 {
		 var questionId = $("#questionId").text();
		 var followTableId = $("#followTableId").text();
		 //未关注
		 if(followTableId=="")
		 {
			 $.ajax({  
	               type: "POST",  
	               url:"/wenda/follow/question/"+questionId,  
	               dataType:"json",
	               async: false,  
	               error: function() {  
	                   alert("网络异常");  
	               },
	               success: function(json) {  
	                   loginStatus = eval(json);
	                   if(loginStatus.code==0)
	                   {
	                	   $("#followTableId").text(loginStatus.msg);
	                	   $("#collectToggle").text("取消收藏");
	                   }else if(loginStatus.code==999)
	                   {
	                	   alert("未登录");
	                   }
	                   
	               }
	          });
		 }else
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
	                	   $("#collectToggle").text("收藏");
	                   }else if(loginStatus.code==999)
	                   {
	                	   alert("未登录");
	                   }
	                   
	               }
	          });
		 }
     }
});