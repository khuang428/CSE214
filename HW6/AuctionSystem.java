/*
 * Karen Huang
 * 111644515
 * 
 * The AuctionSystem class provides a prompt menu for an AuctionTable
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class AuctionSystem implements Serializable{
	private static AuctionTable at;
	private static String username;
	
	public static void main(String[]args) {
		System.out.println("Starting...");
		try {
			File file = new File("auction.obj");
			if(file.exists()) {
				System.out.println("Loading previous Auction Table...");
				FileInputStream fileI = new FileInputStream(file);
				ObjectInputStream inStream = new ObjectInputStream(fileI);
				at = (AuctionTable) inStream.readObject();
				inStream.close();
			}else {
				file.createNewFile();
				System.out.println("No previous auction table detected.");
				System.out.println("Creating new table...");
			}
			Scanner input = new Scanner(System.in);
			System.out.print("Please select a username: ");
			username = input.nextLine();
			while(true) {
				System.out.println();
				System.out.println("(D) - Import Data from URL\n(A) - Create a New Auction\n"
						+ "(B) - Bid on an Item\n(I) - Get Info on Auction\n(P) - Print All Auctions\n"
						+ "(R) - Remove Expired Auctions\n(T) - Let Time Pass\n(Q) - Quit");
				System.out.println();
				System.out.print("Please select an option: ");
				System.out.println();
				switch(input.nextLine().toLowerCase()) {
					case("d"):
						System.out.print("Please enter a URL: ");
						try {
							at = AuctionTable.buildFromURL(input.nextLine());
						}catch(IllegalArgumentException e) {
							System.out.println("URL is not valid");
						}
						break;
					case("a"):
						System.out.println("Creating new Auction as " + username);
						System.out.print("Please enter an Auction ID: ");
						String id = input.nextLine();
						System.out.print("Please enter an Auction time (hours): ");
						int t = input.nextInt();
						input.nextLine();
						System.out.print("Please enter some Item Info: ");
						String i = input.nextLine();
						try {
							at.putAuction(id,new Auction(t,0,id,username,"",i));
							System.out.println("Auction " + id + " inserted into table.");
						}catch(IllegalArgumentException e) {
							System.out.println("Auction ID is already in the table.");
						}
						break;
					case("b"):
						System.out.print("Please enter an Auction ID: ");
						String bID = input.nextLine();
						if(at.getAuction(bID).getTimeRemaining() == 0) {
							System.out.println("Auction " + bID + " is CLOSED");
							System.out.printf("Current Bid: $%.2f\n\n",at.getAuction(bID).getCurrentBid());
							System.out.println("You can no longer bid on this item.");
						}else {
							System.out.println("Auction " + bID + " is OPEN");
							System.out.printf("Current Bid: $%.2f\n\n",at.getAuction(bID).getCurrentBid());
							System.out.print("What would you like to bid: ");
							double bid = input.nextDouble();
							input.nextLine();
							try {
								at.getAuction(bID).newBid(username, bid);
								System.out.println("Bid accepted.");
							}catch(ClosedAuctionException e) {
								System.out.println("You can no longer bid on this item.");
							}
						}
						break;
					case("i"):
						System.out.print("Please enter an Auction ID: ");
						String gID = input.nextLine();
						Auction a = at.getAuction(gID);
						if(a == null) {
							System.out.println("Auction does not exist.");
						}else {
							System.out.println("Auction " + gID + ":");
							System.out.println("   Seller: " + a.getSellerName());
							System.out.println("   Buyer: " + a.getBuyerName());
							System.out.println("   Time: " + a.getTimeRemaining() + " hours");
							System.out.println("   Info: " + a.getItemInfo());
						}
						break;
					case("p"):
						at.printTable();
						break;
					case("r"):
						System.out.println("Removing expired auctions...");
						at.removeExpiredAuctions();
						System.out.println("All expired auctions removed.");
						break;
					case("t"):
						System.out.print("How many hours should pass: ");
						at.letTimePass(input.nextInt());
						input.nextLine();
						break;
					case("q"):
						System.out.println("Writing Auction Table to file...");
						FileOutputStream fileO = new FileOutputStream(file);
						ObjectOutputStream outStream = new ObjectOutputStream(fileO);
						outStream.writeObject(at);
						fileO.close();
						System.out.println();
						System.out.println("Goodbye.");
						input.close();
						System.exit(0);
						break;
				}
			}
		}catch(FileNotFoundException e) {
			System.out.println("File not found");
		} catch (ClassNotFoundException e) {
			System.out.println("AuctionTable not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
