package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Contains information about the User object
 * 
 * @author Samantha Ryan
 *
 */
public abstract class User {

	/** First Name of Student */
	private String firstName;
	/** Last name of Student */
	private String lastName;
	/** Student's id */
	private String id;
	/** Student's email address */
	private String email;
	/** Student's password */
	private String password; 

	/**
	 * Constructs a user object based on firstName, lastName, id, email, and
	 * password
	 * 
	 * @param firstName
	 *            first name of the User
	 * @param lastName
	 *            last name of the User
	 * @param id
	 *            id of the User
	 * @param email
	 *            email address of the User
	 * @param password
	 *            password of the User
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email); 
		setPassword(password);
	}

	/**
	 * Returns the Student's first name.
	 * 
	 * @return firstName of Student
	 */
	public String getFirstName() {
		return firstName;
	} 

	/**
	 * Returns the Student's last name.
	 * 
	 * @return lastName of Student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the Student's ID.
	 * 
	 * @return id of Student
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the Student's email.
	 * 
	 * @return email of Student
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the password of the Student
	 * 
	 * @return hashPW of Student
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the Student's first name to a String parameter.
	 * 
	 * @param firstName
	 *            the firstName to set
	 * @throws IllegalArgumentException
	 *             if parameter is null or empty String
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.equals("")) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the Student's last name to a String parameter.
	 * 
	 * @param lastName
	 *            the lastName to set
	 * @throws IllegalArgumentException
	 *             if parameter is null or empty String
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.equals("")) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Sets the Student's unique ID to a String parameter.
	 * 
	 * @param id
	 *            the id to set
	 * @throws IllegalArgumentException
	 *             if parameter is null or empty String
	 */
	public void setId(String id) {
		if (id == null || id.equals("")) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Sets the Student's email to a String parameter.
	 * 
	 * @param email
	 *            the email to set
	 * @throws IllegalArgumentException
	 *             if the parameter is null, empty String, does not contain
	 *             exactly one "@" symbol, or does not contain a "." after the
	 *             "@".
	 */
	public void setEmail(String email) {
		// Check that parameter is not null or empty//
		if (email == null || email.equals("")) {
			throw new IllegalArgumentException("Invalid email");
		}

		// Check that parameter contains a "." //
		if (!email.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		}

		// Check that parameter contains exactly one "@" symbol //
		int atCount = 0;
		for (int i = 0; i < email.length(); i++) {
			if (email.charAt(i) == '@') {
				atCount++;
			}
		}
		if (atCount != 1) {
			throw new IllegalArgumentException("Invalid email");
		}

		// Check that the index of the last "." is not before the "@"//
		if (email.indexOf('@') > email.lastIndexOf('.')) {
			throw new IllegalArgumentException("Invalid email");
		}
		this.email = email;
	}

	/**
	 * Sets the Student's password to a String parameter.
	 * 
	 * @param password
	 *            the password to set
	 * @throws IllegalArgumentException
	 *             if parameter is null or empty String
	 */
	public void setPassword(String password) {
		if (password == null || password.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/*
	 * generates hashCode for the User
	 * 
	 * @return hashCode of the user
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/**
	 * Returns true if the two objects are equals, false otherwise
	 * 
	 * @return true if this object is equal to a second object on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}