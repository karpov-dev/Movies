package factories;

import constants.Constants;
import models.User.Admin;
import models.User.Customer;

public class UserFactory {
    private static int idCounter = Constants.ID_COUNTER_START;

    public Admin createAdmin(String name, String surname, int age){
        Admin newAdmin = new Admin(name, surname, age);
        newAdmin.setId(idCounter++);

        return newAdmin;
    }

    public Customer createCustomer(String name, String surname, int age){
        Customer newCustomer = new Customer(name, surname, age);
        newCustomer.setId(idCounter++);

        return newCustomer;
    }

}
