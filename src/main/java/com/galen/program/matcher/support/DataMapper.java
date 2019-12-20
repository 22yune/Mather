package com.galen.program.matcher.support;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by baogen.zhang on 2019/12/20
 *
 * @author baogen.zhang
 * @date 2019/12/20
 */

public interface DataMapper {

    @Select("select * from #{table}")
    List<Map<String,Object>> select(@Param("table") String table);
}
