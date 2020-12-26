package ServiceTest;

import models.Mark;
import models.User.Customer;
import models.User.Status;
import managers.UserManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserServiceTest {
    UserManager us;

    @Before
    public void setup() throws ClassNotFoundException {
        us = UserManager.getInstance();
    }

    @Test
    public void userCreate_Test() {
        Customer customer = us.createCustomer("Name", "Surname", 16);

        Assert.assertNotNull(customer);
    }

    @Test
    public void addToDB_Test() {
        Customer customer = us.createCustomer("Name3", "Surname3", 23);

        boolean isAdded = us.addCustomerToBase(customer);

        Assert.assertTrue(isAdded);
    }

    @Test
    public void increaseRate_Test()  {
        Customer customer = null;
        float before = 0;
        try {
            customer = us.getCustomerFromDB(1);
            before = customer.getRate();
            us.increaseCustomerRate(customer, 0.2f);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Assert.assertEquals((before + 0.2f), customer.getRate(), 0.0);
    }

    @Test
    public void banCustomer_Test() {
        Customer customer = null;
        float before = 0;
        try {
            customer = us.getCustomerFromDB(1);
            us.banCustomer(customer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Assert.assertSame(customer.getStatus(), Status.BANNED);
    }

    @Test
    public void unbanCustomer_Test() {
        Customer customer = null;
        float before = 0;
        try {
            customer = us.getCustomerFromDB(1);
            us.unbanCustomer(customer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Assert.assertSame(customer.getStatus(), Status.ACTIVE);
    }

    @Test
    public void getMarks_Test() {
        Customer customer = null;
        try {
            customer = us.getCustomerFromDB(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ArrayList<Mark> marks = us.getMarks(customer);

        Assert.assertEquals(marks.size(), 3);
    }
}
