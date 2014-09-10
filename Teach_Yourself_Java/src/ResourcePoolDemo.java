class Resource{
	boolean allocated;
	int rid;
	
	Resource(int rid){ //コンストラクタ
		this.allocated = false;
		this.rid = rid;
	}
	
	void use(int uid){
		try{
			System.out.println("User " + uid + " users resource " + this.rid);
			Thread.sleep((int)(5000 + 5000 * Math.random()));
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}

class ResourceCoordinator {
	private static final int n = 3;
	private int totalAllocated;
	private Resource resource[];
	
	ResourceCoordinator(){  //コンストラクタ
		totalAllocated = 0;  //初期化
		
		//リソースを作成する
		resource = new Resource[n];  //初期化	
		for(int i = 0; i < n; i++){
			resource[i] = new Resource(i);
		}
	}
	
	synchronized Resource get(){
		//使用可能なリソースを待機する
		while(totalAllocated == n){
			try{
				wait();
			}
			catch(InterruptedException e){
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		//使用可能なリソースを割り当てる
		Resource r = null;
		for(int i = 0; i < n; i++){
			if(resource[i].allocated == false){
				resource[i].allocated = true;
				r = resource[i];
				break;
			}
		}
		
		//totalAllocatedに1を足す
		++totalAllocated;
		
		//待機中のスレッドに通知を送る
		notifyAll();
		
		//リソースを返す
		return r;
	}
	
	synchronized void put(Resource r){
		//リソースに使用可能のマークを付ける
		r.allocated = false;
		
		//totalAllocatedから１を引く
		--totalAllocated;
		
		//待機中のスレッドに通知を送る
		notifyAll();
	}
}

class User extends Thread{
	ResourceCoordinator rc;
	int uid;
	
	User(ResourceCoordinator rc, int uid){
		this.rc = rc;
		this.uid = uid;
	}
	
	public void run(){
		try{
			while(true){
				Resource r = rc.get();
				r.use(uid);
				rc.put(r);
				Thread.sleep(3000);
			}
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}

public class ResourcePoolDemo {
	private static final int n = 10;
	
	public static void main(String args[]){
		//リソースの調停役を作成する
		ResourceCoordinator rc = new ResourceCoordinator();
		
		//ユーザスレッドを作成して起動する
		for(int i = 0; i < n; i ++){
			new User(rc, i).start();
		}
	}
}
