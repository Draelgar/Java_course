package bankSystem;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import commands.Command;

public class Program {
	private static Program mInstance = null;
	private CustomerAccount mCustomerAccount;
	private Thread mUIThread;
	private ReentrantReadWriteLock mLock;
	private Queue<Command> mCommands;
	
	private Program() {
		mCustomerAccount = new CustomerAccount("6554-2,55674542-3");
		mCustomerAccount.addBankAccount("6554-2,76349274-3");
		mCustomerAccount.addBankAccount("6554-2,83527453-3");
		mCommands = new LinkedList<Command>();
		mLock = new ReentrantReadWriteLock();
	};
	
	/** Get the singleton object of this class. **/
	public static Program getSingleton() {
		if(mInstance != null)
			return mInstance;
		else {
			mInstance = new Program();
			return mInstance;
		}
	}
	
	public void run() {
		mUIThread = new Thread(UserInterface.getSingleton());
		mUIThread.start();

		while(mUIThread.isAlive()) {
			if(!mCommands.isEmpty())
				mCommands.poll().execute(mCustomerAccount);
		}
	}
	
	public void addCommand(Command command) {
		mLock.writeLock().lock();
		mCommands.add(command);
		mLock.writeLock().unlock();
	}
	
	public static void main(String args[]) {
		Program.getSingleton().run();
	}
}
