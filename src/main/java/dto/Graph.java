package dto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Graph {

    public int size;
    public boolean[][] adj;
    public List<Integer> nodes;

    public Graph(int size) {
        this.size = size;
        this.adj = new boolean[size][size];
        this.nodes = IntStream.rangeClosed(0, size - 1).boxed().collect(Collectors.toList());
    }

    public void addEdge(int from, int to) {
        this.adj[from][to] = true;
    }

    public boolean hasEdge(int from, int to) {
        return this.adj[from][to];
    }

    public List<Integer> getAdjacent(int vertice) {
        List<Integer> adjacent = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            if (this.adj[vertice][i]) {
                adjacent.add(i);
            }
        }
        return adjacent;
    }

    public static Graph generateRandomGraph(int vertices) {
        Graph graph = new Graph(vertices);
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                graph.adj[i][j] = ThreadLocalRandom.current().nextBoolean();
            }
        }
        return graph;
    }

    public static Graph createGraphFromFile(String filename) {
        Graph graph = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int size = Integer.parseInt(reader.readLine());
            boolean[][] matrix = new boolean[size][size];
            String line = null;
            for (int i = 0; i < size; i++) {
                if ((line = reader.readLine()) != null) {
                    String[] values = line.split(" ");
                    for (int j = 0; j < size; j++) {
                        matrix[i][j] = Integer.parseInt(values[j]) == 1;
                    }
                }
            }
            graph = new Graph(size);
            graph.adj = matrix;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                int value = this.adj[i][j] ? 1 : 0;
                result.append(value);
                result.append(" ");
            }
            result.append(System.getProperty("line.separator"));
        }
        return result.toString();
    }

}
