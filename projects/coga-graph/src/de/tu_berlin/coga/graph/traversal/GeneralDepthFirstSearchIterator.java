/**
 * GeneralDepthFirstSearchIterator.java
 * Created: 12.05.2014, 19:48:14
 */
package de.tu_berlin.coga.graph.traversal;

import de.tu_berlin.coga.graph.Graph;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;


/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class GeneralDepthFirstSearchIterator extends AbstractGraphSearchIterator {


  public GeneralDepthFirstSearchIterator( Graph g ) {
    super( g, false, false, new StackAsQueue<>() );
  }

  public GeneralDepthFirstSearchIterator( Graph g, boolean iterateAllEdges ) {
    super( g, iterateAllEdges, false, new StackAsQueue<>() );
  }

  public GeneralDepthFirstSearchIterator( Graph g, boolean iterateAllEdges, boolean reverse ) {
    super( g, iterateAllEdges, reverse, new StackAsQueue<>() );
  }

  public GeneralDepthFirstSearchIterator( Graph g, int startNode ) {
    super( g, startNode, false, false, new StackAsQueue<>() );
  }

  public GeneralDepthFirstSearchIterator( Graph g, int startNode, boolean reverse ) {
    super( g, startNode, false, reverse, new StackAsQueue<>() );
  }

  private static class StackAsQueue<E> implements Queue<E> {
    private Stack<E> stack;
    StackAsQueue() {
      stack = new Stack<>();
    }

    @Override
    public boolean add( E e ) {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean offer( E e ) {
      return stack.push( e ) != null;
    }

    @Override
    public E remove() {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public E poll() {
      return stack.pop();
    }

    @Override
    public E element() {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public E peek() {
      return stack.peek();
    }

    @Override
    public int size() {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean isEmpty() {
      return stack.isEmpty();
    }

    @Override
    public boolean contains( Object o ) {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public Iterator<E> iterator() {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public Object[] toArray() {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public <T> T[] toArray( T[] a ) {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean remove( Object o ) {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean containsAll( Collection<?> c ) {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean addAll( Collection<? extends E> c ) {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean removeAll( Collection<?> c ) {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean retainAll( Collection<?> c ) {
      throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public void clear() {
      throw new UnsupportedOperationException( "Not supported yet." );
    }
  }
}
