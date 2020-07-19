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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.zetool.test.ConstructorMatcher.hasNonNullStandardConstructor;
import static org.zetool.test.GetterSetterAssert.assertGetterSetter;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.zetool.container.mapping.IdentifiableIntegerMapping;
import org.zetool.graph.Edge;
import org.zetool.graph.Graph;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class MinSpanningTreeProblemTest {

    @Test
    public void testInstantiationWithoutSink() {
        Graph graph = mock(Graph.class);
        IdentifiableIntegerMapping<Edge> distances = mock(IdentifiableIntegerMapping.class);

        MinSpanningTreeProblem fixture = new MinSpanningTreeProblem(graph, distances);
        assertGetterSetter(fixture)
                .hasSameParameter(MinSpanningTreeProblem::getGraph, graph)
                .hasSameParameter(MinSpanningTreeProblem::getDistances, distances);
    }

    @Test
    public void testNullConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException {
        Class<Graph> graph = Graph.class;
        Class<IdentifiableIntegerMapping> distances = IdentifiableIntegerMapping.class;

        assertThat(MinSpanningTreeProblem.class, hasNonNullStandardConstructor((i, o) -> mock(o), graph, distances));
    }

}
