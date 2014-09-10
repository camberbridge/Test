class Account{
	private int balance = 0;
	
	synchronized void deposit(int amount){
		balance += amount;
	}
	
	int getBalance(){  //
		return balance;
	}
}

class Customer extends Thread{
	Account account;
	
	Customer(Account account){
		this.account = account;
	}
	
	public void run(){
		try{
			for(int i = 0; i < 100000; i++){
				account.deposit(10);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

public class BankDemo {
	private static final int n = 10;
	
	public static void main(String args[]){
		Account account = new Account();
		
		Customer customer[] = new Customer[n];
		for(int i = 0; i < n; i++){
			customer[i] = new Customer(account);
			customer[i].start();
		}
		
		for(int i = 0; i < n; i++){
			try{
				customer[i].join();
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}		
		}
		
		System.out.println(account.getBalance());
	}
}
