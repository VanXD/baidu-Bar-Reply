/*
 * tiebaSend.do
 */
package tb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class tb extends HttpServlet{
	
	int count = 0;
	static String[] words;
	PrintWriter writer;
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		String text = textInLine();
		fliteDot(text);
		System.out.println("init success!");
	}
	
	private void fliteDot(String text) {
		this.words = text.split("。");
	}

	private String textInLine() {
		File f = new File("file/a.txt");
		System.out.println(f.getAbsolutePath());
		StringBuilder str = new StringBuilder("");
		String strTmp = "";
		try{
			Reader in = new FileReader(f);
			BufferedReader bufr = new BufferedReader(in);
			while((strTmp= bufr.readLine()) != null){
				System.out.println(strTmp);
				str.append(strTmp);
			}
			bufr.close();
			in.close();
			Writer out = new FileWriter(f);
			BufferedWriter bufw = new BufferedWriter(out);
			bufw.write(str.toString());
			bufw.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return str.toString();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{
		response.setHeader("Content-type", "charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		writer = response.getWriter();
		String cookie = request.getParameter("cookie");
		String para = request.getParameter("para");				
		printCount();
		System.out
				.println("-------------------------------------------------------------------------");
		System.out.println(count % words.length);
		System.out
				.println("-----------------------------------------------------------------------");
		System.out.println(para);
		LOLBar(cookie, fixPara(para) + "&content=" + words[count % words.length]);
				
	}
	protected String fixPara(String s) {
		s = replacePara(s, "http://[\\s\\S]*?[\\?]");
		s = replacePara(s, "content=[\\S\\s]*?&");
		return s;
	}

	private String replacePara(String s, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(s);
		return m.replaceAll("");
	}
	protected void printCount() {
		System.out.println("count:" + count++);
	}
	public void LOLBar(String cookie, String p){
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
	        //    写入的POST数据
	        OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
	        //这戳是POST的参数
	        osw.write(p);
	        osw.flush();
	        osw.close();
	        // 读取响应数据
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(conn.getInputStream()));
	        String s;
	        while ((s = in.readLine()) != null){
	        	System.out.println(s);
	        	writer.write(s);
	        }
	            
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
