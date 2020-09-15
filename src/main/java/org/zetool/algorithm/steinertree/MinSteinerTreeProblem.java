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
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.graph.Edge;
import org.zetool.graph.Graph;
import org.zetool.graph.Node;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class MinSteinerTreeProblem {

    private final Graph graph;
    private final IdentifiableIntegerMapping<Edge> weights;
    /**
     * The terminal nodes. These are the nodes that have to be spanned.
     */
    private final IdentifiableCollection<Node> terminalNodes;

    public MinSteinerTreeProblem(Graph graph, IdentifiableIntegerMapping<Edge> weights, IdentifiableCollection<Node> terminals) {
        // TODO: check, that the terminal nodes are actually a subset of the graph nodes
        this.graph = graph;
        this.weights = weights;
        this.terminalNodes = terminals;
    }

    public Graph getGraph() {
        return graph;
    }

    /**
     * Returns the edge weights.
     *
     * @return the integral weights on the edges
     */
    public IdentifiableIntegerMapping<Edge> getWeights() {
        return weights;
    }

    public IdentifiableCollection<Node> getTerminals() {
        return terminalNodes;
    }

}
