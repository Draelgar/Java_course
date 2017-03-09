package mainPackage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

/** This class handles the conversion between the user interface and the back end program. **/
public class Program {
	private BarberShopHandler mBarberShopHandler;
	
	public Program() {
		mBarberShopHandler = new BarberShopHandler();
	}
	
	/** Execute the main loop of this program. It will run until the user decides to pick the exit option. **/
	public void run() {
		mBarberShopHandler.update(); // Remove out dated data.
		
		ZonedDateTime localZdt = ZonedDateTime.now(); // Convert the local time to a more suitable format.
		String time = localZdt.toLocalTime().toString(); // Get the current local time as a string.
		time = time.substring(0, time.length() - 7); // Cut seconds and milliseconds from the end.
		UserInterface.println("Welcome to this barber booking system. " + localZdt.toLocalDate().toString() + ", " + time);
		
		ProgramMode mode = ProgramMode.NONE;
		String input = "";
		
		// Loop until the user tells the program to exit.
		while(mode != ProgramMode.EXIT) {
			input = "";
			
			switch(mode) {
				case EXIT: {
					return;
				}
				case LIST_APPOINTMENTS: {
					// TODO Ask for time interval
					// TODO List appointments
					break;
				}
				case LIST_AVAILABLE_TIME: {
					// TODO Ask for time interval
					// TODO List the available time
					break;
				}
				case MAIN_MENU: {
					mode = UserInterface.showMenu();
					break;
				}
				case BOOK_APPOINTMENT: {
					// TODO Ask for input
					// TODO Book the appointment
					break;
				}
				case CANCEL_APPOINTMENT: {
					// TODO Ask for input
					// TODO Cancel the appointment
					break;
				}
				case PICK_BARBER: {
					UserInterface.println("These are the barbers that work here: \n");
					String[] barbers = mBarberShopHandler.barbersToString();
					
					for(String barber : barbers)
						UserInterface.println(barber);
					
					UserInterface.println("Please, type the name of the barber you wish to select.");
					input = UserInterface.getInput();
					
					if(!input.equalsIgnoreCase("cancel")) {
						input = mBarberShopHandler.pickBarber(input);
						
						UserInterface.println(input + " has been selected. ");
					}
						
					break;
				}
				case SAVE: {
					UserInterface.println("Please, select the path of the file you wish to save to.");
					input = UserInterface.getInput();
					
					if(!input.equalsIgnoreCase("cancel"))
						try {
							mBarberShopHandler.save(input);
						} catch (IOException e) {
							UserInterface.println("An error has occured: " + e.getMessage());
						}
						
					break;
				}
				case LOAD: {
					UserInterface.println("Please, select the path of the file you wish to load.");
					input = UserInterface.getInput();
					
					if(!input.equalsIgnoreCase("cancel"))
						try {
							mBarberShopHandler.load(input);
						} catch (DateTimeParseException e) {
							UserInterface.println("An error has occured: " + e.getMessage());
						} catch (ArrayIndexOutOfBoundsException e) {
							UserInterface.println("An error has occured: " + e.getMessage());
						} catch (NumberFormatException e) {
							UserInterface.println("An error has occured: " + e.getMessage());
						} catch (IOException e) {
							UserInterface.println("An error has occured: " + e.getMessage());
						}
					break;
				}
				default: {
					mode = ProgramMode.NONE;
					break;
				}
			}
			
			// Every two hours, remove old data.
			if(localZdt.toLocalTime().isAfter(LocalTime.now().plusHours(2)))
				mBarberShopHandler.update();
		}
	}
	
	/** The main method of this application. **/
	public static void main(String[] args) {
		
	}
}
