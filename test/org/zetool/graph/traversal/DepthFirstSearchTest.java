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
package org.zetool.graph.traversal;

import org.zetool.graph.DirectedGraph;
import org.zetool.graph.GraphTest;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.zetool.graph.util.GraphUtil.generateDirected;
import static org.zetool.graph.util.GraphUtil.generateUndirected;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DepthFirstSearchTest {

    /**
     * Checks if the classification in graph edges works correctly.
     */
    @Test
    public void testClassification() {
    DepthFirstSearch dfs = new DepthFirstSearch();
    dfs.setProblem( generateDirected( 8, GraphTest.DFS_GRAPH ) );

    dfs.run();

    System.out.println( "Tree-Edges: " + dfs.treeEdges );
    assertEquals( 7, dfs.treeEdges.size() );
    System.out.println( "Back-Edges: " + dfs.backEdges );
    assertEquals( 2, dfs.backEdges.size() );
    System.out.println( "Forward-Edges: " + dfs.forwardEdges );
    assertEquals( 1, dfs.forwardEdges.size() );
    System.out.println( "Cross-Edges: " + dfs.crossEdges );
    assertEquals( 1, dfs.crossEdges.size() );
  }

  @Test
  public void testLoop() {
    DirectedGraph g = generateDirected( 1, new int[][]{{0,0}} );
    DepthFirstSearch dfs = new DepthFirstSearch();
    dfs.setProblem( g );
    dfs.run();
    assertEquals( 0, dfs.treeEdges.size() );
    assertEquals( 1, dfs.backEdges.size() );
    assertEquals( 0, dfs.forwardEdges.size() );
    assertEquals( 0, dfs.crossEdges.size() );
  }

  @Test
  public void undirectedTest() {
    DepthFirstSearch dfs = new DepthFirstSearch();
    dfs.setProblem( generateUndirected( 8, GraphTest.DFS_GRAPH ) );
    System.out.println( "Starting undirected run" );

    dfs.run();

    System.out.println( "Tree-Edges: " + dfs.treeEdges );
    assertEquals( 7, dfs.treeEdges.size() );
    System.out.println( "Back-Edges: " + dfs.backEdges );
    assertEquals( 4, dfs.backEdges.size() );
    System.out.println( "Forward-Edges: " + dfs.forwardEdges );
    assertEquals( 0, dfs.forwardEdges.size() );
    System.out.println( "Cross-Edges: " + dfs.crossEdges );
    assertEquals( 0, dfs.crossEdges.size() );
  }

  @Test
  public void reversedTest() {
    DepthFirstSearch dfs = new DepthFirstSearch();
    DirectedGraph g = generateDirected( 8, GraphTest.DFS_GRAPH2 );
    dfs.setProblem( g );
    dfs.setReverse( true );
    //dfs.setStart( g.getNode( 2 ) );

    System.out.println( "Starting reversed run" );
    dfs.run();
    System.out.println( "Tree-Edges: " + dfs.treeEdges );
    assertEquals( 5, dfs.treeEdges.size() );
    System.out.println( "Back-Edges: " + dfs.backEdges );
    assertEquals( 6, dfs.backEdges.size() );
    System.out.println( "Forward-Edges: " + dfs.forwardEdges );
    assertEquals( 0, dfs.forwardEdges.size() );
    System.out.println( "Cross-Edges: " + dfs.crossEdges );
    assertEquals( 0, dfs.crossEdges.size() );
  }

}
