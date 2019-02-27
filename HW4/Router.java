/*
 * Karen Huang
 * 111644515
 * 
 * The Router class represents one router in the network using a queue
 */

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Router extends ArrayList<Packet> {
	
	//default constructor
	public Router() {
		
	}
	
	/*
	 * adds a packet to the end of the router queue
	 * 
	 * @param p
	 * 	the packet to be added
	 */
	public void enqueue(Packet p) {
		super.add(p);
	}
	
	/*
	 * removes the first packet in the router queue
	 * 
	 * @return
	 * 	returns the removed packet
	 */
	public Packet dequeue() {
		return super.remove(0);
	}
	
	/*
	 * @return
	 * 	returns the first packet in the router queue
	 */
	public Packet peek() {
		return super.get(0);
	}
	
	/*
	 * @return 
	 * 	returns how many packets are in the router
	 */
	public int size() {
		return super.size();
	}
	
	/*
	 * @return
	 * 	returns whether the router is empty
	 */
	public boolean isEmpty() {
		return super.isEmpty();
	}
	
	/*
	 * @return
	 * 	returns the String representation of the router
	 */
	public String toString() {
		if(size() == 0) {
			return "{}";
		}
		String ret = "{";
		for(int i = 0;i < size() - 1;i++) {
			ret += super.get(i) + ", ";
		}
		ret += super.get(size() - 1);
		return ret +"}";
	}
	
	/*
	 * choose the least full router to send a packet to
	 * 
	 * @param routers
	 * 	the list of routers to go through
	 * @return
	 * 	returns the index of the least full router
	 */
	public static int sendPacketTo(Router[] routers){
		int least = 0;
		for(int i = 1;i < routers.length;i++){
			if(routers[i].size() < routers[least].size()) {
				least = i;
			}
		}
		return least;
	}
}
