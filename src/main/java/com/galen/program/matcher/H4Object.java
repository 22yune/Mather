package com.galen.program.matcher;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Created by baogen.zhang on 2019/9/20
 *
 * @author baogen.zhang
 * @date 2019/9/20
 */
public class H4Object implements Hold{

    private Object target;

    public H4Object(Object target){
        this.target = target;
    }

    @Override
    public Object value(Matcher.Dimension<?> dimension) {
        try {
            return BeanUtils.getProperty(target,dimension.name());
        } catch (Exception e) {
            //TODO
            return null;
        }
    }
}
