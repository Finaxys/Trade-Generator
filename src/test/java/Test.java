import junit.framework.TestCase;

public class Test extends TestCase
{
	public Test(String testMethodName)
	{
		super(testMethodName);
	}
	
	public void test() throws Exception
	{
		fail("test à échouer");
	}
}
//chargement des settings 
//création de trades