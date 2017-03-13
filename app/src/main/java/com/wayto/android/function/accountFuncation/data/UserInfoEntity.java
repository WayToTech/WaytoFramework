package com.wayto.android.function.accountFuncation.data;

import java.io.Serializable;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.account.data
 * @Description:登录实体类
 * @date 2016/11/29 15:01
 */

public class UserInfoEntity implements Serializable {

    private String access_token;

    private String token_type;

    private int expires_in;

    private String id;

    private String icon;

    private String name;

    private String dept;

    private String unit;

    public void setAccess_token(String access_token){
        this.access_token = access_token;
    }
    public String getAccess_token(){
        return this.access_token;
    }
    public void setToken_type(String token_type){
        this.token_type = token_type;
    }
    public String getToken_type(){
        return this.token_type;
    }
    public void setExpires_in(int expires_in){
        this.expires_in = expires_in;
    }
    public int getExpires_in(){
        return this.expires_in;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setDept(String dept){
        this.dept = dept;
    }
    public String getDept(){
        return this.dept;
    }
    public void setUnit(String unit){
        this.unit = unit;
    }
    public String getUnit(){
        return this.unit;
    }
}
