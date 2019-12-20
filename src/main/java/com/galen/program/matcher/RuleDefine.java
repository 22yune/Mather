package com.galen.program.matcher;

import java.util.List;

/**
 * Created by baogen.zhang on 2019/12/20
 *
 * @author baogen.zhang
 * @date 2019/12/20
 */
public interface RuleDefine {

    String tableName();

    List<Dimension> dimensions();

}
