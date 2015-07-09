package com.sina.util;

import java.util.Collections;
import java.util.Map;
import java.util.Random;


public interface Constant {
	int ETL_TIMESLICE = 15;
	int ETL_TIMEBEFOR = 0;
	int ETL_TIMEAFTER = 2;
	int LINEITEM_HIGHEST_PRIORITY = 0;
	int LINEITEM_LOWEST_PRIORITY = 127;
	String LINEITEM_INDEX_FILE_SUFFIX = ".lineitem";
	String AD_INDEX_FILE_SUFFIX = ".ad";
	String ADUNIT_INDEX_FILE_SUFFIX = ".unit";
	String LINEITEM_STATUS_FILE_NAME = "lineItem.status";
	String LINEITEM_STATUS_FILE_SUFFIX = ".status";
	String DEFAULT_OUTPUT_DIR = ".";
	/**
	 * 不含任何TAG的空MAP
	 */
	Map<String, Float> EMPTY_TAGS = Collections.emptyMap();

	/**
	 * 索引字段ID
	 */
	//String INDEX_FILED_ID = StringHelper.intern("ID");
	/**
	 * 索引字段广告单元
	 */
	//String INDEX_FILED_ADUNIT = StringHelper.intern("ADUNIT");
	/**
	 * 索引字段地区定向
	 */
	//String INDEX_FILED_ZONE = StringHelper.intern("ZONE");
	/**
	 * 索引字段地区否条件定向
	 */
	//String INDEX_FILED_NOT_ZONE = "NOTZONE";
	/**
	 * 索引字段TAG定向
	 */
	//String INDEX_FILED_TAG = StringHelper.intern("TAG");
	/**
	 * 索引字段TAG否条件定向
	 */
	//String INDEX_FILED_NOT_TAG = StringHelper.intern("NOTTAG");
	/**
	 * 索引字段时间定向
	 */
	String INDEX_FILED_TIME = "TIME";
	/**
	 * 索引字段浏览器定向
	 */
	String INDEX_FILED_BROWSER = "BROWSER";
	/**
	 * 索引字段语言定向
	 */
	String INDEX_FILED_LANGUAGE = "LANGUAGE";
	/**
	 * 索引字段频道定向
	 */
	String INDEX_FILED_CHANNEL = "CHANNEL";
	/**
	 * 索引字段收入定向
	 */
	String INDEX_FILED_INCOME = "INCOME";
	/**
	 * 索引字段年龄定向
	 */
	String INDEX_FILED_AGE = "AGE";
	/**
	 * 索引字段性别定向
	 */
	String INDEX_FILED_GENDER = "GENDER";
	/**
	 * 索引字段职业定向
	 */
	String INDEX_FILED_OCCUPATION = "OCCUPATION";
	/**
	 * 索引字段自定义1定向
	 */
	String INDEX_FILED_CUSTOM1 = "CUSTOM1";
	/**
	 * 索引字段自定义2定向
	 */
	String INDEX_FILED_CUSTOM2 = "CUSTOM2";
	/**
	 * 索引字段自定义3定向
	 */
	String INDEX_FILED_CUSTOM3 = "CUSTOM3";
	/**
	 * 索引值所有的默认值
	 */
	String INDEX_VALUE_ALL = "*"; // 原先为ALL
	/**
	 * 一小时的微秒数
	 */
	long HOUR_TIME_MILLIS = 3600 * 1000;
	/**
	 * 一天的微秒数
	 */
	long DAY_TIME_MILLIS = 24 * HOUR_TIME_MILLIS;
	/**
	 * 空格字符的正则表达式
	 */
	String SPACE_SPLIT_REG = "\\s+";
	/**
	 * 逗号分割符
	 */
	String COMMA_SPLIT_REG = ",";
	/**
	 * 空格分割符
	 */
	String SPACE_SPLIT = " ";
	/**
	 * 随机数生成器
	 */
	Random DEFAULT_RANDOM = new Random();
	/**
	 * 默认的搜索器大小
	 */
	byte DEFAULT_SEARCHER_ARRAY_SIZE = 2;
	/**
	 * 默认的索引内存块
	 */
	byte DEFAULT_RAM_DIR_ARRAY_SIZE = 2;
	/**
	 * 默认的异常日志输出行头
	 */
	String MTARGET_EXCEPTION_PREFIX = "MTarget Exception: ";

