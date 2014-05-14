
package de.tu_berlin.coga.graph.traversal;

import de.tu_berlin.coga.graph.DirectedGraph;
import java.util.LinkedList;


/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class GeneralBreadthFirstSearchIterator extends AbstractGraphSearchIterator {

  public GeneralBreadthFirstSearchIterator( DirectedGraph g ) {
    this( g, false );
  }

  public GeneralBreadthFirstSearchIterator( DirectedGraph g, boolean iterateAllEdges ) {
    super( g, iterateAllEdges, false, new LinkedList<>() );
  }

  public GeneralBreadthFirstSearchIterator( DirectedGraph g, int startNode ) {
    this( g, startNode, false );
  }

  public GeneralBreadthFirstSearchIterator( DirectedGraph g, int startNode, boolean iterateAllEdges ) {
    this( g, startNode, iterateAllEdges, false );
  }

  public GeneralBreadthFirstSearchIterator( DirectedGraph g, int startNode, boolean iterateAllEdges, boolean reverse ) {
    super( g, startNode, iterateAllEdges, reverse, new LinkedList<>() );
  }
}
