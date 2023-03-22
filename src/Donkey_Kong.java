import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Donkey_Kong extends Resource
{
    public Donkey_Kong(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        Optional<Entity> fairyTarget = world.findNearest(this.getPosition(), new ArrayList(List.of(Banana_Tree.class, Banana_Sapling.class)));
        if (fairyTarget.isPresent()) {
            Point tgtPos = (fairyTarget.get()).getPosition();
            if (this.move(this, world, fairyTarget.get(), scheduler, action)) {
                this.setResourceCount(1);
                Banana_Stump stump = tgtPos.createBananaStump(WorldModel.getBananaStumpKey(), imageStore.getImageList(WorldModel.getBananaStumpKey()));
                stump.addEntity(world);

                if (this.getResourceCount() >= this.getResourceLimit()){
                    Donkey_Kong_Sleeping donkeyKongSleeping = this.getPosition().createDonkeyKongSleeping("donkeyKongSleeping", imageStore.getImageList("donkeyKongSleeping"));
                    this.removeEntity(scheduler, world);
                    scheduler.unscheduleAllEvents(this);
                    donkeyKongSleeping.addEntity(world);
                }
            }
        }
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
    }
}
