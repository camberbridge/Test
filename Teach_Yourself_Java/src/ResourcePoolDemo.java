class Resource{
	boolean allocated;
	int rid;
	
	Resource(int rid){ //�R���X�g���N�^
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
	
	ResourceCoordinator(){  //�R���X�g���N�^
		totalAllocated = 0;  //������
		
		//���\�[�X���쐬����
		resource = new Resource[n];  //������	
		for(int i = 0; i < n; i++){
			resource[i] = new Resource(i);
		}
	}
	
	synchronized Resource get(){
		//�g�p�\�ȃ��\�[�X��ҋ@����
		while(totalAllocated == n){
			try{
				wait();
			}
			catch(InterruptedException e){
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		//�g�p�\�ȃ��\�[�X�����蓖�Ă�
		Resource r = null;
		for(int i = 0; i < n; i++){
			if(resource[i].allocated == false){
				resource[i].allocated = true;
				r = resource[i];
				break;
			}
		}
		
		//totalAllocated��1�𑫂�
		++totalAllocated;
		
		//�ҋ@���̃X���b�h�ɒʒm�𑗂�
		notifyAll();
		
		//���\�[�X��Ԃ�
		return r;
	}
	
	synchronized void put(Resource r){
		//���\�[�X�Ɏg�p�\�̃}�[�N��t����
		r.allocated = false;
		
		//totalAllocated����P������
		--totalAllocated;
		
		//�ҋ@���̃X���b�h�ɒʒm�𑗂�
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
		//���\�[�X�̒�������쐬����
		ResourceCoordinator rc = new ResourceCoordinator();
		
		//���[�U�X���b�h���쐬���ċN������
		for(int i = 0; i < n; i ++){
			new User(rc, i).start();
		}
	}
}
