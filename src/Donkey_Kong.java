import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Donkey_Kong extends NonResourceMoved
{
    public Donkey_Kong(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        Optional<Entity> fairyTarget = world.findNearest(this.getPosition(), new ArrayList(List.of(Banana_Tree.class, Banana_Sapling.class)));
        if (fairyTarget.isPresent()) {
            Point tgtPos = (fairyTarget.get()).getPosition();
            if (this.move(this, world, fairyTarget.get(), scheduler, action)) {
                Banana_Stump sapling = tgtPos.createBananaStump(WorldModel.getBananaStumpKey(), imageStore.getImageList(WorldModel.getBananaStumpKey()));
                sapling.addEntity(world);
            }
        }
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
    }
}
