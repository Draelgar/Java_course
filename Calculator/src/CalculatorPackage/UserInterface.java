package CalculatorPackage;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface {

	public static void main(String[] args) {
		Calculator calc = new Calculator();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		String input = "";
		Pattern pattern = Pattern.compile("\\(([^\\(\\)]*)\\)");
		Matcher matcher;
		String block = "";
		StringBuffer strBuff = new StringBuffer();
		
		System.out.println("Calculator. Type exit to exit.");
		
		while(running) {
			input = scanner.nextLine();
			
			if(input.equalsIgnoreCase("exit"))
				break;
			
			if(input.length() > 0) {
				try {
					strBuff = new StringBuffer();
					
					while(input.contains("(")) {
						
						matcher = pattern.matcher(input);
						while(matcher.find()) {	
							
							block = matcher.group().substring(1, matcher.group().length() - 1);
							
							if(block.trim().length() > 0)
								block = String.valueOf(calc.operate(block));
							
							matcher.appendReplacement(strBuff, block);
						}
						
						matcher.appendTail(strBuff);
						input = strBuff.toString();
						strBuff = new StringBuffer();
					}
					
					System.out.println(input + " = " + calc.operate(input));
					
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: " + e.getMessage());
				} catch (NumberFormatException e) {
					System.out.println("Error: Invalid data " + e.getMessage());
				} catch (ArithmeticException e) {
					System.out.println("Error: " + e.getMessage());
				} catch (NegativeArraySizeException e) {
					System.out.println("Error: " + e.getMessage());
				} catch (IOException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
			
		}

	}

}
