import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

public class Customer extends User {
	public Customer(String userid, String username, String surname, String housenumber, String postcode, String city, String role) {
		super(userid, username, surname, housenumber, postcode, city, role);
	}
	
	
	/* This method takes the book which the user wants to add to their basket,
	 * and adds it to the basket object by calling the addToContents() method from
	 * the Basket class.
	 * @param book object passed in which needs to be added to the basket.
	 * @param basket object where the book is added to.
	 * @return updated basket object with the book added.
	 */
	public Basket addToBasket(Book book, Basket basket) {
		basket.addToContents(book);
		return basket;
	}
	
	/* This method takes the basket object and adds up the price of each book
	 * in it returning the total cost and updating the log file as well as
	 * removing the books from the basket table on the window and clearing 
	 * the basket contents.
	 * @param basket object which contains the books that the user wants to buy.
	 * @param dtmBasket table model which needs to be cleared.
	 * @param P the string containing the method the user used to pay, which is added to the log.
	 * @return total cost of books in the basket.
	 */
	public double pay(Basket basket, DefaultTableModel dtmBasket, String P) throws IOException {
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = formatter.format(objDate);
		Double total = 0.00; //Initialise double which will store the total cost 
		File inputFile = new File("ActivityLog.txt");
		Scanner fileScanner = new Scanner(inputFile);
		ArrayList<String> log = new ArrayList<String>(); //This arraylist will be used to store previous logs if they exist so that they can be written again under the new logs.
		if (inputFile.length() == 0) { //Checking if the file is empty.
			//The file being empty means that the logs can be written straight from the beginning, without having to save any previous logs.
			FileWriter outputFile;
			outputFile = new FileWriter("ActivityLog.txt", true);
			BufferedWriter bw = new BufferedWriter(outputFile);
			for (Book b : basket.getContents()) {
				//Loop through each book, add to total and log the activity. 
				total += Double.parseDouble(b.getPrice()); //Converting the string to double to be added to total.
				//Writing the log string using the book object's details and user's details with getter methods, ending with a newline character so next log can be written under.
				bw.write(this.getUserID() + ", " + this.getPostCode() + ", " + b.getISBN() + ", " + b.getPrice() + ", " + b.getQuantity() + ", purchased, " + P + ", " + strDate + "\n");
				dtmBasket.removeRow(0);
			}
			bw.close();
		}
		else {
			//There are previous logs in the file, which must be saved before the new logs are written from the top.
			while (fileScanner.hasNextLine()) { 
				//Loop through file, saving each line as a string into an arraylist.
				log.add(fileScanner.nextLine());
			}
			FileWriter outputFile;
			outputFile = new FileWriter("ActivityLog.txt", false);
			BufferedWriter bw = new BufferedWriter(outputFile);
			for (Book b : basket.getContents()) {
				//Loop through each book, add to total and log the activity. 
				total += Double.parseDouble(b.getPrice()); //Converting the string to double to be added to total.
				//Writing the log string using the book object's details and user's details with getter methods, ending with a newline character so next log can be written under.
				bw.write(this.getUserID() + ", " + this.getPostCode() + ", " + b.getISBN() + ", " + b.getPrice() + ", " + b.getQuantity() + ", purchased, " + P + ", " + strDate + "\n");
				dtmBasket.removeRow(0);
			}
			for (int i = 0; i < log.size(); i++) {
				//Loop through each book in the basket and log the activity.
				//Writing the previous logs in after from log arraylist, ending with a newline so next log can be written under.
				bw.write(log.get(i) + "\n");
			}
			bw.close();
		}
		basket.empty(); //The empty() method is called from the Basket class to clear the contents.
		return total;
	}
	
	/* This method takes the user's basket and clears the contents as
	 * well as logging the cancel.
	 * @param basket object which needs to have it's contents emptied.
	 * @return empty basket object.
	 */
	public Basket cancel(Basket basket) throws IOException {
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = formatter.format(objDate);
		File inputFile = new File("ActivityLog.txt");
		Scanner fileScanner = new Scanner(inputFile);
		ArrayList<String> log = new ArrayList<String>(); //This arraylist will be used to store previous logs if they exist so that they can be written again under the new logs.
		if (inputFile.length() == 0) { //Checking if the file is empty.
			//The file being empty means that the logs can be written straight from the beginning, without having to save any previous logs.
			FileWriter outputFile;
			outputFile = new FileWriter("ActivityLog.txt", false);
			BufferedWriter bw = new BufferedWriter(outputFile);
			for (Book b : basket.getContents()) {
				//Loop through each book in the basket and log the activity.
				//Writing the log string using the book object's details and user's details with getter methods, ending with a newline so next log can be written under.
				bw.write(this.getUserID() + ", " + this.getPostCode() + ", " + b.getISBN() + ", " + b.getPrice() + ", " + b.getQuantity() + ", cancelled, , " + strDate + "\n");
			}
			bw.close();
		}
		else {
			//There are previous logs in the file, which must be saved before the new logs are written from the top.
			while (fileScanner.hasNextLine()) { 
				//Loop through file, saving each line as a string into an arraylist.
				log.add(fileScanner.nextLine());
			}
			FileWriter outputFile;
			outputFile = new FileWriter("ActivityLog.txt", false);
			BufferedWriter bw = new BufferedWriter(outputFile);
			for (Book b : basket.getContents()) {
				//Loop through each book in the basket and log the activity.
				//Writing the log string using the book object's details and user's details with getter methods, ending with a newline so next log can be written under.
				bw.write(this.getUserID() + ", " + this.getPostCode() + ", " + b.getISBN() + ", " + b.getPrice() + ", " + b.getQuantity() + ", cancelled, , " + strDate + "\n");
			}
			for (int i = 0; i < log.size(); i++) {
				//Loop through each book in the basket and log the activity.
				//Writing the previous logs in after from log arraylist, ending with a newline so next log can be written under.
				bw.write(log.get(i) + "\n");
			}
			bw.close();
		}
		fileScanner.close();
		basket.empty(); //The empty() method is called from the Basket class to clear the contents.
		return basket;
	}
	
	/* This method adds the book objects passed in to searchList if they 
	 * are not already in it.
	 * @param searchList list containing filtered books.
	 * @param b object which needs to be added to searchList. 
	 * @return updated searchList containing the books which match the criteria.
	 */
	public ArrayList<Book> searchBook(ArrayList<Book> searchList, Book b) {
		if (searchList.contains(b)) { 
			//Checks if searchList has the book in it already, if so, nothing is done.
		} 
		else { 
			searchList.add(b); //If the book is not in the list then it is added.
		}
		return searchList;
	}
}
