package com.sina.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.log4j.Logger;

import com.google.protobuf.ByteString;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.Content;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.MFPThirdPartyInfo;
import com.sina.config.DefineConfig;

public final class Utils {
	private static Logger mfpinfolog = Logger.getLogger("monitorLogger");
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(Utils.class);
//	public static AtomicLong total=new AtomicLong(0);
//	public static AtomicLong count=new AtomicLong(0);
	public static String TIME_SLOT_AND = null;
	public static Pattern QUERY_PATTERN = Pattern.compile("\\S+\\s*(<>|=|in\\s*\\(|IN\\s*\\()\\s*\\?");
	public static Pattern LANG_PATTERN = Pattern.compile("\\p{Alpha}{2,}(-\\p{Alpha}{2,})*");
	public static Pattern TEMPLATE_PATTERN=Pattern.compile(Constant.TEMPLATE_REG);
	public static Pattern TEMPLATE_PATTERN_END=Pattern.compile(Constant.TEMPLATE_REG_END);
	public static Pattern AD_TEMPLATE_PATTERN = Pattern.compile("\\{url:http://[\\p{Alnum}|[\\u4e00-\u9fa5]]*(\\.[\\p{Alnum}|[\\u4e00-\u9fa5]]*)*\\}");//\{url:http://[\p{Alnum}]*(\.\p{Alnum}*)*\}
	static {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < Constant.EVERYDAY_TIMESLICE_NUMBER; i++) {
			sb.append("1");
		}
		TIME_SLOT_AND = sb.toString();
	}

	public static final byte[] convertObject2Byte(Object o) {
		if (o == null) {
			return null;
		}

		ByteArrayOutputStream result = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(result);
			out.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out = null;
			}
		}

		return result.toByteArray();
	}

	public static final Object convertByte2Object(byte[] b) {
		if (b == null || b.length == 0) {
			return null;
		}
		Object o = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(bais);
			o = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
		}
		return o;
	}

	public static int bytesToInt(byte[] bytes) {
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}

	public static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		result[3] = (byte) ((i >> 24) & 0xFF);
		result[2] = (byte) ((i >> 16) & 0xFF);
		result[1] = (byte) ((i >> 8) & 0xFF);
		result[0] = (byte) (i & 0xFF);
		return result;
	}

	public static Constant.CreativeRotationType convertToCreativeRotationEnum(
			int creativeRotation) {
		Constant.CreativeRotationType rotation = null;
		switch (creativeRotation) {
		case 0:
			rotation = Constant.CreativeRotationType.EVEN;
			break;
		case 1:
			rotation = Constant.CreativeRotationType.OPTIMIZED;
			break;
		default:
			rotation = Constant.CreativeRotationType.MANUAL;
		}
		return rotation;
	}

	public static Constant.CostType convertToCostTypeEnum(
			int creativeRotation) {
		Constant.CostType type = null;
		switch (creativeRotation) {
		case 0:
			type = Constant.CostType.CPC;
			break;
		case 1:
			type = Constant.CostType.CPM;
			break;
		default:
			type = Constant.CostType.CPT;
		}
		return type;
	}
	public static boolean run() {
		return true;
		// DefineConfig config = DefineConfig.getInstance();
		// return config.getDefineData(Constant.CURRENT_VALID_CONTROL_CENTRE,
		// "")
		// .equalsIgnoreCase(
		// config.getDefineData(Constant.SERVER_INSTANCE_ALIAS));
	}

	public static String createSessionCookieKey() {
		return Constant.COOKIE_KEY + "_SESSIONID";
	}

	public static String createFirstLoginDailyCookieKey() {
		return Constant.COOKIE_KEY + "_FIRST_LOGIN_DAILY";
	}

	public static String createUidCookieKey() {
		return Constant.COOKIE_KEY + "_UID";
	}

	public static int formatToInt(String str) {
		if (str != null && !"".equals(str)) {
			return Integer.parseInt(str);
		} else {
			return 0;
		}
	}

	public static long formatToLong(String str) {
		if (str != null && !"".equals(str)) {
			return Long.parseLong(str);
		} else {
			return 0L;
		}
	}

	public static float formatToFloat(String str) {
		if (str != null && !"".equals(str)) {
			return Float.parseFloat(str);
		} else {
			return 0;
		}
	}

	public static double formatToDouble(String str) {
		if (str != null && !"".equals(str)) {
			return Double.parseDouble(str);
		} else {
			return 0;
		}
	}

	public static boolean formatToBoolean(String str) {
		if ("1".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	// TODO 价格加密
	public static String encodePrice(float price) {
		return String.valueOf(price);
	}

	// TODO 价格解密
	public static String decodePrice(String price) {
		return price;
	}

	public static BitSet formatToBitSet(String str) {
		BitSet bs = new BitSet();
		if (str == null) {
			return bs;
		}
		str = str.trim();
		if (str.isEmpty()) {
			return bs;
		}

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '1') {
				bs.set(i);
			}
		}
		return bs;
	}

	public static ArrayList<String> createSql(HashSet<Long> sets, String sql,
			int batchSize) {
		ArrayList<String> sqls = new ArrayList<String>();
		if (sets != null && !sets.isEmpty()) {
			int t = sets.size() / batchSize;
			t += sets.size() % batchSize > 0 ? 1 : 0;
			Long[] ids = new Long[sets.size()];
			sets.toArray(ids);
			for (int i = 1; i <= t; i++) {
				StringBuffer sb = new StringBuffer();
				for (int j = (i - 1) * batchSize; j < Math.min(i * batchSize,
						ids.length); j++) {
					sb.append(ids[j]);
					if (j < Math.min(i * batchSize, ids.length) - 1) {
						sb.append(",");
					}
				}
				sqls.add(String.format(sql, sb.toString()));
			}

		}
		return sqls;
	}

	public static ArrayList<String> createSql(List<String> sets, String sql,
			int batchSize) {
		ArrayList<String> sqls = new ArrayList<String>();
		if (sets != null && !sets.isEmpty()) {
			int t = sets.size() / batchSize;
			t += sets.size() % batchSize > 0 ? 1 : 0;
			String[] ids = new String[sets.size()];
			sets.toArray(ids);
			for (int i = 1; i <= t; i++) {
				StringBuffer sb = new StringBuffer();
				for (int j = (i - 1) * batchSize; j < Math.min(i * batchSize,
						ids.length); j++) {
					sb.append(ids[j]);
					if (j < Math.min(i * batchSize, ids.length) - 1) {
						sb.append(",");
					}
				}
				sqls.add(String.format(sql, sb.toString()));
			}

		}
		return sqls;
	}

	/**
	 * 返回值为一个Object数组，其中第一个为合并后的tagMap，第二个为权重最高的tag
	 * 
	 * @param userTags
	 * @param urlTags
	 * @return
	 */
	public static Object[] combineTag(Map<String, Float> userTags,
			Map<String, Float> urlTags) {

		Map<String, Float> map = null;
		boolean divTwo = false;
		if (userTags == null && urlTags == null) {
			return new Object[] { Constant.EMPTY_TAGS, "" };
		} else if (userTags == null && urlTags != null) {
			map = urlTags;
			divTwo = false;
		} else if (userTags != null && urlTags == null) {
			map = userTags;
			divTwo = false;
		} else {
			divTwo = false;
			// 合并两个Tags,权重累加
			if (userTags.size() > urlTags.size()) {
				for (Entry<String, Float> entry : urlTags.entrySet()) {
					if (userTags.containsKey(entry.getKey())) {
						divTwo = true;
						userTags.put(entry.getKey(), (userTags.get(entry
								.getKey()) + entry.getValue()));
					} else {
						userTags.put(entry.getKey(), entry.getValue());
					}
				}
				map = userTags;
			} else {
				for (Entry<String, Float> entry : userTags.entrySet()) {
					if (urlTags.containsKey(entry.getKey())) {
						divTwo = true;
						urlTags.put(
								entry.getKey(),
								(urlTags.get(entry.getKey()) + entry.getValue()));
					} else {
						urlTags.put(entry.getKey(), entry.getValue());
					}
				}
				map = urlTags;
			}
		}

		// 对合并的后的Tags权重除以2,并找到权重最高的Tag
		String tag = "";
		float weight = -1;
		for (Entry<String, Float> entry : map.entrySet()) {
			if (divTwo) {
				entry.setValue(entry.getValue() / 2);
			}

			if (entry.getValue() > weight) {
				weight = entry.getValue();
				tag = entry.getKey();
			}
		}

		return new Object[] { map, tag };
	}
	
	public static Object[] combineTagAccordingToProportion(Map<String, Float> userTags,
			Map<String, Float> urlTags,int num) {
		String[] proportion = DefineConfig.getInstance().getDefineData(Constant.PROPORTION_BETWEEN_USER_AND_URL_TAG,"1:1").split(":");
		int userRatio = Integer.parseInt(proportion[0]);
		int urlRatio = Integer.parseInt(proportion[1]);
		Map<String, Float> map = null;
		if(userRatio==0 && urlRatio==0){
			return new Object[] { Constant.EMPTY_TAGS, "" }; 
		}else if(userRatio!=0 && urlRatio==0){
			if(userTags==null || userTags.isEmpty()){
				return new Object[] { Constant.EMPTY_TAGS, "" };
			}else{
				map = userTags;
			}
		}else if(userRatio==0 && urlRatio!=0){
			if(urlTags==null || urlTags.isEmpty()){
				return new Object[] { Constant.EMPTY_TAGS, "" };
			}else{
				map = urlTags;
			}
		}else if(userRatio!=0 && urlRatio!=0){
			HashSet<String> keys = new HashSet<String>();
			if(userTags!=null && !userTags.isEmpty()){
				keys.addAll(userTags.keySet());
			}
			if(urlTags!=null && !urlTags.isEmpty()){
				keys.addAll(urlTags.keySet());
			}
			if(keys.isEmpty()){
				return new Object[] { Constant.EMPTY_TAGS, "" };
			}
			map = new HashMap<String,Float>();
			Iterator<String> keyIterator = keys.iterator();
			while(keyIterator.hasNext()){
				String key = keyIterator.next();
				float urlWeight=0f;
				float userWeight=0f;
				if(userTags!=null && userTags.containsKey(key)){
					userWeight= userTags.get(key)*userRatio;
				}
				if(urlTags!=null && urlTags.containsKey(key)){
					urlWeight=urlTags.get(key)*urlRatio;
				}
				if((urlWeight+userWeight)>0){
					map.put(key, (urlWeight+userWeight)/(userRatio+urlRatio));
				}
			}
			
		}
		String tag="";
		if(map!=null && !map.isEmpty()){
			if(num>-1){
				String[] keyArray =(String[]) map.keySet().toArray(new String[]{});
		        Float[] valueArray=(Float[]) map.values().toArray(new Float[]{});
		        int keyArrayLength=keyArray.length;
		        String key =null;
		        float v =0;
		        for(int i = 0;i<keyArrayLength;i++)
		        {
	                for(int j =0;j<keyArrayLength-i-1;j++)
	                {
                        float value1=valueArray[j];
                        float value2=valueArray[j+1];
                        if(value2>value1)
                        {
                            v=valueArray[j+1];
                            valueArray[j+1]=valueArray[j];
                            valueArray[j]=v;

                            key=(String)keyArray[j+1];
                            keyArray[j+1]=keyArray[j];
                            keyArray[j]=key;
                        }
	                }
		        }
		        tag = keyArray[0];
		        map = new HashMap<String,Float>();
		        for(int i=0;i<Math.min(num,keyArrayLength);i++){
		        	map.put(keyArray[i], valueArray[i]);
		        }
				/*
				List<Map.Entry<String, Float>> listData = new ArrayList<Map.Entry<String, Float>>(map.entrySet());
			    Collections.sort(listData,new Comparator<Map.Entry<String, Float>>(){  
			          public int compare(Map.Entry<String,Float> o1, Map.Entry<String,Float> o2)
			          {
						   if (o2.getValue() != null
								&& o1.getValue() != null
								&& o2.getValue().compareTo(o1.getValue()) > 0) {
				        	   return 1;
				           }else{
				        	   return -1;
				           }
			          }
			      });
			    tag = listData.get(0).getKey();
			    map = new HashMap<String,Float>();
			    for(int i=0;i<Math.min(listData.size(), num);i++){
			    	Map.Entry<String,Float> m = listData.get(i);
			    	map.put(m.getKey(), m.getValue());
			    }*/
			}else{
				float max=0f;
				for(Entry<String,Float> en:map.entrySet()){
					if(en.getValue().floatValue()>max){
						max=en.getValue().floatValue();
						tag = en.getKey();
					}
				}

			}
		    
		}
		return new Object[] { map, tag };
	}
	/**
	 * 对items中的推荐进行归一化处理
	 * 
	 * @param items
	 * @return
	 */
	public static Object[] getUnitaryAndTopWeight(
			Map<String, Float> items) {
		LinkedHashMap<String, Float> itemListTemp = new LinkedHashMap<String, Float>();
		ArrayList<String> topWeight = new ArrayList<String>();
		if (items != null && !items.isEmpty()) {
			/* 取得推荐商品列表中权重的总和 */
			float total = 0f;
			float max =0f;
			for (Entry<String, Float> entry : items.entrySet()) {
				total += entry.getValue();
				if(entry.getValue()>max){
					max = entry.getValue();
				}
			}

			/* 将所有商品的权重进行归一化处理,将它们的权重除以它们的权重总和 */
			for (Entry<String, Float> entry : items.entrySet()) {
				if(entry.getValue().floatValue()==max){
					topWeight.add(entry.getKey());
				}
				itemListTemp.put(entry.getKey(), entry.getValue() / total);
			}
		}
		return new Object[]{itemListTemp,topWeight};
	}
	
	 public final static String adFormat(String str, String u) {
	        StringBuilder sb = new StringBuilder("");
	        int j = 0;
	        int len = str.length();
	        for (; j < len - 2; j ++) {
	            if ('{' != str.charAt(j)) {
	                sb.append(str.charAt(j));
	            } else {
	                if (str.charAt(j + 1) == '0' && str.charAt(j + 2) == '}') {
	                    sb.append(u);
	                    j = j + 2;
	                } else {
	                    sb.append(str.charAt(j));
	                }
	            }
	        }
	        if (j < len)
	            sb.append(str.charAt(j ++));
	        if (j < len)
	            sb.append(str.charAt(j));
	        return sb.toString();
	    }
	    
	    public final static String adUnitFormat(String str, String[] params) {
	        StringBuilder sb = new StringBuilder("");
	        int j = 0;
	        int len = str.length();
	        int paramssize = params.length;
	        for (; j < len - 2; j ++) {
	            if ('{' != str.charAt(j)) {
	                sb.append(str.charAt(j));
	            } else {
	                boolean b2 = false;
	                boolean b3 = false;
	                if (str.charAt(j + 2) == '}') {
	                    b2 = true;
	                } else if (str.charAt(j + 3) == '}') {
	                    b3 = true;
	                }
	                if (b2) {
	                    int n = (int)str.charAt(j + 1) - 48;
	                    if (n >= 0 && n <= 9 && paramssize >= n) {
	                        sb.append(params[n]);
	                        j = j + 2;
	                    }
	                } else if (b3) {
	                    int n1 = (int)str.charAt(j + 1) - 48;
	                    int n2 = (int)str.charAt(j + 2) - 48;
	                    if (n1 >= 0 && n1 <=9 && n2 >= 0 && n2 <=9) {
	                        int n = n1 * 10 + n2;
	                        if (paramssize >= n) {
	                            sb.append(params[n]);
	                            j = j + 3;
	                        }
	                    }
	                } else {
	                    sb.append(str.charAt(j));
	                }
	            }
	        }
	        if (j < len)
	            sb.append(str.charAt(j ++));
	        if (j < len)
	            sb.append(str.charAt(j));
	        return sb.toString();
	    }
	
	    public static InetAddress getInetSocketAddress(boolean isLocalIP) {
			InetAddress result = null;
			try {
				Enumeration<NetworkInterface> netInterfaces = NetworkInterface
						.getNetworkInterfaces();
				InetAddress ip = null;
				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = (NetworkInterface) netInterfaces
							.nextElement();
					Enumeration<InetAddress> address = ni.getInetAddresses();
					while (address.hasMoreElements()) {
						ip = (InetAddress) address.nextElement();
						if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
								&& ip.getHostAddress().indexOf(":") == -1
								&& !isLocalIP) {// 外网IP
							result = ip;
							break;
						} else if (ip.isSiteLocalAddress()
								&& !ip.isLoopbackAddress()
								&& ip.getHostAddress().indexOf(":") == -1
								&& isLocalIP) {// 内网IP
							result = ip;
							break;
						}
					}
				}
			} catch (SocketException e) {
				e.printStackTrace();
			}
			return result;
		}

		public static InetAddress getInetSocketAddress(boolean isLocalIP,
				String specialIP) {
			InetAddress result = null;
			try {
				Enumeration<NetworkInterface> netInterfaces = NetworkInterface
						.getNetworkInterfaces();
				InetAddress ip = null;
				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = (NetworkInterface) netInterfaces
							.nextElement();
					Enumeration<InetAddress> address = ni.getInetAddresses();
					while (address.hasMoreElements()) {
						ip = (InetAddress) address.nextElement();
						if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
								&& ip.getHostAddress().indexOf(":") == -1
								&& !isLocalIP
								&& ip.getHostAddress().equals(specialIP)) {// 外网IP
							result = ip;
							break;
						} else if (ip.isSiteLocalAddress()
								&& !ip.isLoopbackAddress()
								&& ip.getHostAddress().indexOf(":") == -1
								&& isLocalIP
								&& ip.getHostAddress().equals(specialIP)) {// 内网IP
							result = ip;
							break;
						}
					}
				}
			} catch (SocketException e) {
				e.printStackTrace();
			}
			return result;
		}

		public static ArrayList<InetAddress> getInetSocketAddresses() {
			ArrayList<InetAddress> result = new ArrayList<InetAddress>();
			try {
				Enumeration<NetworkInterface> netInterfaces = NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = (NetworkInterface) netInterfaces
							.nextElement();
					Enumeration<InetAddress> address = ni.getInetAddresses();
					while (address.hasMoreElements()) {
						InetAddress ip = (InetAddress) address.nextElement();
						result.add(ip);
					}
				}
			} catch (SocketException e) {
				e.printStackTrace();
			}
			return result;
		}
	
