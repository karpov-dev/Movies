package factories;

import constants.Constants;
import models.Movie.Film;
import models.Movie.Series;
import models.Movie.Style;

public class MovieFactory {
    private static int idCounter = Constants.ID_COUNTER_START;

    public Film createFilm(String name, String description, Style style, int time) {
        Film newFilm = new Film(name, description, style, time);
        newFilm.setId(idCounter++);

        return newFilm;
    }

    public Series createSeries(String name, String description, Style style, int seasons, int episodes, int perEpisodeTime) {
        Series newSeries = new Series(name, description, style, seasons, episodes, perEpisodeTime);
        newSeries.setId(idCounter++);

        return newSeries;
    }
}
