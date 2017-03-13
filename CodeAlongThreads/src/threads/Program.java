package threads;

public class Program {
	public static void main(String[] args) {
		SimpleThread st = new SimpleThread(1000);
		SimpleThread st2 = new SimpleThread(1500);
		st.start();
		st2.start();
		
		try {
			while (st.isAlive() || st2.isAlive()) {
				System.out.println("Main thread will be alive as long as any child threads is alive.");
				
				Thread.sleep(2000);
			}
		} catch (IllegalThreadStateException e) {
			System.out.println("Too many starts! " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("Main thread was interrupted! " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("Main thread is done!");
	}
}
