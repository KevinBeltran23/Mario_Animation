import processing.core.PImage;

import java.util.List;

public class New_Entity_Event extends Actioned
{
    public New_Entity_Event(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }
}
