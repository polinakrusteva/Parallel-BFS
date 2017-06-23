# Parallel-BFS

BFS traversing graph(directed or undirected, connected or disconnected). Graph representation uses adjacency matrix.

### Program accepts certain parameters:
* n - size of graph(number of vertices)
* i - inserts a graph from an input file(if both -n and -i arguments, the graph is randomly generated with size n)* 
* t - maximum number of threads for current run
* o - output result file
* q - program in quite mode(it only displays the final result of the execution)

### Example usage:
**java -jar parallel.bfs-1.0-SNAPSHOT-jar-with-dependencies.jar -n 10240 -t 8 –q**

This traverses a randomly generated graph with 10240 with 8 threads in quite mode
