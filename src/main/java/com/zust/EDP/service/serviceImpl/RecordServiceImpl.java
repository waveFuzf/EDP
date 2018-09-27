package com.zust.EDP.service.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zust.EDP.dao.RecordDao;
import com.zust.EDP.dto.Unread;
import com.zust.EDP.entity.Trecord;
import com.zust.EDP.service.RecordService;

@Service
@Transactional
public class RecordServiceImpl implements RecordService {
	@Autowired
	private RecordDao recordDao;

	@Override
	public List<Unread> Trecord_Uread(Integer UserId) {
		// TODO Auto-generated method stub
		List<Unread> rList = new ArrayList<Unread>();
		List<Trecord> list1 = recordDao.find_by_userId(UserId);
		for (int i = 0; i < list1.size(); i++) {
			Unread read = new Unread();
			read.setFromuserId(list1.get(i).getUser_sender_id().getUserId());
			read.setFromuserimage(list1.get(i).getUser_sender_id().getImage());
			read.setFromusername(list1.get(i).getUser_sender_id().getName());
			List<String> record = new ArrayList<String>();
			List<Trecord> list2 = recordDao.findrecord(UserId, list1.get(i).getUser_sender_id().getUserId());
			for (int j = 0; j < list2.size(); j++) {
				record.add(list2.get(j).getRecord());
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			read.setTime(simpleDateFormat.format(list2.get(0).getRecordDate()));
			read.setRecord(record);
			rList.add(read);
		}
		recordDao.chang_record_state(list1);
		return rList;
	}

}
