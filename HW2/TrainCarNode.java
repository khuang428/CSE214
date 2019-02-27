/*
 * Karen Huang
 * 111644515
 * 
 * The TrainCarNode class acts as a node wrapper for a TrainCar object
 */
public class TrainCarNode {
	private TrainCarNode prev;
	private TrainCarNode next;
	private TrainCar car;
	
	//default constructor
	public TrainCarNode() {
		
	}
	
	/* constructor with a given TrainCar
	 * 
	 * @param c
	 * 	the TrainCar that this node wraps
	 */
	public TrainCarNode(TrainCar c) {
		car = c;
	}
	
	/*
	 * @return
	 * 	returns the previous node
	 */
	public TrainCarNode getPrev() {
		return prev;
	}
	
	/*
	 * @return
	 * 	returns the next node
	 */
	public TrainCarNode getNext() {
		return next;
	}
	
	/*
	 * @return
	 * 	returns the TrainCar that is wrapped by this node
	 */
	public TrainCar getCar() {
		return car;
	}
	
	/*
	 * @param p
	 * 	the new node that is before this one
	 */
	public void setPrev(TrainCarNode p) {
		prev = p;
	}
	
	/*
	 * @param n
	 * 	the new node that is after this one
	 */
	public void setNext(TrainCarNode n) {
		next = n;
	}
	
	/*
	 * @param c
	 * 	the new TrainCar this node will wrap
	 */
	public void setCar(TrainCar c) {
		car = c;
	}
	
	/*
	 * @return
	 * 	returns the string representation of the train car
	 */
	public String toString() {
		if(car.isEmpty()) {
			return String.format("%.1f         %.1f         Empty            0.0         0.00         NO",
					car.getLength(), car.getWeight());
		}
		return String.format("%.1f%-9s%03.1f%-8s",car.getLength()," ", car.getWeight()," ") + car.getLoad().toString();
	}
}
