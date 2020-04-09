
package org.zetool.graph.traversal;

import org.zetool.graph.Edge;
import org.zetool.graph.Node;
import java.util.Objects;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class EdgeNodePair {
  private Node node;
  private Edge predecessor;

  /**
   *
   * @param node
   * @param predecessor
   */
  public EdgeNodePair( Node node, Edge predecessor ) {
    this.node = Objects.requireNonNull( node, "Node is null." );
    this.predecessor = predecessor;
    if( predecessor != null && !(predecessor.isIncidentTo( node ) ) )
      throw new IllegalStateException( "The edge is not incident to the node!" );
  }

  /**
   *
   * @return
   */
  public Node getNode() {
    return node;
  }

  /**
   *
   * @return
   */
  public Edge getPred() {
    return predecessor;
  }

  /**
   *
   * @return
   */
  public boolean hasPredecessor() {
    return predecessor != null;
  }

  @Override
  public String toString() {
    return "EdgeNodePair{" + "node=" + node + ", pred=" + predecessor + '}';
  }
}
