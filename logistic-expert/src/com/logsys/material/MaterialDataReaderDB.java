package com.logsys.material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * 物料数据读取器--数据库读取器
 * @author lx8sn6
 */
public class MaterialDataReaderDB {

	private static final Logger logger=Logger.getLogger(MaterialDataReaderDB.class);
	
	/**
	 * 从数据库获取物料数据，筛选条件为pnset中含有的part number号码
	 * @param pnset 物料号码集合,null为不限制物料号码
	 * @param makebuy 是内置"make"还是外购"buy",如果两个都不不是或者null则不予以限制
	 * @param inuse 物料是否正在使用
	 * @return List<物料信息>列表/null查找失败
	 */
	public static List<MaterialContent> getDataFromDB(Set<String> pnset, String makebuy, boolean inuse) {
		if(pnset!=null)
			if(pnset.size()==1) return new ArrayList<MaterialContent>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session创建错误："+ex);
			return null;
		}
		String hql="from MaterialContent where 1=1";
		if(pnset!=null) {							//如果有pnset限制
			hql+=" and (";
			for(String pn:pnset)
				hql+="pn='"+pn+"' and ";
			hql+=")";
		}
		if(inuse=true) hql+=" and inuse=1";			//如果限制物料是否正在使用
		if(makebuy!=null)							//如果makebuy有限制
			if(makebuy.equals("make")||makebuy.equals("buy"))
				hql+=(" and makebuy='"+makebuy+"'");//如果makebuy有限制
		MaterialContent matcontent;
		List<MaterialContent> matlist;
		Query query;
		try {
			query=session.createQuery(hql);
			matlist=query.list();
		} catch(Throwable ex) {
			logger.error("数据库物料查找出现错误."+ex);
			return null;
		} finally {
			session.close();
		}
		return matlist;
	}

	
	
	
}
