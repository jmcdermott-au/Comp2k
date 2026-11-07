package Components;
import Components.Food.Edible;
import Engine.EntityFactory;
import Engine.Event;
import Engine.GameWorld;
import java.util.concurrent.ThreadLocalRandom;

public class Seeds extends Component {
    private static final int MIN_VIABLE_FLOWER_POPULATION = 5;
    private static final int BASE_SOFT_POPULATION_CAP = 10;
    private static final int FLOWERS_PER_FOOD = 2;
    private static final int MAX_FLOWER_POPULATION = 50;

    public Seeds(GameWorld world, EntityFactory factory) {
        this.world = world;
        this.factory = factory;
    }

    private final GameWorld world;
    private final EntityFactory factory;

    @Override
    public boolean wantEvent(String eventId) {
        return eventId.equals("IJustGrewFood");
    }

    @Override
    public void handleEvent(Event e) {
        switch (e.ID) {
            case "IJustGrewFood" -> handleSprouting(e);
        }
    }

    private void handleSprouting(Event e) {
        long flowerPopulation = world.countEntities(entity -> entity.getComponent(Flower.class) != null);
        if (flowerPopulation >= MAX_FLOWER_POPULATION) {
            return;
        }

        if (flowerPopulation >= MIN_VIABLE_FLOWER_POPULATION) {
            long availableFood = world.countEntities(entity -> {
                Edible edible = entity.getComponent(Edible.class);
                return edible != null && !edible.isConsumed();
            });
            long softCap = Math.min(
                    MAX_FLOWER_POPULATION,
                    BASE_SOFT_POPULATION_CAP + availableFood * FLOWERS_PER_FOOD);
            if (flowerPopulation >= softCap) {
                return;
            }

            double spawnChance = (softCap - flowerPopulation)
                    / (double) (softCap - MIN_VIABLE_FLOWER_POPULATION);
            if (ThreadLocalRandom.current().nextDouble() >= spawnChance) {
                return;
            }
        }

        cPosition pos = self.getComponent(cPosition.class);
        if (pos == null) {
            return;
        }

        int spawnX = pos.getX() + ThreadLocalRandom.current().nextInt(-10, 11);
        int spawnY = pos.getY() + ThreadLocalRandom.current().nextInt(-10, 11);
        world.spawn(factory.create(EntityFactory.Type.FLOWER, spawnX, spawnY));
    }

}
