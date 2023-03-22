import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Donkey_Kong extends Moved
{
    public Donkey_Kong(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action){
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList(Arrays.asList(Banana_Tree.class)));
        // remember to add stuff about check if !transform
        if (target.isEmpty() || !this.moveToNotFull(world, (Banana) target.get(), scheduler, action)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    public boolean moveToNotFull(WorldModel world, Banana target, EventScheduler scheduler, Action action) {
        if (this.getPosition().adjacent(target.getPosition())) {
            //this.setResourceCount(1);
            target.adjustHealth(-1);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!this.getPosition().equals(nextPos)) {
                this.moveEntity(world, scheduler, nextPos);
            }
            return false;
        }
    }
}