/*		public static String Base64Encode(String str){
			if(str==null || str.isEmpty()){
				return "";
			}
			return new String(Base64.encodeBase64(str.getBytes()));
		}
		
		public static String Base64Decode(String str){
			if(str==null || str.isEmpty()){
				return "";
			}
			return new String(Base64.decodeBase64(str.getBytes()));
		}*/
	/**
	 * 取得SQL以?填值的动态参数名
	 * @param sql
	 * @return
	 */
	public static List<String> getDynamicVariable(String sql){
		ArrayList<String> list = null;
		if(sql!=null && !sql.isEmpty()){
			list = new ArrayList<String>();
			Matcher matcher = QUERY_PATTERN.matcher(sql);
			while(matcher.find()){
				int i=-1;
				i = matcher.group(0).lastIndexOf("=");
				if(i==-1){
					i = matcher.group(0).lastIndexOf("in");
				}
				if(i==-1){
					i = matcher.group(0).lastIndexOf("IN");
				}
				if(i==-1){
					i = matcher.group(0).lastIndexOf("<>");
				}
				if(i!=-1){
					list.add(matcher.group(0).substring(0,i).trim());
				}
			}
		}
		return list;
	}
	
/*	public static Map<String,Object[]> convertParamType(Map<String,String> map,Class<?> obj,List<Object> values,List<Type> types){
		Map<String,Object[]> params = null;
		if(map!=null && !map.isEmpty()){
			params = new HashMap<String,Object[]>();
			for(Entry<String,String> en:map.entrySet()){
				try {
					Field field = obj.getDeclaredField(en.getKey());
					params.put(en.getKey(),convertType(field.getType(),en.getValue(),values,types));
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
		}
		return params;
	}*/
	
	
	/*public static Object[] convertType(Class<?> type,String value,List<Object> values,List<Type> types){
		Object[] obj =null;
		if(value!=null){
				String[] vs = null;
				if(value.indexOf(",")>-1){
					vs = value.split(",");
				}
				if(type==Long.class || type==long.class){
					value = value.trim().isEmpty()?"0":value;
					if(vs!=null){
						for(int i=0;i<vs.length;i++){
							values.add(Long.parseLong(vs[i]));
							types.add(new org.hibernate.type.LongType());
						}
					}else{
						values.add(Long.parseLong(value));
						types.add(new org.hibernate.type.LongType());
					}
				}else if(type==Double.class || type==double.class){
					value = value.trim().isEmpty()?"0":value;
					if(vs!=null){
						for(int i=0;i<vs.length;i++){
							values.add(Double.parseDouble(vs[i]));
							types.add(new org.hibernate.type.DoubleType());
						}
					}else{
						values.add(Double.parseDouble(value));
						types.add(new org.hibernate.type.DoubleType());
					}
				}else if(type==Integer.class || type==int.class){
					value = value.trim().isEmpty()?"0":value;
					if(vs!=null){
						for(int i=0;i<vs.length;i++){
							values.add(Integer.parseInt(vs[i]));
							types.add(new org.hibernate.type.IntegerType());
						}
					}else{
						values.add(Integer.parseInt(value));
						types.add(new org.hibernate.type.IntegerType());
					}
				}else if(type==Float.class || type==float.class){
					value = value.trim().isEmpty()?"0":value;
					if(vs!=null){
						for(int i=0;i<vs.length;i++){
							values.add(Float.parseFloat(vs[i]));
							types.add(new org.hibernate.type.FloatType());
						}
					}else{
						values.add(Float.parseFloat(value));
						types.add(new org.hibernate.type.FloatType());
					}
				}else if(type==String.class){
					if(vs!=null){
						for(int i=0;i<vs.length;i++){
							values.add(vs[i]);
							types.add(new org.hibernate.type.StringType());
						}
					}else{
						values.add(value);
						types.add(new org.hibernate.type.StringType());
					}
				}else if(type==Boolean.class || type==boolean.class){
					value = value.trim().isEmpty()?"false":value;
					if(vs!=null){
						for(int i=0;i<vs.length;i++){
							values.add(Boolean.parseBoolean(vs[i]));
							types.add(new org.hibernate.type.BooleanType());
						}
					}else{
						values.add(Boolean.parseBoolean(value));
						types.add(new org.hibernate.type.BooleanType());
					}
				}
		}
		return obj;
	}
	*/
	/*
	public static Set<String> languageSet(String languages){
		HashSet<String> lang = new HashSet<String>();
		if(languages!=null && !languages.isEmpty()){
			Matcher matcher = LANG_PATTERN.matcher(languages);
			while(matcher.find()){
			   String l = matcher.group(0);
			   String[] ls =l.split("-");
			   lang.add(ls[0].toUpperCase());
			}
		}
		return lang;
	}
	*/
	public static Set<String> languageSet(String languages){
		HashSet<String> lang = new HashSet<String>();
		if(languages!=null && !languages.isEmpty()){
			Matcher matcher = LANG_PATTERN.matcher(languages);
			while(matcher.find()){
			   String l = matcher.group(0);
			   if(l.indexOf("-")!=-1){
				   lang.add((l.substring(0,l.indexOf("-"))).toUpperCase());
			   }else{
				   lang.add(l.toUpperCase());
			   }
			}
		}
		return lang;
	}
	public static Object convertType(Class<?> type,String value){
		Object obj =null;
		if(value!=null){
				if(type==Long.class || type==long.class){
					value = value.trim().isEmpty()?"0":value;
					obj = Long.parseLong(value);
				}else if(type==Double.class || type==double.class){
					value = value.trim().isEmpty()?"0":value;
					obj =Double.parseDouble(value);
				}else if(type==Integer.class || type==int.class){
					value = value.trim().isEmpty()?"0":value;
					obj = Integer.parseInt(value);
				}else if(type==Float.class || type==float.class){
					value = value.trim().isEmpty()?"0":value;
					obj = Float.parseFloat(value);
				}else if(type==String.class){
					obj = value;
				}else if(type==Boolean.class || type==boolean.class){
					value = value.trim().isEmpty()?"false":value;
					obj = Boolean.parseBoolean(value);
				}
		}
		return obj;
	}
	
	public static String toKey(String value) {
	    return String.valueOf(value.hashCode() & Integer.MAX_VALUE);
	}
	
	public static String getUrl(String url) {
	    URL ref;
        try {
            ref = new URL(url);
            String referer = ref.getProtocol() + "://" + ref.getHost() + ref.getPath();
            referer=referer.toLowerCase(Locale.US);
            return referer;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return url;
        }
    }
	
	public static String[] templateResolve(String template){
		String[] result=null;
		if(template!=null && !template.isEmpty()){
			String[] res=template.split(Constant.TEMPLATE_REG);
//			Matcher matcher =TEMPLATE_PATTERN.matcher(template);
			Matcher end = TEMPLATE_PATTERN_END.matcher(template);
			if(end.find()){
				result = new String[res.length+1];
				System.arraycopy(res, 0, result, 0, res.length);
				result[result.length-1]="";
			}else{
				result = res;
			}
		}
		return result;
	}
	
    /**
     * 从JCS&REDIS从取得用户或URL的TAG
     * @param region
     * @param key:publisher的用户会话ID或referer的hashcode
     * @return
     */
	/*public static long getTags(String region,String key,DeliveryVO delivery,boolean isUser,HashMap<String,Float> temp){
		long s=0l;
//		long e1=System.nanoTime();
    	try{
	    	byte[] ubyte = (byte[])CacheUtil.getInstance().get(region,key);
//	    	logger.info("USER:"+isUser+"  CACHE="+(System.nanoTime()-e1)+"; ");
	    	if(ubyte!=null){
//	    		e1=System.nanoTime();
	    		MFPThirdPartyInfo userTag = MFPThirdPartyInfo.parseFrom(ubyte);
	    		if(userTag.getTagsCount()>0){
	    			List<Content> list = userTag.getTagsList();
	    			Iterator<Content> it = list.iterator();
	    			while(it.hasNext()){
	    				Content c = it.next();
	    				String k =ByteString.copyFrom(c.getTag(), "utf-8").toStringUtf8();
	    				temp.put(k, c.getWeight());
	    			}
	    		}
	    		if(isUser){
	    			delivery.directionals[3] = userTag.getIncome()==0?Constant.LONG_ALL_BIT_ONE:userTag.getIncome();
	    			delivery.directionals[4] = userTag.getAge()==0?Constant.LONG_ALL_BIT_ONE:userTag.getAge();
	    			delivery.directionals[5] =userTag.getGender()==0?Constant.LONG_ALL_BIT_ONE:userTag.getGender();
	    			delivery.directionals[6] = userTag.getOccupation()==0?Constant.LONG_ALL_BIT_ONE:userTag.getOccupation();
	    			delivery.directionals[7] = userTag.getCustomer1()==0?Constant.LONG_ALL_BIT_ONE:userTag.getCustomer1();
	    			delivery.directionals[8] = userTag.getCustomer2()==0?Constant.LONG_ALL_BIT_ONE:userTag.getCustomer2();
	    			delivery.directionals[9] = userTag.getCustomer3()==0?Constant.LONG_ALL_BIT_ONE:userTag.getCustomer3();
	    			for(int i=0;i<10;i++)
	    			{
	    				logger.info("directionals="+delivery.directionals[i]);
	    			}
	    		}
//	    		logger.info("CONVERT="+(System.nanoTime()-e1)+"; ");
			}else{
				ContentDirectionalAuxiliary auxiliary = ContentDirectionalAuxiliary.getInstance();
				if(isUser){
					if(key!=null){
						auxiliary.addUser(key);
					}
					delivery.directionals[3] = Constant.LONG_NULL_CONDITION;
	    			delivery.directionals[4] = Constant.LONG_NULL_CONDITION;
	    			delivery.directionals[5] = Constant.LONG_NULL_CONDITION;
	    			delivery.directionals[6] = Constant.LONG_NULL_CONDITION;
	    			delivery.directionals[7] = Constant.LONG_NULL_CONDITION;
	    			delivery.directionals[8] = Constant.LONG_NULL_CONDITION;
	    			delivery.directionals[9] = Constant.LONG_NULL_CONDITION;
	    			for(int i=0;i<10;i++)
	    			{
	    				logger.info("directionals="+delivery.directionals[i]);
	    			}
				}else{
					if(delivery.referer!=null){
						auxiliary.addURL(delivery.referer);
					}
				}
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
//    	if(logger.isDebugEnabled()){
//    		s=System.nanoTime()-s;
//    	}
    	return s;
    }*/
	/*public static String adTemplate(String str){
		Matcher matcher = Utils.AD_TEMPLATE_PATTERN.matcher(str);
		Map<String,String> map = new HashMap<String,String>();
		while(matcher.find()){
			String t = matcher.group(0);
			String v = t.substring(5);
			v = v.substring(0,v.length()-1);
			v = DefineConfig.getInstance().getDefineData(Constant.JUMP_URL,"http://www.test.com")
					+ "?"
					+ Constant.URL_UUID_KEY
					+ "="
					+ Constant.MT_SPLIT
							+ "&"
							+ Constant.URL_LANDINGPAGEURL_NAMED
							+ "="
							+ new String(Base64.encodeBase64(v.getBytes()));
			map.put(t,v);
		}
		for(Entry<String,String> en:map.entrySet()){
			str =str.replace(en.getKey(), en.getValue());
		}
		ArrayList<String> assists = new ArrayList<String>();
		ArrayList<String> ids = new ArrayList<String>();
		while(str.indexOf(Constant.MT_SPLIT)>-1){
			int index=str.indexOf(Constant.MT_SPLIT);
			 if(index>0){
				 assists.add(str.substring(0,index));
				 ids.add("X");
			 }else{
				 assists.add("");
				 ids.add("X");
			 }
			 str = str.substring(index+Constant.MT_SPLIT.length());
		}
		ids.add("");
		assists.add(str);
		String[] other = new String[1];
		String[] uuid= new String[1];
		other = assists.toArray(other);
		uuid = ids.toArray(uuid);
		Map<String,String[]> result = new HashMap<String,String[]>();
		result.put(Constant.AD_TEMPLATE_ASSISTS_KEY,other);
		result.put(Constant.AD_TEMPLATE_IDS_KEY, uuid);
		JSONObject obj =JSONObject.fromObject(result);
		return obj.toString();
	}
	*/
