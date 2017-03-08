package mainPackage;

import java.util.Scanner;

public class UserInterface {
	private static Scanner mScanner = new Scanner(System.in);
	
	public static void println(String s) {
		System.out.println(s);
	}
	
	public static void print(String s) {
		System.out.print(s);
	}
	
	public static String getInput() {
		return mScanner.nextLine();
	}
}
