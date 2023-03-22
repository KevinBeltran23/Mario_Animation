import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Donkey_Kong extends Moved
{

    public Donkey_Kong(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public boolean moveToFairy(Donkey_Kong fairy, WorldModel world, Entity target, EventScheduler scheduler, Action action) {
        if (fairy.getPosition().adjacent(target.getPosition())) {
            target.removeEntity(scheduler, world);
            return true;
        } else {
            // maybe get the list of valid neighbors are try going through each one
            Point nextPos = fairy.nextPosition(world, target.getPosition());
            if (!fairy.getPosition().equals(nextPos)) {
                fairy.moveEntity(world, scheduler, nextPos);
            }
            return false;
        }
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

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        Optional<Entity> fairyTarget = world.findNearest(this.getPosition(), new ArrayList(List.of(Banana_Tree.class, Banana_Sapling.class)));
        if (fairyTarget.isPresent()) {
            Point tgtPos = (fairyTarget.get()).getPosition();
            if (this.moveToFairy(this, world, fairyTarget.get(), scheduler, action)) {
                Banana_Stump sapling = tgtPos.createBananaStump(WorldModel.getBananaStumpKey(), imageStore.getImageList(WorldModel.getBananaStumpKey()));
                sapling.addEntity(world);
            }
        }
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
    }
}
