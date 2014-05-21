
package de.tu_berlin.coga.graph.traversal;

import de.tu_berlin.coga.common.algorithm.Algorithm;
import de.tu_berlin.coga.common.util.Helper;
import de.tu_berlin.coga.container.mapping.IdentifiableBooleanMapping;
import de.tu_berlin.coga.container.mapping.IdentifiableIntegerMapping;
import de.tu_berlin.coga.container.mapping.IdentifiableObjectMapping;
import de.tu_berlin.coga.graph.DirectedGraph;
import de.tu_berlin.coga.graph.util.PredecessorIterator;
import de.tu_berlin.coga.graph.util.PredecessorMap;
import de.tu_berlin.coga.graph.Edge;
import de.tu_berlin.coga.graph.Node;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of the breadth first search. The nodes are given numbers for a bfs numbering, the predecessor arcs
 * arce stored to construct shortest paths and the set of arcs is divided into forward/tree arcs and backward arcs.
 * @author Jan-Philipp Kappmeier
 */
public class BreadthFirstSearch extends Algorithm<DirectedGraph,Void> implements PredecessorMap {
  private IdentifiableObjectMapping<Node,Edge> predecessors;
  private IdentifiableIntegerMapping<Node> distances;
  /** The start node for the search. If null, all nodes are iterated over. */
  private Node start;
  /** Defines a target node that is used to stop the traversal. May be {@code null}. */
  private Node stop;


  public void setStart( Node source ) {
    this.start = source;
  }

  public void setStop( Node stop ) {
    this.stop = stop;
  }

  @Override
  protected Void runAlgorithm( DirectedGraph problem ) {
    GeneralBreadthFirstSearchIterator bfs = start == null ? new GeneralBreadthFirstSearchIterator( problem )
            : new GeneralBreadthFirstSearchIterator( problem, start.id() );
    predecessors = new IdentifiableObjectMapping<>( problem.nodeCount() );
    distances = new IdentifiableIntegerMapping<>( problem.nodeCount() );
    for( Node n : problem ) {
      distances.set( n, Integer.MAX_VALUE );
    }

    for( EdgeNodePair n : Helper.in( bfs ) ) {
      predecessors.set( n.getNode(), n.getPred() );
      if( n.getPred() != null ) {
        distances.set( n.getNode(), distances.get( predecessors.get( n.getNode() ).start() ) + 1 );
      } else {
        distances.set( n.getNode(), 0 );
      }
      if( n.getNode().equals( stop ) ) {
        return null;
      }
    }
    return null;
  }

  public int getDistance( Node end ) {
    return distances.get( end );
  }

  /**
   * Returns the edge in the shortest path tree that is incoming to node {@code n}.
   * @param n the node
   * @return the predecessor arc on the shortest path
   */
  @Override
  public Edge getPredecessor( Node n ) {
    return predecessors.get( n );
  }

  @Override
  public Iterator<Edge> iterator() {
    if( stop == null ) {
      throw new IllegalArgumentException( "Iterator only valid if single stopping element is given." );
    }
    return new PredecessorIterator( stop, this );
  }

  public HashSet<Node> getReachableNodes() {
    if( !this.isProblemSolved() ) {
      throw new IllegalStateException( "Can only be called once the algorithm has run!" );
    }
    HashSet<Node> reachableNodes = new HashSet<>();
    for( Node n : getProblem() ) {
      if( distances.get( n ) < Integer.MAX_VALUE ) {
        reachableNodes.add( n );
      }
    }
    return reachableNodes;
  }
}
