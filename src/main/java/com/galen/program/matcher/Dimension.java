package com.galen.program.matcher;

/**
 * Created by baogen.zhang on 2019/9/20
 *
 * @author baogen.zhang
 * @date 2019/9/20
 */
public abstract class Dimension<T> implements Matcher.Dimension<T>{

    private String name;
    private byte positive;
    private byte negative;

    public Dimension (String name,byte positive,byte negative){
        if(positive <0  || negative < 0 || positive > 63 || negative > 63){
            throw new RuntimeException("权重值只能是0-63之间！");//TODO
        }
        this.name = name;
        this.positive = positive;
        this.negative = negative;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public byte positive() {
        return positive;
    }

    @Override
    public byte negative() {
        return negative;
    }


    @Override
    public abstract boolean match(T condition, Object value) ;

    @Override
    public Condition<T> condition(T condition) {
        return new Condition(this,condition);
    }
}
