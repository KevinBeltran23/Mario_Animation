public class Animation implements Action
{
    private final Entity entity;
    private final int repeatCount;

    public Animation(Entity entity, int repeatCount) {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
        this.executeAnimationAction(scheduler, this.entity);
    }

    public void executeAnimationAction(EventScheduler eventScheduler, Entity entity) {
        Actioned e = (Actioned) entity;
        e.nextImage();
        if (this.repeatCount != 1) {
            eventScheduler.scheduleEvent(e, e.createAnimationAction(Math.max(this.repeatCount - 1, 0)), e.getAnimationPeriod());
        }
    }
}
