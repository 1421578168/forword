package com.dong.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC工具类
 */
public class JdbcUtil {
    //驱动类
//    private static  String driveClass;
//    //数据库服务器地址
//    private static String url;
//    //用户名
//    private static String user;
//    //密码
//    private static String password;

    //构造一个c3p0连接池,传入配置文件中的命名配置的名称
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");

    //在静态代码块中加载驱动
   /* static {
        //读取conn.properties文件
        Properties p = new Properties();
        //加载类路径下的文件，转换为输入流
        InputStream ins = JdbcUtil.class.getResourceAsStream("/conn.properties");
        try {
            //加载输入流
            p.load(ins);
            //读取指定key对应的值
             driveClass = p.getProperty("driver");
            url=p.getProperty("url");
            user=p.getProperty("user");
            password=p.getProperty("password");
            Class.forName(driveClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 从连接池中取出一个连接对象并返回
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
                        conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取一个预编译的SQL命令发送器对象
     *
     * @param conn   连接对象
     * @param sql    预编译的SQL语句
     * @param params 赋给SQL语句上每个?的值，由于?的个数不确定，所以传入可变参数
     * @return
     */
    public static PreparedStatement getPreparedStatement(Connection conn, String sql, Object... params) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            //给SQL语句中的？赋值
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    /**
     * 通用的执行增删改的方法
     *
     * @param sql    DML语句(insert,update,delete)
     * @param params 赋给每个输入参数?的值
     * @return 数据库受影响的行数
     */
    public static boolean executeUpdate(String sql, Object... params) {
        boolean result = false;
        //得到連接對象
        Connection conn = JdbcUtil.getConnection();
        //創建预编译的SQL命令发送器
        PreparedStatement stmt = JdbcUtil.getPreparedStatement(conn, sql, params);
        try {
            //执行SQL
            conn.setAutoCommit(false);
            int i = stmt.executeUpdate();
            if (i == 1){
                result = true;
            }
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            //关闭资源
            JdbcUtil.closeAll(stmt, conn);
        }
        return result;
    }

    /**
     * 通用的查询的方法,返回多行多列结果集
     *
     * @param sql    针对返回多行多列的查询语句
     * @param params 赋给每个输入参数?的值
     * @return 返回多行多列结果集, Map中封装的是每一行所有列的数据
     */
    public static List<Map<String, Object>> queryForRows(String sql, Object... params) {
        List<Map<String, Object>> list = new ArrayList<>();
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement stmt = JdbcUtil.getPreparedStatement(conn, sql, params);
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
            //获取包含了结果集中所有列信息的对象
            ResultSetMetaData md = rs.getMetaData();
            //获取结果集中的列的数量
            int count = md.getColumnCount();

            //保存列名和值的Map,key是列名，value是列的值
            Map<String, Object> map = null;
            //遍历结果集
            while (rs.next()) {
                //实例化
                map = new HashMap<>();
                //遍历当前行的每一列
                for (int i = 1; i <= count; i++) {
                    //获取当前列的名称
                    String columnName = md.getColumnLabel(i);
                    //获取当前列的值
                    Object columnValue = rs.getObject(i);
                    //列信息存到 Map中
                    map.put(columnName, columnValue);
                }
                //把每一行的Map添加到集合中
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JdbcUtil.closeAll(rs, stmt, conn);
        }
        return list;
    }

    /**
     * 通用的查询的方法,返回单行多列结果集
     *
     * @param sql    针对返回单行多列的查询语句
     * @param params 赋给每个输入参数?的值
     * @return 返回单行多列结果集, Map中封装的是每一行所有列的数据
     */
    public static Map<String, Object> queryForRow(String sql, Object... params) {
        //保存列名和值的Map,key是列名，value是列的值
        Map<String, Object> map = null;
        //得到連接對象
        Connection conn = JdbcUtil.getConnection();
        //創建预编译的SQL命令发送器
        PreparedStatement stmt = JdbcUtil.getPreparedStatement(conn, sql, params);
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
            //获取包含了结果集中所有列信息的对象
            ResultSetMetaData md = rs.getMetaData();
            //获取结果集中的列的数量
            int count = md.getColumnCount();

            //遍历结果集
            if (rs.next()) {
                //实例化
                map = new HashMap<>();
                //遍历当前行的每一列
                for (int i = 1; i <= count; i++) {

                    //获取当前列的名称
                    String columnName = md.getColumnLabel(i);
                    //获取当前列的值
                    Object columnValue = rs.getObject(i);
                    //列信息存到 Map中
                    map.put(columnName, columnValue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JdbcUtil.closeAll(rs, stmt, conn);
        }
        return map;

    }

    /**
     * 通用的查询的方法,返回单列多行结果集
     *
     * @param cls    列的值的类型映射的Class对象
     * @param sql    针对单列多行的查询语句
     * @param params 赋给每个输入参数?的值
     * @param <T>    列的值的类型
     * @return 返回单列多行结果集
     */
    public static <T> List<T> queryForRows(Class<T> cls, String sql, Object... params) {
        List<T> list = new ArrayList<>();
        //得到連接對象
        Connection conn = JdbcUtil.getConnection();
        //創建预编译的SQL命令发送器
        PreparedStatement stmt = JdbcUtil.getPreparedStatement(conn, sql, params);
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
            while (rs.next()) {
                //获取当前行第一列的值
                Object obj = rs.getObject(1);
                //把obj转换成T类型
                T t = (T) obj;
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JdbcUtil.closeAll(rs, stmt, conn);
        }
        return list;
    }

    /**
     * 通用的查询的方法,返回单列单行结果集
     *
     * @param cls    列的值的类型映射的Class对象
     * @param sql    针对单列单行的查询语句
     * @param params 赋给每个输入参数?的值
     * @param <T>    列的值的类型
     * @return 返回单列单行结果集
     */
    public static <T> T queryForRow(Class<T> cls, String sql, Object... params) {
        T t = null;
        //得到連接對象
        Connection conn = JdbcUtil.getConnection();
        //創建预编译的SQL命令发送器
        PreparedStatement stmt = JdbcUtil.getPreparedStatement(conn, sql, params);
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
            if (rs.next()) {
                //获取当前行第一列的值
                Object obj = rs.getObject(1);
                //对于整型的结果，会自动映射成java.lang.Long类型
                //System.out.println(obj.getClass().getName());
                //把obj转换成T类型
                t = (T) obj;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JdbcUtil.closeAll(rs, stmt, conn);
        }
        return t;
    }

    /**
     * 关闭资源
     *
     * @param acls 要关闭的所有资源,它们都实现了AutoCloseable接口
     */
    public static void closeAll(AutoCloseable... acls) {
        for (AutoCloseable closeable : acls) {
            if (closeable != null) {
                try {
                    //如果传来的是Connection对象，那么close()是将连接归还到连接池中，并不是断开和数据库服务器的连接.
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}