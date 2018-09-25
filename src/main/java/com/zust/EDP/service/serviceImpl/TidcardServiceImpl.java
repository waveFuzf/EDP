package com.zust.EDP.service.serviceImpl;

import com.zust.EDP.dao.TidcardDao;
import com.zust.EDP.entity.Tidcard;
import com.zust.EDP.service.TidcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TidcardServiceImpl implements TidcardService {
    @Autowired
    private TidcardDao tidcardDao;

    @Override
    public void saveTicard(Tidcard tidcard) {
        tidcardDao.saveTidcard(tidcard);

    }

    @Override
    public List<Tidcard> selectByNum(String cardnum) {
            List<Tidcard> tidcard =tidcardDao.selectByNum(cardnum);

        return tidcard;
    }
}
