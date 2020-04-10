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

package org.zetool.graph.visualization;

import java.util.LinkedList;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.zetool.container.mapping.IdentifiableObjectMapping;
import org.zetool.graph.Node;
import org.zetool.math.geom.NDimensional;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class NodePositionMappingTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Tests two default constructors initializing an empty node position mapping.
   */
  @Test
  public void testNonFailConstructors() {
    NodePositionMapping<DimXPoint> npm = new NodePositionMapping<>(9, new LinkedList<>() );
    assertEquals( 9, npm.getDimension() );

    npm = new NodePositionMapping<>(15,2);
    assertEquals( 15, npm.getDimension() );
  }
  
  /**
   * Tests constructor using existing data in a mapping. In contrast to {@link #testNonFailConstructors() }, this
   * should fail if the dimension are not equal.
   */
  @Test
  public void testCopyConstructor() {
    IdentifiableObjectMapping<Node,DimXPoint> mapping = new IdentifiableObjectMapping<>( 6 );
    mapping.set( new Node(0), () -> 13 );
    NodePositionMapping<DimXPoint> npm = new NodePositionMapping<>( 13 , mapping );
    assertEquals( 13, npm.getDimension() );
    
    exception.expect(IllegalArgumentException.class);
    mapping.set( new Node(0), () -> 3 );
    new NodePositionMapping<>( 13 , mapping );
  }
  
  /**
   * Tests failure by copying data from an array. In contrast to {@link #testNonFailConstructors() }, this
   * should fail if the dimension are not equal.
   */
  @Test public void testArrayConstructor() {
    DimXPoint[] points = new DimXPoint[2];
    NodePositionMapping<DimXPoint> npm = new NodePositionMapping<>( 13, points );
    points = new DimXPoint[]{ () -> 11, () -> 11 };
    npm = new NodePositionMapping<>(11, points );
    assertEquals( 11, npm.getDimension() );

    exception.expect(IllegalArgumentException.class);
    npm = new NodePositionMapping<>(2, points);
  }

  /**
   * Tests immediat fail for {@literal null} reference array.
   */
  @Test
  public void testArrayNullConstructor() {
    exception.expect( NullPointerException.class );
    new NodePositionMapping<DimXPoint>( 4, (DimXPoint[])null );
  }
  
  @Test
  public void testDimensionCheckInvalid() {
    NodePositionMapping<DimXPoint> npm = new NodePositionMapping<>(2,2);
    exception.expect(IllegalArgumentException.class);
    npm.set( null, () -> 3 );
  }

  @Test
  public void testDimensionCheckValid() {
    NodePositionMapping<DimXPoint> npm = new NodePositionMapping<>(2,2);
    npm.set( new Node(0), () -> 2 );
  }

  @Test
  public void testDimensionCheckValidInvalid() {
    NodePositionMapping<DimXPoint> npm = new NodePositionMapping<>(2,2);
    npm.set( new Node(0), () -> 2 );
    npm.set( new Node(1), () -> 2 );
    exception.expect(IllegalArgumentException.class);
    npm.set( null, () -> 1 );
  }
  
  @FunctionalInterface
  private interface DimXPoint extends NDimensional<Integer> {
    @Override
    default Integer get( int i ) {
      throw new AssertionError( "get called.");
    }
  } 
}
