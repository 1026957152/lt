package com.lt.dom.otcReq.device;

import com.lt.dom.otcenum.EnumAssetType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


public class AutoGateIdentifyCallBckReq {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String	id;//	Y	uuid
    public String	Mac_addr;//	Y	设备唯一标识码
    public String	time;//	Y	比对时间 yyyy -MM - dd HH:mm:ss
    public String	devicename;//	N	设备名称
    public String	location;//	N	安装位置
    public int   	inout;//	N	出入 0出口   1入口
    public String 	employee_number;//	Y 	人员编号
    public String	name;//	N	姓名
    public String	sex;//	N	性别
    public String	nation;//	N	民族
    public String	idNum;//	N	身份证号
    public String	icNum;//	N	IC卡号
    public String	birthday;//	N	出生年月如：1997 年4月3日
    public String	telephone;//	N	电话
    public String	address;//	N	身份证上住址信息
    public String	depart;//	N	签发机关
    public String	validStart;//	N	有效期开始
    public String	validEnd;//	N	有效期结束
    public int   	IdentifyType;//	Y	识别方式（比对类型）：0人脸识别， 1 黑名单识别（预留字段），2人证比对， 3 IC卡识别
    public int   	resultStatus;//	Y	比对结果 1 比对成功 0 比对失败
    public String	face_base64;//	Y	比对抓拍照片 base64位字符串
    public String	templatePhoto;//	N	模板照片
    public String	temperature;//	N	体温
    public int   	healthCode;//	N	健康码类型 0 未开启 1绿码 2黄码 3红码
    public Rna 	rna;// 	N	其他检测信息




    public class Rna {
        public Integer	ret;//	N	核酸检测结果 （0未开启，1 阴性，2 阳性）
        public String	time;//	N	核酸检测时间

        public Integer getRet() {
            return ret;
        }

        public void setRet(Integer ret) {
            this.ret = ret;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
