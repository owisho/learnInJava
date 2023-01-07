package per.owisho.learn.clone;

public class Leg implements Cloneable{

	private double length;
	
	public Leg(double length){
		this.length = length;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
