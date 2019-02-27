/*
 * Karen Huang
 * 111644515
 * 
 * The Simulator class is used to simulate a network based on user input
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Simulator {
	private static Router dispatcher = new Router();
	private static double arrivalProb;
	//max packets intermediate routers can hold
	private static int maxBufferSize;
	private static int minPacketSize;
	private static int maxPacketSize;
	//maximum number of packets that can arrive at the destination in one simulation unit
	private static int bandwidth;
	private static int duration;
	public static final int MAX_PACKETS = 3;
	
	//running sum of time of packets in network(add destination time - creation time)
	private static int totalServiceTime;
	private static int totalPacketsArrived;
	//packets dropped when all router queues are full
	private static int packetsDropped;
	private static ArrayList<Integer> readyRouters = new ArrayList<Integer>();
	
	/*
	 * simulates a network with specific given specs
	 * 
	 * @return
	 * 	returns the average service time per packet
	 */
	public static double simulate(Router[]routers) {
		for(int time = 0; time < duration;time++) {
			System.out.println("Time: " + time);
			for(int i = 0;i < MAX_PACKETS;i++) {
				if(Math.random() < arrivalProb) {
					Packet ariv = new Packet(randInt(minPacketSize, maxPacketSize), time);
					dispatcher.add(ariv);
					System.out.printf("Packet %d arrives at dispatcher with size %d.\n",ariv.getId(),ariv.getPacketSize());
				}
			}
			if(dispatcher.size() == 0) {
				System.out.println("No packets arrived.");
			}
			while(dispatcher.size() > 0) {
				Packet p = dispatcher.dequeue();
				int sendTo = Router.sendPacketTo(routers);
				if(sendTo == routers.length || routers[sendTo].size() == maxBufferSize) {
					System.out.printf("Network is congested. Packet %d is dropped.\n", p.getId());
					packetsDropped++;
				}else {
					routers[sendTo].enqueue(p);
					System.out.printf("Packet %d sent to Router %d.\n", p.getId(),sendTo + 1);
				}
			}
			for(int i = 0;i < bandwidth;i++) {
				if(readyRouters.isEmpty()) {
					break;
				}else {
					if(!readyRouters.isEmpty()) {
						int rNum = readyRouters.remove(0);
						Packet rem = routers[rNum].dequeue();
						System.out.printf("Packet %d has successfully reached its destination: +%d\n",rem.getId(),time - rem.getTimeArrive());
						totalServiceTime += time - rem.getTimeArrive();
						totalPacketsArrived++;
					}
				}
			}
			for(int i = 1;i < routers.length + 1;i++) {
				System.out.println("R" + i + ": "+ routers[i-1].toString());
			}
			for(int i = 0;i < routers.length;i++) {
				if(routers[i].size() != 0) {
					routers[i].peek().decrTimeToDest();
					if(routers[i].peek().getTimeToDest() == 0 && routers[i].peek().getWaitTime() == 0) {
						readyRouters.add(i);
						System.out.println(i);
					}
				}
			}
			System.out.println();
		}
		if(totalPacketsArrived != 0) {
			return totalServiceTime/(double)totalPacketsArrived;
		}else {
			return 0;
		}
	}
	
	/*
	 * @param minVal
	 * 	the lower bound
	 * @param maxVal
	 * 	the upper bound
	 * @return
	 * 	returns a number in the range between minVal and maxVal, inclusive
	 */
	private static int randInt(int minVal, int maxVal) {
		int range = maxVal - minVal + 1;
		return (int)(Math.random() * (range)) + minVal;
	}
	
	public static void main(String[]args) {
		Scanner input = new Scanner(System.in);
		Router[] routers;
		while(true) {
			System.out.println("Starting simulator...");
			System.out.print("Enter the number of intermediate routers: ");
			routers = new Router[input.nextInt()];
			for(int i = 0;i < routers.length;i++) {
				routers[i] = new Router();
			}
			input.nextLine();
			System.out.print("Enter the arrival probability of a packet: ");
			arrivalProb = input.nextDouble();
			input.nextLine();
			System.out.print("Enter the maximum buffer size of a router: ");
			maxBufferSize = input.nextInt();
			input.nextLine();
			System.out.print("Enter the minimum size of a packet: ");
			minPacketSize = input.nextInt();
			input.nextLine();
			System.out.print("Enter the maximum size of a packet: ");
			maxPacketSize = input.nextInt();
			input.nextLine();
			System.out.print("Enter the bandwidth size: ");
			bandwidth = input.nextInt();
			input.nextLine();
			System.out.print("Enter the simulation duration: ");
			duration = input.nextInt();
			input.nextLine();
			double average = simulate(routers);
			System.out.println("Simulation ending...");
			System.out.println("Total service time: " + totalServiceTime);
			System.out.println("Total packets served: " + totalPacketsArrived);
			System.out.printf("Average service time per packet: %.02f\n",average);
			System.out.println("Total packets dropped: " + packetsDropped);
			while(true) {
				System.out.print("Do you want to try another simulation? (y/n): ");
				String quit = input.nextLine();
				if(quit.equals("n")) {
					System.out.println("Program terminating successfully...");
					input.close();
					System.exit(0);
				}else if(quit.equals("y")) {
					totalServiceTime = 0;
					totalPacketsArrived = 0;
					packetsDropped = 0;
					dispatcher.clear();
					readyRouters.clear();
					Packet.resetPacket();
					break;
				}
			}
		}
	}
}
