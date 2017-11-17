package com.example.lightdance.appointment.Model;

/**
 * Created by pope on 2017/10/28.
 * 用来存放约帖的类型
 * int建议用Integer，因为大量用户并发操作，用普通的更新方法操作的话，会存在数据不一致的情况，
 * Bmob提供了原子计数器来保证原子性的修改某一数值字段的值，但只支持Integer型而不是int
 */

public class TypeBean {

    private String typeName;
    private int typeImg;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeImg() {
        return typeImg;
    }

    public void setTypeImg(int typeImg) {
        this.typeImg = typeImg;
    }
}
