package UDF;
//import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;

public class SplitStringNum extends UDF
{

    public SplitStringNum()
    {
    }

    public String evaluate(String value, String split,int num)
    {
    	if(value!=null&&!value.equals(""))
    	{
	        String values[] = value.split(split);
	        if(values.length>=num)
	        {
	        	return values[num-1];
	        }
	        else return "";
    	}else return "";

        
    }

    public static void main(String args[])
    {
        //SplitString dd = new SplitString();
        //System.out.println(dd.evaluate("sprappkey:2|invite_codereg_src:1239875|", "invite_codereg_src"));
    }
}

