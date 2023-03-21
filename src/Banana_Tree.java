import processing.core.PImage;

import java.util.List;

public class Banana_Tree extends Banana
{
    public Banana_Tree(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod, health, healthLimit);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return false;
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        if (!this.transformHealthEntity(world, scheduler, imageStore, action)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }
}
