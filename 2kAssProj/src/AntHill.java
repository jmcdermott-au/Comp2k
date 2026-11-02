public class AntHill extends Component {
    private static final int SPAWN_INTERVAL_TICKS = 10;

    private final GameWorld world;
    private final EntityFactory factory;
    private int ticksUntilSpawn = SPAWN_INTERVAL_TICKS;

    public AntHill(Entity entity, GameWorld world, EntityFactory factory) {
        super(entity);
        this.world = world;
        this.factory = factory;
    }

    @Override
    public void Behaviour(Event e) {
        if (!e.ID.equals("TickUpdate")) {
            return;
        }

        ticksUntilSpawn--;
        if (ticksUntilSpawn > 0) {
            return;
        }

        cPosition hillPosition = self.getComponent(cPosition.class);
        if (hillPosition == null) {
            throw new IllegalStateException("An ant hill must have a cPosition");
        }

        world.spawn(factory.create(
            EntityFactory.Type.ANT,
            hillPosition.getX(),
            hillPosition.getY()
        ));
        ticksUntilSpawn = SPAWN_INTERVAL_TICKS;
    }
}
