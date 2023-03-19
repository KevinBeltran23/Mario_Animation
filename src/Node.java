public class Node
{
        private int g; //distance from start
        private int h; //heuristic distance
        private int f; //total distance f=g+h
        private Node prev_node; // prior node;
        private Point position;

        public Node (Point pos){
            this.g = g;
            this.h = h;
            this.f = f;
            this.prev_node = prev_node;
            this.position = position;
        }

        public Node getPrevNode(){
        return prev_node;
        }

        public void setPrevNode(Node node){
            this.prev_node = node;
        }

        public boolean containsPoint(Point p){
        return this.position == p;
        }

        public Point getPosition(){
            return this.position;
        }

        public void setPosition(Point pos){
            this.position = pos;
        }

        public int getG(){
            return this.g;
        }

        public int getH() {
            return this.h;
        }

        public int getF(){
            return this.f;
        }

        public void setG(int g){
            this.g = g;
        }

        public void setH(int h){
            this.h = h;
        }

        public void setF(){
            this.f = this.g + this.h;
        }
}
