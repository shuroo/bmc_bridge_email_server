package bl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import model.*;

/**
 * This is a singleton class aimed to act like an in-memory mailing events list, to append and export to csv upon demand.
 */

public class LogEventsList {

    //Singlton
    private static final List<EventLog> mailingEvents = new ArrayList<EventLog>();

    public static void addEventLog(Long size){
        mailingEvents.add(new EventLog(size));
    }

    public static String exportEventsToCsv(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("timestamp_utc;size_kb\n");
            for (EventLog event : mailingEvents) {
                writer.append(event.getTimestamp().toString()).append(";")
                        // todo: check formatting...
                        .append(event.getSize().toString()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return filePath; // Return the path to the created CSV file
    }
}
