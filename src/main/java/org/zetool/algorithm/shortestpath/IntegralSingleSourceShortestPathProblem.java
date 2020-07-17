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

import java.util.Objects;
import java.util.Optional;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import org.zetool.graph.Edge;
import org.zetool.graph.Graph;
import org.zetool.graph.Node;
import org.zetool.container.mapping.IdentifiableIntegerMapping;

/**
 * Collects the inputs for the <i>Successive Shortest Path Problem</i>.
 *
 * @author Jan-Philipp Kappmeier
 */
public class IntegralSingleSourceShortestPathProblem {

    private final Graph graph;
    private final IdentifiableIntegerMapping<Edge> costs;
    private final Node source;
    private final Node target;

    /**
     * T
     *
     * @param graph the graph instance, directed or undirected
     * @param costs the edge costs
     * @param source the source node
     */
    public IntegralSingleSourceShortestPathProblem(@NonNull Graph graph, @NonNull IdentifiableIntegerMapping<Edge> costs, @NonNull Node source) {
        this(graph, costs, source, null);
    }

    /**
     * T
     *
     * @param graph the graph instance, directed or undirected
     * @param costs the edge costs
     * @param source the source node
     * @param target the sink node, can be {@code null}
     */
    public IntegralSingleSourceShortestPathProblem(@NonNull Graph graph, @NonNull IdentifiableIntegerMapping<Edge> costs, @NonNull Node source,
            @Nullable Node target) {
        this.costs = Objects.requireNonNull(costs);
        this.graph = Objects.requireNonNull(graph);
        this.source = Objects.requireNonNull(source);
        this.target = target;
    }

    public Graph getGraph() {
        return graph;
    }

    public IdentifiableIntegerMapping<Edge> getCosts() {
        return costs;
    }

    public Node getSource() {
        return source;
    }

    /**
     * Returns the optional sink node. Iteration stops at this node, if present.
     *
     * @return the optional sink node
     */
    public Optional<Node> getTarget() {
        return Optional.ofNullable(target);
    }

}
