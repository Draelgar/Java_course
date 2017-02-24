package mainPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class FileHandler {
		// Save the data.
		public static void save(Set<BookedTime> bookings) {
			File file = new File("data/data.txt");
			
			try {
				if(!file.exists()) // If the file does not exist, create a new one.
					file.createNewFile();
				
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write(""); // Overwrite the old data.
				
				for(BookedTime bt : bookings) {
					bt.save(bw);
				}
				
				bw.close();
				
			} catch (IOException e) {
				System.out.println("Error, failed to create the file! " + e.getMessage());
			}		
		}
		
		// Load the data.
		public static void load(Set<String> barbers, Set<BookedTime> bookings) {
			// Open the file containing the barber names.
			File file = new File("data/barbers.txt");
			
			try {
				if(!file.exists()) // If the file does not exist, create a new one.
					file.createNewFile();
				
				FileReader fr = new FileReader(file);
				Scanner scanner = new Scanner(fr);
				
				String line = "";
				
				while(scanner.hasNext()) {
					line = scanner.nextLine(); // Read 1 line of data.
					
					barbers.add(line);
				}
				
				scanner.close();
				
			} catch (IOException e) {
				System.out.println("Error, failed to create the file! " + e.getMessage());
			}
			
			// Open a new file to get the appointments data.
			file = new File("data/data.txt");
			
			try {
				if(!file.exists()) // If the file does not exist, create a new one.
					file.createNewFile();
				
				FileReader fr = new FileReader(file);
				Scanner scanner = new Scanner(fr);
				
				String line = "";
				
				while(scanner.hasNext()) {
					line = scanner.nextLine(); // Read 1 line of data.
					
					bookings.add(new BookedTime(line));
				}
				
				scanner.close();
				
			} catch (IOException e) {
				System.out.println("Error, failed to create the file! " + e.getMessage());
			}
		}
}
