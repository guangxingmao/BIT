package com.hackathon.bottosapp.bean;

/**
 * 对账号的简单存储
 */
public class AccountInfoDb {

    private String keystore;

    private String account;

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
