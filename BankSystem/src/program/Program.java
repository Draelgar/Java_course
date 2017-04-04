package program;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import bankSystem.Bank;
import commands.Command;
import ui.UserInterface;

public class Program {
	private static Program mInstance = null;
	private Bank mBank;
	private Thread mUIThread;
	private ReentrantReadWriteLock mLock;
	private Queue<Command> mCommands;
	
	private Program() {
		mBank = new Bank();
		mBank.addCustomer("6554-2", "55674542-3");
		mBank.addBankAccount("6554-2", "76349274-3");
		mBank.addBankAccount("6554-2", "83527453-3");
		
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
				mCommands.poll().execute(mBank);
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
