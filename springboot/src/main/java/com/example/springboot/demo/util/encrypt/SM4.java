package com.example.springboot.demo.util.encrypt;

import com.longrise.LEAP.Base.Util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SM4 implements IEncrypt {
    private static final String _key = "leaplongrise2018";
    private static final String _iv = "longrise12345678";
    public static final int _ENCRYPT = 1;
    public static final int _DECRYPT = 0;
    private int mode = -1;
    private long[] sk = new long[32];
    private boolean isPadding = false;
    private static SM4 instance = new SM4();
    private static final byte[] SboxTable = new byte[]{-42, -112, -23, -2, -52, -31, 61, -73, 22, -74, 20, -62, 40, -5, 44, 5, 43, 103, -102, 118, 42, -66, 4, -61, -86, 68, 19, 38, 73, -122, 6, -103, -100, 66, 80, -12, -111, -17, -104, 122, 51, 84, 11, 67, -19, -49, -84, 98, -28, -77, 28, -87, -55, 8, -24, -107, -128, -33, -108, -6, 117, -113, 63, -90, 71, 7, -89, -4, -13, 115, 23, -70, -125, 89, 60, 25, -26, -123, 79, -88, 104, 107, -127, -78, 113, 100, -38, -117, -8, -21, 15, 75, 112, 86, -99, 53, 30, 36, 14, 94, 99, 88, -47, -94, 37, 34, 124, 59, 1, 33, 120, -121, -44, 0, 70, 87, -97, -45, 39, 82, 76, 54, 2, -25, -96, -60, -56, -98, -22, -65, -118, -46, 64, -57, 56, -75, -93, -9, -14, -50, -7, 97, 21, -95, -32, -82, 93, -92, -101, 52, 26, 85, -83, -109, 50, 48, -11, -116, -79, -29, 29, -10, -30, 46, -126, 102, -54, 96, -64, 41, 35, -85, 13, 83, 78, 111, -43, -37, 55, 69, -34, -3, -114, 47, 3, -1, 106, 114, 109, 108, 91, 81, -115, 27, -81, -110, -69, -35, -68, 127, 17, -39, 92, 65, 31, 16, 90, -40, 10, -63, 49, -120, -91, -51, 123, -67, 45, 116, -48, 18, -72, -27, -76, -80, -119, 105, -105, 74, 12, -106, 119, 126, 101, -71, -15, 9, -59, 110, -58, -124, 24, -16, 125, -20, 58, -36, 77, 32, 121, -18, 95, 62, -41, -53, 57, 72};
    private static final int[] FK = new int[]{-1548633402, 1453994832, 1736282519, -1301273892};
    private static final int[] CK = new int[]{462357, 472066609, 943670861, 1415275113, 1886879365, -1936483679, -1464879427, -993275175, -521670923, -66909679, 404694573, 876298825, 1347903077, 1819507329, -2003855715, -1532251463, -1060647211, -589042959, -117504499, 337322537, 808926789, 1280531041, 1752135293, -2071227751, -1599623499, -1128019247, -656414995, -184876535, 269950501, 741554753, 1213159005, 1684763257};

    public SM4() {
    }

    public static SM4 getInstance() {
        return instance;
    }

    private long get_ulong_be(byte[] b, int i) {
        long n = (long)(b[i] & 255) << 24 | (long)((b[i + 1] & 255) << 16) | (long)((b[i + 2] & 255) << 8) | (long)(b[i + 3] & 255) & 4294967295L;
        return n;
    }

    private void put_ulong_be(long n, byte[] b, int i) {
        b[i] = (byte)((int)(255L & n >> 24));
        b[i + 1] = (byte)((int)(255L & n >> 16));
        b[i + 2] = (byte)((int)(255L & n >> 8));
        b[i + 3] = (byte)((int)(255L & n));
    }

    private long shl(long x, int n) {
        return (x & -1L) << n;
    }

    private long rotl(long x, int n) {
        return this.shl(x, n) | x >> 32 - n;
    }

    private void swap(long[] sk, int i) {
        long t = sk[i];
        sk[i] = sk[31 - i];
        sk[31 - i] = t;
    }

    private byte sm4Sbox(byte inch) {
        int i = inch & 255;
        byte retVal = SboxTable[i];
        return retVal;
    }

    private long sm4Lt(long ka) {
        long bb = 0L;
        long c = 0L;
        byte[] a = new byte[4];
        byte[] b = new byte[4];
        this.put_ulong_be(ka, a, 0);
        b[0] = this.sm4Sbox(a[0]);
        b[1] = this.sm4Sbox(a[1]);
        b[2] = this.sm4Sbox(a[2]);
        b[3] = this.sm4Sbox(a[3]);
        bb = this.get_ulong_be(b, 0);
        c = bb ^ this.rotl(bb, 2) ^ this.rotl(bb, 10) ^ this.rotl(bb, 18) ^ this.rotl(bb, 24);
        return c;
    }

    private long sm4F(long x0, long x1, long x2, long x3, long rk) {
        return x0 ^ this.sm4Lt(x1 ^ x2 ^ x3 ^ rk);
    }

    private long sm4CalciRK(long ka) {
        long bb = 0L;
        long rk = 0L;
        byte[] a = new byte[4];
        byte[] b = new byte[4];
        this.put_ulong_be(ka, a, 0);
        b[0] = this.sm4Sbox(a[0]);
        b[1] = this.sm4Sbox(a[1]);
        b[2] = this.sm4Sbox(a[2]);
        b[3] = this.sm4Sbox(a[3]);
        bb = this.get_ulong_be(b, 0);
        rk = bb ^ this.rotl(bb, 13) ^ this.rotl(bb, 23);
        return rk;
    }

    private void sm4_setkey(long[] SK, byte[] key) {
        long[] MK = new long[4];
        long[] k = new long[36];
        int i = 0;
        MK[0] = this.get_ulong_be(key, 0);
        MK[1] = this.get_ulong_be(key, 4);
        MK[2] = this.get_ulong_be(key, 8);
        MK[3] = this.get_ulong_be(key, 12);
        k[0] = MK[0] ^ (long)FK[0];
        k[1] = MK[1] ^ (long)FK[1];
        k[2] = MK[2] ^ (long)FK[2];

        for(k[3] = MK[3] ^ (long)FK[3]; i < 32; ++i) {
            k[i + 4] = k[i] ^ this.sm4CalciRK(k[i + 1] ^ k[i + 2] ^ k[i + 3] ^ (long)CK[i]);
            SK[i] = k[i + 4];
        }

    }

    private void sm4_one_round(long[] sk, byte[] input, byte[] output) {
        int i = 0;
        long[] ulbuf = new long[36];
        ulbuf[0] = this.get_ulong_be(input, 0);
        ulbuf[1] = this.get_ulong_be(input, 4);
        ulbuf[2] = this.get_ulong_be(input, 8);

        for(ulbuf[3] = this.get_ulong_be(input, 12); i < 32; ++i) {
            ulbuf[i + 4] = this.sm4F(ulbuf[i], ulbuf[i + 1], ulbuf[i + 2], ulbuf[i + 3], sk[i]);
        }

        this.put_ulong_be(ulbuf[35], output, 0);
        this.put_ulong_be(ulbuf[34], output, 4);
        this.put_ulong_be(ulbuf[33], output, 8);
        this.put_ulong_be(ulbuf[32], output, 12);
    }

    private byte[] padding(byte[] input, int mode) {
        if (input == null) {
            return null;
        } else {
            byte[] ret = (byte[])null;
            if (mode == 1) {
                int p = 16 - input.length % 16;
                ret = new byte[input.length + p];
                System.arraycopy(input, 0, ret, 0, input.length);

                for(int i = 0; i < p; ++i) {
                    ret[input.length + i] = (byte)p;
                }
            } else {
                int p = input[input.length - 1];
                ret = new byte[input.length - p];
                System.arraycopy(input, 0, ret, 0, input.length - p);
            }

            return ret;
        }
    }

    private void sm4_setkey_enc(byte[] key) throws Exception {
        if (key != null && key.length == 16) {
            this.mode = 1;
            this.sm4_setkey(this.sk, key);
        } else {
            throw new Exception("key error!");
        }
    }

    private void sm4_setkey_dec(byte[] key) throws Exception {
        if (key != null && key.length == 16) {
            int i = false;
            this.mode = 0;
            this.sm4_setkey(this.sk, key);

            for(int i = 0; i < 16; ++i) {
                this.swap(this.sk, i);
            }

        } else {
            throw new Exception("key error!");
        }
    }

    private byte[] sm4_crypt_ecb(byte[] input) throws Exception {
        if (input == null) {
            throw new Exception("input is null!");
        } else {
            if (this.isPadding && this.mode == 1) {
                input = this.padding(input, 1);
            }

            int length = input.length;
            ByteArrayInputStream bins = new ByteArrayInputStream(input);

            ByteArrayOutputStream bous;
            byte[] output;
            for(bous = new ByteArrayOutputStream(); length > 0; length -= 16) {
                output = new byte[16];
                byte[] out = new byte[16];
                bins.read(output);
                this.sm4_one_round(this.sk, output, out);
                bous.write(out);
            }

            output = bous.toByteArray();
            if (this.isPadding && this.mode == 0) {
                output = this.padding(output, 0);
            }

            bins.close();
            bous.close();
            return output;
        }
    }

    private byte[] sm4_crypt_cbc(byte[] iv, byte[] input) throws Exception {
        if (iv != null && iv.length == 16) {
            if (input == null) {
                throw new Exception("input is null!");
            } else {
                if (this.isPadding && this.mode == 1) {
                    input = this.padding(input, 1);
                }

                int i = false;
                int length = input.length;
                ByteArrayInputStream bins = new ByteArrayInputStream(input);
                ByteArrayOutputStream bous = new ByteArrayOutputStream();
                byte[] temp;
                byte[] out;
                byte[] out;
                int i;
                if (this.mode != 1) {
                    for(temp = new byte[16]; length > 0; length -= 16) {
                        out = new byte[16];
                        out = new byte[16];
                        byte[] out1 = new byte[16];
                        bins.read(out);
                        System.arraycopy(out, 0, temp, 0, 16);
                        this.sm4_one_round(this.sk, out, out);

                        for(i = 0; i < 16; ++i) {
                            out1[i] = (byte)(out[i] ^ iv[i]);
                        }

                        System.arraycopy(temp, 0, iv, 0, 16);
                        bous.write(out1);
                    }
                } else {
                    while(length > 0) {
                        temp = new byte[16];
                        out = new byte[16];
                        out = new byte[16];
                        bins.read(temp);

                        for(i = 0; i < 16; ++i) {
                            out[i] = (byte)(temp[i] ^ iv[i]);
                        }

                        this.sm4_one_round(this.sk, out, out);
                        System.arraycopy(out, 0, iv, 0, 16);
                        bous.write(out);
                        length -= 16;
                    }
                }

                temp = bous.toByteArray();
                if (this.isPadding && this.mode == 0) {
                    temp = this.padding(temp, 0);
                }

                bins.close();
                bous.close();
                return temp;
            }
        } else {
            throw new Exception("iv error!");
        }
    }

    public String encrypt_ecb(String plainText) {
        return this.encrypt_ecb(plainText, "leaplongrise2018");
    }

    public String encrypt_ecb(String plainText, String keyStr) {
        try {
            this.isPadding = true;
            this.mode = 1;
            byte[] keyBytes = keyStr.getBytes();
            this.sm4_setkey_enc(keyBytes);
            byte[] encrypted = this.sm4_crypt_ecb(plainText.getBytes("GBK"));
            String cipherText = (new BASE64Encoder()).encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }

            return cipherText;
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public byte[] encrypt_ecb(byte[] plainTextBytes, byte[] keyBytes) {
        try {
            this.isPadding = true;
            this.mode = 1;
            this.sm4_setkey_enc(keyBytes);
            return this.sm4_crypt_ecb(plainTextBytes);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public String decrypt_ecb(String plainText) {
        return this.decrypt_ecb(plainText, "leaplongrise2018");
    }

    public String decrypt_ecb(String cipherText, String keyStr) {
        try {
            this.isPadding = true;
            this.mode = 0;
            byte[] keyBytes = keyStr.getBytes();
            this.sm4_setkey_dec(keyBytes);
            byte[] decrypted = this.sm4_crypt_ecb((new BASE64Decoder()).decodeBuffer(cipherText));
            return new String(decrypted, "GBK");
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public byte[] decrypt_ecb(byte[] cipherText, byte[] keyBytes) {
        try {
            this.isPadding = true;
            this.mode = 0;
            this.sm4_setkey_dec(keyBytes);
            return this.sm4_crypt_ecb(cipherText);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public String encrypt_cbc(String plainText) {
        return this.encrypt_cbc(plainText, "leaplongrise2018");
    }

    public String encrypt_cbc(String plainText, String keyStr) {
        try {
            this.isPadding = true;
            this.mode = 1;
            String viStr = "longrise12345678";
            byte[] keyBytes = keyStr.getBytes();
            byte[] ivBytes = viStr.getBytes();
            this.sm4_setkey_enc(keyBytes);
            byte[] encrypted = this.sm4_crypt_cbc(ivBytes, plainText.getBytes("GBK"));
            String cipherText = (new BASE64Encoder()).encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }

            return cipherText;
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    public String decrypt_cbc(String cipherText) {
        return this.decrypt_cbc(cipherText, "leaplongrise2018");
    }

    public String decrypt_cbc(String cipherText, String keyStr) {
        try {
            this.isPadding = true;
            this.mode = 0;
            String viStr = "longrise12345678";
            byte[] keyBytes = keyStr.getBytes();
            byte[] ivBytes = viStr.getBytes();
            this.sm4_setkey_dec(keyBytes);
            byte[] decrypted = this.sm4_crypt_cbc(ivBytes, (new BASE64Decoder()).decodeBuffer(cipherText));
            return new String(decrypted, "GBK");
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public String encrypt(String inputstr) {
        return StringUtils.isEmpty(inputstr) ? null : (new SM4()).encrypt_cbc(inputstr);
    }

    public String encrypt(String inputstr, String key) {
        return !StringUtils.isEmpty(inputstr) && !StringUtils.isEmpty(key) ? (new SM4()).encrypt_cbc(inputstr, key) : null;
    }

    public String decrypt(String inputstr) {
        return StringUtils.isEmpty(inputstr) ? null : (new SM4()).decrypt_cbc(inputstr);
    }

    public String decrypt(String inputstr, String key) {
        return !StringUtils.isEmpty(inputstr) && !StringUtils.isEmpty(key) ? (new SM4()).decrypt_cbc(inputstr, key) : null;
    }

    public static void main(String[] args) throws IOException {
        String plainText = "深圳市永兴元*&%￥#asd";
        String kkk = "0987654321qweasd";
        String cipherText = (new SM4()).encrypt_ecb(plainText, kkk);
        System.out.println("ECB加密: " + cipherText);
        String cipherText2 = (new SM4()).encrypt_ecb(plainText);
        System.out.println("ECB加密2: " + cipherText2);
        plainText = (new SM4()).decrypt_ecb(cipherText, kkk);
        System.out.println("ECB解密: " + plainText);
        plainText = (new SM4()).decrypt_ecb(cipherText2);
        System.out.println("ECB解密2: " + plainText);
        System.out.println("********************");
        cipherText = (new SM4()).encrypt_cbc(plainText, kkk);
        System.out.println("CBC加密: " + cipherText);
        plainText = (new SM4()).decrypt_cbc(cipherText, kkk);
        System.out.println("CBC解密: " + plainText);
        String[] str = new String[]{"测试001", "阿萨德003&&……%", "0987-UI9$", "+——2#4字段", "!@#$%^&*()", "?:>'QW", ",.》。《》"};

        for(int i = 0; i < str.length; ++i) {
            String encstr = EncryptService.getInstance().SM4Encrypt(str[i]);
            String decstr = EncryptService.getInstance().SM4DeEncrypt(encstr);
            System.out.println(str[i] + "-------加密：" + encstr + "--------解密：" + decstr);
        }

        cipherText = ByteUtils.toHexString((new SM4()).encrypt_ecb("发改委".getBytes(), ByteUtils.fromHexString("f2d746df9e0248a88e749c4039d5fa6c")));
        System.out.println("CBC加密: " + cipherText);
        String xml = "6c88e9d2c490eeb34efe9713040d37b09666716bb6f6a1f6d241062a9e3719d7987e97c137b15b438d8d333bd1c71f6c6d914303e4036853ddeaa1eea43b0143a7eb7df11cb290ddfced798e1cd61e05a98f7b054e492725c923c590cce9e0da6f4a32f5f7643e2d038e56f4708d92852abe6c6c26d73a1f74e829ba87c90635c37098fff5a814fd97a81691c7a28448a29d47743e143d28adcb10663acfc25be17dd1e7d2a0a15868dc94dd93e33e80c41c91ea2f97e0faecc0faea99d1291a173e0ba9403354e01fd68bac22f33b9c8e2249ba324d5c543edb4e4e897d78e3a53e1b32bcdfca5b9654eff2f7ec41b3a063bc4c5b7a531c54a9eb944633cb79a8273ad4d2d262151736a25289392622eb3b09d16253ab1847204b6cad88500d97d0803584c68af75355030fed336174e3be8016c56d04571d4e9972dcd6aff7fbd896399ae60dedf5c120143f25c2934f1e4c83de7836216065804197143aa477f1ade445b1758fd73e8f095ea940be32384b36c5d57535d13d80a2511b58c3ede480729b41124a4e8d5e85e6bf2d81f9062c399b78501e35118a65a856fff3309a5f4e851985c8057659d9042af196de66a1218f54fe9362dd3e157244fcb7b5f721e14d180712f888bd097cdc421e3c1b17cb1452d988c28429aa43412a2e87f9e78ad17795be02963b7e679d4d7f9ac0ec55b814cf3f5c867bb67a8ba911b634bb88020c6a73ab8e6a094064657393834b06775ff23e4bbdc54d46a3a6e41aba95cfe400fe728ec043ee3175253c5f35c1a3a79bd5f242dde7af9987075e601c900663591e8f1d9ba8fa578bedbe4cb9fcfd4c9d7d3e66907db79d2d19307b56cb668ba5d417ed4ddb9e7e947722fe4c5f729e63d6337f20d3800ff9738c12614dd3a7f6f266fff1f56e71066ae9f17fc5c216343b6c2032ce0c63e4fa0bec8994c4d8349390a71211aeae72b1f2e99b01cfdf98dc0b826dbdcd02db30e042e71367c4a32058d44411299a70800062ae5caf1b67a4abef1fe0e56a915f87c0b381c42824655a9e8d5b926d8db07dab1703cd098a2484342740d8050d27d68c5c7e410543acb79f72557098584000307994a2522154be85f4c5ccf0d3ac15bbc8a922ed88d578db433ae1bda7db598392fb8a6ed647c54d68b1147eb50884b1d263a750b033419b82e43e558792bc8a77de9918f459033a4bc1e33761f4f43a2c44d7f673d8e5bfb5ca711060ba2040e5f4c92c436966ae1b6c2f21d4b34cf8760ca82729e1d44eded4422ed7d57304c072b09f8aa7080b6415f9e9a4d0b7931de0148bf7af0edacf21d67288cea3224caffc5876e6d9a03139e6a5b46c93a9c5ce3d24f57544e944b8cfe61899d63d1b126e71a9bf06f1694da9635bbe1a3969a265f98cd48a375df22d49ca88dab3ff2484f0d415a0f56578b570374c6eba3031569790ed1204ae0ff207faadcb312f7bcb800c6e7eac152ae37bff8f287214e9fe49435a4496bcfc3a9aa4d28cbb4fc7b4cb930798b1f4e3cf163d7fda42ff1472c424abb180e98b9ed7b4fcbae1de79cd3fd983321ebe5ba72ed0592d1ab208fc9971147be738fa0df450c2b7f0dca53a52f0bd619b9f940892e1859999be909449a900934cd09970b1151249c6caea3862d6b0f1b70ccc018af6ae334507ccef348c4b4b37b26b253d4504639b63ec8bbf1492c08e748b03fc79d971303d8b29e047f1e038afd684c67d6762bc5d1190e9541f96b10aa8dbb6f3ffafd2c40f514ebdc1cdec7ead3c5b6153277bdb4d4eb38b87267040654c0fcaf8c2f06072ccbc86fe11e7f1224594f6f779be7a2cc6cbb9ec14d280c610acc88b587c456ca22cb165f7e2da01f5da2bd4c7072dc032ebb95fc0278bb6e309aa77fd09e3d7911eb6173dc0bdb35926d909831cd16168aff9bed5e3daa2b5cb0dbb3041478b721bceaefc6954a9f33d27fb4b2a2572bdedf9decc5c9d8d408da2e6a9b3f43c91986dde22cae8a63e0db258c0";
        plainText = new String((new SM4()).decrypt_ecb(ByteUtils.fromHexString(xml), ByteUtils.fromHexString("94cd4dd2cfa046b18506db56ab0e75f6")));
        System.out.println("ECB解密: " + plainText);
    }
}

