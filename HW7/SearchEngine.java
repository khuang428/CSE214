/*
 * Karen Huang
 * 111644515
 * 
 * The SearchEngine class allows the user to interact with a WebGraph
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class SearchEngine {
	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";
	private static WebGraph web;
	
	public static void main(String[]args) {
		Scanner input = new Scanner(System.in);
		try {
			System.out.println("Loading WebGraph data...");
			web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
			System.out.println("Success!");
			web.updatePageRanks();
			while(true) {
				Collections.sort(web.getPages(),new IndexComparator());
				System.out.println();
				System.out.println("(AP) - Add a new page to the graph\n(RP) - Remove a page from the graph\n"
					+ "(AL) - Add a link between pages in the graph\n(RL) - Remove a link between pages in the graph\n"
					+ "(P) - Print the graph\n(S) - Search for pages with a keyword\n(Q) - Quit\n");
				System.out.print("Please select an option: ");
				switch(input.nextLine().toLowerCase()) {
					case "ap":
						System.out.println();
						System.out.print("Enter a url: ");
						String ua = input.nextLine().trim();
						System.out.print("Enter keywords (space-separated): ");
						String[]k = input.nextLine().trim().split(" ");
						ArrayList<String> kw = new ArrayList<String>(Arrays.asList(k));
						try {
							web.addPage(ua, kw);
						}catch(IllegalArgumentException e) {
							System.out.println();
							System.out.println(e);
						}
						break;
					case "rp":
						System.out.println();
						System.out.print("Enter a url: ");
						String ur = input.nextLine().trim();
						web.removePage(ur);
						break;
					case "al":
						System.out.println();
						System.out.print("Enter a source url: ");
						String asurl = input.nextLine();
						System.out.print("Enter a destination url: ");
						String adurl = input.nextLine();
						try {
							web.addLink(asurl, adurl);
							System.out.println();
							System.out.printf("Link successfully added from %s to %s.\n",asurl, adurl);
						}catch(IllegalArgumentException e) {
							System.out.println();
							System.out.println(e);
						}
						break;
					case "rl":
						System.out.println();
						System.out.print("Enter a source url: ");
						String rsurl = input.nextLine();
						System.out.print("Enter a destination url: ");
						String rdurl = input.nextLine();
						web.removeLink(rsurl, rdurl);
						break;
					case "p":
						System.out.println();
						System.out.println("(I) - Sort based on index (ASC)\n"
								+ "(U) - Sort based on URL (ASC)\n(R) - Sort based on rank(DSC)\n");
						System.out.print("Please select an option: ");
						web.printTable(input.nextLine().toLowerCase());
						break;
					case "s":
						System.out.println();
						System.out.print("Search keyword: ");
						String skw = input.nextLine();
						web.search(skw);
						break;
					case "q":
						System.out.println();
						System.out.println("Goodbye.");
						input.close();
						System.exit(0);
				}
			}
		}catch(FileNotFoundException e) {
			input.close();
			System.out.println(e);
		}
	}
}
