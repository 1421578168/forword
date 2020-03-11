package com.dong.service;

import com.dong.entry.Good;
import com.dong.util.PageBean;

import java.util.List;

public interface GoodService {
    void getPageBean(String gtype, PageBean<Good> pb);
    List<String > findAllGtype();
    Good findGoodById(Integer id);
}
