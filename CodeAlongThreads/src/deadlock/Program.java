package deadlock;

public class Program {
	static Object lock1 = new Object();
	static Object lock2 = new Object();
	public static void main(String[] args) {
		LockThread1 thread1 = new LockThread1();
		LockThread2 thread2 = new LockThread2();
		thread1.start();
		thread2.start();
	}
}
