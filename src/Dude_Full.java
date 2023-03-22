import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Dude_Full extends Dude{

    public Dude_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), new ArrayList(List.of(House.class)));
        if (fullTarget.isPresent() && this.moveToFull(world, fullTarget.get(), scheduler, action)) {
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler, Action action) {
        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!this.getPosition().equals(nextPos)) {
                this.moveEntity(world, scheduler, nextPos);
            }
            return false;
        }
    }



    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Dude_Not_Full dude = this.getPosition().createDudeNotFull(this.getId(), this.getActionPeriod(), this.getAnimationPeriod(), this.getResourceLimit(), this.getImages());
        this.removeEntity(scheduler, world);
        dude.addEntity(world);
        dude.scheduleActions(world, imageStore, scheduler);
    }

}
