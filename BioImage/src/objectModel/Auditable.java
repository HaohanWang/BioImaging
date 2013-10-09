package objectModel;

import java.util.Date;

public interface Auditable {

	public String getCreatID();

	public void setCreateID(String creatID);

	public Date getCreateDate();

	public void setCreateDate(Date creatDate);

	public String getUpdateId();

	public void setUpdateId(String updateId);

	public Date getUpdateDate();

	public void setUpdateDate(Date updateDate);
}
