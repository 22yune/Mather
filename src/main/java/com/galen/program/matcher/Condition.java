package com.galen.program.matcher;

/**
 * Created by baogen.zhang on 2019/9/20
 *
 * @author baogen.zhang
 * @date 2019/9/20
 */

public class Condition<T> extends Rule implements Matcher.Condition<T>{
    private Dimension<T> dimension;
    private T value;

    public Condition(Dimension<T> dimension,T value){
        super();
        initSimple(this,false);
        this.dimension =dimension;
        this.value = value;
    }

    @Override
    public Dimension<T> dimension() {
        return dimension;
    }

    @Override
    public T value() {
        return value;
    }

    @Override
    public Rule rule() {
        return this;
    }
}
