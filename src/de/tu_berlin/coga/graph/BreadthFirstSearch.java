/**
 * BreadthFirstSearch.java
 * Created: 12.05.2014, 18:12:29
 */
package de.tu_berlin.coga.graph;

import de.tu_berlin.coga.common.algorithm.Algorithm;
import de.tu_berlin.coga.common.util.Helper;
import de.tu_berlin.coga.container.mapping.IdentifiableIntegerMapping;
import de.tu_berlin.coga.container.mapping.IdentifiableObjectMapping;
import de.tu_berlin.coga.graph.DirectedGraph;
import ds.graph.Edge;
import ds.graph.Node;


/**
 * Implementation of the breadth first search. The nodes are given numbers for a bfs numbering, the predecessor arcs
 * arce stored to construct shortest paths and the set of arcs is divided into forward/tree arcs and backward arcs.
 * @author Jan-Philipp Kappmeier
 */
public class BreadthFirstSearch extends Algorithm<DirectedGraph,Void> {
  private IdentifiableObjectMapping<Node,Edge> predecessors;
  private IdentifiableIntegerMapping<Node> distances;

  @Override
  protected Void runAlgorithm( DirectedGraph problem ) {
    GeneralBreadthFirstSearchIterator bfs = new GeneralBreadthFirstSearchIterator( problem );
    predecessors = new IdentifiableObjectMapping<>( problem.nodeCount() );
    distances = new IdentifiableIntegerMapping<>( problem.nodeCount() );

    for( EdgeNodePair n : Helper.in( bfs ) ) {
      predecessors.set( n.getNode(), n.getPred() );
      if( n.getPred() != null ) {
        distances.set( n.getNode(), distances.get( predecessors.get( n.getNode() ).start() ) + 1 );
      } else {
        distances.set( n.getNode(), 0 );
      }
    }
    return null;
  }

  IdentifiableIntegerMapping<Node> distances() {
    return distances;
  }

}
