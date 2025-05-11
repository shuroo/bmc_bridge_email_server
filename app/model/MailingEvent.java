package model;
import java.time.LocalDateTime;

/**
 * Class to describe creating an e-mail record in some storage, for later exporting them as csv.
 * In real life, this should be stored in a database or in s3 ( aws bucket ),
 * but here, for simplicity, it is stored in-memory.
 */
public class MailingEvent {

        private Integer size;
        private LocalDateTime timestamp;

        public MailingEvent(Integer size) {
            this.size = size;
            this.timestamp = LocalDateTime.now(); // Automatically sets the current date and time
        }

        public Integer getSize() {
            return size;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
}
