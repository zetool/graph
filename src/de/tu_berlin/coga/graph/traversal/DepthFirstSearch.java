package de.tu_berlin.coga.graph.traversal;

import org.zetool.common.algorithm.Algorithm;
import org.zetool.common.util.Helper;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.container.mapping.IdentifiableObjectMapping;
import de.tu_berlin.coga.graph.Graph;
import de.tu_berlin.coga.graph.Edge;
import de.tu_berlin.coga.graph.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the breadth first search. The nodes are given numberings for a bfs numberinging, the predecessor
 * arcs arce stored to construct shortest paths and the set of arcs is divided into forward/tree arcs and backward arcs.
 * @author Martin Groß
 * @author Jan-Philipp Kappmeier
 */
public class DepthFirstSearch extends Algorithm<Graph, Void> {
  private IdentifiableObjectMapping<Node, Edge> predecessors;
  private IdentifiableIntegerMapping<Node> numbering;
  private IdentifiableIntegerMapping<Node> endTime;
  private int currentNumber = 1;
  /** The start node for the search. If null, all nodes are iterated over. */
  private Node start;
  /** Defines a target node that is used to stop the traversal. May be {@code null}. */
  private Node stop;

  private Graph graph;

  private boolean reverse = false;

  private List<Node> active = new LinkedList<>();

  public List<Edge> backEdges;
  public List<Edge> crossEdges;
  public List<Edge> forwardEdges;
  public List<Edge> treeEdges;
  //private Map<Node,Integer> numbering;
  private Map<Node, State> states;


  public void setStart( Node source ) {
    this.start = source;
  }

  public void setStop( Node stop ) {
    this.stop = stop;
  }

  public void setReverse( boolean reverse ) {
    this.reverse = reverse;
  }

  @Override
  protected Void runAlgorithm( Graph problem ) {
    this.graph = problem;
    init();

    GeneralDepthFirstSearchIterator dfsIterator = start == null ? new GeneralDepthFirstSearchIterator( problem, true, reverse )
            : new GeneralDepthFirstSearchIterator( problem, start.id(), reverse );

    // Iterates over all edges!
    for( EdgeNodePair n : Helper.in( dfsIterator ) ) {

      // Set the predecessor
      predecessors.set( n.getNode(), n.getPred() );
      if( n.getPred() != null ) {
        //System.out.println( "Classifying edge " + n.getPred() );
        if( numbering.get( n.getNode() ) == 0 ) {
          // we have a forward edge
          numbering.set( n.getNode(), currentNumber );
          treeEdges.add( n.getPred() );
        } else if(dfsIterator.isFinished( n.getPred().end() ) ) {
          if( numbering.get( n.getNode() ) < numbering.get( n.getPred().start() ) ) {
            // We have found a cross edge
            crossEdges.add( n.getPred() );
          } else {
            // We have found a cross edge
            forwardEdges.add( n.getPred() );
          }
        } else {
          // We have found a back edge
          backEdges.add( n.getPred() );
        }
        currentNumber++;
      } else {
        // A new start node, no predecessor edge
        numbering.set( n.getNode(), currentNumber++ );

      }
    }
    return null;
  }

  private void init() {
    backEdges = new ArrayList<>( graph.edgeCount() );
    crossEdges = new ArrayList<>( graph.edgeCount() );
    forwardEdges = new ArrayList<>( graph.edgeCount() );
    treeEdges = new ArrayList<>( graph.edgeCount() );
    numbering = new IdentifiableIntegerMapping<>( graph.nodeCount() );
    states = new HashMap<>( graph.nodeCount() );
    predecessors = new IdentifiableObjectMapping<>( graph.nodeCount() );
    currentNumber = 1;
  }

  IdentifiableIntegerMapping<Node> numbering() {
    return numbering;
  }

  public int getNumber( Node node ) {
    return numbering.get( node );
  }


}
