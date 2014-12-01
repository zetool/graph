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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.tu_berlin.coga.graph.structure;

import de.tu_berlin.coga.graph.Edge;
import de.tu_berlin.coga.graph.Node;
import de.tu_berlin.coga.container.collection.IdentifiableCollection;
import de.tu_berlin.coga.container.mapping.IdentifiableObjectMapping;

/**
 * The {@code AbstractNetwork</class> provides an implementation of a directed graph
 * optimized for use by flow algorithms. Examples of these optimizations
 * include use of array based data structures for edges and nodes in order to
 * provide fast access, as well as the possiblity to hide edges and nodes (which
 * is useful for residual networks, for instance).
 */
//@XStreamAlias( "forest" )
public class Forest {
  //TODO: implement better forest datastructure, take care that no edges are
  //      created new if it is a shortest paths
  //forest on an existing graph

  private final IdentifiableObjectMapping<Node, Edge> precedingEdges;

  public Forest( IdentifiableCollection<Node> nodes, IdentifiableObjectMapping<Node, Edge> precedingEdges ) {
    this.precedingEdges = precedingEdges;
  }

  public Path getPathToRoot( Node node ) {
    Path result = new StaticPath();
    Edge edge;
    while( (edge = precedingEdges.get( node )) != null ) {
      result.addFirstEdge( edge );
      node = edge.opposite( node );
    }
    return result;
  }
}
