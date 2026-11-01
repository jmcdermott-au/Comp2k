import java.util.ArrayList;
import java.util.List;

public class Vision extends Component {
    private final List<Entity> entities;
    private final double range;

    public Vision(Entity self, List<Entity> entities, double range) {
        super(self);
        this.entities = entities;
        this.range = range;
    }

    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals("seeFoodReq"))
        {
            Event e1 = new Event();
            e1.ID = "seeFoodResponse";
            List<Entity> edibles = new ArrayList<>();
            for (Entity entity : getVisibleEntities()) 
                {
                Edible ed = null;

                for (Component component : entity.components) 
                    {
                        if (component instanceof Edible edd) 
                            {
                                ed = edd;
                                edibles.add(ed.self);
                            }
                    }
                }
            e1.Parameters.put("edibles", edibles);
            self.SendEvent(e1);
        }
    }



     
    public List<Entity> getVisibleEntities() {
        List<Entity> visible = new ArrayList<>();
        cPosition observerPosition = findPosition(self);

        if (observerPosition == null) {
            return visible;
        }

        double rangeSquared = range * range;

        for (Entity other : entities) {
            if (other == self) {
                continue;
            }

            cPosition otherPosition = findPosition(other);
            if (otherPosition == null) {
                continue;
            }

            long dx = (long) otherPosition.getX() - observerPosition.getX();
            long dy = (long) otherPosition.getY() - observerPosition.getY();

            if (dx * dx + dy * dy <= rangeSquared) {
                visible.add(other);
            }
        }

        return visible;
    }

    private cPosition findPosition(Entity entity) {
        for (Component component : entity.components) {
            if (component instanceof cPosition position) {
                return position;
            }
        }
        return null;
    }
}
