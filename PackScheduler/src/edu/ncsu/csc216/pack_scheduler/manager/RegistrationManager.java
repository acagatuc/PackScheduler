package edu.ncsu.csc216.pack_scheduler.manager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Contains state and behavior for the RegistrationManager object
 * 
 * @author Samantha Ryan
 *
 */
public class RegistrationManager {

	private static RegistrationManager instance;
	private FacultyDirectory facultyDirectory;
	private CourseCatalog courseCatalog;
	private StudentDirectory studentDirectory;
	private final User registrar;
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	private static final String PW = "Regi5tr@r";
	private static String hashPW;
	/** whether a user is logged in or not */
	boolean isLoggedIn = false;

	// Static code block for hashing the registrar user's password
	{
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(PW.getBytes());
			hashPW = new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	private RegistrationManager() {
		this.courseCatalog = new CourseCatalog();
		this.studentDirectory = new StudentDirectory();
		this.registrar = new Registrar();
		this.facultyDirectory = new FacultyDirectory();

	}

	/**
	 * Checks for and creates and instance of RegistrationManager
	 * 
	 * @return intance of RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Returns the RegistrationManager's course catalog
	 * 
	 * @return courseCatalog of the RegistrationManager
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Returns the RegistrationManater's student directory
	 * 
	 * @return studentDirectory of the RegistrationManager
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Returns the RegistraionManager's faculty directory
	 * 
	 * @return facultyDirectory of the RegistrationManager
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Logs a user into the registration system
	 * 
	 * @param id
	 *            id of user to be logged in
	 * @param password
	 *            password of user to be logged in
	 * @return true if the user is able to be logged in to the system; false if
	 *         otherwise
	 */
	public boolean login(String id, String password) {
		if (isLoggedIn) {
			return false;
		}

		if (registrar.getId().equals(id)) {
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					isLoggedIn = true;
					return true;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		}

		User u = getStudentDirectory().getStudentById(id);
		if (u == null) {
			u = getFacultyDirectory().getFacultyById(id);
			if (u == null) {
				throw new IllegalArgumentException("User doesn't exist.");
			}
		}

		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			String localHashPW = new String(digest.digest());
			if (u.getPassword().equals(localHashPW)) {
				currentUser = u;
				isLoggedIn = true;
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Invalid id or password");
		}
		return false;
	}

	/**
	 * Logs a user out of the system
	 */
	public void logout() {
		currentUser = null;
		isLoggedIn = false;
	}

	/**
	 * Returns the user currently logged in to the system
	 * 
	 * @return currentUser of RegistrationManager
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Clears the information in the registration manager, leaving an empty
	 * course catalog and empty student directory
	 */
	public void clearData() {
		getCourseCatalog().newCourseCatalog();
		getStudentDirectory().newStudentDirectory();
		getFacultyDirectory().newFacultyDirectory();
		isLoggedIn = false;
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c
	 *            Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();
			if (s.canAdd(c) && roll.canEnroll(s)) {
				schedule.addCourseToSchedule(c);
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c
	 *            Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every
	 * course and then resetting the schedule.
	 */
	public void resetSchedule() {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}

	/**
	 * If the registrar is currented loged in, they can add a faculty member to
	 * a course
	 * 
	 * @param c
	 *            course who is getting a faculty member added to it
	 * @param f
	 *            faculty member who is teaching the course
	 * @return true if the faculty member can be added to the course
	 */ 
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if (currentUser != null && currentUser instanceof Registrar) {

			return f.getSchedule().addCourseToSchedule(c);

		} else {
			throw new IllegalArgumentException("Illegal Action");
		}

	}

	/**
	 * The registrar is the only user who can remove a faculty member from the
	 * course from the Registration Manager
	 * 
	 * @param c
	 *            course to be removed
	 * @param f
	 *            faculty from whose schedule course is being removed
	 * @return true if the course can be removed
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if (currentUser != null && currentUser instanceof Registrar) {
			return f.getSchedule().removeCourseFromSchedule(c);
		} else {
			throw new IllegalArgumentException("Illegal Action");
		}
	}

	/**
	 * Only the registrar can rest a faculty member's schedule from the
	 * registraton manager
	 * 
	 * @param f
	 *            facuty whose schedule is being reset
	 */
	public void resetFacultySchedule(Faculty f) {
		if (currentUser == null || !(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		}
		f.getSchedule().resetSchedule();

	}

	/**
	 * Creates a Registrar with hard-coded name, id, email, and password
	 * 
	 * @author Samantha Ryan
	 *
	 */
	private static class Registrar extends User {

		private static final String FIRST_NAME = "Wolf";
		private static final String LAST_NAME = "Scheduler";
		private static final String ID = "registrar";
		private static final String EMAIL = "registrar@ncsu.edu";

		/**
		 * Create a registrar user with the user id of registrar and password of
		 * Regi5tr@r. Note that hard coding passwords in a project is HORRIBLY
		 * INSECURE, but it simplifies testing here. This should NEVER be done
		 * in practice!
		 */
		public Registrar() {
			super(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		}

	}
}