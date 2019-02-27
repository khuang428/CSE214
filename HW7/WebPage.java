/*
 * Karen Huang
 * 111644515
 * 
 * The WebPage class represents a hyperlinked(URL) document
 */

import java.util.ArrayList;

public class WebPage {
	private String url;
	private int index;
	private int rank;
	private ArrayList<String> keywords;
	
	//default constructor
	public WebPage() {
		
	}
	
	/*
	 * constructor with url, index, keywords given
	 * @param u
	 * 	the WebPage's URL
	 * @param i
	 * 	the WebPage's index
	 * @param k
	 * 	the keywords associated with the WebPage
	 */
	public WebPage(String u, int i, ArrayList<String>k) {
		url = u;
		index = i;
		keywords = k;
	}
	
	/*
	 * @return
	 * 	returns the url of the document
	 */
	public String getUrl() {
		return url;
	}
	
	/*
	 * @return
	 * 	returns the index of the document
	 */
	public int getIndex() {
		return index;
	}
	
	/*
	 * @return
	 * 	returns the rank of the document
	 */
	public int getRank() {
		return rank;
	}
	
	/*
	 * @return
	 * 	returns the ArrayList with all the keywords of the document
	 */
	public ArrayList<String> getKeywords(){
		return keywords;
	}
	
	/*
	 * changes the URL of the document
	 * 
	 * @param u
	 * 	the new URL of the document
	 */
	public void setUrl(String u) {
		url = u;
	}
	
	/*
	 * changes the index of the document
	 * 
	 * @param i
	 * 	the new index of the document
	 */
	public void setIndex(int i) {
		index = i;
	}
	
	/*
	 * changes the rank of the document
	 * 
	 * @param r
	 * 	the new rank of the document
	 */
	public void setRank(int r) {
		rank = r;
	}
	
	/*
	 * changes the keywords associated with the document
	 * 
	 * @param k
	 * 	the new list of keywords
	 */
	public void setKeywords(ArrayList<String> k) {
		keywords = k;
	}
	
	/*
	 * prints out a tabular representation of the webpage
	 */
	public String toString() {
		String kw = "";
		for(String k:keywords) {
			kw += k + ", ";
		}
		kw = kw.substring(0,kw.length() - 2);
		return String.format("%3d   | %-25s | %3d   | * | %s", index,url,rank,kw);
	}
	
}
