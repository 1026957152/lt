package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumUserType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class AssetAuthorized {
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
    private long uid;  //   	String	The asset ID.
    @NotNull
    private long assetId; //    parent_asset_id	String	The ID of the parent asset. The top-level ID is set to -1.


    private boolean authorized_children;




    @NotNull
    private long owner;  //   	String	The asset ID.
    @NotNull
    private EnumUserType ownerType;  //   	String	The asset ID.



    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long asset_id) {
        this.assetId = asset_id;
    }

    public boolean isAuthorized_children() {
        return authorized_children;
    }

    public void setAuthorized_children(boolean authorized_children) {
        this.authorized_children = authorized_children;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public EnumUserType getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(EnumUserType ownerType) {
        this.ownerType = ownerType;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
