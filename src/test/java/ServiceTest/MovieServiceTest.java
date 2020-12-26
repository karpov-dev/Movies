package ServiceTest;

import models.Mark;
import models.Movie.Film;
import models.Movie.Movie;
import models.Movie.Series;
import models.Movie.Style;
import models.User.Customer;
import managers.MovieManager;
import managers.UserManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class MovieServiceTest {
    MovieManager ms;

    @Before
    public void setup() {
        ms = MovieManager.getInstance();
    }

    @Test
    public void creteFilm_Test() {
        Movie movie = ms.createFilm("Name", "fdsfdsf", Style.ACTION, 120);

        Assert.assertNotNull(movie);
    }

    @Test
    public void createSeries_Test() {
        Movie movie = ms.createSeries("NameSeries", "fdsfds", Style.ACTION,
                3, 15, 45);

        Assert.assertNotNull(movie);
    }

    @Test
    public void addFilmToBase_Test() {
        Movie movie = ms.createFilm("Name", "sdfdsf", Style.ACTION, 120);

        boolean isAdded = ms.addFilmToBase((Film) movie);

        Assert.assertTrue(isAdded);
    }

    @Test
    public void addSeriesToBase_Test() {
        Movie movie = ms.createSeries("NameSeries", "fdsfdsf", Style.ACTION,
                3, 15, 45);

        boolean isAdded = ms.addSeriesToBase((Series) movie);

        Assert.assertTrue(isAdded);
    }

    @Test
    public void rateMovie_Test() {
        Movie movie = ms.getFilmById(1);

        Customer cust = new Customer();
        try {
            cust = UserManager.getInstance().getCustomerFromDB(1);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        ms.rateMovie(movie, cust, 4);


    }

    @Test
    public void getFilmById_Test() {
        Film filmNull = ms.getFilmById(2);
        Film film = ms.getFilmById(1);

        Assert.assertTrue(filmNull == null && film != null);
    }

    @Test
    public void getMarks_Test() {
        Film film = ms.getFilmById(1);

        ArrayList<Mark> marks = ms.getMarks(film);

        Assert.assertEquals(marks.size(), 3);
    }
}
