/*
 * Karen Huang
 * 111644515
 * 
 * The CodeBlock class describes a nested block of code
 */
public class CodeBlock {
	public static final String[] BLOCK_TYPES = {"def", "for", "while", "if", "elif", "else"};
	public static final int DEF = 0;
	public static final int FOR = 1;
	public static final int WHILE = 2;
	public static final int IF = 3;
	public static final int ELIF = 4;
	public static final int ELSE = 5;
	
	private String name;
	private Complexity blockComplexity;
	private Complexity highestSubComplexity;
	private String loopVariable;
	private int indents;
	private final int type; //defined by block types above
	private int numSubs; //sub blocks used for naming
	
	//default constructor
	public CodeBlock() {
		type = 0;
	}
	
	/* constructor with complexity and type given
	 * 
	 * @param b
	 * 	the block complexity
	 * @param t
	 * 	the type of block
	 */
	public CodeBlock(Complexity b, int t) {
		blockComplexity = b;
		highestSubComplexity = new Complexity(0,0);
		type = t;
	}
	
	/*
	 * @return
	 * 	returns the name of the block
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * @return
	 * 	returns the complexity of the block
	 */
	public Complexity getBlockComplexity() {
		return blockComplexity;
	}
	
	/*
	 * @return
	 * 	returns the complexity of the highest order block nested within this block
	 */
	public Complexity getHighestSubComplexity() {
		return highestSubComplexity;
	}
	
	/*
	 * @return
	 * 	returns the loop variable(should be null unless while block)
	 */
	public String getLoopVariable() {
		return loopVariable;
	}
	
	/*
	 * @return
	 * 	returns the number of indents for the start of the block 
	 */
	public int getIndents() {
		return indents;
	}
	
	/*
	 * @return
	 * 	returns the block type
	 */
	public int getType() {
		return type;
	}
	
	/*
	 * @return
	 * 	returns the number of direct sub blocks
	 */
	public int getNumSubs() {
		return numSubs;
	}
	
	/*
	 * sets the name of the block
	 * 
	 * @param n
	 * 	the name of the block
	 */
	public void setName(String n) {
		name = n;
	}
	
	/*
	 * sets the complexity of the current block
	 * 
	 * @param c
	 * 	the complexity of the current block
	 */
	public void setBlockComplexity(Complexity c) {
		blockComplexity = c;
	}
	
	/*
	 * sets the complexity of the highest order block nested in this block
	 * 
	 * @param c
	 * 	the complexity of the highest order block within this one
	 */
	public void setHighestSubComplexity(Complexity c) {
		highestSubComplexity = c;
	}
	
	/*
	 * sets the loop variable used in this block
	 * 
	 * @param v
	 * 	the variable used in the loop(if any)
	 */
	public void setLoopVariable(String v) {
		loopVariable = v;
	}
	
	/*
	 * sets the number of indents for the block
	 * 
	 * @param i
	 *  the number of indents
	 */
	public void setIndents(int i) {
		indents = i;
	}
	
	/*
	 * adds one to the number of sub blocks
	 */
	public void addSubBlock() {
		numSubs++;
	}
}
