$(function(){
	var page=parseInt($("#page").text());
	var pageCount=parseInt($("#pageCount").text());
	if(page==1)
	{
		$("#prev").addClass("disabled");
		$("#prev").addClass("btn");
	}else
	{
		$("#prev").attr("href","/wenda/?page="+(page-1));
	}
	if(page==pageCount)
	{
		$("#next").addClass("disabled");
		$("#next").addClass("btn");
	}else
	{
		$("#next").attr("href","/wenda/?page="+(page+1));
	}
	
	$("#page_1").text("1");
	$("#page_1").attr("href","/wenda/?page=1");
	$("#page_5").text(pageCount);
	$("#page_5").attr("href","/wenda/?page="+pageCount);
	$("#page_li_3").addClass("active");
	$("#page_3").text(page);
	$("#page_3").attr("href","/wenda/?page="+page);
	
	var page2 =  Math.floor((page+1)/2);
	$("#page_2").text(page2);
	$("#page_2").attr("href","/wenda/?page="+page2);
	
	var page4 =  Math.ceil((page+pageCount)/2);
	$("#page_4").text(page4);
	$("#page_4").attr("href","/wenda/?page="+page4);
	
	
});


$("a[id^='delete_']").click(function(){
	var questionId = $(this).attr("id").substr(7);
	var r = confirm("确认删除?")
	if(r == true){
		$.ajax({  
            type: "POST",  
            url:"/wenda/question/delete/"+questionId,  
            async: false,  
            error: function() {  
                alert("网络异常");  
            },
            success:function(){
            	window.location.href = "/wenda";
            }
  
       });
	}else;
})