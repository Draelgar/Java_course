package mainPackage;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class Application {
	private DisplayMode mDisplayMode; // Determines which bookings that should be displayed.
	private BookKeeper mBookKeeper;
	private String mBarber;
	
	Application() {
		mDisplayMode = DisplayMode.Day;
		mBookKeeper = null;
		mBarber = null;
	}
	
	// Run the application.
	public void run() {
		
	}
	
	// Save the current booked appointments to file.
	public void save(String path) throws IOException {
		mBookKeeper.save(path);
	}
	
	// Load booked appointments from file.
	public void load(String path) throws DateTimeParseException, ArrayIndexOutOfBoundsException, NumberFormatException, IOException {
		mBookKeeper.load(path);
	}
	
	// Book a new appointment. Returns false should it fail.
	public boolean bookTime(ZonedDateTime startTime, Duration duration, String customer, String barber, int recurring) {
		BookedTime bt = new BookedTime(startTime, duration, customer, mBarber, recurring);	
		return mBookKeeper.bookTime(bt);
	}
	
	// Get a list of barbers.
	public String[] getBarbers() {
		return (String[]) mBookKeeper.getBarbers().toArray();
	}
	
	// Get the free time for selected barber.
	public String getFreeTime() {
		return mBookKeeper.showFreeTime(mDisplayMode);
	}
	
	public static void main(String args[]) {
		UserInterface ui = new UserInterface();
		ui.run();
	}
}
