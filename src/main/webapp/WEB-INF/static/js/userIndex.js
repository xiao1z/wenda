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

