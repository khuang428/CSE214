/*
 * Karen Huang
 * 111644515
 * 
 * The TrainCar class is used to represent one car in a train
 */
public class TrainCar {
	final double LENGTH; //in meters
	final double WEIGHT; //in tons
	private ProductLoad load; //can be null if the train car is empty
	
	//default constructor
	public TrainCar() {
		LENGTH = 0;
		WEIGHT = 0;
	}
	
	/* constructor for setting size of train car
	 * 
	 * @param l
	 * 	the length of the train car
	 * @param w
	 * 	the weight of the train car
	 */
	public TrainCar(double l, double w) {
		LENGTH = l;
		WEIGHT = w;
	}
	
	/*
	 * @return
	 * 	returns the length of the train car
	 */
	public double getLength() {
		return LENGTH;
	}
	
	/*
	 * @return
	 * 	returns the weight of the train car
	 */
	public double getWeight() {
		return WEIGHT;
	}
	
	/*
	 * @return
	 * 	returns the load in the train car
	 */
	public ProductLoad getLoad() {
		return load;
	}
	
	/*
	 * @param pl
	 * 	the new product load
	 */
	public void setLoad(ProductLoad pl) {
		load = pl;
	}
	
	/*
	 * @return
	 * 	determines whether the car is empty(null load)
	 */
	public boolean isEmpty() {
		return load == null;
	}
}
