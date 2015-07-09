package com.sina.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImportMain {

	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			ImportLoadConfig.load(args[0]);
		} else {
			ImportLoadConfig.load(null);
		}
		InitRedisConfig.initConfig();
		int num = Integer.parseInt(ImportConstant.THRED_NUM);
		System.out.println(num);
		ExecutorService executor = Executors.newFixedThreadPool(num);
		File file = new File(ImportConstant.IMPORT_PATH);
		String[] filelist = null;
		if (file.isDirectory()) {
			filelist = file.list();
		} else {
			System.out.println(ImportConstant.IMPORT_PATH + ": is not path");
			return;
		}

		for (int i = 0; i < filelist.length; i++) {
			File f = new File(ImportConstant.IMPORT_PATH + "/" + filelist[i]);
			System.out.println("start import file : " + f.getName());
			try {

				FileReader fr = new FileReader(f);// 创建FileReader对象，用来读取字符流
				BufferedReader br = new BufferedReader(fr); // 缓冲指定文件的输入
				String myreadline; // 定义一个String类型的变量,用来每次读取一行
				int k = 0;
				while (br.ready()) {
					// long u1 = System.nanoTime();
					myreadline = br.readLine();// 读取一行
					executor.submit(new ImportThread(myreadline));
					if (++k % 5000 == 0) {

						// ImportConstant.sessionNum = ImportConstant.sessionNum
						// + 5000;
						sessionNum();
						System.out.println(k + " sessionNum:"
								+ ImportConstant.sessionNum);
						while (ImportConstant.sessionNum > 500000) {
							System.out.println("sleep 60 second");
							Thread.sleep(60000);

						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("finish import file : " + file.getName());
		}
		executor.shutdown();

	}

	public static void sessionNum() {
		synchronized (ImportConstant.test) {
			ImportConstant.sessionNum = ImportConstant.sessionNum + 5000;
		}
	}

}
