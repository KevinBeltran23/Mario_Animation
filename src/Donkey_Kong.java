import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Donkey_Kong extends NonResourceMoved
{
    private int resourceCount;
    private int resourceLimit;
    public Donkey_Kong(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit(){
        return this.resourceLimit;
    }

    public int getResourceCount(){
        return this.resourceCount;
    }

    public boolean move(NonResourceMoved entity, WorldModel world, Entity target, EventScheduler scheduler, Action action) {
        if (entity.getPosition().adjacent(target.getPosition())) {
            this.resourceCount += 1;
            target.removeEntity(scheduler, world);
            return true;
        } else {
            // maybe get the list of valid neighbors are try going through each one
            Point nextPos = entity.nextPosition(world, target.getPosition());
            if (!entity.getPosition().equals(nextPos)) {
                entity.moveEntity(world, scheduler, nextPos);
            }
            return false;
        }
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        Optional<Entity> fairyTarget = world.findNearest(this.getPosition(), new ArrayList(List.of(Banana_Tree.class, Banana_Sapling.class)));
        if (fairyTarget.isPresent()) {
            Point tgtPos = (fairyTarget.get()).getPosition();
            if (this.move(this, world, fairyTarget.get(), scheduler, action)) {
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
