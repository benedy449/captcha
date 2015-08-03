package com.checkpingcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.framework.control.BaseAction;
import com.framework.control.ServiceResult;
import com.framework.utils.Log;
import com.framework.utils.RandomStringGenerator;


public class Checkpingcode extends BaseAction{
	private static final String CHECK_PIN_CODE_NAME = "checkpingcode.do";
	public static Random random = new Random();
	
	public ServiceResult checkpingcode(Map<String, Object> params, HttpServletRequest request) {
		Map<String, BufferedImage> imageMap = new LinkedHashMap<String, BufferedImage> ();
		String s = RandomStringGenerator.generateRandomString(4, RandomStringGenerator.Mode.ALPHANUMERIC);

		//session
		HttpSession session = request.getSession();
		session.setAttribute("pincode", s);
		//session end
		
        int width = s.length()*35; //前後 各10 + 每個字*10
        int height = 60;
        Color cBackground = new Color(76, 76, 76);
        Color cFont2 = new Color(226, 226, 226);

        BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB); //圖片空間
        Graphics2D g = img.createGraphics(); //
        //文字型態
        g.setFont(new Font(Font.SERIF, Font.BOLD, 50));

        //背景
        g.setColor(cBackground); // set color
        g.fillRect(0, 0, width, height); // background color
        g.setColor(cFont2); // text color
        int z=0,mm=10;

       for (int y = 0 ; y < height; y++){
                for (int x = 0 ; x < width; x++){
                if(z<mm){
                    g.drawRect(x, y, 1, 1);
                }
            }
            if(z<mm){
                z++;
            }else{
                y+=mm;
                z=0;
            }
        }
        //文字
        int q=random.nextInt(2);
        for(int i = 0; i< s.length(); i++) {
            Color cFont1 = new Color(random.nextInt(255), 200,random.nextInt(255));
            g.setColor(cFont1); // text color
            int x = 35*i;
            //旋轉
            double r = Math.random()*0.20;

            if(q==0){
                g.rotate(-r);
            }else{
                g.rotate(r);
            }

            g.drawString(String.valueOf(s.charAt(i)), x , 50); // text

            if(q==0){
                g.rotate(r);
            }else{
                g.rotate(-r);
            }
            q=1-q;

        }
        //雜訊撒點
        Color cFont3 = new Color(random.nextInt(255), random.nextInt(255),random.nextInt(255));
        g.setColor(cFont3); // text color
        for (int i = 0, n = random.nextInt(200); i < n; i++){
            g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
        }
   
        imageMap.put("ckImg",img);
		
		ServiceResult serviceResult = new ServiceResult();
		serviceResult.setOutputResult(imageMap);	
		serviceResult.setResultFlag(true);
		return serviceResult;
		
	}
	
	@Override
	protected ServiceResult doService(String serviceName, Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		if (serviceName.compareToIgnoreCase(CHECK_PIN_CODE_NAME) == 0) {
            return checkpingcode(params, request);
        }
		
        Log.error(this, "doService", String.format("service(%s) not found", serviceName));
        ServiceResult result = new ServiceResult();
        result.setResultFlag(false);
        result.setErrorMessage(String.format("無符合的服務[%s]", serviceName), null);
		return result;
	}
}
