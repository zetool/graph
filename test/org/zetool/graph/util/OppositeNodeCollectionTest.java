
package org.zetool.graph.util;

import java.io.NotSerializableException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class OppositeNodeCollectionTest {

  @Test
  public void testSize() {
  }
  
  @Test(expected = UnsupportedOperationException.class)
  public void testAddNotSupported() {
    OppositeNodeCollection onc = new OppositeNodeCollection(null, null );
    onc.add( null );
  }
  
  @Test(expected = UnsupportedOperationException.class)
  public void testRemoveNotSupported() {
    OppositeNodeCollection onc = new OppositeNodeCollection(null, null );
    onc.remove( null );
  }
  
  @Test(expected = UnsupportedOperationException.class)
  public void testRemoveLastNotSupported() {
    OppositeNodeCollection onc = new OppositeNodeCollection(null, null );
    onc.removeLast();
  }
  
  @Test(expected = UnsupportedOperationException.class)
  public void testGetNotSupported() {
    OppositeNodeCollection onc = new OppositeNodeCollection(null, null );
    onc.get( 2 );
  }
}
