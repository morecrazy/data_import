package com.sina.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.mtarget.proto.data.MFPThirdPartyInfoProto.Content;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.MFPThirdPartyInfo;
@SuppressWarnings("unused")
public class UserTagImportMap extends Mapper<LongWritable, Text, Text, Text> {
	
	protected void setup(Context context) throws IOException,
			InterruptedException {
		super.setup(context);
		Configuration conf = context.getConfiguration();
	}

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		if(value!=null){
			String line = value.toString().trim();
			//System.out.println(line);
			if (!"".equals(line)) {
				String[] values = line.split("\t");
				if(values!=null && values.length>0){
					String userId = values[0];
					Text ui = new Text(userId);
					for(int i=1;i<values.length;i++){
						Text ut = new Text(values[i]);
						context.write(ui, ut);
					}
//					Put put = new Put(userId.getBytes());
//					put.add(Bytes.toBytes("ID"),Bytes.toBytes(""),Bytes.toBytes(userId));
//					put.add(Bytes.toBytes("DIRECTIONAL"),Bytes.toBytes(""),builder.build().toByteArray());
//					context.write(new ImmutableBytesWritable(userId.getBytes()), put);
				}
				
				
			}
		}
	}

}
