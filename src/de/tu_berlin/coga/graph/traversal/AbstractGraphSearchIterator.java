
package de.tu_berlin.coga.graph.traversal;

import de.tu_berlin.coga.container.mapping.IdentifiableObjectMapping;
import de.tu_berlin.coga.graph.Graph;
import de.tu_berlin.coga.graph.util.GraphUtil;
import de.tu_berlin.coga.graph.util.UnifiedGraphAccess;
import de.tu_berlin.coga.graph.Edge;
import de.tu_berlin.coga.graph.Node;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;

/**
 * An abstract graph traversal iterator that takes a datastructure as parameter. During traversal, the elements are
 * inserted and extracted to/from the datastructure when they are first explored and finished, respectiveley. If the
 * data structure is a queue, this iterator behaves like a breadth first search, with a stack it behaves like a
 * depth first search.
 * @author Jan-Philipp Kappmeier
 */
class AbstractGraphSearchIterator implements Iterator<EdgeNodePair> {
  /** Enumeration of the intermediate state of nodes in the graph traversal. */
  private static enum State {
    /** The initial node state. */
    Normal,
    /** Nodes that have been visited already but whose tree is not finished. */
    Visited,
    /** Nodes that have been visited and all nodes in the sub tree are finished. */
    Done;
  }
  /** The iterator data structure used for the graph search. Queue for BFS, stack for DFS. */
  private final Queue<NodeIterator> dataStructure;
  /** The mapping of states for each node. */
  private final IdentifiableObjectMapping<Node,State> nodeState;
  /** Iterator over the nodes to find next unvisited node. Used if not only one component is iterated over.
   * Remains {@code null} if a start node is given. */
  private Iterator<Node> index;
  /** Decides whether all nodes are iterated over or just one component. */
  private boolean checkAllNodes;
  /** Decides whether all edges are iterated over. This means nodes are given out more than once. */
  private boolean iterateAllEdges;
  /** The currently 'next' node and edge pair, that is returned by the iterator. */
  private EdgeNodePair next;
  /** Special case for the first edge. */
  private boolean first = true;
  /** Access wrapper for directed and undirected access. */
  private final UnifiedGraphAccess graphAccess;

  /**
   * Main constructor without start node.
   * @param g the graph
   * @param iterateAllEdges if all edges shall be iterated over
   * @param reverse if the edges are iterated reversed
   * @param ds the datastructure used in the abstract graph traversal algorithm
   */
  AbstractGraphSearchIterator( Graph g, boolean iterateAllEdges, boolean reverse, Queue<NodeIterator> ds ) {
    this.graphAccess = GraphUtil.getUnifiedAccess( Objects.requireNonNull( g, "Graph should not be null!" ), reverse );
    this.dataStructure = Objects.requireNonNull( ds, "Datastructure shall not be null." );
    nodeState = new IdentifiableObjectMapping<>( g.nodeCount() );
    this.iterateAllEdges = iterateAllEdges;
    for( Node n : g ) {
      nodeState.set( n, State.Normal );
    }
    this.checkAllNodes = true;
    index = g.iterator();
    findNextStart();
  }

  /**
   * Main constructor with start node.
   * @param g the graph
   * @param startNode the index of the start node
   * @param iterateAllEdges if all edges shall be iterated over
   * @param reverse if the edges are iterated reversed
   * @param ds the datastructure used in the abstract graph traversal algorithm
   */
  AbstractGraphSearchIterator( Graph g, int startNode, boolean iterateAllEdges, boolean reverse,
          Queue<NodeIterator> ds ) {
    this.graphAccess = GraphUtil.getUnifiedAccess( Objects.requireNonNull( g, "Graph should not be null!" ), reverse );
    this.dataStructure = Objects.requireNonNull( ds, "Datastructure shall not be null." );
    if( g.getNode( startNode ) == null ) {
      throw new IllegalArgumentException( "Node id" + startNode + " not available in graph." );
    }
    nodeState = new IdentifiableObjectMapping<>( g.nodeCount() );
    for( Node n : g ) {
      nodeState.set( n, State.Normal );
    }
    next = new EdgeNodePair( g.getNode( startNode ), null );
    dataStructure.offer( new NodeIterator( next.getNode(), graphAccess.incident( next.getNode() ) ) );
    nodeState.set( g.getNode( startNode ), State.Visited );
    this.iterateAllEdges = iterateAllEdges;
  }

