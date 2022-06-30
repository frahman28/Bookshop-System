import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Admin extends User {
	public Admin(String userid, String username, String surname, String housenumber, String postcode, String city, String role) {
		super(userid, username, surname, housenumber, postcode, city, role);
	}
	
	/* This method takes fields the user entered, checking if they are valid, and creates a new object of the 
	 * correct type if the ISBN is unique adding it to the table, if it has an ISBN identical to one already in stock
	 * the quantity is increased. The result is displayed to the user in a message box.
	 * @param isbn string that admin user entered.
	 * @param title string that admin user entered.
	 * @param lang string that admin user entered.
	 * @param genre string that admin user entered.
	 * @param relDate string that admin user entered.
	 * @param price string that admin user entered.
	 * @param addInfo1 string that admin user entered.
	 * @param addInfo2 string that admin user entered.
	 * @param types book typer admin user selected from the dropdown.
	 * @param bookList list of all book objects in stock.
	 */
	public void addBook(JTextField isbn, JTextField title, JTextField lang, JTextField genre, JTextField relDate, JTextField price, JTextField addInfo1, JTextField addInfo2, JComboBox types, List<Book> bookList) {
		int i = 0; //Local variable used to count the unique books in bookList.
		boolean areFieldsNull = false; //Local boolean to store whether the any field is empty or not.
		if (isbn.getText().isBlank() || title.getText().isBlank() || lang.getText().isBlank() || genre.getText().isBlank() || relDate.getText().isBlank() || price.getText().isBlank() || addInfo1.getText().isBlank() || addInfo2.getText().isBlank()) {
			//All fields are checked to see if they are empty, if at least one is, areFieldsNull is set to true.
			areFieldsNull = true;
		}
		
		for (Book b : bookList) {
			//Looping through each book in bookList to check if the ISBN matches the entered ISBN.
			if (isbn.getText().equals(b.getISBN())) {
				//If the ISBN matches, the quantity of that book in stock is increased by 1, which is displayed in a message to the user.
				b.setQuantity(String.valueOf(Integer.parseInt(b.getQuantity()) + 1));
				JOptionPane.showMessageDialog(null, "Stock Quantity has been updated");
				break;
			}
			else {
				//If no ISBN in bookList match the inputted ISBN then 'i' is incremented by 1.
				i = i + 1;
			}		
		}
		if ((i == bookList.size()) && areFieldsNull == false) { 
			//If the inputted ISBN does not match any of the books in bookList and if there are valid fields entered, a new book object is created and added to bookList and added to the table.
			if (types.getSelectedItem() == Types.values()[0]) { //Checking if the book type selected by the user is paperback.
				//A new paperback object is created and added to bookList, and added to the table.
				bookList.add(new Paperback(isbn.getText(), types.getSelectedItem().toString(), title.getText(), lang.getText(), genre.getText(), relDate.getText(), price.getText(), "1", addInfo1.getText(), addInfo2.getText()));
			}
			else if (types.getSelectedItem() == Types.values()[1]) { //Checking if the book type selected by the user is ebook.
				//A new ebook object is created and added to bookList, and added to the table.
				bookList.add(new eBook(isbn.getText(), types.getSelectedItem().toString(), title.getText(), lang.getText(), genre.getText(), relDate.getText(), price.getText(), "1", addInfo1.getText(), addInfo2.getText()));
			}
			else if (types.getSelectedItem() == Types.values()[2]) { //Checking if the book type selected by the user is audiobook.
				//A new audiobook object is created and added to bookList, and added to the table.
				bookList.add(new Audiobook(isbn.getText(), types.getSelectedItem().toString(), title.getText(), lang.getText(), genre.getText(), relDate.getText(), price.getText(), "1", addInfo1.getText(), addInfo2.getText()));
			}
			JOptionPane.showMessageDialog(null, "Book added to Stock"); //Message box displayed telling the user the book has been added to the stock.
		}
	}
}
