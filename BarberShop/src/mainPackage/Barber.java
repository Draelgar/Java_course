package mainPackage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

/** This class represents the barber for a barber shop. It takes care of all the booked 
 * appointments for the barber.'
 * @author Gustaf Peter Hultgren
 * @version 2.1 **/
public class Barber {
	/** The name of the barber**/
	private String mName;
	/** The set of appointments for this barber. **/
	private SortedSet<Appointment> mAppointments;
	/** The start of this barber's work day. **/
	private LocalTime mStartOfDay;
	/** The end of this barber's work day. **/
	private LocalTime mEndOfDay;
	
	/** Create a new instance of this class. 
	 * @param name -The name of the barber.
	 * @param start -The start time for this barber. 
	 * @param duration -The duration of this barber's work days.**/
	public Barber(String name, LocalTime start, Duration duration) {
		mAppointments = new TreeSet<Appointment>();
		mStartOfDay = start;
		mEndOfDay = start.plusMinutes(duration.toMinutes());
		mName = name;
	}
	
	public Barber(String name) {
		mAppointments = new TreeSet<Appointment>();
		mName = name;
		
		mStartOfDay = LocalTime.of(8, 0);
		mEndOfDay = LocalTime.of(17, 0);
	}
	
	/** Create a new instance of this class from a string array containing the barber's name followed by
	 * all his or her booked appointments.
	 * @param info -The array with all the information needed. **/
	public Barber(String name, String[] info) throws DateTimeParseException, ArrayIndexOutOfBoundsException, NumberFormatException, IOException {
		mAppointments = new TreeSet<Appointment>();
		mName = name;
		
		for(int i = 0; i < info.length; i++) {
			addAppointment(new Appointment(info[i]));
		}
		
		mStartOfDay = LocalTime.of(8, 0);
		mEndOfDay = LocalTime.of(17, 0);
	}
	
	/** Get the name of this barber. 
	 * @return -The name of the barber.**/
	public String getName() {
		return mName;
	}
	
	/** Add a booked appointment. 
	 * @param booking -The appointment to book.
	 * @return True if the appointment was added, false if it collided or was outside of the barber's work day. **/
	public boolean addAppointment(Appointment appointment) {
		if(appointment.getStartTime().toLocalTime().isAfter(mStartOfDay) 
				&& appointment.getEndTime().toLocalTime().isBefore(mEndOfDay)
				&& appointment.getStartTime().isAfter(ZonedDateTime.now()))
			return mAppointments.add(appointment);
		
		return false;
	}
	
	/** Remove the selected appointment if it exists. 
	 * @param booking -An appointment who's time interval collides with or equals 
	 * 			the appointment you wish to remove.
	 * @return True if the item was removed, false if there was no match.**/
	public boolean removeBooking(Appointment appointment) {
		return mAppointments.remove(appointment);
	}
	
	/** Get any booked appointment at the selected date and time. Returns null if no such appointment exists. 
	 * @param dateTime -The specified time to look for.
	 * @return Any booked appointment occupying the given time. Null if no such appointments exists.**/
	public Appointment getBookedTime(ZonedDateTime dateTime) {
		for(Appointment appointment : mAppointments) {
			if(appointment.compareTo(new Appointment(dateTime, Duration.ofHours(1), null, 0.0)) == 0) {
				return appointment;
			}
		}
		
		return null;
	}
	
	/** Return an array of strings representing the contents of the booked appointments set in a file friendly format. 
	 * @return An array of strings, where each element represents an appointment booked for this barber.**/
	public String[] appointmentsToFileString() {
		String[] strings = new String[mAppointments.size()];
		int index = 0;
		
		for(Appointment appointment : mAppointments) {
			strings[index] = appointment.toFileString();
			index++;
		}
		
		return strings;
	}
	
	/** Create a string array representing this object.
	 * @return An array of strings which represents the barber's name and booked appointments.**/
	public String[] toStringArray() {
		String strings[] = new String[mAppointments.size() + 1];
		int index = 1;
		
		strings[0] = mName + "\n";
		for(Appointment appointment : mAppointments) {
			strings[index] = appointment.toFileString();
			index++;
		}
		
		return strings;
	}
	
	/** Return an array of strings representing the contents of the booked appointments set in a user friendly format. 
	 * @return An array of strings, where each element represents an appointment booked for this barber.**/
	public String[] appointmentsToString() {
		String[] strings = new String[mAppointments.size() + 1];
		strings[0] = "Appointments for " + mName + ":\n";
		
		int index = 1;
		
		for(Appointment appointment : mAppointments) {
			strings[index] = appointment.toString();
			index++;
		}
		
		return strings;
	}
	
	/** Get an array of strings representing all appointments within the selected time interval in a user friendly format.
	 * @param start -The start of the time interval to check.
	 * @param end -The end of the time interval to check.
	 * @return A string array where each element represent an appointment within the selected time interval **/
	public Queue<String> getAppointments(ZonedDateTime start, ZonedDateTime end) {
		Queue<String> queue = new LinkedList<String>();
		
		queue.add("Appointments for " + mName + ":\n");
		
		for(Appointment appointment : mAppointments) {
			if(appointment.getStartTime().isAfter(start) && appointment.getEndTime().isBefore(end))
				queue.add(appointment.toString());
		}
		
		return queue;
	}
	
	/** Removes all old appointments that lies in the past. **/
	public void removeOldAppointments() {
		ZonedDateTime present = ZonedDateTime.now();
		
		if(mAppointments.size() > 0) {
			Iterator<Appointment> iterator = mAppointments.iterator();
			while(iterator.hasNext()) {
				if(iterator.next().getStartTime().isBefore(present)) // Does the appointment start in the past?
					iterator.remove(); // Remove the object from the set.
			}
		}
	}
	
	/** Get a string array representing the available times to book appointments for this barber. 
	 * @param start -The start of the period to look for.
	 * @param end -The end of the period to look for.
	 * @return A queue of strings representing time intervals free for bookings.**/
	public Queue<String> showFreeTime(ZonedDateTime start, ZonedDateTime end) {
		Queue<String> queue = new LinkedList<String>();
		LocalDate date = start.toLocalDate();
		String timeInterval = "";
		
		queue.add("Available free time for " + mName + ":\n");
		
		while(date.isBefore(end.toLocalDate())) {
			Queue<String> tempQueue = new LinkedList<String>();
			
			tempQueue.add(date.toString());
			timeInterval = mStartOfDay.toString() + " - ";
			for(Appointment appointment : mAppointments) {
				if(appointment.getStartTime().getDayOfMonth() == date.getDayOfMonth())	
					if(appointment.getStartTime().isAfter(start) && appointment.getEndTime().isBefore(end)) {
						// Add the time to the current line and add the line to the queue.
						timeInterval += appointment.getStartTime().minusMinutes(1).toLocalTime().toString();
						tempQueue.add(timeInterval);
						
						// Start a new line
						timeInterval = appointment.getEndTime().plusMinutes(1).toLocalTime().toString() + " - ";
					}
			}
			
			// Add the end of the day to the last line.
			timeInterval += mEndOfDay.toString();
			tempQueue.add(timeInterval + "\n");
			
			if(tempQueue.size() > 1) {
				String top = null;
				
				while(!tempQueue.isEmpty()){
					top = tempQueue.poll();
					
					if(top != null)
						queue.add(top);
				}
			}
			
			date = date.plusDays(1);
		}
		
		return queue;
	}
	
}
