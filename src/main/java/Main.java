import dto.Graph;
import org.apache.commons.cli.*;
import runnable.NonSilentBFS;
import runnable.SilentBFS;

import java.io.*;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("n", true, "set number of graph vertices");
        options.addOption("i", true, "set file containing graph");
        options.addOption("t", true, "set maximum number of threads");
        options.addOption("q", false, "run program in quiet mode");
        options.addOption("o", true, "set output file");

        CommandLineParser parser = new DefaultParser();
        Graph graph = null;

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("n")) {
                int vertices = Integer.parseInt(cmd.getOptionValue("n"));
                graph = Graph.generateRandomGraph(vertices);
            } else if (cmd.hasOption("i")) {
                String file = cmd.getOptionValue("i");
                graph = Graph.createGraphFromFile(file);
            }
            if (cmd.hasOption("t")) {
                int numThreads = Integer.parseInt(cmd.getOptionValue("t"));
                long startTime = System.currentTimeMillis();
                if (cmd.hasOption("q")) {
                    runBFS(true, numThreads, graph);
                } else {
                    runBFS(false, numThreads, graph);
                    System.out.println("Threads used in current run: " + numThreads);
                }
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println("Total execution time for current run (millis): " + totalTime);
                if (cmd.hasOption("o")) {
                    String filename = cmd.getOptionValue("o");
                    writeResultToFile(filename, totalTime);
                }
                
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void runBFS(boolean isQuiet, int numThreads, Graph graph) {
        if (graph != null) {
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            Queue<Integer> queue = new LinkedBlockingQueue<>();
            Set<Integer> set = Collections.newSetFromMap(new ConcurrentHashMap<>(graph.size));
            if (isQuiet) {
                for (int i = 0; i < numThreads; i++) {
                    Runnable worker = new SilentBFS(queue, set, i % numThreads + 1, graph);
                    executor.execute(worker);
                }
            } else {
                for (int i = 0; i < numThreads; i++) {
                    Runnable worker = new NonSilentBFS(queue, set, i % numThreads + 1, graph);
                    executor.execute(worker);
                }
            }
            executor.shutdown();

            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException е) {
                System.out.println();
            }
        }
    }

    private static void writeResultToFile(String filename, long time) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)))) {
            writer.write(String.format("Total execution time for current run(millis): %d", time));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
