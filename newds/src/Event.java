public class Event {
    private String name;
    private String date;
    private String id;
    private String venue;
    private String description;
    private double fees;
    private int capacity;

    // Constructor
    public Event(String name, String date, String id, String venue, String description, double fees, int capacity) {
        this.name = name;
        this.date = date;
        this.id = id;
        this.venue = venue;
        this.description = description;
        this.fees = fees;
        this.capacity = capacity;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getVenue() {
        return venue;
    }

    public String getDescription() {
        return description;
    }

    public double getFees() {
        return fees;
    }

    public int getCapacity() {
        return capacity;
    }

    // Setter for capacity
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
