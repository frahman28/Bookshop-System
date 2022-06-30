
public class Paperback extends Book {
	private String numberOfPages;
	private String condition;
	public Paperback(String ISBN, String type, String title, String language, String genre, String releaseDate, String price, String quantity, String numberOfPages, String condition) {
		super(ISBN, type, title, language, genre, releaseDate, price, quantity);
		this.numberOfPages = numberOfPages;
		this.condition = condition;
	}
	@Override
	public String getAddInfo1() {
		return this.numberOfPages;
	}
	@Override
	public String getAddInfo2() {
		return this.condition;
	}
}
