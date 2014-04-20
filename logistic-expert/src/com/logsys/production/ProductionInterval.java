package com.logsys.production;

/**
 * ����������
 * @author ShaonanLi
 */
public class ProductionInterval {
	
	/**
	 * ��������ö��
	 * @author ShaonanLi
	 */
	public static enum PdInterval {
		Early1,
		Early2,
		Early3,
		Early4,
		Early5,
		Early6,
		Early7,
		Early8,
		Early9,
		Middle1,
		Middle2,
		Middle3,
		Middle4,
		Middle5,
		Middle6,
		Middle7,
		Middle8,
		Middle9,
		Night1,
		Night2,
		Night3,
		Night4,
		Night5,
		Night6,
		Night7,
		Night8,
		Night9
	}

	/**��������*/
	public PdInterval interval;
	
	/**��ʼСʱ*/
	public int beginhour;
	
	/**��ʼ����*/
	public int beginmin;
	
	/**����Сʱ*/
	public int endhour;
	
	/**��������*/
	public int endmin;
	
	/**������Ч����ʱ��*/
	public int effmin;

	public ProductionInterval(PdInterval interval, int beginhour, int beginmin, int endhour, int endmin, int effmin) {
		this.interval = interval;
		this.beginhour = beginhour;
		this.beginmin = beginmin;
		this.endhour = endhour;
		this.endmin = endmin;
		this.effmin = effmin;
	}
	
}
