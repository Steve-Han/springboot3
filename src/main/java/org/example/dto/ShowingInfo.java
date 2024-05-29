package org.example.dto;

public class ShowingInfo {

    /**
     * AMS房源编号
     */
    private String amsHouseCode;

    /**
     * AMS房间ID
     */
    private Long amsRoomId;

    /**
     * 房源带看反馈状态
     */
    private Long feedbackType;

    /**
     * 意向状态:无意向、成交、空
     */
    private Long intentionStatus;

    /**
     * 反馈备注:首次反馈备注   再次反馈备注
     */
    private String reasons;


    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public Long getAmsRoomId() {
        return amsRoomId;
    }

    public void setAmsRoomId(Long amsRoomId) {
        this.amsRoomId = amsRoomId;
    }

    public Long getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Long feedbackType) {
        this.feedbackType = feedbackType;
    }

    public Long getIntentionStatus() {
        return intentionStatus;
    }

    public void setIntentionStatus(Long intentionStatus) {
        this.intentionStatus = intentionStatus;
    }

    public String getAmsHouseCode() {
        return amsHouseCode;
    }

    public void setAmsHouseCode(String amsHouseCode) {
        this.amsHouseCode = amsHouseCode;
    }

}
