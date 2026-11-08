package Components;

public class EntityInterest extends Component {
    private final int weight;

    public EntityInterest(int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Entity interest weight must be positive");
        }
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
