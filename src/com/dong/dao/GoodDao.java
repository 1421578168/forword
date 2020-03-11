package com.dong.dao;

import com.dong.entry.Good;
import com.dong.util.JdbcUtil;
import com.dong.util.PageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoodDao {
    public boolean insertGood(Good good){
        String sql = "insert into good(gname,gtype,price,pic) values(?,?,?,?)";
        boolean result = JdbcUtil.executeUpdate(sql, good.getGname(), good.getGtype(), good.getPrice(), good.getPic());
        return result;
    }
    
    public List<Good> queryAll(String gtype, PageBean<Good> pb){
        String sql = "select * from good where gtype=? order by id limit ?,?";

        List<Good> goodList = new ArrayList<>();
        List<Map<String, Object>> mapList = JdbcUtil.queryForRows(sql,gtype,(pb.getPageNum() - 1) * pb.getPageSize(), pb.getPageSize());
        for(Map<String, Object> map:mapList){
            Good good1 = new Good();
            good1.setId(Integer.valueOf(map.get("id").toString()));
            good1.setGname(map.get("gname").toString());
            good1.setGtype(map.get("gtype").toString());
            good1.setPrice(Double.valueOf(map.get("price").toString()));
            goodList.add(good1);
        }
        return goodList;
    }
    public List<String > queryGtype(){
        String sql = "select gtype from good ";
        
        List<String> maps = JdbcUtil.queryForRows(String.class,sql);
        
        return maps;
    }

    public long queryCount(String gtype) {
        long count=0;
        String sql="select count(*) from good where gtype=?";
        count= JdbcUtil.queryForRow(Long.class,sql,gtype);
        return count;
    }
    
    public boolean updateGood(Good good){
        String sql = "update good set gname=?,gtype=?,price=? where id=?";
        boolean result = JdbcUtil.executeUpdate(sql, good.getGname(), good.getGtype(), good.getPrice(), good.getId());
        return result;
    }
    
    public boolean remove(Integer id){
        String sql = "delete from good where id=?";
        boolean b = JdbcUtil.executeUpdate(sql, id);
        return b;
    }
}
