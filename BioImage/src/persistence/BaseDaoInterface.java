package persistence;

import java.util.ArrayList;

public interface BaseDaoInterface<B> {
  public B getNewInstance();

  public B getById(long id);

  public ArrayList<B> getAll();

  public int getCount();

  public void save(B instance);

  public void delete(B insatnce);
}
