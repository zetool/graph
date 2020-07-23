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

import org.zetool.container.collection.ArraySet;
import org.zetool.graph.Graph;

/**
 *
 * @author Marlen Schwengfelder
 */
public class PrimAlgorithm extends AbstractAlgorithm<MinSpanningTreeProblem, UndirectedForest> {

    @Override
    protected UndirectedForest runAlgorithm(MinSpanningTreeProblem problem) {
        Graph graph = problem.getGraph();

        ArraySet<Node> vertices = new ArraySet(Node.class, problem.getGraph().nodeCount());
        IdentifiableIntegerMapping<Node> distances = new IdentifiableIntegerMapping<>(graph.nodeCount());
        IdentifiableObjectMapping<Node, Edge> heapEdges = new IdentifiableObjectMapping<>(graph.edgeCount());

        for (Node node : graph.nodes()) {
            vertices.add(node);
            distances.add(node, Integer.MAX_VALUE);
            heapEdges.set(node, null);
        }

        IdentifiableCollection<Edge> solutionEdges = new ListSequence<>();
        while (!vertices.isEmpty()) {
            Node startNode = vertices.first();
            findComponent(startNode, distances, heapEdges, solutionEdges, vertices);
        }

        return new UndirectedForest(solutionEdges);
    }

    private void findComponent(final Node startNode, final IdentifiableIntegerMapping<Node> distances,
            final IdentifiableObjectMapping<Node, Edge> heapedges, final IdentifiableCollection<Edge> solEdges,
            ArraySet<Node> vertices) {
        MinHeap<Node, Integer> queue = new MinHeap<>(getProblem().getGraph().nodeCount());
        queue.insert(startNode, 0);

        while (!queue.isEmpty()) {
            MinHeap<Node, Integer>.Element min = queue.extractMin();
            Node v = min.getObject();
            vertices.remove(v);
            // If we are not seeing the start node, we have a cheapest edge to the node
            if (v != startNode) {
                solEdges.add(heapedges.get(v));
            }

            handleV(v, queue, distances, heapedges);
        }
    }

    private void handleV(final Node v, MinHeap<Node, Integer> queue, final IdentifiableIntegerMapping<Node> distances,
            final IdentifiableObjectMapping<Node, Edge> heapEdges) {
        IdentifiableIntegerMapping<Edge> weights = getProblem().getDistances();

        distances.set(v, Integer.MIN_VALUE);

        IdentifiableCollection<Edge> incidentEdges = getProblem().getGraph().incidentEdges(v);
        for (Edge edge : incidentEdges) {
            Node w = edge.opposite(v);
            if (distances.get(w) == Integer.MAX_VALUE) { // Node w found the first time
                distances.set(w, weights.get(edge));
                heapEdges.set(w, edge);
                queue.insert(w, distances.get(w));
            } else {
                // Update solution edge for w, if cheaper
                if (weights.get(edge) < distances.get(w)) {
                    distances.set(w, weights.get(edge));
                    queue.decreasePriority(w, weights.get(edge));
                    heapEdges.set(w, edge);
                }
            }
        }
    }
}
