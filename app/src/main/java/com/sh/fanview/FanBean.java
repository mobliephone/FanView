package com.sh.fanview;

/**
 * @Description: 实体类
 * @Author: cgw
 * @CreateDate: 2019/8/13 10:21
 */
public class FanBean {

    private String name;//类目
    private float percentage;//百分比
    private float value;//数值

    private int color;//颜色
    private float radian;//弧度

    public FanBean() {
    }

    public FanBean(String name, float percentage, float value) {
        this.name = name;
        this.percentage = percentage;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getRadian() {
        return radian;
    }

    public void setRadian(float radian) {
        this.radian = radian;
    }
}
