package com.lanma.customviewproject.entity;

/**
 * 作者 任强强 on 2017/1/17 10:09.
 */

public class MainListInfo {
    private String itemTitle;
    private Class<?> targetActivity;

    public MainListInfo(String itemTitle, Class<?> targetActivity) {
        this.itemTitle = itemTitle;
        this.targetActivity = targetActivity;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Class<?> getTargetActivity() {
        return targetActivity;
    }

    public void setTargetActivity(Class<?> targetActivity) {
        this.targetActivity = targetActivity;
    }
}
