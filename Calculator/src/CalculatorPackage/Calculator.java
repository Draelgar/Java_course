package CalculatorPackage;

import java.io.IOException;

public class Calculator {
	public double multiply(double a, double b) throws ArithmeticException {
		double sum = a * b;
		
		sum = ((double)Math.round(sum * 1000000000000000.0) / 1000000000000000.0);
		
		return sum;
	}
	
	public double divide(double a, double b) throws ArithmeticException {
		if(b == 0.0)
			throw new ArithmeticException("Division by zero!");
			
		double sum = a / b;
		
		sum = ((double)Math.round(sum * 1000000000000000.0) / 1000000000000000.0);
		
		return sum;
	}
	
	public double add(double a, double b) throws ArithmeticException {
		double sum = a + b;
		
		sum = ((double)Math.round(sum * 1000000000000000.0) / 1000000000000000.0);
		
		return sum;
	}
	
	public double subtract(double a, double b) throws ArithmeticException {
		double sum = a - b;
		
		sum = ((double)Math.round(sum * 1000000000000000.0) / 1000000000000000.0);
		
		return sum;
	}
	
	public double operate(String input) throws ArrayIndexOutOfBoundsException, NumberFormatException, ArithmeticException, NegativeArraySizeException, IOException {
		String values[] = input.split("(?<=[-+])|(?=[-+])"); // Retrieve the values.
		String sequence[];
		char charArr[];
		double sum = Double.NEGATIVE_INFINITY, seqSum = 0.0;
		
		for(int i = 0; i < values.length; i += 2) {
			
			if(values.length < 2) {
				sequence = input.split("(?<=[*/])|(?=[*/])");
				if(sequence.length < 2)
					throw new ArithmeticException("Input contains no valid values!");
			}
			else
				sequence = values[i].split("(?<=[*/])|(?=[*/])");
			
			if(sequence.length > 1) { // Was this a multiplication & division sequence?
				seqSum = Double.parseDouble(sequence[0]); // Set the first value as the sum.
				
				for(int j = 1; j < Math.round(sequence.length / 2.0); j++) {
					// Multiplication.
					if(sequence[(2 * j) - 1].equals("*")) {
						seqSum = multiply(seqSum, Double.parseDouble(sequence[(j * 2)]));
					}
					// Division.
					else {
						seqSum = divide(seqSum, Double.parseDouble(sequence[(j * 2)]));
					}
				}
				
				// Apply to the sum.
				if(i == 2 && sum < 0) {
					sum = Double.parseDouble(values[0]);
					
					// Get the operator.
					if(values[i - 1].equals("+")) {
						sum = add(sum, seqSum);
					}
					else if(values[i - 1].equals("-")) {
						sum = subtract(sum, seqSum);
					}
				}
				else if(i == 0)
				{
					sum = seqSum;
				}
				else {
					// Get the operator.
					if(values[i - 1].equals("+")) {
						sum = add(sum, seqSum);
					}
					else if(values[i - 1].equals("-")) {
						sum = subtract(sum, seqSum);
					}
				}
				
			}
			// Is it a single number?
			else if(i >= 2) {
				charArr = new char[values[i].length()];
				values[i].getChars(0, values[i].length(), charArr, 0);
				boolean isValue = true;
				
				for(int j = 0; j < charArr.length; j++) {
					if(!Character.isDigit(charArr[j]) && charArr[j] != '.' && charArr[j] != ' ') {
						isValue = false;
						break;
					}
				}
				
				// Was it a value?
				if(isValue) {
					if(i == 2 && sum < 0) {
						sum = Double.parseDouble(values[0]);
					}
					// Get the operator.
					if(values[i - 1].equals("+")) {
						sum = add(sum, Double.parseDouble(values[i]));
					}
					else if(values[i - 1].equals("-")) {
						sum = subtract(sum, Double.parseDouble(values[i]));
					}

				}
			}
				
		}
		
		if(sum == Double.NEGATIVE_INFINITY)
			throw new IOException("Incorrect Syntax!");
		
		return sum;
	}
}
