import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GUI {

	private JFrame login; //This frame is set to visible when the user is choosing which user to login as.
	private JFrame admin; //This frame is set to visible when the user has logged in as user1 which is the admin account, allowing them to view books and add a book to stock.
	private JFrame customer; //This frame is set to visible when the user has logged in as user2, user3 or user 4 which are customer accounts, allowing them to view books, manage their basket and pay for books.
	private List<Book> bookList; //This list of Book objects is what is used to display the books in the 'View Books' tab in a table format, to be interacted with by the Admin and Customer.
	private List<User> userList; //This list of User object is used in the system to set the admin object and customer objects with the correct details from the accounts.txt file.
	private Admin accountA; //This Admin object is used if the user is logged in as user1 to use the methods in the Admin class.
	private Customer accountC; //This Customer object is used if the user is logged in as user2, user3 or user4 to use the methods in the Customer class.
	private JTable tblBook; //This JTable is used with bookList to display the books in stock in the 'View Books' tab for Admin and Customer.
	private JTextField tfISBN; //This field is where the Admin enters the ISBN for the book they want to add to stock.
	private JTextField tfTitle; //This field is where the Admin enters the Title for the book they want to add to stock.
	private JTextField tfLang; //This field is where the Admin enters the Language for the book they want to add to stock.
	private JTextField tfGenre; //This field is where the Admin enters the Genre for the book they want to add to stock.
	private JTextField tfRelDate; //This field is where the Admin enters the Release Date for the book they want to add to stock.
	private JTextField tfPrice; //This field is where the Admin enters the Price for the book they want to add to stock.
	private JTextField tfAddInfo1; //This field is where the Admin enters Additional Information 1 for the book they want to add to stock.
	private JTextField tfAddInfo2; //This field is where the Admin enters Additional Information 2 for the book they want to add to stock.
	private JTextField tfEmail; //This field is where the Customer enters their PayPal email address when they want to buy the books in their basket.
	private JTextField tfCardNo; //This field is where the Customer enters their Credit Card Number when they want to buy the books in their basket.
	private JTextField tfSecurityNo; //This field is where the Customer enters their Security Number when they want to buy the books in their basket.
	private JTextField tfEntGenre; //This field is where the Customer enters the Genre of the books they want to search for.
	private ArrayList<Book> basketList; //This ArrayList of Book objects is where the books the Customer adds to their basket will be stored and used to display on the window.
	private JTable tblBasket; //This JTable is used with basketList to display the books the Customer has in their basket.
	private Basket basket; //This Basket object is used when the Customer interacts with their basket, to call the methods in the Basket class. 
	private ArrayList<Book> emptyList; //This ArrayList is used to intiliase the basket object with no books when the user first logs in as a Customer.
	private ArrayList<Book> searchList; //This ArrayList is used to store the books which is the user is searching for based on the filters entered so that they can be displayed on the window.
	private boolean filtered; //This boolean checks if the Customer is currently filtering the books displayed so that errors are not caused.
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					//Setting to login window to visible to allow user to choose login details.
					window.login.setVisible(true);
					window.admin.setVisible(false);
					window.customer.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public GUI() throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		initialize();
	}
	
	/**
	 *Initialise the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize() throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		login = new JFrame();
		login.setBounds(100, 100, 1280, 720);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.getContentPane().setLayout(null);
		
		JPanel lgn = new JPanel();
		lgn.setBounds(10, 11, 1244, 659);
		login.getContentPane().add(lgn);
		lgn.setLayout(null);
		
		JComboBox cbChUser = new JComboBox(Users.values());
		cbChUser.setBounds(301, 333, 666, 45);
		lgn.add(cbChUser);
		
		JLabel lblChUser = new JLabel("Choose User:");
		lblChUser.setBounds(579, 240, 328, 57);
		lgn.add(lblChUser);
		
		//Using getAccs() from User class to make a list of user objects with parameters from the accounts file and assign the list to userList to use in this file.
		userList = User.getAccs(); 
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			/* When the user presses Login the required window is set visible depending on the type of
			 * account the user chose and the variable of object type accountA is set to the first user object
			 * in the list and accountC is set to the chosen user from userList.  
			 */
			public void actionPerformed(ActionEvent e) {
				if (cbChUser.getSelectedItem() == Users.values()[0]) {
					//Setting admin window to true and login window to false as user1 is chosen which is an admin account.
					accountA = (Admin) userList.get(0); //Cast User object in userList to Admin object for user1.
					login.setVisible(false);
					admin.setVisible(true);
					try { //bookList is updated.
						bookList = Book.getStock();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if (cbChUser.getSelectedItem() == Users.values()[1]) {
					//Setting customer window to true and login window to false as user2 is chosen which is a customer account.
					accountC = (Customer) userList.get(1); //Cast User object in userList to Customer object for the user2.
					login.setVisible(false);
					customer.setVisible(true);
					try { //bookList is updated.
						bookList = Book.getStock();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if (cbChUser.getSelectedItem() == Users.values()[2]) {
					//Setting customer window to true and login window to false as user3 is chosen which is a customer account.
					accountC = (Customer) userList.get(2); //Cast User object in userList to Customer object for the user3.
					login.setVisible(false);
					customer.setVisible(true);
					try { //bookList is updated.
						bookList = Book.getStock();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if (cbChUser.getSelectedItem() == Users.values()[3]) {
					//Setting customer window to true and login window to false as user4 is chosen which is a customer account.
					accountC = (Customer) userList.get(3); //Cast User object in userList to Customer object for the user4.
					login.setVisible(false);
					customer.setVisible(true);
					try { //bookList is updated.
						bookList = Book.getStock();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnLogin.setBounds(477, 412, 288, 45);
		lgn.add(btnLogin);
		
		admin = new JFrame();
		admin.setBounds(100, 100, 1280, 720);
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1264, 681);
		admin.getContentPane().add(tabbedPane);
		
		JPanel plViewBooks = new JPanel();
		tabbedPane.addTab("View Books", null, plViewBooks, null);
		plViewBooks.setLayout(null);
		
		JScrollPane sPAdmin = new JScrollPane();
		sPAdmin.setBounds(10, 11, 1239, 544);
		plViewBooks.add(sPAdmin);
		
		tblBook = new JTable();
		sPAdmin.setViewportView(tblBook);
		
		DefaultTableModel dtmBook = new DefaultTableModel() {
			//Make table cells noneditable so user does not change table values and cause errors.
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		dtmBook.setColumnIdentifiers(new Object[] {"ISBN", "Type", "Title", "Language", "Genre", "Release Date", "Price", "Quantity in Stock", "Additional Information 1", "Additional Information 2"});
		tblBook.setModel(dtmBook);
		
		//List returned by Book.getStock() of all books in the stock as objects to be used in the system to interact with and display in the 'View Books' tab for the admin window.
		bookList = Book.getStock();
		
		for (Book b : bookList) {
			//Display all the books from the stock. 
			Object[] rowdata = new Object[] {b.getISBN(), b.getType(), b.getTitle(), b.getLang(), b.getGenre(), b.getRelDate(), b.getPrice(), b.getQuantity(), b.getAddInfo1(), b.getAddInfo2()};
			dtmBook.addRow(rowdata);
		}
		
		JPanel plAddBook = new JPanel();
		tabbedPane.addTab("Add Book", null, plAddBook, null);
		plAddBook.setLayout(null);
		
		tfISBN = new JTextField();
		tfISBN.setBounds(488, 56, 476, 20);
		plAddBook.add(tfISBN);
		tfISBN.setColumns(10);
		
		tfTitle = new JTextField();
		tfTitle.setBounds(488, 151, 476, 20);
		plAddBook.add(tfTitle);
		tfTitle.setColumns(10);
		
		tfLang = new JTextField();
		tfLang.setBounds(488, 196, 476, 20);
		plAddBook.add(tfLang);
		tfLang.setColumns(10);
		
		tfGenre = new JTextField();
		tfGenre.setBounds(488, 240, 476, 20);
		plAddBook.add(tfGenre);
		tfGenre.setColumns(10);
		
		tfRelDate = new JTextField();
		tfRelDate.setBounds(488, 283, 476, 20);
		plAddBook.add(tfRelDate);
		tfRelDate.setColumns(10);
		
		tfPrice = new JTextField();
		tfPrice.setBounds(488, 326, 476, 20);
		plAddBook.add(tfPrice);
		tfPrice.setColumns(10);
		
		tfAddInfo1 = new JTextField();
		tfAddInfo1.setBounds(488, 368, 476, 20);
		plAddBook.add(tfAddInfo1);
		tfAddInfo1.setColumns(10);
		
		tfAddInfo2 = new JTextField();
		tfAddInfo2.setBounds(488, 411, 476, 20);
		plAddBook.add(tfAddInfo2);
		tfAddInfo2.setColumns(10);
		
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setBounds(456, 59, 46, 14);
		plAddBook.add(lblISBN);
		
		JComboBox cbType = new JComboBox(Types.values());
		cbType.setBounds(488, 102, 121, 22);
		plAddBook.add(cbType);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(456, 106, 37, 14);
		plAddBook.add(lblType);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(458, 154, 46, 14);
		plAddBook.add(lblTitle);
		
		JLabel lblLang = new JLabel("Language:");
		lblLang.setBounds(425, 199, 72, 14);
		plAddBook.add(lblLang);
		
		JLabel lblGenre = new JLabel("Genre:");
		lblGenre.setBounds(447, 243, 46, 14);
		plAddBook.add(lblGenre);
		
		JLabel lblRelDate = new JLabel("Release Date (DD-MM-YYYY):");
		lblRelDate.setBounds(324, 286, 226, 14);
		plAddBook.add(lblRelDate);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(447, 329, 37, 14);
		plAddBook.add(lblPrice);
		
		JLabel lblAddInfo1 = new JLabel("Additional Information (Paperback & eBook: Number of Pages/ Audiobooks: Length):");
		lblAddInfo1.setBounds(10, 371, 482, 14);
		plAddBook.add(lblAddInfo1);
		
		JLabel lblAddInfo2 = new JLabel("Additional Information (Paperback: Condition / eBook & Audiobooks: Format):");
		lblAddInfo2.setBounds(51, 414, 459, 14);
		plAddBook.add(lblAddInfo2);
		
		JButton btnLogoutA = new JButton("Logout");
		btnLogoutA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pressing the logout button takes the user back to login window so admin window is set to false and login window is set to true.
				login.setVisible(true);
				admin.setVisible(false);
				
				//Empty all text fields so inputted string is not carried over.
				tfISBN.setText(null);
				tfTitle.setText(null);
				tfLang.setText(null);
				tfGenre.setText(null);
				tfRelDate.setText(null);
				tfPrice.setText(null);
				tfAddInfo1.setText(null);
				tfAddInfo2.setText(null);
			}
		});
		btnLogoutA.setBounds(1160, 590, 89, 23);
		plViewBooks.add(btnLogoutA);
		
		JButton btnAddBook = new JButton("Add Book");
		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pressing Add Book adds a book to the table in 'View Books' and to the stock file using the details entered by the user.
				boolean validDate = true; //Boolean which is used to check if the release date inputted is in the correct format.
		        DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
		        date_format.setLenient(false); //The Release Date entered must match this exact format
		        try {
		            date_format.parse(tfRelDate.getText()); //Parse to check format.
		        } catch (ParseException e1) {
		            validDate = false; //If parse exception is caused due to incorrect format validDate is set to false.
		        }
				if (tfISBN.getText().isBlank() || tfTitle.getText().isBlank() || tfLang.getText().isBlank() || tfGenre.getText().isBlank() || tfRelDate.getText().isBlank() || tfPrice.getText().isBlank() || tfAddInfo1.getText().isBlank() || tfAddInfo2.getText().isBlank()) {
					//Checks if any of the fields are empty, in which case a message is displayed.
					JOptionPane.showMessageDialog(null, "Field(s) Missing");
				} 
				else if (!tfPrice.getText().chars().allMatch(Character::isDigit)) {
					//Checks if the price entered is not numerical, in which case a message is displayed.
					JOptionPane.showMessageDialog(null, "The Price must be a numerical value");
				}
				else if (!tfISBN.getText().chars().allMatch(Character::isDigit) || tfISBN.getText().length() != 8) {
					//Checks if the ISBN entered is not entered with digits, in which case a message is displayed.
					JOptionPane.showMessageDialog(null, "The ISBN number must be 8 digits and consist of only numerical values");
				}
				else if (validDate == false) {
					//Checks if the Release Date entered if in the correct format.
					JOptionPane.showMessageDialog(null, "The Release Date must be a valid date entered in the format DD-MM-YYYY");
				}
				else { //If all fields entered are valid.
					accountA.addBook(tfISBN, tfTitle, tfLang, tfGenre, tfRelDate, tfPrice, tfAddInfo1, tfAddInfo2, cbType, bookList); //Using addBook() method from Admin class passing the text field parameters entered by the user.
					FileWriter outputFile;
					try {
						outputFile = new FileWriter("Stock.txt", false); //Stock is updated, rewriting the file, not appending.
						BufferedWriter bw = new BufferedWriter(outputFile);
						for(Book b : bookList) {
							//The current bookList is used which has the books the user bought taken out of it, so it is accurate to showing the remaining stock after the book has been added.
							bw.write(b.getISBN() + ", " + b.getType() + ", " + b.getTitle() + ", " + b.getLang() + ", " + b.getGenre() + ", " + b.getRelDate() + ", " + String.format("%.2f", Double.parseDouble(b.getPrice())) + ", " + b.getQuantity() + ", " + b.getAddInfo1() + ", " + b.getAddInfo2() + "\n");
						}
						bw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						bookList = Book.getStock(); //Update bookList from stock file after the new book has been added or a book's stock quantity has been updated.
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
	
					while (dtmBook.getRowCount() > 0) { //Removes each row currently in the table, so same books are not displayed repeatedly.
						dtmBook.removeRow(0);
					}
					
					for (Book b : bookList) {
						//Display all the books from the stock after the new book as been added or a book's stock quantity has been updated. 
						Object[] rowdata = new Object[] {b.getISBN(), b.getType(), b.getTitle(), b.getLang(), b.getGenre(), b.getRelDate(), b.getPrice(), b.getQuantity(), b.getAddInfo1(), b.getAddInfo2()};
						dtmBook.addRow(rowdata);
					}
				}
			}
		});
		btnAddBook.setBounds(520, 556, 89, 23);
		plAddBook.add(btnAddBook);
		
		JButton btnLogoutA1 = new JButton("Logout");
		btnLogoutA1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pressing the logout button takes the user back to login window so admin window is set to false and login window is set to true.
				login.setVisible(true);
				admin.setVisible(false);
				
				//Empty all text fields so inputted string is not carried over.
				tfISBN.setText(null);
				tfTitle.setText(null);
				tfLang.setText(null);
				tfGenre.setText(null);
				tfRelDate.setText(null);
				tfPrice.setText(null);
				tfAddInfo1.setText(null);
				tfAddInfo2.setText(null);
			}
		});
		btnLogoutA1.setBounds(1160, 556, 89, 23);
		plAddBook.add(btnLogoutA1);
		
		customer = new JFrame();
		customer.setBounds(100, 100, 1280, 720);
		customer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		customer.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPaneC = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneC.setBounds(0, 0, 1264, 681);
		customer.getContentPane().add(tabbedPaneC);
		
		JPanel plViewBks = new JPanel();
		tabbedPaneC.addTab("View Books", null, plViewBks, null);
		plViewBks.setLayout(null);
		
		JScrollPane sPCustomer = new JScrollPane();
		sPCustomer.setBounds(10, 64, 1239, 544);
		plViewBks.add(sPCustomer);
		
		tblBook = new JTable();
		sPCustomer.setViewportView(tblBook);
		
		tblBook.setModel(dtmBook);
		
		JPanel plBasket = new JPanel();
		tabbedPaneC.addTab("Basket", null, plBasket, null);
		plBasket.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 1087, 631);
		plBasket.add(scrollPane);
		
		tblBasket = new JTable();
		scrollPane.setViewportView(tblBasket);
		
		DefaultTableModel dtmBasket = new DefaultTableModel() {
			//Make table cells noneditable so user does not change table values and cause errors.
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		dtmBasket.setColumnIdentifiers(new Object[] {"ISBN", "Type", "Title", "Language", "Genre", "Release Date", "Price", "Quantity in Stock", "Additional Information 1", "Additional Information 2"});
		tblBasket.setModel(dtmBasket);
		
		//Initialising basket object of type Basket with empty list so that it can be used for when the user wants to interact with their basket, which would require the Basket class methods to be used.
		basket = new Basket(emptyList); 
		
		JButton btnAddToBask = new JButton("Add to Basket");
		btnAddToBask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Book> list; //Local list of book objects used in the function to manage the books depending on if the user has filtered the table or not.
				if (filtered == true) {
					list = searchList; //List is set to searchList as the user is adding a book from the filtered ones displayed in the table.
				}
				else {
					list = bookList; //List is set to bookList as if the user is not filtering then the user is adding a book from the books in bookList.
				}
				
				if (basketList != null) { //Checking if the basket has books so that an index error is not caused.
					for (Book b : basketList) {
						//Removing each book 'b' in the basket from the table so that the updated basket can be displayed without duplicates.
						dtmBasket.removeRow(0);
					}
				}
				
				int i = tblBook.getSelectedRow(); //Variable to hold the index of the row that the user has selected.
				
				if (dtmBook.getValueAt(i, 1).toString().equals("paperback")) { //checks if the book selected is of paperback type.
					if (basketList == null) { //Checking if the basket has books so index error is not caused.
						//Makes a new Book object of subclass paperback with the details of the book the user has selected. 
						Book book = new Paperback(dtmBook.getValueAt(i, 0).toString(), dtmBook.getValueAt(i, 1).toString(), dtmBook.getValueAt(i, 2).toString(), dtmBook.getValueAt(i, 3).toString(), dtmBook.getValueAt(i, 4).toString(), dtmBook.getValueAt(i, 5).toString(), dtmBook.getValueAt(i, 6).toString(), "1", dtmBook.getValueAt(i, 8).toString(), dtmBook.getValueAt(i, 9).toString());
						basket = accountC.addToBasket(book, basket); //Using addToBasket method in Customer class to add the book object to the user's basket.
					}
					else {
						Book book = null; //Initialising new book object.
						int j = 0; //Local variable used to count the unique books in basketList.
						for (Book b : basketList) {
							//Loops through basketList to check is there is already a book in the basket which has an identical ISBN to the book the user has selected.
							if (b.getISBN().equals(dtmBook.getValueAt(i, 0))) {
								//If there is a matching ISBN, the quantity of the book in the basket is increased by 1, showing the quantity of that book that the user will buy if they choose to.
								b.setQuantity(String.valueOf(Integer.parseInt(b.getQuantity()) + 1));
							}
							else if (b.getISBN() != (dtmBook.getValueAt(i, 0))) {
								//If there is no matching ISBN, 'j' is incremented by 1.
								j += 1;
							}
						}
						
						if (j == basketList.size()) { //Checking if the value of 'j' is equal to the size of basketList, indicating the book the user has selected is a new one.
							//As the user wants to add a book to the basket which is not already in it, a new paperback book object is made with the details and assigned to the book object initialised before.
							book = new Paperback(dtmBook.getValueAt(i, 0).toString(), dtmBook.getValueAt(i, 1).toString(), dtmBook.getValueAt(i, 2).toString(), dtmBook.getValueAt(i, 3).toString(), dtmBook.getValueAt(i, 4).toString(), dtmBook.getValueAt(i, 5).toString(), dtmBook.getValueAt(i, 6).toString(), "1", dtmBook.getValueAt(i, 8).toString(), dtmBook.getValueAt(i, 9).toString());
						}
						
						if (book != null) { //Makes sure the book object is valid one to be added to the basket.
							basket = accountC.addToBasket(book, basket); //Using addToBasket method in Customer class to add the book object to the user's basket.
						}
					}
				}
				else if (dtmBook.getValueAt(i, 1).toString().equals("ebook")) { //Checks if the book selected is of ebook type.
					if (basketList == null) { //Checking if the basket has books so index error is not caused.
						//Makes a new Book object of subclass ebook with the details of the book the user has selected.
						Book book = new eBook(dtmBook.getValueAt(i, 0).toString(), dtmBook.getValueAt(i, 1).toString(), dtmBook.getValueAt(i, 2).toString(), dtmBook.getValueAt(i, 3).toString(), dtmBook.getValueAt(i, 4).toString(), dtmBook.getValueAt(i, 5).toString(), dtmBook.getValueAt(i, 6).toString(), "1", dtmBook.getValueAt(i, 8).toString(), dtmBook.getValueAt(i, 9).toString());
						basket = accountC.addToBasket(book, basket); //Using addToBasket method in Customer class to add the book object to the user's basket.
					}
					else {
						Book book = null; //Initialising new book object.
						int j = 0; //Local variable used to count the unique books in basketList.
						for (Book b : basketList) { 
							//Loops through basketList to check is there is already a book in the basket which has an identical ISBN to the book the user has selected.
							if (b.getISBN().equals(dtmBook.getValueAt(i, 0))) {
								//If there is a matching ISBN, the quantity of the book in the basket is increased by 1, showing the quantity of that book that the user will buy if they choose to.
								b.setQuantity(String.valueOf(Integer.parseInt(b.getQuantity()) + 1));
							}
							else if (b.getISBN() != (dtmBook.getValueAt(i, 0))) {
								//If there is no matching ISBN, 'j' is incremented by 1.
								j += 1;
							}
						}
						
						if (j == basketList.size()) { //Checking if the value of 'j' is equal to the size of basketList, indicating the book the user has selected is a new one.
							//As the user wants to add a book to the basket which is not already in it, a new ebook book object is made with the details and assigned to the book object initialised before.
							book = new eBook(dtmBook.getValueAt(i, 0).toString(), dtmBook.getValueAt(i, 1).toString(), dtmBook.getValueAt(i, 2).toString(), dtmBook.getValueAt(i, 3).toString(), dtmBook.getValueAt(i, 4).toString(), dtmBook.getValueAt(i, 5).toString(), dtmBook.getValueAt(i, 6).toString(), "1", dtmBook.getValueAt(i, 8).toString(), dtmBook.getValueAt(i, 9).toString());
						}
						
						if (book != null) { //Makes sure the book object is valid one to be added to the basket.
							basket = accountC.addToBasket(book, basket); //Using addToBasket method in Customer class to add the book object to the user's basket.
						}
					}
				}
				else if (dtmBook.getValueAt(i, 1).toString().equals("audiobook")) { //Checks if the book selected is of audiobook type.
					if (basketList == null) { //Checking if the basket has books so index error is not caused.
						//Makes a new Book object of subclass audiobook with the details of the book the user has selected.
						Book book = new Audiobook(dtmBook.getValueAt(i, 0).toString(), dtmBook.getValueAt(i, 1).toString(), dtmBook.getValueAt(i, 2).toString(), dtmBook.getValueAt(i, 3).toString(), dtmBook.getValueAt(i, 4).toString(), dtmBook.getValueAt(i, 5).toString(), dtmBook.getValueAt(i, 6).toString(), "1", dtmBook.getValueAt(i, 8).toString(), dtmBook.getValueAt(i, 9).toString());
						basket = accountC.addToBasket(book, basket); //Using addToBasket method in Customer class to add the book object to the user's basket.
					}
					else {
						Book book = null; //Initialising new book object.
						int j = 0; //Local variable used to count the unique books in basketList.
						for (Book b : basketList) {
							//Loops through basketList to check is there is already a book in the basket which has an identical ISBN to the book the user has selected.
							if (b.getISBN().equals(dtmBook.getValueAt(i, 0))) {
								//If there is a matching ISBN, the quantity of the book in the basket is increased by 1, showing the quantity of that book that the user will buy if they choose to.
								b.setQuantity(String.valueOf(Integer.parseInt(b.getQuantity()) + 1));
							}
							else if (b.getISBN() != (dtmBook.getValueAt(i, 0))) {
								//If there is no matching ISBN, 'j' is incremented by 1.
								j += 1;
							}
						}
						if (j == basketList.size()) { //Checking if the value of 'j' is equal to the size of basketList, indicating the book the user has selected is a new one.
							//As the user wants to add a book to the basket which is not already in it, a new audiobook book object is made with the details and assigned to the book object initialised before.
							book = new Audiobook(dtmBook.getValueAt(i, 0).toString(), dtmBook.getValueAt(i, 1).toString(), dtmBook.getValueAt(i, 2).toString(), dtmBook.getValueAt(i, 3).toString(), dtmBook.getValueAt(i, 4).toString(), dtmBook.getValueAt(i, 5).toString(), dtmBook.getValueAt(i, 6).toString(), "1", dtmBook.getValueAt(i, 8).toString(), dtmBook.getValueAt(i, 9).toString());
						}
						if (book != null) { //Makes sure the book object is valid one to be added to the basket
							basket = accountC.addToBasket(book, basket); //Using addToBasket method in Customer class to add the book object to the user's basket.
						}
					}
				}
				
				basketList = basket.getContents(); //BasketList is updated with the contents of the basket object using getter method which returns an ArrayList of book objects.
				
				if (basketList != null) { //Checking if basketList has items in it to be able to loop through so an index error is not caused.
					//Each book in the basket is displayed again after a new book has been added or the quantity has been updated, by looping through basketList. 
					for (Book b : basketList) {
						Object[] rowdata = new Object[] {b.getISBN(), b.getType(), b.getTitle(), b.getLang(), b.getGenre(), b.getRelDate(), b.getPrice(), b.getQuantity(), b.getAddInfo1(), b.getAddInfo2()};
						dtmBasket.addRow(rowdata);
					}
				}
				
				int remBook = 0; //Initiliased variable which will store index of a book which needs to be removed from 'View Books' if the user has added the last of that book to their basket.
				
				int remBookFiltered = 0; //Works same as remBook but in the case that the user has filtered 'View Books'.
				
				boolean remove = false; //Boolean to store whether or not a book needs to be removed. 
				
				Iterator<Book> itr = list.iterator(); //Iterator to loop through 'list' of book objects being either the contents of bookList or searchList.
				while (itr.hasNext()) {
					Book b = itr.next(); //Assigning book object 'b' to be the next book in 'list'.
					if (dtmBook.getValueAt(i, 0).equals(b.getISBN())) { //Checking if the ISBN of the selected book matches the ISBN of the book in 'list'.
						if (Integer.parseInt(b.getQuantity()) == 1) { //Checking the quantity of the book remaining in stock.
							//If the book's quantity is 1 then it needs to be removed from 'View Books' so the user cannot keep adding it to their basket.
							remove = true; //Set to true as there is book which needs to be removed.
							remBook = list.indexOf(b); //remBook is set to the index of the book which the user has selected, which needs to be removed.
						}
						else {
							//If the book's quantity is not 1, the book's quantity is decreased by 1. 
							b.setQuantity(String.valueOf(Integer.parseInt(b.getQuantity()) - 1)); //the new quantity value is set for the book using setter method.
						}
					}
				}
				
				if (filtered == true) { //Checking if the user is currently filtering the books in 'View Books'.
					/* This same method for finding the book which needs to removed is used but for bookList as if the user has filtered 'View Books', 'list' is set to searchList
					 * so the same book needs to be removed from bookList so that it is also updated, but it has different indexes so remBookFiltered is used to update bookList.
					 */
					Iterator<Book> iter = bookList.iterator(); //Iterator to loop through bookList of book objects.
					while (iter.hasNext()) {
						Book bk = iter.next(); //Assigning book object 'bk' to be the next book in bookList.
						if (dtmBook.getValueAt(i, 0).equals(bk.getISBN())) { //Checking if the ISBN of the selected book matches the ISBN of the book in bookList.
							if (Integer.parseInt(bk.getQuantity()) == 1) { //Checking the quantity of remaining stock of the selected book.
								remove = true; //Set to true as there is book which needs to be removed
								remBookFiltered = bookList.indexOf(bk); //remBook is set to the index of the book which the user has selected, which needs to be removed.
							}
						}
					}
				}
				
				if (remove == true ) { //Checking if a book needs to be removed.
					list.remove(remBook); //Removes book from 'list' which is either searchList or bookList using the index of the valure remBook.
					if (filtered == true) { //Checking if the user has filtered 'View Books'. 
						//As 'View Books' is filtered, 'list' holds the content of searchList so bookList needs to be updated separately.
						bookList.remove(remBookFiltered); //Removes book from bookList using the index of value remBookFiltered.
					}
					
					for (int l = 0; l < list.size() + 1; l++) {
						//All the books in the table are cleared to be updated.
						dtmBook.removeRow(0);
					}
				}
				else {
					for (int l = 0; l < list.size(); l++) {
						//All the books in the table are cleared to be updated.
						dtmBook.removeRow(0);
					}
				}
				
				for (Book bk : list) {
					//Updates the table in 'View Books' with each book in 'list' after the quantity has been decreased or a book has been removed.
					Object[] rowdata = new Object[] {bk.getISBN(), bk.getType(), bk.getTitle(), bk.getLang(), bk.getGenre(), bk.getRelDate(), bk.getPrice(), bk.getQuantity(), bk.getAddInfo1(), bk.getAddInfo2()};
					dtmBook.addRow(rowdata);
				}
			}
		});
		btnAddToBask.setBounds(10, 619, 124, 23);
		plViewBks.add(btnAddToBask);
		
		//Initialising searchList to store Book objects when the user filters or searches genre.
		searchList = new ArrayList<Book>();
		
		JLabel lblFilter = new JLabel("Filter By:");
		lblFilter.setBounds(10, 39, 68, 14);
		plViewBks.add(lblFilter);
		
		tfEntGenre = new JTextField();
		tfEntGenre.setBounds(162, 36, 124, 20);
		plViewBks.add(tfEntGenre);
		tfEntGenre.setColumns(10);
		
		JLabel lblGenre1 = new JLabel("Enter Genre:");
		lblGenre1.setBounds(88, 39, 79, 14);
		plViewBks.add(lblGenre1);
		
		JButton btnAudiobooks = new JButton("Audiobooks with Length Greater than 5");
		btnAudiobooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtered = true; //Public boolean set to true as user is filtering 'View Books'.
				while (dtmBook.getRowCount() > 0) {
					//Remove each row from the table using getRowCount.
					dtmBook.removeRow(0);
				}
				
				for (Book b : bookList) {
					//Loop through each book in bookList and check for matching criteria.
					if ((b.getType().equals("audiobook")) && Double.parseDouble(b.getAddInfo1()) > 5) {
						searchList = accountC.searchBook(searchList, b); //Using searchBook method in Customer class to return list of books matching the criteria.
					}
				}
				for (Book bk : searchList) {
					//Add each book in searchList to the table.
					Object[] rowdata = new Object[] {bk.getISBN(), bk.getType(), bk.getTitle(), bk.getLang(), bk.getGenre(), bk.getRelDate(), bk.getPrice(), bk.getQuantity(), bk.getAddInfo1(), bk.getAddInfo2()};
					dtmBook.addRow(rowdata);
				}
			}
		});
		btnAudiobooks.setBounds(454, 35, 252, 23);
		plViewBks.add(btnAudiobooks);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pressing reset returns 'View Books' to displaying all books in stock.
				filtered = false; //Set to false as 'View Books' is returned to an unfiltered state showing all the books in stock.
				while (dtmBook.getRowCount() > 0) {
					//Remove each row from the table using getRowCount.
					dtmBook.removeRow(0);
				}
				for (Book bk : bookList) {
					//Loop through each book in bookList to display all the books in the table again.
					Object[] rowdata = new Object[] {bk.getISBN(), bk.getType(), bk.getTitle(), bk.getLang(), bk.getGenre(), bk.getRelDate(), bk.getPrice(), bk.getQuantity(), bk.getAddInfo1(), bk.getAddInfo2()};
					dtmBook.addRow(rowdata);
				}
				searchList.clear(); //Empty searchList so user can filter books again without overlapping with previous searches/filters.
			}
		});
		btnReset.setBounds(1160, 35, 89, 23);
		plViewBks.add(btnReset);
		
		JLabel lblReset = new JLabel("Reset Filters:");
		lblReset.setBounds(1074, 39, 76, 14);
		plViewBks.add(lblReset);
		
		JButton btnEtrGenre = new JButton("Enter");
		btnEtrGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean filter = false; //Local boolean set to true if the user is filtering 'View Books' by genre.
				filtered = true; //Public boolean set to true as user is filtering 'View Books'.
				for (Book b : bookList) {
					//Loop through each book in bookList for the genre entered by the user.
					if (tfEntGenre.getText().equalsIgnoreCase(b.getGenre())) {
						filter = true; //Set to true so the table can be updated.
						searchList = accountC.searchBook(searchList, b); //Using searchBook method in Customer class to return list of books matching the user's filters.
					}
				}
				if (filter == true) { 
					//As filter is set to true, each row in 'View Books' table is removed and each book from searchList is displayed.
					while (dtmBook.getRowCount() > 0) {
						//Remove each row from the table using getRowCount.
						dtmBook.removeRow(0);
					}
					for (Book bk : searchList) {
						Object[] rowdata = new Object[] {bk.getISBN(), bk.getType(), bk.getTitle(), bk.getLang(), bk.getGenre(), bk.getRelDate(), bk.getPrice(), bk.getQuantity(), bk.getAddInfo1(), bk.getAddInfo2()};
						dtmBook.addRow(rowdata);
					}
				}
			}
		});
		btnEtrGenre.setBounds(296, 35, 89, 23);
		plViewBks.add(btnEtrGenre);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pressing cancel on the basket tab clears the basket and returns the books that were in the basket to the stock in the 'View Books' tab.
				if (basketList != null) {
					while (dtmBasket.getRowCount() > 0) {
						//Removing all the books from the basket table, by looping through each book in the basket list.
						dtmBasket.removeRow(0);
					}			
					try {
						basket = accountC.cancel(basket);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} //Cancel method from Customer class used and empty basket object returned.
					basketList.clear(); //basketList emptied so user can buy new set of books
				}
				searchList.clear(); //searchList cleared in case the user had filtered the 'View Books' table or searched a genre.
				filtered = false; //Set to false as 'View Books' is returned to an unfiltered state showing all the books in stock.
				while (dtmBook.getRowCount() > 0) {
					//Remove each row from the table using getRowCount.
					dtmBook.removeRow(0);
				}
				try {
					bookList = Book.getStock(); //Restoring bookList with the book stock saved it Stock.txt using getStock() method from Book class.
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (Book bk : bookList) {
					//Enter each book from bookList which has been restored with the current stock.
					Object[] rowdata = new Object[] {bk.getISBN(), bk.getType(), bk.getTitle(), bk.getLang(), bk.getGenre(), bk.getRelDate(), bk.getPrice(), bk.getQuantity(), bk.getAddInfo1(), bk.getAddInfo2()};
					dtmBook.addRow(rowdata);
				}
			}
		});
		btnCancel.setBounds(1107, 11, 142, 23);
		plBasket.add(btnCancel);
		
		JPanel plPay = new JPanel();
		tabbedPaneC.addTab("Pay", null, plPay, null);
		plPay.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email Address:");
		lblEmail.setBounds(191, 103, 93, 14);
		plPay.add(lblEmail);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(290, 100, 562, 20);
		plPay.add(tfEmail);
		tfEmail.setColumns(10);
		
		JButton btnPaypal = new JButton("Pay by PayPal");
		btnPaypal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfEmail.getText().isBlank() || !tfEmail.getText().contains("@")) {
					//Checks if the field is empty or if the inputted string contains the '@' character representing an email, if either is not true, a message is displayed.
					JOptionPane.showMessageDialog(null, "Enter a valid PayPal Email Address");
				}
				else if (basketList == null || basketList.isEmpty()) { //Checks if the basket has any books to buy, if not a message is displayed.
					JOptionPane.showMessageDialog(null, "Your basket is empty");
				}
				else { //If books are available to buy and valid credentials are inputted, the total cost is added up and displayed with the Stock being updated and the purchase is logged.
					Double total;
					try {
						total = accountC.pay(basket, dtmBasket, "PayPal");//The pay method in the Customer class is called to return the total cost of the books, "PayPal" is passed in as the user is paying through the Paypal option.
						basketList.clear();//The basket is emptied so the user can purchase books again without the total being added up across seperate purchases.
						JOptionPane.showMessageDialog(null, String.format("%.2f", total) + " paid using PayPal"); //Total is formatted to 2 decimal places to be displayed to the user.
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} 
					FileWriter outputFile;
					try {
						outputFile = new FileWriter("Stock.txt", false); //Stock is updated, rewriting the file, not appending.
						BufferedWriter bw = new BufferedWriter(outputFile);
						for(Book b : bookList) {
							//The current bookList is used which has the books the user bought taken out of it, so it is accurate to showing the remaining stock after the purchase.
							bw.write(b.getISBN() + "," + " " + b.getType() + "," + " " + b.getTitle() + "," + " " + b.getLang() + "," + " " + b.getGenre() + "," + " " + b.getRelDate() + "," + " " + b.getPrice() + "," + " " + b.getQuantity() + "," + " " + b.getAddInfo1() + "," + " " + b.getAddInfo2() + "\n");
						}
						bw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnPaypal.setBounds(904, 99, 141, 23);
		plPay.add(btnPaypal);
		
		JLabel lblPaypal = new JLabel("PayPal:");
		lblPaypal.setBounds(88, 103, 46, 14);
		plPay.add(lblPaypal);
		
		JLabel lblCardNo = new JLabel("Card Number:");
		lblCardNo.setBounds(201, 312, 106, 14);
		plPay.add(lblCardNo);
		
		JLabel lblCard = new JLabel("Credit Card:");
		lblCard.setBounds(88, 312, 93, 14);
		plPay.add(lblCard);
		
		JLabel lblSecurityNo = new JLabel("Security Number:");
		lblSecurityNo.setBounds(179, 360, 129, 14);
		plPay.add(lblSecurityNo);
		
		tfCardNo = new JTextField();
		tfCardNo.setBounds(290, 309, 562, 20);
		plPay.add(tfCardNo);
		tfCardNo.setColumns(10);
		
		tfSecurityNo = new JTextField();
		tfSecurityNo.setBounds(290, 357, 86, 20);
		plPay.add(tfSecurityNo);
		tfSecurityNo.setColumns(10);
		
		JButton btnCredit = new JButton("Pay by Credit Card");
		btnCredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfCardNo.getText().isBlank() || tfCardNo.getText().length() != 6 || !tfCardNo.getText().chars().allMatch(Character::isDigit)) {
					//Checks if the inputted string for card number consists of digits, is 6 digits long and if it is not empty, if at least one of these are not true then a message is displayed.
					JOptionPane.showMessageDialog(null, "Enter a valid Credit Card Number");
				}
				else if (tfSecurityNo.getText().isBlank() || tfSecurityNo.getText().length() != 3 || !tfSecurityNo.getText().chars().allMatch(Character::isDigit)) {
					//Checks if the inputted string for security number consists of digits, is 3 digits long and if it is not empty, if at least one of these are not true then a message is displayed.
					JOptionPane.showMessageDialog(null, "Enter a valid Security Number");
				}
				else if (basketList == null || basketList.isEmpty()) { //Checks if the basket has any books to buy, if not a message is displayed.
					JOptionPane.showMessageDialog(null, "Your basket is empty");
				}
				else { //If books are available to buy and valid credentials are inputted, the total cost is added up and displayed with the Stock being updated and the purchase is logged.
					Double total;
					try {
						total = accountC.pay(basket, dtmBasket, "Credit Card"); //The pay method in the Customer class is called to return the total cost of the books, "Credit Card" is passed in as the user is paying through the Credit Card option.
						basketList.clear(); //Basket is emptied so the user can purchase books again without the total being added up across seperate purchases.
						JOptionPane.showMessageDialog(null, String.format("%.2f", total) + " paid using Credit Card"); //Total is formatted to 2 decimal places to be displayed to the user.
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					FileWriter outputFile;
					try {
						outputFile = new FileWriter("Stock.txt", false); //Stock is updated, rewriting the file, not appending.
						BufferedWriter bw = new BufferedWriter(outputFile);
						for(Book b : bookList) {
							//The current bookList is used which has the books the user bought taken out of it, so it is accurate to showing the remaining stock after the purchase.
							bw.write(b.getISBN() + "," + " " + b.getType() + "," + " " + b.getTitle() + "," + " " + b.getLang() + "," + " " + b.getGenre() + "," + " " + b.getRelDate() + "," + " " + b.getPrice() + "," + " " + b.getQuantity() + "," + " " + b.getAddInfo1() + "," + " " + b.getAddInfo2() + "\n");
						}
						bw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnCredit.setBounds(904, 308, 141, 23);
		plPay.add(btnCredit);
		
		JButton btnLogoutC = new JButton("Logout");
		btnLogoutC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pressing logout cancels the customer's basket if there are items and allows user to be taken back to login window so customer window is set to false and login window is set to true.
				if (basketList != null) {
					while (dtmBasket.getRowCount() > 0) {
						//Removing all the books from the basket table, by looping through each book in the basket list.
						dtmBasket.removeRow(0);
					}			
					try {
						basket = accountC.cancel(basket);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} //Cancel method from Customer class used and empty basket object returned.
					basketList.clear(); //basketList emptied so user can buy new set of books
				}
				searchList.clear(); //searchList cleared in case the user had filtered the 'View Books' table or searched a genre.
				filtered = false; //Set to false as 'View Books' is returned to an unfiltered state showing all the books in stock.
				while (dtmBook.getRowCount() > 0) {
					//Remove each row from the table using getRowCount.
					dtmBook.removeRow(0);
				}
				try {
					bookList = Book.getStock(); //Restoring bookList with the book stock saved it Stock.txt using getStock() method from Book class.
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (Book bk : bookList) {
					//Enter each book from bookList which has been restored with the current stock.
					Object[] rowdata = new Object[] {bk.getISBN(), bk.getType(), bk.getTitle(), bk.getLang(), bk.getGenre(), bk.getRelDate(), bk.getPrice(), bk.getQuantity(), bk.getAddInfo1(), bk.getAddInfo2()};
					dtmBook.addRow(rowdata);
				}
				
				login.setVisible(true);
				customer.setVisible(false);
				
				//Empty all text fields so inputted string is not carried over.
				tfEmail.setText(null);
				tfCardNo.setText(null);
				tfSecurityNo.setText(null);
				tfEntGenre.setText(null);
			}
		});
		btnLogoutC.setBounds(1160, 619, 89, 23);
		plViewBks.add(btnLogoutC);
		
		JButton btnLogoutC1 = new JButton("Logout");
		btnLogoutC1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pressing logout cancels the customer's basket and allows user to be taken back to login window so customer window is set to false and login window is set to true.
				if (basketList != null) {
					while (dtmBasket.getRowCount() > 0) {
						//Removing all the books from the basket table, by looping through each book in the basket list.
						dtmBasket.removeRow(0);
					}			
					try {
						basket = accountC.cancel(basket);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} //Cancel method from Customer class used and empty basket object returned.
					basketList.clear(); //basketList emptied so user can buy new set of books
				}
				searchList.clear(); //searchList cleared in case the user had filtered the 'View Books' table or searched a genre.
				filtered = false; //Set to false as 'View Books' is returned to an unfiltered state showing all the books in stock.
				while (dtmBook.getRowCount() > 0) {
					//Remove each row from the table using getRowCount.
					dtmBook.removeRow(0);
				}
				try {
					bookList = Book.getStock(); //Restoring bookList with the book stock saved it Stock.txt using getStock() method from Book class.
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (Book bk : bookList) {
					//Enter each book from bookList which has been restored with the current stock.
					Object[] rowdata = new Object[] {bk.getISBN(), bk.getType(), bk.getTitle(), bk.getLang(), bk.getGenre(), bk.getRelDate(), bk.getPrice(), bk.getQuantity(), bk.getAddInfo1(), bk.getAddInfo2()};
					dtmBook.addRow(rowdata);
				}
				
				login.setVisible(true);
				customer.setVisible(false);
				
				//Empty all text fields so inputted string is not carried over.
				tfEmail.setText(null);
				tfCardNo.setText(null);
				tfSecurityNo.setText(null);
				tfEntGenre.setText(null);
			}
		});
		btnLogoutC1.setBounds(1160, 619, 89, 23);
		plBasket.add(btnLogoutC1);
		
		JButton btnLogoutC2 = new JButton("Logout");
		btnLogoutC2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pressing logout cancels the customer's basket and allows user to be taken back to login window so customer window is set to false and login window is set to true.
				if (basketList != null) {
					while (dtmBasket.getRowCount() > 0) {
						//Removing all the books from the basket table, by looping through each book in the basket list.
						dtmBasket.removeRow(0);
					}			
					try {
						basket = accountC.cancel(basket);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} //Cancel method from Customer class used and empty basket object returned.
					basketList.clear(); //basketList emptied so user can buy new set of books
				}
				searchList.clear(); //searchList cleared in case the user had filtered the 'View Books' table or searched a genre.
				filtered = false; //Set to false as 'View Books' is returned to an unfiltered state showing all the books in stock.
				while (dtmBook.getRowCount() > 0) {
					//Remove each row from the table using getRowCount.
					dtmBook.removeRow(0);
				}
				try {
					bookList = Book.getStock(); //Restoring bookList with the book stock saved it Stock.txt using getStock() method from Book class.
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (Book bk : bookList) {
					//Enter each book from bookList which has been restored with the current stock.
					Object[] rowdata = new Object[] {bk.getISBN(), bk.getType(), bk.getTitle(), bk.getLang(), bk.getGenre(), bk.getRelDate(), bk.getPrice(), bk.getQuantity(), bk.getAddInfo1(), bk.getAddInfo2()};
					dtmBook.addRow(rowdata);
				}
				
				login.setVisible(true);
				customer.setVisible(false);
				
				//Empty all text fields so inputted string is not carried over.
				tfEmail.setText(null);
				tfCardNo.setText(null);
				tfSecurityNo.setText(null);
				tfEntGenre.setText(null);
			}
		});
		btnLogoutC2.setBounds(1160, 619, 89, 23);
		plPay.add(btnLogoutC2);
	}
}
