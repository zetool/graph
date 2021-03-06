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

import org.checkerframework.checker.nullness.qual.NonNull;
import org.zetool.container.collection.IdentifiableCollection;
import org.zetool.graph.Edge;

/**
 * A collection of edges forming an undirected tree.
 *
 * @author Marlen Schwengfelder
 * @author Jan-Philipp Kappmeier
 */
public class UndirectedForest {

    private final IdentifiableCollection<Edge> edges;

    public UndirectedForest(@NonNull IdentifiableCollection<Edge> edges) {
        this.edges = edges;
    }

    public IdentifiableCollection<Edge> getEdges() {
        return edges;
    }
}
