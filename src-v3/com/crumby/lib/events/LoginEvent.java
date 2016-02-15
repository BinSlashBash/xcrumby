package com.crumby.lib.events;

public class LoginEvent {
    public String accountType;

    public LoginEvent(String accountType) {
        this.accountType = accountType;
    }
}
