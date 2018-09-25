package com.zust.EDP.service;

import com.zust.EDP.entity.Tidcard;

import java.util.List;

public interface TidcardService {

    public void saveTicard(Tidcard tidcard);

    public List<Tidcard> selectByNum(String cardnum);


}
