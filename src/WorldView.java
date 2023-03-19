import java.util.Iterator;
import java.util.Optional;
import processing.core.PApplet;
import processing.core.PImage;

public final class WorldView {
    private final PApplet screen;
    private final WorldModel world;
    private final int tileWidth;
    private final int tileHeight;
    private final Viewport viewport;

    public WorldModel getWorldModel() {
        return this.world;
    }

    public Viewport getViewport() {
        return this.viewport;
    }

    public WorldView(int numRows, int numCols, PApplet screen, WorldModel world, int tileWidth, int tileHeight) {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    public void drawViewport() {
        this.drawBackground();
        this.drawEntities();
    }

    public void drawBackground() {
        for(int row = 0; row < this.viewport.getNumRows(); ++row) {
            for(int col = 0; col < this.viewport.getNumCols(); ++col) {
                Point worldPoint = this.viewport.viewportToWorld(col, row);
                Optional<PImage> image = this.world.getBackgroundImage(worldPoint);
                if (image.isPresent()) {
                    this.screen.image(image.get(), (float)(col * this.tileWidth), (float)(row * this.tileHeight));
                }
            }
        }

    }

    public void drawEntities() {
        Iterator var1 = this.world.getEntities().iterator();

        while(var1.hasNext()) {
            Entity entity = (Entity)var1.next();
            Point pos = entity.getPosition();
            if (this.viewport.contains(pos)) {
                Point viewPoint = this.viewport.worldToViewport(pos.x, pos.y);
                this.screen.image(getCurrentImageEntity(entity), (float)(viewPoint.x * this.tileWidth), (float)(viewPoint.y * this.tileHeight));
            }
        }
    }

    public PImage getCurrentImageEntity(Entity entity) {
        return entity.getImages().get(entity.getImageIndex() % entity.getImages().size());
    }
}
