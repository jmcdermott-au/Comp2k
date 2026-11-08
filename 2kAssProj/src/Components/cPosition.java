package Components;

import java.util.concurrent.ThreadLocalRandom;

import Engine.Event;

public class cPosition extends Component {
    private int x;
    private int y;

    public cPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveBy(int dx, int dy) {
        x += dx;
        y += dy;
    }

    @Override
    public boolean wantEvent(String eventId) {
        return "TickUpdate".equals(eventId)
            || "IwannaMoveHere".equals(eventId)
            || "IwannaGraze".equals(eventId)
            || "RoamAroundHere".equals(eventId);
    }

    @Override
    public void handleEvent(Event e) {
        switch (e.ID) {
            case "TickUpdate" -> handleTickUpdate(e);
            case "IwannaMoveHere" -> handleMoveRequest(e);
            case "IwannaGraze" -> handleGrazeRequest(e);
            case "RoamAroundHere" -> handleRoamRequest(e);
        }
    }

    @Override
    public void Behaviour(Event e) {
        handleEvent(e);
    }

    private void handleTickUpdate(Event e) {
        // moveBy(1, 1);
    }

    private void handleMoveRequest(Event e) {
        cPosition location = e.getParameter("location", cPosition.class);
        if (location == null) {
            throw new IllegalArgumentException(
                "IwannaMoveHere requires a cPosition 'location' parameter"
            );
        }

        int toMoveX = Integer.compare(location.getX(), x);
        int toMoveY = Integer.compare(location.getY(), y);
        moveBy(toMoveX, toMoveY);
    }

    private void handleGrazeRequest(Event e) {
        Integer speed = e.getParameter("speed", Integer.class);
        if (speed == null) {
            throw new IllegalArgumentException(
                "IwannaGraze requires an integer 'speed' parameter"
            );
        }

        int randomNumX = speed * ThreadLocalRandom.current().nextInt(-1, 2);
        int randomNumY = speed * ThreadLocalRandom.current().nextInt(-1, 2);
        moveBy(randomNumX, randomNumY);
    }

    private void handleRoamRequest(Event e) {
        Integer centerX = e.getParameter("centerX", Integer.class);
        Integer centerY = e.getParameter("centerY", Integer.class);
        if (centerX == null || centerY == null) {
            throw new IllegalArgumentException(
                "RoamAroundHere requires integer 'centerX' and 'centerY' parameters"
            );
        }

        long minX = Math.max((long) Integer.MIN_VALUE, (long) centerX - 10);
        long maxX = Math.min((long) Integer.MAX_VALUE, (long) centerX + 9);
        long minY = Math.max((long) Integer.MIN_VALUE, (long) centerY - 10);
        long maxY = Math.min((long) Integer.MAX_VALUE, (long) centerY + 9);

        x = (int) Math.max(minX, Math.min((long) x, maxX));
        y = (int) Math.max(minY, Math.min((long) y, maxY));

        int[] stepX = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] stepY = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] validSteps = new int[stepX.length];
        int validCount = 0;

        for (int i = 0; i < stepX.length; i++) {
            long nextX = (long) x + stepX[i];
            long nextY = (long) y + stepY[i];
            if (nextX >= minX && nextX <= maxX
                    && nextY >= minY && nextY <= maxY) {
                validSteps[validCount++] = i;
            }
        }

        if (validCount > 0) {
            int step = validSteps[ThreadLocalRandom.current().nextInt(validCount)];
            moveBy(stepX[step], stepY[step]);
        }
    }
}