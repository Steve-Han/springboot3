package org.example.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @moudle: TenantVisitData
 * @version:v1.0
 * @Description: CBS带看信息实体
 * @author: zjshao
 * @date: 2020年05月19日 上午9:16:51
 */
public class TenantVisitData implements Serializable {

    /**
     * 城市
     */
    private String cityCode;

    /**
     * 分公司
     */
    private String subBizId;

    /**
     * 传输id
     */
    private String transmissionId;

    /**
     * 通信时间
     */
    private Date transmissionTime;

    /**
     * 接口编号
     */
    private String interfaceCode;

    /**
     * CBS带看单编号
     */
    private Long showingsId;

    /**
     * 业务类型 枚举：CBS、CRM  默认CBS
     */
    private String bizType;

    /**
     * 带看单信息
     */
    private List<ShowingInfo> showingsList;

    /**
     * 带看单状态:1:已取消 2:已超期 3:带看中 4：已反馈 5:意向中 6:已成交（CBS状态->AMS:已超期->已取消,）
     */
    private Long leadStatus;

    /**
     * 带看人
     */
    private String leadusersName;

    /**
     * 带看人编号
     */
    private Long leadusersId;

    /**
     * 陪看人
     */
    private String accompanyName;

    /**
     * 陪看人编号
     */
    private Long accompanyId;

    /**
     * 创建带看时间
     */
    private Date createTime;

    /**
     * 预计带看时间
     */
    private Date beginTime;

    /**
     * 实际带看时间
     */
    private Date endTime;

    /**
     * 租客姓名
     */
    private String customerName;

    /**
     * 租客手机号
     */
    private String customerMobile;

    /**
     * 客源编号
     */
    private String clientId;

    /**
     * 需求扩展编号
     */
    private Long customerDemandId;

    /**
     * 需求类型
     */
    private Long demandType;

    /**
     * 需求描述
     */
    private String demandTypeName;

    /**
     * 租客性别
     */
    private Long sex;

    /**
     * 客户来源
     */
    private String tenantSourceCode;

    /**
     * 操作类型
     */
    private Long operation;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSubBizId() {
        return subBizId;
    }

    public void setSubBizId(String subBizId) {
        this.subBizId = subBizId;
    }

    public String getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(String transmissionId) {
        this.transmissionId = transmissionId;
    }

    public Date getTransmissionTime() {
        return transmissionTime;
    }

    public void setTransmissionTime(Date transmissionTime) {
        this.transmissionTime = transmissionTime;
    }

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public Long getShowingsId() {
        return showingsId;
    }

    public void setShowingsId(Long showingsId) {
        this.showingsId = showingsId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public List<ShowingInfo> getShowingsList() {
        return showingsList;
    }

    public void setShowingsList(List<ShowingInfo> showingsList) {
        this.showingsList = showingsList;
    }

    public Long getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(Long leadStatus) {
        this.leadStatus = leadStatus;
    }

    public String getLeadusersName() {
        return leadusersName;
    }

    public void setLeadusersName(String leadusersName) {
        this.leadusersName = leadusersName;
    }

    public Long getLeadusersId() {
        return leadusersId;
    }

    public void setLeadusersId(Long leadusersId) {
        this.leadusersId = leadusersId;
    }

    public String getAccompanyName() {
        return accompanyName;
    }

    public void setAccompanyName(String accompanyName) {
        this.accompanyName = accompanyName;
    }

    public Long getAccompanyId() {
        return accompanyId;
    }

    public void setAccompanyId(Long accompanyId) {
        this.accompanyId = accompanyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getCustomerDemandId() {
        return customerDemandId;
    }

    public void setCustomerDemandId(Long customerDemandId) {
        this.customerDemandId = customerDemandId;
    }

    public Long getDemandType() {
        return demandType;
    }

    public void setDemandType(Long demandType) {
        this.demandType = demandType;
    }

    public String getDemandTypeName() {
        return demandTypeName;
    }

    public void setDemandTypeName(String demandTypeName) {
        this.demandTypeName = demandTypeName;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public Long getOperation() {
        return operation;
    }

    public void setOperation(Long operation) {
        this.operation = operation;
    }

    public String getTenantSourceCode() {
        return tenantSourceCode;
    }

    public void setTenantSourceCode(String tenantSourceCode) {
        this.tenantSourceCode = tenantSourceCode;
    }

}
