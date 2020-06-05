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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.zetool.container.mapping.IdentifiableConstantMapping;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.graph.DefaultDirectedGraph;
import org.zetool.graph.Edge;
import org.zetool.graph.MutableDirectedGraph;
import org.zetool.graph.Node;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DijkstraTest {

    @Test
    public void singleNode() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(1, 0);
        IdentifiableIntegerMapping<Edge> costs = new IdentifiableConstantMapping<>(0);
        Dijkstra dijkstra = new Dijkstra(graph, costs, graph.getNode(0));
        dijkstra.run();

        assertThat(dijkstra.getDistance(graph.getNode(0)), is(equalTo(0)));
    }

    @Test
    public void singleEdge() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(2, 1);
        graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        IdentifiableIntegerMapping<Edge> costs = new IdentifiableConstantMapping<>(4);
        Dijkstra dijkstra = new Dijkstra(graph, costs, graph.getNode(0));
        dijkstra.run();

        assertThat(dijkstra.getDistance(graph.getNode(0)), is(equalTo(0)));
        assertThat(dijkstra.getDistance(graph.getNode(1)), is(equalTo(4)));
    }

    @Test
    public void parallelEdges() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(2, 2);
        Edge longEdge = graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        Edge shortEdge = graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        IdentifiableIntegerMapping<Edge> costs = new IdentifiableIntegerMapping<>(2);
        costs.set(shortEdge, 1);
        costs.set(longEdge, 1);
        Dijkstra dijkstra = new Dijkstra(graph, costs, graph.getNode(0));
        dijkstra.run();

        assertThat(dijkstra.getDistance(graph.getNode(0)), is(equalTo(0)));
        assertThat(dijkstra.getDistance(graph.getNode(1)), is(equalTo(1)));
    }

    @Test
    public void disconnectedNodes() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(2, 0);
        IdentifiableIntegerMapping<Edge> costs = new IdentifiableIntegerMapping<>(2);
        Dijkstra dijkstra = new Dijkstra(graph, costs, graph.getNode(0));
        dijkstra.run();

        assertThat(dijkstra.getDistance(graph.getNode(0)), is(equalTo(0)));
        assertThat(dijkstra.getDistance(graph.getNode(1)), is(equalTo(Integer.MAX_VALUE)));
    }

    @Test
    public void noReverseSearch() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(2, 1);
        graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        IdentifiableIntegerMapping<Edge> costs = new IdentifiableConstantMapping<>(4);
        Dijkstra dijkstra = new Dijkstra(graph, costs, graph.getNode(1));
        dijkstra.run();

        assertThat(dijkstra.getDistance(graph.getNode(0)), is(equalTo(Integer.MAX_VALUE)));
        assertThat(dijkstra.getDistance(graph.getNode(1)), is(equalTo(0)));
    }

    @Test
    public void graphWithTwoPaths() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(4, 4);

        Node source = graph.getNode(0);
        Node upperIntermediate = graph.getNode(1);
        Node lowerIntermediate = graph.getNode(2);
        Node target = graph.getNode(3);

        Edge upperFirst = graph.createAndSetEdge(source, upperIntermediate);
        Edge upperLast = graph.createAndSetEdge(upperIntermediate, target);
        Edge lowerFirst = graph.createAndSetEdge(source, lowerIntermediate);
        Edge lowerLast = graph.createAndSetEdge(lowerIntermediate, target);

        IdentifiableIntegerMapping<Edge> costs = new IdentifiableIntegerMapping<>(2);
        costs.set(upperFirst, 0);
        costs.set(upperLast, 1);
        costs.set(lowerFirst, 1);
        costs.set(lowerLast, 1);

        Dijkstra dijkstra = new Dijkstra(graph, costs, source);
        dijkstra.run();

        assertThat(dijkstra.getDistance(source), is(equalTo(0)));
        assertThat(dijkstra.getDistance(upperIntermediate), is(equalTo(0)));
        assertThat(dijkstra.getDistance(lowerIntermediate), is(equalTo(1)));
        assertThat(dijkstra.getDistance(target), is(equalTo(1)));
    }

    /**
     * Shortest path along {@code s -> 3 -> 1 -> t} with cost 3.
     */
    @Test
    public void graphWithDirectedCycle() {
        CycleGraph cycleGraph = new CycleGraph();

        IdentifiableIntegerMapping<Edge> costs = new IdentifiableIntegerMapping<>(2);
        costs.set(cycleGraph.in1, 2);
        costs.set(cycleGraph.in2, 2);
        costs.set(cycleGraph.in3, 1);
        costs.set(cycleGraph.out1, 1);
        costs.set(cycleGraph.out2, 2);
        costs.set(cycleGraph.out3, 2);
        costs.set(cycleGraph.cycle12, 1);
        costs.set(cycleGraph.cycle23, 1);
        costs.set(cycleGraph.cycle13, 1);

        Dijkstra dijkstra = new Dijkstra(cycleGraph.graph, costs, cycleGraph.source);
        dijkstra.run();

        assertThat(dijkstra.getDistance(cycleGraph.source), is(equalTo(0)));
        assertThat(dijkstra.getDistance(cycleGraph.node1), is(equalTo(2)));
        assertThat(dijkstra.getDistance(cycleGraph.node2), is(equalTo(2)));
        assertThat(dijkstra.getDistance(cycleGraph.node3), is(equalTo(1)));
        assertThat(dijkstra.getDistance(cycleGraph.target), is(equalTo(3)));
    }

    /**
     * Shortest path along {@code s -> 1 -> 2 -> t} with cost 3. The path uses a negative cost arc.
     */
    @Test
    public void failsForraphWithDirectedCycleZeroCost() {
        CycleGraph cycleGraph = new CycleGraph();

        IdentifiableIntegerMapping<Edge> costs = new IdentifiableIntegerMapping<>(2);
        costs.set(cycleGraph.in1, 2);
        costs.set(cycleGraph.in2, 2);
        costs.set(cycleGraph.in3, 2);
        costs.set(cycleGraph.out1, 2);
        costs.set(cycleGraph.out2, 2);
        costs.set(cycleGraph.out3, 2);
        costs.set(cycleGraph.cycle12, 1);
        costs.set(cycleGraph.cycle23, -1);
        costs.set(cycleGraph.cycle13, 0);

        Dijkstra dijkstra = new Dijkstra(cycleGraph.graph, costs, cycleGraph.source);
        dijkstra.run();

        assertThat(dijkstra.getDistance(cycleGraph.source), is(equalTo(0)));
        assertThat(dijkstra.getDistance(cycleGraph.node1), is(equalTo(2)));

        int correctDistanceWithNegativeCost = 1;
        int wrongDistanceIgnoringNegativeCost = correctDistanceWithNegativeCost + 1;
        assertThat(dijkstra.getDistance(cycleGraph.node2), is(equalTo(wrongDistanceIgnoringNegativeCost)));

        assertThat(dijkstra.getDistance(cycleGraph.node3), is(equalTo(2)));

        int correctShortestPathDistance = 3;
        int wrongShortestPathDistance = correctShortestPathDistance + 1;
        assertThat(dijkstra.getDistance(cycleGraph.target), is(equalTo(wrongShortestPathDistance)));
    }

    /**
     * A graph consisting of a directed cycle with three nodes which are connected to both, source and sink.
     */
    private static class CycleGraph {

        private final MutableDirectedGraph graph = new DefaultDirectedGraph(5, 9);

        private final Node source = graph.getNode(0);
        private final Node node1 = graph.getNode(1);
        private final Node node2 = graph.getNode(2);
        private final Node node3 = graph.getNode(3);
        private final Node target = graph.getNode(4);

        private final Edge in1 = graph.createAndSetEdge(source, node1);
        private final Edge in2 = graph.createAndSetEdge(source, node2);
        private final Edge in3 = graph.createAndSetEdge(source, node3);

        private final Edge out1 = graph.createAndSetEdge(node1, target);
        private final Edge out2 = graph.createAndSetEdge(node2, target);
        private final Edge out3 = graph.createAndSetEdge(node3, target);

        private final Edge cycle12 = graph.createAndSetEdge(node1, node2);
        private final Edge cycle23 = graph.createAndSetEdge(node2, node3);
        private final Edge cycle13 = graph.createAndSetEdge(node3, node1);
    }
}
