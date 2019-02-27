/*
 * Karen Huang
 * 111644515
 * 
 * The TrainLinkedList class is a doubly linked list of TrainCarNode objects
 */
public class TrainLinkedList {
	private TrainCarNode head;
	private TrainCarNode tail;
	private TrainCarNode cursor;
	private int size;
	private double length;
	private double value;
	private double weight;
	private int numDanger;

	/* default constructor
	 * 
	 * Postcondition: TrainLinkedList is initialized to an empty linked list and
	 * 	head, tail, and cursor are set to null
	 */
	public TrainLinkedList() {
	}
	
	/*
	 * @return
	 * 	returns a reference to the TrainCar at the node referenced by the cursor
	 * Precondition: list is not empty
	 */
	public TrainCar getCursorData() {
		if(cursor == null) {
			return null;
		}
		return cursor.getCar();
	}
	
	/* places car in the node referenced by the cursor
	 * 
	 * @param car
	 * 	car to be placed
	 * Precondition: list is not empty
	 * Postcondition: cursor node now contains reference to car
	 */
	public void setCursorData(TrainCar car) {
		if(!cursor.getCar().isEmpty()) {
			ProductLoad oldpl = cursor.getCar().getLoad();
			weight -= oldpl.getWeight();
			value -= oldpl.getValue();
			if(oldpl.isDangerous()) {
				numDanger--;
			}
		}
		weight -= cursor.getCar().getWeight();
		length -= cursor.getCar().getLength();
		cursor.setCar(car);
		if(!car.isEmpty()) {
			ProductLoad newpl = car.getLoad();
			weight += newpl.getWeight();
			value += newpl.getValue();
			if(newpl.isDangerous()) {
				numDanger++;
			}
		}
		weight += car.getWeight();
		length += car.getLength();
	}
	
	/* moves cursor to point at the next TrainCarNode
	 * 
	 * @return
	 * 	returns true if works, false if cursor stays
	 * Precondition: list is not empty and cursor does not currently reference tail
	 * Postcondition: cursor advances to next TraincarNode or stays at the tail
	 */
	public boolean cursorForward() {
		if(!cursor.equals(tail)) {
			cursor = cursor.getNext();
			return true;
		}
		return false;
	}
	
	/* moves cursor to point at the previous TrainCarNode
	 * 
	 * @return
	 * 	returns true if works, false if cursor stays
	 * Precondition: list is not empty and cursor does not currently reference head
	 * Postcondition: cursor moves back TraincarNode or stays at the head
	 */
	public boolean cursorBackward() {
		if(!cursor.equals(head)) {
			cursor = cursor.getPrev();
			return true;
		}
		return false;
	}
	
	/* inserts a car after the cursor position
	 * 
	 * @param newCar
	 * 	TrainCar to be inserted
	 * Precondition: newCar has been instantiated
	 * Postcondition: newCar has been inserted, all other cars keep their order,
	 * 	cursor now points at newCar
	 * @exception IllegalArgumentExcecption
	 * 	indicates newCar is null
	 */
	public void insertAfterCursor(TrainCar newCar) throws IllegalArgumentException{
		if(newCar == null) {
			throw new IllegalArgumentException();
		}
		TrainCarNode insert = new TrainCarNode(newCar);
		if(cursor == null) {
			head = insert;
			tail = insert;
			cursor = insert;
		}else {
			if(cursor.getNext() != null) {
				cursor.getNext().setPrev(insert);
				insert.setNext(cursor.getNext());
			}else {
				tail = insert;
			}
			cursor.setNext(insert);
			insert.setPrev(cursor);
			cursorForward();
		}
		weight += newCar.getWeight();
		length += newCar.getLength();
		size++;
		if(!newCar.isEmpty()) {
			ProductLoad newpl = newCar.getLoad();
			weight += newpl.getWeight();
			value += newpl.getValue();
			if(newpl.isDangerous()) {
				numDanger++;
			}
		}
	}
	
	/* removes the TrainCarNode referenced by the cursor
	 * 
	 * @return
	 * 	returns the TrainCar within the removed node
	 * Precondition: cursor is not null
	 * Postcondition: cursor now references the next node or the previous node if no next node,
	 * 	TrainCarNode that was referenced by the cursor is removed
	 */
	public TrainCar removeCursor() {
		TrainCar ret = cursor.getCar();
		size--;
		weight -= ret.getWeight();
		length -= ret.getLength();
		if(!ret.isEmpty()) {
			ProductLoad pl = ret.getLoad();
			weight -= pl.getWeight();
			value -=pl.getValue();
			if(pl.isDangerous()) {
				numDanger--;
			}
		}
		if(cursor.getNext() == null) {
			cursorBackward();
			cursor.setNext(null);
			tail = cursor;
		}else if(cursor.getPrev() != null){
			cursor.getPrev().setNext(cursor.getNext());
			cursorForward();
			cursor.setPrev(cursor.getPrev().getPrev());
		}else {
			cursor = null;
		}
		return ret;
	}
	
