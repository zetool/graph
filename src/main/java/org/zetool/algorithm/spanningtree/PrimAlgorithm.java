/* zet evacuation tool copyright (c) 2007-20 zet evacuation team
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
package org.zetool.algorithm.spanningtree;

import org.zetool.common.algorithm.AbstractAlgorithm;
import org.zetool.graph.Edge;
import org.zetool.container.collection.IdentifiableCollection;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.container.mapping.IdentifiableObjectMapping;
import org.zetool.container.collection.ListSequence;
import org.zetool.container.priority.MinHeap;
import org.zetool.graph.Node;

import java.util.Random;

import org.zetool.graph.Graph;

/**
 *
 * @author Marlen Schwengfelder
 */
public class PrimAlgorithm extends AbstractAlgorithm<MinSpanningTreeProblem, UndirectedForest> {

    Graph OriginNetwork;
    Node startNode;
    IdentifiableCollection<Node> solNodes = new ListSequence<>();
    IdentifiableCollection<Edge> solEdges = new ListSequence<>();
    IdentifiableIntegerMapping<Node> distances;
    IdentifiableObjectMapping<Node, Edge> heapedges;
    int NumEdge = 0;
    int overalldist = 0;

    @Override
    protected UndirectedForest runAlgorithm(MinSpanningTreeProblem problem) {

        try {
            OriginNetwork = problem.getGraph();
            int numNodes = OriginNetwork.nodeCount();

            IdentifiableIntegerMapping<Edge> TransitForEdge = problem.getDistances();

            //gives a random start node
            Random r = new Random();
            long seed = r.nextLong();
            r.setSeed(seed);
            int num = 0 + Math.abs(r.nextInt()) % numNodes;

            startNode = OriginNetwork.getNode(num);
            distances = new IdentifiableIntegerMapping<Node>(OriginNetwork.nodeCount());
            heapedges = new IdentifiableObjectMapping<Node, Edge>(OriginNetwork.edgeCount());
            MinHeap<Node, Integer> queue = new MinHeap<Node, Integer>(OriginNetwork.nodeCount());
            IdentifiableCollection<Edge> incidentEdges;
            for (Node node : OriginNetwork.nodes()) {
                distances.add(node, Integer.MAX_VALUE);
                heapedges.set(node, null);
            }

            distances.set(startNode, 0);

            queue.insert(startNode, 0);

            while (!queue.isEmpty()) {
                MinHeap<Node, Integer>.Element min = queue.extractMin();
                Node v = min.getObject();
                solNodes.add(v);
                distances.set(v, Integer.MIN_VALUE);

                // If we are not seeing the start node, we have a cheapest edge to the node
                if (v != startNode) {
                    solEdges.add(heapedges.get(v));
                }
                incidentEdges = OriginNetwork.incidentEdges(v);
                for (Edge edge : incidentEdges) {
                    Node w = edge.opposite(v);
                    if (distances.get(w) == Integer.MAX_VALUE) { // if w is a new node
                        distances.set(w, TransitForEdge.get(edge));
                        heapedges.set(w, edge);
                        queue.insert(w, distances.get(w));
                    } else {
                        if (TransitForEdge.get(edge) < distances.get(w)) {
                            distances.set(w, TransitForEdge.get(edge));
                            queue.decreasePriority(w, TransitForEdge.get(edge));
                            heapedges.set(w, edge);
                        }
                    }

                }
            }

        } catch (Exception e) {
        }
        return new UndirectedForest(solEdges);

    }

}
