
package org.zetool.graph.util;

/**
 * An interface to iterate over a chain of predecessors of anchors.
 * @param <U> the type of predecessing
 * @param <V> the anchor type
 * @author Jan-Philipp Kappmeier
 */
public interface PredecessorMap<U,V> {
  /**
   */
  U getPredecessor( V v );
}
