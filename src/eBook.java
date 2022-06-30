
public class eBook extends Book {
	private String numberOfPages;
	private String format;
	public eBook(String ISBN, String type, String title, String language, String genre, String releaseDate, String price, String quantity, String numberOfPages, String format) {
		super(ISBN, type, title, language, genre, releaseDate, price, quantity);
		this.numberOfPages = numberOfPages;
		this.format = format;
	}
	@Override
	public String getAddInfo1() {
		return this.numberOfPages;
	}
	@Override
	public String getAddInfo2() {
		return this.format;
	}	
}
