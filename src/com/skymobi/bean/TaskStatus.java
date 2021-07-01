package com.skymobi.bean;

import com.skymobi.util.JsonUtil;

/**
 * Author:boshuai.li
 * Time:2021/05/06   20:06
 * Description: TaskStatus 任务运行时状态
 */
public class TaskStatus {
    private boolean isVip; // 是否是vip
    private boolean isWaitVerifyCode; // 是否需要等待验证码
    private int startRemainTime;// 剩余可玩时间 单位：分钟

    private int endRemainTime; // 剩余可玩时间 - 游戏玩完之后 单位：分钟

    public int getEndRemainTime() {
        return endRemainTime;
    }

    public void setEndRemainTime(int endRemainTime) {
        this.endRemainTime = endRemainTime;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public boolean isWaitVerifyCode() {
        return isWaitVerifyCode;
    }

    public void setWaitVerifyCode(boolean waitVerifyCode) {
        isWaitVerifyCode = waitVerifyCode;
    }

    public int getStartRemainTime() {
        return startRemainTime;
    }

    public void setStartRemainTime(int startRemainTime) {
        this.startRemainTime = startRemainTime;
    }

    @Override
    public String toString() {
        return JsonUtil.toJSON(this);
    }
}
