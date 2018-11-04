package model;

import java.util.List;
import java.util.Map;

public class MovieTheater {

    private final String movieTheaterID;

    private final String movieTheaterName;

    private int auditoriumAmount;

    private List<Auditorium> auditoriumList;

    private Map<String, String> auditoriumNamesMap;

    public MovieTheater(String theaterID, String movieTheaterName, int auditoriumAmount, Map<String, String> auditoriumNamesMap) {
        this.movieTheaterID = theaterID;
        this.movieTheaterName = movieTheaterName;
        this.auditoriumAmount = auditoriumAmount;
        this.auditoriumNamesMap = auditoriumNamesMap;
    }

    public String getMovieTheaterName() {
        return movieTheaterName;
    }

    public int getAuditoriumAmount() {
        return auditoriumAmount;
    }

    public String getMovieTheaterID() {
        return movieTheaterID;
    }

    public Map<String, String> getAuditoriumNamesMap() {
        return auditoriumNamesMap;
    }

}
