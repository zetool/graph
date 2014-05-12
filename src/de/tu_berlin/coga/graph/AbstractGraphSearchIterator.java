/**
 * AbstractGraphSearchIterator.java
 * Created: 12.05.2014, 19:55:43
 */
package de.tu_berlin.coga.graph;

import de.tu_berlin.coga.container.mapping.IdentifiableBooleanMapping;
import de.tu_berlin.coga.graph.DirectedGraph;
import ds.graph.Edge;
import ds.graph.Node;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;


/**
 *
 * @author Jan-Philipp Kappmeier
 */
class AbstractGraphSearchIterator implements Iterator<EdgeNodePair> {
  private Queue<Iterator<Edge>> iteratorQ;


  private DirectedGraph g;
  private IdentifiableBooleanMapping<Node> seen;
  private int index = 0;
  private boolean checkAllNodes;
  private Iterator<Edge> currentIterator;
  private EdgeNodePair next;
  //private Queue<Iterator<Edge>> iteratorQ = new LinkedList<>();

  AbstractGraphSearchIterator( DirectedGraph g, Queue<Iterator<Edge>> ds ) {
    this.g = Objects.requireNonNull( g, "Graph should not be null!" );
    this.iteratorQ = Objects.requireNonNull( ds, "Datastructure shall not be null." );
    seen = new IdentifiableBooleanMapping<>( g.nodeCount() );
    findNextStart();
  }

  AbstractGraphSearchIterator( DirectedGraph g, int startNode, Queue<Iterator<Edge>> ds ) {
   this( g, startNode, true, ds );
  }

  AbstractGraphSearchIterator( DirectedGraph g, int startNode, boolean checkAllNodes, Queue<Iterator<Edge>> ds ) {
    this.g = Objects.requireNonNull( g, "Graph should not be null!" );
    this.iteratorQ = Objects.requireNonNull( ds, "Datastructure shall not be null." );
    seen = new IdentifiableBooleanMapping<>( g.nodeCount() );
    if( startNode >= g.nodeCount() ) {
      throw new IllegalArgumentException( "Node id " + startNode + " too high." );
    }
    if( g.getNode( startNode ) == null ) {
      throw new IllegalArgumentException( "Node id" + startNode + " not available in graph." );
    }
    //q.offer( new EdgeNodePair( g.getNode( startNode ), null ) );
    next = new EdgeNodePair( g.getNode( startNode ), null );
    iteratorQ.offer( g.incidentEdges( next.getNode() ).iterator() );
    seen.set( g.getNode( startNode ), true );
    this.checkAllNodes = checkAllNodes;
  }

  private void findNextStart() {
    for( Node n : g.nodes() ) {
      if( seen.get( n ) == false ) {
        next = new EdgeNodePair( n, null );
        //q.offer( next );
        iteratorQ.offer( g.incidentEdges( n ).iterator() );
        seen.set( n, true );
        return;
      }
    }
  }

  @Override
  public boolean hasNext() {
    return next != null;
  }

  @Override
  public EdgeNodePair next() {
		if( next == null )
			throw new NoSuchElementException( "No more nodes." );
		EdgeNodePair returnNode = next;
		performSearchStep();
    if( iteratorQ.isEmpty() && checkAllNodes )
      findNextStart();
		return returnNode;
  }

  public void performSearchStep() {
    // perform one step of the BFS-algorithm
    while( !iteratorQ.isEmpty() ) {
      // We may have an iterator here
      currentIterator = iteratorQ.peek();

      while( currentIterator.hasNext() ) {
        Edge e = currentIterator.next();
        // we see e and return it
        if( seen.get( e.end() ) == false ) {
          EdgeNodePair newPair = new EdgeNodePair( e.end(), e );
          iteratorQ.offer( g.incidentEdges( newPair.getNode() ).iterator() );
          seen.set( e.end(), true );
          next = newPair;
          return;
        }
      }

      // we have iterated over all the iterator and not given out a node. remove the first element
      iteratorQ.poll();
      next = null;
    }
  }

}
