package com.lt.dom.vo;

import com.lt.dom.oct.Menu;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Peter Xu on 01/18/2015.
 */
public class MenuNode implements Serializable{

    private Menu current;

    private List<MenuNode> childs;

    public MenuNode(Menu menu) {
        this.current = menu;
    }

    public Menu getCurrent() {
        return current;
    }

    public void setCurrent(Menu current) {
        this.current = current;
    }

    public List<MenuNode> getChilds() {
        return childs;
    }

    public void setChilds(List<MenuNode> childs) {
        this.childs = childs;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
