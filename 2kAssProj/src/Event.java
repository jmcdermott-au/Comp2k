import java.util.LinkedHashMap;
public class Event {
String ID;
LinkedHashMap<String, Object> Parameters; 
public Event()
{
    Parameters = new LinkedHashMap<>();
}

public void Debug()
{
    Parameters.forEach((key, value) -> System.out.println(key + " : " + value));
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
