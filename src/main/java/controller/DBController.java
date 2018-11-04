package controller;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.io.*;
import java.util.Properties;
import java.util.logging.*;

public class DBController {

    private final static String DATABASE_NAME = "cbooking";

    private final static String CONFIG_FILE = "config/db.config";

    private String URI;

    private String configURI;

    private String configPass;

    private String configUsername;

    public DBController() {
        readConfig();
        makeURI();
    }

    public MongoDatabase getDataBase() {
        final MongoClient mongoClient = new MongoClient(new MongoClientURI(URI));
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    public String getURI() {
        return URI;
    }

    private void readConfig() {
        Properties prop = new Properties();
        try {
            InputStream is = new FileInputStream(CONFIG_FILE);
            try {
                prop.load(is);
                configURI = prop.getProperty("db.URI");
                configPass = prop.getProperty("db.PASSWORD");
                configUsername = prop.getProperty("db.USERNAME");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find configuration database file " + CONFIG_FILE);
        }
    }

    private void makeURI() {
        if (configURI!=null && configPass!=null && configUsername!=null) {
            URI = configURI.replaceAll("<USERNAME>", configUsername).replaceAll("<PASSWORD>", configPass);
        }
    }

}