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
package org.zetool.algorithm.shortestpath;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import org.zetool.common.algorithm.AbstractAlgorithm;
import org.zetool.container.priority.MinHeap;
import org.zetool.graph.Edge;
import org.zetool.container.collection.IdentifiableCollection;
import org.zetool.graph.Node;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.container.mapping.IdentifiableObjectMapping;
import org.zetool.graph.DirectedGraph;
import org.zetool.graph.Graph;

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
        DijkstraRunner runner = new DijkstraRunner(getProblem(), createAccessor());
        return new IntegralShortestPathSolution(getProblem().getGraph().nodes(), runner.distances, runner.edges, runner.nodes);
    }

    private Function<Node, IdentifiableCollection<Edge>> createAccessor() {
        if (isDirectedInstance()) {
            return createAccessor((DirectedGraph) getProblem().getGraph());
        } else {
            return getProblem().getGraph()::incidentEdges;
        }
    }

    private boolean isDirectedInstance() {
        return getProblem().getGraph().isDirected();
    }

    private Function<Node, IdentifiableCollection<Edge>> createAccessor(DirectedGraph graph) {
        return reverse ? graph::incomingEdges : graph::outgoingEdges;
    }

    /**
     * Internal runner for Dijkstr's algorithm.
     */
    private class DijkstraRunner {

        private final IdentifiableIntegerMapping<Edge> costs;
        private final Graph graph;
        private final Node source;
        private final Node target;
        private final MinHeap<Node, Integer> queue;
        /**
         * Graph type agnostic incident edge accessor.
         */
        private final Function<Node, IdentifiableCollection<Edge>> incidentEdges;

        private final IdentifiableIntegerMapping<Node> distances;
        private final IdentifiableObjectMapping<Node, Edge> edges;
        private final IdentifiableObjectMapping<Node, Node> nodes;

        /**
         * Initializes the runner with the required input parameters and runs the shortest paths computation.
         *
         * @param graph the graph instance
         * @param costs the edge costs
         * @param source the source code
         * @param target the optional target node, can be {@code null}
         */
        DijkstraRunner(IntegralSingleSourceShortestPathProblem problemInstance, @NonNull Function<Node, IdentifiableCollection<Edge>> incidentEdges) {
            this.graph = problemInstance.getGraph();
            this.costs = problemInstance.getCosts();
            this.source = problemInstance.getSource();
            this.target = problemInstance.getTarget().orElse(null);
            this.incidentEdges = incidentEdges;
            distances = new IdentifiableIntegerMapping<>(graph.nodeCount());
            edges = new IdentifiableObjectMapping<>(graph.edgeCount());
            nodes = new IdentifiableObjectMapping<>(graph.nodeCount());
            queue = new MinHeap<>(graph.nodeCount());
            run();
        }

        private void run() {
            init();
            while (!queue.isEmpty()) {
                MinHeap<Node, Integer>.Element minElement = queue.extractMin();

                Node v = minElement.getObject();
                Integer vDistance = minElement.getPriority();
                distances.set(v, vDistance);

                if (v.equals(target)) {
                    return;
                }

                decreaseIncidentEdges(v, vDistance);
            }
        }

        private void init() {
            for (int v = 0; v < graph.nodeCount(); v++) {
                distances.set(graph.getNode(v), Integer.MAX_VALUE);
                queue.insert(graph.getNode(v), Integer.MAX_VALUE);
            }
            distances.set(source, 0);
            queue.decreasePriority(source, 0);
        }

        private void decreaseIncidentEdges(Node v, Integer minDistance) {
            for (Edge edge : incidentEdges.apply(v)) {
                Node candidate = edge.opposite(v);
                if (notClassified(candidate) && isEdgeImproving(candidate, minDistance, edge)) {
                    queue.decreasePriority(candidate, minDistance + costs.get(edge));
                    edges.set(candidate, edge);
                    nodes.set(candidate, v);
                }
            }
        }

        /**
         * Checks whether a node has already received its shortest path distance.
         *
         * @param node the node
         * @return {@code true} if the shortest path distance to {@code node} is already known
         */
        private boolean notClassified(Node node) {
            return queue.contains(node);
        }

        /**
         * Checks whether the shortest path distance to a node can be improved by using a certain edge.
         *
         * @param candidate the candidate node to which the edge is pointing to
         * @param currentNodeDistance the current node distance
         * @param edge the edge that is evaluated
         * @return {@code true} if using the {@code edge} yields a shorter path to {@link candidate}
         */
        private boolean isEdgeImproving(Node candidate, Integer currentNodeDistance, Edge edge) {
            return (long) queue.priority(candidate) > (long) currentNodeDistance + (long) costs.get(edge);
        }

    }
}
