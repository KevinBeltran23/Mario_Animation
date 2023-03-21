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

    public void transformToBanana(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        Banana_Tree bananaTree = this.getPosition().createBananaTree(WorldModel.getBananaTreeKey() + "_" + this.getId(), Point.getNumFromRange(1.4, 1.0), Point.getNumFromRange(0.6, 0.05), Point.getIntFromRange(3, 1), imageStore.getImageList(WorldModel.getBananaTreeKey()));
        this.removeEntity(scheduler, world);
        bananaTree.addEntity(world);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        if (!this.transformHealthEntity(world, scheduler, imageStore, action)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }
}
