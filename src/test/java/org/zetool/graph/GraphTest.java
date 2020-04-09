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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
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
  public void testStringRepresentationEmpty() {
    Graph g = new DefaultGraph( 0, 10 );
    String actualString = DirectedGraph.stringRepresentation( g );
    assertEquals( "({}, {})", actualString );
  }

  @Test
  public void testStringRepresentationOneNode() {
    Graph g = new DefaultGraph( 1, 10 );
    String actualString = DirectedGraph.stringRepresentation( g );
    assertEquals( "({0}, {})", actualString );
  }

  @Test
  public void testStringRepresentationMultipleNodes() {
    Graph g = new DefaultGraph( 3, 10 );
    String actualString = DirectedGraph.stringRepresentation( g );
    assertEquals( "({0, 1, 2}, {})", actualString );
  }

  @Test
  public void testStringRepresentationOneEdge() {
    Graph g = new DefaultGraph( 3, 10 );
    Edge e = new Edge( 0, g.getNode( 0 ), g.getNode( 1 ) );
    ((DefaultGraph) g).setEdge( e );
    String actualString = DirectedGraph.stringRepresentation( g );
    assertEquals( "({0, 1, 2}, {" + e.toString() + "})", actualString );
  }

  @Test
  public void testStringRepresentationMultipleEdges() {
    Graph g = new DefaultGraph( 3, 10 );
    Edge e = new Edge( 0, g.getNode( 0 ), g.getNode( 1 ) );
    Edge f = new Edge( 1, g.getNode( 1 ), g.getNode( 2 ) );
    ((DefaultGraph) g).setEdge( e );
    ((DefaultGraph) g).setEdge( f );
    String actualString = DirectedGraph.stringRepresentation( g );
    assertEquals( "({0, 1, 2}, {" + e.toString() + ", " + f.toString() + "})", actualString );
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
}
