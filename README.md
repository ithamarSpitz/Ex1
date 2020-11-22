# Ex1
this program should create an undirected graph
there are some explanations about the objects wrote for it:


class nodeInfo

*first i biult a simple constructor, gives every node a different id.

*The function getKey returns the key (id) associated with this node. each node_data has a unique key.

*The function getInfo returns the remark (meta data) associated with this node.

*The function setInfo allows changing the remark (meta data) associated with this node.

*The function getTag temporal data (aka distance, color, or state which used in the algorithms.

*The function setTag allows setting the "tag" value for temporal marking an node.

*The function addNi adds a neighbour node to this node and save the edges weight

*The function getDist returns the distance between two node(the edge's weight)

*The function getV returns this node neighbours as an hashmap

*The function getLis returns this node neighbours as a collection

*The function removeNi removes a specific node from this node's records


class WGraph_DS 

*first i biult a simple constructor for the graph.

*The function getNode returns the node_data by the node_id, null if none.

*The function getMyNode returns the object 'nodeInfo' as I wrote it.

*The function hasEdge returns true if and only if there is an edge between node1 and node2

*The function getEdge returns the weight of the edge (node1, node1). If there is no such edge returns -1.

*The function addNode add a new node to the graph with the given key. if there is already a node with such a key, no action performed.

*The function connect Connect an edge between node1 and node2, with an edge with weight >=0. if the edge node1-node2 exists, the method updates the weight of the edge.

*The function getV returns a pointer for a collection representing all the nodes in the graph.

*The function getV returns a Collection containing all the nodes connected to node_id.

*The function removeNode deletes the given node from the graph and removes all edges which starts or ends at this node.


*The function removeEdge deletes the edge from the graph.

*The function nodeSize returns the number of nodes in the graph.

*The function edgeSize returns the number of edges in the graph.

*The function getMC return the number of changes which made in the graph.

*The function getMap returns the hashmap contains the graph for shorter actions

*The function equals returns true iff the graphs are equals(by pointers or by value)


class WGraph_Algo

*first i biult a simple constructor for the algorithms.

*The function init Inits the graph on which this set of algorithms operates on.

*The function getGraph returns the graph of which this class works.

*The function copy computes a deep copy of this weighted graph.

*The function isConnected returns true if and only if there is a valid path from every node to other node. using bfs algorithm as I did in ex0 with minor changes.

*The function shortestPathDist returns the the shortest path between src to dest as an ordered List of nodes. if no such path returns -1.
uses the shortestPath function and another function that sums the distances.

*The function shortestPath returns the the shortest path between src to dest as an ordered List of nodes. if no such path returns empty list.
  using dijkstra's algorithm, rating every node on the way by its distance from src node.

*The function save saves this graph to the given file name. Return true if the file was successfully saved.

*The function load loads a graph to this graph algorithm. if the file was successfully loaded returns true, in case the graph was not loaded the original graph doesn't change.

*The function pathDist returns the sum of distances in a path of nodes

*The function BFS bfs algorithm from ex0, checks connectivity of the graph.

*The function cleanTag sets back the graph's node's tags to 0
  
