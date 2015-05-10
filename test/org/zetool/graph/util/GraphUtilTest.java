
package org.zetool.graph.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.zetool.graph.DirectedGraph;
import org.zetool.graph.Edge;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class GraphUtilTest {

  @Test
  public void testEmptyGraphGeneration() {
    DirectedGraph emptyGraph = GraphUtil.generateDirected( 0, new int[][]{} );
    DirectedGraph noEdges = GraphUtil.generateDirected( 1, new int[][]{} );

    assertThat( emptyGraph.nodeCount(), is( equalTo( 0 ) ) );
    assertThat( emptyGraph.edgeCount(), is( equalTo( 0 ) ) );
    for( Edge e : emptyGraph.edges() ) {
      throw new AssertionError( "Iterating should not be possible!" );
    }

    assertThat( noEdges.nodeCount(), is( equalTo( 1 ) ) );
    assertThat( noEdges.edgeCount(), is( equalTo( 0 ) ) );
    for( Edge e : noEdges.edges() ) {
      throw new AssertionError( "Iterating should not be possible!" );
    }
  }

  @Test
  public void testGraphGeneration() {
    DirectedGraph emptyGraph = GraphUtil.generateDirected( 2, new int[][]{{1, 0}} );
    DirectedGraph noEdges = GraphUtil.generateDirected( 3, new int[][]{{0, 1}, {2, 1}, {0, 2}} );

    assertThat( emptyGraph.nodeCount(), is( equalTo( 2 ) ) );
    assertThat( emptyGraph.edgeCount(), is( equalTo( 1 ) ) );
    int count = 0;
    for( Edge e : emptyGraph.edges() ) {
      count++;
      assertThat( e.start().id(), is( equalTo( 1 ) ) );
      assertThat( e.end().id(), is( equalTo( 0 ) ) );
    }
    assertThat( count, is( equalTo( 1 ) ) );

    assertThat( noEdges.nodeCount(), is( equalTo( 3 ) ) );
    assertThat( noEdges.edgeCount(), is( equalTo( 3 ) ) );
    assertThat( edgesBetweenNodes( noEdges, 0, 1 ), is( equalTo( 1 ) ) );
    assertThat( edgesBetweenNodes( noEdges, 2, 1 ), is( equalTo( 1 ) ) );
    assertThat( edgesBetweenNodes( noEdges, 0, 2 ), is( equalTo( 1 ) ) );
  }

  public static int edgesBetweenNodes( DirectedGraph g, int start, int end ) {
    int count = 0;
    for( Edge e : g.edges() ) {
      if( e.start().id() == start && e.end().id() == end ) {
        count++;
      }
    }
    return count;
  }
}
