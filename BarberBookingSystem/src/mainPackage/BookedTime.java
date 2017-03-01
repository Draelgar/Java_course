package mainPackage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public BookedTime(String data) throws IOException, DateTimeParseException, ArrayIndexOutOfBoundsException, NumberFormatException {
		String[] sarr = data.split(",");
		Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}\\+\\d{2}:\\d{2}.+$");
		Matcher matcher = pattern.matcher(sarr[2]);
		
		try {
			mBarber = sarr[0];
			mCustomer = sarr[1];
			if(matcher.find()) {
				mStartTime = ZonedDateTime.parse(sarr[2]);
			}
			else{
				throw new IOException("Zoned date time " + sarr[2] + " is in an incorrect format!");
			}
			mDuration = Duration.ofMinutes(Integer.parseInt(sarr[3]));
			mRecurring = Integer.parseInt(sarr[4]);
		} catch (IOException e) {
			throw e;
		} catch (DateTimeParseException e) {
			throw e;
		} catch (ArrayIndexOutOfBoundsException e){
			throw e;
		} catch (NumberFormatException e) {
			throw e;
		}
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
		int barb = mBarber.compareToIgnoreCase(bt.mBarber);
		
		if(barb == 0) {
			if(bt.getStartTime().isAfter(getEndTime()))
				return -1;
			else if(bt.getEndTime().isBefore(mStartTime))
				return 1;
			else
				return 0;
		}
		else {
			return barb;
		}
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
