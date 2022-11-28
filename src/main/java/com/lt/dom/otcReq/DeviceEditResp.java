package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.BaseResp;
import com.lt.dom.OctResp.ComponentRightResp;
import com.lt.dom.OctResp.SupplierResp;
import com.lt.dom.oct.Device;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcenum.EnumDeviceOnline;
import com.lt.dom.otcenum.EnumDeviceStatus;
import com.lt.dom.otcenum.EnumDeviceType;
import org.springframework.hateoas.EntityModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceEditResp  {

    private String name;
    private String serialNumber;
    private Map parameterList
            ;
    private EntityModel componentRightTab;


    public <K, V> void setParameterList(Map parameterList) {
        this.parameterList = parameterList;
    }

    public Map getParameterList() {
        return parameterList;
    }

    private EnumDeviceType type;
    @NotNull
    private String project_id;
    private String device_model_id;
    @NotNull
    private long nickname;


    @NotNull
    private String manufacturer;
    @NotNull
    private boolean enabledOn;


    private LocalDateTime active_time;
    @NotNull
    private LocalDateTime update_time;

    @NotNull
    private LocalDateTime create_time;


    private long assetId;


    @NotNull
    private EnumDeviceStatus status;
    private String type_text;
    private String status_text;

private String code;
    private SupplierResp supplier;
    private List<ComponentRightResp> allowed;

    public static DeviceEditResp from(Device device) {
        DeviceEditResp deviceResp = new DeviceEditResp();
        deviceResp.setActive_time(device.getActive_time());
        deviceResp.setCreate_time(device.getCreate_time());
        deviceResp.setCode(device.getId_());
        deviceResp.setEnabledOn(device.isEnabledOn());
        deviceResp.setName(device.getName());
        deviceResp.setManufacturer(device.getManufacturer());
        deviceResp.setAssetId(device.getAssetId());
        deviceResp.setSerialNumber(device.getSerialNumber());
        deviceResp.setStatus(device.getStatus());
        deviceResp.setStatus_text(device.getStatus().toString());
        deviceResp.setType(device.getType());
        deviceResp.setProject_id(device.getProject_id());
        deviceResp.setType_text(device.getType().toString());

        return deviceResp;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isEnabledOn() {
        return enabledOn;
    }

    public void setEnabledOn(boolean enabledOn) {
        this.enabledOn = enabledOn;
    }

    public LocalDateTime getActive_time() {
        return active_time;
    }

    public void setActive_time(LocalDateTime active_time) {
        this.active_time = active_time;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long asset_id) {
        this.assetId = asset_id;
    }


    public EnumDeviceStatus getStatus() {
        return status;
    }

    public void setStatus(EnumDeviceStatus status) {
        this.status = status;
    }


    public EnumDeviceType getType() {
        return type;
    }

    public void setType(EnumDeviceType type) {
        this.type = type;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getDevice_model_id() {
        return device_model_id;
    }

    public void setDevice_model_id(String device_model_id) {
        this.device_model_id = device_model_id;
    }

    public long getNickname() {
        return nickname;
    }

    public void setNickname(long nickname) {
        this.nickname = nickname;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }


    public Info live_info;

    public Info getLive_info() {
        return live_info;
    }

    public void setLive_info(Info live_info) {
        this.live_info = live_info;
    }

    public void setType_text(String type_text) {

        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void withSupplier(Optional<Supplier> optionalSupplier) {

        if(optionalSupplier.isEmpty()){
            Supplier supplier = optionalSupplier.get();

            this.setSupplier(SupplierResp.from(supplier));

        }

    }

    public void setSupplier(SupplierResp supplier) {
        this.supplier = supplier;
    }

    public SupplierResp getSupplier() {
        return supplier;
    }

    public void setAllowed(List<ComponentRightResp> allowed) {
        this.allowed = allowed;
    }

    public List<ComponentRightResp> getAllowed() {
        return allowed;
    }

    public void setComponentRightTab(EntityModel componentRightTab) {
        this.componentRightTab = componentRightTab;
    }

    public EntityModel getComponentRightTab() {
        return componentRightTab;
    }

    public static class Info {
        private EnumDeviceOnline line;
        private LocalDateTime lastSeen_at;
        private String line_text;

        public EnumDeviceOnline getLine() {
            return line;
        }

        public void setLine(EnumDeviceOnline line) {
            this.line = line;
        }

        public void setLastSeen_at(LocalDateTime lastSeen_at) {
            this.lastSeen_at = lastSeen_at;
        }

        public LocalDateTime getLastSeen_at() {
            return lastSeen_at;
        }

        public void setLine_text(String line_text) {
            this.line_text = line_text;
        }

        public String getLine_text() {
            return line_text;
        }
    }



    public static class ComponentRightTab {

        private Map parameterList;
        private List<Long> rightIds;


        public <K, V> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }

        public List<Long> getRightIds() {
            return rightIds;
        }

        public void setRightIds(List<Long> rightIds) {
            this.rightIds = rightIds;
        }
    }
}
