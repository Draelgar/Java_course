package deadlock;

public class LockThread2 extends Thread {
	public void run() {
		synchronized(Program.lock2) {
			System.out.println("T2 Lock 2");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				
			}
			System.out.println("Waiting for lock 1");
			synchronized(Program.lock1) {
				System.out.println("T2 Lock 1");
			}
		}
	}

}
