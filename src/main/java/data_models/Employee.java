package data_models;

import java.util.UUID;

public class Employee extends Person {

	private UUID employeeId;
	private PermisionEnum permission;		// has to be generated
	public Position m_Position;
	public LoginCredentials m_LoginCredentials;

	public Employee(){

	}

}