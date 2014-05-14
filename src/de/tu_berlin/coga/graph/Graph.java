/* zet evacuation tool copyright (c) 2007-10 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.tu_berlin.coga.graph;

import de.tu_berlin.coga.container.collection.IdentifiableCollection;
import ds.graph.Edge;
import ds.graph.Node;

/**
 * The {@code Graph} interface provides a common interface for
 * implementations of graphs.
 */
public interface Graph extends Iterable<Node> {

  /**
	 * Checks whether the graph is directed.
	 * @return {@code true} if the graph is directed, {@code false}
	 * otherwise.
	 */
	boolean isDirected();

	/**
	 * Returns an {@link IdentifiableCollection} containing all the edges of
	 * this graph.
	 * @return an {@link IdentifiableCollection} containing all the edges of
	 * this graph.
	 */
	public IdentifiableCollection<Edge> edges();

	/**
	 * Returns an {@link IdentifiableCollection} containing all the nodes of
	 * this graph.
	 * @return an {@link IdentifiableCollection} containing all the nodes of
	 * this graph.
	 */
	IdentifiableCollection<Node> nodes();

	/**
	 * Returns the number of edges in this graph.
	 * @return the number of edges in this graph.
	 */
	int edgeCount();

	/**
	 * Returns the number of nodes in this graph.
	 * @return the number of nodes in this graph.
	 */
	int nodeCount();

	/**
	 * Returns an {@link IdentifiableCollection} containing all the edges incident to
	 * the specified node.
	 * @param node the node
	 * @return an {@link IdentifiableCollection} containing all the edges incident to
	 * the specified node.
	 */
	IdentifiableCollection<Edge> incidentEdges( Node node );

	/**
	 * Returns an {@link IdentifiableCollection} containing all the nodes adjacent to
	 * the specified node.
	 * @param node the node
	 * @return an {@link IdentifiableCollection} containing all the nodes adjacent to
	 * the specified node.
	 */
	IdentifiableCollection<Node> adjacentNodes( Node node );

	/**
	 * Returns the degree of the specified node, i.e. the number of edges
	 * incident to it.
	 * @param node the node for which the degree is to be returned.
	 * @return the degree of the specified node.
	 */
	int degree( Node node );

	/**
	 * Checks whether the specified edge is contained in this graph.
	 * @param edge the edge to be checked.
	 * @return {@code true} if the edge is contained in this graph,
	 * {@code false} otherwise.
	 */
	boolean contains( Edge edge );

	/**
	 * Checks whether the specified node is contained in this graph.
	 * @param node the node to be checked.
	 * @return {@code true} if the node is contained in this graph,
	 * {@code false} otherwise.
	 */
	boolean contains( Node node );

	/**
	 * Returns the edge with the specified id or {@code null} if the graph
	 * does not contain an edge with the specified id.
	 * @param id the id of the edge to be returned.
	 * @return the edge with the specified id or {@code null} if the graph
	 * does not contain an edge with the specified id.
	 */
	Edge getEdge( int id );

	/**
	 * Returns an edge starting at {@code start} and ending at
	 * {@code end}. In case of undirected graphs no distinction between
	 * {@code start} and {@code end} is made (i.e. in this case an
	 * edge incident to both {@code start} and {@code end} is
	 * returned). If no such edge exists, {@code null} is returned.
	 * @param start the start node of the edge to be returned.
	 * @param end the end node of the edge to be returned.
	 * @return an edge starting at {@code start} and ending at
	 * {@code end}.
	 */
	Edge getEdge( Node start, Node end );

	/**
	 * Returns an {@link IdentifiableCollection} containing all edges starting at
	 * {@code start} and ending at
	 * {@code end}. In case of undirected graphs no distinction between
	 * {@code start} and {@code end} is made (i.e. in this case all
	 * edges incident to both {@code start} and {@code end} are
	 * returned). If no such edge exists, an empty list is returned.
	 * @param start the start node of the edges to be returned.
	 * @param end the end node of the edges to be returned.
	 * @return an {@link IdentifiableCollection} containing all edges starting at
	 * {@code start} and ending at {@code end}.
	 */
	IdentifiableCollection<Edge> getEdges( Node start, Node end );

	/**
	 * Returns the node with the specified id or {@code null} if the graph
	 * does not contain a node with the specified id.
	 * @param id the id of the node to be returned.
	 * @return the node with the specified id or {@code null} if the graph
	 * does not contain a node with the specified id.
	 */
	Node getNode( int id );
}
