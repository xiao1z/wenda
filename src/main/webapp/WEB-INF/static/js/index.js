$(function(){
	var page=$("#page").text();
	var hasMore=$("#hasMore").text();
	if(page<=4)
	{
		if(page==1)
		{
			$("#prev").addClass("disabled");
			$("#prev").addClass("btn");
		}else
		{
			$("#prev").attr("href","/wenda/?page="+(page-1));
		}	
		$("#page_li_"+page).addClass("active");
		if(hasMore==-1)
		{
			for(var i=page;i<6;i++)
			{
				$("#page_"+i).addClass("disabled");
				$("#page_"+i).addClass("btn");
				$("#next").addClass("disabled");
				$("#next").addClass("btn");
			}
		}
		$("#next").attr("href","/wenda/?page="+(page-(-1)));
	}else
	{
		$("#page_4").text(page);
		$("#page_4").attr("href","/wenda/?page="+page);
		$("#page_li_4").addClass("active");
		$("#prev").attr("href","/wenda/?page="+(page-1));
		$("#next").attr("href","/wenda/?page="+(page-(-1)));
		
	
		$("#page_1").text(page-3);
		$("#page_1").attr("href","/wenda/?page="+(page-3));
		$("#page_2").text(page-2);
		$("#page_2").attr("href","/wenda/?page="+(page-2));
		$("#page_3").text(page-1);
		$("#page_3").attr("href","/wenda/?page="+(page-1));
		$("#page_5").text(page-(-1));
		$("#page_5").attr("href","/wenda/?page="+(page-(-1)));
		
		if(hasMore==-1)
		{
			$("#page_5").addClass("disabled");
			$("#page_5").addClass("btn");
			$("#next").addClass("disabled");
			$("#next").addClass("btn");
		}
	}
	
});