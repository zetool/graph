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

package org.zetool.graph.visualization;

import org.zetool.container.mapping.IdentifiableObjectMapping;
import org.zetool.graph.Node;
import org.zetool.math.geom.NDimensional;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <E>
 */
public class NodePositionMapping<E extends NDimensional<? extends Number>> extends IdentifiableObjectMapping<Node,E> {
  private final int dimension;

  public NodePositionMapping( int dimension, IdentifiableObjectMapping<Node, E> mapping ) {
    super( mapping );
    for( E e : mapping ) {
      if( e.getDimension() != dimension ) {
        throw new IllegalArgumentException( "Dimension of points in mapping are not equal!" );
      }      
    }
    this.dimension = dimension;
  }

  public NodePositionMapping( int dimension, Iterable<Node> domain ) {
    super( domain );
    this.dimension = dimension;
  }

  /**
   * Initializes a new {@code NodePositionMapping} and ensures that the points contained in the original mapping have
   * the right dimension.
   * @param dimension the dimension of points
   * @param mapping the original mapping
   */
  public NodePositionMapping( int dimension, E[] mapping ) {
    super( mapping );
    // Test data
    for( E e : mapping ) {
      if( e != null && e.getDimension() != dimension ) {
        throw new IllegalArgumentException( "Dimension of points in mapping are not equal!" );
      }
    }
    this.dimension = dimension;
  }
  
  public NodePositionMapping( int dimension, int domainSize ) {
    super( domainSize );
    this.dimension = dimension;
  }

  @Override
  public void set( Node identifiableObject, E value ) {
    if( value.getDimension() != getDimension() ) {
      throw new IllegalArgumentException( "Dimensions not equal!" );
    }
    super.set( identifiableObject, value );
  }

  public final int getDimension() {
    return dimension;
  }
}
