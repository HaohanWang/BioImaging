package objectModel;

import java.util.Date;

public class BaseEntity implements Identifiable {

	private long id;

	public BaseEntity(){
		Date date = new Date();
		this.id = date.getTime();
	}
	
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	
	public static void main(String[] args){
		BaseEntity be = new BaseEntity();
		System.out.println(be.getId());
	}

}
