package dao;

import models.Movie.Movie;
import models.Review;
import managers.MovieManager;
import managers.UserManager;

import java.sql.*;

public class ReviewDAO extends DAO {
    public ReviewDAO() throws ClassNotFoundException {
        super();
    }

    public Review getReviewById(int id) throws SQLException {
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM review WHERE id=" + id);

            if(rs.next())
            return extractReviewFromSet(rs);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public boolean insertReview(Review review) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO review(movie_id, user_id, review_text) " +
                    "VALUES (?, ?, ?)");

            ps.setInt(1, review.getMovie().getId());
            ps.setInt(2, review.getUser().getId());
            ps.setNString(3, review.getText());

            return ps.executeUpdate() == 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public Review extractReviewFromSet(ResultSet rs) {
        Review review = new Review();

        try {
            review.setId(rs.getInt("Id"));

            Movie movie = MovieManager.getInstance().getFilmById(rs.getInt("movie_id"));

            if (movie == null)
                movie = MovieManager.getInstance().getSeriesById(rs.getInt("movie_id"));


            review.setMovie(movie);
            review.setUser(UserManager.getInstance().getCustomerFromDB(rs.getInt("user_id")));
            review.setText(rs.getNString("review_text"));
        }
        catch (Exception ignored) { }

        return review;
    }
}
