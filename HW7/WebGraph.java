/*
 * Karen Huang
 * 111644515
 * 
 * The WebGraph class represents a directed graph of WebPages
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class WebGraph {
	public static final int MAX_PAGES = 40;
	private ArrayList<WebPage> pages;
	private int[][] edges = new int[MAX_PAGES][MAX_PAGES];
	
	//default constructor
	public WebGraph() {
		
	}
	
	/*
	 * constructor with pages and edges given
	 * 
	 * @param p
	 * 	the ArrayList of pages
	 * @param e
	 * 	the 2D array of edges
	 */
	public WebGraph(ArrayList<WebPage> p, int[][]e) {
		pages = p;
		edges = e;
	}
	
	/*
	 * @return
	 * 	returns the list of pages
	 */
	public ArrayList<WebPage>getPages(){
		return pages;
	}
	
	/*
	 * Constructs a WebGraph from given files
	 * 
	 * @param pagesFile
	 * 	file containing page info
	 * @param linksFile
	 * 	file containing link info between pages
	 * Precondition: both files exist and are formatted properly
	 * Postcondition: a WebGraph has been constructed
	 * @return
	 * 	the newly created WebGraph
	 * @exception IllegalArgumentException
	 * 	thrown when the files are formatted incorrectly
	 * @exception FileNotFoundException
	 * 	thrown when the files do not exist
	 */
	public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException,
																					 FileNotFoundException{
		ArrayList<WebPage> pages = new ArrayList<WebPage>();
		ArrayList<String> names = new ArrayList<String>();
		int[][]edges = new int[MAX_PAGES][MAX_PAGES];
		try {
			Scanner p = new Scanner(new File(pagesFile));
			int ctr = 0;
			while(p.hasNextLine()) {
				String[] page = p.nextLine().trim().split(" ");
				
				//checking that url is first, there are no other urls, there is a keyword
				if(!page[0].contains(".") || page.length < 2) {
					p.close();
					throw new IllegalArgumentException("Bad formatting in pages.txt.");
				}
				for(int i = 1;i < page.length;i++) {
					if(page[i].contains(".")) {
						p.close();
						throw new IllegalArgumentException("Bad formatting in pages.txt.");
					}
				}
				
				ArrayList<String>kws = new ArrayList<String>();
				for(int i = 1;i < page.length;i++) {
					kws.add(page[i]);
				}
				pages.add(new WebPage(page[0],ctr++,kws));
				names.add(page[0]);
			}
			Scanner l = new Scanner(new File(linksFile));
			while(l.hasNextLine()) {
				String[] link = l.nextLine().trim().split(" ");
				
				//checking only 2 things per line, they are urls, they are in list of pages
				if(link.length != 2) {
					p.close();
					l.close();
					throw new IllegalArgumentException("Bad formatting in links.txt.");
				}
				for(String li:link) {
					if(!li.contains(".") || !names.contains(li)) {
						p.close();
						l.close();
						throw new IllegalArgumentException("Bad formatting in links.txt.");
					}
				}
				
				edges[names.indexOf(link[0])][names.indexOf(link[1])] = 1;
			}
			p.close();
			l.close();
			return new WebGraph(pages,edges);
		}catch(FileNotFoundException e) {
			throw new FileNotFoundException("File not found.");
		}
	}
	
	/*
	 * adds a WebPage to the graph
	 * 
	 * @param url
	 * 	the url of the new WebPage
	 * @param keywords
	 * 	the keywords associated with the new page
	 * Precondition: url is unique, url and keywords are not null
	 * Postcondition: pages has been added to pages at index i
	 * @exception IllegalArgumentException
	 * 	thrown if url is not unique or if url or keywords is null
	 */
	public void addPage(String url, ArrayList<String> keywords) throws IllegalArgumentException{
		if(pages.size() == MAX_PAGES) {
			System.out.println("WebGraph is full.");
			return;
		}
		if(url == null || url.equals("") || keywords == null || keywords.get(0).equals("")) {
			throw new IllegalArgumentException("URL or keywords is null.");
		}
		if(!url.contains(".")) {
			throw new IllegalArgumentException("Not a valid url.");
		}
		for(WebPage page:pages) {
			if(url.equals(page.getUrl())) {
				throw new IllegalArgumentException("URL already exists.");
			}
		}
		pages.add(new WebPage(url,pages.size(),keywords));
		updatePageRanks();
	}
	
	/*
	 * adds a one-way link between 2 WebPages
	 * 
	 * @param source
	 * 	name of page that links to the other page
	 * @param destination
	 * 	name of page being linked to
	 * Precondition: both WebPages exist
	 * @exception IllegalArgumentException
	 * 	thrown if either URL doesn't exist
	 */
	public void addLink(String source, String destination) throws IllegalArgumentException{
		int sIndex = -1;
		int dIndex = -1;
		for(WebPage page:pages) {
			if(source.equals(page.getUrl())){
				sIndex = page.getIndex();
			}
			if(destination.equals(page.getUrl())){
				dIndex = page.getIndex();
			}
		}
		if(sIndex == -1) {
			throw new IllegalArgumentException(source + " could not be found.");
		}
		if(dIndex == -1) {
			throw new IllegalArgumentException(destination + " could not be found.");
		}
		edges[sIndex][dIndex] = 1;
		updatePageRanks();
	}
	
	/*
	 * remove the WebPage with given URL
	 * 
	 * @param url
	 * 	the url of the page to be removed
	 * Postcondition: page is removed and its corresponding row/col in edges is removed
	 */
	public void removePage(String url) {
		int ctr = -1;
		for(WebPage page:pages) {
			if(page.getUrl().equals(url)) {
				ctr = page.getIndex();
			}
		}
		if(ctr == -1) {
			System.out.println(url+ " could not be found.");
			return;
		}
		
		for(int i = ctr;i < pages.size() - 1;i++) {
			for(int j = 0;j < pages.size();j++){
				edges[j][i] = edges[j][i+1];
			}
		}
		for(int i = ctr;i < pages.size() - 1;i++) {
			for(int j = 0;j < pages.size();j++){
				edges[i][j] = edges[i+1][j];
			}
		}
		for(int i = 0;i < pages.size();i++) {
			edges[i][pages.size()] = 0;
			edges[pages.size()][i] = 0;
		}
		pages.remove(ctr);
		for(int i = ctr;i < pages.size();i++) {
			pages.get(i).setIndex(i);
		}
		System.out.println(url+ " has been removed.");
		updatePageRanks();
	}
	
	/*
	 * removes a link between two pages
	 * 
	 * @param source
	 * 	the url from which to remove the link
	 * @param destination
	 * 	the url of the link to be removed
	 * Postcondition: the entry in edges is set to 0, or nothing is done if one of the URLs is not found
	 */
	public void removeLink(String source, String destination) {
		int sIndex = -1;
		int dIndex = -1;
		for(WebPage page:pages) {
			if(source.equals(page.getUrl())){
				sIndex = page.getIndex();
			}
			if(destination.equals(page.getUrl())){
				dIndex = page.getIndex();
			}
		}
		if(sIndex == -1 || dIndex == -1) {
			return;
		}
		edges[sIndex][dIndex] = 0;
		System.out.println();
		System.out.printf("Link removed from %s to %s.\n",source,destination);
		updatePageRanks();
	}
	
	/*
	 * calculates and assigns the rank for every page in the WebGraph
	 * 
	 * Postcondition: all pages have been assigned a rank
	 */
	public void updatePageRanks() {
		ArrayList<Integer>links = new ArrayList<Integer>();
		for(int i = 0;i < pages.size();i++) {
			links.add(0);
		}
		for(int i = 0;i < pages.size();i++) {
			//System.out.println(i);
			for(int j = 0;j < pages.size();j++){
				//System.out.println(j);
				links.set(i, links.get(i) + edges[j][i]);
			}
		}
		ArrayList<Integer>linksNoDupe = new ArrayList<Integer>(links);
		Collections.sort(linksNoDupe);
		LinkedHashSet<Integer> linksTemp = new LinkedHashSet<Integer>(linksNoDupe);
		linksNoDupe = new ArrayList<Integer>(linksTemp);
		//System.out.println(links);
		//System.out.println(linksNoDupe);
		for(WebPage p:pages) {
			//System.out.println(p.getIndex());
			p.setRank(linksNoDupe.indexOf(links.get(p.getIndex()))+1);
		}
	}
	
	/*
	 * prints out a tabular representation of a keyword search
	 * @param keyword
	 * 	the keyword being search for
	 */
	public void search(String keyword) {
		ArrayList<WebPage> relevant = new ArrayList<WebPage>();
		for(WebPage p:pages) {
			if(p.getKeywords().contains(keyword)) {
				relevant.add(p);
			}
		}
		System.out.println();
		if(relevant.size() == 0) {
			System.out.println("No search results found for the keyword " + keyword);
		}else {
			int ctr = 1;
			System.out.println("Rank  PageRank  URL\n"+
					"---------------------------------------------");
			Collections.sort(relevant,new RankComparator());
			for(WebPage p:relevant) {
				System.out.printf("%3d |  %3d    |%-25s\n",ctr++,p.getRank(),p.getUrl());
			}
		}
	}
	
	/*
	 * prints the WebGraph in tabular form
	 * 
	 * @param order
	 * 	the order to sort in and then print
	 */
	public void printTable(String order) {
		switch(order) {
			case "i":
				Collections.sort(pages,new IndexComparator());
				break;
			case "u":
				Collections.sort(pages,new URLComparator());
				break;
			case "r":
				Collections.sort(pages,new RankComparator());
				break;
			default:
				System.out.println("Not a valid option.");
				return;
		}
		System.out.println("Index  URL                        PageRank  Links                     Keywords\r\n" + 
				"------------------------------------------------------------------------------------------------------");
		for(WebPage p:pages) {
			String s = p.toString();
			int index = p.getIndex();
			String e = "  ";
			for(int i = 0;i < pages.size();i++) {
				if(edges[index][i] == 1) {
					e += i + ", ";
				}
			}
			e = e.substring(0, e.length() - 2);
			s = s.replace("*", String.format("%-25s",e));
			System.out.println(s);
		}
	}
}
