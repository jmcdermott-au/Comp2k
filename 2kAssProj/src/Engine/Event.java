package Engine;
import java.util.LinkedHashMap;

public class Event {
    public String ID;
    public LinkedHashMap<String, Object> Parameters;
    private boolean propagationStopped;

    public Event() {
        Parameters = new LinkedHashMap<>();
    }

    public Event with(String key, Object value) {
        this.Parameters.put(key, value);
        return this;
    }

    public Event(String ID) {
        Parameters = new LinkedHashMap<>();
        this.ID = ID;
    }

    public void Debug() {
        Parameters.forEach((key, value) -> System.out.println(key + " : " + value)); // cheeky cheeky lil lamba
    }

    public void stopPropagation() {
        propagationStopped = true;
    }

    public boolean isPropagationStopped() {
        return propagationStopped;
    }

    @SuppressWarnings("unchecked")
    public <T> T getParameter(String key, Class<T> type) {
        Object obj = Parameters.get(key);
        if (type.isInstance(obj)) {
            return (T) obj;
        }
        return null; // Or throw an exception depending on your error handling
    }

}
