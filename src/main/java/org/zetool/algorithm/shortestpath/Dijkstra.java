/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
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
package org.zetool.algorithm.shortestpath;

import org.zetool.common.algorithm.AbstractAlgorithm;
import org.zetool.container.priority.MinHeap;
import org.zetool.graph.Edge;
import org.zetool.container.collection.IdentifiableCollection;
import org.zetool.graph.Node;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.container.mapping.IdentifiableObjectMapping;
import org.zetool.graph.DirectedGraph;

/**
 *
 * @author Martin Groß
 */
public class Dijkstra extends AbstractAlgorithm<IntegralSingleSourceShortestPathProblem, IntegralShortestPathSolution> {

    private final boolean reverse;

    public Dijkstra() {
        this(false);
    }

    public Dijkstra(boolean reverse) {
        super("Integral Dijkstra");
        this.reverse = reverse;
    }

    @Override
    protected IntegralShortestPathSolution runAlgorithm(IntegralSingleSourceShortestPathProblem problem) {
        final IdentifiableIntegerMapping<Edge> costs = getProblem().getCosts();
        if (!getProblem().getGraph().isDirected()) {
            throw new IllegalArgumentException("Only directed graphs allowed");
        }
        final DirectedGraph graph = (DirectedGraph) getProblem().getGraph();
        final Node source = getProblem().getSource();
        final Node sink = getProblem().getTarget().orElse(null);

        final IdentifiableIntegerMapping<Node> distances = new IdentifiableIntegerMapping<>(graph.nodeCount());
        final IdentifiableObjectMapping<Node, Edge> edges = new IdentifiableObjectMapping<>(graph.edgeCount());
        final IdentifiableObjectMapping<Node, Node> nodes = new IdentifiableObjectMapping<>(graph.nodeCount());

        MinHeap<Node, Integer> queue = new MinHeap<>(graph.nodeCount());
        for (int v = 0; v < graph.nodeCount(); v++) {
            distances.set(graph.getNode(v), Integer.MAX_VALUE);
            queue.insert(graph.getNode(v), Integer.MAX_VALUE);
        }
        distances.set(source, 0);
        queue.decreasePriority(source, 0);
        while (!queue.isEmpty()) {
            MinHeap<Node, Integer>.Element min = queue.extractMin();
            Node v = min.getObject();
            Integer pv = min.getPriority();
            distances.set(v, pv);
            IdentifiableCollection<Edge> incidentEdges;
            if (!reverse) {
                incidentEdges = graph.outgoingEdges(v);
            } else {
                incidentEdges = graph.incomingEdges(v);
            }

            for (Edge edge : incidentEdges) {
                Node w = edge.opposite(v);
                if (queue.contains(w) && (long) queue.priority(w) > (long) pv + (long) costs.get(edge)) {
                    queue.decreasePriority(w, pv + costs.get(edge));
                    edges.set(w, edge);
                    nodes.set(w, v);
                    if (w.equals(sink)) {
                        return new IntegralShortestPathSolution(graph.nodes(), distances, edges, nodes);
                    }
                }
            }
        }
        return new IntegralShortestPathSolution(graph.nodes(), distances, edges, nodes);
    }
}
