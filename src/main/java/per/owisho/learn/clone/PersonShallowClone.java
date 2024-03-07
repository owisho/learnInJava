package per.owisho.learn.clone;

public class PersonShallowClone extends Ancestor implements Cloneable{

	private Leg leg;
	
	public PersonShallowClone(Leg leg){
		this.leg = leg;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Leg getLeg() {
		return leg;
	}

	public void setLeg(Leg leg) {
		this.leg = leg;
	}

}

