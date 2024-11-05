// controllers/CampusEventManager.java
import java.util.ArrayList;
import java.util.List;

public class CampusEventManager {
    private List<Event> events;

    public CampusEventManager() {
        events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
        // Notify user interface to update event list
    }

    public void deleteEvent(String eventId) {
        events.removeIf(event -> event.getId().equals(eventId));
        // Notify user interface to update event list
    }

    public List<Event> getEvents() {
        return events; // Return current events for display
    }

    public Event getEventById(String eventId) {
        for (Event event : events) {
            if (event.getId().equals(eventId)) {
                return event;
            }
        }
        return null; // Event not found
    }
}
