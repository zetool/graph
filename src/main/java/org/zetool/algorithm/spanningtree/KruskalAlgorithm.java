/* zet evacuation tool copyright (c) 2007-20 zet evacuation team
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.zetool.algorithm.spanningtree;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.zetool.common.algorithm.AbstractAlgorithm;
import org.zetool.container.collection.DisjointSet;
import org.zetool.container.collection.IdentifiableCollection;
import org.zetool.container.collection.ListSequence;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.graph.Edge;
import org.zetool.graph.Graph;
import org.zetool.graph.Node;

/**
 * Computes a minimum spanning tree using Kruskal's algorithm. If the input graph is connected, the resulting edges form
 * a single tree. Otherwise the result is a forest containing trees.
 * <p>
 * Kruskal, Joseph B.
 * <a href="https://www.ams.org/journals/proc/1956-007-01/S0002-9939-1956-0078686-7/S0002-9939-1956-0078686-7.pdf">
 * On the shortest spanning subtree of a graph and the traveling salesman problem.</a> Proceedings of the American
 * Mathematical society, 1956, 7(1), 48-50.
 *
 * @author Jan-Philipp Kappmeier
 */
public class KruskalAlgorithm extends AbstractAlgorithm<MinSpanningTreeProblem, UndirectedForest>
        implements MinimumSpanningTreeAlgorithm {

    /**
     * Execute Kruskal's algorithm with a given {@link MinSpanningTreeProblem minimum spanning tree instance}.
     *
     * @param problem the minimum spanning tree instance
     * @return a forest containing the edges forming one or multiple spanning trees
     */
    @Override
    protected UndirectedForest runAlgorithm(MinSpanningTreeProblem problem) {
        UndirectedForest tree;

        Graph graph = problem.getGraph();

        IdentifiableIntegerMapping<Edge> weights = problem.getDistances();

        IdentifiableCollection<Edge> treeEdges = new ListSequence<>();

        if (graph.edgeCount() == 0) {
            return new UndirectedForest(treeEdges);
        }

        final Comparator<Edge> comp = (Edge o1, Edge o2) -> weights.get(o1) - weights.get(o2);

        PriorityQueue<Edge> heap = new PriorityQueue<>(graph.edgeCount(), comp);

        // iterate over all edges and add into list
        for (Edge e : graph.edges()) {
            heap.offer(e);
        }

        DisjointSet<Node> unionFind = new DisjointSet<>(graph.nodes());

        while (!heap.isEmpty() && treeEdges.size() != graph.nodeCount() - 1) {
            Edge e = heap.poll();
            if (unionFind.find(e.start()).id() != unionFind.find(e.end()).id()) {
                treeEdges.add(e);
                unionFind.union(unionFind.find(e.start()), unionFind.find(e.end()));
            }
        }

        tree = new UndirectedForest(treeEdges);
        return tree;
    }

}
