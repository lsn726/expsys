package com.logsys.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * 系统工具类
 * @author ShaonanLi
 */
public class SystemUtils {

	private static final Logger logger=Logger.getLogger(SystemUtils.class);
	
	/**用于计算同一表示符的素数*/
	private static final int PRIME_FACTOR=997;
		
	/**
	 * 获取本机的统一机器标示符，目前支持的平台：
	 * 1、Windows
	 * @return 机器统一标示符字符串
	 */
	public static String getUniqueMachineID() {
		try {
			BigInteger umid=BigInteger.ZERO;						//用于计算唯一值的BigInteger对象
			String os=System.getProperty("os.name");
			List<String> factorlist=new ArrayList<String>();		//用于计算唯一值的因素列表
			if(os!=null&&os.startsWith("Windows")) {	//如果是Windows平台
				factorlist.add(System.getProperty("os.name"));		//第一个因素是OS的hashcode
				factorlist.add(System.getProperty("user.name"));	//第二个值是用户名的hashcode
				//开始获取CPU识别符
				Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" }); 
				process.getOutputStream().close();
				Scanner sc = new Scanner(process.getInputStream());
				sc.next();								//跳过Property名
				while(sc.hasNext())
					factorlist.add(sc.next());
				sc.close();
				//开始获取硬盘标示符
				process=Runtime.getRuntime().exec(new String[] {"wmic","diskdrive","get", "serialnumber"});
				process.getOutputStream().close();
				sc=new Scanner(process.getInputStream());
				sc.next();
				while(sc.hasNext())
					factorlist.add(sc.next());
				sc.close();
				//开始获取主板标示符
				process=Runtime.getRuntime().exec(new String[] {"wmic","BaseBoard","get","serialnumber"});
				process.getOutputStream().close();
				sc=new Scanner(process.getInputStream());
				sc.next();
				while(sc.hasNext())
					factorlist.add(sc.next());
				sc.close();
				//开始获取操作系统序列号
				process=Runtime.getRuntime().exec(new String[] {"wmic","os","get","serialnumber"});
				process.getOutputStream().close();
				sc=new Scanner(process.getInputStream());
				sc.next();
				while(sc.hasNext())
					factorlist.add(sc.next());
				sc.close();
				//开始获取网卡Mac地址号
				process=Runtime.getRuntime().exec(new String[] {"wmic","nicconfig","get","macaddress"});
				process.getOutputStream().close();
				sc=new Scanner(process.getInputStream());
				sc.next();
				while(sc.hasNext())
					factorlist.add(sc.next());
				sc.close();
				//信息获取完毕，开始生成唯一码
				for(String factor:factorlist) {
					umid=umid.multiply(BigInteger.valueOf(PRIME_FACTOR));	//先做乘得出素数
					umid=umid.xor(BigInteger.valueOf(factor.hashCode()));	//XOR新因素的hashcode
					umid=umid.shiftLeft(factor.hashCode()%3);				//向左移动一位
				}
				return umid.toString(16);				//返回16进制码
			} else {
				logger.error("不支持的操作系统平台["+os+"].");
				return null;
			}
		} catch(Throwable ex) {
			logger.error("不能获取唯一的机器ID:",ex);
			return null;
		}
		
	}
	
}
