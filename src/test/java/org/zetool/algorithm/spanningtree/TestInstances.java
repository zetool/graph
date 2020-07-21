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

import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.graph.DefaultGraph;
import org.zetool.graph.Edge;
import org.zetool.graph.MutableGraph;
import org.zetool.graph.Node;

/**
 * Collection of test instances for minimum spanning tree algorithms.
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestInstances {

    /**
     * Produces a test instance with 6 nodes, 11 edges and optimal solution cost of 39.
     *
     * @return the minimum spanning tree test instance
     */
    static MinSpanningTreeProblem createComplexSingleComponentInstance() {
        MutableGraph graph = new DefaultGraph(7, 11);

        Node a = graph.getNode(0);
        Node b = graph.getNode(1);
        Node c = graph.getNode(2);
        Node d = graph.getNode(3);
        Node e = graph.getNode(4);
        Node f = graph.getNode(5);
        Node g = graph.getNode(6);

        IdentifiableIntegerMapping<Edge> weights = new IdentifiableIntegerMapping<>(11);
        setEdgeAndWeight(graph, a, b, weights, 7);
        setEdgeAndWeight(graph, a, d, weights, 5);
        setEdgeAndWeight(graph, b, c, weights, 8);
        setEdgeAndWeight(graph, b, d, weights, 9);
        setEdgeAndWeight(graph, b, e, weights, 7);
        setEdgeAndWeight(graph, c, e, weights, 5);
        setEdgeAndWeight(graph, d, e, weights, 15);
        setEdgeAndWeight(graph, d, f, weights, 6);
        setEdgeAndWeight(graph, e, f, weights, 8);
        setEdgeAndWeight(graph, e, g, weights, 9);
        setEdgeAndWeight(graph, f, g, weights, 11);

        return new MinSpanningTreeProblem(graph, weights);
    }

    /**
     * Produces a test instance with 15 nodes, 29 edges and optimal solution consisting of two components with
     * respective costs of 38 and 12.
     *
     * @return the minimum spanning tree test instance
     */
    static MinSpanningTreeProblem createComplexTwoComponentInstance() {
        MutableGraph graph = new DefaultGraph(15, 29);

        Node a01 = graph.getNode(0);
        Node a02 = graph.getNode(1);
        Node a03 = graph.getNode(2);
        Node a04 = graph.getNode(3);
        Node a05 = graph.getNode(4);
        Node a06 = graph.getNode(5);
        Node a07 = graph.getNode(6);
        Node a08 = graph.getNode(7);
        Node a09 = graph.getNode(8);
        Node a10 = graph.getNode(9);

        IdentifiableIntegerMapping<Edge> weights = new IdentifiableIntegerMapping<>(11);
        setEdgeAndWeight(graph, a01, a02, weights, 3);
        setEdgeAndWeight(graph, a01, a04, weights, 6);
        setEdgeAndWeight(graph, a01, a05, weights, 9);
        setEdgeAndWeight(graph, a02, a03, weights, 2);
        setEdgeAndWeight(graph, a02, a04, weights, 4);
        setEdgeAndWeight(graph, a02, a05, weights, 9);
        setEdgeAndWeight(graph, a02, a06, weights, 9);
        setEdgeAndWeight(graph, a03, a04, weights, 2);
        setEdgeAndWeight(graph, a03, a06, weights, 8);
        setEdgeAndWeight(graph, a03, a07, weights, 9);
        setEdgeAndWeight(graph, a04, a07, weights, 9);
        setEdgeAndWeight(graph, a05, a06, weights, 8);
        setEdgeAndWeight(graph, a05, a10, weights, 18);
        setEdgeAndWeight(graph, a06, a07, weights, 7);
        setEdgeAndWeight(graph, a06, a09, weights, 9);
        setEdgeAndWeight(graph, a06, a10, weights, 10);
        setEdgeAndWeight(graph, a07, a08, weights, 4);
        setEdgeAndWeight(graph, a07, a09, weights, 5);
        setEdgeAndWeight(graph, a08, a09, weights, 1);
        setEdgeAndWeight(graph, a08, a10, weights, 4);
        setEdgeAndWeight(graph, a09, a10, weights, 3);

        Node b1 = graph.getNode(10);
        Node b2 = graph.getNode(11);
        Node b3 = graph.getNode(12);
        Node b4 = graph.getNode(13);
        Node b5 = graph.getNode(14);

        setEdgeAndWeight(graph, b1, b3, weights, 10);
        setEdgeAndWeight(graph, b1, b2, weights, 3);
        setEdgeAndWeight(graph, b1, b4, weights, 6);
        setEdgeAndWeight(graph, b1, b5, weights, 8);
        setEdgeAndWeight(graph, b2, b4, weights, 2);
        setEdgeAndWeight(graph, b3, b4, weights, 4);
        setEdgeAndWeight(graph, b3, b5, weights, 5);
        setEdgeAndWeight(graph, b4, b5, weights, 3);

        return new MinSpanningTreeProblem(graph, weights);
    }

    private static void setEdgeAndWeight(MutableGraph graph, Node a, Node b,
            IdentifiableIntegerMapping<Edge> weights, int weight) {
        Edge newEdge = graph.createAndSetEdge(a, b);
        weights.set(newEdge, weight);
    }
}
