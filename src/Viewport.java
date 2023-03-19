
public final class Viewport {
    private int row;
    private int col;
    private final int numRows;
    private final int numCols;

    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public boolean contains(Point p) {
        return p.y >= this.row && p.y < this.row + this.numRows && p.x >= this.col && p.x < this.col + this.numCols;
    }

    public void shift(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public Point worldToViewport(int col, int row) {
        return new Point(col - this.col, row - this.row);
    }

    public Point viewportToWorld(int col, int row) {
        return new Point(col + this.col, row + this.row);
    }

    public void shiftView(int colDelta, int rowDelta, WorldView worldView) {
        int newCol = clamp(this.col + colDelta, 0, worldView.getWorldModel().getNumCols() - this.numCols);
        int newRow = clamp(this.row + rowDelta, 0, worldView.getWorldModel().getNumRows() - this.numRows);
        this.shift(newCol, newRow);
    }
}

