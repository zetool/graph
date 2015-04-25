
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
    this.dimension = dimension;
  }

  public NodePositionMapping( int dimension, Iterable<Node> domain ) throws IllegalArgumentException {
    super( domain );
    this.dimension = dimension;
  }

  public NodePositionMapping( int dimension, E[] mapping ) throws IllegalArgumentException {
    super( mapping );
    this.dimension = dimension;
  }
  
  public NodePositionMapping( int dimension, int domainSize ) throws IllegalArgumentException {
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
