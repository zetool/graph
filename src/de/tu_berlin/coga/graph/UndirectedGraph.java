
package de.tu_berlin.coga.graph;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public interface UndirectedGraph extends Graph {
	@Override
	public default boolean isDirected() {
		return false;
	}

}
