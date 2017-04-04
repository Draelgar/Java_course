package commands;

import bankSystem.Bank;

/** This interface is for simple containers for commands from the UI.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public interface Command {
	public void execute(Bank bank);
}
