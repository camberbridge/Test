import java.util.*;

class Worker extends Thread{
	protected static final int n = 10;
	private Toolbox toolbox;  
	private int id;
	private Tool tools[];  //Tool�^�z��錾
	
	Worker(Toolbox toolbox, int id){
		this.toolbox = toolbox;
		this.id = id;
	}
	
	public void run(){
		try{
			while(true){
				tools = toolbox.getTools();  //2�̍H����擾����(Tool�^�z��̏�����)
				//synchronized�ɂ���K�v����
				
				Thread.sleep((int)(10000 + 10000 * Math.random()));
				//10�b�ȏ�20�b�����ҋ@
				
				toolbox.releaseTools(tools);  //2�̍H����g�p���A���
				//synchronized�ɂ���K�v����
				
				Thread.sleep((int)(20000));  //20�b�ҋ@
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
			stack.push(new Tool(i));  //�X�^�b�N�Ƀv�b�V���B�I�u�W�F�N�g�𐶐�&�n��
		}
	}
	
	synchronized Tool[] getTools(){
		while(true){
			//2�̃c�[�������p�\�ł��邩�ǂ������`�F�b�N
			if(stack.size() >= 2){
				Tool tool0 = stack.pop();  //�X�^�b�N��1�ԏ�̃I�u�W�F�N�g��Ԃ��A������X�^�b�N����폜
				Tool tool1 = stack.pop();
				System.out.println("Get tools " + tool0 + " " + tool1);
				Tool a[] = new Tool[2];
				a[0] = tool0;
				a[1] = tool1;
				
				return a;  //Tool�^a�z���Ԃ�
			}
			
			//2�̃c�[�������p�\�ɂȂ�܂őҋ@
			try{
				wait();
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	synchronized void releaseTools(Tool a[]){
		//�c�[���{�b�N�X�Ƀc�[����߂�
		System.out.println("Release tools " + a[0] + " " + a[1]);
		stack.push(a[0]);
		stack.push(a[1]);
		
		//�S�Ă̑ҋ@�X���b�h�ɒʒm����
		notifyAll();
	}
}

public class WorkersTools {
	public static void main(String args[]){
		//Toolbox�I�u�W�F�N�g���쐬����
		Toolbox toolbox = new Toolbox();
		
		//Worker�I�u�W�F�N�g���쐬����
		for(int i = 0; i < Worker.n; i++){
			new Worker(toolbox, i).start();
		}
	}
}
