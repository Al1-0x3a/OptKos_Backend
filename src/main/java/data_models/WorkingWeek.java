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

public class WorkingWeek {

	private List<WorkingDay> workingDays;

	public WorkingWeek(){
		workingDays = new ArrayList<>();
		workingDays.add(new WorkingDay("Montag"));
		workingDays.add(new WorkingDay("Dienstag"));
		workingDays.add(new WorkingDay("Mittwoch"));
		workingDays.add(new WorkingDay("Donnerstag"));
		workingDays.add(new WorkingDay("Freitag"));
		workingDays.add(new WorkingDay("Samstag"));
		workingDays.add(new WorkingDay("Sonntag"));
	}

	public List<WorkingDay> getWorkingDays() {
		return workingDays;
	}

	public WorkingDay getWorkingDayByIndex(int index){
		return workingDays.get(index);
	}

	public void setWorkingDays(List<WorkingDay> workingDays) {
		this.workingDays = workingDays;
	}

}