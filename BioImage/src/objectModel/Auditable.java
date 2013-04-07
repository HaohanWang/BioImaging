package objectModel;

import java.util.Date;

public interface Auditable {

	public String getCreatID();

	public void setCreatID(String creatID);

	public Date getCreatDate();

	public void setCreatDate(Date creatDate);

	public String getUpdateId();

	public void setUpdateId(String updateId);

	public Date getUpdateDate();

	public void setUpdateDate(Date updateDate);
}
