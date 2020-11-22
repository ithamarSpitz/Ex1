package ex1;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class myTests {
    private static Random _rnd = null;

    @Test
    void checkCopy() {
        weighted_graph graph= graph_creator(100,110,6);
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(graph);
        weighted_graph copy=algo.copy();
        assertEquals(copy,graph);
    }

    @Test
    void VSize() {
        weighted_graph graph = new WGraph_DS();
        for(int i =0; i<20; i++ ){
            graph.addNode(i);
        }
        graph.addNode(3);
        graph.removeNode(2);
        graph.removeNode(2);
        graph.removeNode(3);
        assertEquals(18,graph.nodeSize());
    }

    @Test
    void ESize() {
        weighted_graph graph = new WGraph_DS();
        for (int i = 0; i < 10; i++) {
        graph.addNode(i);
        }
        for (int i = 0; i < 4; i++) {
            graph.connect(0,i,2.0+i);
        }//3 edges
        graph.connect(0,1,8.0);
        int sizeE =  graph.edgeSize();
        assertEquals(3, sizeE);
        assertEquals(graph.getEdge(3,0),graph.getEdge(0,3));
        assertEquals(graph.getEdge(0,1), 8.0);
    }

    @Test
    void getVTest() {
        weighted_graph graph = new WGraph_DS();
        for (int i = 0; i < 50; i++) {
            graph.addNode(0);
        }

        for(node_info node: graph.getV()){
            assertNotNull(node);
        }
    }

    @Test
    void testConnect() {
        weighted_graph graph = new WGraph_DS();
        for (int i = 0; i < 9; i++) {
            graph.addNode(i);
        }
        for (int i = 0 , j=8; i <9 ; i++,j--) {
            graph.connect(i,j,(i+j)/2);
        }
        assertEquals(4.0,graph.getEdge(6,2));
        assertEquals(17,graph.getMC());
        assertEquals(-1,graph.getEdge(1,3));
        assertFalse(graph.hasEdge(8,3));
        graph.removeEdge(0,8);
        graph.removeEdge(8,0);
        graph.removeEdge(0,1);
        graph.connect(0,1,1);
        assertEquals(graph.getEdge(8,2),-1);
        assertEquals(graph.getEdge(8,0),4);
    }

    @Test
    void removeNode() {
        weighted_graph graph = new WGraph_DS();
        for (int i = 0; i < 9; i++) {
            graph.addNode(i);
        }
        for (int i = 0 , j=8; i <9 ; i++,j--) {
            graph.connect(i,j,(i+j)/2);
        }
        graph.removeNode(1);
        graph.removeNode(1);
        graph.removeNode(0);
        assertNull(graph.removeNode(10));
        assertFalse(graph.hasEdge(8,0));
        assertEquals(6,graph.edgeSize());
        assertEquals(7,graph.nodeSize());
    }
    /**
     * Checks that the arc has been deleted from the graph.
     */
    @Test
    void removeEdge() {
        weighted_graph graph = new WGraph_DS();
        for (int i = 0; i < 9; i++) {
            graph.addNode(i);
        }
        for (int i = 0 , j=8; i <9 ; i++,j--) {
            graph.connect(i,j,(i+j)/2);
        }
        graph.removeEdge(0,8);
        graph.removeEdge(4,4);
        graph.removeEdge(5,3);
        graph.removeEdge(0,3);
        graph.removeEdge(3,5);
        assertEquals(graph.getEdge(0,1),-1);
        assertEquals(graph.getEdge(3,5),-1);
        assertEquals(graph.getEdge(1,7),4.0);
    }

    @Test
    void hasEdge() {
        weighted_graph g = graph_creator(50,49*25,5);
        boolean b = true;
        for(int i=0;i<50;i++) {
            for(int j=i+1;j<50;j++) {
                b &= g.hasEdge(i,j);
                assertTrue(b);
                assertTrue(g.hasEdge(j,i));
            }
        }
    }
    /**
     * Checks that the graph is a link graph - that is, if there is an arc between any two vertices in the graph.
     */
    @Test
    void isConnected() {
        weighted_graph graph=graph_creator(0,0,1);
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(graph);
        assertTrue(algo.isConnected());

        graph=graph_creator(0,0,1);
        algo.init(graph);
        assertTrue(algo.isConnected());

        graph=graph_creator(1,0,1);
        algo.init(graph);
        assertTrue(algo.isConnected());


        graph=graph_creator(2,1,1);
        algo.init(graph);
        assertTrue(algo.isConnected());

        graph=graph_creator(15,13,1);
        algo.init(graph);
        assertFalse(algo.isConnected());

        graph=graph_creator(15,23,1);
        algo.init(graph);
        assertFalse(algo.isConnected());

        graph=graph_creator(8,16,1);
        algo.init(graph);
        boolean b=algo.isConnected();
        assertTrue(b);
    }

    @Test
    void shortestPathDist() {
        weighted_graph_algorithms algo = new WGraph_Algo();
        weighted_graph graph=new WGraph_DS();
        for(int i =0; i<20; i++ ){
            graph.addNode(i);
        }
        int sum =0;
        for(int i =0; i<20; i++ ){
            sum=(i*3)/(i*i);
            graph.connect(i,i+1,(i*3)/(i*i));
        }
        assertTrue(algo.isConnected());
        double ans = algo.shortestPathDist(0,19);
        assertEquals(ans, sum);
    }

    @Test
    void shortestPath() {
        weighted_graph_algorithms algo = new WGraph_Algo();
        weighted_graph graph=new WGraph_DS();
        for(int i =0; i<20; i++ ){
            graph.addNode(i);
        }
        for(int i =0; i<20; i++ ){
            graph.connect(i,i+1,(i*3)/(i*i));
        }
        int[] way = new int[19];
        for (int i = 0; i < 20; i++) {
            way[i]=i;
        }
        algo.init(graph);
        int i = 0;
        for(node_info n: algo.shortestPath(0,19)) {
            assertEquals(n.getKey(), way[i]);
            i++;
        }
    }

    @Test
    void saveAndLoad() {
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(graph_creator(1000,3000,50));
        String str = "g0.obj";
        algo.save(str);
        weighted_graph g1 =graph_creator(10,30,1);
        algo.load(str);
        assertEquals(algo.getGraph(),g1);
        algo.getGraph().removeNode(0);
        assertNotEquals(algo.getGraph(),g1);
    }

    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }

    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes);
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}
