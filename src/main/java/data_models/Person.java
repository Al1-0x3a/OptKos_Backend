package data_models;

import java.util.UUID;

public abstract class Person {

	private String firstname;
	private GenderEnum gender;				// has to be generated
	private String lastname;
	private UUID personId;
	private SalutationEnum salutation;		// has to be generated
	private String title;
	public Address m_Address;
	public Phone m_Phone;
	public Email m_Email;

	public Person(){

	}

}