/**
 * 
 */

 $(function() {
	var close = $('.close');
	var sns = $('.sns');
	
	var height = $('body').height();

    $(window).scroll(function () {
        if($(this).scrollTop() > 200) {
            close.css('z-index', 0);
        } else {
            close.css('z-index', -1);
        }
    });
    
    $(window).scroll(function () {
        if($(this).scrollTop() > height/2-20) {
            sns.css('z-index', 0);
        } else {
            sns.css('z-index', -1);
        }
    });

	close.click(function () {
		$('body,html').animate({scrollTop: 0}, 1500);
		return false;
	});
});