  /**
   * Finds a new start node if a component is completely traversed. Iterates over the node sets using an iterator that
   * is shared between calls such that in total exactly one iteration is performed.
   */
  private void findNextStart() {
    while( index.hasNext() ) {
      Node n = index.next();
      if( nodeState.get( n ) == State.Normal ) {
        next = new EdgeNodePair( n, null );
        dataStructure.offer( new NodeIterator( n, graphAccess.incident( n ) ) );
        nodeState.set( n, State.Visited );
        return;
      }
    }
  }

  @Override
  public boolean hasNext() {
    if( first ) {
      first = false;
      return next != null;
    }
    performSearchStep();
    if( dataStructure.isEmpty() && checkAllNodes ) {
      findNextStart();
    }
    return next != null;
  }

  @Override
  public EdgeNodePair next() {
		if( next == null ) {
      throw new NoSuchElementException( "No more nodes." );
    }
    return next;
  }

  /**
   * Performs one step in the graph traversal. Returns a pair of a node and the edge that was used to visit the node. In
   * the case of a start node, {@code null} is returned for the predecessor edge.
   *
   * <p>If the traversal mode is set to only traverse nodes, each found node is exactly returned once and the edges form
   * a tree. In the other case all found edges are returned, thus nodes may be returned more than once. The second mode
   * can be used to generate a total classification of the edges. If only nodes are important, the first mode is enough.
   * </p>
   */
  public void performSearchStep() {
    // perform one step of the BFS/DFS-algorithm
    while( !dataStructure.isEmpty() ) {
      // We extract the root element, but leave in datastructure. At the current position we have a node and an iterator
      // valid at the node. The iterator remains valid between two calls of performSearchStep().
      Iterator<Edge> currentIterator = dataStructure.peek().iter;
      Node baseNode = dataStructure.peek().n;

      while( currentIterator.hasNext() ) {
        Edge e = currentIterator.next();
        // Return the edge and node if it is new or if all edges are returned
        if( nodeState.get( graphAccess.getEnd( e, baseNode ) ) == State.Normal ) {
          EdgeNodePair newPair = new EdgeNodePair( graphAccess.getEnd( e, baseNode ), e );
          dataStructure.offer( new NodeIterator( newPair.getNode(), graphAccess.incident( newPair.getNode() ) ) );
          nodeState.set( graphAccess.getEnd( e, baseNode ), State.Visited );
          next = newPair;
          return;
        } else if( iterateAllEdges ) {
          EdgeNodePair newPair = new EdgeNodePair( graphAccess.getEnd( e, baseNode ), e );
          next = newPair;
          return;
        }
      }

      // We have iterated over all the iterator and not given out a node. Remove the first element; we are done with it.
      nodeState.set( dataStructure.poll().n, State.Done );
      next = null;
    }
  }

  /**
   * Checks wether traversal for a node is already complete. Traversal is complete after the node has first been visited
   * when it leaves the datastructure. E.g. for a DFS traversal this is the case when all nodes in the subtree have been
   * visited, too.
   * @param n the node
   * @return {@code true} if the node is done
   */
  public boolean isFinished( Node n ) {
    return nodeState.get( n ) == State.Done;
  }

  /**
   * A pair of a node and the iterator of outgoing arcs of the node. These pairs are stored in the datastructure
   * (usually queue or stack) used in the graph traversal.
   */
  protected static class NodeIterator {
    /** The node. */
    private Node n;
    /** The iterator of incident arcs of the node. */
    private Iterator<Edge> iter;

    /**
     * Initializes the pair with a node and an iterator of its incident arcs.
     * @param n the node
     * @param iter the iterator
     */
    private NodeIterator( Node n, Iterator<Edge> iter ) {
      this.n = n;
      this.iter = iter;
    }
  }
}
