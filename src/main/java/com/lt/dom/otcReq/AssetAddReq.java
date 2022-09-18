package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumDeviceType;
import com.lt.dom.otcenum.EnumUserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


public class AssetAddReq {


    private String asset_id;  //   	String	The asset ID.

    private Long parent_asset_id; //    parent_asset_id	String	The ID of the parent asset. The top-level ID is set to -1.

    private String name; // asset_name	String	The asset name.
    private String full_name; //asset_full_name	String	The full name of the specified asset.

    private long level; //Integer	The hierarchical level of the specified asset. The root node is set to 0.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    @NotNull
    private EnumDeviceType enumDeviceType;  //   	String	The asset ID.


    public EnumDeviceType getEnumDeviceType() {
        return enumDeviceType;
    }

    public void setEnumDeviceType(EnumDeviceType enumDeviceType) {
        this.enumDeviceType = enumDeviceType;
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

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

}
