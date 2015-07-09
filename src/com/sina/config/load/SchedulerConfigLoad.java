package com.sina.config.load;

import java.util.List;

/**
 * 配置信息定时加载类
 * 
 * @Title: MFP_SERVER
 * @FileName: SchedulerConfigLoad.java
 * @Description:
 * @Copyright: Copyright (c) 2008
 * @Company:
 * @author Sun Jue
 * @Create Date: 2011-6-15
 */
public class SchedulerConfigLoad {

	private List<ConfigLoad> lstConfigLoad;

	public List<ConfigLoad> getLstConfigLoad() {
		return lstConfigLoad;
	}

	public void setLstConfigLoad(List<ConfigLoad> lstConfigLoad) {
		this.lstConfigLoad = lstConfigLoad;
	}

	/**
	 * 顺序加载各个配置的配置信息
	 */
	public void load() {
		if (lstConfigLoad != null) {
			for (ConfigLoad configLoad : lstConfigLoad) {
				configLoad.load();
			}
		}
	}

}
