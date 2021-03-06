package ui;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import commands.*;
import program.Program;

public class UserInterface implements Runnable{
	private Scanner mScanner;
	private static UserInterface mInstance = null;
	private Queue<String> mPrinterQueue;
	private ReentrantReadWriteLock mLock;
	private String mBankAccountName = "";
	private String mCustomerNumber = "6554-2";
	
	/** Create a new instance of this class. **/
	private UserInterface() {
		mPrinterQueue = new LinkedList<String>();
		mLock = new ReentrantReadWriteLock();
	};
	
	/** Add a new entry to the print queue. **/
	public void print(String string) {
		mLock.writeLock().lock();
		mPrinterQueue.add(string);
		mLock.writeLock().unlock();
	}
	
	/** Get the singleton object of this class. **/
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
		System.out.println("Please select what to do:\n"
					+ "0 - List all accounts.\n"
					+ "1 - Show the balance for all accounts.\n"
					+ "2 - Show the balance for a specific account.\n"
					+ "3 - Show the history for a specific account.\n"
					+ "4 - Add a sum of money to a specific account.\n"
					+ "5 - Withdraw a sum of money from a specific account.\n"
					+ "6 - Transfer a sum of money between two accounts.\n"
					+ "7 - Lock the specified account. The account no longer"
					+ " accepts withdrawals.\n"
					+ "8 - Unlock a specified account. The account now accepts"
					+ " withdrawals again.\n"
					+ "9 - Exit the application.\n\n");
		
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
	
	/** Select a bank account name. **/
	private void selectBankAccountName() {
		if(mBankAccountName == "") {
			System.out.println("Please type the name of the bank account.");
			String bankAccountName = mScanner.nextLine();
			
			if(bankAccountName.contains(","))
				mBankAccountName = bankAccountName.substring(bankAccountName.indexOf(",") + 1);
			else
				mBankAccountName = bankAccountName;
		}
		else {
			System.out.println("Please type the name of the bank account. "
					+ "Or just press enter to keep the previously selected name.");

			String bankAccountName = mScanner.nextLine();
			if(bankAccountName != "")
				if(bankAccountName.contains(","))
					mBankAccountName = bankAccountName.substring(bankAccountName.indexOf(",") + 1);
				else
					mBankAccountName = bankAccountName;
		}
	}
	
	/** Close the used resources. **/
	private void close() {
		mScanner.close();
	}
	
	/** Lists the accounts associated by the current customer. **/
	private void listAccounts() {
		Program.getSingleton().addCommand(new AccountsCommand(this, mCustomerNumber));
	}
	
	/** List the account balances. **/
	private void listAccountBalance() {
		Program.getSingleton().addCommand(new AllBalanceCommand(this, mCustomerNumber));
	}
	
	/** Display the balance for a specific account. **/
	private void displayBalance() {
		Program.getSingleton().addCommand(new BalanceCommand(this, mCustomerNumber, mBankAccountName));
	}
	
	/** Display the transaction history for a specific account. **/
	private void displayHistory() {
		Program.getSingleton().addCommand(new HistoryCommand(this, mCustomerNumber, mBankAccountName));
	}
	
	/** Lock or unlock a bank account. **/
	private void lockBankAccount(boolean lock) {
		Program.getSingleton().addCommand(new LockCommand(this, mCustomerNumber, mBankAccountName, lock));
	}
	
	/** Insert a sum to the specified account. **/
	private void insert(double sum) {
		Program.getSingleton().addCommand(new InsertCommand(this, mCustomerNumber, mBankAccountName, sum));
	}
	
	/** Withdraw a sum from the specified account. **/
	private void withdraw(double sum) {
		Program.getSingleton().addCommand(new WithdrawCommand(this, mCustomerNumber, mBankAccountName, sum));
	}
	
	/** Transfer a sum of money between two bank accounts. **/
	private void transfer(String targetBankAccountName, double sum) {
		Program.getSingleton().addCommand(
				new TransferCommand(this, mCustomerNumber, mBankAccountName, targetBankAccountName, sum));
	}
	
	/** Get a sum from the user. **/
	private double getSum() {
		System.out.println("Please type the sum of money this transaction will handle.\n");
		String response = mScanner.nextLine();
		
		try {
			double sum = Double.parseDouble(response);
			return sum;
		} catch(NumberFormatException e) {
			System.out.println("The sum was not a number. " + e.getMessage());
		}
		
		return Double.NaN;
	}
	
	/** Get the name of the target bank account. **/
	private String getTarget() {
		System.out.println("Please type the name of the target bank account.");
		String target = mScanner.nextLine();
		
		if(target.contains(","))
			return target.substring(target.indexOf(",") + 1);
		else
			return target;
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
					selectBankAccountName();
					displayBalance();
					break;
				}
				case HISTORY: {
					selectBankAccountName();
					displayHistory();
					break;
				}
				case INSERT: {
					selectBankAccountName();
					double sum = getSum();
					if(sum != Double.NaN)
						insert(sum);
					break;
				}
				case LOCK: {
					selectBankAccountName();
					lockBankAccount(true);
					break;
				}
				case TRANSFER: {
					selectBankAccountName();
					double sum = getSum();
					if(sum != Double.NaN) {
						String target = getTarget();
						transfer(target, sum);
					}
					break;
				}
				case UNLOCK: {
					selectBankAccountName();
					lockBankAccount(false);
					break;
				}
				case WITHDRAW: {
					selectBankAccountName();
					double sum = getSum();
					if(sum != Double.NaN)
						withdraw(sum);
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
