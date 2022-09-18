package com.lt.dom.OctResp;

import com.lt.dom.oct.AssetAuthorized;
import com.lt.dom.oct.AssetDevice;
import com.lt.dom.otcenum.EnumUserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


public class AssetDeviceResp {


    @NotNull
    private String asset_id;  //   	String	The asset ID.
    @NotNull
    private Long parent_asset_id; //    parent_asset_id	String	The ID of the parent asset. The top-level ID is set to -1.
    @NotNull
    private String asset_name; // asset_name	String	The asset name.
    private String asset_full_name; //asset_full_name	String	The full name of the specified asset.
    @NotNull
    private long level; //Integer	The hierarchical level of the specified asset. The root node is set to 0.
    private long user;
    private EnumUserType userType;
    private boolean authorized_children;

    public static AssetDeviceResp from(AssetDevice assetDevice, AssetAuthorized e) {


        AssetDeviceResp assetDeviceResp = new AssetDeviceResp();

        assetDeviceResp.setAsset_id(assetDevice.getAsset_id());
        assetDeviceResp.setAsset_name(assetDevice.getAsset_name());
        assetDeviceResp.setParent_asset_id(assetDevice.getParent_asset_id());
        assetDeviceResp.setAsset_full_name(assetDevice.getAsset_full_name());
        assetDeviceResp.setUser(e.getUid());
        assetDeviceResp.setUserType(e.getOwnerType());
        assetDeviceResp.setAuthorized_children(e.isAuthorized_children());
        return assetDeviceResp;
    }


    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public Long getParent_asset_id() {
        return parent_asset_id;
    }

    public void setParent_asset_id(Long parent_asset_id) {
        this.parent_asset_id = parent_asset_id;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public String getAsset_full_name() {
        return asset_full_name;
    }

    public void setAsset_full_name(String asset_full_name) {
        this.asset_full_name = asset_full_name;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getUser() {
        return user;
    }

    public void setUserType(EnumUserType userType) {
        this.userType = userType;
    }

    public EnumUserType getUserType() {
        return userType;
    }

    public void setAuthorized_children(boolean authorized_children) {
        this.authorized_children = authorized_children;
    }

    public boolean getAuthorized_children() {
        return authorized_children;
    }
}
