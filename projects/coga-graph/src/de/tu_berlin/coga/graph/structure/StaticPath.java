/* zet evacuation tool copyright (c) 2007-10 zet evacuation team
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

import de.tu_berlin.coga.graph.structure.Path;
import de.tu_berlin.coga.graph.Edge;
import de.tu_berlin.coga.container.collection.IdentifiableCollection;
import de.tu_berlin.coga.container.collection.ListSequence;
import de.tu_berlin.coga.graph.util.PredecessorMap;
import ds.graph.GraphLocalization;
import java.util.Iterator;

/**
 * The {@code StaticPath} class represents a static path in a {@link Network}. A static path is a sequence of
 * {@code Edge} objects, where the end node of an edge must be equal to the start node of the next edge (if there is
 * one). The sequence is internally stored as a {@link ListSequence}.
 */
public class StaticPath implements Path, Iterable<Edge> {

  /** The sequence of edges of this path. */
  protected ListSequence<Edge> edges;

  /**
   * Constructs a new path without edges. Edges can be added with the corresponding methods.
   */
  public StaticPath() {
    edges = new ListSequence<>();
  }

  /**
   * Constructs a new path with the given edges. The edges must be consistent, i.e. the endnode of an edge must be equal
   * to the startnode of the next edge (if there follows one more). If the edges are not consistent, an
   * {@code IllegalArgumentException} is thrown.
   * @param edges the edges the path shall be contained of
   */
  public StaticPath( Edge... edges ) {
    this();
    boolean consistent = true;
    for( Edge edge : edges ) {
      consistent &= addLastEdge( edge );
    }
    if( !consistent ) {
      throw new IllegalArgumentException( GraphLocalization.loc.getString( "ds.Graph.NotConsistentException" ) );
    }
  }

  /**
   * Constructs a new path with the given edges. The edges must be consistent, i.e. the endnode of an edge must be equal
   * to the startnode of the next edge (if there follows one more). If the edges are not consistent, an
   * {@code IllegalArgumentException} is thrown.
   * @param edges the edges the path shall be contained of
   */
  public StaticPath( Iterable<Edge> edges ) {
    this();
    boolean consistent = true;
    for( Edge edge : edges ) {
      consistent &= addLastEdge( edge );
    }
    if( !consistent ) {
      throw new IllegalArgumentException( GraphLocalization.loc.getString( "ds.Graph.NotConsistentException" ) );
    }
  }

  public StaticPath( PredecessorMap p ) {
    this();
    boolean consistent = true;
    for( Edge e : p ) {
      consistent &= addFirstEdge( e );
    }
    if( !consistent ) {
      throw new IllegalArgumentException( GraphLocalization.loc.getString( "ds.Graph.NotConsistentException" ) );
    }
  }

  /**
   * Returns the sequence of edges of this path as a {@link ListSequence}.
   * @return the sequence of edges of this path as a {@link ListSequence}.
   */
  public IdentifiableCollection<Edge> getEdges() {
    return edges;
  }

  /**
   * Private method to test whether the edge {@code pred<\code> can be followed
   * by the edge {@code succ}, i.e. the endnode of {@code pred<\code>
   * is equal to the startnode of {@code succ}.
   * @param pred first node
   * @param succ second node
   * @return {@code true} if {@code pred<\code> can be followed
   * by {@code succ}, {@code false} else.
   */
  private boolean isConsistent( Edge pred, Edge succ ) {
    return pred.end().equals( succ.start() );
  }

  /**
   * Extends the path by adding an edge at the end. The edge must be consistent to the current last edge of the path,
   * i.e. i.e. the end node of the current last edge must be equal to the start node of {@code edge}, else an exception
   * is thrown.
   * @param edge the edge to insert at the end of the path.
   * @return {@code true} if the insertion was successful.
   */
  public boolean addLastEdge( Edge edge ) {
    if( edges.empty() || isConsistent( edges.last(), edge ) ) {
      edges.add( edge );
      return true;
    } else {
      System.out.println( "StaticPath: " + edges.last() + " " + edge );
      throw new IllegalArgumentException( "" );
      //throw new AssertionError(GraphLocalization.getSingleton ().getString ("ds.Graph.EdgeCanNotAddedException"));
    }
  }

