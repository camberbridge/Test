class K{
	boolean flag;  //boolean�̏����l�́A�U(false)
	
	synchronized void k1(){
		if(flag == false){
			flag = true;
			try{
				System.out.println("Calling wait");
				wait();  //���̃X���b�h�͖������ɑҋ@�B���̃X���b�h����̒ʒm��������A�ĊJ����B
				
				//�����ȍ~�̏�����notify()�Œʒm��������ĊJ
				
				System.out.println("Hey");
			}
			catch(InterruptedException e){  //Wait()���\�b�h�̗�O
				e.printStackTrace();
			}
		}else{
			flag = false;
			System.out.println("calling notifyAll");
			notifyAll();  /*����synchronized���\�b�h�𔲂���Ƃ��A���b�N��������āA
			               *����K�N���X�̃I�u�W�F�N�g�̃��b�N�����ҋ@���Ă���X���b�h
			               *�ɒʒm����B
			               */
		}
	}
}

class ThreadK extends Thread{
	K k;
	
	ThreadK(K k){  //�R���X�g���N�^
		this.k = k;
	}
	
	public void run(){
		k.k1();
		System.out.println("Done");
	}
}

public class waitNotifyDemo {
	public static void main(String args[]){
		K k = new K();
		
		new ThreadK(k).start(); 
		//ThreadK t = new ThreadK();
		//t.start();
		new ThreadK(k).start();;
	}
}
