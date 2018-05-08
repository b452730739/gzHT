package com.elegps.javabean;

public class TaskInfo {


    /**
     * Seq : 1
     * TaskID : ef6b1cbb-7f3c-4e71-a105-1d89dbdee823
     * FlowNO : 20180424002
     * MachineNO : 888888888
     * MachineModel : MA1200ⅡS
     * CreateTime : 2018/4/24 14:29:10
     * StatusText : 生产中
     * Remark :
     */

    private String Seq;
    private String TaskID;
    private String FlowNO;
    private String MachineNO;
    private String MachineModel;
    private String CreateTime;
    private String StatusText;
    private String Remark;

    public String getSeq() {
        return Seq;
    }

    public void setSeq(String Seq) {
        this.Seq = Seq;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public String getFlowNO() {
        return FlowNO;
    }

    public void setFlowNO(String FlowNO) {
        this.FlowNO = FlowNO;
    }

    public String getMachineNO() {
        return MachineNO;
    }

    public void setMachineNO(String MachineNO) {
        this.MachineNO = MachineNO;
    }

    public String getMachineModel() {
        return MachineModel;
    }

    public void setMachineModel(String MachineModel) {
        this.MachineModel = MachineModel;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getStatusText() {
        return StatusText;
    }

    public void setStatusText(String StatusText) {
        this.StatusText = StatusText;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
}
