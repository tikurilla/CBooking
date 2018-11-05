package view;

import controller.FreeMarkerTemplateEngine;
import controller.DBController;
import model.Auditorium;
import model.AuditoriumDAO;
import model.MovieTheater;
import model.MovieTheatersDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import spark.ModelAndView;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        DBController dbController = new DBController();
        MovieTheatersDAO movieTheatersDAO = new MovieTheatersDAO(dbController.getDataBase());
        AuditoriumDAO auditoriumDAO = new AuditoriumDAO(dbController.getDataBase());
        // theaters page: reterns list of movie theaters list with links to details for each of them
        get("/theaters", (request, response) -> {
            HashMap<String, Object> attributes = new HashMap<>();
            List<MovieTheater> movieTheaters = movieTheatersDAO.getMovieTheatersList();
            attributes.put("movieTheaters", movieTheaters);
            return new ModelAndView(attributes, "theaters.ftl");
        }, new FreeMarkerTemplateEngine());

        // movie theater page with detailed information
        get("/theater/:movieTheaterID", (request, response) -> {
            String movieTheaterID = request.params(":movieTheaterID");
            MovieTheater movieTheater = movieTheatersDAO.getMovieTheater(movieTheaterID);
            HashMap<String, Object> attributes = new HashMap<>();
            attributes.put("movieTheater", movieTheater);
            return new ModelAndView(attributes, "theater.ftl");
        }, new FreeMarkerTemplateEngine());

        // auditorium page with detailed information
        get("/auditorium/:auditoriumID", (request, response) -> {
            String movieTheaterID = request.params(":auditoriumID");
            Auditorium auditorium = auditoriumDAO.getAuditorium(movieTheaterID);
            auditorium.makeMapFromSet();
            HashMap<String, Object> attributes = new HashMap<>();
            attributes.put("auditorium", auditorium);
            return new ModelAndView(attributes, "auditorium.ftl");
        }, new FreeMarkerTemplateEngine());

        // adding new occupied seats
        post("/checkout/:auditoriumID", (request, response) -> {
            HashMap<String, Object> attributes = new HashMap<>();
            String auditoriumID = request.params(":auditoriumID");
            Set<Integer> occupiedSeats = Auditorium.makeSeatsSetFromQerry(request.body());
            auditoriumDAO.bookAuditoriumSeats(occupiedSeats, auditoriumID);
            attributes.put("message", "Места зарезервированы!");
            return new ModelAndView(attributes, "checkout.ftl");
        }, new FreeMarkerTemplateEngine());
    }

}