	String DEFINE_CLICK_IMPRESSION = "DEFINE_CLICK_IMPRESSION";

	String DEFINE_COMPANY_BALANCE_SPECIAL = "DEFINE_COMPANY_BALANCE_SPECIAL";

	String DEFINE_CTR_TIME_RANGE_SPECIAL = "DEFINE_CTR_TIME_RANGE_SPECIAL";
	/**
	 * 兴趣中保存的最大兴趣数
	 */
	String DEFINE_INTEREST_MAX_SIZE = "DEFINE_INTEREST_MAX_SIZE";
	/**
	 * 兴趣中保存的最大FORBIDDEN数
	 */
	String DEFINE_FORBIDDEN_MAX_SIZE = "DEFINE_FORBIDDEN_MAX_SIZE";
	/**
	 * 索引数据存放的类型
	 */
	String INDEX_DIRECTORY_TYPE = "INDEX_DIRECTORY_TYPE";
	/**
	 * 内存索引
	 */
	String INDEX_DIRECTORY_TYPE_RAM = "RAM";
	/**
	 * 磁盘索引
	 */
	String INDEX_DIRECTORY_TYPE_DISK = "DISK";
	/**
	 * 磁盘存放目录
	 */
	String INDEX_DIRECTORY_TYPE_DISK_PATH = "FSDIR_PATH";
	/**
	 * 默认本地文件目录的存放地址
	 */
	String DEFAULT_DISK_DIRECTORY_PATH = "./FSDIR_PATH";
	/**
	 * 默认本地索引文件目录的存放地址
	 */
	String DEFAULT_INDEX_DIRECTORY = "INDEX_FILE_FOLDER";
	/**
	 * 默认本地ITEM文件目录的存放地址
	 */
	String DEFAULT_ITEM_INFO_DIRECTORY = "ITEM_INFO_FILE_FOLDER";
	/**
	 * 自定义索引数据存放
	 */
	String INDEX_DIRECTORY_TYPE_CUSTOMIZE = "CUSTOMIZE";
	/**
	 * 自定义索引数据存放目录的class
	 */
	String INDEX_DIRECTORY_CUSTOMIZE_CLASS = "CUSTOMIZE_INDEX_DIRECTORY";
	/**
	 * 默认的搜索结果大小
	 */
	String SEARCH_RESULT_SIZE = "SEARCH_RESULT_SIZE";
	/**
	 * 默认的搜索器数量
	 */
	String SEARCHER_ARRAY_SIZE = "SEARCHER_ARRAY_SIZE";
	/**
	 * 内存与搜索器的比率,最终的内存块数为"SEARCHER_ARRAY_SIZE>>>RAMDIRECTORY_SEARCHER_RATE"
	 */
	String RAMDIRECTORY_SEARCHER_RATE = "RAMDIRECTORY_SEARCHER_RATE";
	/**
	 * 最大内存索引块
	 */
	String MAX_DIRECTORY_ARRAY_SIZE = "MAX_RAMDIRECTORY_ARRAY_SIZE";
	/**
	 * 返回给客户端搜索请求数的倍数
	 */
	String CLIENT_CACHE_MULTIPLIER = "CLIENT_CACHE_MULTIPLIER";
	/**
	 * 服务器端缓存的搜索请求数的倍数
	 */
	String SERVER_CACHE_MULTIPLIER = "SERVER_CACHE_MULTIPLIER";
	/**
	 * 服务器JCS中端缓存搜索请求结果的Region
	 */
	String SEARCH_RESULT_SERVER_CACHE_REGION = "cachesearchresult";
	/**
	 * CTR调节系数
	 */
	String CTR_ADJUST_MODULUS = "CTR_ADJUST_MODULUS";
	/**
	 * 数据同步批量的记录数
	 */
	String ETL_BATCH_SIZE = "ETL_BATCH_SIZE";
	/**
	 * ETL单位时间片长度,单位为分钟
	 */
	String ETL_TIME_SLICE_INTERVAL = "ETL_TIME_SLICE_INTERVAL";
	/**
	 * 预先抽取时间片个数
	 */
	String PREPROCESS_ETL_NUMBER = "PREPROCESS_ETL_NUMBER";
	/**
	 * HashFunction加密算法
	 */
	String DEFAULT_URL_HASH_ARITHMETIC = "MD5";
	/**
	 * 存于用户Cookie的sessionid的key
	 */
	String COOKIE_KEY = "MTARGET_COOKIE_SESSIONID";
	/**
	 * 是否加载数据库t_define_table表中的数据
	 */
	String UPLOAD_DB_DEFINE_TABLE = "UPLOAD_DB_DEFINE_TABLE";
	/**
	 * 当前SERVER实例的名称,主要供主控机使用,以区别对待主备机,该项配置在本地
	 */
	String SERVER_INSTANCE_ALIAS = "SERVER_INSTANCE_ALIAS";
	/**
	 * 当前负责主控机名称,设置在T_DEFINE_TABLE中,以标识当前起作用的主控机
	 */
	String CURRENT_VALID_CONTROL_CENTRE = "CURRENT_VALID_CONTROL_CENTRE";
	/**
	 * 订单项搜索数据路径
	 */
	String LINEITEM_SEARCH_RELATED_FILE_PATH = "LINEITEM_SEARCH_RELATED_FILE_PATH";
	/**
	 * 广告创意ETL文件位置
	 */
	String AD_ETL_FILE_PATH = "AD_ETL_FILE_PATH";
	/**
	 * 广告单元ETL文件位置
	 */
	String ADUNIT_ETL_FILE_PATH = "ADUNIT_ETL_FILE_PATH";
	/**
	 * 订单项状态ETL文件位置
	 */
	String LINEITEM_STATUS_ETL_FILE_PATH = "LINEITEM_STATUS_ETL_FILE_PATH";
	/**
	 * 优化广告选择策略,以百分之几的比重选择点击率高的广告,值为0-100之间
	 */
	String CREATIVE_OPTIMIZED_ROTATION_PERCENT = "CREATIVE_OPTIMIZED_ROTATION_PERCENT";
	/**
	 * 更改广告的点击URL,将其先达到指定跳转URL,再重定向到landingPageUrl,该值在DefineConfig中取得
	 */
	String JUMP_URL = "JUMP_URL";
	/**
	 * 默认的点击URL在广告content中的属性名,广告的content为json格式
	 */
	String CLICK_URL = "CLICK_URL";
	/**
	 * 广告点击展示UUID
	 */
	String CLICK_UUID = "UUID";
	/**
	 * 请求跳转时在URL中广告位ID的命名
	 */
	String URL_ADUNITID_NAMED = "ADUNITID";
	/**
	 * 请求URL中的频道信息
	 */
	String URL_CHANNEL = "CHANNEL";
	/**
	 * 请求跳转时在URL中订单项ID的命名
	 */
	String URL_LINEITEMID_NAMED = "LINEITEMID";
	/**
	 * 请求跳转时在URL中TAG的命名
	 */
	String URL_TAG_NAMED = "TAG";
	/**
	 * 请求跳转时在URL中广告ID的命名
	 */
	String URL_ADID_NAMED = "ADID";
	/**
	 * 请求跳转时在URL中价格的命名
	 */
	String URL_PRICE_NAMED = "PRICE";
	/**
	 * 请求跳转时在URL中跳转页的命名
	 */
	String URL_LANDINGPAGEURL_NAMED = "LandingPageURL";
	/**
	 * MT的会话ID
	 */
	String URL_SESSION_ID = "SESSIONID";
	/**
	 * URL REFERE
	 */
	String URL_REFERER = "Referer";
	/**
	 * HEAD 中的城市
	 */
	String HEAD_REGION_CITY = "city";
	/**
	 * 客户的会话ID
	 */
	String PUBLISHER_SESSION_ID = "PUBLISHERSESSIONID";
	/**
	 * CPC每次点击最小价格差额
	 */
	String DEFINE_CLICK_COST_PRICEUNIT = "DEFINE_CLICK_COST_PRICEUNIT";
	/**
	 * 默认ClickCost最小价格差额
	 */
	int DEFAULT_CLICK_COST_PRICEUNIT = 50;
	/**
	 * CPM每次展示最小价格差额
	 */
	String DEFINE_IMPRESSION_COST_PRICEUNIT = "DEFINE_IMPRESSION_COST_PRICEUNIT";
	/**
	 * 默认ImpressionCost最小价格差额
	 */
	int DEFAULT_IMPRESSION_COST_PRICEUNIT = 10;

