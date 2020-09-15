/* zet evacuation tool copyright © 2007-20 zet evacuation team
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
package org.zetool.algorithm.steinertree;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.zetool.algorithm.shortestpath.Dijkstra;
import org.zetool.algorithm.shortestpath.IntegralSingleSourceShortestPathProblem;
import org.zetool.algorithm.spanningtree.KruskalAlgorithm;
import org.zetool.algorithm.spanningtree.MinSpanningTreeProblem;
import org.zetool.algorithm.spanningtree.UndirectedForest;
import org.zetool.common.algorithm.AbstractAlgorithm;
import org.zetool.common.debug.Debug;
import org.zetool.container.collection.IdentifiableCollection;
import org.zetool.container.collection.ListSequence;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.container.mapping.IdentifiableObjectMapping;
import org.zetool.graph.DefaultGraph;
import org.zetool.graph.Edge;
import org.zetool.graph.Graph;
import org.zetool.graph.MutableGraph;
import org.zetool.graph.Node;
import org.zetool.graph.structure.Forest;
import org.zetool.graph.structure.Path;

/**
 * Approximates the general Steiner tree minimization problem using a minimum spanning tree of the terminal nodes and
 * metric completion. By reduction this yields a 2-approximation of the optimal solution.
 *
 * @author Jan-Philipp Kappmeier
 */
public class SteinerTreeSpanningTreeApproximationAlgorithm extends AbstractAlgorithm<MinSteinerTreeProblem, SteinerTree> {

    /**
     * The logger object of this algorithm.
     */
    private static final Logger LOG = Debug.globalLogger;

    @Override
    protected SteinerTree runAlgorithm(MinSteinerTreeProblem problem) {
        // Phase 1:
        // Compute metric closure
        MetricClosure metricClosure = computeMetricClosure(problem.getTerminals());

        // Phase 2:
        // Compute MST
        UndirectedForest mst = computeMinimumSpanningTree(metricClosure);

        // Phase 3:
        // Build solution
        IdentifiableCollection<Edge> edges = createSolutionTree(mst, metricClosure);

        long cost = 0;
        for (Edge edge : edges) {
            cost += problem.getWeights().get(edge);
        }

        return new SteinerTree(edges, cost);
    }

    private MetricClosure computeMetricClosure(IdentifiableCollection<Node> terminals) {
        int terminalCount = terminals.size();
        LOG.log(Level.INFO, "Number of terminal Nodes: {0}", terminalCount);
        LOG.log(Level.FINE, "Terminals: {0}", terminals);

        int subgraphEdgeCount = (terminalCount * (terminalCount - 1)) / 2;
        MutableGraph geometricClosure = new DefaultGraph(terminalCount, subgraphEdgeCount);
        IdentifiableIntegerMapping<Edge> shortestpathDist = new IdentifiableIntegerMapping<>(subgraphEdgeCount);

        Dijkstra dijkstra = new Dijkstra();
        IdentifiableObjectMapping<Node, Forest> rootedForests = new IdentifiableObjectMapping<>(terminals);
        for (int i = 0; i < terminals.size(); ++i) {
            Node node = terminals.get(i);
            dijkstra.setProblem(new IntegralSingleSourceShortestPathProblem(getProblem().getGraph(), getProblem().getWeights(), node));
            dijkstra.run();
            rootedForests.set(node, dijkstra.getSolution().getForest());

            for (int j = i + 1; j < terminals.size(); ++j) {
                Node other = terminals.get(j);
                int dist = dijkstra.getSolution().getDistance(other);

                Edge edge = geometricClosure.createAndSetEdge(geometricClosure.getNode(i), geometricClosure.getNode(j));
                shortestpathDist.add(edge, dist);
            }
        }

        LOG.log(Level.FINE, "Created closure graph: {0}", geometricClosure);
        LOG.log(Level.FINE, "Costs: {0}", shortestpathDist);

        return new MetricClosure(geometricClosure, shortestpathDist, rootedForests);
    }

    private UndirectedForest computeMinimumSpanningTree(MetricClosure closure) {
        MinSpanningTreeProblem mstProblem = new MinSpanningTreeProblem(closure.closureGraph, closure.shortestpathDist);
        KruskalAlgorithm mstAlgorithm = new KruskalAlgorithm();
        mstAlgorithm.setProblem(mstProblem);
        mstAlgorithm.run();
        return mstAlgorithm.getSolution();
    }

    private IdentifiableCollection<Edge> createSolutionTree(UndirectedForest mst, MetricClosure closure) {
        ListSequence<Edge> treeEdges = new ListSequence<>();
        for (Edge edge : mst.getEdges()) {
            Node fromOriginal = getProblem().getTerminals().get(edge.start().id());
            Node toOriginal = getProblem().getTerminals().get(edge.end().id());

            Forest forest = closure.rootedForests.get(fromOriginal);
            Path path = forest.getPathToRoot(toOriginal);
            treeEdges.addAll(path.getEdges());
        }

        return treeEdges;
    }

    private static class MetricClosure {

        final Graph closureGraph;
        final IdentifiableIntegerMapping<Edge> shortestpathDist;
        final IdentifiableObjectMapping<Node, Forest> rootedForests;

        public MetricClosure(Graph closureGraph, IdentifiableIntegerMapping<Edge> shortestpathDist, IdentifiableObjectMapping<Node, Forest> rootedForests) {
            this.closureGraph = closureGraph;
            this.shortestpathDist = shortestpathDist;
            this.rootedForests = rootedForests;
        }
    }
}
