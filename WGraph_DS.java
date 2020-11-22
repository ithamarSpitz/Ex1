package ex1;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class WGraph_DS implements weighted_graph ,java.io.Serializable{
    public static class nodeInfo implements node_info ,java.io.Serializable {
        private int key;
        private static int id = 1;
        private double tag = 0;
        private String info = null;
        private HashMap<Integer ,node_info> lis = new HashMap<Integer ,node_info>();
        private HashMap<Integer, Double> myNi = new HashMap<Integer, Double>();

        //simple constructor, gives every node a different id
        private nodeInfo() {
            this.key = id++;
        }

        private nodeInfo(int key) {
            this.key = key;
        }

        //copy constructor of the class
        public nodeInfo(node_info n) {
            this.key = n.getKey();
            this.tag = n.getTag();
            this.info = n.getInfo();
        }

        @Override
//        Returns the key (id) associated with this node.
//        each node_data has a unique key.
        public int getKey() {
            return key;
        }

        @Override
//         Returns the remark (meta data) associated with this node.
        public String getInfo() {
            return info;
        }

        @Override
        // Allows changing the remark (meta data) associated with this node.
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        //Temporal data (aka distance, color, or state which used in the algorithms.
        public double getTag() {
            return tag;
        }

        @Override
        // Allow setting the "tag" value for temporal marking an node.
        public void setTag(double t) {
            this.tag = t;
        }

        //adds a neighbour node to this node and save the edges weight
        public void addNi(nodeInfo n, double i) {
            if(this.getKey()!=n.getKey()){
            myNi.put(n.getKey(), i);
            lis.put(n.getKey(), n);}
        }

        //returns the distance between two node(the edge's weight)
        public Double getDist(node_info n) {
            return myNi.get(n.getKey());
        }

        // returns this node neighbours as an hashmap
        public HashMap<Integer, Double> getV() {
            return myNi;
        }

        // returns this node neighbours as a collection
        public Collection<node_info> getLis() {
            return  lis.values();
        }

        //remove a specific node from this node's records
        public void removeNi(nodeInfo n) {
            if (this.myNi.containsKey(n.getKey()))
                this.myNi.remove(n.getKey());
            this.lis.remove(n);
        }
    }

    private HashMap<Integer, nodeInfo> myMap;
    private HashMap<Integer, node_info> lis;
    private int edges ;
    private int mc ;


    //     simple constructor for the graph
    public WGraph_DS(){
        this.edges=0;
        this.mc=0;
        myMap= new HashMap<Integer, nodeInfo>();
        lis=new HashMap<Integer, node_info>();
    }

    @Override
    //     returns the node_data by the node_id, null if none.
    public node_info getNode(int key) {
        if (myMap.containsKey(key)) {
            return myMap.get(key);
        }
        return null;
    }

    //    returns the object 'nodeInfo' as I wrote it.
    public nodeInfo getMyNode(int key) {
        if (myMap.containsKey(key)) {
            return myMap.get(key);
        }
        return null;
    }

    @Override
    //   return true if and only if there is an edge between node1 and node2
    public boolean hasEdge(int node1, int node2) {
        if (myMap != null && myMap.containsKey(node1) && myMap.containsKey(node2) && myMap.get(node1).getV().containsKey(node2))
            return true;
        return false;
    }

    @Override
    //     * returns the weight of the edge (node1, node1). If there is no such edge returns -1.
    public double getEdge(int node1, int node2) {
        if (myMap != null && myMap.containsKey(node1) && myMap.containsKey(node2) && myMap.get(node1).getV().containsKey(node2))
            return myMap.get(node1).getDist(myMap.get(node2));
        return -1;
    }

    @Override
    //      add a new node to the graph with the given key.
    //      if there is already a node with such a key, no action performed.
    public void addNode(int key) {
        if(myMap.containsKey(key)){
            return;
        }
        nodeInfo n = new nodeInfo(key);
        this.myMap.put(n.getKey(), n);
        this.lis.put(n.getKey(), n);
        mc++;
    }

    @Override
    //     Connect an edge between node1 and node2, with an edge with weight >=0.
    //     if the edge node1-node2 exists, the method updates the weight of the edge.
    public void connect(int node1, int node2, double w) {
        if (node1!=node2&&myMap != null && myMap.containsKey(node1) && myMap.containsKey(node2)
                && (getEdge(node1,node2)==w||w<0.0)) {
            return;
        }else{
            if (node1!=node2&&myMap != null && myMap.containsKey(node1) && myMap.containsKey(node2)
                && !myMap.get(node1).getV().containsKey(node2)) {
            myMap.get(node1).addNi(myMap.get(node2), w);
            myMap.get(node2).addNi(myMap.get(node1), w);
            edges++;
            mc++;
        }else{
        if (node1!=node2&&myMap != null && myMap.containsKey(node1) && myMap.containsKey(node2)) {
            myMap.get(node1).addNi(myMap.get(node2), w);
            myMap.get(node2).addNi(myMap.get(node1), w);
            mc++;
        }}}
    }

    @Override
    //     Returns a pointer for a collection representing all the nodes in the graph.
    public Collection<node_info> getV() {
        return lis.values();
    }

    @Override
    //     Returns a Collection containing all the nodes connected to node_id.
    public Collection<node_info> getV(int node_id) {
        return myMap.get(node_id).lis.values();
    }

    @Override
    //     Deletes the given node from the graph and removes all edges which starts or ends at this node.
    public node_info removeNode(int key) {
        if(myMap.get(key)!=null&&!myMap.get(key).getLis().isEmpty())
        for (node_info i : myMap.get(key).getLis()) {
            myMap.get(i.getKey()).removeNi(myMap.get(key));
            edges--;
            mc++;
        }
        node_info n = (node_info)myMap.get(key);
        myMap.remove(key);
        lis.remove(key);
        return n;
    }

    @Override
    //     Deletes the edge from the graph.
    public void removeEdge(int node1, int node2) {
        if (node1!=node2&&myMap != null && myMap.containsKey(node1) && myMap.containsKey(node2)
                && myMap.get(node1).getV().containsKey(node2)) {
            myMap.get(node1).removeNi(myMap.get(node2));
            myMap.get(node2).removeNi(myMap.get(node1));
            edges--;
            mc++;
        }
    }

    @Override
    //   Returns the number of nodes in the graph.
    public int nodeSize() {
        return myMap.size();
    }

    @Override
    //  Returns the number of edges in the graph.
    public int edgeSize() {
        return edges;
    }

    @Override
    //  Return the number of changes which made in the graph.
    public int getMC() {
        return mc;
    }

    //   Returns the hashmap contains the graph for shorter actions
    public HashMap<Integer,nodeInfo> getMap(){
        return myMap;
    }

    //    Returns true iff the graphs are equals(by pointers or by value)
    public boolean equals(Object second) {
        WGraph_DS other=(WGraph_DS)second;
        boolean boom=true;
        if(this.getMap().size()!=other.getMap().size()) {
            return false;
        }
        for(node_info i: this.getV()) {
            for(node_info j: this.getV(i.getKey()) ) {
                boom&=other.hasEdge(i.getKey(), j.getKey());
            }
        }
        return boom;
    }
}
