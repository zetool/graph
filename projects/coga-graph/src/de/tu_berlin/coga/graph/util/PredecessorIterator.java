package de.tu_berlin.coga.graph.util;

import de.tu_berlin.coga.graph.Edge;
import de.tu_berlin.coga.graph.Node;
import java.util.Iterator;

/**
 * Iterates over the predecessors of a given vertex until a source is reached.
 * That is, we assume to be given a predecessor tree, where each node {@code v}
 * has an preceeding edge {@code e} such that the end node of {@code e} equals
 * {@code v}. The iterator then iterates along all preceeding edges until the
 * preceeding edge of a node is {@code null}.
 * @author Jan-Philipp Kappmeier
 */
public class PredecessorIterator implements Iterator<Edge> {
  /** The current vertex. */
  private Node current;
  /** The edge that is returned as next preceeding edge. */
  private Edge next;
  /** The predecessor map storing the prdecessor information. */
  private final PredecessorMap<Edge,Node> pred;

  public PredecessorIterator( Node current, PredecessorMap<Edge,Node> pred ) {
    this.current = current;
    this.pred = pred;
  }

  @Override
  public boolean hasNext() {
    next = pred.getPredecessor( current );
    if( next != null ) {
      current = next.start();
      return true;
    }
    return false;
  }

  @Override
  public Edge next() {
    return next;
  }
}
