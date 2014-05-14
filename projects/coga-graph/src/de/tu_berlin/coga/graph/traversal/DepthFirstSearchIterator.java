
package de.tu_berlin.coga.graph.traversal;

import de.tu_berlin.coga.graph.DirectedGraph;
import ds.graph.Node;
import java.util.Iterator;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DepthFirstSearchIterator implements Iterator<Node> {
  private GeneralDepthFirstSearchIterator bfs;

  public DepthFirstSearchIterator( DirectedGraph g ) {
    bfs = new GeneralDepthFirstSearchIterator( g );
  }

  public DepthFirstSearchIterator( DirectedGraph g, int startNode ) {
   bfs = new GeneralDepthFirstSearchIterator( g, startNode, true );
  }

  public DepthFirstSearchIterator( DirectedGraph g, int startNode, boolean onlyOneComponent ) {
    bfs = new GeneralDepthFirstSearchIterator( g, startNode, onlyOneComponent );
  }

  @Override
  public boolean hasNext() {
    return bfs.hasNext();
  }

  @Override
  public Node next() {
    return bfs.next().getNode();
  }
}
