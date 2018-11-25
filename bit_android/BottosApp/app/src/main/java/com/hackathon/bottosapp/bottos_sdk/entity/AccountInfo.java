package com.hackathon.bottosapp.bottos_sdk.entity;

public class AccountInfo {

    /**
     * account_name : testtest
     * pubkey : 0454f1c2223d553aa6ee53ea1ccea8b7bf78b8ca99f3ff622a3bb3e62dedc712089033d6091d77296547bc071022ca2838c9e86dec29667cf740e5c9e654b6127f
     * balance : 0
     * staked_balance : 0
     * unStaking_balance : 0
     * unStaking_time : 0
     */

    private String account_name;
    private String pubkey;
    private String balance;
    private String staked_balance;
    private String unStaking_balance;
    private int    unStaking_time;

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getStaked_balance() {
        return staked_balance;
    }

    public void setStaked_balance(String staked_balance) {
        this.staked_balance = staked_balance;
    }

    public String getUnStaking_balance() {
        return unStaking_balance;
    }

    public void setUnStaking_balance(String unStaking_balance) {
        this.unStaking_balance = unStaking_balance;
    }

    public int getUnStaking_time() {
        return unStaking_time;
    }

    public void setUnStaking_time(int unStaking_time) {
        this.unStaking_time = unStaking_time;
    }
}
