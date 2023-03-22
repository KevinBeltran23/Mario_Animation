import processing.core.PImage;

import java.util.List;

public class Banana_Sapling extends Banana
{
    public Banana_Sapling(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod, health, healthLimit);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Banana_Stump bananaStump;
        Banana_Tree bananaTree;
        if (this.getHealth() <= 0) {
            bananaStump = this.getPosition().createBananaStump(WorldModel.getBananaStumpKey() + "_" + this.getId(), imageStore.getImageList(WorldModel.getBananaStumpKey()));
            this.removeEntity(scheduler, world);
            bananaStump.addEntity(world);
            return true;
        } else if (this.getHealth() >= this.getHealthLimit()) {
            bananaTree = this.getPosition().createBananaTree(WorldModel.getBananaTreeKey() + "_" + this.getId(), Point.getNumFromRange(1.4, 1.0), Point.getNumFromRange(0.6, 0.05), Point.getIntFromRange(3, 1), imageStore.getImageList(WorldModel.getBananaTreeKey()));
            this.removeEntity(scheduler, world);
            bananaTree.addEntity(world);
            bananaTree.scheduleActions(world, imageStore, scheduler);
            return true;
        } else {
            return false;
        }
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        this.adjustHealth(1);
        if (!this.transformHealthEntity(world, scheduler, imageStore, action)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }
}
