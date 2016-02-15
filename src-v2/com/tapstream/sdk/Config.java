/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private String androidId = null;
    private boolean collectAdvertisingId = true;
    private boolean collectTasteData = true;
    private String deviceId = null;
    private boolean fireAutomaticInstallEvent = true;
    private boolean fireAutomaticOpenEvent = true;
    public Map<String, Object> globalEventParams = new HashMap<String, Object>();
    private String hardware = null;
    private String installEventName = null;
    private String odin1 = null;
    private String openEventName = null;
    private String openUdid = null;
    private String wifiMac = null;

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

    public void setAndroidId(String string2) {
        this.androidId = string2;
    }

    public void setCollectAdvertisingId(boolean bl2) {
        this.collectAdvertisingId = bl2;
    }

    @Deprecated
    public void setCollectAndroidId(boolean bl2) {
    }

    @Deprecated
    public void setCollectDeviceId(boolean bl2) {
    }

    public void setCollectTasteData(boolean bl2) {
        this.collectTasteData = bl2;
    }

    @Deprecated
    public void setCollectWifiMac(boolean bl2) {
    }

    public void setDeviceId(String string2) {
        this.deviceId = string2;
    }

    public void setFireAutomaticInstallEvent(boolean bl2) {
        this.fireAutomaticInstallEvent = bl2;
    }

    public void setFireAutomaticOpenEvent(boolean bl2) {
        this.fireAutomaticOpenEvent = bl2;
    }

    public void setHardware(String string2) {
        this.hardware = string2;
    }

    public void setInstallEventName(String string2) {
        this.installEventName = string2;
    }

    public void setOdin1(String string2) {
        this.odin1 = string2;
    }

    public void setOpenEventName(String string2) {
        this.openEventName = string2;
    }

    public void setOpenUdid(String string2) {
        this.openUdid = string2;
    }

    public void setWifiMac(String string2) {
        this.wifiMac = string2;
    }
}