	String AD_REGION = "AD_REGION";
	String PRICE_ENCODE_SEED = "MTARGET";
	/**
	 * 主控机使用的保存每个lineitem在各个tomcat中的权重
	 */
	String SERVER_REDIS_LINEITEMTOMCAT_WEIGHT_KEY = "SERVER_REDIS_LINEITEMTOMCAT_WEIGHT_KEY";
	/**
	 * 主控机使用的保存每个lineitem在各个tomcat中的投放量展示数
	 */
	String SERVER_REDIS_LINEITEMTOMCAT_KEY = "SERVER_REDIS_LINEITEMTOMCAT_KEY";
	/**
	 * 主控机使用的保存每个lineitem在各个tomcat中的计算投放数时的参数
	 */
	String SERVER_REDIS_LINEITEMTOMCAT_QUOTA_KEY = "SERVER_REDIS_LINEITEMTOMCAT_QUOTA_KEY";
	/**
	 * 主控机使用的保存当天整天的lineitem数据信息
	 */
	String SERVER_REDIS_LINEITEMTODAY_KEY = "SERVER_REDIS_LINEITEMTODAY_KEY";
	/**
	 * 主控机使用的保存明天整天的lineitem数据信息
	 */
	String SERVER_REDIS_LINEITEMTOMRROW_KEY = "SERVER_REDIS_LINEITEMTOMRROW_KEY";
	/**
	 * tomcat存放CPM订单展示数的KEY名
	 */
	String TOMCAT_LINE_IMP = "TOMCAT_LINE_IMP";
	/**
	 * ADSERVER实例名称
	 */
	String ADSERVER_NAME = "ADSERVER_NAME";
	/**
	 * 默认的REDIS过期时间,一天
	 */
	int DEFAULT_REDIS_EXPIRE_SECONDS = 86400;
	/**
	 * REDIS过期时间参数
	 */
	String REDIS_EXPIRE_SECONDS = "REDIS_EXPIRE_SECONDS";
	/**
	 * 一天的时间片个数
	 */
	int EVERYDAY_TIMESLICE_NUMBER = 96;
	/**
	 * 定义COOKIE过期时间
	 */
	String COOKIE_EXPIRATION = "COOKIE_EXPIRATION";
	/**
	 * 默认COOKIE过期值
	 */
	int COOKIE_EXPIRATION_VALUE = -1;
	/**
	 * 用户TAG在jcs中存入的REGION名
	 */
	String JCS_USER_TAG_REGION = "USER_TAG_REGION";
	/**
	 * REFERE URL TAG存放在JCS的REGION名
	 */
	String JCS_REFERER_TAG_REGION = "REFERE_TAG_REGION";
	/**
	 * 无定向投放比重,设0-100之间的数.
	 */
	String ASTATIC_DELIVERY_RATE = "ASTATIC_DELIVERY_RATE";
	/**
	 * 记录CPM展示次数的REGION
	 */
	String CPM_IMPRESSION_REGION = "CPM_IMPRESSION_REGION";
	/**
	 * 投放出错时需返回的错误代码,默认为空字符
	 */
	String DELIVERY_ERROR_CODE = "DELIVERY_ERROR_CODE";
	/**
	 * 广告landingPageUrl后跟着的UUID的KEY
	 */
	String URL_UUID_KEY = "UUIDKEY";
	/**
	 * 主控机删除的文件从当前时间片前第几个时间片开始,默认为1
	 */
	String CENTRE_DEL_EXPIRE_FILE_POINT = "CENTRE_DEL_EXPIRE_FILE_POINT";
	/**
	 * 主控机需删除几个时间片的过期文件 ,默认为0
	 */
	String CENTRE_DEL_EXPIRE_FILE_NUM = "CENTRE_DEL_EXPIRE_FILE_NUM";
	/**
	 * TAG CTR的默认值,默认为万分之一
	 */
	String DEFAULT_TAG_CTR = "DEFAULT_TAG_CTR";
	/**
	 * TAG CTR的默认值
	 */
	float DEFAULT_TAG_CTR_VALUE = 1f / 10000;
	/**
	 * 保底广告优先级设定值
	 */
	String ADVERTISING_THE_DOWNSIDE_TYPE = "ADVERTISING_THE_DOWNSIDE_TYPE";
	/**
	 * 
	 * 当前搜索广告单元不存在返回的错误代码
	 * 
	 */
	String ADUNIT_EMPTY_ERROR_CODE = "ADUNIT_EMPTY_ERROR_CODE";
	/**
	 * 
	 * USER TAG与URL TAG的比例,默认值是1:1 前者是USER,后者是URL
	 */
	String PROPORTION_BETWEEN_USER_AND_URL_TAG = "PROPORTION_BETWEEN_USER_AND_URL_TAG";
	/**
	 * 配置IP生成的IP索引文件的路径,如果为CSV文件，则以逗号或分号分隔多个文件路径，排后的文件权重高
	 */
	String IP_FILE_PATH_CONFIG = "IP_FILE_PATH_CONFIG";
	/**
	 * 配置解析IP文件的方式解析方式，默认为MAXMIND
	 */
	String IP_FILE_PARSER_TYPE = "IP_PARSER_TYPE";
	/**
	 * 配置解析IP文件的方式解析方式
	 */
	String IP_FILE_PARSER_TYPE_MAXMIND = "MAXMIND";
	/**
	 * 配置解析IP文件的方式解析方式
	 */
	String IP_FILE_PARSER_TYPE_CSV = "CSV";
	/**
	 * 配置IP生成的IP索引实例数
	 */
	String IP_LOOKUP_INSTANCE_NUM = "IP_LOOKUP_INSTANCE_NUM";
	/**
	 * 默认IP生成的IP索引实例数
	 */
	int DEFAULT_IP_LOOKUP_INSTANCE_NUM = 1;// 10;
	/**
	 * Zone搜索时使用的方式：<BR>
	 * SEARCH=倒排索引搜索方式，默认<BR>
	 * SET=HashSet方式<BR>
	 * PREFIX=String的prefix(startsWith)方式，未实现
	 */
	String ZONE_SEARCHER_TYPE = "ZONE_SEARCHER_TYPE";
	/**
	 * Zone搜索时使用的方式:SEARCH=倒排索引搜索方式
	 */
	String ZONE_SEARCHER_TYPE_SEARCH = "SEARCH";
	/**
	 * Zone搜索时使用的方式:SET=HashSet方式
	 */
	String ZONE_SEARCHER_TYPE_SET = "SET";
	/**
	 * Zone搜索时使用的方式:PREFIX=String的prefix(startsWith)方式，未实现
	 */
	String ZONE_SEARCHER_TYPE_PREFIX = "PREFIX";
	/**
	 * com.mtarget.cache.memory.MemoryCache中临时存放的对象个数
	 */
	String MEMORY_CACHE_NUM = "MEMORY_CACHE_NUM";
	/**
	 * com.mtarget.cache.memory.MemoryCache中临时存放的默认对象个数
	 */
	int DEFAULT_MEMORY_CACHE_NUM = 10000;
	/**
	 * 默认所有位都为1的long型
	 */
	long LONG_ALL_BIT_ONE = 0xFFFFFFFFFFFFFFFFL;// 0xFFFFFFFFFFFFFFFFL==-1
	/**
	 * 搜索定向条件为空
	 */
	long LONG_NULL_CONDITION = 0X8000000000000000L;// LONG_BIT[63];
	/**
	 * 64个指定一位为1的long数组，低位优先，最高位为搜索定向条件为空
	 */
	long[] LONG_BIT = new long[] { 1L << 0, 1L << 1, 1L << 2, 1L << 3, 1L << 4,
			1L << 5, 1L << 6, 1L << 7, 1L << 8, 1L << 9, 1L << 10, 1L << 11,
			1L << 12, 1L << 13, 1L << 14, 1L << 15, 1L << 16, 1L << 17,
			1L << 18, 1L << 19, 1L << 20, 1L << 21, 1L << 22, 1L << 23,
			1L << 24, 1L << 25, 1L << 26, 1L << 27, 1L << 28, 1L << 29,
			1L << 30, 1L << 31, 1L << 32, 1L << 33, 1L << 34, 1L << 35,
			1L << 36, 1L << 37, 1L << 38, 1L << 39, 1L << 40, 1L << 41,
			1L << 42, 1L << 43, 1L << 44, 1L << 45, 1L << 46, 1L << 47,
			1L << 48, 1L << 49, 1L << 50, 1L << 51, 1L << 52, 1L << 53,
			1L << 54, 1L << 55, 1L << 56, 1L << 57, 1L << 58, 1L << 59,
			1L << 60, 1L << 61, 1L << 62, 1L << 63 };
	String DIRECTIONAL_ALL = "*";// 原先值ALL

