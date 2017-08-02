var observe;
var questionId = $("#questionId").text();
var extraCommentId;
var successUploaded = 0;
var filesCount = 0;
if (window.attachEvent) {
    observe = function (element, event, handler) {
        element.attachEvent('on'+event, handler);
    };
}
else {
    observe = function (element, event, handler) {
        element.addEventListener(event, handler, false);
    };
}
function init (textareaId) {
    var text = document.getElementById(textareaId);
    function resize () {
        text.style.height = 'auto';
        text.style.height = text.scrollHeight+'px';
    }
    /* 0-timeout to get the already changed text */
    function delayedResize () {
        window.setTimeout(resize, 0);
    }
    observe(text, 'change',  resize);
    observe(text, 'cut',     delayedResize);
    observe(text, 'paste',   delayedResize);
    observe(text, 'drop',    delayedResize);
    observe(text, 'keydown', delayedResize);


    resize();
}

function getExtraData(index)
{
	var obj = {};
	obj["commentId"] = extraCommentId;
    obj["offset"] = index+1;//index表示上传的第几个文件，从0开始
    return obj;
}

function initFileInput(ctrlName,uploadUrl) 
{      
    var control = $('#' + ctrlName);   
    control.fileinput({  
        language: 'zh', //设置语言  
        uploadUrl: uploadUrl,  //上传地址  
        uploadAsync:true, 
        showCaption: true, 
        dropZoneEnabled:false,
        uploadExtraData:function (previewId, index) {
            return getExtraData(index);
        },
        
        showUpload: false,//是否显示上传按钮 
        showRemove: true,//是否显示删除按钮 
        showCaption: true,//是否显示输入框 
        showPreview:true, 
        showCancel:true,  
        maxFileCount: 128, 
        initialPreviewShowDelete:true, 
        layoutTemplates:
        {
        	actionUpload:'',
        },
        previewSettings:
        {
        	 image: {width: "240px", height: "160px"}
        },
        allowedPreviewTypes: ['image'],  
        allowedFileTypes: ['image'],  
        allowedFileExtensions:  ['jpg', 'png'],  
        maxFileSize : 20000,    
    });
}  


$(function(){
	init ('questionComment');
	$("textarea[id^='subCommentContent_']").each(function(){
	    init($(this).attr("id"));
	  });
	
	var path="/wenda/comment/question/"+questionId+"/img";  
    initFileInput("commentImg",path);  
	
	//根据Url #后的id进行定位
    var url = window.location.toString();
    var id = url.split("#")[1];
    if(id){
      var t = $(id).offset().top;
      $(window).scrollTop(t);
   }
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
                    if(loginStatus.code==0)
                    {
                    	location.reload(true);  
                    }else if(loginStatus.code==999)
                    {
                   		$("#submitUnlogin_comment_inComment_"+commentId).fadeIn();
                    }
                }
           });
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

 $('#commentImg').on('fileuploaded', function(event, data, previewId, index) {
	 successUploaded++;
	 if(successUploaded==filesCount)
		 location.reload(true);  
	});

 
$("#commentSubmit").click(function(){
	 //var questionId = $("#questionId").text();
	 var content = $("#questionComment").val();
	 if(content=="")
		 $("#submitComment_emptyContent").fadeIn();
	 else if(loginStatus.code==999)
	 {
	 	 $("#submitUnlogin_comment").fadeIn();
	 }
	 else
	 {
		 filesCount = $('#commentImg').fileinput('getFilesCount');
	   	 $.ajax({  
                type: "POST",  
                url:"/wenda/comment/question/"+questionId,  
                dataType:"json",
                data:{"content":content,"filesCount":filesCount},
                async: true,  
                error: function() {  
                    alert("网络异常");  
                },
                success: function(json) {  
                    loginStatus = eval(json);
                    if(loginStatus.code==0)
                    {
                    	extraCommentId = loginStatus.msg;
                    	if(filesCount!=0)
                    		$("#commentImg").fileinput('upload');
                    }else if(loginStatus.code==999)
                    {
                   		$("#submitUnlogin_comment").fadeIn();
                    }
                }
           });
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