import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Dude_Full extends Dude {

    public Dude_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), new ArrayList(List.of(House.class)));
        if (Objects.equals(this.getId(), "marioTime")){
            Mario mario = this.getPosition().createMario("mario", 1.0, 0.180, imageStore.getImageList("mario"));
            scheduler.unscheduleAllEvents(this);
            this.removeEntity(scheduler, world);
            mario.addEntity(world);
            mario.scheduleActions(world, imageStore, scheduler);
        }
        else if (fullTarget.isPresent() && this.move(world, fullTarget.get(), scheduler, action)) {
            this.transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    public boolean move(WorldModel world, Entity target, EventScheduler scheduler, Action action) {
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

    public void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Dude_Not_Full dude = this.getPosition().createDudeNotFull(this.getId(), this.getActionPeriod(), this.getAnimationPeriod(), this.getResourceLimit(), this.getImages());
        this.removeEntity(scheduler, world);
        dude.addEntity(world);
        dude.scheduleActions(world, imageStore, scheduler);
    }

}
