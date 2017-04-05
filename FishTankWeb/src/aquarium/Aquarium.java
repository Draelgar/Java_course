/**
 * 
 */
package aquarium;

import java.sql.SQLException;

import database.SQLManager;

/**
 * @author Gustaf Peter Hultgren **/
public class Aquarium {
	private SQLManager mSqlManager;
	
	public Aquarium() {
		mSqlManager = new SQLManager();
		try {
			mSqlManager.initialize();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addFish(Fish fish) {
		try {
			mSqlManager.updateDatabase("INSERT INTO fish VALUES("
					+ fish.calculateIndex() + ",'"
					+ fish.fishType().toString() + "','"
					+ fish.name() + "',"
					+ fish.weight() + ","
					+ fish.length() + ","
					+ fish.age() + ","
					+ (int)((fish.isMale()) ? 1 : 0) + ",'"
					+ fish.fishClass().toString() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
