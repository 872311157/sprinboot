package com.example.springboot.demo.controller;

import com.example.springboot.demo.service.TestService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping("/test1")
    public void tset(){
        List<Map<String, Object>> maps = this.testService.queryTable("userinfo");
        for (Map<String, Object> map : maps){
            for (String key : map.keySet()){
                String value = map.get(key).toString();
                System.out.println(value);
            }
        }
    }

    /**
     * 二维码
     */
    @RequestMapping("/code")
    public void createQRCode(){
        //展示页面地址
        try {
            String pageUrl = "www.baidu.com";
            //生成二维码图片
            Map<EncodeHintType, Object> hints = new HashMap();
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = (new QRCodeWriter()).encode(pageUrl, BarcodeFormat.QR_CODE, 350, 350, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height+50, 2);
            for(int x = 0; x < width; ++x) {
                for(int y = 0; y < height; ++y) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }
            //添加文字
            String text = "二维码";
            String pressText = new String(text.getBytes(), "utf-8");
            Graphics g = image.createGraphics();
            g.setColor(Color.BLUE);
            Font font = new Font("宋体", Font.PLAIN, 50);
            FontMetrics metrics = g.getFontMetrics(font);
            // 文字在图片中的坐标 这里设置在中间
            int startX = (image.getWidth() - metrics.stringWidth(pressText)) / 2;
            int startY = image.getHeight();
            g.setFont(font);
            g.drawString(pressText, startX, startY);
            g.dispose();

            String destPath = "/home/longrise/e/test";
            File file = new File(destPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            destPath = destPath + "/test.png";
            File ewfile = new File(destPath);
            if (ewfile.exists()) {
                ewfile.delete();
            }
            boolean flag = ImageIO.write(image, "png", ewfile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
