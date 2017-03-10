package mainPackage;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This class handles the conversion between the user interface and the back end program. **/
public class Program {
	private BarberShop mBarberShop;
	
	public Program() {
		mBarberShop = new BarberShop();
	}
	
	/** Execute the main loop of this program. It will run until the user decides to pick the exit option. **/
	public void run() {
		UserInterface ui = UserInterface.getSingleton();
		try {
			mBarberShop.load("data/data.txt");
		} catch (DateTimeParseException | ArrayIndexOutOfBoundsException | NumberFormatException | IOException e) {
			UserInterface.getSingleton().println(e.getMessage());
		}
		mBarberShop.update(); // Remove out dated data.
		
		ZonedDateTime localZdt = ZonedDateTime.now(); // Convert the local time to a more suitable format.
		String time = localZdt.toLocalTime().toString(); // Get the current local time as a string.
		time = time.substring(0, time.length() - 7); // Cut seconds and milliseconds from the end.
		ui.println("Welcome to this barber booking system. " + localZdt.toLocalDate().toString() + ", " + time);
		
		ProgramMode mode = ProgramMode.NONE;
		String input = "";
		ZonedDateTime dateTime = null;
		
		// Loop until the user tells the program to exit.
		while(mode != ProgramMode.EXIT) {
			input = "";
			
			switch(mode) {
				case EXIT: {
					return;
				}
				case LIST_APPOINTMENTS: {
					// Ask for time interval
					TimeMode timeMode = ui.getTimeMode();
					if(timeMode != TimeMode.NONE) {
						// List the appointments
						Queue<String> queue = mBarberShop.getAppointments(timeMode);
						for(String s : queue) {
							ui.println(s);
						}
					}
					
					mode = ProgramMode.MAIN_MENU;
					break;
				}
				case LIST_AVAILABLE_TIME: {
					// Ask for time interval
					TimeMode timeMode = ui.getTimeMode();
					if(timeMode != TimeMode.NONE) {
						// List the available time
						Queue<String> queue = mBarberShop.getFreeTime(timeMode);
						for(String s : queue) {
							ui.println(s);
						}
					}
					
					mode = ProgramMode.MAIN_MENU;
					break;
				}
				case MAIN_MENU: {
					mode = ui.showMenu();
					break;
				}
				case BOOK_APPOINTMENT: {
					String name;
					// Ask for name
					ui.println("What's your name?");
					name = ui.getInput();
					
					// If cancel was types, return to the main menu.
					if(name.equalsIgnoreCase("cancel")) {
						mode = ProgramMode.MAIN_MENU;
						break;
					}
					
					boolean repeat = true;
					
					while (repeat) {
						// Ask for date & time input
						ui.println("Please, type the date and time in question (YYYY-MM-DD hh:mm).");
						input = ui.getInput();
						
						// If cancel was typed, return to the main menu.
						if(input.equalsIgnoreCase("cancel")) {
							mode = ProgramMode.MAIN_MENU;
							break;
						}
						
						dateTime = getDateTime(input);
						
						if(dateTime == null)
							break;
						
						// Ask for duration
						ui.println("How many minutes do you wish to book?");
						input = ui.getInput();
						
						// If cancel was typed, return to the main menu.
						if(input.equalsIgnoreCase("cancel")) {
							mode = ProgramMode.MAIN_MENU;
							break;
						}
						
						try {
							Duration duration = Duration.ofMinutes(Integer.parseUnsignedInt(input));
							
							if(duration.toMinutes() <= 0)
								break;
							
							// Ask for recurrence
							ui.println("For recurred booking, how many weeks between each time? (0 = only a single booking).");
							input = ui.getInput();
							
							// If cancel was typed, return to the main menu.
							if(input.equalsIgnoreCase("cancel")) {
								mode = ProgramMode.MAIN_MENU;
								break;
							}
							
							// Book the appointment
							if(dateTime != null) {
								// Cancel the appointment
								if(mBarberShop.bookAppointment(dateTime, duration, name, mBarberShop.getPrice() * duration.toMinutes(), Integer.parseUnsignedInt(input))) {
									ui.println("The appointment was successfully booked!");
									mode = ProgramMode.MAIN_MENU;
									repeat = false;
								}
								else
									ui.println("The given date and time was already occupied, please try again or change barber.");
							}
						
						} catch (NumberFormatException | ArithmeticException e) {
							ui.println("The given data was invalid: " + e.getMessage());
						}
					}
					
					break;
				}
				case CANCEL_APPOINTMENT: {
					// Ask for date & time input
					ui.println("Please, type the date and time in question (YYYY-MM-DD hh:mm).");
					input = ui.getInput();
					
					// If cancel was typed, return to the main menu.
					if(input.equalsIgnoreCase("cancel")) {
						mode = ProgramMode.MAIN_MENU;
						break;
					}

					dateTime = getDateTime(input);
						
					if(dateTime != null) {
						// Cancel the appointment
						if(mBarberShop.cancelAppointment(dateTime))
							ui.println("The appointment was successfully canceled!");
						else
							ui.println("No appointment could be found for the given date and time.");
						
						mode = ProgramMode.MAIN_MENU;
					}
					
					break;
				}
				case PICK_BARBER: {
					ui.println("These are the barbers that work here: \n");
					String[] barbers = mBarberShop.barbersToString();
					
					for(String barber : barbers)
						ui.println(barber);
					
					ui.println("Please, type the name of the barber you wish to select.");
					input = ui.getInput();
					
					if(!input.equalsIgnoreCase("cancel")) {
						input = mBarberShop.pickBarber(input);
						
						ui.println(input + " has been selected. ");
					}
					
					mode = ProgramMode.MAIN_MENU;
						
					break;
				}
				case SAVE: {
					ui.println("Please, select the path of the file you wish to save to.");
					input = ui.getInput();
					
					if(!input.equalsIgnoreCase("cancel"))
						try {
							mBarberShop.save(input);
						} catch (IOException e) {
							ui.println("An error has occured: " + e.getMessage());
						}
						
					mode = ProgramMode.MAIN_MENU;
					
					break;
				}
				case LOAD: {
					ui.println("Please, select the path of the file you wish to load.");
					input = ui.getInput();
					
					if(!input.equalsIgnoreCase("cancel"))
						try {
							mBarberShop.load(input);
						} catch (DateTimeParseException e) {
							ui.println("An error has occured: " + e.getMessage());
						} catch (ArrayIndexOutOfBoundsException e) {
							ui.println("An error has occured: " + e.getMessage());
						} catch (NumberFormatException e) {
							ui.println("An error has occured: " + e.getMessage());
						} catch (IOException e) {
							ui.println("An error has occured: " + e.getMessage());
						}
					
					mode = ProgramMode.MAIN_MENU;
					break;
				}
				default: {
					mode = ProgramMode.MAIN_MENU;
					break;
				}
			}
			
			// Every two hours, remove old data.
			if(localZdt.toLocalTime().isAfter(LocalTime.now().plusHours(2)))
				mBarberShop.update();
		}
		
		try {
			mBarberShop.save("data/data.txt");
		} catch (IOException e) {
			UserInterface.getSingleton().println(e.getMessage());
		}
		
		ui.close();
	}
	
