/**
 * 
 */
package barberShopTestingPackage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import org.junit.Test;

import mainPackage.Appointment;

/**
 * @author Gustaf Peter Hultgren **/
public class AppointmentTest {

	@Test
	public void creation() {
		ZonedDateTime start = ZonedDateTime.now();
		Duration duration = Duration.ofMinutes(45);
		String customer = "Customer";
		double price = 100.0;
		
		Appointment appointment = new Appointment(start, duration, customer, price);
		
		appointment.toString();
		
		assertEquals("Checking start time.", start, appointment.getStartTime());
		assertEquals("Checking end time.",start.plusMinutes(duration.toMinutes()), appointment.getEndTime());
		assertTrue("Checking customer name.", appointment.getCustomer().equalsIgnoreCase(customer));
		assertEquals("Checking price.", price, appointment.getPrice(), 0.005);
	}

	@Test
	public void readableString() {
		ZonedDateTime start = ZonedDateTime.now();
		Duration duration = Duration.ofMinutes(45);
		String customer = "Customer";
		double price = 100.0;
		
		Appointment appointment = new Appointment(start, duration, customer, price);
		
		String expected = "Date: " + start.toLocalDate().toString() + "\n";
		expected += "Customer: " + customer + "\n";
		
		// Convert time to a string representation.
		String startString = start.toLocalTime().toString();
		String endString = start.plusMinutes(duration.toMinutes()).toLocalTime().toString();
		
		expected += "Time: " + startString + " - " + endString + "\n";
		expected += "Price: " + price + "kr\n";
		
		assertTrue("Checking readable string format.", appointment.toString().equalsIgnoreCase(expected));
	}
	
	@Test
	public void fileString() {
		ZonedDateTime start = ZonedDateTime.now();
		Duration duration = Duration.ofMinutes(45);
		String customer = "Customer";
		double price = 100.0;
		
		Appointment appointment = new Appointment(start, duration, customer, price);
		
		String fileString = customer + "," + start.toString() + "," 
				+ start.plusMinutes(duration.toMinutes()).toString() 
				+ "," + price + "\n";
		
		assertTrue("Checking file storage string format.", appointment.toFileString().equalsIgnoreCase(fileString));
	}
	
	@Test
	public void recreation() {
		ZonedDateTime start = ZonedDateTime.of(2017, 4, 6, 9, 20, 0, 0, Calendar.getInstance().getTimeZone().toZoneId());
		
		Duration duration = Duration.ofMinutes(45);
		String customer = "Customer";
		double price = 100.0;
		
		String fileString = customer + "," + start.toString() + "," 
				+ start.plusMinutes(duration.toMinutes()).toString() 
				+ "," + price + "\n";
		
		Appointment creation = new Appointment(start, duration, customer, price);
		
		try {
			Appointment recreation = new Appointment(fileString);
			assertEquals("Check start time.", creation.getStartTime(), recreation.getStartTime());
			assertEquals("Check end time.", creation.getEndTime(), recreation.getEndTime());
			assertEquals("Check customer.", creation.getCustomer(), recreation.getCustomer());
			assertEquals("Check price.", creation.getPrice(), recreation.getPrice(), 0.005);
		} catch (DateTimeParseException | ArrayIndexOutOfBoundsException | NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}
}
