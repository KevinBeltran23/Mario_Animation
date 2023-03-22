import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Fairy extends regularMovement {

    public Fairy(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        Optional<Entity> fairyTarget = world.findNearest(this.getPosition(), new ArrayList(List.of(Stump.class)));
        if (fairyTarget.isPresent()) {
            Point tgtPos = (fairyTarget.get()).getPosition();
            if (this.move(this, world, fairyTarget.get(), scheduler, action)) {
                Plant sapling = tgtPos.createSapling(WorldModel.getSaplingKey() + "_" + (fairyTarget.get()).getId(), imageStore.getImageList(WorldModel.getSaplingKey()), 0);
                sapling.addEntity(world);
                sapling.scheduleActions(world, imageStore, scheduler);
            }
        }
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
    }
}