	/** Converts a string into a ZonedDateTime object.
	 *  @param input -The string to convert.
	 *  @return The resulting ZonedDateTime object. Null if the string was invalid.**/
	private ZonedDateTime getDateTime(String input) {
		UserInterface ui = UserInterface.getSingleton();
		
		int year = 0, month = 0, day = 0, hour = 0, minute = 0;
		Pattern pattern = Pattern.compile("(\\d+)");
		Matcher matcher;
		
		try { // Search for the correct data.
			matcher = pattern.matcher(input);
			if(matcher.find()) {
				year = Integer.parseUnsignedInt(matcher.group(1));
				if(matcher.find()) {
					month = Integer.parseUnsignedInt(matcher.group(1));
					if(matcher.find()) {
						day = Integer.parseUnsignedInt(matcher.group(1));
						if(matcher.find()) {
							hour = Integer.parseUnsignedInt(matcher.group(1));
							if(matcher.find()) {
								minute = Integer.parseUnsignedInt(matcher.group(1));
							}
						}
					}
				}
			}
			
			// Was the minute variable properly set?
			if(minute < 0) {
				ui.println(input + " is invalid, please try again.");
				return null;
			}
			
			return ZonedDateTime.of(year, month, day, hour, minute, 0, 0, ZonedDateTime.now().getZone());
			
		} catch(NumberFormatException | DateTimeException e) {
			ui.println(input + " is invalid, please try again. " + e.getMessage());
		}
		
		ui.println(input + " is invalid, please try again.");
		return null;
	}
	
	/** The main method of this application. **/
	public static void main(String[] args) {
		Program program = new Program();
		program.run();
	}
	
}
