
package de.tu_berlin.coga.graph;

import ds.graph.Edge;
import ds.graph.Node;

/**
 * Returns the predecessor of a node.
 * @author Jan-Philipp Kappmeier
 */
public interface PredecessorMap extends Iterable<Edge> {
  /** Returns the predecessor edge for a given node.
   * @param n the node
   * @return the predecessor edge for a given node
   */
  Edge getPredecessor( Node n );
}
