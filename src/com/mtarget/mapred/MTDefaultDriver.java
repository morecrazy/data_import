package com.mtarget.mapred;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public abstract class MTDefaultDriver implements Tool {

	protected Configuration conf;

	public void run() throws Exception {
		ToolRunner.run(this.getConf(), this, null);
	}

	public Scan getColumnScan(String... columns) {
		Scan scan = new Scan();
		for (String col : columns) {
			scan.addFamily(Bytes.toBytes(col));
		}
		return scan;
	}

	public Scan getColumnScan(String columns) {
		Scan scan = new Scan();
		String[] cols = columns.split(" ");
		for (String col : cols) {
			scan.addFamily(Bytes.toBytes(col));
		}
		return scan;
	}

	@Override
	public Configuration getConf() {
		if (this.conf == null) {
			this.conf = new Configuration();

		}
		return this.conf;
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf != null ? conf : new Configuration();
	}
}
