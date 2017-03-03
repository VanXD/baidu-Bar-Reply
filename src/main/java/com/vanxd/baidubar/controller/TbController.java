/*
 * tiebaSend.do
 */
package com.vanxd.baidubar.controller;

import com.vanxd.baidubar.component.MessagePool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class TbController {

	@RequestMapping("/tiebaSend.do")
	@ResponseBody
	public String tiebaSend(HttpServletResponse response, String cookie, String para)
		throws IOException, ServletException{
		response.setHeader("Content-type", "charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String[] sentences = MessagePool.getSentences();
		Integer count = MessagePool.getCount();
		return sendMessage(cookie, fixPara(para) + "&content=" + sentences[count % sentences.length]);
	}

	private String sendMessage(String cookie, String p){
		try {
			String urlStr = "http://tieba.baidu.com/f/commit/post/add";
	        URL url;	
			url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setDoOutput(true);  
	        conn.setDoInput(true);    
	        conn.setRequestMethod("POST");    
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	        conn.setRequestProperty("Cookie", cookie);
	        conn.connect();
	        //写入的POST数据
	        OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
	        //这里是POST的参数
	        osw.write(p);
	        osw.flush();
	        osw.close();
	        // 读取响应数据
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(conn.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String tmp = "";
	        while ((tmp = in.readLine()) != null){
	        	sb.append(tmp);
	        }
			System.out.println(sb);
			return sb.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private String fixPara(String s) {
		s = replacePara(s, "http://[\\s\\S]*?[\\?]");
		s = replacePara(s, "content=[\\S\\s]*?&");
		return s;
	}
	private String replacePara(String s, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(s);
		return m.replaceAll("");
	}
}
