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

import data_loader.data_access_object.*;
import data_models.Customer;
import data_models.Employee;

import java.util.List;

public class AdministrativeManager {

    public List<Employee> getAllEmployees() {
        return EmployeeDao.getAllEmployeesFromDb();
    }

    public boolean createEmployee(Employee employee) {
        return EmployeeDao.createNewEmployee(employee);
    }

    public boolean updateEmployee(Employee employee){
        return EmployeeDao.updateEmployee(employee);
    }

    public Customer getCustomerById(String uuid) {
        return CustomerDao.getCustomerById(uuid);
    }

    public List<Customer> getAllCustomers() {
        return CustomerDao.getAllCustomersFromDb();
    }
}
