package managers;

import dao.ReviewDAO;
import models.Movie.Movie;
import models.Review;
import models.User.Customer;

import java.sql.SQLException;

public class ReviewManager {

    ReviewDAO dao;
    private static ReviewManager instance;

    public static ReviewManager getInstance() {
        if(instance == null)
            instance = new ReviewManager();
        return instance;
    }


    private ReviewManager() {
        try {
            dao = new ReviewDAO();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Review createReview(Customer customer, Movie movie, String text) {
        Review newReview = new Review(text, movie, customer);

        addToBase(newReview);

        return newReview;
    }

    public Review getReviewById(int id) {
        try {
            return dao.getReviewById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean addToBase(Review review) {
        return dao.insertReview(review);
    }



}
