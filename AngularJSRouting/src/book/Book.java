/**
 * 
 */
package book;

/**
 * @author Gustaf Peter Hultgren **/
public class Book {
	private int id;
	private String title;
	private String authorFirstName;
	private String authorLastName;
	
	public Book() {
		id = 0;
		title = "";
		authorFirstName = "";
		authorLastName = "";
	}
	
	public Book(int id, String title, String author) {
		this.id = id;
		this.title = title;
		this.authorFirstName = author;
	}
	
	public Book(int id, String title, String firstName, String lastName) {
		this.id = id;
		this.title = title;
		this.authorFirstName = firstName;
		this.authorLastName = lastName;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return authorFirstName;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.authorFirstName = author;
	}
	
	public void setAuthorFirstName(String firstName) {
		this.authorFirstName = firstName;
	}
	
	public void setAuthorLastName(String lastName) {
		this.authorLastName = lastName;
	}
	
	public String getAuthorFirstName() {
		return authorFirstName;
	}
	
	public String getAuthorLastName() {
		return authorLastName;
	}
	
}
