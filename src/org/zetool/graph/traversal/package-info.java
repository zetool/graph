/**
 * Provides iterators and algorithms to traverse nodes (and edges) of a graph.
 * Provided algorithms are breadth first search and depth first search.
 * 
 * <p>Both algorithms are provided as {@link Iterator} in two variants. The default
 * variant iterates over the nodes in the graph, while the general variant iterates
 * over nodes and incoming edges in the dfs/bfs tree.</p>
 * <p>Additionally implementations as {@link de.tu_berlin.coga.common.algorithm.Algorithm} are provided that do not
 * only iterate on the graph but also create the bfs/dfs tree and compute the partition
 * of the edges into forward, tree, back and cross edges, if available.</p>
 * <p>The algorithm take arbitrary graphs, the corresponding sets are computed
 * if the graph is undirected or directed.</p>
 */

package org.zetool.graph.traversal;
