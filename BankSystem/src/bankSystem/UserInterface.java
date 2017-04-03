package bankSystem;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserInterface implements Runnable{
	private Scanner mScanner;
	private static UserInterface mInstance = null;
	private Queue<String> mPrinterQueue;
	private ReentrantReadWriteLock mLock;
	
	private UserInterface() {
		mPrinterQueue = new LinkedList<String>();
		mLock = new ReentrantReadWriteLock();
	};
	
	public void print(String string) {
		mLock.writeLock().lock();
		mPrinterQueue.add(string);
		mLock.writeLock().unlock();
	}
	
	public static UserInterface getSingleton() {
		if(mInstance != null)
			return mInstance;
		
		else {
			mInstance = new UserInterface();
			return mInstance;
		}
	}
	
	/** Initialize the user interface.
	 * @param inputStream -The stream to bind to for user input. **/
	private void init(InputStream inputStream) {
		mScanner = new Scanner(inputStream);
	}
	
	/** Display the greetings notice. **/
	private void greeting() {
		System.out.println("Welcome to our bank system!");
	}
	
	/** Show the menu. **/
	private MenuSelection menu() {
		System.out.println("Please select what to do:"
					+ "0 - List all accounts."
					+ "1 - Show the balance for all accounts."
					+ "2 - Show the balance for a specific account."
					+ "3 - Show the history for a specific account."
					+ "4 - Add a sum of money to a specific account."
					+ "5 - Withdraw a sum of money from a specific account."
					+ "6 - Transfer a sum of money between two accounts."
					+ "7 - Lock the specified account. The account no longer"
					+ "accepts withdrawals."
					+ "8 - Unlock a specified account. The account now accepts"
					+ "withdrawals again."
					+ "9 - Exit the application.");
		
		MenuSelection selection = MenuSelection.NONE;
		String response = "";
		
		while(selection == MenuSelection.NONE) {
			response = mScanner.nextLine();
			selection = translateOption(response);
			
			if(selection == MenuSelection.NONE)
			{
				System.out.println("Unknown command, please try again.");
			}
		}
		
		return selection;
	}
	
	/** Translate user input into enumerated data. **/
	private MenuSelection translateOption(String option){
		try {
			int value = Integer.parseInt(option);
			switch(value) {
				case 0:
					return MenuSelection.ACCOUNTS;
				case 1:
					return MenuSelection.ALL_BALANCE;
				case 2:
					return MenuSelection.BALANCE;
				case 3:
					return MenuSelection.HISTORY;
				case 4:
					return MenuSelection.INSERT;
				case 5:
					return MenuSelection.WITHDRAW;
				case 6:
					return MenuSelection.TRANSFER;
				case 7:
					return MenuSelection.LOCK;
				case 8:
					return MenuSelection.UNLOCK;
				case 9: {
					System.out.println("Exiting application.");
					return MenuSelection.EXIT;
				}
				default:
					return MenuSelection.NONE;	
			}
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		
		return MenuSelection.NONE;
	}
	
	/** Close the used resources. **/
	private void close() {
		mScanner.close();
	}
	
	/** Lists the accounts associated by the current customer. **/
	private void listAccounts() {
		Program.getSingleton().addCommand(new AccountsCommand());
	}
	
	/** List the account balances. **/
	private void listAccountBalance() {
		Program.getSingleton().addCommand(new AllBalanceCommand());
	}
	
	/** Run the user interface. **/
	public void run() {
		init(System.in);
		
		greeting();
		MenuSelection mode = MenuSelection.NONE;
		
		while(mode != MenuSelection.EXIT) {
			mode = menu();
			
			switch(mode) {
				case ACCOUNTS: {
					listAccounts();
					break;
				}
				case ALL_BALANCE: {
					listAccountBalance();
					break;
				}
				case BALANCE: {
					// Create a command taking the account name as input.
					break;
				}
				case HISTORY: {
					// Create a command taking the account name as input.
					break;
				}
				case INSERT: {
					// Create a command with the following inputs:
					//	- Account name.
					// 	- Sum to add.
					break;
				}
				case LOCK: {
					// Create a command taking the account name as input.
					break;
				}
				case TRANSFER: {
					// Create a command with the following inputs:
					//	- Source account name.
					//	- Destination account name.
					// 	- Sum to add.
					break;
				}
				case UNLOCK: {
					// Create a command taking the account name as input.
					break;
				}
				case WITHDRAW: {
					// Create a command with the following inputs:
					//	- Account name.
					// 	- Sum to subtract.
					break;
				}
				default: {
					break;
				}
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			mLock.readLock().lock();
			while(!mPrinterQueue.isEmpty()) {
				System.out.println(mPrinterQueue.poll());
			}
			mLock.readLock().unlock();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		close();
	}
}
