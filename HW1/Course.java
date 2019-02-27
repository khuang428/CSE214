/*
 * Karen Huang
 * 111644515
 * 
 * The Course class is used to represent one course in the list of courses in the Planner class
 */
public class Course {
	private String courseName;
	private String department;
	private String instructor;
	private int code;
	private byte section;
	
	/*
	 * default constructor
	 */
	public Course() {
	}
	
	/* constructor with all variables given
	 * 
	 * @param cn
	 * 	new course name
	 * @param d
	 * 	new department
	 * @param i
	 * 	new instructor
	 * @param c
	 * 	new course code
	 * @param s
	 * 	new course section
	 */
	public Course(String cn, String d, String i, int c, byte s) {
		courseName = cn;
		department = d;
		instructor = i;
		code = c;
		section = s;
	}
	/* Creates a clone of the course
	 * 
	 * @return
	 * 	returns a copy of the course
	 */
	public Object clone() {
		Course ret = new Course();
		ret.setCourseName(courseName);
		ret.setDepartment(department);
		ret.setInstructor(instructor);
		ret.setCode(code);
		ret.setSection(section);
		return ret;
	}
	
	/* Checks if the object given is the same as the course
	 * 
	 * @param obj
	 * 	the object being compared to the course
	 * @return
	 * 	returns true if obj is the same as the course calling this method, false if not
	 */
	public boolean equals(Object obj) {
		Course comp = (Course)obj;
		if(comp.getCourseName().equals(courseName)
		  && comp.getDepartment().equals(department)
		  && comp.getInstructor().equals(instructor)
		  && comp.getCode() == code
		  && comp.getSection() == section) {
			return true;
		}
		return false;
	}
	
	/*
	 * @return
	 * 	returns the course name
	 */
	public String getCourseName() {
		return courseName;
	}
	
	/*
	 * @return
	 * 	returns the course's department
	 */
	public String getDepartment() {
		return department;
	}
	
	/*
	 * @return
	 * 	returns the course instructor
	 */
	public String getInstructor() {
		return instructor;
	}
	
	/*
	 * @return
	 * 	returns the course's code
	 */
	public int getCode() {
		return code;
	}
	
	/*
	 * @return
	 * 	returns the course section
	 */
	public byte getSection() {
		return section;
	}

	/*  
	 * @param cn
	 * 	the new course name
	 */
	public void setCourseName(String cn) {
		courseName = cn;
	}
	
	/*
	 * @param d
	 * 	the new course department
	 */
	public void setDepartment(String d) {
		department = d;
	}
	
	/*
	 * @param i
	 * 	the new course instructor
	 */
	public void setInstructor(String i) {
		instructor = i;
	}
	
	/*
	 * @param c
	 * 	the new course code
	 * Precondition: c must be above 0
	 * @exception IllegalArgumentException
	 * 	indicates that c is 0 or less
	 */
	public void setCode(int c) throws IllegalArgumentException{
		if(c <= 0) {
			throw(new IllegalArgumentException("Number must be above 0"));
		}
		code = c;
	}
	
	/*
	 * @param s
	 * 	the new course section
	 * Precondition: s must be above 0
	 * @exception IllegalArgumentException
	 * 	indicates that s is 0 or less
	 */
	public void setSection(byte s) throws IllegalArgumentException{
		if(s <= 0) {
			throw(new IllegalArgumentException("Number must be above 0"));
		}
		section = s;
	}
	
	/*
	 * @return
	 * 	returns a string representation of the course
	 */
	public String toString() {
		return String.format("%-25s%-10s  %-5d%02d      %-25s", courseName, department, code,section, instructor);
	}
}
