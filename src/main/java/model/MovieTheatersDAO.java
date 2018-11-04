package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieTheatersDAO {
    private MongoDatabase cBookingDatabase;

    public MovieTheatersDAO(final MongoDatabase cBookingDatabase) {
        this.cBookingDatabase = cBookingDatabase;
    }

    public List<MovieTheater> getMovieTheatersList() {
        MongoCollection<Document> movieTheatersCollection = getCollection("movie_theaters");
        List<Document> docList = movieTheatersCollection.find().into(new ArrayList<Document>()); // reading entire movie_theaters collection
        List<MovieTheater> reternedList = new ArrayList<>();
        if (docList!=null) {
            // reading all specific fields for every movie theater
            for(Document movieTheaterDocument: docList) {
                String theaterID = "";
                String movieTheaterName = "";
                List<String> auditoriumIDs = new ArrayList<>();
                int auditoriumAmount = 0;
                if (movieTheaterDocument.containsKey("_id")) {
                    theaterID = movieTheaterDocument.get("_id").toString();
                }
                if (movieTheaterDocument.containsKey("movie_theater_name")) {
                    movieTheaterName = (String)movieTheaterDocument.get("movie_theater_name");
                }
                if (movieTheaterDocument.containsKey("auditorium_amount")) {
                    auditoriumAmount = ((Double)movieTheaterDocument.get("auditorium_amount")).intValue();
                }
                // reading auditoriums IDs
                if (movieTheaterDocument.containsKey("auditoriums")) {
                    List<Document> auditoriums = (List<Document>) movieTheaterDocument.get("auditoriums");
                    for(int i=0;i<auditoriums.size();i++) {
                        StringBuilder str = new StringBuilder();
                        str.append(auditoriums.get(i));
                        auditoriumIDs.add(str.toString());
                    }
                }
                // creating and add to returned List new MovieTheater
                if (movieTheaterName!=null & auditoriumAmount!=0 & auditoriumIDs.size()>0) {
                    reternedList.add(new MovieTheater(theaterID,
                                            movieTheaterName,
                                            auditoriumAmount,
                                            null)); // response for reading entire movie_theaters collection
                                                    // doesn't have string names for auditoriums, so it's null (it's bad practice, I know)
                }
            }
        }
        return reternedList;
    }

    public MovieTheater getMovieTheater(String movieTheaterID) {
        Document requiredTheatersDocFilter = new Document().append("_id", new ObjectId(movieTheaterID)); // reading only one document with specific _id
        MongoCollection<Document> movieTheatersCollection = getCollection("movie_theaters");
        List<Document> docList = movieTheatersCollection.find(requiredTheatersDocFilter).into(new ArrayList<Document>());
        Document requiredDoc = docList.get(0); // returned only one document with unique ID
        String theaterID = "";
        String movieTheaterName = "";
        List<String> auditoriumIDs = new ArrayList<>();
        int auditoriumAmount = 0;
        if (requiredDoc.containsKey("_id")) {
            theaterID = requiredDoc.get("_id").toString();
        }
        if (requiredDoc.containsKey("movie_theater_name")) {
            movieTheaterName = (String)requiredDoc.get("movie_theater_name");
        }
        if (requiredDoc.containsKey("auditorium_amount")) {
            auditoriumAmount = ((Double)requiredDoc.get("auditorium_amount")).intValue();
        }
        // reading auditoriums IDs
        if (requiredDoc.containsKey("auditoriums")) {
            List<Document> auditoriums = (List<Document>) requiredDoc.get("auditoriums");
            for(int i=0;i<auditoriums.size();i++) {
                StringBuilder str = new StringBuilder();
                str.append(auditoriums.get(i));
                auditoriumIDs.add(str.toString());
            }
        }
        // reading auditoriums with ID from auditoriumIDs list
        Document requiredAuditoriumDocumentFilter = new Document().
                    append("_id", new Document().
                                        append("$in", auditoriumIDs));
        MongoCollection<Document> auditoriumsCollection = getCollection("auditorium");
        List<Document> auditoriumsWithSpecIDs = auditoriumsCollection.find(requiredAuditoriumDocumentFilter).into(new ArrayList<Document>());
        List<String> auditoriumNamesList = new ArrayList<>();
        Map<String, String> auditoriumNamesMap = new HashMap<>();
        int i = 0;
        for(Document document : auditoriumsWithSpecIDs) {
            auditoriumNamesMap.put(auditoriumIDs.get(i), document.getString("auditorium_name"));
            i++;
        }
        return new MovieTheater(movieTheaterID,
                        movieTheaterName,
                        auditoriumAmount,
                        auditoriumNamesMap);
    }

    private MongoCollection<Document>  getCollection(String collectionName) {
        return cBookingDatabase.getCollection(collectionName);
    }

}