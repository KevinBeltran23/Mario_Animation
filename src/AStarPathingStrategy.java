import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        // path we will return
        List<Point> path = new LinkedList<Point>();

        // open list - priority queue
        Queue<Node> openList = new PriorityQueue<Node>(Comparator.comparingInt(Node::getF));

        Node currentNode = new Node(start);
        openList.add(currentNode);

        while(!withinReach.test(currentNode.getPosition(), end))
            for (Point pos : potentialNeighbors.apply(currentNode.getPosition()).toList()) {
                Node q = new Node(pos);
                openList.add(q);
            }
        for (Point pos : potentialNeighbors.apply(currentNode.getPosition()).toList()) {
            Node q = new Node(pos);
            openList.remove(q);

            q.setG(currentNode.getG() + 1);
            if (!path.contains(pos)) {
                if (openList.contains(q)) {
                    for (Node node : openList) {
                        // if the heuristic distance is less than the current heuristic distance
                        if (node.equals(q) && heuristic(q.getPosition(), end) < heuristic(node.getPosition(), end)) {
                            node.setG(q.getG());

                            path.add(q.getPosition());

                            node.setF();
                            node.setPrevNode(currentNode);
                            break;
                        }
                    }
                }
            }
        }

        return assemblePath(path, currentNode);
    }

    public int heuristic (Point current, Point end){
        return Math.abs(current.x - end.x) + Math.abs(current.y - end.y);
    }

    public List<Point> assemblePath(List<Point> path, Node current){
        path.add(current.getPosition());
        if(current.getPrevNode() == null){
            Collections.reverse(path);
            return path;
        }
        return assemblePath(path, current.getPrevNode());
    }
}