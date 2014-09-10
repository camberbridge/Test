class K{
	boolean flag;  //booleanの初期値は、偽(false)
	
	synchronized void k1(){
		if(flag == false){
			flag = true;
			try{
				System.out.println("Calling wait");
				wait();  //このスレッドは無期限に待機。他のスレッドからの通知が来次第、再開する。
				
				//ここ以降の処理はnotify()で通知が来たら再開
				
				System.out.println("Hey");
			}
			catch(InterruptedException e){  //Wait()メソッドの例外
				e.printStackTrace();
			}
		}else{
			flag = false;
			System.out.println("calling notifyAll");
			notifyAll();  /*このsynchronizedメソッドを抜けるとき、ロックを解放して、
			               *このKクラスのオブジェクトのロック解放を待機しているスレッド
			               *に通知する。
			               */
		}
	}
}

class ThreadK extends Thread{
	K k;
	
	ThreadK(K k){  //コンストラクタ
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
