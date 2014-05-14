
package de.tu_berlin.coga.graph;

import de.tu_berlin.coga.container.collection.ArraySet;
import de.tu_berlin.coga.container.collection.HidingSet;
import de.tu_berlin.coga.container.collection.IdentifiableCollection;
import de.tu_berlin.coga.container.collection.ListSequence;
import de.tu_berlin.coga.container.mapping.IdentifiableObjectMapping;
import ds.graph.Edge;
import ds.graph.Node;
import java.util.Iterator;


/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class SimpleUndirectedGraph implements UndirectedGraph {

/**
 * Simple implementation of an undirected graph. The graph supports initial constant number of nodes, but edges can be
 * dynamically added. The edges are stored as adjacency lists for each node.
 * @author Jan-Philipp Kappmeier
 */
	/** The nodes of the network. Must not be null. */
	protected ArraySet<Node> nodes;
	/** The edges of the network. Must not be null. */
	protected HidingSet<Edge> edges;
	/** Caches the edges incident to a node for all nodes in the graph. */
	protected IdentifiableObjectMapping<Node, ListSequence<Edge>> incidentEdges;
  /** The number of nodes. */
  private final int nodeCount;
  /** The number of edges currently in the grpah. */
  private int edgeCount;

  /**
   * Initializes a graph with the given number of nodes. The graph is empty afterwards and does not contain any edge.
   * @param size the number of nodes.
   */
  public SimpleUndirectedGraph( int size ) {
    nodeCount = size;
    nodes = new ArraySet<>( Node.class, size );
    incidentEdges = new IdentifiableObjectMapping<>( size );
    for( int i = 0; i < size; ++i ) {
      nodes.add( new Node( i ) );
      incidentEdges.set( nodes.get( i ), new ListSequence<>() );
    }
  }



  /**
   * Adds a new edge between two nodes. It is not checked if an edge between the nodes may already exist and parallel
   * edges are allowed.
   * @param v one end node of the edge
   * @param w the other end node of the edge
   * @return the newly created edge
   * @throws IllegalArgumentException if the ids of the nodes lies outside the number of nodes in the graph or if the
   *
   */
  public Edge addEdge( Node v, Node w ) throws IllegalArgumentException {
    if( v.id() > nodeCount ) {
      throw new IllegalArgumentException( "Start node " + v + " does not exist." );
    }
    if( w.id() > nodeCount ) {
      throw new IllegalArgumentException( "End node " + w + " does not exist." );
    }
    if( !( v.equals( nodes.get( v.id() ) ) ) || !(w.equals( nodes.get( w.id() ) ) ) ) {
      throw new IllegalArgumentException( "Node does not belong to graph!" );
    }
    return addEdge( v.id(), w.id() );
  }

  /**
   * Adds a new edge between to nodes given by their indices. Parallel edges can be added.
   * @param vIndex the index of the first node
   * @param wIndex the index of the second node
   * @return the newly created edge
   * @throws IllegalArgumentException if the node indices are out of range.
   */
  public Edge addEdge( int vIndex, int wIndex ) throws IllegalArgumentException {
    if( vIndex > nodeCount || vIndex < 0 ) {
      throw new IllegalArgumentException( "Start node " + vIndex + " does not exist." );
    }
    if( wIndex > nodeCount || wIndex < 0 ) {
      throw new IllegalArgumentException( "End node " + wIndex + " does not exist." );
    }

    Node first = nodes.get( vIndex );
    Node second = nodes.get( wIndex );
    Edge e = new Edge(edgeCount++, first, second );
    incidentEdges.get( first ).add( e );
    incidentEdges.get( second ).add( e );
    return e;
  }

  /**
   * Adds an existing edge to this graph. Using this method an edge can exist in multiple graphs. The two nodes of the
   * edge must be in the range of this graph.
   * @param e the edge
   * @return the edge
   */
  public Edge addEdge( Edge e ) {
    if( e.start().id() > nodeCount ) {
      throw new IllegalArgumentException( "Start node " + e.start() + " does not exist." );
    }
    if( e.end().id() > nodeCount ) {
      throw new IllegalArgumentException( "End node " + e.end() + " does not exist." );
    }
    if( !(e.start().equals( nodes.get( e.end().id() ) ) )
            || !(e.end() == nodes.get( e.end().id() ) ) ) {
      throw new IllegalArgumentException( "Node does not belong to graph!" );
    }
    incidentEdges.get( e.start() ).add( e );
    incidentEdges.get( e.end() ).add( e );
    edgeCount++;
    return e;
  }

  /**
   * Returns the node with a given index.
   * @param i the index
   * @return the node with the index
   */
  @Override
  public Node getNode( int i ) {
    return nodes.get( i );
  }

  /**
   * Returns the number of nodes of the graph.
   * @return the number of nodes of the graph
   */
  @Override
  public final int nodeCount() {
    return nodeCount;
  }

  /**
   * Returns the number of edges currently in the graph.
   * @return the number of edges currently in the graph
   */
  @Override
  public final int edgeCount() {
    return edgeCount;
  }

  /**
   * Returns a list of adjacent edges.
   * @param v the node
   * @return  the list of adjacent edges
   */
  public IdentifiableCollection<Edge> getAdjacent( Node v ) {
    return getAdjacent( v );
  }

  @Override
  public Iterator<Node> iterator() {
    //return nodeCount > 0 ? new EdgeIterator() : Collections.<Edge>emptyIterator();
    return nodes.iterator();
  }

  @Override
  public IdentifiableCollection<Edge> edges() {
    return edges;
  }

  @Override
  public IdentifiableCollection<Node> nodes() {
    return nodes;
  }

  @Override
  public IdentifiableCollection<Edge> incidentEdges( Node node ) {
    return incidentEdges.get( node );
  }

  @Override
  public IdentifiableCollection<Node> adjacentNodes( Node node ) {
    throw new UnsupportedOperationException( "Not supported yet." );
  }

  @Override
  public int degree( Node node ) {
    return incidentEdges.get( node ).size();
  }

  @Override
  public boolean contains( Edge edge ) {
    return edges.contains( edge );
  }

  @Override
  public boolean contains( Node node ) {
    return nodes.contains( node );
  }

  @Override
  public Edge getEdge( int id ) {
    throw new UnsupportedOperationException( "Not supported yet." );
  }

  @Override
  public Edge getEdge( Node start, Node end ) {
    throw new UnsupportedOperationException( "Not supported yet." );
  }

  @Override
  public IdentifiableCollection<Edge> getEdges( Node start, Node end ) {
    return edges;
  }
}
