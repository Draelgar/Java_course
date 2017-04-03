package bankSystem;

import java.util.Map;
import java.util.Map.Entry;

public class AccountsCommand implements Command {

	@Override
	public void execute(CustomerAccount ca) {
		Map<String, Double> map = ca.getAllBankAccountBalance();
		
		String string = "Accounts:\n";
		java.util.Iterator<Entry<String, Double>> it = map.entrySet().iterator();
		
		while(it.hasNext()) {
			Entry<String, Double> line = it.next();
			string += "\t\u25CF \"" + line.getKey() + "\"\n";
		}
		
		string += "\n";
		
		UserInterface.getSingleton().print(string);
	}
	
}
