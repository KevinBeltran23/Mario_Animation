import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Mario extends regularMovement
{
    public Mario(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action) {
        Optional<Entity> fairyTarget = world.findNearest(this.getPosition(), new ArrayList(List.of(Banana_Stump.class, Donkey_Kong_Sleeping.class)));
        if (fairyTarget.isPresent()) {
            this.move(this, world, fairyTarget.get(), scheduler, action);
        }
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
    }
}
