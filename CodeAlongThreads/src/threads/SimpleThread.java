package threads;

public class SimpleThread extends Thread {
	private long mMilliseconds;
	
	public SimpleThread(long milliseconds) {
		System.out.println("SimpleThread Created!");
		mMilliseconds = milliseconds;
	}

	public void run() {
		try {
			for (int i = 0; i < 11; i++) {
				System.out.println("Current count: " + i);
	
				sleep(mMilliseconds);
			}
		} catch (InterruptedException e) {
			System.out.println("Thread was interrupted : " + e.getMessage());
		} finally {
			System.out.println("All resources closed!");
		}

		System.out.println("Simple Thread is done!");
	}
}
