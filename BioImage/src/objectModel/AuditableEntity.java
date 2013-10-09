package objectModel;

import java.util.Date;

public class AuditableEntity extends BaseEntity implements Auditable {
	private String createdId = null, updateID = null;
	private Date createDate = null, updateDate = null;

	public AuditableEntity(){
		super();
	}
	@Override
	public String getCreatID() {
		return createdId;
	}

	@Override
	public void setCreateID(String createdId) {
		this.createdId = createdId;

	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;

	}

	@Override
	public String getUpdateId() {
		return updateID;
	}

	@Override
	public void setUpdateId(String updateId) {
		this.updateID = updateId;
	}

	@Override
	public Date getUpdateDate() {
		return updateDate;
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;

	}

}
