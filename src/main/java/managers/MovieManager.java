package managers;

import dao.MovieDAO;
import models.Mark;
import models.Movie.Film;
import models.Movie.Movie;
import models.Movie.Series;
import models.Movie.Style;
import models.User.Customer;

import java.util.ArrayList;

public class MovieManager {
    public static MovieManager instance;
    MovieDAO dao;

    public static MovieManager getInstance() {
        if(instance == null) instance = new MovieManager();
        return instance;
    }

    private MovieManager() {
        try {
            dao = MovieDAO.getInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Mark> getMarks(Movie movie) {
        return dao.getMarks(movie);
    }

    public void rateMovie(Movie movie, Customer customer, float rate) {
        ArrayList<Mark> marks = getMarks(movie);
        float totalRate = movie.getRate() * marks.size();
        movie.setRate((totalRate + rate) / marks.size());

        addMark(new Mark(customer, movie, rate));

        if(movie.getClass() == Film.class)
            dao.updateFilm((Film) movie);
        else dao.updateSeries((Series) movie);
    }

    private void addMark(Mark mark) {
        dao.addMark(mark);
    }

    private void autoCustomerRate(Movie movie) throws ClassNotFoundException {
        UserManager us = UserManager.getInstance();
        for (Mark mark: dao.getMarks(movie)
             ) {
            float markValue = mark.getMark();
            if(markValue <= movie.getRate() + 0.3f
            && markValue >= movie.getRate() - 0.3f)
                us.increaseCustomerRate(mark.getCustomer(), 0.1f);
            else us.increaseCustomerRate(mark.getCustomer(), -0.1f);
        }
    }

    public Film createFilm(String name, String description, Style style, int time) {
        Film newFilm = new Film(name, description, style, time);
        addFilmToBase(newFilm);
        return  newFilm;
    }

    public boolean addFilmToBase(Film film) {
        return dao.insertFilm(film);
    }

    public Series createSeries(String name, String description, Style style
            , int seasons, int episodes, int perEpisodeTime ) {
        Series newSeries = new Series(name, description, style, seasons, episodes, perEpisodeTime);
        addSeriesToBase(newSeries);
        return newSeries;
    }

    public boolean addSeriesToBase(Series series) {
        return dao.insertSeries(series);
    }

    public Film getFilmById(int id) {
        return dao.getFilmById(id);
    }

    public Series getSeriesById(int id) {
        return dao.getSeriesById(id);
    }

}
