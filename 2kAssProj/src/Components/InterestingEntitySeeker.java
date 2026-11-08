package Components;

import Components.Food.Eater;
import Engine.Entity;
import Engine.Event;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InterestingEntitySeeker extends Component {
    private static final int HANGOUT_RADIUS = 6;
    private static final int MIN_HANGOUT_DISTANCE = 2;

    private Entity target;
    private cPosition hangoutSpot;

    @Override
    public void proposeGoals(Brain brain) {
        Eater eater = self.getComponent(Eater.class);
        Vision vision = self.getComponent(Vision.class);
        cPosition myPosition = self.getComponent(cPosition.class);
        if (eater == null || eater.isHungry() || vision == null || myPosition == null) {
            target = null;
            hangoutSpot = null;
            return;
        }

        List<Entity> visibleEntities = vision.getVisibleEntities().stream()
                .filter(entity -> entity.getComponent(cPosition.class) != null)
                .toList();
        if (!visibleEntities.contains(target) || reachedHangoutSpot(myPosition)) {
            target = chooseTarget(visibleEntities);
            hangoutSpot = target == null
                    ? null
                    : chooseHangoutSpot(target.getComponent(cPosition.class), myPosition);
        }
        if (target == null || hangoutSpot == null) {
            return;
        }

        brain.proposeGoal(
                "hangOutNearEntity",
                10,
                new Event("IwannaMoveHere").with("location", hangoutSpot));
    }

    private Entity chooseTarget(List<Entity> candidates) {
        long totalWeight = candidates.stream().mapToLong(this::interestWeight).sum();
        if (totalWeight == 0) {
            return null;
        }

        long selection = ThreadLocalRandom.current().nextLong(totalWeight);
        for (Entity candidate : candidates) {
            selection -= interestWeight(candidate);
            if (selection < 0) {
                return candidate;
            }
        }
        throw new IllegalStateException("Could not select an entity by interest weight");
    }

    private int interestWeight(Entity entity) {
        EntityInterest interest = entity.getComponent(EntityInterest.class);
        return interest == null ? 1 : interest.getWeight();
    }

    private boolean reachedHangoutSpot(cPosition position) {
        return hangoutSpot != null
                && hangoutSpot.getX() == position.getX()
                && hangoutSpot.getY() == position.getY();
    }

    private cPosition chooseHangoutSpot(cPosition targetPosition, cPosition myPosition) {
        for (int attempt = 0; attempt < 100; attempt++) {
            int offsetX = ThreadLocalRandom.current().nextInt(
                    -HANGOUT_RADIUS, HANGOUT_RADIUS + 1);
            int offsetY = ThreadLocalRandom.current().nextInt(
                    -HANGOUT_RADIUS, HANGOUT_RADIUS + 1);
            int distanceSquared = offsetX * offsetX + offsetY * offsetY;
            if (distanceSquared > HANGOUT_RADIUS * HANGOUT_RADIUS
                    || distanceSquared < MIN_HANGOUT_DISTANCE * MIN_HANGOUT_DISTANCE) {
                continue;
            }

            int x = clampCoordinate((long) targetPosition.getX() + offsetX);
            int y = clampCoordinate((long) targetPosition.getY() + offsetY);
            long dx = (long) x - myPosition.getX();
            long dy = (long) y - myPosition.getY();
            if (dx != 0 || dy != 0) {
                return new cPosition(x, y);
            }
        }

        int x = clampCoordinate((long) targetPosition.getX() + HANGOUT_RADIUS);
        if (x == myPosition.getX() && targetPosition.getY() == myPosition.getY()) {
            x = clampCoordinate((long) targetPosition.getX() - HANGOUT_RADIUS);
        }
        return new cPosition(x, targetPosition.getY());
    }

    private int clampCoordinate(long coordinate) {
        return (int) Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, coordinate));
    }
}
