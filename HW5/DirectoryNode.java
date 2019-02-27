/*
 * Karen Huang
 * 111644515
 * 
 * The DirectoryNode class represents a single node in the file tree
 */
public class DirectoryNode {
	private String name;
	private DirectoryNode left;
	private DirectoryNode middle;
	private DirectoryNode right;
	private final DirectoryNode parent;
	private final boolean isFile;
	
	/*
	 * constructor with name and whether if file given
	 * @param n
	 * 	the name of the node
	 * @param f
	 * 	whether the new node is a file
	 * @param p
	 * 	the parent of this node
	 */
	public DirectoryNode(String n, boolean f, DirectoryNode p) {
		name = n;
		isFile = f;
		parent = p;
		
	}
	
	/*
	 * @return
	 * 	returns the name of the DirectoryNode
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * @return
	 * 	returns the left child of the node
	 */
	public DirectoryNode getLeft() {
		return left;
	}
	
	/*
	 * @return
	 * 	returns the middle child of the node
	 */
	public DirectoryNode getMiddle() {
		return middle;
	}
	
	/*
	 * @return
	 * 	returns the right child of the node
	 */
	public DirectoryNode getRight() {
		return right;
	}
	
	/*
	 * @return
	 * 	returns the parent node
	 */
	public DirectoryNode getParent() {
		return parent;
	}
	
	/*
	 * @return
	 * 	returns whether the node is a file
	 */
	public boolean isFile() {
		return isFile;
	}
	
	/*
	 * sets the name of the node
	 * @param n	
	 * 	the new name
	 */
	public void setName(String n) {
		name = n;
	}
	
	/*
	 * adds a child to the node in the order left,middle,right if possible
	 * @param newChild
	 * 	the child to be added
	 * Precondition: node is not a file and there is at least one empty child position
	 * Postcondition: newChild added as a child
	 * @exception FullDirectoryException
	 * 	thrown if all child references are occupied
	 * @exception NotADirectoryException
	 * 	thrown if the node is a file
	 */
	public void addChild(DirectoryNode newChild) throws FullDirectoryException,
														NotADirectoryException{
		if(isFile) {
			throw new NotADirectoryException();
		}
		if(left != null) {
			if(middle != null) {
				if(right != null) {
					throw new FullDirectoryException();
				}else {
					right = newChild;
				}
			}else {
				middle = newChild;
			}
		}else {
			left = newChild;
		}
	}
}
