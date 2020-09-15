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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zetool.container.collection.ListSequence;
import org.zetool.container.mapping.IdentifiableConstantMapping;
import org.zetool.graph.DefaultGraph;
import org.zetool.graph.MutableGraph;
import org.zetool.graph.Node;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@RunWith(Parameterized.class)
public class SteinerTreeSpanningTreeApproximationAlgorithmTest {

    private final int[] nodes;
    private final int expected;

    public SteinerTreeSpanningTreeApproximationAlgorithmTest(int[] nodes, int expected) {
        this.nodes = nodes;
        this.expected = expected;
    }

    @Test
    public void wheel() {
        MinSteinerTreeProblem instance = SteinerTreeTestInstances.WHEEL_3;
        SteinerTreeSpanningTreeApproximationAlgorithm approximationAlgorithm
                = new SteinerTreeSpanningTreeApproximationAlgorithm();
        approximationAlgorithm.setProblem(instance);
        approximationAlgorithm.runAlgorithm();
        SteinerTree steinerTree = approximationAlgorithm.getSolution();

        assertThat(steinerTree.getEdges(), is(iterableWithSize(2)));
        assertThat(steinerTree.getCost(), is(equalTo(20L)));
    }

    @Parameterized.Parameters
    public static Collection pathNodesAndDistances() {
        return Arrays.asList(new Object[][]{
            {new int[]{0, 1, 2, 3}, 1},
            {new int[]{1, 0, 2, 3}, 1},
            {new int[]{1, 2, 0, 3}, 3},
            {new int[]{0, 2, 1, 3}, 2}
        });
    }

    @Test
    public void singlePath() {
        MutableGraph graph = new DefaultGraph(4, 3);

        Node steiner1 = graph.getNode(nodes[0]);
        Node steiner2 = graph.getNode(nodes[1]);
        Node terminal1 = graph.getNode(nodes[2]);
        Node terminal2 = graph.getNode(nodes[3]);

        IntStream.of(0, 1, 2).forEach(i -> graph.createAndSetEdge(graph.getNode(i), graph.getNode(i + 1)));

        MinSteinerTreeProblem instance = new MinSteinerTreeProblem(graph, new IdentifiableConstantMapping<>(3),
                new ListSequence<>(List.of(terminal1, terminal2)));
        SteinerTreeSpanningTreeApproximationAlgorithm approximationAlgorithm
                = new SteinerTreeSpanningTreeApproximationAlgorithm();
        approximationAlgorithm.setProblem(instance);
        approximationAlgorithm.runAlgorithm();

        SteinerTree steinerTree = approximationAlgorithm.getSolution();
        assertThat(steinerTree.getEdges(), is(iterableWithSize(expected)));
        assertThat(steinerTree.getCost(), is(equalTo(expected * 3L)));
    }

}