	/*
	 * @return
	 * 	returns the number of TrainCar objects on the train
	 */
	public int size() {
		return size;
	}
	
	/*
	 * @return
	 * 	returns the total length of the train
	 */
	public double getLength() {
		return length;
	}
	
	/*
	 * @return
	 * 	returns the total value of the products in the train
	 */
	public double getValue() {
		return value;
	}
	
	/*
	 * @return
	 * 	returns the total weight of the train, including products
	 */
	public double getWeight() {
		return weight;
	}
	
	/*
	 * @return
	 * 	returns whether or not there is a dangerous load on the train
	 */
	public boolean isDangerous() {
		return numDanger > 0;
	}
	
	/* searches for a product and sums all their weights and values
	 * 
	 * @param name
	 * 	the name of the product to search for
	 */
	public void findProduct(String name) {
		if(size == 0) {
			System.out.printf("No record of %s on board train.\n", name);
			return;
		}
		TrainCarNode pointer = head;
		double w = 0, v = 0;
		int ctr = 0;
		boolean dangerous = false;
		String danger;
		while(pointer.getNext() != null) {
			if(pointer.getCar().getLoad().getName().equals(name)) {
				ctr++;
				ProductLoad load = pointer.getCar().getLoad();
				dangerous = load.isDangerous();
				w += load.getWeight();
				v += load.getValue();
			}
			pointer = pointer.getNext();
		}
		if(pointer.getCar().getLoad().getName().equals(name)) {
			ctr++;
			ProductLoad load = pointer.getCar().getLoad();
			dangerous = load.isDangerous();
			w += load.getWeight();
			v += load.getValue();
		}
		if(dangerous) {
			danger = "YES";
		}else {
			danger = "NO";
		}
		if(w == 0 || v == 0) {
			System.out.printf("No record of %s on board train.\n", name);
		}else {
			System.out.printf("The following products were found on %d car(s):\n", ctr);
			System.out.printf("    Name      Weight (t)     Value ($)   Dangerous\n"
					+ "===================================================\n");
			System.out.printf("      %s         %.1f     %.2f          %s",name,w,v,danger);
		}
	}
	

	/*
	 * prints a formatted table of all train cars and their products
	 */
	public void printManifest() {
		System.out.println("    CAR:                               LOAD:");
		System.out.println("      Num   Length (m)    Weight (t)   "
				+ "    Name      Weight (t)     Value ($)   Dangerous");
		System.out.println("    =================================="
				+ "===================================================");
		if(size == 0) {
			return;
		}
		TrainCarNode pointer = head;
		int ctr = 1;
		while(pointer.getNext() != null){
			if(pointer.equals(cursor)) {
				System.out.print(" ->");
			}else {
				System.out.print("   ");
			}
			System.out.printf("    %d          ",ctr);
			System.out.println(pointer.toString());
			pointer = pointer.getNext();
			ctr++;
		}
		if(pointer.equals(cursor)) {
			System.out.print(" ->");
		}else {
			System.out.print("   ");
		}
		System.out.printf("    %d          ",ctr);
		System.out.println(pointer.toString());
	}
	
	/* removes all dangerous cars from the train
	 * 
	 * Postcondition: dangerous cars have been removed, order of other cars stays the same
	 */
	public void removeDangerousCars() {
		if(size == 0) {
			return;
		}
		cursor = head;
		while(cursor.getNext() != null) {
			if(!cursor.getCar().isEmpty() && cursor.getCar().getLoad().isDangerous()) {
				removeCursor();
			}else {
				cursorForward();
			}
		}
		if(!cursor.getCar().isEmpty() && cursor.getCar().getLoad().isDangerous()) {
			removeCursor();
		}
	}
	
	/*
	 * @return
	 * 	returns a string representation of the train
	 */
	
	public void setLoad(ProductLoad pl) {
		if(!cursor.getCar().isEmpty()) {
			ProductLoad oldpl = cursor.getCar().getLoad();
			weight -= oldpl.getWeight();
			value -= oldpl.getValue();
			if(oldpl.isDangerous()) {
				numDanger--;
			}
		}
		weight += pl.getWeight();
		value += pl.getValue();
		if(pl.isDangerous()) {
			numDanger++;
		}
		cursor.getCar().setLoad(pl);
	}
	public String toString() {
		String ret = "Train: ";
		ret += size + " car(s), ";
		ret += String.format("%.1f meters, %.1f tons, ", length, weight);
		ret += String.format("$%.2f value, ", value);
		if(numDanger > 0) {
			ret += "DANGEROUS.";
		}else {
			ret += "NOT DANGEROUS.";
		}
		return ret;
	}
}
