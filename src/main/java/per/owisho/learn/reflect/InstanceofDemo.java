package per.owisho.learn.reflect;

import org.junit.Test;

public class InstanceofDemo{

	@Test
	public void primativeClass(){
		int i = 1;
		String s = "1";
		Object o = i;
		System.out.println(o instanceof Integer);
		System.out.println(s instanceof String);
	}
	
}
