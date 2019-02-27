/*
 * Karen Huang
 * 111644515
 * 
 * Exception thrown when planner is full 
 */
@SuppressWarnings("serial")
public class FullPlannerException extends Exception{
	
	public FullPlannerException() {
	}
	
	public FullPlannerException(String message) {
		super(message);
	}
}
