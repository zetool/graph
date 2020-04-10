
package org.zetool.graph.util;

import org.zetool.graph.DirectedGraph;
import org.zetool.graph.Edge;
import org.zetool.graph.Graph;
import org.zetool.graph.Node;
import org.zetool.graph.UndirectedGraph;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

/**
 * An iterator that iterates over either incoming arcs (for a directed graph) or
 * all adjacent arcs (for undirected graph).
 * @author Jan-Philipp Kappmeier
 */
public class IncomingIterator implements Iterable<Edge> {

  private Graph g;
  private Node n;
  
  IncomingIterator( Graph g, Node n ) {
    this.g = Objects.requireNonNull( g , "Graph must not be null." );
    this.n = Objects.requireNonNull( n , "Node must not be null." );
  }

  @Override
  public Iterator<Edge> iterator() {
    if( g instanceof DirectedGraph ) {
      return ((DirectedGraph)g).incomingEdges( n ).iterator();
    } else if( g instanceof UndirectedGraph ) {
      //return Collections.emptyIterator(); //((UndirectedGraph)g).incidentEdges( n ).iterator();
      return ((UndirectedGraph)g).incidentEdges( n ).iterator();
    } else {
      throw new AssertionError( "Only UndirectedGraph and DirectedGraph supported!" );
    }
  }
  
}
