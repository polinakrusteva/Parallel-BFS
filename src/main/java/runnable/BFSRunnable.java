package runnable;

import dto.Graph;

import java.util.List;
import java.util.Queue;
import java.util.Set;

public abstract class BFSRunnable {

    protected final Queue<Integer> queue;
    protected final Set<Integer> visited;
    protected final int id;
    protected final Graph graph;

    public BFSRunnable(Queue<Integer> queue, Set<Integer> visited, int id, Graph graph) {
        this.queue = queue;
        this.visited = visited;
        this.id = id;
        this.graph = graph;
    }

    public void search() {
        Integer node = id;
        visitNode(node);
        while (visited.size() != graph.size) {
            while (!queue.isEmpty()) {
                Integer currentNode = queue.poll();
                if (currentNode != null) {
                    List<Integer> adjacent = graph.getAdjacent(currentNode);
                    for (Integer adj : adjacent) {
                        visitNode(adj);
                    }
                }
            }
            while (queue.isEmpty() && visited.size() != graph.size) {
                node = (node + 1) % graph.size;
                visitNode(node);
            }

        }
    }

    private void visitNode(Integer node) {
        synchronized (graph.nodes.get(node)) {
            if (!visited.contains(node)) {
                visited.add(node);
                queue.add(node);
            }
        }
    }

}
