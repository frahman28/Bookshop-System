import java.util.Comparator;

public class PriceCompare implements Comparator<Book> {
	public int compare(Book b1, Book b2) {
		if (Double.parseDouble(b1.getPrice()) < Double.parseDouble(b2.getPrice())) return -1;
		if (Double.parseDouble(b1.getPrice()) > Double.parseDouble(b2.getPrice())) return 1;
		else return 0;
	}
}
