package commands;

import java.util.Map;
import java.util.Map.Entry;

import bankSystem.CustomerAccount;
import bankSystem.UserInterface;

public class AllBalanceCommand implements Command {

	@Override
	public void execute(CustomerAccount ca) {
		Map<String, Double> map = ca.getAllBankAccountBalance();
		
		String string = "Account Balances:\n";
		java.util.Iterator<Entry<String, Double>> it = map.entrySet().iterator();
		
		while(it.hasNext()) {
			Entry<String, Double> line = it.next();
			string += "\t\u25CF \"" + line.getKey() + "\": " + line.getValue() + "kr\n";
		}
		
		string += "\n";
		
		UserInterface.getSingleton().print(string);
	}

}
