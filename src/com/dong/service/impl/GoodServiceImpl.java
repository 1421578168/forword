package com.dong.service.impl;

import com.dong.dao.GoodDao;
import com.dong.entry.Good;
import com.dong.service.GoodService;
import com.dong.util.PageBean;

import java.util.List;

public class GoodServiceImpl implements GoodService {
    private GoodDao goodDao = new GoodDao();

    @Override
    public void getPageBean(String gtype, PageBean<Good> pb) {
        List<Good> goodList = goodDao.queryAll(gtype, pb);
        pb.setList(goodList);
        int count = (int) goodDao.queryCount(gtype);
        pb.setTotalCount(count);
    }
    

    @Override
    public List<String> findAllGtype() {
        return goodDao.queryGtype();
    }

    @Override
    public Good findGoodById(Integer id) {
        return goodDao.findGood(id);
    }

}
