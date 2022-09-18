package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class AutoGate {
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
    private long device;

    public long getDevice() {
        return device;
    }

    public void setDevice(long device) {
        this.device = device;
    }







    public String	name;//	名称
    public String	Mac_addr;//	设备唯一mac地址
    public String	SN;//	设备唯一序列号
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


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac_addr() {
        return Mac_addr;
    }

    public void setMac_addr(String mac_addr) {
        Mac_addr = mac_addr;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getInout() {
        return inout;
    }

    public void setInout(int inout) {
        this.inout = inout;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getWhitevalue() {
        return whitevalue;
    }

    public void setWhitevalue(int whitevalue) {
        this.whitevalue = whitevalue;
    }

    public int getMapvalue() {
        return mapvalue;
    }

    public void setMapvalue(int mapvalue) {
        this.mapvalue = mapvalue;
    }

    public int getRecogSpaceTime() {
        return recogSpaceTime;
    }

    public void setRecogSpaceTime(int recogSpaceTime) {
        this.recogSpaceTime = recogSpaceTime;
    }

    public int getDelayvalue() {
        return delayvalue;
    }

    public void setDelayvalue(int delayvalue) {
        this.delayvalue = delayvalue;
    }

    public int getLightLevelPercent() {
        return lightLevelPercent;
    }

    public void setLightLevelPercent(int lightLevelPercent) {
        this.lightLevelPercent = lightLevelPercent;
    }

    public int getLightType() {
        return lightType;
    }

    public void setLightType(int lightType) {
        this.lightType = lightType;
    }

    public int getSystemVol() {
        return systemVol;
    }

    public void setSystemVol(int systemVol) {
        this.systemVol = systemVol;
    }

    public int getDetectRange() {
        return detectRange;
    }

    public void setDetectRange(int detectRange) {
        this.detectRange = detectRange;
    }

    public int getWgOutType() {
        return wgOutType;
    }

    public void setWgOutType(int wgOutType) {
        this.wgOutType = wgOutType;
    }

    public int getWgInOutMode() {
        return wgInOutMode;
    }

    public void setWgInOutMode(int wgInOutMode) {
        this.wgInOutMode = wgInOutMode;
    }

    public int getLivenessEnable() {
        return livenessEnable;
    }

    public void setLivenessEnable(int livenessEnable) {
        this.livenessEnable = livenessEnable;
    }

    public int getInfraredImaging() {
        return InfraredImaging;
    }

    public void setInfraredImaging(int infraredImaging) {
        InfraredImaging = infraredImaging;
    }

    public int getLivenessValue() {
        return livenessValue;
    }

    public void setLivenessValue(int livenessValue) {
        this.livenessValue = livenessValue;
    }

    public int getRecogModeDB() {
        return recogModeDB;
    }

    public void setRecogModeDB(int recogModeDB) {
        this.recogModeDB = recogModeDB;
    }

    public int getRecogModeIC() {
        return recogModeIC;
    }

    public void setRecogModeIC(int recogModeIC) {
        this.recogModeIC = recogModeIC;
    }

    public int getRecogModeID() {
        return recogModeID;
    }

    public void setRecogModeID(int recogModeID) {
        this.recogModeID = recogModeID;
    }

    public int getDetectVoiceEnable() {
        return detectVoiceEnable;
    }

    public void setDetectVoiceEnable(int detectVoiceEnable) {
        this.detectVoiceEnable = detectVoiceEnable;
    }

    public int getRecogRelay() {
        return recogRelay;
    }

    public void setRecogRelay(int recogRelay) {
        this.recogRelay = recogRelay;
    }

    public String getRebootTime() {
        return rebootTime;
    }

    public void setRebootTime(String rebootTime) {
        this.rebootTime = rebootTime;
    }

    public int getTemperatureMode() {
        return TemperatureMode;
    }

    public void setTemperatureMode(int temperatureMode) {
        TemperatureMode = temperatureMode;
    }

    public String getTempNormalMax() {
        return TempNormalMax;
    }

    public void setTempNormalMax(String tempNormalMax) {
        TempNormalMax = tempNormalMax;
    }

    public int getTempThermography() {
        return TempThermography;
    }

    public void setTempThermography(int tempThermography) {
        TempThermography = tempThermography;
    }

    public int getOutSideDetectMode() {
        return OutSideDetectMode;
    }

    public void setOutSideDetectMode(int outSideDetectMode) {
        OutSideDetectMode = outSideDetectMode;
    }

    public int getEnableMasks() {
        return EnableMasks;
    }

    public void setEnableMasks(int enableMasks) {
        EnableMasks = enableMasks;
    }

    public int getIntelligentRecognition() {
        return IntelligentRecognition;
    }

    public void setIntelligentRecognition(int intelligentRecognition) {
        IntelligentRecognition = intelligentRecognition;
    }

    public int getRelayMode() {
        return relayMode;
    }

    public void setRelayMode(int relayMode) {
        this.relayMode = relayMode;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getBannerEnable() {
        return bannerEnable;
    }

    public void setBannerEnable(int bannerEnable) {
        this.bannerEnable = bannerEnable;
    }

    public int getBannerSpaceTime() {
        return bannerSpaceTime;
    }

    public void setBannerSpaceTime(int bannerSpaceTime) {
        this.bannerSpaceTime = bannerSpaceTime;
    }

    public int getVideoSwitch() {
        return VideoSwitch;
    }

    public void setVideoSwitch(int videoSwitch) {
        VideoSwitch = videoSwitch;
    }
}
