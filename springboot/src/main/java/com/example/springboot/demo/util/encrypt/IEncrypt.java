package com.example.springboot.demo.util.encrypt;

public interface IEncrypt {
    String encrypt(String var1);

    String encrypt(String var1, String var2);

    String decrypt(String var1);

    String decrypt(String var1, String var2);
}
