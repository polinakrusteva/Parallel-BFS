package runnable;

import dto.Graph;

import java.util.Queue;
import java.util.Set;

public class NonSilentBFS extends BFSRunnable implements Runnable {

    public NonSilentBFS(Queue<Integer> queue, Set<Integer> visited, int id, Graph graph) {
        super(queue, visited, id, graph);
    }

    @Override
    public void run() {
        System.out.println("Thread-" + this.id + " started.");
        long start = System.currentTimeMillis();
        search();
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("Thread-" + this.id + " stopped.");
        System.out.println("Thread-" + this.id + " execution time was (millis):" + time);
    }

}
