package com.galen.program.matcher;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by baogen.zhang on 2019/12/20
 *
 * @author baogen.zhang
 * @date 2019/12/20
 */
public class RuleLoad {


    public static  Map<Map<String,Object>,Rule> load(RuleDefine ruleDefine){
        
        return null;
    }

    public static Matcher.Rule toRule(Object t, RuleDefine ruleDefine){
        Matcher.Rule rule = null;
        for(Matcher.Dimension dimension: ruleDefine.dimensions()){
            try {
                Object v = null;
                if(t instanceof Map){
                    v = ((Map)t).get(dimension.name());
                }else {
                    PropertyUtils.getProperty(t,dimension.name());
                }
                if( rule == null){
                    rule = dimension.condition(v).rule();
                }else {
                    rule.and(dimension.condition(v).rule());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return rule;
    }
}
