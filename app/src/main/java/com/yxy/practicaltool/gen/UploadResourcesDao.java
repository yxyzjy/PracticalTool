package com.yxy.practicaltool.gen;

import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yxy on 2017/7/19 0019.
 */

@Entity
public class UploadResourcesDao {

    private int unitsId;
    private String unitsName;
    private String name2;
    private int pinzhongId;
    private String pinzhongName;
    private String num4;
    private String des5;
    private String tip6;
    private int H_State;
    private int attributeId;
    private String attributeName;
    public String getAttributeName() {
        return this.attributeName;
    }
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    public int getAttributeId() {
        return this.attributeId;
    }
    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }
    public int getH_State() {
        return this.H_State;
    }
    public void setH_State(int H_State) {
        this.H_State = H_State;
    }
    public String getTip6() {
        return this.tip6;
    }
    public void setTip6(String tip6) {
        this.tip6 = tip6;
    }
    public String getDes5() {
        return this.des5;
    }
    public void setDes5(String des5) {
        this.des5 = des5;
    }
    public String getNum4() {
        return this.num4;
    }
    public void setNum4(String num4) {
        this.num4 = num4;
    }
    public String getPinzhongName() {
        return this.pinzhongName;
    }
    public void setPinzhongName(String pinzhongName) {
        this.pinzhongName = pinzhongName;
    }
    public int getPinzhongId() {
        return this.pinzhongId;
    }
    public void setPinzhongId(int pinzhongId) {
        this.pinzhongId = pinzhongId;
    }
    public String getName2() {
        return this.name2;
    }
    public void setName2(String name2) {
        this.name2 = name2;
    }
    public String getUnitsName() {
        return this.unitsName;
    }
    public void setUnitsName(String unitsName) {
        this.unitsName = unitsName;
    }
    public int getUnitsId() {
        return this.unitsId;
    }
    public void setUnitsId(int unitsId) {
        this.unitsId = unitsId;
    }
    @Generated(hash = 158559394)
    public UploadResourcesDao(int unitsId, String unitsName, String name2,
            int pinzhongId, String pinzhongName, String num4, String des5,
            String tip6, int H_State, int attributeId, String attributeName) {
        this.unitsId = unitsId;
        this.unitsName = unitsName;
        this.name2 = name2;
        this.pinzhongId = pinzhongId;
        this.pinzhongName = pinzhongName;
        this.num4 = num4;
        this.des5 = des5;
        this.tip6 = tip6;
        this.H_State = H_State;
        this.attributeId = attributeId;
        this.attributeName = attributeName;
    }
    @Generated(hash = 91416941)
    public UploadResourcesDao() {
    }

}
