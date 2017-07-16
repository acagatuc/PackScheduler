package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Contains information about the CourseNameValidator Contains functionality to
 * determine whether a course's name is valid or not
 * 
 * @author Samantha Ryan
 *
 */
public class CourseNameValidator {
	private final State letterState = new LetterState();
	private final State numberState = new NumberState();
	private final State suffixState = new SuffixState();
	private final State initialState = new InitialState();

	private State currentState = initialState;

	private boolean validEndState;
	private int letterCount;
	private int digitCount;

	/**
	 * Returns the number of letters in a course name
	 * 
	 * @return letterCount
	 */
	public int getLetterCount() {
		return letterCount;
	}

	/**
	 * Returns the number of digits in a course name
	 * 
	 * @return digitCount
	 */
	public int getDigitCount() {
		return digitCount;
	}

	/**
	 * Contains information about the abstract State class, declares two methods
	 * for onLetter and onDigit, and defines a third method for non-alphanumeric
	 * characters
	 * 
	 * @author Samantha Ryan
	 *
	 */
	abstract class State {
		/**
		 * Handles transition on letter
		 */
		abstract void onLetter() throws InvalidTransitionException;

		/**
		 * Handles transition on digit
		 */
		abstract void onDigit() throws InvalidTransitionException;

		/**
		 * Handles other characters
		 * 
		 * @throws InvalidTransitionException
		 */
		void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}

	/**
	 * Defines the LetterState, which is a State
	 * 
	 * @author Samantha Ryan
	 *
	 */
	private class LetterState extends State {	
		private static final int MAX_PREFIX_LETTERS = 4;

		/** 
		 * If the next character is a letter, it counts the letter and keeps the
		 * state the same; if the max number of letters has been reached, then
		 * an exception is thrown
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			letterCount++;
			if (getLetterCount() > MAX_PREFIX_LETTERS) {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
			currentState = letterState;
		}

		/**
		 * If the next character is a digit, it counts the digit and assigns the
		 * state to numberState
		 */
		@Override
		public void onDigit() {
			currentState = numberState;
			digitCount++;

		}
	}

	/**
	 * Defines the SuffixState, which is a State
	 * 
	 * @author Samantha Ryan
	 *
	 */
	private class SuffixState extends State {

		/**
		 * If the next character is a letter, an exception is thrown
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		/**
		 * If the next character is a digit, an exception is thrown
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");

		}

	}

	/**
	 * Defines the InitialState, which is a State
	 * 
	 * @author Samantha Ryan
	 *
	 */
	private class InitialState extends State {

		/**
		 * If the next character is a letter, the letter is counted and the
		 * state is transitioned into the letterState
		 */
		@Override
		public void onLetter() {
			letterCount++;
			currentState = letterState;
		}

		/**
		 * If the next character is a digit, an error is thrown
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course must start with a letter");
		}
	}

	/**
	 * Defines the NumberState, which is a State
	 * 
	 * @author Samantha Ryan
	 *
	 */
	private class NumberState extends State  {
		private static final int COURSE_NUMBER_LENGTH = 3;

		/**
		 * If the next character is a letter and the course name has 3 digits,
		 * the state is transitioned into the suffixState; if not, an error is
		 * thrown
		 * @throws InvalidTransitionException 
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (getDigitCount() < COURSE_NUMBER_LENGTH) {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
			currentState = suffixState;
		}

		/**
		 * If the next character is a digit and the course name has 3 digits, an
		 * error is thrown; if not, the digit is counted and the state remains
		 * the same
		 * @throws InvalidTransitionException 
		 */
		@Override
		public void onDigit() throws InvalidTransitionException  {
			if (getDigitCount() == COURSE_NUMBER_LENGTH) {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
			digitCount++;
		}
	}

	/**
	 * Determines whether a course name is of a valid format
	 * 
	 * @param a
	 *            name of course to test
	 * @return true if the course name is valid; false if otherwise
	 * @throws InvalidTransitionException
	 *             if a transition error occurs
	 */
	public boolean isValid(String a) throws InvalidTransitionException {
		letterCount = 0;
		digitCount = 0;
		char c;
		for (int k = 0; k < a.length(); k++) {
			c = a.charAt(k);
			if (Character.isLetter(c)) {
				currentState.onLetter();
			} else if (Character.isDigit(c)) {
				currentState.onDigit();
			} else {
				currentState.onOther();
			}
		}
		if (currentState != letterState) {
			validEndState = true;
		}
		currentState = initialState;

		return validEndState;
	}

}
