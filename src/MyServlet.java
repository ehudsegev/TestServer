/**
 * Created by segeve on 11/22/2015.
 */

import dbmock.AllStatement;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(
        name = "MyServlet",
        urlPatterns = {"/*"}
)
public class MyServlet extends HttpServlet{
    private Category logger = null;

    private Connection connection = null;

    private Map<Integer, SR> srmap = new HashMap<Integer, SR>();
    public void UpdateSRMap(String srMapString) {

            if(srMapString!= null && !srMapString.isEmpty()) {
                String[] params = srMapString.split(";");
                srmap.clear();
                for (String param : params) {
                    SR sr = new SR(param);

                    if(!srmap.containsKey(sr.srid)) {
                        srmap.put(sr.srid, sr);
                        System.out.println("Building map: "+sr.Debug());
                    }
                }
                
            }
            if(srmap.get(0)==null)
            {
                srmap.put(0, new SR("0,4,5,100,0,0,0"));
            }

    }
    public MyServlet() {
        String srMapString = "0,4,5,100,0,0,0";
        logger = new Category();

        try {
            Class.forName("dbmock.DummyJdbcDriver");
            connection = DriverManager.getConnection("DB_URL", "USER", "PASS");
            UpdateSRMap(srMapString);

        } catch (ClassNotFoundException cnfe) {

        } catch (SQLException se) {

        }
    }
    public MyServlet(String srMapString) {

        logger = new Category();

        try {
            Class.forName("dbmock.DummyJdbcDriver");
            connection = DriverManager.getConnection("DB_URL", "USER", "PASS");
            UpdateSRMap(srMapString);

        } catch (ClassNotFoundException cnfe) {

        } catch (SQLException se) {

        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String requri = request.getRequestURI();
        logger.forcedLog(MyServlet.class.getName(), Level.INFO, "This is info : " + requri, null);
        //System.out.println( httpExchange.getRequestURI());
        //?dp=4&usedb=false&duration=5&endtime=500

        String reshtml = "This is the response";
        try {
            Map<String, String> urlparams = getQueryMap(request.getQueryString());
            //String path  = httpExchange.getRequestURI().getPath();
            //String shortpath = getPathFrom(path,2);
            int srid = 0;

            SR sr = null;
            if (urlparams.containsKey("srid")) {
                srid = Integer.valueOf(urlparams.get("srid"));

                if(srmap.containsKey(srid))
                {
                    sr = srmap.get(srid);
                }
                else {
                    sr = srmap.get(0);
                }
            }

            //System.out.println(sr.Debug());
            try {
                MethodLevel(sr, sr.cpdeep);
            }catch(Exception ex)
            {

            }

            if(!requri.contains("ajax"))
            {
                reshtml="<!DOCTYPE HTML PUBLIC \"-//W3C//HTML 4.0//EN\">\n" +
                        "<html><head><title></title>\n" +
                        "\t</head>\n" +
                        "\t<body>\n" +
                        "\t\n" +
                        "\t    <p>\n" +
                        "            UDI Tool</p>\n" +
                        "        <p>\n" +
                        "            <span style=\"font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;mso-ascii-theme-font:\n" +
                        "minor-latin;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;\n" +
                        "mso-hansi-theme-font:minor-latin;mso-bidi-font-family:Arial;mso-bidi-theme-font:\n" +
                        "minor-bidi\">&nbsp;<o:p></o:p></span></p>\n" +
                        "        <p>\n" +
                        "            <span style=\"font-size:11.0pt;font-family:&quot;Calibri&quot;,sans-serif;mso-ascii-theme-font:\n" +
                        "minor-latin;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;\n" +
                        "mso-hansi-theme-font:minor-latin;mso-bidi-font-family:Arial;mso-bidi-theme-font:\n" +
                        "minor-bidi\">This application is designed to provide synthetic data for Testing &amp; Demo \n" +
                        "            Purposes of the �AppPulse Trace� Product.<o:p></o:p></span></p>\n" +
                        "\t</body>\n" +
                        "</html>";

            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            //baseRequest.setHandled(true);
            reshtml = instrumentedString(reshtml);
            response.getWriter().println(reshtml);


        } catch (Exception ex) {

        }
    }

    public String instrumentedString(String str) {
        return str;
    }
    
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	if(req.getRequestURI().contains("initsrmap"))
    	{
	    	String body = "";//req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
	    	UpdateSRMap(body);
	    	if(srmap.size() == 0)
            {
            	UpdateSRMap("0,4,5,100,0,0,0");
            }
    	}
	}
	public Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
    public String getPathFrom(String path,int start) {
        String shortpath = "/";
        String[] params = path.split("/");
        Map<String, String> map = new HashMap<String, String>();
        int index = 1;
        for (String param : params) {
            if(param != "" && index >= start)
            {
                shortpath = shortpath+"/"+param;
                index++;
            }
        }
        return shortpath;
    }


    public void MyOutboundCall(HttpURLConnection urlConnection)
    {
        try {

            // create a urlconnection object

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                //content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch (Exception se) {

        }
    }

    public void MethodLevel(SR sr,int deep) {
        try {

            logger.forcedLog(MyServlet.class.getName(), Level.INFO, "Inside MethodLevel : " + deep, null);
            if (deep > 0) {
                Thread.sleep(sr.cpMethodTime);
                MethodLevel(sr,deep - 1);

            } else {

                if (sr.sqlid > 0) {
                    try {
                        Statement statement = connection.createStatement();
                        ((AllStatement)statement).queryTimeMilliSec = sr.cpEndcpMethodTime;
                        ResultSet resultSet = statement.executeQuery("select /*+label(page_perf_over_time_from_raw_pastHour)*/ (FLOOR((timestamplong-60000)/120000)*120000+60000) as timestamp_long, sum(loadinmillis) as page_sum_end_of_resp_load_in_millis , count(*) as page_count, avg(loadinmillis) as page_avg_end_of_resp_load_in_millis , sum(total_load_time) as page_sum_load_in_millis , avg(total_load_time) as page_avg_load_in_millis , avg(redirect_duration) as page_avg_redirect_duration , avg(network_duration) as page_avg_network_duration , avg(request_duration) as page_avg_request_duration , avg(download_duration) as page_avg_download_duration , avg(dom_duration) as page_avg_dom_duration , avg(render_duration) as page_avg_render_duration from pages_raw where applicationid = '1004'::Integer and srid = '7955693342782706376'::Integer and timestamplong >= '1448964900000'::Integer and timestamplong < '1448968500000'::Integer group by (FLOOR((timestamplong-60000)/120000)*120000+60000) order by timestamp_long");
                    } catch (SQLException se) {

                    }
                }
                else {
                    if (sr.obport > 0) {
                        //System.out.println("Sending outbound request to port: "+sr.obport);
                        try {
                            URL url = new URL("http://localhost:" + sr.obport + "/uditool"+sr.obport + String.format("%03d", sr.srid)+"/ajax001?srid=" + sr.srid);

                            // create a urlconnection object
                            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                            MyOutboundCall(urlConnection);
                        }
                        catch (Exception se) {

                        }
                        //MyHttpClientSend("http", "localhost", sr.obport, "/fe/be/" + sr.srid + "/" + sr.obport + "?srid=" + sr.srid, null);
                    }else
                    {
                        Thread.sleep(sr.cpEndcpMethodTime);
                    }
                    if (sr.excid > 0) {
                        throw new EmptyStackException();
                    }
                }
            }
        }catch(InterruptedException ie)
        {}

    }

}

