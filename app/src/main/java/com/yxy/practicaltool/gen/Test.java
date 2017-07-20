package com.yxy.practicaltool.gen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangjingye on 2017/7/20.
 */

@Entity
public class Test {

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Generated(hash = 1115628523)
    public Test(String name) {
        this.name = name;
    }

    @Generated(hash = 372557997)
    public Test() {
    }
}
