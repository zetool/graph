/**
 * EdgeNodeTuple.java
 * Created: 12.05.2014, 18:00:47
 */
package de.tu_berlin.coga.graph;

import ds.graph.Edge;
import ds.graph.Node;
import java.util.Objects;


/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class EdgeNodePair {
  private Node node;
  private Edge predecessor;

  public EdgeNodePair( Node node, Edge predecessor ) {
    this.node = Objects.requireNonNull( node, "Node is null." );
    this.predecessor = predecessor;
    if( predecessor != null && !(predecessor.isIncidentTo( node ) ) )
      throw new IllegalStateException( "The edge is not incident to the node!" );
  }

  public Node getNode() {
    return node;
  }

  public Edge getPred() {
    return predecessor;
  }

  public boolean hasPredecessor() {
    return predecessor != null;
  }

  @Override
  public String toString() {
    return "EdgeNodePair{" + "node=" + node + ", pred=" + predecessor + '}';
  }


}
