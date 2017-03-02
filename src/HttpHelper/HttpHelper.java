package HttpHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import Util.ConvertTimeUtil;

import com.alibaba.fastjson.JSON;

import net.sf.json.processors.JsonBeanProcessor;
import Bean.brzcsj;
import jdbc.Dao;



public class HttpHelper {
	public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            java.net.URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * ��ָ�� URL ����POST����������
     * 
     * @param url
     *            ��������� URL
     * @param param
     *            ��������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return ����Զ����Դ����Ӧ���
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            java.net.URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            out.print(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
//���÷���
    public static void main(String[] args) {
    	String kssj="2016-07-23 09:50:00.000";
    	String jssj="2016-07-24 09:50:00.000";
    	
    	String sj1=ConvertTimeUtil.dateToStamp(kssj);
    	String sj2=ConvertTimeUtil.dateToStamp(jssj);
    	brzcsj sjBrzcsj=new brzcsj(sj1,sj2);
    	String json=JSON.toJSONString(sjBrzcsj);
        //获取门诊诊断
        //String s=HttpHelper.sendGet("http://192.168.40.254/Hospital/platformservlet","sql="+json+"&cs=3");
       // System.out.println(s);
    	//获取门诊处方
    	// String s=HttpHelper.sendGet("http://192.168.40.254/Hospital/platformservlet","sql="+json+"&cs=4");
    	 //获取门诊配方
    	//String s=HttpHelper.sendGet("http://192.168.40.254/Hospital/platformservlet","sql="+json+"&cs=5");
    	//获取门诊病人费用
    	//String s=HttpHelper.sendGet("http://192.168.40.254/Hospital/platformservlet","sql="+json+"&cs=6");
    	//获取检验明细
    	String s=HttpHelper.sendPost("http://192.168.40.254/Hospital/platformservlet","sql="+json+"&cs=7");
    	 System.out.println(s);
        Dao dao =Dao.getInstance();
        dao.insert(s);
        //���� POST ����
    //    String sr=HttpHelper.sendPost("http://localhost:6144/Home/RequestPostString", "key=123&v=456");
     //   System.out.println(sr);
    }
 
    
     
}

