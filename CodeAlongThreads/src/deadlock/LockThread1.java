package deadlock;

public class LockThread1 extends Thread {
	public void run() {
		synchronized(Program.lock1) {
			System.out.println("T1 Lock 1");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				
			}
				System.out.println("Waiting for Lock 2");
				synchronized(Program.lock2){
					System.out.println("T1 Lock 2");
				}
			}
	}
}
