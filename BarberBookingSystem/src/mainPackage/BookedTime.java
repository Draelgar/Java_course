package mainPackage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;

@SuppressWarnings("rawtypes")
public class BookedTime implements Comparable{
	private ZonedDateTime mStartTime;
	private Duration mDuration;
	private String mCustomer;
	private String mBarber;
	private int mRecurring;
	
	public BookedTime() {
		mStartTime = ZonedDateTime.now();
		mDuration = Duration.ofHours(1);

		mCustomer = "None";
		mBarber = "None";
		mRecurring = 0;
	}
	
	// Create a new BookedTime object from a data string.
	public BookedTime(String data) {
		String[] sarr = data.split(",");
		
		mBarber = sarr[0];
		mCustomer = sarr[1];
		mStartTime = ZonedDateTime.parse(sarr[2]);
		mDuration = Duration.ofMinutes(Integer.parseInt(sarr[3]));
		mRecurring = Integer.parseInt(sarr[4]);
	}
	
	public BookedTime(ZonedDateTime start, Duration duration, String customer, String barber, int recurring) {
		mStartTime = start;
		mDuration = duration;
		mCustomer = customer;
		mBarber = barber;
		
		mRecurring = recurring;
	}
	
	public void setTimeInterval(ZonedDateTime start, Duration duration) {
		mStartTime = start;
		mDuration = duration;
	}
	
	public void setBarber(String barber) {
		mBarber = barber;
	}
	
	public void setCustomer(String customer) {
		mCustomer = customer;
	}
	
	public int getRecurring() {
		return mRecurring;
	}
	
	public ZonedDateTime getStartTime() {
		return mStartTime;
	}
	
	public ZonedDateTime getEndTime() {
		ZonedDateTime endTime = mStartTime;
		endTime = endTime.plusMinutes(mDuration.toMinutes());
		
		return endTime;
	}
	
	public Duration getDuration() {
		return mDuration;
	}
	
	public String getCustomer() {
		return mCustomer;
	}
	
	public String getBarber() {
		return mBarber;
	}
	
	// Check weather the given time interval overlaps the booked one.
	public boolean isOverlaping(String barber, ZonedDateTime start, ZonedDateTime end) {
		if(mBarber.equalsIgnoreCase(barber))
			if(start.isAfter(getEndTime()) || end.isBefore(mStartTime))
				return false;
			else
				return true;
		
		return false;
	}
	
	public int compareTo(Object o) {
		
		BookedTime bt = (BookedTime)o;
		if(bt.getStartTime().isAfter(mStartTime))
			return -1;
		else
			return 1;
	}
	
	public String print() {
		
		String text = "Date: " + mStartTime.toLocalDate().toString() + "\n";
		text += "Barber: " + mBarber + "\n";
		text += "Customer: " + mCustomer + "\n";
		
		// Convert time to a string representation.
		String start = mStartTime.toLocalTime().toString().substring(0);
		String end = getEndTime().toLocalTime().toString().substring(0);
		
		text += "Time: " + start + " - " + end + "\n";
		
		return text;
	}
	
	public void save(BufferedWriter bw) {
		try {
			bw.append(mBarber + "," + mCustomer + "," + mStartTime.toString() + "," + mDuration.toMinutes() + "," + mRecurring);
			bw.newLine();
		
		} catch(IOException e) {
			System.out.println("Error! " + e.getMessage());
		}
	}
}
