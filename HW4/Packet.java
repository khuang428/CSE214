/*
 * Karen Huang
 * 111644515
 * 
 * The Packet class represents a packet that will be sent through the network
 */
public class Packet {
	private static int packetCount = 0;
	private int id;
	private int packetSize;
	private int timeArrive;
	private int timeToDest;
	private int waitTime;
	
	//default constructor
	public Packet() {
		
	}
	
	/*
	 * constructor with size and arrival time given
	 * increments id and then sets the packet's id to that new number
	 * 
	 * @param s
	 * 	the size of the packet(also used to determine time to destination
	 * @param a
	 * 	the time of arrival of the packet
	 */
	public Packet(int s,int a) {
		id = incrPacketCount();
		packetSize = s;
		timeArrive = a;
		timeToDest = s / 100;
	}
	
	/*
	 * @return
	 * 	returns the number of packets that have been created
	 */
	public int getPacketCount() {
		return packetCount;
	}
	
	/*
	 * @return
	 * 	returns the id of the packet
	 */
	public int getId() {
		return id;
	}
	
	/*
	 * @return
	 * 	returns the size of the packet
	 */
	public int getPacketSize() {
		return packetSize;
	}
	
	/*
	 * @return
	 * 	returns when the packet was created
	 */
	public int getTimeArrive() {
		return timeArrive;
	}
	
	/*
	 * @return
	 * 	returns the number of simulation units it will take for the packet to arrive
	 */
	public int getTimeToDest() {
		return timeToDest;
	}
	
	/*
	 * @return
	 * 	returns the number of simulation units a packet waits to be sent to destination
	 */
	public int getWaitTime() {
		return waitTime;
	}
	/*
	 * sets the total number of packets created
	 * 
	 * @param c
	 * 	the new packetCount
	 */
	public void setPacketCount(int c) {
		packetCount = c;
	}
	
	/*
	 * sets the packet id
	 * 
	 * @param i
	 * 	the new id of the packet
	 */
	public void setId(int i) {
		id = i;
	}
	
	/*
	 * sets the packet size
	 * 
	 * @param s
	 * 	the new size of the packet
	 */
	public void setPacketSize(int s) {
		packetSize = s;
	}
	
	/*
	 * sets the time of creation of the packet
	 * 
	 * @param ta
	 * 	the new time of creation of the packet
	 */
	public void setTimeArrive(int ta) {
		timeArrive = ta;
	}
	
	/*
	 * sets the time it will take for the packet to reach the destination
	 * 
	 * @param td
	 * 	the new time to destination
	 */
	public void setTimeToDest(int td) {
		timeToDest = td;
	}
	
	/*
	 * decreases the time to destination by 1
	 */
	public void decrTimeToDest() {
		if(timeToDest > 0) {
			timeToDest--;
		}else {
			waitTime++;
		}
	}
	
	/*
	 * adds one to packetCount
	 * 
	 * @return
	 * 	returns the new value of packetCount
	 */
	public static int incrPacketCount() {
		return ++packetCount;
	}
	
	/*
	 * resets the packet count for a new simulation
	 */
	public static void resetPacket() {
		packetCount = 0;
	}
	
	/* 
	 * outputs a formatted string with the id, timeArrive, and timeToDest, respectively
	 * 
	 * @return
	 * 	returns the string representation of the packet
	 */
	public String toString() {
		return String.format("[%d, %d, %d]", id, timeArrive, timeToDest);
	}
}
