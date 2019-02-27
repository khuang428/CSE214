/*
 * Karen Huang
 * 111644515
 * 
 * The Complexity class represents the big O complexity of a block of code(limited to n and logn)
 */
public class Complexity {
	private int nPower;
	private int logPower;
	
	//default constructor
	public Complexity() {
		
	}
	
	/* constructor with given nPower and logPower
	 * 
	 * @param n
	 * 	the n power of the complexity of a block of code
	 * @param l
	 * 	the log power of the complexity of a block of code
	 */
	public Complexity(int n,int l) {
		nPower = n;
		logPower = l;
	}
	
	/*
	 * @return
	 * 	returns the n power of the complexity of a block of code
	 */
	public int getNPower() {
		return nPower;
	}
	
	/*
	 * @return
	 * 	returns the log power of the complexity of a block of code
	 */
	public int getLogPower() {
		return logPower;
	}
	
	/* 
	 * sets the nPower to n
	 * 
	 * @param n
	 * 	the new nPower of the complexity of a block of code
	 */
	public void setNPower(int n) {
		nPower = n;
	}
	
	/* 
	 * sets the logPower to l
	 * 
	 * @param l
	 * 	the new logPower of the complexity of a block of code
	 */
	public void setLogPower(int l) {
		logPower = l;
	}
	
	/*
	 * @return
	 * 	returns the string representation of the complexity of a block of code
	 */
	public String toString() {
		if(nPower == 0 && logPower == 0) {
			return "O(1)";
		}
		else if(nPower == 0) {
			return String.format("O(log(n)^%s)", logPower);
		}
		else if(logPower == 0) {
			return String.format("O(n^%s)", nPower);
		}
		return String.format("O(n^%s * log(n)^%s)", nPower, logPower);
	}
}
