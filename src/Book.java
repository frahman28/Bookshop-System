import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public abstract class Book{
	private String ISBN;
	private String type;
	private String title;
	private String language;
	private String genre;
	private String releaseDate;
	private String price;
	private String quantity;
	static PriceCompare bkPriceCompare = new PriceCompare();
	
	public Book(String ISBN, String type, String title, String language, String genre, String releaseDate, String price, String quantity) {
		this.ISBN = ISBN;
		this.type = type;
		this.title = title;
		this.language = language;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.price = price;
		this.quantity = quantity;
	}
	public String getISBN() {
		return this.ISBN;
	}
	public String getType() {
		return this.type;
	}
	public String getTitle() {
		return this.title;
	}
	public String getLang() {
		return this.language;
	}
	public String getGenre() {
		return this.genre;
	}
	public String getRelDate() {
		return this.releaseDate;
	}
	public String getPrice() {
		return this.price;
	}
	public String getQuantity() {
		return this.quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public abstract String getAddInfo1();
	public abstract String getAddInfo2();
	
	/* This method reads the file Stock.txt and makes each book into a Book
	 * object of the appropriate type and adds them to a List which is returned.
	 * @return the stock as a list of book objects. 
	 * @throws FileNotFoundException
	 */
	public static List<Book> getStock() throws FileNotFoundException, IndexOutOfBoundsException {
		List<Book> listBook = new ArrayList<Book>(); //Initialise list of book objects to add books to and return.
		File inputFile = new File("Stock.txt");
		Scanner fileScanner = new Scanner(inputFile);
		Paperback pb = null; //Initialise paperback book object to be assigned to when making each book.
		eBook eb = null; //Initialise ebook book object to be assigned to when making each book.
		Audiobook ab = null; //Initialise audiobook book object to be assigned to when making each book.
		while (fileScanner.hasNextLine()) { 
			String[] details = fileScanner.nextLine().split(", ");
			if (details.length != 1) {
				if (details[1].equals("paperback")) {
					//Checks second index in string to see if it is the string paperback so that it can be made into a paperback type book object and added to listBook.
					pb = new Paperback(details[0].trim(), details[1].trim(), details[2].trim(), details[3].trim(), details[4].trim(), details[5].trim(), details[6].trim(), details[7].trim(), details[8].trim(), details[9].trim());
					listBook.add(pb);
				}
				else if (details[1].equals("ebook")) {
					//Checks second index in string to see if it is the string ebook so that it can be made into an ebook type book object and added to listBook.
					eb = new eBook(details[0].trim(), details[1].trim(), details[2].trim(), details[3].trim(), details[4].trim(), details[5].trim(), details[6].trim(), details[7].trim(), details[8].trim(), details[9].trim());
					listBook.add(eb);
				}
				else {
					//As the type is not paperback or ebook, it is the string audiobook so it can be made into an audiobook type book object and added to listBook.
					ab = new Audiobook(details[0].trim(), details[1].trim(), details[2].trim(), details[3].trim(), details[4].trim(), details[5].trim(), details[6].trim(), details[7].trim(), details[8].trim(), details[9].trim());
					listBook.add(ab);
				}
			}
		}
		fileScanner.close();
		Collections.sort(listBook, bkPriceCompare); //Sort the list to be in order of price, ascending.
		return listBook;
	}	
}
