import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Jayanee Venkat
 * @userid jvenkat8
 * @GTID 903628863
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("do not enter any null parameters");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> result = new ArrayList<>();
        Queue<Vertex<T>> q = new LinkedList<>();
        q.add(start);
        visited.add(start);

        while (!q.isEmpty()) {
            Vertex<T> temp = q.remove();
            result.add(temp);
            for (VertexDistance<T> v : graph.getAdjList().get(temp)) {
                if (!visited.contains(v.getVertex())) {
                    q.add(v.getVertex());
                    visited.add(v.getVertex());
                }
            }
        }

        return result;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("do not enter any null parameters");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> result = new ArrayList<>();

        dfs(start, graph, visited, result);
        return result;
    }

    /**
     * private helper method for dfs
     * @param start vertex type; starting vertx
     * @param graph Graph generic type; represents the graph
     * @param visited set of vertecies visited; vertex type
     * @param f List of vertex type
     * @param <T> return type
     */
    private static <T> void dfs(Vertex<T> start, Graph<T> graph, Set<Vertex<T>> visited, List<Vertex<T>> f) {
        f.add(start);
        visited.add(start);
        for (VertexDistance<T> v : graph.getAdjList().get(start)) {
            if (!visited.contains((v.getVertex()))) {
                dfs(v.getVertex(), graph, visited, f);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (graph == null || start == null) {
            throw new IllegalArgumentException("one of the parameters is null");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> v = graph.getAdjList();
        if (!v.containsKey(start)) {
            throw new IllegalArgumentException("list is empty-- without start value");
        }


        Queue<VertexDistance<T>> q = new PriorityQueue<>();
        Map<Vertex<T>, Integer> result = new HashMap<>();

        for (Vertex<T> vertex : v.keySet()) {
            if (vertex.equals(start)) {
                result.put(vertex, 0);
            } else {
                result.put(vertex, Integer.MAX_VALUE);
            }
        }
        q.add(new VertexDistance<>(start, 0));
        while (!q.isEmpty()) {
            VertexDistance<T> temp = q.remove();

            for (VertexDistance<T> vs : v.get(temp.getVertex())) {
                if (result.get(vs.getVertex()) > temp.getDistance() + vs.getDistance()) {
                    result.put(vs.getVertex(), temp.getDistance() + vs.getDistance());
                    q.add(new VertexDistance<>(vs.getVertex(), temp.getDistance() + vs.getDistance()));
                }
            }
        }

        return result;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * You should NOT allow self-loops or parallel edges in the MST.
     * <p>
     * You may import/use PriorityQueue, java.util.Set, and any class that
     * implements the aforementioned interface.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("no parameters can be null and start needs to be contained");
        }
        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Set<Edge<T>> result = new HashSet<>();

        visited.add(start);
        for (VertexDistance<T> vs : graph.getAdjList().get(start)) {
            queue.add(new Edge<>(start, vs.getVertex(), vs.getDistance()));
        }
        while (!queue.isEmpty() && result.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> temp = queue.remove();
            if (!visited.contains(temp.getV())) {
                result.add(temp);
                result.add(new Edge<>(temp.getV(), temp.getU(), temp.getWeight()));
                visited.add(temp.getV());
                for (VertexDistance<T> v : graph.getAdjList().get(temp.getV())) {
                    if (!visited.contains(v.getVertex())) {
                        queue.add(new Edge<>(temp.getV(), v.getVertex(), v.getDistance()));
                    }
                }
            }
        }

        if (result.size() < 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return result;
    }
}