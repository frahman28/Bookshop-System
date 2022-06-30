import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class User {
	private String userid;
	private String username;
	private String surname;
	private String housenumber;
	private String postcode;
	private String city;
	private String role;
	public User(String userid, String username, String surname, String housenumber, String postcode, String city, String role) {
		this.userid = userid;
		this.username = username;
		this.surname = surname;
		this.housenumber = housenumber;
		this.postcode = postcode;
		this.city = city;
		this.role = role;
	}
	public String getUserID() {
		return this.userid;
	}
	public String getUser() {
		return this.username;
	}
	public String getSur() {
		return this.surname;
	}
	public String getHouseNo() {
		return this.housenumber;
	}
	public String getPostCode() {
		return this.postcode;
	}
	public String getCity() {
		return this.city;
	}
	public String getRole() {
		return this.role;
	}
	
	/* This method reads the account details from UserAccounts.txt and uses them to
	 * create User objects depending on the type, which are added to a list.
	 * @return list of User objects containing information from Accounts.txt.
	 * @throws FileNotFoundException
	 */
	public static List<User> getAccs() throws FileNotFoundException {
		List<User> listUser = new ArrayList<User>(); //Initialising listUser as an ArrayList of User objects.
		File inputFile = new File("UserAccounts.txt");
		Scanner fileScanner = new Scanner(inputFile);
		Admin ad = null; //Initialising Admin object to be assigned to in the loop.
		Customer ct = null; //Initialising Customer object to be assigned to in the loop.
		while (fileScanner.hasNextLine()) {
			String[] details = fileScanner.nextLine().split(",");
			if (details[6].equals(" admin")) { 
				//Checks if 6th index contains string which is referring to the type as admin, where an Admin User object with those details is created and assigned to ad which is added to listUser. 
				ad = new Admin(details[0].trim(), details[1].trim(), details[2].trim(), details[3].trim(), details[4].trim(), details[5].trim(), details[6].trim());
				listUser.add(ad);
			}
			else {
				//In all other cases the User details would be for type Customer, so a new Customer object is created with those details and assigned to ct which is added to listUser.
				ct = new Customer(details[0].trim(), details[1].trim(), details[2].trim(), details[3].trim(), details[4].trim(), details[5].trim(), details[6].trim());
				listUser.add(ct);
			}
		}
		fileScanner.close();
		return listUser;
	}
	
}