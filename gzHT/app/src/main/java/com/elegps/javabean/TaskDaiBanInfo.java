package com.elegps.javabean;

import java.io.Serializable;

public class TaskDaiBanInfo implements Serializable{


    /**
     * FlowNO : 20180424002
     * MachineNO :
     * CurNodeName : 阀板部件
     * CreateTime : 2018/4/24 17:35:27
     * StatusText : 生产中
     */

    private String FlowNO;
    private String MachineNO;
    private String CurNodeName;
    private String CreateTime;
    private String StatusText;

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

    public String getCurNodeName() {
        return CurNodeName;
    }

    public void setCurNodeName(String CurNodeName) {
        this.CurNodeName = CurNodeName;
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
}
