package ServiceTest;

import models.Movie.Movie;
import models.Review;
import models.User.Customer;
import managers.MovieManager;
import managers.ReviewManager;
import managers.UserManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class ReviewServiceTest {
    ReviewManager rs;

    @Before
    public void setup() {
        rs = ReviewManager.getInstance();
    }

    @Test
    public void createReview_Test() {
        Review review = null;
        try {
            Customer customer = UserManager.getInstance().getCustomerFromDB(1);
            Movie movie = MovieManager.getInstance().getFilmById(1);

            review = rs.createReview(customer, movie, "sdfdsfdfsd");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(review);
    }

    @Test
    public void getReview() {
        Review review = rs.getReviewById(1);

        Assert.assertNotNull(review);
    }
}