	enum CostType {
		CPC, CPM, CPT;
	}

	// enum RedisType {
	// SHARE, MONOPOLIZE, LOCAL;
	// }

	enum LineItemStatus {
		DELIVERING, READY, PAUSED, NEEDS_CREATIVES, PAUSED_INVENTORY_RELEASED, PENDING_APPROVAL, COMPLETED, DISAPPROVED, DRAFT, CANCELED;
	}

	enum DeliveryRateType {
		EVENLY, FRONTLOADED, AS_FAST_AS_POSSIBLE;
	}

	enum RoadblockingType {
		ONLY_ONE, ONE_OR_MORE, AS_MANY_AS_POSSIBLE, ALL_ROADBLOCK;
	}

	enum UnitType {
		IMPRESSIONS, CLICKS;
	}

	enum Duration {
		NONE, LIFETIME, DAILY;
	}

	enum DiscountType {
		ABSOLUTE_VALUE, PERCENTAGE;
	}

	enum StartDateTimeType {
		USE_START_DATE_TIME, IMMEDIATELY, ONE_HOUR_FROM_NOW;
	}

	/*
	 * 广告单元状态
	 */
	enum Status {
		ACTIVE, INACTIVE, ARCHIVED;
	}

	enum UnlimitedEndDateTime {
		FALSE, TRUE;
	}

