package elevator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/** This class will handle console output in an ordered manner.
 * @author Gustaf Peter Hultgren
 * @verion 1.0  **/
public class UserInterface implements Runnable{
	/** The one and only instance of this class. **/
	private static UserInterface mInstance = null;
	private Queue<String> mPrintQueue;
	private ReadWriteLock mLock;
	
	/** A private default constructor to prevent unauthorized creation of new instances. **/
	private UserInterface() {
		mPrintQueue = new LinkedList<String>();
		mLock = new ReentrantReadWriteLock();
	}
	
	/** Get the one and only instance of this object. If none exists yet, create a new one.
	 * @return The singleton instance of this object. **/
	public static UserInterface getSingleton() {
		if(mInstance == null)
			mInstance = new UserInterface();
		
		return mInstance;
	}
	
	/** Run the program loop for the interface. **/
	public void run() {
		while(true) {
			synchronized(mLock.readLock()) {
				if(!mPrintQueue.isEmpty()) {
					synchronized(mLock.writeLock()) {
						System.out.println(mPrintQueue.poll());
					}
					
					// Wait for 20 milliseconds.
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/** Add a string to the print queue.
	 * @param string -The string of characters to print. **/
	public void print(String string) {
		synchronized(mLock.writeLock()) {
			mPrintQueue.add(string);
		}
	}
}
