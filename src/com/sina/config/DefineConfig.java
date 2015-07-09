package com.sina.config;

import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.sina.config.load.ConfigLoad;
import com.sina.util.Constant;


public class DefineConfig extends ConfigLoad {
	private static final DefineConfig instance = new DefineConfig();
	public static int priority = 0;
	public static int noDirectionalRate = 0;
	public static String ADKEY = "";
	public static String ADUNITKEY = "";
	public static int threshold;
	public static BitSet detail = null;
	public static BitSet param = null;
	public static BitSet click = null;
	// #时间-TP,地域-ZE,频道-CH,页面-RE,用户ID-PD,浏览器-BR,语言-LA,频道-CE,收入-IN,年龄-AG,性别-GE,职业-OC,自定义1-C1,自定义2-C2,自定义3-C3,广告位-AU,UUID-UU
	public static boolean TP = true;
	public static boolean ZE = true;
	public static boolean CH = true;
	public static boolean RE = true;
	public static boolean PD = true;
	public static boolean BR = true;
	public static boolean LA = true;
	public static boolean CE = true;
	public static boolean IN = true;
	public static boolean AG = true;
	public static boolean GE = true;
	public static boolean OC = true;
	public static boolean C1 = true;
	public static boolean C2 = true;
	public static boolean C3 = true;
	public static boolean AU = true;
	public static boolean UU = true;
	// #UUID-UU,订单项-LT,广告-AD,展示价格/点击价格-PR
	public static boolean D_UU = true;
	public static boolean D_LT = true;
	public static boolean D_AD = true;
	public static boolean D_PR = true;
	// #时间-TP、地域-ZE、频道-CH、页面-RE、用户ID-PD、广告位-AU、UUID-UU、订单项-LT、广告-AD、点击价格-PR
	public static boolean C_TP = true;
	public static boolean C_ZE = true;
	public static boolean C_CH = true;
	public static boolean C_RE = true;
	public static boolean C_PD = true;
	public static boolean C_AU = true;
	public static boolean C_UU = true;
	public static boolean C_LT = true;
	public static boolean C_AD = true;
	public static boolean C_PR = true;

	private DefineConfig() {

	}

	public static DefineConfig getInstance() {
		return instance;
	}

	private Map<String, String> cache = new HashMap<String, String>();

	public String getDefineData(String key) {
		return getDefineData(key, null);
	}

	public String getDefineData(String key, String defaultValue) {
		String value = cache.get(key);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public String[] getDefineDataSplit(String key, String regex) {
		String value = cache.get(key);
		if (value != null) {
			return value.split(regex);
		}
		return null;
	}

	/**
	 * get the integer value, default is 0
	 * 
	 * @param key
	 * @return
	 */
	public int getDefineDataInt(String key) {
		return getDefineDataInt(key, 0);
	}

	public int getDefineDataInt(String key, int defaultValue) {
		String value = cache.get(key);
		if (value == null) {
			return defaultValue;
		} else {
			// try {
			return Integer.parseInt(value);
			// } catch (Exception e) {
			// return defaultValue;
			// }
		}
	}

	public float getDefineDataFloat(String key, float defaultValue) {
		String value = cache.get(key);
		if (value == null) {
			return defaultValue;
		} else {
			// try {
			return Float.parseFloat(value);
			// } catch (Exception e) {
			// return defaultValue;
			// }
		}
	}

	public long getDefineDataLong(String key, long defaultValue) {
		String value = cache.get(key);
		if (value == null) {
			return defaultValue;
		} else {
			return Long.parseLong(value);
		}
	}

	@SuppressWarnings("unchecked")
	public void load() {
		//logger.debug("DefineConfig Load Start...");

		Map<String, String> tmp = new HashMap<String, String>();
		boolean loadDB = true;
		try {
			Configuration configuration = new PropertiesConfiguration(
					propertiesfilename);
			Iterator<String> it = configuration.getKeys();
			while (it.hasNext()) {
				String key = it.next();
				tmp.put(key, configuration.getString(key));
			}
			loadDB = tmp.get(Constant.UPLOAD_DB_DEFINE_TABLE) == null ? false
					: "Y".equalsIgnoreCase(tmp
							.get(Constant.UPLOAD_DB_DEFINE_TABLE)) ? true
							: false;
			//logger.debug("DefineConfig Load properties OK...");
		} catch (ConfigurationException e) {
			loadDB = false;
			tmp.clear();
			//logger.debug("DefineConfig Load properties Error...");
		}

	/*	if (loadDB) {
			String sql = "select * from t_define_table";
			List<Map<String, Object>> sqlResult = jdbcTemplate
					.queryForList(sql);
			if (sqlResult != null && sqlResult.size() > 0) {
				for (Map<String, Object> map : sqlResult) {
					String name = (String) map.get("NAME");
					String value = (String) map.get("DEFINE_VALUE");
					if (name != null && name.trim().length() > 0
							&& value != null && value.trim().length() > 0) {
						tmp.put(name.trim(), value.trim());
					}
				}
			}
			logger.debug("DefineConfig Load DB OK...");
		}
		this.cache = Collections.unmodifiableMap(tmp);
		priority = this.getDefineDataInt("ADVERTISING_THE_DOWNSIDE_TYPE",
				Constant.LINEITEM_LOWEST_PRIORITY);
		noDirectionalRate = getDefineDataInt(Constant.ASTATIC_DELIVERY_RATE, 10);
		ADKEY = getDefineData(Constant.AD_TEMPLATE_VAR,
				Constant.AD_TEMPLATE_VAR_VALUE);
		ADUNITKEY = getDefineData(Constant.ADUNIT_TEMPLATE_VAR,
				Constant.ADUNIT_TEMPLATE_VAR_VALUE);
		threshold = getDefineDataInt(
				Constant.MULTIPLE_ADUNIT_REQUEST_THRESHOLD, 0);
		BitSet detailZore = Utils
				.formatToBitSet(Constant.IMPRESSION_LOG_DETAIL_PARAM_ZORE);
		BitSet paramZore = Utils
				.formatToBitSet(Constant.IMPRESSION_LOG_PRE_PARAM_ZORE);
		BitSet clickZore = Utils.formatToBitSet(Constant.CLICK_LOG_PARAM_ZORE);
		BitSet detailt = Utils.formatToBitSet(getDefineData(
				Constant.IMPRESSION_LOG_DETAIL_PARAM,
				Constant.IMPRESSION_LOG_DETAIL_PARAM_DEFAULT));
		BitSet paramt = Utils.formatToBitSet(getDefineData(
				Constant.IMPRESSION_LOG_PRE_PARAM,
				Constant.IMPRESSION_LOG_PRE_PARAM_DEFAULT));
		BitSet clickt = Utils.formatToBitSet(getDefineData(
				Constant.CLICK_LOG_PARAM, Constant.CLICK_LOG_PARAM_DEFAULT));
		detailt.and(detailZore);
		paramt.and(paramZore);
		clickt.and(clickZore);*/
/*s*/

		//logger.debug("DefineConfig Load End...=" + this.cache);
	}
}
