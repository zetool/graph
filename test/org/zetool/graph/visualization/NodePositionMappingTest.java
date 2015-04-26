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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.zetool.graph.Node;
import org.zetool.math.geom.NDimensional;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class NodePositionMappingTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testDimensionCheck() {
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
