/*
 * Karen Huang
 * 111644515
 * 
 * The DirectoryTree class represents the tree of DirectoryNodes
 */
public class DirectoryTree {
	private DirectoryNode root;
	private DirectoryNode cursor;
	
	/*
	 * default constructor that creates the tree's root node
	 * Postcondition: both root and cursor refer to a node called "root"
	 */
	public DirectoryTree() {
		root = new DirectoryNode("root",false,null);
		cursor = root;
	}
	
	/*
	 * resets the location of the cursor to the root node
	 */
	public void resetCursor() {
		cursor = root;
	}
	
	/*
	 * moves cursor to the directory with the given name
	 * @param name
	 * 	the directory to move to
	 * Precondition: name refers to a valid directory
	 * Postcondition: cursor now references directory with name
	 * @exception NotADirectoryException
	 * 	thrown if name refers to a file
	 * @exception NoSuchDirectoryException
	 * 	thrown if directory is not one of the children
	 */
	public void changeDirectory(String name) throws NotADirectoryException,
													NoSuchDirectoryException{
		if(cursor.getLeft() != null && cursor.getLeft().getName().equals(name)) {
			if(cursor.getLeft().isFile()) {
				throw new NotADirectoryException();
			}else {
				cursor = cursor.getLeft();
				return;
			}
		}
		if(cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)) {
			if(cursor.getMiddle().isFile()) {
				throw new NotADirectoryException();
			}else {
				cursor = cursor.getMiddle();
				return;
			}
		}
		if(cursor.getRight() != null && cursor.getRight().getName().equals(name)) {
			if(cursor.getRight().isFile()) {
				throw new NotADirectoryException();
			}else {
				cursor = cursor.getRight();
				return;
			}
		}
		throw new NoSuchDirectoryException();
	}
	
	/*
	 * Postcondition: cursor remains at the same node
	 * @return
	 * 	returns a string of the absolute path of the node at the cursor
	 */
	public String presentWorkingDirectory() {
		DirectoryNode ptr = cursor;
		String ret = ptr.getName();
		while(ptr.getParent() != null) {
			ret = ptr.getParent().getName() + "/" + ret;
			ptr = ptr.getParent();
		}
		return ret;
	}
	
	/*
	 * Postcondition: cursor remains at the same node
	 * @return
	 * 	returns a string with all the children of the cursor
	 */
	public String listDirectory() {
		String ret = "";
		if(cursor.getLeft() != null) {
			ret += cursor.getLeft().getName()+ " ";
		}
		if(cursor.getMiddle() != null) {
			ret += cursor.getMiddle().getName()+ " ";
		}
		if(cursor.getRight() != null) {
			ret += cursor.getRight().getName();
		}
		return ret;
	}
	
	/*
	 * prints a formatted nested list of names of all the nodes in the directory tree from the cursor
	 * Postcondition: cursor remains at the same node
	 */
	public void printDirectoryTree() {
		printHelper(cursor,0);
	}
	
	/*
	 * helper function for printDirectoryTree
	 * @param ptr
	 * 	temporary cursor
	 * @param indents
	 * 	number of indents for nesting
	 */
	public void printHelper(DirectoryNode ptr, int indents) {
		if(ptr == null) {
			return;
		}
		for(int i = 0;i < indents;i++) {
			System.out.print("   ");
		}
		if(ptr.isFile()) {
			System.out.println("-" + ptr.getName());
		}else {
			System.out.println("|-" + ptr.getName());
			printHelper(ptr.getLeft(),indents + 1);
			printHelper(ptr.getMiddle(),indents + 1);
			printHelper(ptr.getRight(),indents + 1);
		}
	}
	
	//for the following 2 methods, assuming no name repetition
	
	/*
	 * creates a directory with the given name and adds it to the children of the cursor
	 *
	 * @param name
	 * 	the name of the directory to add
	 * Precondition: name is a legal argument
	 * Postcondition: directory is added as a child of the cursor
	 * @exception IllegalArgumentException
	 * 	thrown if name is invalid
	 * @exception FullDirectoryException
	 * 	thrown if all child references of cursor are occupied
	 */
	public void makeDirectory(String name) throws IllegalArgumentException,
												  FullDirectoryException{
		if(name.contains(" ") || name.contains("/")) {
			throw new IllegalArgumentException();
		}
		try {
				cursor.addChild(new DirectoryNode(name,false,cursor));
		}catch(FullDirectoryException e) {
			throw new FullDirectoryException();
		} catch (NotADirectoryException e) {
			//this won't ever catch wtf
			//cursor can't even point to a file in the first place why is this needed
		}
		
	}
	
	/*
	 * create a file with the given name and adds it to the children of the cursoe
	 * 
	 * @param name
	 * 	the name of the file to add
	 * Precondition: name is a legal argument
	 * Postcondition: file is added as a child of the cursor
	 * @exception IllegalArgumentException
	 * 	thrown if name is invalid
	 * @exception FullDirectoryException
	 * 	thrown if all child references of cursor are occupied
	 */
	public void makeFile(String name) throws IllegalArgumentException,
	  										 FullDirectoryException{
		if(name.contains(" ") || name.contains("/")) {
			throw new IllegalArgumentException();
		}
		try {
				cursor.addChild(new DirectoryNode(name,true,cursor));
		}catch(FullDirectoryException e) {
			throw new FullDirectoryException();
		} catch (NotADirectoryException e) {
			
		}
		
	}
}
