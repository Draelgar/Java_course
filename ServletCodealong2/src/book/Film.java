/**
 * 
 */
package book;

import java.time.LocalTime;

/**
 * @author Gustaf Peter Hultgren **/
public class Film {
	private int id;
	private String title;
	private LocalTime duration;
	private String genre;
	private int pgi;
	private String bookTitle;
	private int bookId;
	
	public Film(int id, String title, LocalTime duration, String genre, int pgi, String bookTitle, int bookId) {
		this.id = id;
		this.setTitle(title);
		this.setDuration(duration);
		this.setGenre(genre);
		this.setPgi(pgi);
		this.setBookTitle(bookTitle);
		this.setBookId(bookId);
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalTime getDuration() {
		return duration;
	}

	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getPgi() {
		return pgi;
	}

	public void setPgi(int pgi) {
		this.pgi = pgi;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
}
