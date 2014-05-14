
package de.tu_berlin.coga.graph;

import de.tu_berlin.coga.container.mapping.IdentifiableBooleanMapping;
import de.tu_berlin.coga.container.mapping.IdentifiableConstantMapping;
import ds.graph.Edge;
import ds.graph.Node;
import java.util.Iterator;


/**
 * Utility methods for graphs.
 * @author Jan-Philipp Kappmeier
 */
public class GraphUtil {
  /** Unit edge weights. */
	public final static IdentifiableConstantMapping<Edge> UNIT_EDGE_MAPPING = new IdentifiableConstantMapping<>( 1 );
  /** Unit node weights. */
	public final static IdentifiableConstantMapping<Node> UNIT_NODE_MAPPING = new IdentifiableConstantMapping<>( 1 );

  /**
   * Returns a unified access for a given graph.
   * @param g the underlying graph
   * @return an object providing unified access for a given graph
   * @throws ClassCastException if the graphs are not {@link DirectedGraph} or {@link UndirectedGraph}.
   */
  public static UnifiedGraphAccess getUnifiedAccess( Graph g ) throws ClassCastException {
    return getUnifiedAccess( g, false );
  }

  public static UnifiedGraphAccess getUnifiedAccess( Graph g, boolean reverse ) throws ClassCastException {
    if( g.isDirected() ) {
      return reverse ? new ReverseGraphAccess( (DirectedGraph)g ) : new DirectedGraphAccess( (DirectedGraph)g );
    } else {
      return new UndirectedGraphAccess( (UndirectedGraph)g );
    }
  }

  /**
   * Unified graph access for undirected graphs.
   */
  private static class UndirectedGraphAccess implements UnifiedGraphAccess {
    private UndirectedGraph g;
    private IdentifiableBooleanMapping<Edge> seen;

    private UndirectedGraphAccess( UndirectedGraph g ) {
      this.g = g;
      seen = new IdentifiableBooleanMapping<>( g.edgeCount() );
    }

    @Override
    public Node getEnd( Edge e, Node n ) {
      return e.opposite( n );
    }

    @Override
    public Iterator<Edge> incident( Node n ) {
      final Iterator<Edge> baseIter = g.incidentEdges( n ).iterator();
      return new Iterator<Edge>() {
        private Edge next;

        @Override
        public boolean hasNext() {
          while( baseIter.hasNext() ) {
            next = baseIter.next();
            if( !seen.get( next ) ) {
              seen.set( next, true );
              return true;
            }
          }
          return false;
        }

        @Override
        public Edge next() {
          return next;
        }
      };
    }
  }

  /**
   * Unified graph access for directed graphs.
   */
  private static class DirectedGraphAccess implements UnifiedGraphAccess {
    private DirectedGraph g;

    private DirectedGraphAccess( DirectedGraph g ) {
      this.g = g;
    }

    @Override
    public Node getEnd( Edge e, Node n ) {
      assert e.start().equals( n );
      return e.end();
    }

    @Override
    public Iterator<Edge> incident( Node n ) {
      return g.outgoingEdges( n ).iterator();
    }
  }

  /**
   * Unified graph access for directed graphs.
   */
  private static class ReverseGraphAccess implements UnifiedGraphAccess {
    private DirectedGraph g;

    private ReverseGraphAccess( DirectedGraph g ) {
      this.g = g;
    }

    @Override
    public Node getEnd( Edge e, Node n ) {
      assert e.end().equals( n );
      return e.start();
    }

    @Override
    public Iterator<Edge> incident( Node n ) {
      return g.incomingEdges( n ).iterator();
    }
  }
}
