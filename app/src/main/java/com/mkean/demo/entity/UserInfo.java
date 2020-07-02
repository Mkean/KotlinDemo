package com.mkean.demo.entity;

import android.content.Context;

import com.mkean.demo.entity.entityEnum.Sex;
import com.mkean.demo.utils.SnappyDBUtil;

public class UserInfo {
    private static UserInfo INSTANCE;

    public UserInfo() {
    }

    public static UserInfo getInstance(Context context) {
        SnappyDBUtil snappyDBUtil = new SnappyDBUtil(context);
        INSTANCE = (UserInfo) snappyDBUtil.getDbObject("userInfo", UserInfo.class);
        if (INSTANCE == null){
            INSTANCE = new UserInfo();
        }
        return INSTANCE;
    }

    private boolean isLogin;

    /**
     * 推荐人，默认 0
     *只对普通用户有效
     *
     */
    private Long refereeUid;
    private Long areaId;
    private String bankName;
    private String bankCard;
    private String realName;
    /**
     * 身份证号码
     */
    private String idCard;

    private String email;
    private String headUrl;
    private String mobile;  // 手机号
    private MoneyAccount moneyAccount;
    private MoneyAccount pensionMoneyAccount;
    private int moneyAccountId;
    private String nickname;
    private int pensionMoneyAccountId;
    private String status;
    private long uid;
    private String username;
    private Sex sex;
    private long createTime;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    private boolean openCard;

    public boolean isOpenCard() {
        return openCard;
    }

    public void setOpenCard(boolean openCard) {
        this.openCard = openCard;
    }

    private double lautide;
    private double longtide;

    private int cityId;

    private double accumulativeIntegral;
    private Integer userLevel;

//    public BigDecimal getAccumulativeIntegral() {
//        return accumulativeIntegral;
//    }
//
//    public void setAccumulativeIntegral(BigDecimal accumulativeIntegral) {
//        this.accumulativeIntegral = accumulativeIntegral;
//    }


    public double getAccumulativeIntegral() {
        return accumulativeIntegral;
    }

    public void setAccumulativeIntegral(double accumulativeIntegral) {
        this.accumulativeIntegral = accumulativeIntegral;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private String pwd;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public double getLautide() {
        return lautide;
    }

    public void setLautide(double lautide) {
        this.lautide = lautide;
    }

    public double getLongtide() {
        return longtide;
    }

    public void setLongtide(double longtide) {
        this.longtide = longtide;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public MoneyAccount getPensionMoneyAccount() {
        return pensionMoneyAccount;
    }

    public void setPensionMoneyAccount(MoneyAccount pensionMoneyAccount) {
        this.pensionMoneyAccount = pensionMoneyAccount;
    }

    public Long getRefereeUid() {
        return refereeUid;
    }

    public void setRefereeUid(Long refereeUid) {
        this.refereeUid = refereeUid;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public MoneyAccount getMoneyAccount() {
        return moneyAccount;
    }

    public void setMoneyAccount(MoneyAccount moneyAccount) {
        this.moneyAccount = moneyAccount;
    }

    public int getMoneyAccountId() {
        return moneyAccountId;
    }

    public void setMoneyAccountId(int moneyAccountId) {
        this.moneyAccountId = moneyAccountId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPensionMoneyAccountId() {
        return pensionMoneyAccountId;
    }

    public void setPensionMoneyAccountId(int pensionMoneyAccountId) {
        this.pensionMoneyAccountId = pensionMoneyAccountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "isLogin=" + isLogin +
                ", refereeUid=" + refereeUid +
                ", areaId=" + areaId +
                ", bankName='" + bankName + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", realName='" + realName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", email='" + email + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", mobile='" + mobile + '\'' +
                ", moneyAccount=" + moneyAccount +
                ", pensionMoneyAccount=" + pensionMoneyAccount +
                ", moneyAccountId=" + moneyAccountId +
                ", nickname='" + nickname + '\'' +
                ", pensionMoneyAccountId=" + pensionMoneyAccountId +
                ", status='" + status + '\'' +
                ", uid=" + uid +
                ", username='" + username + '\'' +
                ", lautide=" + lautide +
                ", longtide=" + longtide +
                ", cityId=" + cityId +
                ", accumulativeIntegral=" + accumulativeIntegral +
                ", userLevel=" + userLevel +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
