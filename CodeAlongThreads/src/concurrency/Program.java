package concurrency;

public class Program {

	public static void main(String[] args) {
		Counter localCounter = new Counter();
		for(int i = 0; i < 100; i++) {
			Thread tempThread = new Thread(new Runnable() {
				public void run() {
					localCounter.inc();
				}
			});
			
			tempThread.start();
		}
	}

}
