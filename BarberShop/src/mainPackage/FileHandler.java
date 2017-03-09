package mainPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.Queue;

public class FileHandler {
		/** Save the information currently within the database. 
		 * @param info -The string of information to process. 
		 * @param path -The path of the file.
		 * @throws IOException**/
		public static void save(String[] info, String path, boolean overwrite) throws IOException {
			BufferedWriter bw = null;
			
			try {
				bw = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8);
				
				if(overwrite)
					bw.write(""); // Overwrite the old data.
				
				// Save all booked appointments to the file.
				for(String s : info) {
					bw.append(s);
				}
				
			} catch (IOException e) {
				throw e;
			} finally {
				if(bw != null)
					bw.close(); // Close the file.
			}
		}
		
		/** Load data for the barber shop from a selected file. 
		 * @param path -The path of the file to load data from.
		 * @return A string array with the contents of the file.
		 * @throws IOException
		 * @throws DateTimeParseException
		 * @throws ArrayIndexOutOfBoundsException
		 * @throws NumberFormatException**/
		public static String[] load(String path) throws IOException, DateTimeParseException, ArrayIndexOutOfBoundsException, NumberFormatException{
			// Open a new file to get the appointments data.
			BufferedReader br = null;
			Queue<String> list = new LinkedList<String>();
			
			try {		
				br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
				
				String line = "";
				
				do {
					line = br.readLine(); // Read 1 line of data.
					
					if(line != null)
						list.add(line);
					
				} while(line != null);
				
			} catch (IOException e) {
				throw e;
			} finally {
				if(br != null)
					br.close();
			}
			
			// Return the list in array format.
			return list.toArray(new String[0]);
		}
}
