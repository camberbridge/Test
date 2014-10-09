/**
 * cssでアニメーション by transition
 */
$(function(){
  	//$('.jst-mr').transition({
    //	translate: [100]  // 右に100px移動
 	// });
   		
   	$('.jst-move').hover(
   		function(){
    		$(this).transition({
    			scale: 3  //マウスを合わせたら３倍に
    		});
    	},
    	function(){
    		$(this).transition({
    			scale: 1  //マウスリリースで元通り
    		}).stop();
    	}
    );
    		
    $('.jst-kurukuru').hover(
    	function(){
    		$(this).transition({
    			perspective: '100px',
    			rotateX: '180deg'
    		});
    	},
    	function(){
    		$(this).transition({
    			perspective: '100px',
    			rotateX: '360deg'
    		});
    	}
    );
    		
    $('.jst-ml').hover(
    	function(){
    		$(this).transition({ x: -150 })
    			   .transition({ y: 150 })
    			   .transition({ x: 0 })
    			   .transition({ y: 0 });
    	}
    );
    		
    $('.jst-mr').hover(
    	function(){
    		$(this).transition({
				x: 200, y: 200,
				skewX: '60deg' 
    		});
    	},
    	function(){
    		$(this).transition({
    			x: 0, y: 0,
    			skewX: '0deg'
    		}, 'easeInCirc');
    	}
    );
});
