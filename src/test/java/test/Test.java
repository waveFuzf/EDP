package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.zust.EDP.util.Tools;

public class Test {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		String a="2018-2-12 12:20";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println(simpleDateFormat.parse(a));
		
	}

}
