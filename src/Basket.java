import java.util.ArrayList;

public class Basket {
	private ArrayList<Book> contents = new ArrayList<Book>();
	
	Basket(ArrayList<Book> contents) {
		this.contents = contents;
	}
	
	public ArrayList<Book> getContents() {
		return this.contents;
	}
	
	/* Adds the book object passed in to the ArrayList of book objects in the basket.
	 * @param book object which the user has selected passed in as it needs to be added to the basket. 
	 */
	public void addToContents(Book book) {
		
		if (this.contents == null) { //Checking if the contents of the ArrayList is null so an error is not caused.
			contents = new ArrayList<Book>(); //Sets contents as a new ArrayList of book objects as initially it is null.
			contents.add(book); //Adds the book object to the ArrayList.
		} 
		else { //If the ArrayList contents is not null.
			contents.add(book); //Adds the book object to the ArrayList.
		}	
	}
	

	/* Clears contents of ArrayList storing user's books.
	 */
	public void empty() { 
		this.contents.clear();
	}
}
