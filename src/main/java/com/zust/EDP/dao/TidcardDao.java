package com.zust.EDP.dao;

import com.zust.EDP.entity.Tidcard;

import java.util.List;

public interface TidcardDao {
    public void saveTidcard(Tidcard tidcard);
    public List<Tidcard> selectByNum(String cardnum);
}
