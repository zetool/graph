/* zet evacuation tool copyright © 2007-20 zet evacuation team
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
package org.zetool.algorithm.steinertree;

import org.zetool.container.collection.IdentifiableCollection;
import org.zetool.container.collection.ListSequence;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.graph.DefaultGraph;
import org.zetool.graph.Edge;
import org.zetool.graph.MutableGraph;
import org.zetool.graph.Node;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class SteinerTreeTestInstances {

    /**
     * A wheel graph with outer edge cost 10 and inner edge cost 5. Optimal Steiner tree has cost of 15 and uses the
     * inner node of the wheel as Steiner node.
     */
    public static final MinSteinerTreeProblem WHEEL_3 = createWheelGraphInstance(3, 10, 5);

    public static MinSteinerTreeProblem createWheelGraphInstance(int outerNodes, int outerCost, int innerCost) {
        if (outerNodes <= 2) {
            throw new IllegalArgumentException("At least 3 outer nodes required (triangle).");
        }

        int edgeCount = 2 * outerNodes;
        MutableGraph graph = new DefaultGraph(outerNodes + 1, edgeCount);
        Node center = graph.getNode(outerNodes);
        IdentifiableIntegerMapping<Edge> distances = new IdentifiableIntegerMapping<>(graph.edges());
        IdentifiableCollection<Node> terminals = new ListSequence<>();
        for (int i = 0; i < outerNodes; ++i) {
            Node start = graph.getNode(i);
            Node end = graph.getNode(i + 1 % outerNodes);
            Edge outerEdge = graph.createAndSetEdge(start, end);
            distances.set(outerEdge, outerCost);
            Edge innerEdge = graph.createAndSetEdge(start, center);
            distances.set(innerEdge, innerCost);
            terminals.add(start);
        }

        return new MinSteinerTreeProblem(graph, distances, terminals);
    }
}
