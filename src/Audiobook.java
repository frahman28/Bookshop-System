
public class Audiobook extends Book {
	private String length;
	private String format;
	public Audiobook(String ISBN, String type, String title, String language, String genre, String releaseDate, String price, String quantity, String length, String format) {
		super(ISBN, type, title, language, genre, releaseDate, price, quantity);
		this.length = length;
		this.format = format;
	}
	@Override
	public String getAddInfo1() {
		return this.length;
	}
	@Override
	public String getAddInfo2() {
		return this.format;
	}	
}
