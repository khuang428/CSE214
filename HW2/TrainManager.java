/*
 * Karen Huang
 * 111644515
 * 
 * The TrainManager class provides a prompt menu for manipulating a TrainLinkedList
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class TrainManager {
	public static void main(String[]args) {
		Scanner input = new Scanner(System.in);
		TrainLinkedList tll = new TrainLinkedList();
		while(true) {
			System.out.print("(F) Cursor Forward\n(B) Cursor Backward\n"
					+ "(I) Insert Car After Cursor\n(R) Remove Car At Cursor\n"
					+ "(L) Set Product Load\n(S) Search For Product\n(T) Display Train\n"
					+ "(M) Display Manifest\n(D) Remove Dangerous Cars\n(Q) Quit\n"
					+ "\nEnter a selection: ");
			switch(input.nextLine().toLowerCase()) {
				case("f"):
					System.out.println();
					if(tll.getCursorData() == null) {
						System.out.println("There are no cars on the train.\n");
						break;
					}
					boolean moved = tll.cursorForward();
					if(moved) {
						System.out.println("Cursor moved forward\n");
					}else {
						System.out.println("No next car, cannot move cursor forward\n");
					}
					break;
				case("b"):
					System.out.println();
					if(tll.getCursorData() == null) {
						System.out.println("There are no cars on the train.\n");
						break;
					}
					moved = tll.cursorBackward();
					if(moved) {
						System.out.println("Cursor moved backward\n");
					}else {
						System.out.println("No previous car, cannot move cursor backward\n");
					}
					break;
				case("i"):
					try {
						System.out.println();
						System.out.print("Enter a car length in meters: ");
						double l = input.nextDouble();
						input.nextLine();
						System.out.print("Enter a car weight in ton: ");
						double w = input.nextDouble();
						input.nextLine();
						System.out.println();
						if(l == 0 || w == 0) {
							System.out.println("Length and weight must be over 0.");
							break;
						}else {
							TrainCar tc = new TrainCar(l,w);
							tll.insertAfterCursor(tc);
							System.out.printf("New train car %.1f meters %.1f tons inserted into train.\n\n", l, w);
						}
					}catch(InputMismatchException e) {
						System.out.println("\"The information input is not valid.\n");
						input.nextLine();
					}
					break;
				case("r"):
					System.out.println();
					if(tll.getCursorData() == null) {
						System.out.println("There is nothing to remove.\n");
						break;
					}
					TrainCar tc = tll.removeCursor();
					System.out.println();
					System.out.println("Car successfully unlinked. The following load has been removed from the train:");
					System.out.printf("    Name      Weight (t)     Value ($)   Dangerous\n"
							+ "===================================================\n");
					if(tc.isEmpty()) {
						System.out.println("     Empty           0.0          0.00          NO");
					}else {
						System.out.println(tc.getLoad().toString());
					}
					System.out.println();
					break;
				case("l"):
					try {
						System.out.println();
						if(tll.getCursorData() == null) {
							System.out.println("There is no car to add a load to.\n");
							break;
						}
						System.out.print("Enter a product name: ");
						String n = input.nextLine();
						System.out.print("Enter product weight in tons: ");
						double weight = input.nextDouble();
						if(weight <= 0) {
							System.out.println("Weight must be greater than 0.\n");
							break;
						}
						input.nextLine();
						System.out.print("Enter product value in dollars: ");
						double value = input.nextDouble();
						if(value < 0) {
							System.out.println("Value must be positive.\n");
							break;
						}
						input.nextLine();
						System.out.print("Enter is product dangerous? (y/n): ");
						boolean d;
						String danger = input.next().toLowerCase();
						input.nextLine();
						if(danger.equals("y")) {
							d = true;
						}else if(danger.equals("n")) {
							d = false;
						}else {
							System.out.println("Please enter y or n");
							break;
						}
						System.out.println();
						ProductLoad pl = new ProductLoad(n,weight,value,d);
						tll.setLoad(pl);
						System.out.printf("%.1f tons of %s added to the current car.\n",weight,n);
						System.out.println();
					}catch(InputMismatchException e) {
						System.out.println("The information input is not valid.\n");
						input.nextLine();
					}
					break;
				case("s"):
					System.out.println();
					System.out.print("Enter product name: ");
					String name = input.nextLine();
					System.out.println();
					tll.findProduct(name);
					System.out.println();
					break;
				case("t"):
					System.out.println();
					System.out.println(tll.toString());
					System.out.println();
					break;
				case("m"):
					System.out.println();
					tll.printManifest();
					System.out.println();
					break;
				case("d"):
					System.out.println();
					tll.removeDangerousCars();
					System.out.println("Dangerous cars successfully removed from the train.\n");
					break;
				case("q"):
					System.out.println();
					System.out.println("Program terminating successfully...");
					input.close();
					System.exit(0);
					break;
				default:
					System.out.println("Invalid command.\n");
			}
		}
	}
}
