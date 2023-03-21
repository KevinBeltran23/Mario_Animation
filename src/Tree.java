import processing.core.PImage;

import java.util.List;

public final class Tree extends Plant{

    public Tree(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod, health, healthLimit);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Stump stump = this.getPosition().createStump(WorldModel.getStumpKey() + "_" + this.getId(), imageStore.getImageList(WorldModel.getStumpKey()));
            this.removeEntity(scheduler, world);
            stump.addEntity(world);
            return true;
        } else {
            return false;
        }
    }
    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        if (!this.transformHealthEntity(world, scheduler, imageStore, action)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }
}
