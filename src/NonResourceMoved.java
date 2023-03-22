import processing.core.PImage;

import java.util.List;

public abstract class NonResourceMoved extends Moved{
    public NonResourceMoved(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);
        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }
        return newPos;
    }

    public boolean move(NonResourceMoved entity, WorldModel world, Entity target, EventScheduler scheduler, Action action) {
        if (entity.getPosition().adjacent(target.getPosition())) {
            target.removeEntity(scheduler, world);
            return true;
        } else {
            // maybe get the list of valid neighbors are try going through each one
            Point nextPos = entity.nextPosition(world, target.getPosition());
            if (!entity.getPosition().equals(nextPos)) {
                entity.moveEntity(world, scheduler, nextPos);
            }
            return false;
        }
    }

}
