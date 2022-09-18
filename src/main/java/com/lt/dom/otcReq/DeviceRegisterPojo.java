package com.lt.dom.otcReq;


//https://developer.tuya.com/en/docs/cloud/52d5df4241?id=Kbn026xsi0cab

import com.lt.dom.otcenum.EnumDeviceType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DeviceRegisterPojo {  // 这个就是机器了啊


    @NotNull
    private long asset_id;

    public long getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(long asset_id) {
        this.asset_id = asset_id;
    }

    private String hardware_id;

    @NotEmpty
    private String serialNumber;

    @NotEmpty
    private String name;

    @NotNull
    private EnumDeviceType type;

    public EnumDeviceType getType() {
        return type;
    }

    public void setType(EnumDeviceType type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHardware_id() {
        return hardware_id;
    }

    public void setHardware_id(String hardware_id) {
        this.hardware_id = hardware_id;
    }




    private AutoGate auto_gate;

    public AutoGate getAuto_gate() {
        return auto_gate;
    }

    public void setAuto_gate(AutoGate auto_gate) {
        this.auto_gate = auto_gate;
    }

    public static class AutoGate {



      //  public String	name;//	名称
        public String	sn;//	设备唯一序列号

        public String	Mac_addr;//	设备唯一mac地址


/*        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }*/

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String	location;//	位置
        public int   	inout;//	出入 0出口   1入口
        public String	pwd;//	密码 6位数字
        public int   	whitevalue;//	人脸识别阈值 [70,100]
        public int   	mapvalue;//	人证识别阈值 [30,100]
        public int   	recogSpaceTime;//	识别去重时间（秒）
        public int   	delayvalue;//	开门延迟 秒（默认2S）
        public int	lightLevelPercent;//	补光灯亮度  0-100
        public int   	lightType;//	0 常关   1 常开  3自动补光（自动补光规则：检测到人脸即亮、无人脸立即关闭，当摄像头透光量低时常开补光灯）
        public int   	systemVol;//	系统音量 [0,7]
        public int   	detectRange;//	识别距离 [0,2] [无限制,0.5米,1米,2米]
        public int   	wgOutType;//	韦根输出类型  0.韦根26 1.韦根34
        public int   	wgInOutMode;//	韦根模式 0韦根输出，1 韦根输入
        public int   	livenessEnable;//	活体开关  0开启  1关闭
        public int	InfraredImaging;//	黑白成像视频  0开启  1关闭
        public int   	livenessValue;//	活体分数  0-10
        public int	recogModeDB;//	人脸识别 0禁用，1开启
        public int   	recogModeIC;//	识别模式 0禁用  1 IC卡识别
        //2  IC卡加人脸识别   3 仅刷身份证
//4 验证身份证
        public int   	recogModeID;//	识别模式   0 禁用  1人证识别 （身份证照片+人脸照片比对）
        public int	detectVoiceEnable;//	陌生人识别  0禁用，1抓拍识别，
        //        2 抓拍识别并开门
        public int   	recogRelay;//	识别开门   0继电器输出   1继电器不输出（设置继电器不输出后需调用接口开闸http://deviceAddress:port/setDeviceRemoteOpen ）
        public String	rebootTime;//	自动重启间隔 "DDHHmm"
        //DD说明：00 每天，01周一，02周二，03周三，04周四，05周五，06周六，07周日
        public int   	TemperatureMode;//	测温功能   0 不测温   1 测温+人脸识别  2 只测温
        public String	TempNormalMax;//	测温报警温度（默认37.2）
        public int   	TempThermography;//	测温热成像视频 0 关闭 1 开启
        public int	OutSideDetectMode;// 	测温使用环境 0:标准模式 1:户外模式
        public int  	EnableMasks;//	口罩检测 0 关闭 1 开启
        public int  	IntelligentRecognition;//	安全帽识别  0 关闭 1 开启
        /*    public int   	wgOutType;//	韦根输出类型  0-韦根26， 1-韦根34
            public int	wgInOutMode;//	韦根模式 0韦根输出  1韦根输入*/
        public int   	relayMode;//	继电器类型 0常开, 1常关
        public String	banner;//	设备标语（默认值为人脸识别门禁终端）（说明：修改此值需要开启 bannerEnable参数）
        public int	bannerEnable;//	待机屏保 0 关  1 打开
        public int	bannerSpaceTime;//	待机屏保图片切换时间
        public int   	VideoSwitch;//	RTSP视频流切换  0 RGB视频流， 1 IR视频
    }


}
