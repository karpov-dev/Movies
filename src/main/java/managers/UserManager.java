package managers;

import dao.UserDAO;
import models.Mark;
import models.User.Customer;
import models.User.Status;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {

    private static UserManager instance;
    UserDAO dao;


    public static UserManager getInstance() throws ClassNotFoundException {
        if(instance == null)
            instance = new UserManager();
        return instance;
    }

    private UserManager() {
        try {
            dao = UserDAO.getInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void banCustomer(Customer customer) {
        customer.setStatus(Status.BANNED);
        // add update in db
        boolean id = dao.updateCustomer(customer);
        if(id){

        }
    }

    public void unbanCustomer(Customer customer) {
        customer.setStatus(Status.ACTIVE);
        // add update in db
        dao.updateCustomer(customer);
    }

    public ArrayList<Mark> getMarks(Customer customer) {
        return dao.getMarks(customer);
    }

    public Customer getCustomerFromDB(int id) throws SQLException {
        return dao.getCustomerById(id);
    }

    public void increaseCustomerRate(Customer customer, float increment){
        customer.setRate(customer.getRate() + increment);
        // update in db
        dao.updateCustomer(customer);
    }

    public Customer createCustomer(String name, String surname, int age) {
        Customer newCustomer = new Customer(name, surname, age);

        addCustomerToBase(newCustomer);

        return newCustomer;
    }

    public boolean addCustomerToBase(Customer customer) {
        return dao.insertCustomer(customer);
    }

    //get users by params
}
