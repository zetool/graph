
package de.tu_berlin.coga.graph.traversal;

import de.tu_berlin.coga.graph.DirectedGraph;
import de.tu_berlin.coga.graph.Node;
import java.util.Iterator;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DepthFirstSearchIterator implements Iterator<Node> {
  private final GeneralDepthFirstSearchIterator dfs;

  public DepthFirstSearchIterator( DirectedGraph g ) {
    dfs = new GeneralDepthFirstSearchIterator( g );
  }

  public DepthFirstSearchIterator( DirectedGraph g, int startNode ) {
   dfs = new GeneralDepthFirstSearchIterator( g, startNode, true );
  }

  public DepthFirstSearchIterator( DirectedGraph g, int startNode, boolean onlyOneComponent ) {
    dfs = new GeneralDepthFirstSearchIterator( g, startNode, onlyOneComponent );
  }

  @Override
  public boolean hasNext() {
    return dfs.hasNext();
  }

  @Override
  public Node next() {
    return dfs.next().getNode();
  }
}
