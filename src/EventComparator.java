//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Comparator;

public final class EventComparator implements Comparator<Event> {
    public EventComparator() {
    }

    public int compare(Event lft, Event rht) {
        return (int)(1000.0 * (lft.getTime() - rht.getTime()));
    }
}

