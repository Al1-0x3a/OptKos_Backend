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

public abstract class Person {

    private String firstname;
    private GENDER gender;
    private String lastname;
    private String personId;
    private SALUTATION salutation;
    private TITLE title;
    private Address address;
    private List<Phone> phoneList;
    private List<Email> emailList;

    public enum SALUTATION {
        Herr, Frau
    }

    public enum TITLE {
        Professor, Doktor, Diplom, Senior, Junior, Default
    }

    public enum GENDER {
        W, M
    }


    public Person() {
        phoneList = new ArrayList<>();
        emailList = new ArrayList<>();

        // personId = UUID.randomUUID().toString();
        this.address = new Address(this.personId);
    }

    public Person(String personId) {
        phoneList = new ArrayList<>();
        emailList = new ArrayList<>();
        this.personId = personId;
        this.address = new Address(this.personId);
    }

    public void setPersonId(String personId) {
        if (this.personId == null)
            this.personId = personId;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void addPhoneNumber(Phone phone) {
        this.phoneList.add(phone);
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPersonId() {
        return personId;
    }

    public SALUTATION getSalutation() {
        return salutation;
    }

    public void setSalutation(SALUTATION salutation) {
        this.salutation = salutation;
    }

    public TITLE getTitle() {
        return title;
    }

    public void setTitle(TITLE title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstname='" + firstname + '\'' +
                ", gender=" + gender +
                ", lastname='" + lastname + '\'' +
                ", personId=" + personId +
                ", salutation='" + salutation + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}