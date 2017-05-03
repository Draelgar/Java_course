package mainPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.Set;

public class FileHandler {
		// Save the data.
		public static void save(Set<BookedTime> bookings, String path) throws IOException {
			BufferedWriter bw = null;
			
			try {
				bw = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8);
				
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
			BufferedReader br = null;
			
			try {
				br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
				
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
		public static void load(BookKeeper bookKeeper, String path) throws IOException, DateTimeParseException, ArrayIndexOutOfBoundsException, NumberFormatException{
			// Open a new file to get the appointments data.
			BufferedReader br = null;
			
			try {
				
				br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
				
				String line = "";
				
				do {
					line = br.readLine(); // Read 1 line of data.
					
					if(line != null)
						bookKeeper.bookTime(new BookedTime(line));
					
				} while(line != null);
				
			} catch (IOException e) {
				throw e;
			} finally {
				if(br != null)
					br.close();
			}
		}
}
