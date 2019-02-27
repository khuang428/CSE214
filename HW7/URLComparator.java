/*
 * Karen Huang
 * 111644515
 * 
 * The URLComparator class sorts WebPages based on URL
 */

import java.util.Comparator;

public class URLComparator implements Comparator<WebPage>{
	public int compare(WebPage arg0, WebPage arg1) {
		return arg0.getUrl().compareTo(arg1.getUrl());
		
	}
}
