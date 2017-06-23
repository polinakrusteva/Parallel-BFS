package runnable;

import dto.Graph;

import java.util.Queue;
import java.util.Set;

public class SilentBFS extends BFSRunnable implements Runnable {

    public SilentBFS(Queue<Integer> queue, Set<Integer> visited, int id, Graph graph) {
        super(queue, visited, id, graph);
    }

    @Override
    public void run() {
        search();
    }

}
