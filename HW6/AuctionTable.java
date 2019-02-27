/*
 * Karen Huang
 * 111644515
 * 
 * The AuctionTable class represents the database of open auctions
 */

import java.io.Serializable;
import java.util.HashMap;
import big.data.*;

public class AuctionTable extends HashMap<String,Auction> implements Serializable{
	//default constructor
	public AuctionTable() {
		
	}
	
	/*
	 * constructs an AuctionTable from a remote data source
	 * @param URL
	 * 	the URL of the remote data source
	 * Precondition: URL represents a valid data source with proper syntax
	 * @return
	 * 	returns the newly constructed AuctionTable
	 * @exception IllegalArgumentException
	 * 	thrown if URL is not valid
	 */
	public static AuctionTable buildFromURL(String URL) throws IllegalArgumentException{
		try {
			DataSource ds = DataSource.connect(URL).load();
			String[]dataSN = ds.fetchStringArray("listing/seller_info/seller_name");
			String[]dataCBs = ds.fetchStringArray("listing/auction_info/current_bid");
			double[]dataCB = new double[dataCBs.length];
			for(int i = 0;i < dataCBs.length;i++) {
				dataCB[i] = Double.parseDouble(dataCBs[i].replace('$', '0').replaceAll(",", "")); 
			}
			String[]dataTL = ds.fetchStringArray("listing/auction_info/time_left");
			int[]dataTR = new int[dataTL.length];
			for(int i = 0;i < dataTL.length;i++) {
				String[]dataTLs = dataTL[i].split(" ");
				for(int j = 0;j < dataTLs.length;j++){
					if(dataTLs[j].contains("day")) {
						dataTR[i] += Integer.parseInt(dataTLs[j-1]) * 24;
					}else if(dataTLs[j].contains("hour")) {
						dataTR[i] += Integer.parseInt(dataTLs[j-1]);
					}
				}
			}
			String[]dataAID = ds.fetchStringArray("listing/auction_info/id_num");
			String[]dataBN = ds.fetchStringArray("listing/auction_info/high_bidder/bidder_name");
			String[]dataI0 = ds.fetchStringArray("listing/item_info/memory");
			String[]dataI1 = ds.fetchStringArray("listing/item_info/hard_drive");
			String[]dataI2 = ds.fetchStringArray("listing/item_info/cpu");
			AuctionTable ret = new AuctionTable();
			for(int i = 0;i < dataAID.length;i++) {
				ret.putAuction(dataAID[i],new Auction(dataTR[i],dataCB[i],dataAID[i],dataSN[i],dataBN[i],dataI2[i]+" - "+dataI0[i]+" - "+dataI1[i]));
			}
			return ret;
		}catch(DataSourceException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/*
	 * posts an auction and adds it to the table
	 * @param auctionID
	 * 	the key for the auction
	 * @param auction
	 * 	the auction to add
	 * Postcondition: auction is added
	 * @exception: IllegalArgumentException
	 * 	thrown if auctionID is already stored in the table
	 */
	public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException{
		if(keySet().contains(auctionID)) {
			throw new IllegalArgumentException();
		}
		super.put(auctionID, auction);
	}
	
	/*
	 * gets the information of an auction with given key
	 * @param auctionID
	 * 	the key for the auction
	 * @return
	 * 	returns the auction with the given key(may be null)
	 */
	public Auction getAuction(String auctionID) {
		return super.get(auctionID);
	}
	
	/*
	 * simulates the passing of time
	 * @param numHours
	 * 	how much time is passing
	 */
	public void letTimePass(int numHours) {
		for(String key:keySet()) {
			getAuction(key).decrementTimeRemaining(numHours);
		}
	}
	
	/*
	 * removes expired auctions from the table
	 * Postcondition: only open auctions are in the table
	 */
	public void removeExpiredAuctions() {
		String[]toRemove = new String[keySet().size()];
		int i = 0;
		for(String key:keySet()) {
			if(getAuction(key).getTimeRemaining() == 0) {
				toRemove[i++] = key;
			}
		}
		for(String key:toRemove) {
			super.remove(key);
		}
	}
	
	/*
	 * prints the tabular representation of the AuctionTable
	 */
	public void printTable() {
		System.out.println("Auction ID |     Bid     |          Seller           |           Buyer           |    Time   |  Item Info\n"+
				"========================================================================================================================");
		for(String key:keySet()) {
			System.out.println(getAuction(key));
		}
	}
}
