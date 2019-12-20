package com.galen.program.matcher;

/**
 * Created by baogen.zhang on 2019/9/20
 *
 * @author baogen.zhang
 * @date 2019/9/20
 */

public interface Value<T extends Value<T>> {

    boolean match(Object object);

    default Matcher.Dimension<T> dimension(String name, byte positive, byte negative) {
        return new Dimension<T>(name,positive,negative) {
            @Override
            public boolean match(T condition, Object value) {
                return condition.match(value);
            }
        };
    }

    default Matcher.Condition<T> condition(String name, byte positive, byte negative) {
        return new Dimension<T>(name,positive,negative) {
            @Override
            public boolean match(T condition, Object value) {
                return condition.match(value);
            }
        }.condition((T) this);
    }
}