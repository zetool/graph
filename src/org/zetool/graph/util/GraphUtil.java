
package org.zetool.graph.util;

import org.zetool.container.mapping.IdentifiableBooleanMapping;
import org.zetool.container.mapping.IdentifiableConstantMapping;
import org.zetool.graph.DefaultDirectedGraph;
import org.zetool.graph.DirectedGraph;
import org.zetool.graph.Graph;
import org.zetool.graph.UndirectedGraph;
import org.zetool.graph.Edge;
import org.zetool.graph.Node;
import org.zetool.graph.structure.Path;
import org.zetool.graph.structure.StaticPath;
import org.zetool.graph.traversal.BreadthFirstSearch;
import java.util.Iterator;
import org.zetool.graph.SimpleUndirectedGraph;


/**
 * Utility methods for graphs.
 * @author Jan-Philipp Kappmeier
 */
public class GraphUtil {
  /** Unit edge weights. */
	public static final IdentifiableConstantMapping<Edge> UNIT_EDGE_MAPPING = new IdentifiableConstantMapping<>( 1 );
  /** Unit node weights. */
	public static final IdentifiableConstantMapping<Node> UNIT_NODE_MAPPING = new IdentifiableConstantMapping<>( 1 );

  /** Private constructor for utility class. */
  private GraphUtil() {
  }

  public static Iterable<Edge> outgoingIterator( Graph g, Node n ) {
    return new OutgoingIterator( g, n );
  }

  public static Iterable<Edge> incomingIterator( Graph g, Node n ) {
    return new IncomingIterator( g, n );
  }
  
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
   * Computes a graph beteen two vertices in a directed graph, if there is
   * such a path.
   * @param g the graph
   * @param start the start vertex
   * @param end the end vertex
   * @return a path between to vertices if such a path exists, or {@code null}
   */
  public static Path getPath( Graph g, Node start, Node end ) {
    // start a breadth first search from the start vertex until the end vertex
    // is reached.
    BreadthFirstSearch bfs = new BreadthFirstSearch();
    bfs.setStart( start );
    bfs.setStop( end );
    bfs.run();

    // compute the path based on the predecessors computed by the breadth
    // first search. if a pa exists, return it, otherwise null.
    Path path = new StaticPath( bfs );
    if( path.first().start().equals( start ) ) {
      return path;
    } else {
      return null;
    }
  }

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
  
	/**
	 * Creates a network equal to the network but all edges between a pair of
	 * nodes are reversed.
   * @param g the graph that is reversed
	 * @return a reversed copy of the network
	 */
	public static DirectedGraph createReverseNetwork( DirectedGraph g ) {
		DefaultDirectedGraph result = new DefaultDirectedGraph( g.nodeCount(), g.edgeCount() );
		for( Edge edge : g.edges() )
			result.createAndSetEdge( edge.end(), edge.start() );
		return result;
	}

  /**
   * Unified graph access for undirected graphs.
   */
  private static class UndirectedGraphAccess implements UnifiedGraphAccess {
    private final UndirectedGraph g;
    private final IdentifiableBooleanMapping<Edge> seen;

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
    private final DirectedGraph g;

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
    private final DirectedGraph g;

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
