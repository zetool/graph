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
package org.zetool.algorithm.shortestpath;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.zetool.container.mapping.IdentifiableConstantMapping;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.graph.DefaultDirectedGraph;
import org.zetool.graph.Edge;
import org.zetool.graph.MutableDirectedGraph;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DijkstraTest {

    @Test
    public void singleNode() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(1, 0);
        IdentifiableIntegerMapping<Edge> costs = new IdentifiableConstantMapping<>(0);
        Dijkstra dijkstra = new Dijkstra(graph, costs, graph.getNode(0));
        dijkstra.run();

        assertThat(dijkstra.getDistance(graph.getNode(0)), is(equalTo(0)));
    }

    @Test
    public void singleEdge() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(2, 1);
        graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        IdentifiableIntegerMapping<Edge> costs = new IdentifiableConstantMapping<>(4);
        Dijkstra dijkstra = new Dijkstra(graph, costs, graph.getNode(0));
        dijkstra.run();

        assertThat(dijkstra.getDistance(graph.getNode(0)), is(equalTo(0)));
        assertThat(dijkstra.getDistance(graph.getNode(1)), is(equalTo(4)));
    }

    @Test
    public void parallelEdges() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(2, 2);
        Edge longEdge = graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        Edge shortEdge = graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        IdentifiableIntegerMapping<Edge> costs = new IdentifiableIntegerMapping<>(2);
        costs.set(shortEdge, 1);
        costs.set(longEdge, 1);
        Dijkstra dijkstra = new Dijkstra(graph, costs, graph.getNode(0));
        dijkstra.run();

        assertThat(dijkstra.getDistance(graph.getNode(0)), is(equalTo(0)));
        assertThat(dijkstra.getDistance(graph.getNode(1)), is(equalTo(1)));
    }
    
    @Test
    public void disconnectedNodes() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(2, 0);
        IdentifiableIntegerMapping<Edge> costs = new IdentifiableIntegerMapping<>(2);
        Dijkstra dijkstra = new Dijkstra(graph, costs, graph.getNode(0));
        dijkstra.run();

        assertThat(dijkstra.getDistance(graph.getNode(0)), is(equalTo(0)));
        assertThat(dijkstra.getDistance(graph.getNode(1)), is(equalTo(Integer.MAX_VALUE)));
    }

    @Test
    public void noReverseSearch() {
        MutableDirectedGraph graph = new DefaultDirectedGraph(2, 1);
        graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        IdentifiableIntegerMapping<Edge> costs = new IdentifiableConstantMapping<>(4);
        Dijkstra dijkstra = new Dijkstra(graph, costs, graph.getNode(1));
        dijkstra.run();

        assertThat(dijkstra.getDistance(graph.getNode(0)), is(equalTo(Integer.MAX_VALUE)));
        assertThat(dijkstra.getDistance(graph.getNode(1)), is(equalTo(0)));
    }

}
