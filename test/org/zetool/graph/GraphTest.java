/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.zetool.graph;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Graphs for unit testing.
 *
 * @author Jan-Philipp Kappmeier
 */
public class GraphTest {

  @Test
  public void testStringRepresentation() {
    // Empty graph
    Graph g = new DefaultGraph(0,10);
    String graphString = DirectedGraph.stringRepresentation( g );
    assertEquals( "({}, {})", graphString );
    
    // Only one node
    g = new DefaultGraph(1,10);
    graphString = DirectedGraph.stringRepresentation( g );
    assertEquals( "({0}, {})", graphString );
    
    // More nodes
    g = new DefaultGraph(3,10);
    graphString = DirectedGraph.stringRepresentation( g );
    assertEquals( "({0, 1, 2}, {})", graphString );
    
    // One edge
    g = new DefaultGraph(3,10);
    Edge e = new Edge(0, g.getNode( 0 ), g.getNode( 1 ) );
    ((DefaultGraph)g).setEdge( e );
    graphString = DirectedGraph.stringRepresentation( g );
    assertEquals( "({0, 1, 2}, {" + e.toString() + "})", graphString );
    
    // Two edges
    g = new DefaultGraph(3,10);
    e = new Edge(0, g.getNode( 0 ), g.getNode( 1 ) );
    Edge f = new Edge(1, g.getNode( 1 ), g.getNode( 2 ) );
    ((DefaultGraph)g).setEdge( e );
    ((DefaultGraph)g).setEdge( f );
    graphString = DirectedGraph.stringRepresentation( g );
    assertEquals( "({0, 1, 2}, {" + e.toString() + ", " + f.toString() + "})", graphString );
  }
  
  /** A sample graph containing all classes of edges and a loop. */
  public static final int[][] DFS_GRAPH = {
    {0, 1},
    {0, 4},
    {0, 7},
    {1, 2},
    {2, 3},
    {3, 1},
    {4, 5},
    {4, 4},
    {5, 2},
    {5, 6},
    {5, 7}
  };
  public static final int[][] DFS_GRAPH2 = {
    {0, 3},
    {1, 0},
    {2, 1},
    {2, 4},
    {2, 7},
    {3, 1},
    {4, 5},
    {4, 4},
    {5, 0},
    {5, 6},
    {5, 7}
  };

  /**
   * Generates a directed graph out of edges in an array.
   *
   * @param nodes the number of n odes
   * @param edges the edges
   * @return a directed graph
   */
  public static DirectedGraph generateDirected( int nodes, int[][] edges ) {
    DefaultDirectedGraph g = new DefaultDirectedGraph( nodes, edges.length );

    for( int i = 0; i < edges.length; ++i ) {
      g.createAndSetEdge( g.getNode( edges[i][0] ), g.getNode( edges[i][1] ) );
    }

    return g;
  }

  public static UndirectedGraph generateUndirected( int nodes, int[][] edges ) {
    SimpleUndirectedGraph g = new SimpleUndirectedGraph( nodes );

    for( int i = 0; i < edges.length; ++i ) {
      g.addEdge( edges[i][0], edges[i][1] );
    }

    return g;
  }
}
