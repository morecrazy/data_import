package com.sina.test;

import com.sina.imp.ImportThread;

public class TestJson {
	public static void main(String[] args)
	{
        String json = "00000000.1dbe62a5.51027488.c3fe7487	{\"age_list\":[{\"id\":\"606\",\"weight\":\"0.999007\"},{\"id\":\"602\",\"weight\":\"0.222061\"}]," +
        		"\"gender\":\"502\",\"it_list\": [{\"id\":\"21061\",\"weight\":\"0.012427374\"},{\"id\":\"21060\",\"weight\":\"1.3164872E-4\"}]}";
        
        ImportThread t = new ImportThread(json);
        t.call();
        
	}


}

