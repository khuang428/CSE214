/*
 * Karen Huang
 * 111644515
 * 
 * The BashTerminal class simulates a basic Unix bash terminal
 */

import java.util.Scanner;

public class BashTerminal {
	public static void main(String[]args) {
		Scanner input = new Scanner(System.in);
		DirectoryTree dt = new DirectoryTree();
		String[]cmd;
		System.out.println("Starting bash terminal.");
		while(true) {
			System.out.print("[karhuang@insertpunhere]: $");
			cmd = input.nextLine().trim().replaceAll("\\s+", " ").split(" ");
			if(cmd.length == 1) {
				switch(cmd[0].toLowerCase()) {
					case "exit":
						System.out.println("Program terminating normally.");
						input.close();
						System.exit(0);
						break;
					case "ls":
						System.out.println(dt.listDirectory());
						break;
					case "pwd":
						System.out.println(dt.presentWorkingDirectory());
						break;
					default:
						System.out.println("Invalid command");
				}
			}else if(cmd.length == 2){
				switch(cmd[0].toLowerCase()) {
					case "cd":
						if(cmd[1].equals("/")) {
							dt.resetCursor();
						}else {
							try {
								dt.changeDirectory(cmd[1]);
							}catch(NotADirectoryException e) {
								System.out.println("ERROR: Cannot change directory into a file.");
							}catch(NoSuchDirectoryException e) {
								System.out.printf("ERROR: No such directory named '%s'.\n",cmd[1]);
							}
						}
						break;
					case "mkdir":
						try {
							dt.makeDirectory(cmd[1]);
						}catch(IllegalArgumentException e) {
							System.out.println("ERROR: Invalid name.");
							//also won't catch because of umbrella error "invalid command" woops
						} catch (FullDirectoryException e) {
							System.out.println("ERROR: Present directory is full.");
						}
						break;
					case "touch":
						try {
							dt.makeFile(cmd[1]);
						}catch(IllegalArgumentException e) {
							System.out.println("ERROR: Invalid name.");
						} catch (FullDirectoryException e) {
							System.out.println("ERROR: Present directory is full.");
						}
						break;
					case "ls":
						if(cmd[1].toLowerCase().equals("-r")) {
							dt.printDirectoryTree();
						}
						break;
					default:
						System.out.println("Invalid command");
				}
			}else {
				System.out.println("ERROR: Invalid command.");
			}
		}
	}
}
