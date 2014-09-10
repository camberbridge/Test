class NormPriorityThread extends Thread{
	public void run(){
		for(int i = 0; i < 20; i++){
			System.out.println("Normal priority thread");
		}
	}
}

class LowPriorityThread extends Thread{
	public void run(){
		setPriority(MIN_PRIORITY);  //インスタンスメソッド。スレッドの優先順位を１にする。
		try{
			for(int i = 0; i < 1000; i++){
				Thread.sleep(1000);
				System.out.println("Low priority thread");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

public class PriorityDemo {
	public static void main(String args[]){
		LowPriorityThread lpt = new LowPriorityThread();
		lpt.start();
		
		try{
			Thread.sleep(2000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		
		NormPriorityThread npt = new NormPriorityThread();
		npt.start();
	}
}