/*	public static String adUnitTemplate(String str,Integer adNumber) throws Exception{
		Matcher matcher = TEMPLATE_PATTERN.matcher(str);
		HashMap<String,String> map = new HashMap<String,String>();
		ArrayList<String>   order =new ArrayList<String>();
		while(matcher.find()){
			String s=matcher.group();
			String v =s.replace("~{", "");
			v = v.replace("}","");
			map.put(s,Constant.MT_SPLIT);
			order.add(v);
		}
		for(Entry<String,String> en:map.entrySet()){
			str = str.replace(en.getKey(), en.getValue());
		}
		System.out.println(str);
		ArrayList<String> assists = new ArrayList<String>();
		ArrayList<String> ids = new ArrayList<String>();
		if(str.indexOf(Constant.MT_SPLIT)>-1){
			while(str.indexOf(Constant.MT_SPLIT)>-1){
				int index=str.indexOf(Constant.MT_SPLIT,0);
				 if(index>0){
					 assists.add(str.substring(0,index));
				 }else{
					 assists.add("");
				 }
				 str = str.substring(index+Constant.MT_SPLIT.length());
			}
			if(!str.isEmpty()){
			assists.add(str);
			order.add("-1");
			}
			
		}else{
			order.add("0");
			assists.add(str);
		}
		boolean ok=true;
		ArrayList<String> indexes = new ArrayList<String>();
		for(int i=0;i<adNumber;i++){
			indexes.add(String.valueOf(i));
		}
		for(String index:indexes){
			if(!order.contains(index)){
					ok=false;
					break;
			}
		}
		if(!ok){
			throw new Exception("ADNUMBER AND TEMPLATE DOES NOT MATCH ");
		}
		String[] other = new String[1];
		String[] orders =new String[1];
		other = assists.toArray(other);
		orders = order.toArray(orders);
		
		
		Map<String,String[]> result =new HashMap<String,String[]>();
		result.put(Constant.ADUNIT_TEMPLATE_ASSISTS_KEY, other);
		result.put(Constant.ADUNIT_TEMPLATE_ORDER_KEY, orders);
		JSONObject obj = JSONObject.fromObject(result);
		return obj.toString();
	}*/
	
