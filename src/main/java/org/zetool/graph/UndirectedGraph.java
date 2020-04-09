
package org.zetool.graph;

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
