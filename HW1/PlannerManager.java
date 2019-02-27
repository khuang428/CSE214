/*
 * Karen Huang
 * 111644515
 * 
 * The PlannerManager class provides a prompt menu for planner operations
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class PlannerManager {
	public static void main(String[]args) {
		Planner pl = new Planner();
		Planner b = new Planner(); //backup planner
		Scanner input = new Scanner(System.in); //gets all user input
		while(true) {
			System.out.print("(A) Add Course\n(G) Get Course\n(R) Remove Course\n"
				+ "(P) Print Courses in Planner\n(F) Filter by Department Code\n"
				+ "(L) Look For Course\n(S) Size\n(B) Backup\n(PB)Print Courses in Backup\n"
				+ "(RB)Revert to Backup\n(Q) Quit\n\nEnter a selection: ");
			switch(input.nextLine().toLowerCase()) {
				case("a"):
					System.out.println();
					try {
						System.out.print("Enter course name: ");
						String cn = input.nextLine();
						System.out.print("Enter department: ");
						String d = input.nextLine();
						System.out.print("Enter course code: ");
						int c = input.nextInt();
						input.nextLine();
						System.out.print("Enter course section: ");
						byte s = input.nextByte();
						input.nextLine();
						System.out.print("Enter instructor: ");
						String i = input.nextLine();
						System.out.print("Enter position: ");
						int p = input.nextInt();
						input.nextLine();
						System.out.println();
						pl.addCourse(new Course(cn,d,i,c,s),p);
						System.out.printf("%s %d.%02d successfully added to planner.\n\n",d,c,s);
					}catch(InputMismatchException e) {
						System.out.println("The information input is not valid.\n");
						input.nextLine();
					}catch(FullPlannerException e) {
						System.out.println("The planner is full.\n");
					}catch(IllegalArgumentException e) {
						System.out.println("You cannot add a course to that position.\n");
					}
					break;
				case("g"):
					System.out.println();
					try {
						System.out.print("Enter position: ");
						int p = input.nextInt();
						input.nextLine();
						System.out.print("No. Course Name              Department  Code Section Instructor\n"
								+ "-----------------------------------------------------------------\n");
						pl.printCourse(p);
						System.out.println();
					}catch(IllegalArgumentException e) {
						System.out.println("There is no course there.\n");
					}
					break;
				case("r"):
					System.out.println();
					try {
						System.out.print("Enter position: ");
						int p = input.nextInt();
						input.nextLine();
						System.out.println();
						Course c = pl.getCourse(p);
						pl.removeCourse(p);
						System.out.printf("%s %d.%02d has been successfully removed from the planner.\n\n",
							c.getDepartment(), c.getCode(), c.getSection());
					}catch(IllegalArgumentException e) {
						System.out.println("There is no course there.\n");
					}
					break;
				case("p"):
					System.out.println();
					pl.printAllCourses();
					break;
				case("f"):
					System.out.println();
					System.out.print("Enter department code: ");
					String de = input.nextLine();
					System.out.println();
					Planner.filter(pl, de);
					System.out.println();
					break;
				case("l"):
					System.out.println();
					try {
						System.out.print("Enter course name: ");
						String cn = input.nextLine();
						System.out.print("Enter department: ");
						String d = input.nextLine();
						System.out.print("Enter course code: ");
						int c = input.nextInt();
						input.nextLine();
						System.out.print("Enter course section: ");
						byte s = input.nextByte();
						input.nextLine();
						System.out.print("Enter instructor: ");
						String i = input.nextLine();
						System.out.println();
						Course comp = new Course(cn,d,i,c,s);
						if(!pl.exists(comp)) {
							System.out.println("The course is not in the planner.");
						}else {
							for(int n = 1;n <= pl.size();n++) {
								if(pl.getCourse(n).equals(comp)) {
									System.out.printf("%s %d.%02d is found at position %d.\n\n",d,c,s,n);
								}
							}
						}
					}catch(InputMismatchException e) {
						System.out.println("The information input is not valid.\n");
						input.nextLine();
					}
					break;
				case("s"):
					System.out.println();
					System.out.printf("There are %d courses in the planner.\n\n",pl.size());
					break;
				case("b"):
					System.out.println();
					b = (Planner)pl.clone();
					System.out.println("Created a backup of the current planner.\n");
					break;
				case("pb"):
					System.out.println();
					b.printAllCourses();
					break;
				case("rb"):
					System.out.println();
					pl = (Planner)b.clone();
					System.out.println("Planner successfully reverted to the backup copy.\n");
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
