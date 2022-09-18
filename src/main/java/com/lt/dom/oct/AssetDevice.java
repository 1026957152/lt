package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumDeviceType;
import com.lt.dom.otcenum.EnumUserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class AssetDevice {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @NotNull
    private String asset_id;  //   	String	The asset ID.
    @NotNull
    private Long parent_asset_id; //    parent_asset_id	String	The ID of the parent asset. The top-level ID is set to -1.
    @NotNull
    private String asset_name; // asset_name	String	The asset name.
    private String asset_full_name; //asset_full_name	String	The full name of the specified asset.
    @NotNull
    private long level; //Integer	The hierarchical level of the specified asset. The root node is set to 0.




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

}