	enum CreativeRotationType {
		EVEN, OPTIMIZED, MANUAL;
	}

	enum TargetWindow {
		_TOP, _BLANK;
	}

	/**
	 * TOMCAT状态
	 */
	enum StatusType {
		VALID, STOP, INVALID;
	}

	// enum DELIVERY_BRANCH {
	// eCPM, NONE_TAP, ASTATIC, CPT, NONE;
	// }

	int SESSION_IDLE_TIME = 60;
	int SESSION_IDLE_COUNT = 2;

	enum TCP_SERVER_TYPE {
		DELIVERY, CLICK;
	}

	/**
	 * 订单状态
	 * 
	 */
	enum OrderStatus {
		DRAFT, PENDING_APPROVAL, APPROVED, DISAPPROVED, PAUSED, CANCELED;
	}

	/**
	 * 公司类型
	 */
	enum CompanyType {
		HOUSE_ADVERTISER, HOUSE_AGENCY, ADVERTISER, AGENCY, AD_NETWORK;
	}

	enum CreativeType {
		TEXT, FLASH, PICTURE;
	}

	int RESTFUL_DEFAULT_LIMIT = 10;
	int RESTFUL_MAX_LIMIT = 100;
	/**
	 * 用户与URL TAG合并后保留最高多少个TAG
	 */
	String USER_URL_TAG_NUM = "USER_URL_TAG_NUM";
	int USER_URL_TAG_NUM_VALUE = -1;
	/**
	 * 待识别URL文件临时存放位置,默认为当前用户路径
	 */
	String WAITING_FOR_RECOGNITION_URL_TEMP_POSITION = "WAITING_FOR_RECOGNITION_URL_TEMP_POSITION";
	/**
	 * 待识别URL文件最终存放位置,默认为当前用户路径
	 */
	String WAITING_FOR_RECOGNITION_URL_DEST_POSITION = "WAITING_FOR_RECOGNITION_URL_DEST_POSITION";
	/**
	 * 待确认URL日志文件过期时间,默认为一小时，以毫秒为单位
	 */
	String WAITING_FOR_RECOGNITION_URL_EXPIRE_MILLISECOND = "WAITING_FOR_RECOGNITION_URL_EXPIRE_MILLISECOND";
	long WAITING_FOR_RECOGNITION_URL_EXPIRE_MILLISECOND_VALUE = 60L * 60 * 1000;
	String TEMPLATE_REG = "~\\{\\d+\\}";// "\\{\\d+\\}";//"(^|\\b)\\{\\d*\\}";
	String TEMPLATE_REG_END = "~\\{\\d+\\}$";
	String MULTIPLE_ADUNIT_REQUEST_THRESHOLD = "MULTIPLE_ADUNIT_REQUEST_THRESHOLD";
	String AD_TEMPLATE_VAR = "AD_TEMPLATE_VAR";
	String AD_TEMPLATE_VAR_VALUE = "uuid";
	String ADUNIT_TEMPLATE_VAR = "ADUNIT_TEMPLATE_VAR";
	String ADUNIT_TEMPLATE_VAR_VALUE = "ads";
	String AD_TEMPLATE_DEFINE = "AD_TEMPLATE_DEFINE";
	String AD_TEMPLATE_DEFINE_VALUE = "<!--#define(String uuid)-->";
	String ADUNIT_TEMPLATE_DEFINE = "ADUNIT_TEMPLATE_DEFINE";
	String ADUNIT_TEMPLATE_DEFINE_VALUE = "<!--#define(String[] ads)-->";
	String MT_SPLIT = "MTSPLIT";
	String AD_TEMPLATE_ASSISTS_KEY = "ASSISTS";
	String AD_TEMPLATE_IDS_KEY = "IDS";
	String ADUNIT_TEMPLATE_ASSISTS_KEY = "ASSISTS";
	String ADUNIT_TEMPLATE_ORDER_KEY = "ORDERS";
	String ADUNIT_TEMPLATE_ADS_KEY = "ADS";
	String REDIS_EXPIRED_TIME = "REDIS_EXPIRED_TIME";
	int DEFAULT_REDIS_EXPIRED_TIME = 86400;
	String IMPRESSION_LOG_PRE_PARAM = "IMPRESSION_LOG_PRE_PARAM";
	String IMPRESSION_LOG_DETAIL_PARAM = "IMPRESSION_LOG_DETAIL_PARAM";
	String CLICK_LOG_PARAM = "CLICK_LOG_PARAM";
	String IMPRESSION_LOG_PRE_PARAM_ZORE = "11111111111111111";
	String IMPRESSION_LOG_PRE_PARAM_DEFAULT = "11111111111111111";
	String IMPRESSION_LOG_DETAIL_PARAM_ZORE = "1111";
	String IMPRESSION_LOG_DETAIL_PARAM_DEFAULT = "1111";
	String CLICK_LOG_PARAM_ZORE = "1111111111";
	String CLICK_LOG_PARAM_DEFAULT = "1111111111";
	String CLICK_EVENT = "event";
	String LOAD_FILE_BATCH = "LOAD_FILE_BATCH";
	int LOAD_FILE_BATCH_TIME = 1;
}
