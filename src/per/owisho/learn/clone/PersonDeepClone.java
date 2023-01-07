package per.owisho.learn.clone;

public class PersonDeepClone extends Ancestor implements Cloneable{

	private Leg leg;
	
	public PersonDeepClone(Leg leg){
		this.leg = leg;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		PersonDeepClone p = (PersonDeepClone) super.clone();
		p.setLeg((Leg)p.getLeg().clone());
		return p;
	}

	public Leg getLeg() {
		return leg;
	}

	public void setLeg(Leg leg) {
		this.leg = leg;
	}
	
}
