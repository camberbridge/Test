/**
 * ���[�ƁA�����N���N���b�N������W�����v���āA���̃����N��2��ȏ�N���b�N�����Ȃ�����B
 * �ł��A�������̃����N���N���b�N������A�}�E�X���[�����s�����ꍇ�́A���̃J�E���g�����������ăN���b�N���܂��\�ɂ���B
 * �^�b�`��ʂȒ[���̃X���C�v�Ń}�E�X���[���C�x���g�𓊂����邩�͂킩��ʁB
 */
var $counta = 0;
var $countb = 0;
var $countc = 0;
  
function osari(){
  	 ++$counta;
  	 if($counta < 2){
	  	document.getElementById("a").innerHTML = $counta;
	  	location.href="#hogea";
	  	$countb = 0;
	  	$countc = 0;
	  	document.getElementById("b").innerHTML = $countb;
	  	document.getElementById("c").innerHTML = $countc;
	 }
}
  	
function singo(){
  	++$countb;
  	if($countb < 2){
  		document.getElementById("b").innerHTML = $countb;
  		location.href="#hogeb";
  		$counta = 0;
  		$countc = 0;
  		document.getElementById("a").innerHTML = $counta;
  		document.getElementById("c").innerHTML = $countc;
  	}
}

function ringo(){
  	++$countc;
  	if($countc < 2){
  		document.getElementById("c").innerHTML = $countc;
  		location.href="#hogec";
  		$counta = 0;
  		$countb = 0;
  		document.getElementById("a").innerHTML = $counta;
  		document.getElementById("b").innerHTML = $countb;
  	}
}
  	  	
$(function(){
  	var mousewheelevent = 'onwheel' 
  		                   in 
  		                   document ? 'wheel' : 'onmousewheel' 
  		                   in 
  		                   document ? 'mousewheel' : 'DOMMouseScroll';
	$(document).on(mousewheelevent, function(e){
		e.preventDefault();
		var delta = e.originalEvent.deltaY ? -(e.originalEvent.deltaY) :
			        e.originalEvent.wheelDelta ? e.originalEvent.wheelDelta :
			        -(e.originalEvent.detail);
		if(delta < 0){
			$counta = 0;
			$countb = 0;
			$countc = 0;
	  		document.getElementById("a").innerHTML = $counta;
	  		document.getElementById("b").innerHTML = $countb;
			document.getElementById("c").innerHTML = $countc;
		}else{
			$counta = 0;
			$countb = 0;
			$countc = 0;
	  		document.getElementById("a").innerHTML = $counta;
	  		document.getElementById("b").innerHTML = $countb;
			document.getElementById("c").innerHTML = $countc;
		}
	});
});