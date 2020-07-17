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
package org.zetool.algorithm.shortestpath;

import static org.mockito.Mockito.mock;
import static org.zetool.test.GetterSetterAssert.assertGetterSetter;

import java.util.Optional;

import org.junit.Test;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.graph.Graph;
import org.zetool.graph.Node;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class IntegralSingleSourceShortestPathProblemTest {

    @Test
    public void testInstantiationWithoutSink() {
        Graph graph = mock(Graph.class);
        Node source = mock(Node.class);
        IdentifiableIntegerMapping costs = mock(IdentifiableIntegerMapping.class);

        IntegralSingleSourceShortestPathProblem fixture = new IntegralSingleSourceShortestPathProblem(graph, costs, source);
        assertGetterSetter(fixture)
                .hasSameParameter(IntegralSingleSourceShortestPathProblem::getGraph, graph)
                .hasSameParameter(IntegralSingleSourceShortestPathProblem::getCosts, costs)
                .hasSameParameter(IntegralSingleSourceShortestPathProblem::getSource, source)
                .hasSameParameter(IntegralSingleSourceShortestPathProblem::getTarget, Optional.empty());
    }

    @Test
    public void testInstantiationWithSink() {
        Graph graph = mock(Graph.class);
        Node source = mock(Node.class, "source");
        Node sink = mock(Node.class, "sink");
        IdentifiableIntegerMapping costs = mock(IdentifiableIntegerMapping.class);

        IntegralSingleSourceShortestPathProblem fixture = new IntegralSingleSourceShortestPathProblem(graph, costs, source, sink);
        assertGetterSetter(fixture)
                .hasSameParameter(IntegralSingleSourceShortestPathProblem::getGraph, graph)
                .hasSameParameter(IntegralSingleSourceShortestPathProblem::getCosts, costs)
                .hasSameParameter(IntegralSingleSourceShortestPathProblem::getSource, source)
                .hasEqualParameter(IntegralSingleSourceShortestPathProblem::getTarget, Optional.of(sink));
    }
}
