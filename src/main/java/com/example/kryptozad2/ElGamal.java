package com.example.kryptozad2;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HexFormat;


public class ElGamal {
    private BigInteger p;
    private BigInteger k;
    private BigInteger h;

    public void setP(BigInteger p) {
        this.p = p;
    }

    public void setH(BigInteger h) {
        this.h = h;
    }

    public void setG(BigInteger g) {
        this.g = g;
    }

    private BigInteger g;
    private BigInteger Nm1;

    private String pKey = "";

    private String gKey = "";

    private String hKey = "";

    private String privateKey = "";

    public void generateKeys() {
        p = BigInteger.probablePrime(512, new SecureRandom());
        g = new BigInteger(512, new SecureRandom());
        k = new BigInteger(512, new SecureRandom()); // to tylko prywatny
        // N == p // g == g // a == k //

        h = g.modPow(k, p);
        Nm1 = p.subtract(BigInteger.ONE);

        while (true) {
            p = BigInteger.probablePrime(512, new SecureRandom());
            if (p.gcd(Nm1).equals(BigInteger.ONE)) {
                break;
            }
        }
        byte[] t1 = p.toByteArray();
        byte[] t2 = g.toByteArray();
        byte[] t3 = h.toByteArray();
        byte[] t4 = k.toByteArray();
        HexFormat hex = HexFormat.of();
        pKey = hex.formatHex(t1);
        gKey = hex.formatHex(t2);
        hKey = hex.formatHex(t3);
        privateKey = hex.formatHex(t4);

    }

    public byte[] encryptMessage(byte[] toEncrypt) {
        byte[] encrypted = null;

        BigInteger b = new BigInteger(256, new SecureRandom());
        BigInteger temp = new BigInteger(toEncrypt);
        BigInteger c1 = g.modPow(b, p);
        BigInteger c2 = h.modPow(b, p);
        c2 = c2.multiply(temp);
            System.out.println("c1: " +c1);
            System.out.println("c2: " +c2);

    return encrypted;
    }

    public String decryptMessage(BigInteger c1) {
        BigInteger temp;
        BigInteger subs = BigInteger.valueOf(2);
        BigInteger temp2;
        temp = c1.modPow(k,p);
        temp2 = temp.modPow((p.subtract(subs)),p);
        //temp2 = c2.multiply(temp2);
        byte[] test = temp2.toByteArray();
        String decrypted = new String(test);
        return decrypted;
    }

    public String getpKey() {
        return pKey;
    }

    public String getgKey() {
        return gKey;
    }

    public String gethKey() {
        return hKey;
    }

    public void setpKey(String pKey) {
        this.pKey = pKey;
    }

    public void setgKey(String gKey) {
        this.gKey = gKey;
    }

    public void sethKey(String hKey) {
        this.hKey = hKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
