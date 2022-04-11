package com.rms.supply.api.base;

/**
 * 单例持有一个变量 如果变量值被初始化 说明app被杀 ，直接重启app
 */
public class StatusHolder {
    private static StatusHolder mInstance;
    private boolean isKill = true;

    public boolean isKill() {
        return isKill;
    }

    public void setKill(boolean kill) {
        isKill = kill;
    }

    private StatusHolder() {

    }

    public static StatusHolder getInstance() {
        if (mInstance == null) {
            synchronized (StatusHolder.class) {
                if (mInstance == null) {
                    mInstance = new StatusHolder();
                }
            }
        }
        return mInstance;
    }
}
