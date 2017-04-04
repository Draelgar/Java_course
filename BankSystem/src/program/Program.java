package program;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import bankSystem.Bank;
import commands.Command;
import ui.UserInterface;

/** This class handles the communication between the user interface and the bank system.
 * @author Gustaf Peter Hultgren 
 * @version 1.0 **/
public class Program {
	private static Program mInstance = null;
	private Bank mBank;
	private Thread mUIThread;
	private ReentrantReadWriteLock mLock;
	private Queue<Command> mCommands;
	
	/** Create a new program object. **/
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
	
	/** Run the program. **/
	public void run() {
		mUIThread = new Thread(UserInterface.getSingleton());
		mUIThread.start();

		while(mUIThread.isAlive()) {
			if(!mCommands.isEmpty())
				mCommands.poll().execute(mBank);
		}
	}
	
	/** Add a command from the user interface. **/
	public void addCommand(Command command) {
		mLock.writeLock().lock();
		mCommands.add(command);
		mLock.writeLock().unlock();
	}
	
	/** The main function of this application. **/
	public static void main(String args[]) {
		Program.getSingleton().run();
	}
}
