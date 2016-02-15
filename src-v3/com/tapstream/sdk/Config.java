package com.tapstream.sdk;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private String androidId;
    private boolean collectAdvertisingId;
    private boolean collectTasteData;
    private String deviceId;
    private boolean fireAutomaticInstallEvent;
    private boolean fireAutomaticOpenEvent;
    public Map<String, Object> globalEventParams;
    private String hardware;
    private String installEventName;
    private String odin1;
    private String openEventName;
    private String openUdid;
    private String wifiMac;

    public Config() {
        this.hardware = null;
        this.odin1 = null;
        this.openUdid = null;
        this.wifiMac = null;
        this.deviceId = null;
        this.androidId = null;
        this.installEventName = null;
        this.openEventName = null;
        this.fireAutomaticInstallEvent = true;
        this.fireAutomaticOpenEvent = true;
        this.collectTasteData = true;
        this.collectAdvertisingId = true;
        this.globalEventParams = new HashMap();
    }

    public String getAndroidId() {
        return this.androidId;
    }

    public boolean getCollectAdvertisingId() {
        return this.collectAdvertisingId;
    }

    @Deprecated
    public boolean getCollectAndroidId() {
        return false;
    }

    @Deprecated
    public boolean getCollectDeviceId() {
        return false;
    }

    public boolean getCollectTasteData() {
        return this.collectTasteData;
    }

    @Deprecated
    public boolean getCollectWifiMac() {
        return false;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public boolean getFireAutomaticInstallEvent() {
        return this.fireAutomaticInstallEvent;
    }

    public boolean getFireAutomaticOpenEvent() {
        return this.fireAutomaticOpenEvent;
    }

    public String getHardware() {
        return this.hardware;
    }

    public String getInstallEventName() {
        return this.installEventName;
    }

    public String getOdin1() {
        return this.odin1;
    }

    public String getOpenEventName() {
        return this.openEventName;
    }

    public String getOpenUdid() {
        return this.openUdid;
    }

    public String getWifiMac() {
        return this.wifiMac;
    }

    public void setAndroidId(String str) {
        this.androidId = str;
    }

    public void setCollectAdvertisingId(boolean z) {
        this.collectAdvertisingId = z;
    }

    @Deprecated
    public void setCollectAndroidId(boolean z) {
    }

    @Deprecated
    public void setCollectDeviceId(boolean z) {
    }

    public void setCollectTasteData(boolean z) {
        this.collectTasteData = z;
    }

    @Deprecated
    public void setCollectWifiMac(boolean z) {
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setFireAutomaticInstallEvent(boolean z) {
        this.fireAutomaticInstallEvent = z;
    }

    public void setFireAutomaticOpenEvent(boolean z) {
        this.fireAutomaticOpenEvent = z;
    }

    public void setHardware(String str) {
        this.hardware = str;
    }

    public void setInstallEventName(String str) {
        this.installEventName = str;
    }

    public void setOdin1(String str) {
        this.odin1 = str;
    }

    public void setOpenEventName(String str) {
        this.openEventName = str;
    }

    public void setOpenUdid(String str) {
        this.openUdid = str;
    }

    public void setWifiMac(String str) {
        this.wifiMac = str;
    }
}
