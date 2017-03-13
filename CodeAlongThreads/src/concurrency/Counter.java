package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
	private Lock mLock = new ReentrantLock(true);
	private int count = 0;
	
	public void inc() {
		mLock.lock();
		try {
			count++;
			System.out.println(count);
		}
		finally {
			mLock.unlock();
		}
	}
}
