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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.assertThat;
import static org.zetool.algorithm.spanningtree.TestInstances.createComplexSingleComponentInstance;
import static org.zetool.algorithm.spanningtree.TestInstances.createComplexTwoComponentInstance;

import org.junit.Test;
import org.zetool.container.mapping.IdentifiableConstantMapping;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.graph.DefaultGraph;
import org.zetool.graph.Edge;
import org.zetool.graph.MutableGraph;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class KruskalAlgorithmTest {

    @Test
    public void singleNode() {
        MutableGraph graph = new DefaultGraph(1, 0);
        IdentifiableIntegerMapping<Edge> weights = new IdentifiableConstantMapping<>(0);

        MinSpanningTreeProblem mstProblem = new MinSpanningTreeProblem(graph, weights);
        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.setProblem(mstProblem);
        kruskal.run();

        UndirectedForest solution = kruskal.getSolution();
        assertThat(solution.getEdges(), is(emptyIterable()));
    }

    @Test
    public void singleEdge() {
        MutableGraph graph = new DefaultGraph(2, 1);
        graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        IdentifiableIntegerMapping<Edge> weights = new IdentifiableConstantMapping<>(4);

        MinSpanningTreeProblem mstProblem = new MinSpanningTreeProblem(graph, weights);
        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.setProblem(mstProblem);
        kruskal.run();

        UndirectedForest solution = kruskal.getSolution();

        assertThat(solution.getEdges(), is(iterableWithSize(1)));
    }

    @Test
    public void parallelEdges() {
        MutableGraph graph = new DefaultGraph(2, 2);
        Edge expensiveEdge = graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        Edge cheapEdge = graph.createAndSetEdge(graph.getNode(0), graph.getNode(1));
        IdentifiableIntegerMapping<Edge> weights = new IdentifiableIntegerMapping<>(2);
        weights.set(cheapEdge, 1);
        weights.set(expensiveEdge, 2);

        MinSpanningTreeProblem mstProblem = new MinSpanningTreeProblem(graph, weights);
        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.setProblem(mstProblem);
        kruskal.run();

        UndirectedForest solution = kruskal.getSolution();

        assertThat(solution.getEdges(), is(iterableWithSize(1)));
        assertThat(solution.getEdges(), contains(cheapEdge));
    }

    @Test
    public void complexSingleComponentInstance() {
        MinSpanningTreeProblem mstProblem = createComplexSingleComponentInstance();
        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.setProblem(mstProblem);
        kruskal.runAlgorithm();

        UndirectedForest solution = kruskal.getSolution();

        assertThat(solution.getEdges(), is(iterableWithSize(6)));
        int cost = 0;
        for (Edge edge : solution.getEdges()) {
            cost += mstProblem.getDistances().get(edge);
        }
        assertThat(cost, is(equalTo(39)));
    }

    @Test
    public void complexTwoComponentInstance() {
        MinSpanningTreeProblem mstProblem = createComplexTwoComponentInstance();
        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.setProblem(mstProblem);
        kruskal.runAlgorithm();

        UndirectedForest solution = kruskal.getSolution();

        int sizeComponentA = 9;
        int sizecomponentB = 4;
        assertThat(solution.getEdges(), is(iterableWithSize(sizeComponentA + sizecomponentB)));
        int cost = 0;
        for (Edge edge : solution.getEdges()) {
            cost += mstProblem.getDistances().get(edge);
        }
        int costComponentA = 38;
        int costComponentB = 12;
        assertThat(cost, is(equalTo(costComponentA + costComponentB)));
    }

}
