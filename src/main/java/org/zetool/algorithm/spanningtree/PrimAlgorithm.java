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

import org.zetool.common.algorithm.AbstractAlgorithm;
import org.zetool.graph.Edge;
import org.zetool.container.collection.IdentifiableCollection;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.container.mapping.IdentifiableObjectMapping;
import org.zetool.container.collection.ListSequence;
import org.zetool.container.priority.MinHeap;
import org.zetool.graph.Node;

import org.zetool.graph.Graph;

/**
 * Computes a minimum spanning tree using Prim's (or Jarník's) algorithm. If the input graph is connected, the resulting
 * edges form a single tree. Otherwise the result is a forest containing trees.
 * <p>
 * Jarník, Vojtěch. <a href="https://dml.cz/bitstream/handle/10338.dmlcz/500726/Jarnik_01-0000-31_1.pdf">O jistém
 * problému minimálním</a> (About a certain minimal problem.) Práce Moravské Přírodovědecké Společnosti, 1930, 6(4),
 * 57–63.
 * <p>
 * Prim, Robert C. <a href="">Shortest connection networks And some generalizations</a>. Bell System Technical Journal,
 * 1957, 36(6), 1389–1401.
 *
 * @author Marlen Schwengfelder
 */
public class PrimAlgorithm extends AbstractAlgorithm<MinSpanningTreeProblem, UndirectedForest> {

    /**
     * Execute Prim's/Jarník's algorithm with a given {@link MinSpanningTreeProblem minimum spanning tree instance}.
     *
     * @param problem the minimum spanning tree instance
     * @return a forest containing the edges forming one or multiple spanning trees
     */
    @Override
    protected UndirectedForest runAlgorithm(MinSpanningTreeProblem problem) {
        Graph graph = problem.getGraph();

        IdentifiableIntegerMapping<Node> distances = new IdentifiableIntegerMapping<>(graph.nodeCount());
        IdentifiableObjectMapping<Node, Edge> heapEdges = new IdentifiableObjectMapping<>(graph.edgeCount());

        for (Node node : graph.nodes()) {
            distances.add(node, Integer.MAX_VALUE);
            heapEdges.set(node, null);
        }

        IdentifiableCollection<Edge> solutionEdges = new ListSequence<>();
        for (Node startNode : graph.nodes()) {
            if (distances.get(startNode) == Integer.MAX_VALUE) {
                findComponent(startNode, distances, heapEdges, solutionEdges);
            }
        }

        return new UndirectedForest(solutionEdges);
    }

    /**
     * Starting from a node detect the spanning tree containing this node.
     *
     * @param startNode the node to start
     * @param distances current node distances
     * @param heapEdges cheapest connecting edges for each node (that have been discovered before)
     * @param solutionEdges the collection of edges in the solution
     */
    private void findComponent(final Node startNode, final IdentifiableIntegerMapping<Node> distances,
            final IdentifiableObjectMapping<Node, Edge> heapEdges, final IdentifiableCollection<Edge> solutionEdges) {
        MinHeap<Node, Integer> queue = new MinHeap<>(getProblem().getGraph().nodeCount());

        handleNode(startNode, queue, distances, heapEdges);
        while (!queue.isEmpty()) {
            MinHeap<Node, Integer>.Element min = queue.extractMin();
            Node v = min.getObject();
            solutionEdges.add(heapEdges.get(v));
            handleNode(v, queue, distances, heapEdges);
        }
    }

    private void handleNode(final Node v, MinHeap<Node, Integer> queue, IdentifiableIntegerMapping<Node> distances,
            final IdentifiableObjectMapping<Node, Edge> heapEdges) {
        IdentifiableIntegerMapping<Edge> weights = getProblem().getDistances();

        distances.set(v, Integer.MIN_VALUE);

        IdentifiableCollection<Edge> incidentEdges = getProblem().getGraph().incidentEdges(v);
        for (Edge edge : incidentEdges) {
            Node w = edge.opposite(v);
            if (isConnectedFirst(w, distances)) {
                // Node w found the first time
                distances.set(w, weights.get(edge));
                heapEdges.set(w, edge);
                queue.insert(w, distances.get(w));
            } else if (canBeConnectedCheaper(w, distances, edge, weights)) {
                // Update solution edge for w, if cheaper
                distances.set(w, weights.get(edge));
                heapEdges.set(w, edge);
                queue.decreasePriority(w, weights.get(edge));
            }
        }
    }

    /**
     * Checks wether a node is considered for the solution the first time.
     *
     * @param w the node that is considered
     * @param distances the current node distances
     * @return {@code true} if {@code w} is considered the first time, i.e. no connecting edge is known
     */
    private boolean isConnectedFirst(Node w, IdentifiableIntegerMapping<Node> distances) {
        return distances.get(w) == Integer.MAX_VALUE;
    }

    /**
     * Checks wether an edge can be used to connect a node cheaper as before.
     *
     * @param w the node that is considered
     * @param distances the current node distances
     * @param edge the edge that is considered
     * @param weights the edge weights
     * @return {@code true} if {@code w} can be cheaper connected to the tree by using {@link edge}
     */
    private boolean canBeConnectedCheaper(Node w, IdentifiableIntegerMapping<Node> distances, Edge edge,
            IdentifiableIntegerMapping<Edge> weights) {
        return weights.get(edge) < distances.get(w);
    }
}
