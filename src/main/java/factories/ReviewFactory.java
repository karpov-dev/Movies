package factories;

import constants.Constants;
import models.Movie.Movie;
import models.Review;
import models.User.User;

public class ReviewFactory {
    private static int idCounter = Constants.ID_COUNTER_START;

    public Review createReview(String text, Movie movie, User user){
        Review newReview = new Review(text, movie, user);
        newReview.setId(idCounter++);

        return newReview;
    }
}
