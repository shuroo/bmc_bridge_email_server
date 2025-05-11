package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class to describe creating an e-mail record in some storage, for later exporting them as csv.
 * In real life, this should be stored in a database or in s3 ( aws bucket ),
 * but here, for simplicity, it is stored in-memory.
 */
public class EventLog {

        private Long size;
        private LocalDateTime timestamp;

        public EventLog(Long size) {
            this.size = size;
            this.timestamp = LocalDateTime.now(); // Automatically sets the current date and time
        }

        public String getTimestamp(){
            //2025-04-01T07:53:12Z
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return this.timestamp.format(formatter);
        }

        public Long getSize() {
            return size;
        }

    }
