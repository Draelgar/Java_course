package mainPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Set;

public class FileHandler {
		// Save the data.
		public static void save(Set<BookedTime> bookings, String path) throws IOException {
			File file = new File(path);
			FileWriter fw = null;
			BufferedWriter bw = null;
			
			try {
				if(!file.exists()) // If the file does not exist, create a new one.
					file.createNewFile();
				
				fw = new FileWriter(file); // Create the file writer and link it to the file.
				bw = new BufferedWriter(fw); // Connect the file writer with the buffered writer.
				
				bw.write(""); // Overwrite the old data.
				
				// Save all booked appointments to the file.
				for(BookedTime bt : bookings) {
					bt.save(bw);
				}
				
			} catch (IOException e) {
				throw e;
			} finally {
				if(bw != null)
					bw.close(); // Close the file.
			}
		}
		
		public static void loadStaff(Set<String> barbers, String path) throws IOException {
			// Open the file containing the barber names.
			File file = new File(path);
			FileReader fr = null;
			BufferedReader br = null;
			
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				
				String line = "";

				do {
					line = br.readLine(); // Read 1 line of data.
					if(line != null && line.contains(","))
						line = line.substring(line.indexOf(','));
					
					if(line != null)
						barbers.add(line);
					
				} while(line != null);
			} catch (IOException e) {
				throw e;
			}
			finally {
				if(br != null)
					br.close();
			}
		}
		
		// Load the data.
		public static void load(Set<BookedTime> bookings, String path) throws IOException, DateTimeParseException, ArrayIndexOutOfBoundsException, NumberFormatException{
			// Open a new file to get the appointments data.
			File file = new File(path);
			BufferedReader br = null;
			FileReader fr = null;
			
			try {
				
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				
				String line = "";
				
				do {
					line = br.readLine(); // Read 1 line of data.
					
					if(line != null)
						bookings.add(new BookedTime(line));
					
				} while(line != null);
				
			} catch (IOException e) {
				throw e;
			} finally {
				if(br != null)
					br.close();
			}
		}
}
