package model;

import com.mongodb.MongoWriteConcernException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuditoriumDAO {

    private MongoDatabase cBookingDatabase;

    public AuditoriumDAO(final MongoDatabase cBookingDatabase) {
        this.cBookingDatabase = cBookingDatabase;
    }

    public Auditorium getAuditorium(String auditoriumID) {
        Document requiredAuditoriumDocFilter = new Document().append("_id", auditoriumID); // reading only one document with specific _id
        MongoCollection<Document> auditoriumCollection = getCollection("auditorium");
        List<Document> docList = auditoriumCollection.find(requiredAuditoriumDocFilter).into(new ArrayList<Document>());
        Document requiredDoc = docList.get(0); // returned only one document with unique ID
        String auditoriumName = "";
        int maxCapacity = 0;
        if (requiredDoc.containsKey("auditorium_name")) {
            auditoriumName = requiredDoc.get("auditorium_name").toString();
        }
        if (requiredDoc.containsKey("max_capacity")) {
            maxCapacity = requiredDoc.getDouble("max_capacity").intValue();
        }
        // reading occupied seats
        Set<Integer> occupiedSeatsSet = new HashSet<>();
        if (requiredDoc.containsKey("occupied_seats")) {
            List<Integer> occupiedSeatsList = (List<Integer>) requiredDoc.get("occupied_seats");
            for(int i=0;i<occupiedSeatsList.size();i++) {
                occupiedSeatsSet.add(occupiedSeatsList.get(i).intValue());
            }
        }
        return new Auditorium(auditoriumID, auditoriumName, maxCapacity, occupiedSeatsSet);
    }

    private MongoCollection<Document> getCollection(String collectionName) {
        return cBookingDatabase.getCollection(collectionName);
    }

    public void bookAuditoriumSeats(Set<Integer> seatsSet, String auditoriumID) {
        MongoCollection<Document> auditoriumCollection = getCollection("auditorium");
        Document requiredDocument = new Document()
                .append("_id", auditoriumID);
        Document updateDoc = new Document()
                .append("$addToSet", new Document("occupied_seats", new Document("$each", seatsSet)));
        try {
            auditoriumCollection.updateOne(requiredDocument, updateDoc);
        }
        catch (MongoWriteConcernException e) {
            e.getWriteResult();
        }
    }

}