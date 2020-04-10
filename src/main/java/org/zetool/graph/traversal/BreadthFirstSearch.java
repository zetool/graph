package org.zetool.graph.traversal;

import org.zetool.common.util.Helper;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.container.mapping.IdentifiableObjectMapping;
import org.zetool.graph.util.PredecessorIterator;
import org.zetool.graph.util.PredecessorMap;
import org.zetool.graph.Edge;
import org.zetool.graph.Graph;
import org.zetool.graph.Node;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.zetool.common.algorithm.AbstractAlgorithm;

/**
 * Implementation of the breadth first search. The nodes are given numbers for a bfs numbering, the predecessor arcs
 * arce stored to construct shortest paths and the set of arcs is divided into forward/tree arcs and backward arcs.
 *
 * @author Jan-Philipp Kappmeier
 */
public class BreadthFirstSearch extends AbstractAlgorithm<Graph, Void>
        implements PredecessorMap<Edge, Node>, Iterable<Edge> {

    /** The predecessor nodes. */
    private IdentifiableObjectMapping<Node, Edge> predecessors;
    /** The node distances comuted by one run of the algorithm. */
    private IdentifiableIntegerMapping<Node> distances;
    /** All nodes reachable by one run of the algorithm. */
    private HashSet<Node> reachableNodes;
    /** The start node for the search. If null, all nodes are iterated over. */
    private Node start;
    /** Defines a target node that is used to stop the traversal. May be {@code null}. */
    private Node stop;

    /**
     * Defines the starting vertex of the breadth first search. If no such vertex is defined, the breadth first search
     * traverses through all vertices, starting with an arbitrary vertex. If necessary, it starts from multiple
     * vertices. If a start vertex is specified, exaftly one run of breadth first search starts from this vertex.
     *
     * @param source the start vertex, can be {@code null}
     */
    public void setStart(Node source) {
        this.start = source;
    }

    /**
     * Defines the stop vertex of the breadth first search. If no stop vertex is defined, the algorithm stops when all
     * reachable nodes are traversed. If a stop vertex is defined, the algorithm stops immediately after the vertex has
     * been reached.
     *
     * @param stop the stop vertex, can be {@code null}
     */
    public void setStop(Node stop) {
        this.stop = stop;
    }

    @Override
    protected Void runAlgorithm(Graph problem) {
        GeneralBreadthFirstSearchIterator bfs = start == null ? new GeneralBreadthFirstSearchIterator(problem)
                : new GeneralBreadthFirstSearchIterator(problem, start.id());
        predecessors = new IdentifiableObjectMapping<>(problem.nodeCount());
        distances = new IdentifiableIntegerMapping<>(problem.nodeCount());
        for (Node n : problem) {
            distances.set(n, Integer.MAX_VALUE);
        }

        for (EdgeNodePair n : Helper.in(bfs)) {
            predecessors.set(n.getNode(), n.getPred());
            if (n.getPred() != null) {
                distances.set(n.getNode(), distances.get(predecessors.get(n.getNode()).start()) + 1);
            } else {
                distances.set(n.getNode(), 0);
            }
            if (n.getNode().equals(stop)) {
                return null;
            }
        }
        return null;
    }

    public int getDistance(Node end) {
        return distances.get(end);
    }

    /**
     * Returns the edge in the shortest path tree that is incoming to node {@code n}.
     *
     * @param n the node
     * @return the predecessor arc on the shortest path
     */
    @Override
    public Edge getPredecessor(Node n) {
        return predecessors.get(n);
    }

    @Override
    public Iterator<Edge> iterator() {
        if (stop == null) {
            throw new IllegalArgumentException("Iterator only valid if single stopping element is given.");
        }
        return new PredecessorIterator(stop, this);
    }

    /**
     * Returns a set containing all nodes found by the run of the algorithm.
     *
     * @return a set of reachable nodes
     */
    public Set<Node> getReachableNodes() {
        if (!this.isProblemSolved()) {
            throw new IllegalStateException("Can only be called once the algorithm has run!");
        }
        if (reachableNodes == null) {
            reachableNodes = new HashSet<>();
        }
        for (Node n : getProblem()) {
            if (distances.get(n) < Integer.MAX_VALUE) {
                reachableNodes.add(n);
            }
        }
        return reachableNodes;
    }

    public boolean isReachable(Node node) {
        return getReachableNodes().contains(node);
    }
}
