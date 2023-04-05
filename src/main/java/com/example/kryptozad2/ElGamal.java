package com.example.kryptozad2;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class ElGamal {
    private BigInteger p;
    private BigInteger k;
    private BigInteger h;
    private BigInteger g;
    private BigInteger Nm1;

    private String publicKey = "";

    private String privateKey = "";



    public void generateKeys() {
        p = BigInteger.probablePrime(514,new SecureRandom());
        g = new BigInteger(512,new SecureRandom());
        k = new BigInteger(512,new SecureRandom()); // to tylko prywatny
        // N == p // g == g // a == k //

        h = g.modPow(k,p);
        Nm1 = p.subtract(BigInteger.ONE);

        while(true) {
            p = BigInteger.probablePrime(108,new SecureRandom());
            if(p.gcd(Nm1).equals(BigInteger.ONE)) {
                break;
            }
        }
        byte[] t1 = p.toByteArray();
        byte[] t2 = g.toByteArray();
        byte[] t3 = h.toByteArray();
        byte[] t4 = k.toByteArray();
        byte[] fajrant = p.add(g).add(h).toByteArray();
        publicKey = Base64.getEncoder().encodeToString(fajrant);
        //publicKey = Base64.getEncoder().encodeToString(t1) + Base64.getEncoder().encodeToString(t2) + Base64.getEncoder().encodeToString(t3);
        privateKey = Base64.getEncoder().encodeToString(t4);
        System.out.println(publicKey);
        System.out.println(privateKey);
    }




    public BigInteger findPrimitiveRoot(BigInteger p) {
        BigInteger one = BigInteger.ONE;
        BigInteger two = BigInteger.valueOf(2);
        BigInteger phi = p.subtract(one);
        BigInteger[] factors = phi.divideAndRemainder(two);
        while (true) {
            BigInteger g = getRandomNumberInRange(two, p.subtract(one));
            if (g.modPow(factors[0], p).equals(one) || g.modPow(factors[1], p).equals(one)) {
                continue;
            }
            boolean isPrimitiveRoot = true;
            for (BigInteger i = two; i.compareTo(phi) < 0; i = i.add(one)) {
                if (g.modPow(i, p).equals(one)) {
                    isPrimitiveRoot = false;
                    break;
                }
            }
            if (isPrimitiveRoot) {
                return g;
            }
        }
    }

    public BigInteger getRandomNumberInRange(BigInteger min, BigInteger max) {
        BigInteger range = max.subtract(min).add(BigInteger.ONE);
        return new BigInteger(range.bitLength(), new Random()).add(min).mod(range).add(min);
    }
}
