package com.example.springboot.demo.util.encrypt;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.longrise.LEAP.Base.Encrypt;

import com.longrise.LEAP.Base.Global;

public class EncryptService {
    private static EncryptService instance = new EncryptService();
    private IEncrypt md5 = null;
    private IEncrypt des = null;
    private IEncrypt sm4 = null;

    private EncryptService() {
        try {
            this.md5 = (IEncrypt)Global.getInstance().getCoreClassLoder().loadClass("com.longrise.LEAP.Base.Encrypt.MD5").getMethod("getInstance").invoke((Object)null);
            this.des = (IEncrypt)Global.getInstance().getCoreClassLoder().loadClass("com.longrise.LEAP.Base.Encrypt.DESService").getMethod("getInstance").invoke((Object)null);
            this.sm4 = (IEncrypt)Global.getInstance().getCoreClassLoder().loadClass("com.longrise.LEAP.Base.Encrypt.SM4").getMethod("getInstance").invoke((Object)null);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public static EncryptService getInstance() {
        return instance;
    }

    public String MD5Encrypt(String str) {
        if (str == null) {
            return null;
        } else {
            return str.equals("") ? "" : this.md5.encrypt(str);
        }
    }

    public String DESEncrypt(String str) {
        if (str == null) {
            return null;
        } else {
            return str.equals("") ? "" : this.des.encrypt(str);
        }
    }

    public String DESEncrypt(String str, String key) {
        if (str == null) {
            return null;
        } else {
            return str.equals("") ? "" : this.des.encrypt(str, key);
        }
    }

    public String DESDeEncrypt(String str) {
        if (str == null) {
            return null;
        } else {
            return str.equals("") ? "" : this.des.decrypt(str);
        }
    }

    public String DESDeEncrypt(String str, String key) {
        if (str == null) {
            return null;
        } else {
            return str.equals("") ? "" : this.des.decrypt(str, key);
        }
    }

    public String SM4Encrypt(String str) {
        if (str == null) {
            return null;
        } else {
            return str.equals("") ? "" : this.sm4.encrypt(str);
        }
    }

    public String SM4Encrypt(String str, String key) {
        if (str == null) {
            return null;
        } else {
            return str.equals("") ? "" : this.sm4.encrypt(str, key);
        }
    }

    public String SM4DeEncrypt(String str) {
        if (str == null) {
            return null;
        } else {
            return str.equals("") ? "" : this.sm4.decrypt(str);
        }
    }

    public String SM4DeEncrypt(String str, String key) {
        if (str == null) {
            return null;
        } else {
            return str.equals("") ? "" : this.sm4.decrypt(str, key);
        }
    }
}

