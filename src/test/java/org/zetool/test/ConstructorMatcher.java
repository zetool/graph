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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Reflective matcher checking that constructors don't accept {@code null} values.
 *
 * @author Jan-Philipp Kappmeier
 */
public class ConstructorMatcher<T> extends TypeSafeMatcher<Class<T>> {

    /**
     * The constructor types.
     */
    private final Class<?>[] types;

    /**
     * Produces the argument values for the .
     */
    private final BiFunction<Integer, Class<?>, Object> valueFactory;

    /**
     * Errors occured during matching for later description.
     */
    private final List<String> errors = new LinkedList<>();

    /**
     * Instantiates the {@code ConstructorMatcher} for a constructor accepting the given types.
     *
     * @param types the types the constructor should accept
     * @param valueFactory produces the argument values for the constructor
     */
    public ConstructorMatcher(BiFunction<Integer, Class<?>, Object> valueFactory, Class<?>[] types) {
        this.types = types;
        this.valueFactory = valueFactory;
    }

    @Override
    protected boolean matchesSafely(Class<T> fixture) {
        try {
            Constructor<T> cons = fixture.getConstructor(types);
            return nullValuesThrow(cons) && nonNullValuesPass(cons);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new AssertionError(ex);
        }
    }

    @Override
    public void describeTo(Description d) {
        d.appendText("constructor accepting no null values, but working withot issues for non-null parameter values");
    }

    @Override
    protected void describeMismatchSafely(Class<T> item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
        mismatchDescription.appendText("\n").appendText(String.join("\n", errors));
    }

    private boolean nullValuesThrow(Constructor<T> constructor) throws InvocationTargetException,
            InstantiationException, IllegalAccessException {
        for (int i = 0; i < types.length; ++i) {
            if (acceptsNullValue(constructor, i)) {
                errors.add("Parmeter " + i + " of type " + types[i] + " accepted null value");
            }
        }
        return errors.isEmpty();
    }

    private boolean acceptsNullValue(Constructor<T> constructor, int parameter) throws InstantiationException,
            IllegalAccessException, InvocationTargetException {
        Object[] values = fillParameterValues(parameter);
        try {
            constructor.newInstance(values);
        } catch (InvocationTargetException ex) {
            if (!(ex.getCause() instanceof NullPointerException)) {
                throw ex;
            }
            return false;
        }
        return true;
    }

    private boolean nonNullValuesPass(Constructor<T> constructor) throws InstantiationException,
            IllegalAccessException, InvocationTargetException {
        Object[] values = fillParameterValues();
        constructor.newInstance(values);
        return true;
    }

    /**
     * Fills an array with parameter argument values with mocked values.
     *
     * @return mocked parameter arguments for the constructor
     */
    private Object[] fillParameterValues() {
        return fillParameterValues(-1);
    }

    /**
     * Fills mock values for the constructor types, except for the asserted index. If the index is set to a value
     * outside the parameter range all values are filled with mocks.
     *
     * @param parameter the index of the parameter that is set to {@code null}
     * @return values for the constructor of the given types
     */
    private Object[] fillParameterValues(int parameter) {
        Object[] values = new Object[types.length];
        for (int j = 0; j < types.length; ++j) {
            if (j != parameter) {
                values[j] = valueFactory.apply(j, types[j]);
            } else {
                values[j] = null;
            }
        }
        return values;
    }

    public static <T> Matcher<Class<T>> hasNonNullStandardConstructor(BiFunction<Integer, Class<?>, Object> values,
            Class<?>... types) {
        return new ConstructorMatcher<>(values, types);
    }

}
