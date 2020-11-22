package ex1;


import java.io.*;
import java.util.*;

public class WGraph_Algo extends WGraph_DS implements weighted_graph_algorithms, java.io.Serializable {
    private WGraph_DS myGraph = new WGraph_DS();

    //    simple constructor for this object.
    WGraph_Algo() {
        this.myGraph = new WGraph_DS();
    }

    @Override
    //  Inits the graph on which this set of algorithms operates on.
    public void init(weighted_graph g) {
        this.myGraph = (WGraph_DS) g;
    }

    @Override
    //    Returns the graph of which this class works.
    public weighted_graph getGraph() {
        return myGraph;
    }

    @Override
    //   Computes a deep copy of this weighted graph.
    public weighted_graph copy() {
        WGraph_DS myCopy = new WGraph_DS();
        for (node_info i : myGraph.getV()) {
            myCopy.addNode(i.getKey());
        }
        for (node_info i : myGraph.getV()) {
            for (node_info j : myGraph.getV(i.getKey())) {
                myCopy.connect(i.getKey(), j.getKey(), myGraph.getMyNode(i.getKey()).getDist(myGraph.getMyNode(j.getKey())));
            }
        }

        return myCopy;
    }

    @Override
    //    Returns true if and only if there is a valid path from every node to other node.
    //    using bfs algorithm as I did in ex0 with minor changes.
    public boolean isConnected() {
        if (myGraph.nodeSize() == 0 || myGraph.nodeSize() == 1)
            return true;
        node_info i = myGraph.getMap().values().iterator().next();
        if (BFS(i) == myGraph.nodeSize())
            return true;
        return false;
    }

    @Override
    //  Returns the the shortest path between src to dest as an ordered List of nodes.
    //  if no such path returns -1.
    //  uses the shortestPath function and another function that sums the distances.
    public double shortestPathDist(int src, int dest) {
        if (myGraph.getMap().isEmpty() || myGraph.getMap() == null || myGraph.nodeSize() == 0 || myGraph.nodeSize() == 1)
            return -1;
        return pathDist(shortestPath(src, dest));
    }

    @Override
    //  Returns the the shortest path between src to dest as an ordered List of nodes.
    //  if no such path returns null.
    //  using dijkstra's algorithm, rating every node on the way by its distance from src node.
    public List<node_info> shortestPath(int src, int dest) {
        if (myGraph.getMap().isEmpty() || myGraph.getMap() == null || myGraph.nodeSize() == 0 || myGraph.nodeSize() == 1)
            return null;
        Map<Integer, node_info> visited = new HashMap<Integer, node_info>();
        List<node_info> ans = new LinkedList<node_info>();
        HashMap<Integer, Double> dis = new HashMap<>();
        PriorityQueue<node_info> que = new PriorityQueue<node_info>(new Comparator<node_info>() {
            @Override
            public int compare(node_info o1, node_info o2) {
                if (dis.get(o1.getKey()) <= dis.get(o2.getKey()))
                    return (int) -dis.get(o1.getKey());
                return (int) -dis.get(o2.getKey());
            }
        });
        nodeInfo start = myGraph.getMyNode(src);
        nodeInfo s = start;
        nodeInfo end = myGraph.getMyNode(dest);
        visited.put(myGraph.getNode(src).getKey(), myGraph.getNode(src));
        que.add(myGraph.getNode(src));
        dis.put(myGraph.getNode(src).getKey(), 0.0);
        while (que.size() != 0 && (!dis.containsKey(dest) || dis.get(que.peek().getKey()) < dis.get(dest))) {
            start = (nodeInfo) que.poll();
            visited.put(start.getKey(), start);
            for (node_info n : start.getLis()) {
                if (!visited.containsKey(n.getKey())) {
                    n.setTag(start.getKey());
                }
                if (!dis.containsKey(n.getKey()) || !dis.containsKey((int) n.getTag())) {
                    dis.put(n.getKey(), dis.get((int) n.getTag()) + myGraph.getMap().get((int) n.getTag()).getDist(myGraph.getMyNode(n.getKey())));
                    que.add(n);
                } else {
                    if (dis.get(n.getKey()) > dis.get((int) n.getTag()) + myGraph.getEdge(n.getKey(), (int) n.getTag())) {
                        dis.put(n.getKey(), dis.get((int) n.getTag()) + myGraph.getEdge(n.getKey(), (int) n.getTag()));
                        que.add(n);
                    }
                }
            }
        }
        if (end.getTag() != 0 || dis.containsKey(dest)) {
            while (visited.get((int) end.getTag()) != null && end.getKey() != s.getKey()) {
                ans.add(end);
                end = (nodeInfo) visited.get((int) end.getTag());
            }
            ans.add(s);
            cleanTags(visited);
            Collections.reverse(ans);
            return ans;
        }
        return null;
    }

    @Override
    //    Saves this graph to the given file name.
    //    Return true if the file was successfully saved.
    public boolean save(String file) {
        WGraph_Algo n = this;
        try {
            FileOutputStream fille = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fille);
            out.writeObject(n);
            out.close();
            fille.close();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    //     Loads a graph to this graph algorithm.
    //     if the file was successfully loaded returns true, in case the
    //     graph was not loaded the original graph doesn't change.
    public boolean load(String file) {
        try {
            FileInputStream fille = new FileInputStream(file);
            ObjectInputStream input = new ObjectInputStream(fille);
            WGraph_Algo newGr = (WGraph_Algo) input.readObject();
            this.init(newGr.myGraph);
        } catch (IOException | ClassNotFoundException e) {
            return false;

        }
        return true;
    }

    //   Returns the sum of distances in a path of nodes
    public double pathDist(Collection<node_info> lis) {
        if (lis != null && lis.size() != 0 && lis.size() != 1) {
            double sum = 0;
            int j = -1;
            for (node_info i : lis) {
                if (j != -1)
                    sum += myGraph.getEdge(i.getKey(), j);
                j = i.getKey();
            }
            return sum;
        }
        return -1;
    }

    //bfs algorithm from ex0, checks connectivity of the graph.
    public int BFS(node_info start) {
        // hashmap saves all nodes which visited by bfs algorithm
        Map<Integer, node_info> visited = new HashMap<Integer, node_info>();
        // queue of BFS
        LinkedList<node_info> queue = new LinkedList<node_info>();
        visited.put(start.getKey(), start);
        queue.add(start);
        while (queue.size() != 0) {
            // take the first-added node, poll it from the queue, name it start and repeat the process
            start = queue.poll();
            //check all start node neighbours and add the unchecked nodes to the queue and the hashmap
            for (node_info n : myGraph.getV(start.getKey())) {
                if (!visited.containsKey(n.getKey())) {
                    visited.put(n.getKey(), n);
                    queue.add(n);
                }
            }
        }
        //returns the number of connected nodes
        return visited.size();
    }

    // the function set back the graph's node's tags to 0
    public void cleanTags(Map<Integer, node_info> hashMap) {
        hashMap.forEach((key, value) -> {
            value.setTag(0);
        });
    }
}