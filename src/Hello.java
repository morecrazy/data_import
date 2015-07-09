import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.util.HotSwapper;


public class Hello {
    public void say() {
        System.out.println("Hello");
    }

}
class Test {
    public static void main(String[] args) throws Exception {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("Hello");
        CtMethod m = cc.getDeclaredMethod("say");
        m.insertBefore("{ System.out.println(\"Hello.say():\"); }");
        try{
        	//HotSwapper swapper=new HotSwapper(8000);
        
            //swapper.reload("Hello",cc.toBytecode());
        }
        catch(RuntimeException ex){
            if(ex.getMessage().toUpperCase().contains("NO SUCH CLASS")){
                cc.toClass();
            }
        }
        catch(Exception e)
        {
        	
        }
        Hello hello=new Hello();
        hello.say();
    }
}
