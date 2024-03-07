package per.owisho.learn.clone;

import org.junit.Test;

/**
 * Object 类相关方法测试类
 */
public class ObjectDemo {
	
	@Test
	public void shallowclone() throws CloneNotSupportedException{

		PersonShallowClone p = new PersonShallowClone(new Leg(10));
		Object o = p.clone();
		System.out.println(o.getClass());
		if(o instanceof PersonShallowClone){
			PersonShallowClone pc = PersonShallowClone.class.cast(o);
			pc.getLeg().setLength(100);
			System.out.println(p.getLeg().getLength());
		}
		
	}
	
	@Test
	public void deepclone() throws CloneNotSupportedException{
		PersonDeepClone p = new PersonDeepClone(new Leg(10));
		System.out.println(p.getLeg());
		Object o = p.clone();
		System.out.println(p.getLeg());
		System.out.println(o.getClass());
		if(o instanceof PersonDeepClone){
			PersonDeepClone pc = PersonDeepClone.class.cast(o);
			pc.getLeg().setLength(100);
			System.out.println(p.getLeg().getLength());
		}
	}
	
	@Test
	public void primitiveclassclone() throws CloneNotSupportedException{
		PrimitiveFieldClassClone p = new PrimitiveFieldClassClone();
		Object o = p.clone();
		if(o instanceof PrimitiveFieldClassClone){
			PrimitiveFieldClassClone pc = PrimitiveFieldClassClone.class.cast(o);
			pc.i = 2;
			pc.f = 2;
			System.out.println(p.i);
			System.out.println(p.f);
		}
		
	}
}
