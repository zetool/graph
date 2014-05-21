/**
 * BreadthFirstSearchIterator.java
 * Created: 12.05.2014, 18:00:30
 */
package de.tu_berlin.coga.graph.traversal;

import de.tu_berlin.coga.graph.DirectedGraph;
import de.tu_berlin.coga.graph.Node;
import java.util.Iterator;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class BreadthFirstSearchIterator implements Iterator<Node> {
  private GeneralBreadthFirstSearchIterator bfs;

  public BreadthFirstSearchIterator( DirectedGraph g ) {
    bfs = new GeneralBreadthFirstSearchIterator( g );
  }

  public BreadthFirstSearchIterator( DirectedGraph g, int startNode ) {
   bfs = new GeneralBreadthFirstSearchIterator( g, startNode, true );
  }

  public BreadthFirstSearchIterator( DirectedGraph g, int startNode, boolean onlyOneComponent ) {
    bfs = new GeneralBreadthFirstSearchIterator( g, startNode, onlyOneComponent );
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
