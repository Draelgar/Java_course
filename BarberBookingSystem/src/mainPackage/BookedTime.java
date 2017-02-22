package mainPackage;

import java.time.ZonedDateTime;

public class BookedTime {
	private ZonedDateTime mStartTime;
	private ZonedDateTime mEndTime;
	private String mCustomer;
	private String mBarber;
	
	public BookedTime() {
		mStartTime = ZonedDateTime.now();
		mEndTime = ZonedDateTime.now();
		mCustomer = "None";
		mBarber = "None";
	}
	
	public BookedTime(ZonedDateTime start, ZonedDateTime end, String customer, String barber) {
		mStartTime = start;
		mEndTime = end;
		mCustomer = customer;
		mBarber = barber;
	}
	
	public void setTimeInterval(ZonedDateTime start, ZonedDateTime end) {
		mStartTime = start;
		mEndTime = end;
	}
	
	public void setBarber(String barber) {
		mBarber = barber;
	}
	
	public void setCustomer(String customer) {
		mCustomer = customer;
	}
	
	public ZonedDateTime getStartTime() {
		return mStartTime;
	}
	
	public ZonedDateTime getEndTime() {
		return mEndTime;
	}
	
	public String getCustomer() {
		return mCustomer;
	}
	
	public String getBarber() {
		return mBarber;
	}
	
	// Check weather the given time interval overlaps the booked one.
	public boolean isOverlaping(ZonedDateTime start, ZonedDateTime end) {
		if((start.isAfter(mStartTime) && start.isBefore(mEndTime)) || 
				(end.isAfter(mStartTime) && end.isBefore(mEndTime)))
			return true;
		
		return false;
	}
	
	public void print() {
		System.out.println("Date: " + mStartTime.toLocalDate().toString());
		System.out.println("Barber: " + mBarber);
		System.out.println("Customer: " + mCustomer);
		
		// Convert time to a string representation and cut the seconds.
		String start = mStartTime.toLocalTime().toString().substring(0, 7);
		String end = mEndTime.toLocalTime().toString().substring(0, 7);
		
		System.out.println("Time: " + start + " - " + end);
	}
}
