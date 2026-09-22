package datalayer;

import domain.Session;
import java.io.IOException;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import domain.Character;

public class ProcessingSession {
    public static void saveSession(Session session) {
        ObjectMapper mapper = new ObjectMapper();
        File levelData = new File("session_data.json");
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(levelData, session);
        } catch (IOException e) {
            System.out.println("Error writing level data to file");
        }
    }

    public static Session loadSession() {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
        File levelData = new File("session_data.json");
        Session session = new Session();
        try {
            session = mapper.readValue(levelData, Session.class);
        } catch (IOException e) {
            session.genLevel();
        }
        return session;
    }
}