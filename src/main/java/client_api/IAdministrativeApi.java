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

package client_api;

import data_models.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IAdministrativeApi {
    @WebMethod
    List<Employee> getEmployees();
    @WebMethod
    boolean createEmployee(Employee employee);
    @WebMethod
    boolean updateEmployee(Employee employee);
    @WebMethod
    Employee getNewEmployee();
    @WebMethod
    Customer getCustomer(String customerId);
    @WebMethod
    List<Customer> getCustomers();
    @WebMethod
    boolean createCustomer(Customer customer);
    @WebMethod
    boolean updateCustomer(Customer customer);
    @WebMethod
    Customer getNewCustomer();
    @WebMethod
    Phone getNewPhone(String personId);
    @WebMethod
    boolean createPhone(Phone phone);
    @WebMethod
    boolean updatePhone(Phone phone);
    @WebMethod
    boolean deletePhone(Phone phone);
    @WebMethod
    Email getNewEmail(String personId);
    @WebMethod
    boolean createEmail(Email email);
    @WebMethod
    boolean updateEmail(Email email);
    @WebMethod
    boolean deleteEmail(Email email);
    @WebMethod
    boolean deleteEmployee(Employee employee);
    @WebMethod
    List<Service> getServices();
    @WebMethod
    Service getServiceById(String uuid);
    @WebMethod
    boolean createService(Service service);
    @WebMethod
    boolean updateService(Service service);
    @WebMethod
    boolean deleteService(Service service);
    @WebMethod
    Service getNewService();
    @WebMethod
    CustomerCategory getCustomerCategoryById(String uuid);
    @WebMethod
    List<CustomerCategory> getAllCustomerCategories();
    @WebMethod
    CustomerCategory getNewCustomerCategory();
    @WebMethod
    boolean createCustomerCategory(CustomerCategory customerCategory);
    @WebMethod
    boolean updateCustomerCategory(CustomerCategory customerCategory);
    @WebMethod
    boolean deleteCustomerCategory(CustomerCategory customerCategory);
    @WebMethod
    List<Position> getAllPositionFromDb();
    @WebMethod
    boolean createServiceDuration(String employeeId, String serviceId, int duration);
    @WebMethod
    List<ServiceEmployeeDuration> getServiceDuration(String employeeId, String serviceId);
    @WebMethod
    boolean deleteServiceDuration(String serviceDurationId);
    @WebMethod
    Customer getCustomerByPhoneNumber(String phoneNumber);
    @WebMethod
    boolean login(String username, String password);
    @WebMethod
    void registerUser(String username, String password);
    Colour getNewColour();
    @WebMethod
    List<Colour> getColours();
    @WebMethod
    void createColour(Colour colour);
    @WebMethod
    void updateColour(Colour colour);
    @WebMethod
    void deleteColour(Colour colour);
    @WebMethod
    ColourMixture getNewColourMixture();
    @WebMethod
    List<ColourMixture> getAllColourMixtures();
    @WebMethod
    void createColourMixture(ColourMixture colourMixture);
    @WebMethod
    void updateColourMixture(ColourMixture colourMixture);
    @WebMethod
    void deleteColourMixture(ColourMixture colourMixture);
    @WebMethod
    CustomerColour getNewCustomerColour();
    @WebMethod
    List<CustomerColour> getAllCustomerColours();
    @WebMethod
    void createCustomerColour(CustomerColour customerColour);
    @WebMethod
    void updateCustomerColour(CustomerColour customerColour);
    @WebMethod
    void deleteCustomerColour(CustomerColour customerColour);
    @WebMethod
    void addNewMixtures(List<ColourMixture> cmList);
}
