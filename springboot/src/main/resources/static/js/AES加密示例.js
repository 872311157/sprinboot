window.onload=function(){
	debugger
	var url = "http://192.168.5.210:8090/LROA/restservices/LROA/lroa_searcHumanLroadala/query";
	var userid = "a997a25dbb6a4f5aa65cc87637a09e23";
	
	var aesEncrypt = CryptoJS.AES.encrypt("Message", "Secret Passphrase");
    console.log("aesEncrypt = ", aesEncrypt.iv.toString(CryptoJS.enc.Hex));
    var aesDecrypt = CryptoJS.AES.decrypt(aesEncrypt, "Secret Passphrase");
    console.log("aesDecrypt = %s", aesDecrypt.toString(CryptoJS.enc.Utf8));
	
	userid = this.encrypt(userid);
	console.log("加密后" + userid);
	userid = this.decrypt(userid);
	console.log("解密后" + userid);
	
	
	var md5 = CryptoJS.MD5("Message").toString(CryptoJS.enc.Hex);
    console.log("md5 = %s", md5);

    var sHA1 = CryptoJS.SHA1("Message").toString(CryptoJS.enc.Hex);
    console.log("sHA1 = %s", sHA1);

    var sHA256 = CryptoJS.SHA256("Message").toString(CryptoJS.enc.Hex);
    console.log("sHA256 = %s", sHA256);

    var hmacMD5 = CryptoJS.HmacMD5("Message", "Secret Passphrase").toString(CryptoJS.enc.Hex);
    console.log("hmacMD5 = %s", hmacMD5);

    var hmacSHA1 = CryptoJS.HmacSHA1("Message", "Secret Passphrase").toString(CryptoJS.enc.Hex);
    console.log("hmacSHA1 = %s", hmacSHA1);

    var aesEncrypt = CryptoJS.AES.encrypt("Message", "Secret Passphrase");
    console.log("aesEncrypt = %s", aesEncrypt.iv.toString(CryptoJS.enc.Hex));

    var aesDecrypt = CryptoJS.AES.decrypt(aesEncrypt, "Secret Passphrase");
    console.log("aesDecrypt = %s", aesDecrypt.toString(CryptoJS.enc.Utf8));

    // base64 encrypt
    var rawStr = "hello world!";
    var wordArray = CryptoJS.enc.Utf8.parse(rawStr);
    var base64 = CryptoJS.enc.Base64.stringify(wordArray);
    console.log('base64Encrypt = ', base64);

    // base64 decrypt
    var parsedWordArray = CryptoJS.enc.Base64.parse(base64);
    var parsedStr = parsedWordArray.toString(CryptoJS.enc.Utf8);
    console.log('base64Decrypt = ',parsedStr);

	
//	var user = {'userid': userid, 'key':_h5ensid, 'iv':_h5ensid};
//	url = url + "?par=" + JSON.stringify(user);
//	$.ajax({
//        type: "post",
//        async: false,//同步，异步
//        url: url, //请求的服务端地址
//        dataType: "json",
//        success:function(data){
//        	console.log(data);
//        },
//        error:function(i, s, e){
//            alert('error'); //错误的处理
//        }
//    });
}

function FN() {
}

FN.prototype = {
    getUUID: function (len, radix) {
        var chars = '0123456789abcdefghijklmnopqrstuvwxyz'.split(''),
            uuid = [],
            i;
        radix = radix || chars.length;

        if (len) {
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
        } else {
            var r;
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        return uuid.join('');
    }
};

//创建全局通用对象
var BB = new FN();
var _h5ensid = BB.getUUID(16, 16);
//加密方法
this.encrypt = function(data) {
    var key = CryptoJS.enc.Utf8.parse(_h5ensid);
    var iv = CryptoJS.enc.Utf8.parse(_h5ensid);
    return CryptoJS.AES.encrypt(data, key, {
        iv: iv,
        mode: CryptoJS.mode.CBC
    }).toString();
}
//解密
this.decrypt = function(data){
	var key = CryptoJS.enc.Utf8.parse(_h5ensid);
	var iv = CryptoJS.enc.Utf8.parse(_h5ensid);
	var decrypted = CryptoJS.AES.decrypt(data, key, {
	    iv: iv, 
	    mode: CryptoJS.mode.CBC
	});
	return CryptoJS.enc.Utf8.stringify(decrypted);
}