  /**
   * Extends the path by adding an edge at the start. The edge must be consistent to the current first edge of the path,
   * i.e. i.e. the startnode of the current first edge must be equal to the endnode of {@code edge}.
   * @param edge the edge to insert at the end of the path.
   * @return {@code true} if the insertion was successfull, {@code false} else.
   */
  public boolean addFirstEdge( Edge edge ) {
    if( edges.empty() || isConsistent( edge, edges.first() ) ) {
      edges.addFirst( edge );
      return true;
    } else {
      return false;
    }
  }

  /**
   * Shortens the path by removing the last edge. If the path is empty, nothing happens.
   * @return {@code false} if there was no element to be removed, {@code true} else.
   */
  public boolean removeLastEdge() {
    if( !edges.empty() ) {
      edges.removeLast();
      return true;
    } else {
      return false;
    }
  }

  /**
   * Shortens the path by removing the first edge. If the path is empty, nothing happens.
   * @return {@code false} if there was no element to be removed, {@code true} else.
   */
  public boolean removeFirstEdge() {
    if( !edges.empty() ) {
      edges.removeFirst();
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns an iterator for the edges of this path. With the iterator one can iterate comfortable through all the edges
   * of the path.
   * @return an iterator for the edges of this path.
   */
  public Iterator<Edge> iterator() {
    return edges.iterator();
  }

  /**
   * Returns the first edge of the path or null if the path is empty.
   * @return the first edge of the path or null if the path is empty.
   */
  public Edge first() {
    if( edges.empty() ) {
      return null;
    } else {
      return edges.getFirst();
    }
  }

  /**
   * Returns the last edge of the path or null if the path is empty.
   * @return the last edge of the path or null if the path is empty.
   */
  public Edge last() {
    if( edges.empty() ) {
      return null;
    } else {
      return edges.getLast();
    }
  }

  /**
   * Returns the length of this path, i.e. the number of edges.
   * @return the length of this path, i.e. the number of edges.
   */
  public int length() {
    return edges.size();
  }

    // to do: martin fragen was stringbuilder für vorteile haben
  /**
   * Returns a String containing all edges, seperated by commata. An edge e=(a,b) will be represented by (a,b) in the
   * string.
   * @return a String containing all edges, where the edges are identified by their nodes
   */
  public String nodesToString() {
    StringBuilder builder = new StringBuilder();
    builder.append( "{" );
    if( length() > 0 ) {
      for( Edge edge : this ) {
        builder.append( edge.nodesToString() );
        builder.append( ", " );
      }
      builder.delete( builder.length() - 2, builder.length() );
    }
    builder.append( "}" );
    return builder.toString();
  }

    // to do: durch toString von ListSequence ersetzen ? vielleicht ?
  /**
   * Returns a String containing the IDs of the edges of this path.
   * @return a String containing the IDs of the edges of this path.
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append( "(" );
    if( length() > 0 ) {
      for( Edge edge : this ) {
        builder.append( edge );
        builder.append( ", " );
      }
      builder.delete( builder.length() - 2, builder.length() );
    }
    builder.append( ")" );
    return builder.toString();
  }

  /**
   * Clones this path by cloning the sequence of edges (i.e. all edges are cloned) and creating a new {@code StaticPath}
   * with the clone.
   * @return a {@code StaticPath} object with clones of the edges of this object.
   */
  @Override
  public StaticPath clone() {
    return new StaticPath( edges.clone() );
  }

  /**
   * Returns whether an object is equal to this static path. The result is true if and only if the argument is not null
   * and is a {@code StaticPath} object having a sequence of edges that is equal to this path's sequence of edges (i.e.
   * all edges must have the same IDs).
   * @param o object to compare.
   * @return {@code true} if the given object represents a {@code StaticPath} equivalent to this node, {@code false}
   * otherwise.
   */
  @Override
  public boolean equals( Object o ) {
    if( o == null || !(o instanceof StaticPath) ) {
      return false;
    } else {
      StaticPath p = (StaticPath)o;
      return edges.equals( p.getEdges() );
    }
  }

  /**
   * Returns the hash code of this static path. The hash code is equal to the hash code of the contained
   * {@code ListSequence} object for the edges.
   * @return the hash code of this static path.
   */
  @Override
  public int hashCode() {
    return edges.hashCode();
  }

  public boolean contains( Edge e ) {
    for( Edge ed : this ) {
      if( ed.equals( e ) ) {
        return true;
      }
    }
    return false;
  }

}
