package bl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import model.*;

/**
 * This is a singleton class aimed to act like an in-memory mailing events list,
 * allowing events to be appended and exported to CSV upon demand.

 * @author Shiri Rave
 * @date 13/05/2025
 */
public class LogEventsList {

    // Singleton instance to hold mailing events
    private static final List<EventLog> mailingEvents = new ArrayList<EventLog>();

    /**
     * Adds a new event log to the mailing events list.
     *
     * @param size the size of the email in kilobytes
     */
    public static void addEventLog(Long size) {
        mailingEvents.add(new EventLog(size));
    }

    /**
     * Method to format the given timestamp fetched, sccording to the csn examples given.
     * @param timeStamp
     * @return the line formatted as string.
     */
    private static String formatCSVDate(String timeStamp){
        StringBuilder sb = new StringBuilder();
        sb.append(timeStamp.replace(" ","T"));
        sb.append("Z;");
        return sb.toString();
    }

    /**
     * Aid method to append a new line to the given csv file
     * @param writer - the FileWriter
     * @param event - the log event for email sent.
     * @throws java.io.IOException
     */
    private static void appendMailLineToWriter(FileWriter writer,EventLog event) throws java.io.IOException{
        writer.append(formatCSVDate(event.getTimestamp().toString()))
            .append(event.getSize().toString())
            .append("\n");
    }

    /**
     * Exports the mailing events to a CSV file at the specified file path.
     *
     * @param filePath the path where the CSV file will be created
     * @return the file path of the created CSV file, or null if an error occurs
     */
    public static String exportEventsToCsv(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append(Constants.CSV_HEADER);
            for (EventLog event : mailingEvents) {
                appendMailLineToWriter(writer,event);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return filePath; // Return the path to the created CSV file
    }
}