package com.sina.website;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import com.mtarget.mapred.Constant;
import com.sina.util.MurmurHash3;

public class UserTagMap extends Mapper<LongWritable, Text, Text, Text>{
	protected Logger logger = Logger.getLogger(UserTagMap.class);
	public String PATTERN_STRING = "http://.*/2012[0-9-/]{4,6}/[0-9]{7,}\\.(html|shtml)";
	private Pattern p = Pattern.compile(PATTERN_STRING);
	@SuppressWarnings("unchecked")
	protected void setup(Context context) throws IOException,InterruptedException {
			super.setup(context);
			Configuration conf = context.getConfiguration();
			FileSystem fs = FileSystem.get(conf);
			PATTERN_STRING = conf.get("PATTERN_STRING");
			 p = Pattern.compile(PATTERN_STRING);
	}

	public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {
		if(value!=null){
			String line = value.toString().trim();
			if (!"".equals(line)) {
				String[] values = line.split("\t");
				if(values!=null && values.length>18){
					String memberId = "";
					if(values[18]!=null)
					{
						String s[] = values[18].split(":");
						if(s!=null&&s.length>2)
						{
							memberId = values[18].split(":")[2];
						}
						
					}
					String userId = values[19];
					String url= values[27];
					
					
					//for(int i=1;i<values.length;i++){
					 Matcher m = p.matcher(url);
					if(m.matches())
					{
						url = MurmurHash3.Hex_MurmurHash3_x64_128(url.getBytes(),url.length());
						Text ut = new Text(new StringBuilder(userId).append(":").append(url).append(":").append(memberId).toString());
						
						Text ui = new Text(userId);
						context.write(ui, ut);
					}
				}	
			}
		}
	}
		

}
