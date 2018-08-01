/*
 * MIT License
 *
 * Copyright (c) 2018 Michael Szostak , Ali Kaya , Johannes Börmann, Nina Leveringhaus , Andre` Rehle , Felix Eisenmann , Patrick Handreck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package data_models;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Employee extends Person {

	private String employeeId;
	private char isDeleted;
	private Position position;
	private String colour;
    private List<WorkingDay> workingDays;

	public Employee(){
	    super();
		this.initWorkingDays();
	}
	public Employee(String personId){
		super(personId);
		this.employeeId = UUID.randomUUID().toString();
		this.initWorkingDays();
		this.position = new Position().setDefaultValues();
	}


	public Employee(String employeeId, char isDeleted, Position position, String displayColor) {
		this.employeeId = employeeId;
		this.isDeleted = isDeleted;
		this.position = position;
		this.colour = displayColor;
		this.initWorkingDays();
	}
	public void setColour(String colour){this.colour =colour;}

	public String getColour(){return this.colour;}
	public List<WorkingDay> getWorkingDays() {
		return this.workingDays;
	}

	public void setWorkingDays(List<WorkingDay> workingDays) {
		this.workingDays = workingDays;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public char getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(char isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Employee{" +
				super.toString() +
				"employeeId=" + employeeId +
				", isDeleted=" + isDeleted +
				", positionId=" + position +
				'}';
	}

	private void initWorkingDays(){
		workingDays = new ArrayList<>();
		workingDays.add(new WorkingDay("Montag"));
		workingDays.add(new WorkingDay("Dienstag"));
		workingDays.add(new WorkingDay("Mittwoch"));
		workingDays.add(new WorkingDay("Donnerstag"));
		workingDays.add(new WorkingDay("Freitag"));
		workingDays.add(new WorkingDay("Samstag"));
		workingDays.add(new WorkingDay("Sonntag"));
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Employee employee = (Employee) o;
		return Objects.equals(employeeId, employee.employeeId);
	}

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}