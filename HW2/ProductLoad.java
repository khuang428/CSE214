/*
 * Karen Huang
 * 111644515
 * 
 * The ProductLoad class is used to represent a load in a train car
 */
public class ProductLoad {
	private String name;
	private double weight; //in tons
	private double value; //in dollars
	private boolean dangerous;
	
	//default constructor
	public ProductLoad() {
		
	}
	
	/* constructor with all variables
	 * 
	 * @param n
	 * 	the product's name
	 * @param w
	 * 	the product's weight in tons
	 * @param v
	 * 	the product's value in dollars
	 * @param d
	 * 	whether the product is dangerous or not
	 */
	public ProductLoad(String n, double w, double v, boolean d) {
		name = n;
		weight = w;
		value = v;
		dangerous = d;
	}
	
	/*
	 * @return
	 * 	returns the product's name
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * @return
	 * 	returns the product's weight
	 */
	public double getWeight() {
		return weight;
	}
	
	/*
	 * @return
	 * 	returns the product's value
	 */
	public double getValue() {
		return value;
	}
	
	/*
	 * @return
	 * 	returns whether the product is dangerous
	 */
	public boolean isDangerous() {
		return dangerous;
	}
	
	/*
	 * @param n
	 * 	the new product name
	 */
	public void setName(String n) {
		name = n;
	}
	
	/*
	 * @param w
	 * 	the new product weight
	 * Precondition: w must be greater than 0
	 * @exception IllegalArgumentException
	 * 	indicates that w is 0 or less
	 */
	public void setWeight(double w) {
		if(w <= 0) {
			throw new IllegalArgumentException("Weight must be above 0.");
		}
		weight = w;
	}
	
	/*
	 * @param v
	 * 	the new product value
	 * Precondition: v must not be negative
	 * @exception IllegalArgumentException
	 * 	indicates that v is negative
	 */
	public void setValue(double v) {
		if(v < 0) {
			throw new IllegalArgumentException("Value cannot be negative.");
		}
		value = v;
	}
	
	/*
	 * @param d
	 * 	whether the product is dangerous
	 */
	public void setDanger(boolean d) {
		dangerous = d;
	}
	
	/*
	 * @return
	 * 	returns a string representation of the product load
	 */
	public String toString() {
		String danger;
		if(dangerous) {
			danger = "YES";
		}else {
			danger = "NO";
		}
		return String.format("%-15s%04.1f     %06.2f         %s", name, weight, value,danger);
	}
}
