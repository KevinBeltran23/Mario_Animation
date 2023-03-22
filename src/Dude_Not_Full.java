import processing.core.PImage;

import java.util.*;

public final class Dude_Not_Full extends Dude {

    public Dude_Not_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList(Arrays.asList(Tree.class, Sapling.class)));
        if (Objects.equals(this.getId(), "marioTime")){
            Mario mario = this.getPosition().createMario("mario", 1.0, 0.180, imageStore.getImageList("mario"));
            scheduler.unscheduleAllEvents(this);
            this.removeEntity(scheduler, world);
            mario.addEntity(world);
            mario.scheduleActions(world, imageStore, scheduler);
        }
        else if (target.isEmpty() || !this.move(world, (Plant) target.get(), scheduler, action) || !this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    public boolean move(WorldModel world, Plant target, EventScheduler scheduler, Action action) {
        if (this.getPosition().adjacent(target.getPosition())) {
            this.setResourceCount(1);
            target.adjustHealth(-1);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!this.getPosition().equals(nextPos)) {
                this.moveEntity(world, scheduler, nextPos);
            }
            return false;
        }
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            Dude_Full dude = this.getPosition().createDudeFull(this.getId(), this.getActionPeriod(), this.getAnimationPeriod(), this.getResourceLimit(), this.getImages());
            this.removeEntity(scheduler, world);
            scheduler.unscheduleAllEvents(this);
            dude.addEntity(world);
            dude.scheduleActions(world, imageStore, scheduler);
            return true;
        } else {
            return false;
        }
    }
}
