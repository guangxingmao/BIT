package com.hackathon.bottosapp.bottos_sdk.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.bottosapp.bottos_sdk.entity.WalletKeyPair;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.CipherException;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.Credentials;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.DeterministicSeed;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.ECKeyPair;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.Keys;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.Numeric;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.SecureRandomUtils;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.Wallet;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.WalletFile;
import com.hackathon.bottosapp.bottos_sdk.utils.protocol.ObjectMapperFactory;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.Hash;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;


/**
 * Created by xionglh on 2018/8/20
 */
public class KeystoreKeyCreatTool {

    private static final SecureRandom secureRandom             = SecureRandomUtils.secureRandom();
    private static final int          PUBLIC_KEY_SIZE          = 64;
    private static final int          PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
    private static       ObjectMapper objectMapper             = ObjectMapperFactory.getObjectMapper();

    public static WalletKeyPair createWalletKeyPair() {
        WalletKeyPair walletKeyPair = new WalletKeyPair();
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        DeterministicSeed ds = new DeterministicSeed(secureRandom, 128, "", creationTimeSeconds);
        ECKeyPair ecKeyPair = getECKeyPair(ds);
        BigInteger publicKey = ecKeyPair.getPublicKey();
        String publicKeyStr = Numeric.toHexStringWithPrefixZeroPadded(publicKey, PUBLIC_KEY_LENGTH_IN_HEX);
        BigInteger privateKey = ecKeyPair.getPrivateKey();
        String privateKeyStr = Numeric.toHexStringNoPrefixZeroPadded(privateKey, Keys.PRIVATE_KEY_LENGTH_IN_HEX);
        walletKeyPair.setPrivateKey(privateKeyStr);
        walletKeyPair.setPublicKey(publicKeyStr);
        return walletKeyPair;
    }

    private static ECKeyPair getECKeyPair(DeterministicSeed ds) {
        byte[] seedBytes = ds.getSeedBytes();
        if (seedBytes == null)
            return null;
        return ECKeyPair.create(Hash.sha256(seedBytes));
    }

    public static String createKeys() {
        WalletKeyPair walletKeyPair = createWalletKeyPair();
        return GJsonUtil.toJson(walletKeyPair, WalletKeyPair.class);
    }

    public static String createKeystore(String password, String privateKey) {
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
        WalletFile walletFile;
        try {
            walletFile = Wallet.create(password, ecKeyPair, 1024, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return GJsonUtil.toJson(walletFile, WalletFile.class);
    }

    public static WalletFile createWalletFile(String password, String privateKey) {
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
        try {
            return Wallet.create(password, ecKeyPair, 1024, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String recoverKeystore(String pwd, String keystore) {
        Credentials credentials;
        ECKeyPair keypair;
        String privateKey = null;
        try {
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            credentials = Credentials.create(Wallet.decrypt(pwd, walletFile));
            keypair = credentials.getEcKeyPair();
            privateKey = Numeric.toHexStringNoPrefixZeroPadded(keypair.getPrivateKey(), Keys.PRIVATE_KEY_LENGTH_IN_HEX);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

}
