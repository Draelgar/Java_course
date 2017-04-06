/**
 * 
 */
package barberShopTestingPackage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import mainPackage.FileHandler;

/**
 * @author Gustaf Peter Hultgren **/
public class FileHandlerTest {
	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Test
	public void saveAndLoad() {
		String inData[] = {"Hello\n", "world\n", "!"};
		String path = "";
		
		try {
			File tempFile = testFolder.newFile("testData.txt");
			path = tempFile.getPath();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileHandler.save(inData, path, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String outData[] = FileHandler.load(path);
			
			assertEquals("Check the length of the out data.", 3, outData.length);
			
			assertTrue("Check element 0.", inData[0].equalsIgnoreCase(outData[0] + "\n"));
			assertTrue("Check element 1.", inData[1].equalsIgnoreCase(outData[1] + "\n"));
			assertTrue("Check element 2.", inData[2].equalsIgnoreCase(outData[2]));
			
		} catch (DateTimeParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
