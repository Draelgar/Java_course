package mainPackage;

import java.util.Scanner;

public class WordAndLetterCounter {
	
	private int mMode = 0;

	public static void main(String[] args) {
		
		WordAndLetterCounter counter = new WordAndLetterCounter();
		counter.run();
	}
	
	public void run() 
	{
		
		boolean running = true;
		
		while(running)
		{
			switch(mMode)
			{
				case 0:
				{
					// Show the menu.
					showMenu();
					break;
				}
				case 1:
				{
					String s = getString();
					System.out.println(s);
					
					// Set the string to all lower case.
					String sl = s.toLowerCase();
					
					// Was anything else than just a single 'b' typed?
					if(sl.compareTo("b") != 0)
					{
						// Count the letters and print the result.
						System.out.println('"' + s + '"' + " contains " + countWords(sl) + " words!");
					}
					
					mMode = 0;
					break;
				}
				case 2:
				{
					String s = getString();
					System.out.println(s);
					
					// Set the string to all lower case.
					String sl = s.toLowerCase();
					
					// Was anything else than just a single 'b' typed?
					if(sl.compareTo("b") != 0)
					{
						// Count the letters and print the result.
						System.out.println('"' + s + '"' + " contains " + countLetters(sl) + " letters!");
					}
					
					mMode = 0;
					break;
				}
				case 3:
				{
					String s = getString();
					System.out.println(s);
					
					// Set the string to all lower case.
					String sl = s.toLowerCase();
					
					// Was anything else than just a single 'b' typed?
					if(sl.compareTo("b") != 0)
					{
						// Count the letters and print the result.
						System.out.println('"' + s + '"' + " contains " + countLetters(sl) + " letters and " + countWords(sl) + " words!");
					}
					
					mMode = 0;	
					break;
				}
				default:
				{
					// Terminate the application.
					running = false;
					break;
				}
					
			}
		}

	}
	
	private void showMenu()
	{
		// Create the input buffer string.
		String inputBuffer = "";
		Scanner scanner = new Scanner(System.in);
		
		// Print the menu to the console.
		System.out.println("Do you want to count words (type w), letters (type l) or both (type b)");
		System.out.println("Or type q to quit");
		
		try {
			// Get the input.
			inputBuffer = scanner.next();
			
			
			// Change the buffer to lower case only so that it won't matter if the user used lower or upper case!
			inputBuffer = inputBuffer.toLowerCase();
			
			// Was there any input?
			if(inputBuffer.length() > 0)
			{
				char letter = 0, c_buffer[] = new char[1];
				inputBuffer.getChars(0, 1, c_buffer, 0);
				
				// Get the first character as the answer.
				letter = c_buffer[0];
				
				switch(letter)
				{
					case 'w': // If 'w' is typed, set mode to counting words.
					{
						mMode = 1;
						
						break;
					}
					case 'l': // If 'l' is typed, set mode to counting letters.
					{
						mMode = 2;
						
						break;
					}
					case 'b': // If 'b' is typed, set mode to both.
					{
						mMode = 3;
						
						break;
					}
					case 'q': // If 'q' is typed, quit the application.
					{
						mMode = -1;
						
						break;
					}
					default: // Bad input, repeat menu!
					{
						mMode = 0;
						
						break;
					}
				}
	
			}
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
	
	// Counts letters of a string, assumes the string is all lower case.
	private int countLetters(String s) {
		
		// Was the string at least 1 character long?
		if(s.length() > 0)
		{
			// Create a new char array buffer.
			char buffer[] = new char[s.length()];
			int count = 0;
			
			// Get the char array from the string.
			s.getChars(0, s.length(), buffer, 0);
			
			// Loop through the elements of the character array.
			for(int i = 0; i < s.length(); i++)
			{
				// Identify the character.
				if(buffer[i] >= 'a' && buffer[i] <= 'ö') 
				{
					count++;
				}
			}
			
			return count;
		}
		
		// The string was too short!
		return -1;
	}
	
	// Get a string from the user input.
	private String getString()
	{
		System.out.println("Type the string to check. Or type a single 'b' to go back to the menu.");
		
		String s = "";
		
		Scanner scanner = new Scanner(System.in);
		
		try {
		s = scanner.nextLine();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return s;
	}
	
	// Counts the words of a string, assumes the string is all lower case.
	private int countWords(String s)
	{	
		if(s.length() > 0)
		{
			int count = 0;
			boolean newWord = false;
			
			// Create a new char array buffer.
			char buffer[] = new char[s.length()];
			
			// Get the char array from the string.
			s.getChars(0, s.length(), buffer, 0);
			
			// Loop through the elements of the character array.
			for(int i = 0; i < s.length(); i++)
			{
				// Identify the character. Are we already at a new word?
				if((buffer[i] >= 'a' && buffer[i] <= 'ö') || (buffer[i] >= '0' && buffer[i] <= '9')) 
				{
					if(!newWord)
					{
						count++;
						newWord = true;
					}
				}
				else
				{
					newWord = false;
				}
			}
			
			return count;
		}
		
		return -1;
	}

}
