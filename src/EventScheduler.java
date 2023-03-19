//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public final class EventScheduler {
    private final PriorityQueue<Event> eventQueue = new PriorityQueue(new EventComparator());
    private final Map<Entity, List<Event>> pendingEvents = new HashMap();
    private double currentTime = 0.0;

    public EventScheduler() {
    }

    public double getCurrentTime() {
        return this.currentTime;
    }

    public void scheduleEvent(Entity entity, Action action, double afterPeriod) {
        double time = this.currentTime + afterPeriod;
        Event event = new Event(action, time, entity);
        this.eventQueue.add(event);
        List<Event> pending = this.pendingEvents.getOrDefault(entity, new LinkedList());
        pending.add(event);
        this.pendingEvents.put(entity, pending);
    }

    public void unscheduleAllEvents(Entity entity) {
        List<Event> pending = this.pendingEvents.remove(entity);
        if (pending != null) {
            Iterator var3 = pending.iterator();

            while(var3.hasNext()) {
                Event event = (Event)var3.next();
                this.eventQueue.remove(event);
            }
        }

    }

    public void removePendingEvent(Event event) {
        List<Event> pending = (List)this.pendingEvents.get(event.getEntity());
        if (pending != null) {
            pending.remove(event);
        }

    }

    public void updateOnTime(double time) {
        double stopTime = this.currentTime + time;

        while(!this.eventQueue.isEmpty() && ((Event)this.eventQueue.peek()).getTime() <= stopTime) {
            Event next = (Event)this.eventQueue.poll();
            this.removePendingEvent(next);
            this.currentTime = next.getTime();
            next.getAction().executeAction(this);
        }

        this.currentTime = stopTime;
    }
}

