import static org.junit.Assert.*;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sina.imp.UserInfoJson;
import com.sina.proto.data.UserInfoProto.UserInfo;


public class TestJson {

	static ObjectMapper mapper =  new ObjectMapper();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String pros = "{\"dmp_age_list\":[{\"id\":\"603\",\"weight\":\"0.04\"},{\"id\":\"604\",\"weight\":\"0.01\"},{\"id\":\"601\",\"weight\":\"0.89\"},{\"id\":\"602\",\"weight\":\"0.05\"}],\"dmp_gender\":\"501\",\"dmp_interest_list\":[{\"id\":\"20631\",\"weight\":\"0.200000\"},{\"id\":\"20632\",\"weight\":\"0.200000\"},{\"id\":\"20633\",\"weight\":\"0.200000\"},{\"id\":\"20634\",\"weight\":\"0.200000\"},{\"id\":\"20639\",\"weight\":\"0.200000\"}]}";
		UserInfo.Builder builder = UserInfo.newBuilder();
		UserInfoJson json = null;
		try
		{
			json = mapper.readValue(pros, UserInfoJson.class); 
		}
		catch(Exception e)
		{
			System.out.println("ParseException: json format wrong " + pros);
		}
	}

}
