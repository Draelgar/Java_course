/**
 * 
 */
package barberShopTestingPackage;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import mainPackage.Appointment;
import mainPackage.Barber;

/**
 * @author Gustaf Peter Hultgren **/
public class BarberTest {
	private Barber mBarber;
	
	/*public boolean addAppointment(Appointment appointment) {
		if(appointment.getStartTime().toLocalTime().isAfter(mStartOfDay) 
				&& appointment.getEndTime().toLocalTime().isBefore(mEndOfDay)
				&& appointment.getStartTime().isAfter(ZonedDateTime.now()))
			return mAppointments.add(appointment);
		
		return false;
	}*/
	
	@Before
	public void setup() {
		int year = LocalDateTime.now().getYear() + 1;
		ZonedDateTime start = ZonedDateTime.of(year, 5, 30, 8, 
				30, 0, 0, Calendar.getInstance().getTimeZone().toZoneId());
		
		Duration duration = Duration.ofMinutes(45);
		
		mBarber = new Barber("Greta");
		mBarber.addAppointment(new Appointment(start, duration, "Fredrik", 100.0));
		start = start.plusMinutes(50);
		mBarber.addAppointment(new Appointment(start, duration, "Olle", 100.0));
		start = start.plusMinutes(50);
		mBarber.addAppointment(new Appointment(start, duration, "Lisa", 100.0));
		start = start.plusMinutes(50);
		mBarber.addAppointment(new Appointment(start, duration, "Anna", 100.0));
		start = start.plusMinutes(50);
		mBarber.addAppointment(new Appointment(start, duration, "Fred", 100.0));
		start = start.plusMinutes(50);
		mBarber.addAppointment(new Appointment(start, duration, "Pelle", 100.0));
	}
	
	@After
	public void tearDown() {
		mBarber = null;
	}
	
	@Test
	public void addAppointment() {
		int year = LocalDateTime.now().getYear() + 1;
		ZonedDateTime start = ZonedDateTime.of(year, 5, 28, 7, 
				30, 0, 0, Calendar.getInstance().getTimeZone().toZoneId());
		
		Duration duration = Duration.ofMinutes(45);
		
		assertFalse("Add appointment before work hours.", mBarber.addAppointment(new Appointment(start, duration, "Pelle", 100.0)));
		start = start.plusHours(11);
		assertFalse("Add appointment after work hours.", mBarber.addAppointment(new Appointment(start, duration, "Pelle", 100.0)));
		start = start.minusHours(10);
		assertTrue("Add appointment within work hours.", mBarber.addAppointment(new Appointment(start, duration, "Pelle", 100.0)));
		assertFalse("Add identical appointment.", mBarber.addAppointment(new Appointment(start, duration, "Pelle", 100.0)));
		start = start.plusMinutes(30);
		assertFalse("Add colliding appointment.", mBarber.addAppointment(new Appointment(start, duration, "Pelle", 100.0)));
		start = start.minusMinutes(60);
		assertFalse("Add colliding appointment.", mBarber.addAppointment(new Appointment(start, duration, "Pelle", 100.0)));
	}
	
	@Test
	public void removeAppointment() {
		int year = LocalDateTime.now().getYear() + 1;
		ZonedDateTime start = ZonedDateTime.of(year, 5, 30, 8, 
				30, 0, 0, Calendar.getInstance().getTimeZone().toZoneId());
		
		Duration duration = Duration.ofMinutes(45);
		
		assertTrue("Remove existing appointment.", mBarber.removeBooking(new Appointment(start, duration, "Pelle", 100.0)));
		assertFalse("Repeat remove the same appointment", mBarber.removeBooking(new Appointment(start, duration, "Pelle", 100.0)));
	}

	@Test
	public void getBookedAppointment() {
		int year = LocalDateTime.now().getYear() + 1;
		ZonedDateTime start = ZonedDateTime.of(year, 5, 30, 8, 
				30, 0, 0, Calendar.getInstance().getTimeZone().toZoneId());
		
		Appointment appointment = mBarber.getBookedTime(start);
		
		assertEquals("Check start time.", start, appointment.getStartTime());
		assertEquals("Check end time.", start.plusMinutes(45), appointment.getEndTime());
		assertEquals("Check customer.", "Fredrik", appointment.getCustomer());
		assertEquals("Check price.", 100.0, appointment.getPrice(), 0.005);
	}
}
