import java.util.*;

class Worker extends Thread{
	protected static final int n = 10;
	private Toolbox toolbox;  
	private int id;
	private Tool tools[];  //Tool型配列宣言
	
	Worker(Toolbox toolbox, int id){
		this.toolbox = toolbox;
		this.id = id;
	}
	
	public void run(){
		try{
			while(true){
				tools = toolbox.getTools();  //2つの工具を取得する(Tool型配列の初期化)
				//synchronizedにする必要あり
				
				Thread.sleep((int)(10000 + 10000 * Math.random()));
				//10秒以上20秒未満待機
				
				toolbox.releaseTools(tools);  //2つの工具を使用し、解放
				//synchronizedにする必要あり
				
				Thread.sleep((int)(20000));  //20秒待機
			}
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}

class Tool{
	protected static final int n = 4;
	private int id;
	
	Tool(int id){
		this.id = id;
	}
	
	public String toString(){
		return "" + id;
	}
}

class Toolbox{
	private Stack<Tool> stack = new Stack<Tool>();
	
	Toolbox(){
		for(int i = 0; i < Tool.n; i++){
			stack.push(new Tool(i));  //スタックにプッシュ。オブジェクトを生成&渡す
		}
	}
	
	synchronized Tool[] getTools(){
		while(true){
			//2つのツールが利用可能であるかどうかをチェック
			if(stack.size() >= 2){
				Tool tool0 = stack.pop();  //スタックの1番上のオブジェクトを返し、それをスタックから削除
				Tool tool1 = stack.pop();
				System.out.println("Get tools " + tool0 + " " + tool1);
				Tool a[] = new Tool[2];
				a[0] = tool0;
				a[1] = tool1;
				
				return a;  //Tool型a配列を返す
			}
			
			//2つのツールが利用可能になるまで待機
			try{
				wait();
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	synchronized void releaseTools(Tool a[]){
		//ツールボックスにツールを戻す
		System.out.println("Release tools " + a[0] + " " + a[1]);
		stack.push(a[0]);
		stack.push(a[1]);
		
		//全ての待機スレッドに通知する
		notifyAll();
	}
}

public class WorkersTools {
	public static void main(String args[]){
		//Toolboxオブジェクトを作成する
		Toolbox toolbox = new Toolbox();
		
		//Workerオブジェクトを作成する
		for(int i = 0; i < Worker.n; i++){
			new Worker(toolbox, i).start();
		}
	}
}
