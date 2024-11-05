import java.util.ArrayList;

public class EventManager {
    private static ArrayList<Event> eventsList = new ArrayList<>();

    public static void addEvent(Event event) {
        eventsList.add(event);
    }

    public static ArrayList<Event> getEvents() {
        return new ArrayList<>(eventsList); // Return a copy of the list to avoid modification
    }
}
