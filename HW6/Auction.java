/*
 * Karen Huang
 * 111644515
 * 
 * The Auction class represents an active auction in the database
 */

import java.io.Serializable;

public class Auction implements Serializable{
	private int timeRemaining;
	private double currentBid;
	private String auctionID;
	private String sellerName;
	private String buyerName;
	private String itemInfo;
	
	//default constructor
	public Auction() {
		
	}
	
	/*
	 * constructor with parameters given
	 * @param tr
	 * 	time remaining for the auction
	 * @param cb
	 * 	the current highest bid
	 * @param aid
	 * 	the auction id
	 * @param sn
	 * 	the seller's name
	 * @param bn
	 * 	the buyer's name
	 * @param i
	 * 	the item info
	 */
	public Auction(int tr, double cb, String aid, String sn, String bn, String i) {
		timeRemaining = tr;
		currentBid = cb;
		auctionID = aid;
		if(sn.equals("")) {
			sn = "N/A";
		}
		if(bn.equals("")) {
			bn = "N/A";
		}
		if(i.equals("")) {
			i = "N/A";
		}
		sellerName = sn;
		buyerName = bn;
		itemInfo = i;
	}
	
	/*
	 * @return
	 * 	returns time remaining for the auction
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}
	
	/*
	 * @return
	 * 	returns the current highest bid
	 */
	public double getCurrentBid() {
		return currentBid;
	}
	
	/*
	 * @return
	 * 	returns the auction ID
	 */
	public String getAuctionID() {
		return auctionID;
	}
	
	/*
	 * @return
	 * 	returns the name of the seller
	 */
	public String getSellerName() {
		return sellerName;
	}
	
	/*
	 * @return
	 * 	returns the name of the current highest bidder 
	 */
	public String getBuyerName() {
		return buyerName;
	}
	
	/*
	 * @return
	 * 	returns the item info
	 */
	public String getItemInfo() {
		return itemInfo;
	}
	
	/*
	 * decreases time remaining by a specified amount 
	 * @param time
	 * 	the time to decrease by
	 * Postcondition: timeRemaining is decremented by the amount or set to 0 if amount is greater
	 */
	public void decrementTimeRemaining(int time) {
		timeRemaining -= time;
		if(timeRemaining < 0) {
			timeRemaining = 0;
		}
	}
	
	/*
	 * makes a new bid and replaces the current one if the new one is larger
	 * @param bidderName
	 * 	name of new bidder
	 * @param bidAmt
	 * 	how much is being bid
	 * Precondition: auction is not closed
	 * Postcondition: currentBid reflects the largest bid
	 * @exception ClosedAuctionException
	 * 	thrown if auction is closed
	 */
	public void newBid(String bidderName, double bidAmt) throws ClosedAuctionException{
		if(timeRemaining == 0) {
			throw new ClosedAuctionException();
		}
		if(bidAmt > currentBid) {
			currentBid = bidAmt;
			buyerName = bidderName;
		}
	}
	
	/*
	 * @return
	 * 	returns a tabular representation of the auction
	 */
	public String toString() {
		return String.format("%s  | $%10.2f | %-25s | %-25s | %3d hours | %25s",
				auctionID,currentBid, sellerName, buyerName, timeRemaining, itemInfo.substring(0, 25));
	}
}
