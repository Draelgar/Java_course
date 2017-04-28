/**
 * 
 */
package aquarium;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

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
	
	public void close() {
		try {
			mSqlManager.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Fish> fetch() {
		try {
			ArrayList<Map<String, Object>> list = mSqlManager.sendQuery("SELECT * FROM fish ORDER BY name ASC;");
			
			ArrayList<Fish> fishes = new ArrayList<Fish>();
			
			for(Map<String, Object> row : list) {
				String type = (String)row.get("type");
				String name = (String)row.get("name");
				double weight = (double)row.get("weight");
				double length = (double)row.get("length");
				int age = (int)row.get("age");
				boolean male = (boolean)row.get("gender");
				
				FishType fishType = FishType.valueOf(type.toUpperCase());
				
				switch(fishType) {
					case SHARK: {
						fishes.add(new Shark(name, weight, length, age, male));
						break;
					}
					case EEL: {
						fishes.add(new Eel(name, weight, length, age, male));
						break;
					}
					case PIKE: {
						fishes.add(new Pike(name, weight, length, age, male));
						break;
					}
					case CLOWNFISH: {
						fishes.add(new Clownfish(name, weight, length, age, male));
						break;
					}
					case BASS: {
						fishes.add(new Bass(name, weight, length, age, male));
						break;
					}
					default: {
						
					}
				}
			}
			
			Gson gson = new Gson();
			String data = gson.toJson(fishes.toArray());
			
			System.out.println(data);
			
			return fishes;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
