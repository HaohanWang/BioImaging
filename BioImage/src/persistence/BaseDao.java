package persistence;

import java.util.ArrayList;

public class BaseDao<B> implements BaseDaoInterface<B> {
	public DataBaseConnection database;

	@Override
	public B getNewInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<B> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void save(B instance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(B insatnce) {
		// TODO Auto-generated method stub

	}

}
