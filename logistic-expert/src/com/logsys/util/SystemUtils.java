package com.logsys.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * ϵͳ������
 * @author ShaonanLi
 */
public class SystemUtils {

	private static final Logger logger=Logger.getLogger(SystemUtils.class);
	
	/**���ڼ���ͬһ��ʾ��������*/
	private static final int PRIME_FACTOR=997;
		
	/**
	 * ��ȡ������ͳһ������ʾ����Ŀǰ֧�ֵ�ƽ̨��
	 * 1��Windows
	 * @return ����ͳһ��ʾ���ַ���
	 */
	public static String getUniqueMachineID() {
		try {
			BigInteger umid=BigInteger.ZERO;						//���ڼ���Ψһֵ��BigInteger����
			String os=System.getProperty("os.name");
			List<String> factorlist=new ArrayList<String>();		//���ڼ���Ψһֵ�������б�
			if(os!=null&&os.startsWith("Windows")) {	//�����Windowsƽ̨
				factorlist.add(System.getProperty("os.name"));		//��һ��������OS��hashcode
				factorlist.add(System.getProperty("user.name"));	//�ڶ���ֵ���û�����hashcode
				//��ʼ��ȡCPUʶ���
				Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" }); 
				process.getOutputStream().close();
				Scanner sc = new Scanner(process.getInputStream());
				sc.next();								//����Property��
				while(sc.hasNext())
					factorlist.add(sc.next());
				sc.close();
				//��ʼ��ȡӲ�̱�ʾ��
				process=Runtime.getRuntime().exec(new String[] {"wmic","diskdrive","get", "serialnumber"});
				process.getOutputStream().close();
				sc=new Scanner(process.getInputStream());
				sc.next();
				while(sc.hasNext())
					factorlist.add(sc.next());
				sc.close();
				//��ʼ��ȡ�����ʾ��
				process=Runtime.getRuntime().exec(new String[] {"wmic","BaseBoard","get","serialnumber"});
				process.getOutputStream().close();
				sc=new Scanner(process.getInputStream());
				sc.next();
				while(sc.hasNext())
					factorlist.add(sc.next());
				sc.close();
				//��ʼ��ȡ����ϵͳ���к�
				process=Runtime.getRuntime().exec(new String[] {"wmic","os","get","serialnumber"});
				process.getOutputStream().close();
				sc=new Scanner(process.getInputStream());
				sc.next();
				while(sc.hasNext())
					factorlist.add(sc.next());
				sc.close();
				//��ʼ��ȡ����Mac��ַ��
				process=Runtime.getRuntime().exec(new String[] {"wmic","nicconfig","get","macaddress"});
				process.getOutputStream().close();
				sc=new Scanner(process.getInputStream());
				sc.next();
				while(sc.hasNext())
					factorlist.add(sc.next());
				sc.close();
				//��Ϣ��ȡ��ϣ���ʼ����Ψһ��
				for(String factor:factorlist) {
					umid=umid.multiply(BigInteger.valueOf(PRIME_FACTOR));	//�����˵ó�����
					umid=umid.xor(BigInteger.valueOf(factor.hashCode()));	//XOR�����ص�hashcode
					umid=umid.shiftLeft(factor.hashCode()%3);				//�����ƶ�һλ
				}
				return umid.toString(16);				//����16������
			} else {
				logger.error("��֧�ֵĲ���ϵͳƽ̨["+os+"].");
				return null;
			}
		} catch(Throwable ex) {
			logger.error("���ܻ�ȡΨһ�Ļ���ID:",ex);
			return null;
		}
		
	}
	
}
