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
import org.zetool.graph.Edge;

/**
 *
 * @author Marlen Schwengfelder
 */
public class SteinerTree {

    private final IdentifiableCollection<Edge> Edges;
    private final long cost;

    public SteinerTree(IdentifiableCollection<Edge> Edges, long cost) {
        this.Edges = Edges;
        this.cost = cost;
    }

    /**
     * Returns the edges of the Steiner tree.
     *
     * @return
     */
    public IdentifiableCollection<Edge> getEdges() {
        return Edges;
    }

    /**
     * Returns the cost of the computed Steiner tree. The cost of the tree is the sum of all edge costs.
     *
     * @return the cost of the Steiner tree
     */
    public long getCost() {
        return cost;
    }

}
