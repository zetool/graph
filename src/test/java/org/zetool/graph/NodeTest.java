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
package org.zetool.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class NodeTest {

  @Test
  public void testEquals() {
    Node original = new Node( 1 );
    Node copy = new Node( 1 );
    assertNotSame( original, copy );
    assertEquals( "Node as to be equal to itself", original, original );
    assertEquals( "Two nodes are equal", original, copy );
    assertEquals( "Two nodes are equal", copy, original );
    assertThat( "Cannot be equal to null", original, not( equalTo( null ) ) );
    assertThat( "Not equal with different class", original, not( equalTo( new NodeTest() ) ) );
    int hashCode = original.hashCode();
    assertEquals( "Hash code not equal", hashCode, copy.hashCode() );
    assertEquals( "Hash code does not change", hashCode, original.hashCode() );
    Node different = new Node( 2 );
    assertThat( "Hash code not different", hashCode, is( not( different.hashCode() ) ) );
  }
  
  @Test
  public void testClone() throws CloneNotSupportedException {
    Node original = new Node( 1 );
    Node copy = original.clone();
    assertEquals( "Cloned not equal", original, copy );
    assertNotSame( "Cloned is the same", original, copy );
  }
  
  @Test
  public void testId() {
    Node n = new Node( 2 );
    assertEquals( "ID not correct", n.id(), 2 );
  }
  
  @Test
  public void testToString() {
    Node n = new Node( 335 );
    assertEquals( "String representation wrong", n.toString(), "335" );
  }
}
