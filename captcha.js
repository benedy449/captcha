$(document).ready(function() {

	loadCheckImage("ckimg");
	 $("#reckimg").click(function(){
		 loadCheckImage("ckimg");
	 });
	 
});





//驗證碼
function loadCheckImage(imgid){
	var toUrl = "checkpingcode.do";
	$.ajax({
	    'url': toUrl,
	    'type': 'POST',
	    'success': function(result){
	    	$("#"+imgid).attr("src","data:image/jpg;base64,"+result);
	    }
	});
}