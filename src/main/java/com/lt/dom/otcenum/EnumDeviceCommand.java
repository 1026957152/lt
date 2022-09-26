package com.lt.dom.otcenum;

import javax.persistence.Column;

public enum EnumDeviceCommand {
    barcode(EnumDeviceType.OUTLET,"barcode","","",EnumValueType.boolean_,""),

    ;



    private EnumDeviceType category;//	String	Instruction name

    private String name;//	String	Instruction name
    private String desc;//	String	Description
    
//##@Column(unique=true)
private String code;//	String	Instruction
    private EnumValueType type;//	String	Instruction type
    private String values;//	String	The input parameter range of the instruction

    EnumDeviceCommand(EnumDeviceType category,String name,String desc, String code, EnumValueType type,String values) {

        this.category = category;
        this.desc = desc;
        this.code = code;
        this.type = type;
        this.values = values;
    }

    public EnumDeviceType getCategory() {
        return category;
    }

    public void setCategory(EnumDeviceType category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EnumValueType getType() {
        return type;
    }

    public void setType(EnumValueType type) {
        this.type = type;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
