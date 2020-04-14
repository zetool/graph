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
package org.zetool.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DefaultDirectedGraphTest {

    @Test
    public void edgesAddedInIncreasingOrder() {
        DefaultDirectedGraph fixture = new DefaultDirectedGraph(3, 3);
        Edge edge0 = fixture.createAndSetEdge(fixture.getNode(0), fixture.getNode(1));
        Edge edge1 = fixture.createAndSetEdge(fixture.getNode(0), fixture.getNode(2));
        Edge edge2 = fixture.createAndSetEdge(fixture.getNode(1), fixture.getNode(2));

        assertThat(edge0.id(), is(equalTo(0)));
        assertThat(edge1.id(), is(equalTo(1)));
        assertThat(edge2.id(), is(equalTo(2)));
    }

    @Test
    public void testNextEdgeID() {
        DefaultDirectedGraph fixture = new DefaultDirectedGraph(3, 3);
        Edge edgeFirst = fixture.createAndSetEdge(fixture.getNode(0), fixture.getNode(1));

        fixture.setNextEdgeIdCandidate(2);
        Edge edgeManuallySet = fixture.createAndSetEdge(fixture.getNode(1), fixture.getNode(2));

        Edge edgeStartAgain = fixture.createAndSetEdge(fixture.getNode(0), fixture.getNode(2));
        assertThat(edgeFirst.id(), is(equalTo(0)));
        assertThat(edgeManuallySet.id(), is(equalTo(2)));
        assertThat(edgeStartAgain.id(), is(equalTo(1)));
    }
}
