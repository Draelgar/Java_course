package shopPackage;

public class Item {
	private long mArticleNumber;
	private double mPrice;
	private String mDescription;
	private int mCount;
	
	// Create a new instance of this class.
	public Item(long articleNumber, double price, String description) {
		mArticleNumber = articleNumber;
		mPrice = price;
		mDescription = description;
		mCount = 1;
	}
	
	@Override
	public String toString() {
		return "Art no: " + mArticleNumber + "\nPrice: " + mPrice + " kr\n-------------------------------------\nDescription:\n" + mDescription + "\nCount: " + mCount + "\n\n";
	}
	
	@Override
	public boolean equals(Object o) {
		//assert(o.getClass() == Item.class);
		
		Item item = (Item)o;
		if(item.getArticleNumber() == mArticleNumber)
			return true;
		else
			return false;
	}
	
	public long getArticleNumber() {
		return mArticleNumber;
	}
	
	public double getPrice() {
		return mPrice;
	}
	
	public String getDescription() {
		return mDescription;
	}
	
	public int getCount() {
		return mCount;
	}
	
	public void setArticleNumber(long articleNumber) {
		mArticleNumber = articleNumber;
	}
	
	public void setPrice(double price) {
		mPrice = price;
	}
	
	public void setDescription(String description) {
		mDescription = description;
	}
	
	public void add(int amount) {
		mCount += amount;
	}
	
	public void subtract(int amount) {
		mCount -= amount;
	}
}
