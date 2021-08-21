package reactive;

public class Consultant extends Employee {

	private int cid;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}
	
	public Consultant(int id, int cid, String name) {
		super(id, name);
		this.cid = cid;
	}
	
	@Override
	public String toString() {
		return "[id=" + super.getId() + ", name=" + super.getName() + ", cid=" + cid + "]";
	}
}