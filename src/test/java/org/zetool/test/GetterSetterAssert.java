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
package org.zetool.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <T> the asserted type
 */
public class GetterSetterAssert<T> {

    private final T object;

    public GetterSetterAssert(T object) {
        this.object = object;
    }

    public <R> GetterSetterAssert<T> hasEqualParameter(Function<T, R> dataAccess, R expected) {
        assertThat(dataAccess.apply(object), is(equalTo(expected)));
        return this;
    }

    public <R> GetterSetterAssert<T> hasSameParameter(Function<T, R> dataAccess, R expected) {
        assertThat(dataAccess.apply(object), is(sameInstance(expected)));
        return this;
    }

    public <P> GetterSetterAssert<T> withParameter(BiConsumer<T, P> dataAccess, P value) {
        dataAccess.accept(object, value);
        return this;
    }

    public static <T> GetterSetterAssert<T> assertGetterSetter(T object) {
        return new GetterSetterAssert<>(object);
    }
}
