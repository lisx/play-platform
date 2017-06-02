$(function(){
	$('.content_bj').css('top',$('.header').outerHeight() + $('.container').outerHeight());
	$('.content').css('height',$('.content_bj',window.parent.document).outerHeight() -58)
})