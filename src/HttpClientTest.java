

import java.lang.reflect.Method;
import java.util.HashMap;

import org.lilystudio.httpclient.GetMethod;
import org.lilystudio.httpclient.HttpClient;

public class HttpClientTest {

	public static void main(String[] args) throws Exception {


/*		while(true)
		{
			   try {    
				    // 每次都创建出一个新的类加载器   
				   String path = Class.class.getClass().getResource("/").getPath();

				   CustomCL cl = new CustomCL(path, new String[]{"EchoImpl"});    
				   Class cls = cl.loadClass("EchoImpl");    
				   Echo echo = (Echo)cls.newInstance(); 
				   
				     
				   //Method m = echo.getClass().getMethod("echo", new Class[]{});    
				   //m.invoke(echo, new Object[]{});    
				   echo.echo();   
				   
				   EchoImpl i = new EchoImpl();
				   i.echo();
				    }  catch(Exception ex) {    
				           ex.printStackTrace();    
				    }    

			Class c = Class.forName("ClassLoadTest");
			//ClassLoadTest.class.getClassLoader().getParent().loadClass("ClassLoadTest");
			ClassLoadTest test = (ClassLoadTest)c.newInstance();
			//test.echo();
			Thread.sleep(10000);
		}*/
			for (int i = 0; i < 1000; i++) {
			GetMethod method = new GetMethod(
					//"http://ads.ad.sina.com.cn/exp?adunitid=PDPS000000017957&TIMESTAMP="+System.nanoTime()+"&referral=http%3A%2F%2Fnews.sina.com.cn%2Fxhtml%2F792%2F2012-07-19%2F36.html");
					"http://10.79.96.30/flash_cookie?localStorage?adunitid=PDPS000000017957&TIMESTAMP="+System.nanoTime()+"&referral=http%3A%2F%2Fnews.sina.com.cn%2Fxhtml%2F792%2F2012-07-19%2F36.html");
/*			method.setRequestHeader("Host", "ads.ad.sina.com.cn");
			method.setRequestHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.0; WOW64; rv:13.0) Gecko/20100101 Firefox/13.0.1");*/
			//method.setRequestHeader("Accept", "*/*");
/*			method.setRequestHeader("Accept-Language",
					"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			method.setRequestHeader("Accept-Encoding", "gzip, deflate");
			method.setRequestHeader("Referer",
					"http://news.sina.com.cn/xhtml/792/2012-07-19/36.html");
			method.setRequestHeader(
					"Cookie",
					"U_TRS1=000000c3.d5115671.50090d90.873470f0; U_TRS2=000000c3.d51c5671.50090d90.eca22c5d; UOR=,news.sina.com.cn,; Apache=1051128134329.3821.1342770546441; SINAGLOBAL=1051128134329.3821.1342770546441; ULV=1342770547138:1:1:1:1051128134329.3821.1342770546441:; vjuids=59b96e4b.138a35c9c56.0.04456dd9e61ce; vjlast=1342770552; _s_upa=2");
*/			HttpClient client = new HttpClient();

			long t = System.nanoTime();
			client.execute(method);

			System.out.println(((System.nanoTime() - t)/1000000) + "(" + i + "):"
					+ new String(client.getResponseBody()));
			
			Thread.sleep(1000);
		}
		
		
/*		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("test", 68);
		map.get("test").intValue();*/
		
	}
}
