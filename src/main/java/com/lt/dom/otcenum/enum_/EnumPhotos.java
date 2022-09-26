package com.lt.dom.otcenum.enum_;

public enum EnumPhotos {
    full(Integer.MAX_VALUE,Integer.MAX_VALUE),
    large(1080,720),
    medium(1080,720),
    thumb(200,200),
    avatar(56,56),


    ;

    private String name;
    private int max_width;
    private int max_height;



    EnumPhotos(String barcode) {

    }

    EnumPhotos(int max_width, int max_height) {
        this.max_height = max_height;
        this.max_width = max_width;
    }
}