/*
 * Karen Huang
 * 111644515
 * 
 * The Planner class is used to manage a list of up to 50 courses
 */
public class Planner {
	final int MAX_COURSES = 50;
	private int numCourses = 0; //the number of courses in the planner
	private Course [] courseList; //the list of courses in the planner
	
	/*
	 * returns an empty planner
	 */
	public Planner() {
		courseList = new Course[50];
	}
	
	/*
	 * @return
	 * 	returns the number of courses in the planner
	 * Precondition: the planner must be instantiated
	 */
	public int size() {
		return numCourses;
	}
	
	/* Adds a course to the given position
	 * 
	 * @param newCourse
	 * 	the new course to add to the planner
	 * @param position
	 * 	the position that the course is to be added to
	 * Precondition: the course has been instantiated and 1 <= position < = numCourses + 1
	 * 	and numCourses < MAX_COURSES
	 * Postcondition: the course is in the correct position and all courses with an equal or
	 * 	greater position move back one position
	 * @exception IllegalArgumentException
	 * 	indicates that the position is not in the valid range
	 * @exception FullPlannerException
	 * 	indicates that the planner is full
	 */
	public void addCourse(Course newCourse, int position)
		throws IllegalArgumentException, FullPlannerException{
		if(position < 1 || position > numCourses + 1) {
			throw new IllegalArgumentException("Can't add a course there.");
		}
		if(numCourses == MAX_COURSES) {
			throw new FullPlannerException("The planner is full.");
		}
		if(position == numCourses + 1) {
			courseList[position-1] = newCourse;
		}else {
			for(int pos = numCourses; pos > position - 1;pos--) {
				courseList[pos] = courseList[pos-1];
				//System.out.println(pos);
			}
			courseList[position-1] = newCourse;
		}
		numCourses++;
	}
	
	/*Adds a course to the end of the list
	 * 
	 * @param newCourse
	 * 	the new course to add to the planner
	 * Precondition: the course has been instantiated
	 * Postcondition: the course is in the end of the list
	 * @exception FullPlannerException
	 * 	indicates that the planner is full
	 */
	public void addCourse(Course newCourse) throws FullPlannerException{
		if(numCourses == MAX_COURSES) {
			throw new FullPlannerException("The planner is full.");
		}
		addCourse(newCourse, size()+1);
	}
	
	/* Removes a course from the given position
	 * 
	 * @param position
	 * 	the position in the planner where a course will be removed
	 * Precondition: the planner has been instantiated and 1 <= position < = numCourses
	 * Postcondition: the course at the given position is removed and all courses with
	 * 	an equal or greater position move forward one position
	 * @exception IllegalArgumentException
	 * 	indicates the position is not in range
	 */
	public void removeCourse(int position) throws IllegalArgumentException{
		if(position < 1 || position > numCourses) {
			throw new IllegalArgumentException("Can't remove course from there.");
		}
		for(int pos = position - 1;pos < numCourses;pos++) {
			courseList[pos] = courseList[pos+1];
		}
		courseList[numCourses] = new Course();
		numCourses--;
	}
	
	/* Retrieves the course at the given position
	 * 
	 * @param position
	 * 	the position of the course to retrieve
	 * Precondition: the planner has been instantiated and 1 <= position < = numCourses
	 * @return
	 * 	returns the course at the given position
	 * @exception IllegalArgumentException
	 * 	indicates the position is not in range
	 */
	public Course getCourse(int position) throws IllegalArgumentException{
		if(position < 1 || position > numCourses) {
			throw new IllegalArgumentException("No course exists there.");
		}
		return courseList[position - 1];
	}
	
	/* Prints out all the courses in the planner in a given department
	 * 
	 * @param planner
	 * 	the list of courses to search through
	 * @param department
	 * 	the department code to search by
	 * Precondition: the planner has been instantiated
	 * Postcondition: displays a formatted table of each course with the same preference numbers
	 */
	public static void filter(Planner planner, String department) {
		System.out.print("No. Course Name              Department  Code Section Instructor\n"
				+ "-----------------------------------------------------------------\n");
		for(int i = 0;i < planner.size();i++) {
			if(planner.getCourse(i+1).getDepartment().equals(department)) {
				planner.printCourse(i+1);
			}
		}
	}
	
	/* Checks if a course is in the planner
	 * 
	 * @param course
	 * 	course to look for in the planner
	 * Precondition: both planner and course have been instantiated
	 * @return
	 * 	returns true if course is in the planner, false if not
	 */
	public boolean exists(Course course) {
		for(int i = 0;i < numCourses;i++) {
			if(course.equals(courseList[i])) {
				return true;
			}
		}
		return false;
	}
	
	/* Creates a copy of the planner for backup
	 * Precondition: the planner has been instantiated
	 * @return
	 * 	returns a copy of the planner
	 */
	public Object clone() {
		Planner ret = new Planner();
		for(int i = 0;i < size();i++) {
			try {
				ret.addCourse((Course)courseList[i].clone());
			}catch(FullPlannerException e) {
			}
		}
		return ret;
	}
	
	/* Prints a formatted list of the planner
	 * Precondition: the planner has been instantiated
	 * Postcondition: displays a formatted table of the planner
	 */
	public void printAllCourses() {
		System.out.println(toString());
	}
	
	/* Prints a formatted line for the course at the given position
	 * 
	 * @param position
	 * 	the position of the course to be printed 
	 * Precondition: the planner has been instantiated and 1 <= position < = numCourses
	 * Postcondition: the course is printed out in a formatted line
	 * @exception IllegalArgumentException
	 * 	the position is not in range
	 */
	public void printCourse(int position) throws IllegalArgumentException{
		if(position < 1 || position > numCourses) {
			throw new IllegalArgumentException("No course exists there.");
		}
		System.out.println(position + "   " + courseList[position - 1].toString());
	}
	
	/*
	 * @return
	 * 	returns a string representation of the planner
	 */
	public String toString() {
		String ret = "";
		ret += "No. Course Name              Department  Code Section Instructor\n"
				+ "-----------------------------------------------------------------\n";
		for(int i = 1;i <= numCourses;i++) {
			ret += i + "   " + courseList[i - 1].toString() + "\n";
		}
		return ret;
	}
/*
	public static void main(String[]args) {
		Planner pl = new Planner();
		Course c1 = new Course();
		c1.setCode(220);
		Course c2 = new Course();
		c2.setCode(320);
		c2.setCourseName("STUFF AND THING");
		c2.setDepartment("CSE");
		c2.setInstructor("everyone");
		c2.setSection((byte) 2);
		Course c3 = new Course();
		c3.setCode(214);
		Course c4 = new Course();
		c4.setCode(373);
		try {
			pl.addCourse(c1,1);
			pl.addCourse(c2,2);
			pl.printAllCourses();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
*/
}
