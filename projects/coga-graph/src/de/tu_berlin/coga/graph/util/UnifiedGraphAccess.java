
package de.tu_berlin.coga.graph.util;

import de.tu_berlin.coga.graph.Edge;
import de.tu_berlin.coga.graph.Node;
import java.util.Iterator;

/**
 * A class that simplifies graph traversal if an algorithm can run on both, directed and undirected graphs. It provides
 * iterators for iterating incident edges and also returns the end node of an edge starting at a given node. For the
 * undirected case the iterators take into account that double iteration cannot occur.
 *
 * <p>However, this class can only be used to iterate over incident edges of nodes in undirected graphs once because
 * any later generated iterators will skip the seen edges.</p>
 * @author Jan-Philipp Kappmeier
 */
public interface UnifiedGraphAccess {


  /**
   * Returns the node that is at the other end of an edge. If the underlying graph is directed, it is supposed that
   * {@code n} is the node {@code e.getStart()}.
   * @param e the edge
   * @param n the node
   * @return the end node (the node at the opposing end) of {@code n}
   */
  Node getEnd( Edge e, Node n );

  /**
   * Returns an iterator of the incident edges of node {@code n}. For directed graphs the iterator iterates over
   * outgoing arcs, for undirected graphs it iterates over all nodes. More than one iterator can be used per node for
   * undirected graphs, but only one iterator can be used in the undirected case.
   * @param n the node
   * @return an iterator of incident edges of node {@code n}
   */
  Iterator<Edge> incident( Node n );

}
