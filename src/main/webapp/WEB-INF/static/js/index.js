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
	$("#page_7").text(pageCount);
	$("#page_7").attr("href","/wenda/?page="+pageCount);
	$("#page_li_4").addClass("active");
	$("#page_4").text(page);
	$("#page_4").attr("href","/wenda/?page="+page);


	var page3 = page-1 ;
	if(page3<=1){
		$("#page_3").text("...");
		$("#page_3").addClass("disabled");
		$("#page_3").addClass("btn");
	}
	else{
		$("#page_3").text(page3);
		$("#page_3").attr("href","/wenda/?page="+page3);
	}

	var page5 = page+1 ;
	if(page5>=pageCount){
		$("#page_5").text("...");
		$("#page_5").addClass("disabled");
		$("#page_5").addClass("btn");
	}
	else{
		$("#page_5").text(page5);
		$("#page_5").attr("href","/wenda/?page="+page5);
	}

	var page2=Math.floor((page+1)/2);
	if(page2==page||page2>=page3){
		$("#page_2").text("...");
		$("#page_2").addClass("disabled");
		$("#page_2").addClass("btn");
	}
	else{
		$("#page_2").text(page2);
		$("#page_2").attr("href","/wenda/?page="+page2);
	}
	var page6 =  Math.ceil((page+pageCount)/2);
	if(page6==pageCount||page6<=page5){
		$("#page_6").text("...");
		$("#page_6").addClass("disabled");
		$("#page_6").addClass("btn");
	}
	else{
		$("#page_6").text(page6);
		$("#page_6").attr("href","/wenda/?page="+page6);
	}
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
});