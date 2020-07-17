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

import org.zetool.container.collection.IdentifiableCollection;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.container.mapping.IdentifiableObjectMapping;
import org.zetool.graph.Edge;
import org.zetool.graph.Node;
import org.zetool.graph.structure.Forest;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class IntegralShortestPathSolution {

    private final IdentifiableCollection<Node> nodes;
    private final IdentifiableIntegerMapping<Node> distances;
    private final IdentifiableObjectMapping<Node, Edge> predecessorEdges;
    private final IdentifiableObjectMapping<Node, Node> predecessorNodes;

    public IntegralShortestPathSolution(IdentifiableCollection<Node> nodes, IdentifiableIntegerMapping<Node> distances,
            IdentifiableObjectMapping<Node, Edge> predecessorEdges, IdentifiableObjectMapping<Node, Node> predecessorNodes) {
        this.nodes = nodes;
        this.distances = distances;
        this.predecessorEdges = predecessorEdges;
        this.predecessorNodes = predecessorNodes;
    }

    public int getDistance(Node node) {

        return distances.get(node);
    }

    public IdentifiableObjectMapping<Node, Edge> getLastEdges() {
        return predecessorEdges;
    }

    public Edge getLastEdge(Node node) {
        return predecessorEdges.get(node);
    }

    public IdentifiableObjectMapping<Node, Node> getPredecessors() {
        return predecessorNodes;
    }

    public Node getPredecessor(Node node) {
        return predecessorNodes.get(node);
    }

    public Forest getForest() {
        return new Forest(nodes, predecessorEdges);
    }
}
