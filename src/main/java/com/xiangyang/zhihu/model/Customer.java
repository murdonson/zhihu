package com.xiangyang.zhihu.model;

public class Customer {

    private String cName;

    private String SimpleName;

    private String Trade;

    private String Source;

    private String Address;
    private String Remark;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getSimpleName() {
        return SimpleName;
    }

    public void setSimpleName(String simpleName) {
        SimpleName = simpleName;
    }

    public String getTrade() {
        return Trade;
    }

    public void setTrade(String trade) {
        Trade = trade;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
