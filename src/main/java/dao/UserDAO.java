package dao;

import models.Mark;
import models.Movie.*;
import models.User.Customer;
import models.User.Role;
import models.User.Status;
import managers.MovieManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO extends DAO {
    private static UserDAO instance;

    public static UserDAO getInstance() throws ClassNotFoundException {
        if(instance == null) instance = new UserDAO();
        return instance;
    }

    private UserDAO() throws ClassNotFoundException {
        super();
    }

    public Customer getCustomerById(int id) throws SQLException {
        try {

            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id=" + id );//+ "AND role="+Role.CUSTOMER);
            Customer cust = null;
            if(rs.next())
            cust = extractCustomerFromSet(rs);
            int fd = cust.getId();
            return cust;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }


    private Customer extractCustomerFromSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();

        customer.setId(rs.getInt("id"));
        customer.setRate(rs.getFloat("rate"));
        customer.setStatus(Status.valueOf(rs.getString("user_status")));
        customer.setAge(rs.getInt("age"));
        customer.setName(rs.getString("first_name"));
        customer.setSurname(rs.getString("surname"));
        customer.setRole(Role.CUSTOMER);

        return customer;
    }

    public ArrayList<Mark> getMarks(Customer customer) {
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM marks_list WHERE user_id="+customer.getId());

            ArrayList<Mark> marks = new ArrayList<Mark>();

            while (rs.next()) {
                Movie movie = MovieManager.getInstance().getFilmById(rs.getInt("movie_id"));
                if(movie == null)
                    movie = MovieManager.instance.getSeriesById(rs.getInt("movie_id"));

                marks.add(new Mark(customer, movie, rs.getFloat("mark")));
            }

            return marks;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new ArrayList<>();
    }

    private Film extractFilmFromSet(ResultSet rs) throws SQLException {
        try {
            MovieDAO dao = MovieDAO.getInstance();
            return dao.getFilmById(rs.getInt("Id"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Series extractSeriesFromSet(ResultSet rs) throws SQLException {
        try {
            MovieDAO dao = MovieDAO.getInstance();
            return dao.getSeriesById(rs.getInt("Id"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean insertCustomer(Customer customer) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users" +
                    "(first_name, surname, rate, user_status, age, role) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getSurname());
            ps.setFloat(3, customer.getRate());
            ps.setString(4, customer.getStatus().toString());
            ps.setInt(5, customer.getAge());
            ps.setString(6, Role.CUSTOMER.name());

            return ps.executeUpdate() == 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
    public boolean updateCustomer(Customer customer) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET first_name=?, " +
                    "surname=?, rate=?, user_status=?, age=? WHERE id=" + customer.getId());

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getSurname());
            ps.setFloat(3, customer.getRate());
            ps.setString(4, customer.getStatus().toString());
            ps.setInt(5, customer.getAge());


            int i = ps.executeUpdate();

            return i == 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
