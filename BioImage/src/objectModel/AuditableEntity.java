package objectModel;

import java.util.Date;

public class AuditableEntity extends BaseEntity implements Auditable {
    private String createdId = null, updateID = null;
    private Date createDate = null, updateDate = null;
    
    @Override
    public String getCreatID() {
      // TODO Auto-generated method stub
      
      return createdId;
    }
    @Override
    public void setCreatID(String creatID) {
      // TODO Auto-generated method stub
      this.createdId = createdId;
      
    }
    @Override
    public Date getCreatDate() {
      // TODO Auto-generated method stub
      return createDate;
    }
    @Override
    public void setCreatDate(Date creatDate) {
      // TODO Auto-generated method 
      this.createDate = createDate;
      
    }
    @Override
    public String getUpdateId() {
      // TODO Auto-generated method stub
      return updateID;
    }
    @Override
    public void setUpdateId(String updateId) {
      // TODO Auto-generated method stub
      this.updateID = updateId;
    }
    @Override
    public Date getUpdateDate() {
      // TODO Auto-generated method stub
      return updateDate;
    }
    @Override
    public void setUpdateDate(Date updateDate) {
      // TODO Auto-generated method stub
      this.updateDate = updateDate;
      
    }
    
}
