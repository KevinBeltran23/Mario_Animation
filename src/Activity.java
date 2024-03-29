public class Activity implements Action
{
    private final WorldModel world;
    private final ImageStore imageStore;
    private final Entity entity;

    public Activity(Entity entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler, this.entity);
    }

    public void executeActivityAction(EventScheduler eventScheduler, Entity entity) {
        if(entity instanceof Sapling) {
            ((Sapling)entity).execute(this.world, this.imageStore, eventScheduler, this);
        }
        else if(entity instanceof Banana_Sapling) {
            ((Banana_Sapling)entity).execute(this.world, this.imageStore, eventScheduler, this);
        }
        else if(entity instanceof Tree){
            ((Tree)entity).execute(this.world, this.imageStore, eventScheduler, this);
        }
        else if(entity instanceof Banana_Tree){
            ((Banana_Tree)entity).execute(this.world, this.imageStore, eventScheduler, this);
        }
        else if(entity instanceof Fairy){
            ((Fairy)entity).execute(this.world, this.imageStore, eventScheduler, this);
        }
        else if(entity instanceof Mario){
            ((Mario)entity).execute(this.world, this.imageStore, eventScheduler, this);
        }
        else if(entity instanceof Dude_Not_Full){
            ((Dude_Not_Full)entity).execute(this.world, this.imageStore, eventScheduler, this);
        }
        else if(entity instanceof Donkey_Kong){
            ((Donkey_Kong)entity).execute(this.world, this.imageStore, eventScheduler, this);
        }
        else if(entity instanceof Dude_Full){
            ((Dude_Full)entity).execute(this.world, this.imageStore, eventScheduler, this);
        }
        else if(entity instanceof Stump){
            throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", Stump.class));
        }
        else if(entity instanceof Donkey_Kong_Sleeping){
            throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", Donkey_Kong_Sleeping.class));
        }
        else if(entity instanceof Banana_Stump){
            throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", Banana_Stump.class));
        }
        else if(entity instanceof House){
            throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", House.class));
        }
        else{
            throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", Obstacle.class));
        }
    }

}