/*	public static String[] lineItemFiles(String path,long timethreshold){
		String[] names=null;
		File dir = new File(path);
		if(dir.exists() && dir.isDirectory()){
			File[] files = dir.listFiles(new AdFileFilter(timethreshold));
			if(files!=null){
				names = new String[files.length];
				for(int i=0;i<files.length;i++){
					names[i]=files[i].getName().replace(Constant.LINEITEM_INDEX_FILE_SUFFIX,"");
				}
				Arrays.sort(names);
			}
		}
		return names;
	}*/
	
/*	public static File getRecentFile(String path,String type,long timethreshold){
		File f=null;
		String[] times=lineItemFiles(path,timethreshold);
		if(times!=null && times.length>0){
			for(int i=times.length-1;i>=0;i--){
				File ad = new File(path+File.separator+times[i]+Constant.AD_INDEX_FILE_SUFFIX);
				File adunit =new File(path+File.separator+times[i]+Constant.ADUNIT_INDEX_FILE_SUFFIX);
				if(ad.exists() && adunit.exists()){
					if(Constant.AD_INDEX_FILE_SUFFIX.equals(type)){
						f = ad;
					}else if(Constant.ADUNIT_INDEX_FILE_SUFFIX.equals(type)){
						f=adunit;
					}else if (Constant.LINEITEM_INDEX_FILE_SUFFIX.equals(type)){
						f = new File(path+File.separator+times[i]+Constant.LINEITEM_INDEX_FILE_SUFFIX);
					}
					break;
				}
			}
		}
		return f;
	}*/
	
	/*public static void impressionLogPrex(StringBuilder logSb,DeliveryVO delivery,String uuid,String timestamp,String adUnitId){
		//时间,地域,频道,页面,广告位,UUID,用户ID,浏览器,语言,频道,收入,年龄,性别,职业,自定义1,自定义2,自定义3
				BitSet paramLog=DefineConfig.param;
				if(paramLog.get(0)){
					logSb.append("TP=").append(timestamp).append("\t");
				}
				IpInfo info =delivery.ipInfo;
				if(paramLog.get(1)){
					logSb.append("ZE=").append(info.getCountry()).append("-").append(info.getProvince()).append("-").append(info.getCity()).append("\t");
				}
				
				if(paramLog.get(2)){
					logSb.append("CH=").append(delivery.channel).append("\t");
				}
				
				if(paramLog.get(3)){
					logSb.append("RE=").append(delivery.referer).append("\t");
				}
				
				if(paramLog.get(4)){
					logSb.append("AU=").append((adUnitId==null?delivery.adUnitId:adUnitId)).append("\t");
				}
				
				if(paramLog.get(5)){
					logSb.append("UU=").append(uuid).append("\t");
				}
				if(paramLog.get(6)){
					logSb.append("PD=").append(delivery.publisherSessionId).append("\t");
				}
				
				if(paramLog.get(7)){
					logSb.append("BR=").append(delivery.directionals[0]).append("\t");
				}
				if(paramLog.get(8)){
					logSb.append("LA=").append(delivery.directionals[1]).append("\t");
				}
				if(paramLog.get(9)){
					logSb.append("CE=").append(delivery.directionals[2]).append("\t");
				}
				if(paramLog.get(10)){
					logSb.append("IN=").append(delivery.directionals[3]).append("\t");
				}
				if(paramLog.get(11)){
					logSb.append("AG=").append(delivery.directionals[4]).append("\t");
				}
				if(paramLog.get(12)){
					logSb.append("GE=").append(delivery.directionals[5]).append("\t");
				}
				if(paramLog.get(13)){
					logSb.append("OC=").append(delivery.directionals[6]).append("\t");
				}
				if(paramLog.get(14)){
					logSb.append("C1=").append(delivery.directionals[7]).append("\t");
				}
				if(paramLog.get(15)){
					logSb.append("C2=").append(delivery.directionals[8]).append("\t");
				}
				if(paramLog.get(16)){
					logSb.append("C3=").append(delivery.directionals[9]).append("\t");
				}
		
	}
	
	public static void impressionLogOutput(DeliveryVO delivery){
		//时间,地域,频道,页面,广告位,UUID,用户ID,浏览器,语言,频道,收入,年龄,性别,职业,自定义1,自定义2,自定义3
		if(!delivery.impressionLogMap.isEmpty()){
			for(Entry<String,ImpressionLogVO> en:delivery.impressionLogMap.entrySet()){
				StringBuilder logSb = new StringBuilder(200);
				if(DefineConfig.TP){
					logSb.append("TP=").append(delivery.timestamp).append("\t");
				}
				IpInfo info =delivery.ipInfo;
				if(DefineConfig.ZE){
					logSb.append("ZE=").append(info.getCountry()).append("-").append(info.getProvince()).append("-").append(info.getCity()).append("\t");
				}
				if(DefineConfig.CH){
					logSb.append("CH=").append(delivery.channel).append("\t");
				}
				if(DefineConfig.RE){
					logSb.append("RE=").append(delivery.referer).append("\t");
				}
				if(DefineConfig.PD){
					logSb.append("PD=").append(delivery.publisherSessionId).append("\t");
				}
				if(DefineConfig.BR){
					logSb.append("BR=").append(delivery.directionals[0]).append("\t");
				}
				if(DefineConfig.LA){
					logSb.append("LA=").append(delivery.directionals[1]).append("\t");
				}
				if(DefineConfig.CE){
					logSb.append("CE=").append(delivery.directionals[2]).append("\t");
				}
				if(DefineConfig.IN){
					logSb.append("IN=").append(delivery.directionals[3]).append("\t");
				}
				if(DefineConfig.AG){
					logSb.append("AG=").append(delivery.directionals[4]).append("\t");
				}
				if(DefineConfig.GE){
					logSb.append("GE=").append(delivery.directionals[5]).append("\t");
				}
				if(DefineConfig.OC){
					logSb.append("OC=").append(delivery.directionals[6]).append("\t");
				}
				if(DefineConfig.C1){
					logSb.append("C1=").append(delivery.directionals[7]).append("\t");
				}
				if(DefineConfig.C2){
					logSb.append("C2=").append(delivery.directionals[8]).append("\t");
				}
				if(DefineConfig.C3){
					logSb.append("C3=").append(delivery.directionals[9]).append("\t");
				}
				if(DefineConfig.AU){
					logSb.append("AU=").append(en.getKey()).append("\t");
				}
				if(DefineConfig.UU){
					logSb.append("UU=").append(en.getValue().uuid==null?"":en.getValue().uuid).append("\t");
				}
				ArrayList<SubLog> subs = en.getValue().sub;
				if(subs!=null && !subs.isEmpty()){
					for(SubLog log:subs){
						logSb.append("{");
						{
							//时间、地域、频道、页面、广告位、UUID、用户ID、自定义定向条件、{UUID、订单项、广告、展示价格、点击价格}
							if(DefineConfig.D_UU){
								logSb.append("UU=").append( log.subUuid).append(" ");
							}
							if(DefineConfig.D_LT){
								logSb.append("LT=").append(log.lineItemId).append(" "); // 订单项计数
							}
							if(DefineConfig.D_AD){
								logSb.append("AD=").append(log.adId).append(" "); // 广告创意计数
							}
							if(DefineConfig.D_PR){
								logSb.append("PR=").append(log.price).append(" ");
							}
						}
						logSb.append("}").append("\t");
					}
				}
				logSb.append("\n");
				mfpinfolog.info(logSb.toString());
			}
		}
	}*/
	public static void main(String[] a) throws Exception{
//		String sql="aaa";
//		String sql="{url:http://www.1.com.cn}<a href='{url:http://aa可ad.可了.com.cn}&B=B1&C=C1'>1111</a><a href='{url:http://www.3.com.cn}&B=B1&C=C1'>2222</a>{url:http://www.4.com.cn}";
//		System.out.println(adTemplate(sql));
		String sql="[~{1}aaaa~{0}bbbb~{1}cccc~{2}gggg~{2}~{0}]";
		//String str =adUnitTemplate(sql,1);
		//System.out.println(str);
	}
}
