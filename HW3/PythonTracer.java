/*
 * Karen Huang
 * 111644515
 * 
 * The PythonTracer class implements a Stack to find the complexity of a python function
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class PythonTracer {
	public static final int SPACE_COUNT = 4;
	
	/*
	 * opens file and traces through the code, returning the big O complexity of the function
	 * 
	 * @param filename
	 * 	the file to open
	 * Precondition: filename is not null and the file contains a Python function with valid syntax
	 * @return
	 * 	returns the Complexity representing the total complexity of the code in the file
	 */
	public static Complexity traceFile(String filename) {
		Stack<CodeBlock> stack = new Stack<CodeBlock>();
		String line;
		String[] splitLine;
		int indents;
		int keyword;
		CodeBlock oldTop;
		Complexity oldTopComplexity;
		try {
			Scanner sc = new Scanner(new File(filename));
			while(sc.hasNextLine()) {
				keyword = -1;
				line = sc.nextLine();
				if(line.trim().length() != 0 && line.trim().charAt(0) != '#') {
					indents = line.indexOf(line.trim()) / SPACE_COUNT;
					while(indents < stack.size()) {
						if(indents == 0) {
							System.out.println("Leaving block 1");
							sc.close();
							return new Complexity(stack.peek().getBlockComplexity().getNPower() + stack.peek().getHighestSubComplexity().getNPower(),
									stack.peek().getBlockComplexity().getLogPower() + stack.peek().getHighestSubComplexity().getLogPower());
						}else {
							oldTop = stack.pop();
							oldTopComplexity = new Complexity(oldTop.getBlockComplexity().getNPower() + oldTop.getHighestSubComplexity().getNPower(),
								oldTop.getBlockComplexity().getLogPower() + oldTop.getHighestSubComplexity().getLogPower());
							System.out.printf("Leaving block %s, ",oldTop.getName());
							if(oldTopComplexity.getNPower() > stack.peek().getHighestSubComplexity().getNPower()) {
								stack.peek().setHighestSubComplexity(oldTopComplexity);
								System.out.printf("updating block %s:\n",stack.peek().getName());
							}else if(oldTopComplexity.getNPower() == stack.peek().getHighestSubComplexity().getNPower() 
								&& oldTopComplexity.getLogPower() > stack.peek().getHighestSubComplexity().getLogPower()) {
								stack.peek().setHighestSubComplexity(oldTopComplexity);
								System.out.printf("updating block %s:\n",stack.peek().getName());
							}else {
								System.out.println("nothing to update.");
							}
							System.out.printf("\tBlock %s:   block complexity = " + stack.peek().getBlockComplexity()
									+"   highest sub-complexity = " + stack.peek().getHighestSubComplexity() + "\n",stack.peek().getName());
							System.out.println();
						}
					}
					splitLine = line.trim().split(" ");
					for(int i = 0;i < 6;i++) {
						if(splitLine[0].equals(CodeBlock.BLOCK_TYPES[i])) {
							keyword = i;
						}
					}
					if(keyword != -1) {
						if(keyword == 1) {
							if(splitLine[splitLine.length - 1].equals("N:")) {
								stack.push(new CodeBlock(new Complexity(1,0),keyword));
							}else if(splitLine[splitLine.length - 1].equals("log_N:")) {
								stack.push(new CodeBlock(new Complexity(0,1),keyword));
							}
						}else if(keyword == 2) {
							stack.push(new CodeBlock(new Complexity(0,0),keyword));
							stack.peek().setLoopVariable(splitLine[1]);
						}else {
							stack.push(new CodeBlock(new Complexity(0,0),keyword));
						}
						if(keyword == 0) {
							stack.peek().setName("1");
						}else {
							CodeBlock naming = stack.pop();
							stack.peek().addSubBlock();
							naming.setName(stack.peek().getName() + "." + stack.peek().getNumSubs());
							stack.push(naming);
						}
						System.out.printf("Entering block %s '%s':\n",stack.peek().getName(),CodeBlock.BLOCK_TYPES[keyword]);
						System.out.printf("\tBlock %s:   block complexity = " + stack.peek().getBlockComplexity()
								+"   highest sub-complexity = " + stack.peek().getHighestSubComplexity() + "\n",stack.peek().getName());
						System.out.println();
					}else if(stack.peek().getType() == 2 && splitLine[0].equals(stack.peek().getLoopVariable())){
						System.out.printf("Found update statement, updating block %s:\n",stack.peek().getName());
						if(splitLine[1].equals("/=")) {
							stack.peek().setBlockComplexity(new Complexity(0,1));
						}else if(splitLine[1].equals("-=")) {
							stack.peek().setBlockComplexity(new Complexity(1,0));
						}
						System.out.printf("\tBlock %s:   block complexity = " + stack.peek().getBlockComplexity()
								+"   highest sub-complexity = " + stack.peek().getHighestSubComplexity() + "\n",stack.peek().getName());
						System.out.println();
					}
				}
			}
			while(stack.size() > 1) {
				oldTop = stack.pop();
				oldTopComplexity = new Complexity(oldTop.getBlockComplexity().getNPower() + oldTop.getHighestSubComplexity().getNPower(),
					oldTop.getBlockComplexity().getLogPower() + oldTop.getHighestSubComplexity().getLogPower());
				System.out.printf("Leaving block %s, ",oldTop.getName());
				if(oldTopComplexity.getNPower() > stack.peek().getHighestSubComplexity().getNPower()) {
					stack.peek().setHighestSubComplexity(oldTopComplexity);
					System.out.printf("updating block %s:\n",stack.peek().getName());
				}else if(oldTopComplexity.getNPower() == stack.peek().getHighestSubComplexity().getNPower() 
					&& oldTopComplexity.getLogPower() > stack.peek().getHighestSubComplexity().getLogPower()) {
					stack.peek().setHighestSubComplexity(oldTopComplexity);
					System.out.printf("updating block %s:\n",stack.peek().getName());
				}else {
					System.out.println("nothing to update.");
				}
				System.out.printf("\tBlock %s:   block complexity = " + stack.peek().getBlockComplexity()
						+"   highest sub-complexity = " + stack.peek().getHighestSubComplexity() + "\n",stack.peek().getName());
				System.out.println();
			}
			System.out.println("Leaving block 1");
			sc.close();
			return new Complexity(stack.peek().getBlockComplexity().getNPower() + stack.peek().getHighestSubComplexity().getNPower(),
					stack.peek().getBlockComplexity().getLogPower() + stack.peek().getHighestSubComplexity().getLogPower());
		}catch(FileNotFoundException e){
			System.out.println("File does not exist.");
		}
		return null;
	}
	
	public static void main(String[]args) {
		Scanner input = new Scanner(System.in);
		Complexity c;
		while(true) {
			System.out.print("Please enter a file name (or 'quit' to quit): ");
			String s = input.nextLine();
			if(s.equals("quit")) {
				input.close();
				System.exit(0);
			}
			c = traceFile(s);
			System.out.println();
			if(c != null) {
				System.out.println("Overall complexity of " + s + ": " + c);
				System.out.println();
			}
		}
	}
}
