
package org.example.dto;

/**
 * work列表请求入参
 * @Description: work列表请求入参
 * @author: hanyu
 * @date: 2023-12-21
 *
 */
public class CRMWorkTakeOrderListRequest {
    /**
     * 城市
     */
    private String cityCode;
    /**
     * 分公司
     */
    private String subBizId;
    /**
     * 传输ID
     */
    private String transmissionId;

    /**
     * 通信时间
     */
    private String transmissionTime;

    /**
     * 接口编号
     */
    private String interfaceCode;

    /**
     * 员工编号
     */
    private String tawSystemUserId;

    /**
     * 带看状态
     */
    private String status;

    /**
     * 租客电话/租客姓名
     */
    private String search;

    /**
     * 页号
     */
    private String pageNum;

    /**
     * 页面条数
     */
    private String pageSize;

    public CRMWorkTakeOrderListRequest( String cityCode, String subBizId,
            String transmissionId, String transmissionTime, String interfaceCode,
            String tawSystemUserId, String status, String search, String pageNum,
            String pageSize) {
        super();
        this.cityCode = cityCode;
        this.subBizId = subBizId;
        this.transmissionId = transmissionId;
        this.transmissionTime = transmissionTime;
        this.interfaceCode = interfaceCode;
        this.tawSystemUserId = tawSystemUserId;
        this.status = status;
        this.search = search;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public CRMWorkTakeOrderListRequest() {
        super();
    }

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

    /**
     * 取得 传输ID
     * @return 传输ID
     */
    public String getTransmissionId() {
        return transmissionId;
    }

    /**
     * 設定 传输ID
     * @param transmissionId 传输ID
     */
    public void setTransmissionId(String transmissionId) {
        this.transmissionId = transmissionId;
    }

    /**
     * 取得 通信时间
     * @return 通信时间
     */
    public String getTransmissionTime() {
        return transmissionTime;
    }

    /**
     * 設定 通信时间
     * @param transmissionTime 通信时间
     */
    public void setTransmissionTime(String transmissionTime) {
        this.transmissionTime = transmissionTime;
    }

    /**
     * 取得 接口编号
     * @return 接口编号
     */
    public String getInterfaceCode() {
        return interfaceCode;
    }

    /**
     * 設定 接口编号
     * @param interfaceCode 接口编号
     */
    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    /**
     * 取得 员工编号
     * @return 员工编号
     */
    public String getTawSystemUserId() {
        return tawSystemUserId;
    }

    /**
     * 設定 员工编号
     * @param tawSystemUserId 员工编号
     */
    public void setTawSystemUserId(String tawSystemUserId) {
        this.tawSystemUserId = tawSystemUserId;
    }

    /**
     * 取得 带看状态
     * @return 带看状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 設定 带看状态
     * @param status 带看状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 取得 租客电话/租客姓名
     * @return 租客电话/租客姓名
     */
    public String getSearch() {
        return search;
    }

    /**
     * 設定 租客电话/租客姓名
     * @param search 租客电话/租客姓名
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * 取得 页号
     * @return 页号
     */
    public String getPageNum() {
        return pageNum;
    }

    /**
     * 設定 页号
     * @param pageNum 页号
     */
    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 取得 页面条数
     * @return 页面条数
     */
    public String getPageSize() {
        return pageSize;
    }

    /**
     * 設定 页面条数
     * @param pageSize 页面条数
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }    

}
