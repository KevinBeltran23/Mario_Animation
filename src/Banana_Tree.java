import processing.core.PImage;

import java.util.List;

public class Banana_Tree extends Banana
{
    public Banana_Tree(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod, health, healthLimit);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Banana_Stump bananaStump = this.getPosition().createBananaStump(WorldModel.getStumpKey() + "_" + this.getId(), imageStore.getImageList(WorldModel.getBananaStumpKey()));
            this.removeEntity(scheduler, world);
            bananaStump.addEntity(world);
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
