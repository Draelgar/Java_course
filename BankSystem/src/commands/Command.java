package commands;

import bankSystem.CustomerAccount;

/** This interface is for simple containers for commands from the UI.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public interface Command {
	public void execute(CustomerAccount ca);
}
