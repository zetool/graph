/**
 * OutgoingStarGraph.java
 * Created: 07.01.2014, 17:05:24
 */

package org.zetool.graph;

import org.zetool.graph.Node;

/**
 * Provides means to access a graph or network in the manner of outgoing star
 * by accessing the first outgoing arc of a node and the last outgoing arc and
 * iterate over all of them.
 * @author Jan-Philipp Kappmeier
 */
public interface OutgoingStarGraph {
	public int getFirst( Node node );

	public int getLast( Node node );

	public int next( int current );
}
