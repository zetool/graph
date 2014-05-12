
package de.tu_berlin.coga.graph;

import de.tu_berlin.coga.graph.DirectedGraph;
import java.util.LinkedList;


/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class GeneralBreadthFirstSearchIterator extends AbstractGraphSearchIterator {

  public GeneralBreadthFirstSearchIterator( DirectedGraph g ) {
    super( g, new LinkedList<>() );
  }

  public GeneralBreadthFirstSearchIterator( DirectedGraph g, int startNode ) {
    super( g, startNode, new LinkedList<>() );
  }

  public GeneralBreadthFirstSearchIterator( DirectedGraph g, int startNode, boolean checkAllNodes ) {
    super( g, startNode, checkAllNodes, new LinkedList<>() );
  }
}
