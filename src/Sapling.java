import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class Sapling extends Plant{

    public Sapling(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod, health, healthLimit);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Stump stump;
        Tree tree;
        if (this.getHealth() <= 0) {
            stump = this.getPosition().createStump(WorldModel.getStumpKey() + "_" + this.getId(), imageStore.getImageList(WorldModel.getStumpKey()));
            this.removeEntity(scheduler, world);
            stump.addEntity(world);
            return true;
        } else if (this.getHealth() >= this.getHealthLimit()) {
            tree = this.getPosition().createTree(WorldModel.getTreeKey() + "_" + this.getId(), Point.getNumFromRange(1.4, 1.0), Point.getNumFromRange(0.6, 0.05), Point.getIntFromRange(3, 1), imageStore.getImageList(WorldModel.getTreeKey()));
            this.removeEntity(scheduler, world);
            tree.addEntity(world);
            tree.scheduleActions(world, imageStore, scheduler);
            return true;
        } else {
            return false;
        }
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        this.setHealth(1);
        if (!this.transformPlant(world, scheduler, imageStore, action)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }

    }
}
