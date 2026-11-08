package Components;

import java.util.Objects;

public class Faction extends Component {
    public enum Id {
        ARMED,
        ENEMY
    }

    private final Id id;

    public Faction(Id id) {
        this.id = Objects.requireNonNull(id, "Faction id cannot be null");
    }

    public Id getId() {
        return id;
    }
}
