package com.lt.dom.vo;

import com.lt.dom.oct.AssetDevice;
import com.lt.dom.oct.Menu;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Peter Xu on 01/18/2015.
 */
public class AssetNode implements Serializable{

    private AssetDevice current;

    private List<AssetNode> childs;

    public AssetNode(AssetDevice menu) {
        this.current = menu;
    }

    public AssetDevice getCurrent() {
        return current;
    }

    public void setCurrent(AssetDevice current) {
        this.current = current;
    }

    public List<AssetNode> getChilds() {
        return childs;
    }

    public void setChilds(List<AssetNode> childs) {
        this.childs = childs;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
