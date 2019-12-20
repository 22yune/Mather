package com.galen.program.matcher;

import java.util.function.BiFunction;

/**
 * Created by baogen.zhang on 2019/9/20
 *
 * @author baogen.zhang
 * @date 2019/9/20
 */
public class Matcher {

    public static final BiFunction<Object,Object,Boolean> OBJECT_EQUAL_MATCH = (t,u) -> {
        if(t == null){//TODO
            return true;
        }else if(u == null){
            return false;
        }else{
            return t.equals(u);
        }
    };
    public static final BiFunction<String,Object,Boolean> STRING_STRICT_MATCH = (t,u) -> {
        if(t == null){//TODO
            return true;
        }else if(u == null){
            return false;
        }else{
            if(u instanceof String){
                String c = (String)u;
                if(t.startsWith("!!")){
                    return c.matches(t.substring(1));
                }else if(c.startsWith("!")){
                    return !c.matches(t.substring(1));
                }else {
                    return  c.matches(t);
                }
            }else {
                return false;
            }
        }
    };



    public static <T> com.demo.matcher.Dimension<T> newDimension(String name, byte positive, byte negative, BiFunction<T,Object,Boolean> match){
        return new com.demo.matcher.Dimension<T>(name,positive,negative) {
            @Override
            public boolean match(T condition, Object value) {
                return match.apply(condition, value);
            }
        };
    }

    public interface Rule {

        long apply(Hold hold);

    //    long apply(Object origin,)

        Rule  and(Rule rule);

        Rule  or(Rule rule);

        Rule  not();

    }

    public interface Condition<T> {

        Dimension dimension();

        T value();

        Rule rule();
    }
    public interface Dimension<T> {

        String name();

        byte positive();
        byte negative();

        boolean match(T condition, Object value);


        Condition<T> condition(T condition);
    }



}
