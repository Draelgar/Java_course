/**
 * 
 */
package barberShopTestingPackage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mainPackage.BarberShop;
import mainPackage.TimeMode;

/**
 * @author Gustaf Peter Hultgren **/
public class BarberShopTest {
	private BarberShop mBarberShop;
	
	@Before
	public void setup() {
		try {
			mBarberShop = new BarberShop("data/data.txt");
			
			int year = LocalDateTime.now().getYear();
			int month = LocalDateTime.now().getMonthValue();
			int day = LocalDateTime.now().getDayOfMonth();
			int hour = LocalDateTime.now().getHour();
			int minute = LocalDateTime.now().getMinute();
			ZonedDateTime start = ZonedDateTime.of(year, month, day, hour, 
					minute, 0, 0, Calendar.getInstance().getTimeZone().toZoneId());
			
			Duration duration = Duration.ofMinutes(45);
			
			mBarberShop.bookAppointment(start, duration, "Fredrik", 100.0, 0);
			mBarberShop.bookAppointment(start.plusDays(1), duration, "Olle", 100.0, 0);
			mBarberShop.bookAppointment(start.plusDays(10), duration, "Lisa", 100.0, 0);
			mBarberShop.bookAppointment(start.plusMonths(1), duration, "Anna", 100.0, 0);
			mBarberShop.bookAppointment(start.plusMonths(11), duration, "Fred", 100.0, 0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		mBarberShop = null;
	}
	
	@Test
	public void pickBarber() {
		String name = mBarberShop.pickBarber("Greta");
		
		assertTrue(name.equalsIgnoreCase("Greta"));
		
		name = mBarberShop.pickBarber("Adolf");
		assertFalse(name.equalsIgnoreCase("Adolf"));
	}
	
	@Test
	public void getAppointments() {
		Queue<String> queue = mBarberShop.getAppointments(TimeMode.DAY);

		assertEquals("Day", 1, queue.size());
		
		queue = mBarberShop.getAppointments(TimeMode.WEEK);	
		assertEquals("Week", 2, queue.size());
		
		queue = mBarberShop.getAppointments(TimeMode.MONTH);
		assertEquals("Month", 3, queue.size());
		
		queue = mBarberShop.getAppointments(TimeMode.YEAR);
		assertEquals("Year", 4, queue.size());
		
		queue = mBarberShop.getAppointments(TimeMode.YEAR_AHEAD);
		assertEquals("Year Ahead", 5, queue.size());
	}

}
