/*
 * Karen Huang
 * 111644515
 * 
 * The RankComparator class sorts WebPages based on rank
 */

import java.util.Comparator;

public class RankComparator implements Comparator<WebPage>{
	public int compare(WebPage arg0, WebPage arg1) {
		if(arg0.getRank() == arg1.getRank()) {
			IndexComparator tiebreaker = new IndexComparator();
			return tiebreaker.compare(arg0, arg1);
		}else if(arg0.getRank() > arg1.getRank()) {
			return -1;
		}else {
			return 1;
		}
	}
}
