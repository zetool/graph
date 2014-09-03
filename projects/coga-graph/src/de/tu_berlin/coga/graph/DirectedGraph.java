package de.tu_berlin.coga.graph;

import de.tu_berlin.coga.container.collection.IdentifiableCollection;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public interface DirectedGraph extends Graph {

	/**
	 * Returns an {@link IdentifiableCollection} containing all the edges ending at
	 * the specified node. This operation is only defined for directed graphs.
	 * @return an {@link IdentifiableCollection} containing all the edges ending at
	 * the specified node.
	 * @param node the node
	 * @throws UnsupportedOperationException if the graph is not directed.
	 */
	IdentifiableCollection<Edge> incomingEdges( Node node );

	/**
	 * Returns an {@link IdentifiableCollection} containing all the edges starting at
	 * the specified node. This operation is only defined for directed graphs.
	 * @param node the node
	 * @return an {@link IdentifiableCollection} containing all the edges starting at
	 * the specified node.
	 * @throws UnsupportedOperationException if the graph is not directed.
	 */
	IdentifiableCollection<Edge> outgoingEdges( Node node );


	/**
	 * Returns an {@link IdentifiableCollection} containing all the nodes that are
	 * incident to an edge ending at the specified node. This operation is only
	 * defined for directed graphs.
	 * @param node the node
	 * @return an {@link IdentifiableCollection} containing all the nodes that are
	 * incident to an edge ending at the specified node.
	 * @throws UnsupportedOperationException if the graph is not directed.
	 */
	IdentifiableCollection<Node> predecessorNodes( Node node );

	/**
	 * Returns an {@link IdentifiableCollection} containing all the nodes that are
	 * incident to an edge starting at the specified node. This operation is
	 * only defined for directed graphs.
	 * @param node the node
	 * @return an {@link IdentifiableCollection} containing all the nodes that are
	 * incident to an edge starting at the specified node.
	 * @throws UnsupportedOperationException if the graph is not directed.
	 */
	IdentifiableCollection<Node> successorNodes( Node node );

	/**
	 * Returns the indegree of the specified node, i.e. the number of edges
	 * ending at it. The indegree is not defined for undirected graphs.
	 * @param node the node for which the indegree is to be returned.
	 * @return the indegree of the specified node.
	 * @throws UnsupportedOperationException if the graph is not directed.
	 */
	int inDegree( Node node );

	/**
	 * Returns the outdegree of the specified node, i.e. the number of edges
	 * starting at it. The outdegree is not defined for undirected graphs.
	 * @param node the node for which the outdegree is to be returned.
	 * @return the outdegree of the specified node.
	 * @throws UnsupportedOperationException if the graph is not directed.
	 */
	int outDegree( Node node );

	@Override
	public default boolean isDirected() {
		return true;
	}

  public static String stringRepresentation( Graph g ) {
		StringBuilder buffer = new StringBuilder();
		buffer.append( "({" );
		for( Node node : g.nodes() ) {
			buffer.append( node.toString() );
			buffer.append( ", " );
		}
		if( g.nodeCount() > 0 )
			buffer.delete( buffer.length() - 2, buffer.length() );
		buffer.append( "}, {" );
		int counter = 0;
		for( Edge edge : g.edges() ) {
			if( counter == 10 ) {
				counter = 0;
				buffer.append( "\n" );
			}
			buffer.append( edge.toString() );
			buffer.append( ", " );
			counter++;
		}
		if( g.edgeCount() > 0 )
			buffer.delete( buffer.length() - 2, buffer.length() );
		buffer.append( "})" );
		return buffer.toString();
  }

}
