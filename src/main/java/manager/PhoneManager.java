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

package manager;

import data_loader.data_access_object.CustomerDao;
import data_models.Customer;
import data_models.Phone;

public class PhoneManager {

    public Customer getCustomerByPhoneNumber(String phoneNumber){
        Customer customer = null;
        customer = CustomerDao.getCustomerByPhoneNumber(phoneNumber);
        if(customer.getPersonId() == null){
            System.out.println("Unknown Caller " + phoneNumber);
            Customer newCustomer = new Customer();

            /*Setting the PhoneNumber*/
            Phone phone = new Phone();
            phone.setPersonId(newCustomer.getPersonId());
            phone.setNumber(phoneNumber);
            newCustomer.getPhoneList().add(phone);

            return newCustomer;
        }else{
            System.out.println("Incoming call from: " + customer.getFirstname() + " " + customer.getLastname());
            return customer;
        }
    }
}
