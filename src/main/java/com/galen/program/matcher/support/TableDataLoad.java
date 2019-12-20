package com.galen.program.matcher.support;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baogen.zhang on 2019/12/20
 *
 * @author baogen.zhang
 * @date 2019/12/20
 */
public class TableDataLoad {
    private DataMapper dataMapper;


    private TableDataLoad(ApplicationContext applicationContext) {
        SqlSession sqlSession = applicationContext.getBean(SqlSession.class);
        if(sqlSession == null){
            throw new RuntimeException("can not find SqlSession Bean in ApplicationContext");
        }
        addMapper(sqlSession,DataMapper.class);
        dataMapper = sqlSession.getMapper(DataMapper.class);
    }

    private static void addMapper(SqlSession sqlSession, Class... mappers){
        if(sqlSession == null){
            throw new RuntimeException("SqlSession can not be null!");
        }
        for(Class mapper : mappers){
            sqlSession.getConfiguration().addMapper(mapper);
        }
    }
    private static void addMapper(SqlSession sqlSession, String... mappers){
        if(sqlSession == null){
            throw new RuntimeException("SqlSession can not be null!");
        }
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        for(String mapper : mappers){
            Resource mapperLocation = resourcePatternResolver.getResource("classpath:" + mapper);
            try {
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(),
                        sqlSession.getConfiguration(), mapperLocation.toString(), sqlSession.getConfiguration().getSqlFragments());
                xmlMapperBuilder.parse();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
            }
        }
    }

    public static TableDataLoad newInstance(ApplicationContext applicationContext){
        return new TableDataLoad(applicationContext);
    }

    public List<Map<String,Object>> getDatas(String tableName){
        return dataMapper.select(tableName);
    }

    @Deprecated
    public static List<Map<String,Object>> getDatas(String url,String userName,String password){
        String tablesSql = "select table_name from user_tables";
        String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement preparedStatement = conn.prepareStatement(tablesSql);
             ResultSet results = preparedStatement.executeQuery()) {
            List<Map<String,Object>> datas = new ArrayList<>();
            while (results.next()) {
                Map<String,Object> data = new HashMap<>();
                ResultSetMetaData metaData =results.getMetaData();
                for(int i = 0; i < metaData.getColumnCount(); i++){
                    results.getObject(i);
                    data.put(metaData.getColumnName(i),results.getObject(i));
                }

            }
            return datas;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
