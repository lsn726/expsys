package com.logsys.bom;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * BOM������
 * @author lx8sn6
 */
public class BOMUtil {

	private static Logger logger=Logger.getLogger(BOMUtil.class);

	/**������BOM�ڵ��е�״̬*/
	public static enum BOM_PN_STATUS {
		/**�����ڵ�*/
		TOPNODE,
		/**�ӽڵ�*/
		SUBNODE,
		/**�����ڶ����ڵ�,�ִ����ӽڵ�*/
		MIXNODE,
		/**�����ڵĽڵ�*/
		NOTEXIST,
		/**���ִ���*/
		ERROR
	};
	
	/**
	 * ��BOMListת��Ϊ����BOMNode�Ľṹ������
	 * @param bomlist ƽ�л�BOM�б�
	 * @return BOMNode�ṹ����Ԫ
	 */
	public static BOMNode bomListToBomNodeStructure(List<BOMContent> bomlist) {
		if(bomlist==null) {
			logger.error("���ܽṹ��BOM�б�,����Ϊ��.");
			return null;
		}
		if(bomlist.size()==0) {
			logger.error("���ܽṹ��BOM�б�,�б��SizeΪ0.");
			return null;
		}
		if(bomlist.get(0).getLvl()!=0) {
			logger.error("BOM�б����,CS12�����󶥲����ֶ����lvlΪ0���ܳɼ�pn.");
			return null;
		}
		BOMNode topnode=new BOMNode(bomlist.get(0));		//����ڵ�
		topnode.setFatherNode(null);						//���ö���ڵ�ĸ��ڵ�Ϊnull
		BOMNode fathernode=topnode;							//���ڵ㣬��ʼΪ����ڵ�
		BOMNode prevnode=topnode;							//ǰһ������Ľڵ�
		BOMNode itnode;										//���ڱ����Ľڵ�
		BOMContent bnow;									//�������ڴ����BOM��Ϣ
		int lvldiff;										//�ȼ�����
		int index;
		for(index=1;index<bomlist.size();index++) {			//��ʼ��ͷ�����ڵ�
			bnow=bomlist.get(index);
			itnode=new BOMNode(bnow);
			if(bnow.getLvl()==0) {
				logger.error("���󣬳��ֶ���һ��0���𶥼��ڵ�.");
				return null;
			}
			lvldiff=prevnode.getBomContent().getLvl()-bnow.getLvl();	//��ȡ�ȼ�����
			if(lvldiff==0) {	//�������һ����ڵ���ͬ�����Node,����ӵ�ͬһ��fathernode��
				fathernode.addSubNode(itnode);		//����ӽڵ�,ͬʱ���ø��ڵ�
				prevnode=itnode;					//����ǰ�ڵ�����Ϊǰһ�ڵ�
				continue;
			}
			if(lvldiff<0) {		//�����ǰ���ϵȼ�������һ����ڵ㣬��˵���µ������ǰһ����������
				if(bnow.getLvl()-prevnode.getBomContent().getLvl()>1) {	//һ��ֻ������һ���ȼ�
					logger.error("����,�������������ڸ��������Ӵ���1.");
					return null;
				} else {
					prevnode.addSubNode(itnode);	//����ǰ�ڵ�����Ϊǰһ�ڵ���ӽڵ㣬ͬʱ���ø��ڵ�Ϊǰһ�ڵ�
					fathernode=prevnode;			//���ڵ�����Ϊǰһ�ڵ�
					prevnode=itnode;				//ǰһ�ڵ�����Ϊ��ǰ�ڵ�
					continue;
				}
			}
			if(lvldiff>0) {		//�����ǰ�ڵ�ȼ�С��ǰһ�ڵ�ȼ�����˵ǰһ�ڵ���װ������
				fathernode=prevnode.getFatherNode();		//���ҵ���һ�����ڵ�
				for(int i=lvldiff;i>0;i--) 				//ѭ�������ҵ���һ�����ڵ�
					fathernode=fathernode.getFatherNode();
				fathernode.addSubNode(itnode);				//Ϊ���ڵ�����ӽڵ�
				prevnode=itnode;							//ǰһ���ڵ�����Ϊ��ǰ�ڵ�
				continue;
			}
		}
		return topnode;
	}
	
	/**
	 * �ж�Part Number�����ݿ�BOM���е�״̬
	 * @param pn ��Ҫ�鿴��Part Number����
	 * @return �Ǻ���Ķ����ڵ�true/���Ǻ���Ķ����ڵ�false
	 */
	public static BOM_PN_STATUS getPNStatusInBOMDB(String pn) {
		if(pn==null) {
			logger.error("�����ж��Ƿ��Ǻ���Ķ����ڵ�.Part NumberΪ��.");
			return BOM_PN_STATUS.ERROR;
		}
		List<BOMContent> bomlist=BOMDataReaderDB.getBOMListByPN(pn);
		if(bomlist==null) return BOM_PN_STATUS.ERROR;
		boolean isTopNode=false;
		boolean isSubNode=false;
		for(BOMContent cont:bomlist) {
			if(cont.getLvl()==0) isTopNode=true;
			if(cont.getLvl()!=0) isSubNode=true;
		}
		if(isTopNode&&isSubNode) return BOM_PN_STATUS.MIXNODE;
		if(isTopNode&&!isSubNode) return BOM_PN_STATUS.TOPNODE;
		if(!isTopNode&&isSubNode) return BOM_PN_STATUS.SUBNODE;
		if(!isTopNode&&!isSubNode) return BOM_PN_STATUS.ERROR;
		return BOM_PN_STATUS.ERROR;
	}
	
}
