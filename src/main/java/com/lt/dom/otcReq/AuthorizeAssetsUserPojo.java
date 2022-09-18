package com.lt.dom.otcReq;


//https://developer.tuya.com/en/docs/cloud/52d5df4241?id=Kbn026xsi0cab

import java.time.LocalDateTime;
import java.util.List;

public class AuthorizeAssetsUserPojo {  // 这个就是机器了啊
    List<Long> asset_ids;
    Boolean authorized_children;

    public List<Long> getAsset_ids() {
        return asset_ids;
    }

    public void setAsset_ids(List<Long> asset_ids) {
        this.asset_ids = asset_ids;
    }

    public Boolean getAuthorized_children() {
        return authorized_children;
    }

    public void setAuthorized_children(Boolean authorized_children) {
        this.authorized_children = authorized_children;
    }
}
