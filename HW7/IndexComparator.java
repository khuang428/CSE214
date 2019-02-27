/*
 * Karen Huang
 * 111644515
 * 
 * The IndexComparator class sorts WebPages based on index
 */

import java.util.Comparator;

public class IndexComparator implements Comparator<WebPage>{
	public int compare(WebPage arg0, WebPage arg1) {
		if(arg0.getIndex() == arg1.getIndex()) {
			return 0;
		}else if(arg0.getIndex() > arg1.getIndex()) {
			return 1;
		}else {
			return -1;
		}
	}

}
