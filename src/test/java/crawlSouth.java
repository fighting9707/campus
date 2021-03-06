import com.campus.pojo.SouthPowerBuyData;
import com.campus.pojo.SouthPowerDetail;
import com.campus.pojo.SouthPowerUseData;
import net.sf.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class crawlSouth {
    //static String url = "http://letki.free.ngrok.cc/";
    static String url = "http://172.18.2.42:8000/";
    static float cost = 0.63f;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws IOException {
        String buildingId = "001001006";
        String roomId = "001001006001005";
        String password = "";
        HashMap headers = loginSouth(buildingId, roomId, password);
        SouthPowerDetail southPowerDetail = obtainDetail(headers, buildingId, roomId);
        obtainUseNowData(headers, southPowerDetail);
        obtainBuyData(headers,buildingId,roomId);
    }

    public static HashMap loginSouth(String buildingId, String roomId, String password) throws IOException {
        CloseableHttpResponse response = get(url, null);
        String cookie = response.getHeaders(
                "Set-Cookie"
        )[0].getValue();
        Pattern pattern = Pattern.compile("ASP.NET_SessionId=.*?;");
        Matcher m = pattern.matcher(cookie);
        if (m.find()) {
            cookie = m.group(0);
        }
        String content = getContent(response);
        Document doc = Jsoup.parse(content);
        String __EVENTTARGET = doc.select("#__EVENTTARGET").val().trim();
        String __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val().trim();
        String __LASTFOCUS = doc.select("#__LASTFOCUS").val().trim();
        String __VIEWSTATE = doc.select("#__VIEWSTATE").val().trim();
        String __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val().trim();
        String Radio1 = "1";
        String ScriptManager1 = "UpdatePanel1|Radio1$1";
        // 构建消息实体
        HashMap<String, String> map = new HashMap();
        map.put("__EVENTTARGET", __EVENTTARGET);
        map.put("__EVENTARGUMENT", __EVENTARGUMENT);
        map.put("__LASTFOCUS", __LASTFOCUS);
        map.put("__VIEWSTATE", __VIEWSTATE);
        map.put("__EVENTVALIDATION", __EVENTVALIDATION);
        map.put("Radio1", Radio1);
        map.put("ScriptManager1", ScriptManager1);
        HashMap headers = new HashMap();
        headers.put("Cookie", cookie);
        response = post(url, headers, map);
        content = getContent(response);
        //#################################################宿舍选择
        String txtjz2 = buildingId;//这里修改需要选择的宿舍
        String txtname2 = "001001001001001";//这里不能修改
        String txtpwd2 = "";
        //验证码
        String txtyzm2 = "";
        doc = Jsoup.parse(content);
        __EVENTTARGET = doc.select("#__EVENTTARGET").val().trim();
        __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val().trim();
        __LASTFOCUS = doc.select("#__LASTFOCUS").val().trim();
        __VIEWSTATE = doc.select("#__VIEWSTATE").val().trim();
        __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val().trim();
        Radio1 = "1";
        ScriptManager1 = "UpdatePanel1|Radio1$1";
        // 构建消息实体
        map = new HashMap();

        map.put("__EVENTTARGET", __EVENTTARGET);
        map.put("__EVENTARGUMENT", __EVENTARGUMENT);
        map.put("__LASTFOCUS", __LASTFOCUS);
        map.put("__VIEWSTATE", __VIEWSTATE);
        map.put("__EVENTVALIDATION", __EVENTVALIDATION);
        map.put("Radio1", Radio1);
        map.put("ScriptManager1", ScriptManager1);
        map.put("txtjz2", txtjz2);
        map.put("txtname2", txtname2);
        map.put("txtyzm2", txtyzm2);
        headers = new HashMap();
        headers.put("Cookie", cookie);
        response = post(url, headers, map);
        content = getContent(response);
        //##################################################################
        //登录
        doc = Jsoup.parse(content);
        __EVENTTARGET = doc.select("#__EVENTTARGET").val().trim();
        __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val().trim();
        __LASTFOCUS = doc.select("#__LASTFOCUS").val().trim();
        __VIEWSTATE = doc.select("#__VIEWSTATE").val().trim();
        __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val().trim();
        Radio1 = "1";
        // 构建消息实体
        map = new HashMap();
        txtjz2 = buildingId;//宿舍楼，这里要与前面的选择一致
        txtname2 = roomId;//选择宿舍楼宿舍层
        txtpwd2 = password;
        //验证码
        txtyzm2 = "2367";
        map.put("Radio1", Radio1);
        map.put("txtjz2", txtjz2);
        map.put("txtname2", txtname2);
        map.put("txtpwd2", txtpwd2);
        map.put("txtyzm2", txtyzm2);
        map.put("Button1", "");
        map.put("__EVENTTARGET", __EVENTTARGET);
        map.put("__EVENTARGUMENT", __EVENTARGUMENT);
        map.put("__LASTFOCUS", __LASTFOCUS);
        map.put("__VIEWSTATE", __VIEWSTATE);
        map.put("__EVENTVALIDATION", __EVENTVALIDATION);
        map.put("hidtime", "2018-08-19 17:46:01");

        headers = new HashMap();
        headers.put("Cookie", cookie);
        response = post(url, headers, map);
        content = getContent(response);
        String temp = "";
        for (int i = 0; i < response.getHeaders("Set-Cookie").length; i++) {
            temp += response.getHeaders("Set-Cookie")[i].getValue();
        }
        pattern = Pattern.compile("loginschool=.*?;");
        m = pattern.matcher(temp);
        if (m.find()) {
            cookie += m.group(0);
        }
        pattern = Pattern.compile(".ASPXAUTH=.*?;");
        m = pattern.matcher(temp);
        if (m.find()) {
            cookie += m.group(0);
        }
        headers.put("Cookie", cookie);
        return headers;

    }

    public static SouthPowerDetail obtainDetail(HashMap headers, String buildingId, String roomId) {
        CloseableHttpResponse response = get(url + "PowerMonitoring/ssjkSSSJCX2.aspx?id=73", headers);
        String content = getContent(response);
        //获取总余额
        float balance = 0;
        Pattern pattern = Pattern.compile("lblzye\",disabled:false,encodeValue:false,value:\"\\d*[.]\\d*\"");
        String temp = "";
        Matcher m = pattern.matcher(content);
        if (m.find()) {
            temp = m.group(0);
        }

        Pattern p = Pattern.compile("\\d*[.]\\d*");
        m = p.matcher(temp);
        if (m.find()) {
            balance = Float.parseFloat(m.group(0));
        }

        //获取本月总用电量
        float powerUserMonth = 0;
        pattern = Pattern.compile("lblbyzydl\",disabled:false,encodeValue:false,value:\".*?\"");
        temp = "";
        m = pattern.matcher(content);
        if (m.find()) {
            temp = m.group(0);
        }

        p = Pattern.compile("\\d*[.]\\d*");
        m = p.matcher(temp);
        if (m.find()) {
            powerUserMonth = Float.parseFloat(m.group(0));
        }
        SouthPowerDetail southPowerDetail = new SouthPowerDetail();
        southPowerDetail.setBalance(balance);
        southPowerDetail.setBuildingId(buildingId);
        southPowerDetail.setRoomId(roomId);
        southPowerDetail.setPowerUserMonth(powerUserMonth);
        return southPowerDetail;
    }

    public static List obtainUseNowData(HashMap headers, SouthPowerDetail southPowerDetail) {
        CloseableHttpResponse response = get(url + "UserAccountment/AccountDetails2.aspx?id=75", headers);
        String content = getContent(response);
        Document doc = Jsoup.parse(content);
        HashMap params = new HashMap();
        String __EVENTTARGET = doc.select("#__EVENTTARGET").val();
        String __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val();
        String __VIEWSTATE = doc.select("#__VIEWSTATE").val();
        String __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val();
        String hidJZ = doc.select("#hidJZ").val();
        Calendar cal = Calendar.getInstance();
        //设置当前时间
        cal.setTime(new Date());
        String endTime = sdf.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为本月的第一天
        String startTime = sdf.format(cal.getTime());
        params.put("__EVENTTARGET", "RegionPanel2$Region1$Toolbar1$ContentPanel1$btnSelect");
        params.put("__EVENTARGUMENT", __EVENTARGUMENT);
        params.put("__VIEWSTATE", __VIEWSTATE);
        params.put("__EVENTVALIDATION", __EVENTVALIDATION);
        params.put("hidJZ", hidJZ);
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$TextBox1", startTime);
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$TextBox2", endTime);
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$txtDBBH", "");
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$ddlCZFS", "----全部----");
        params.put("RegionPanel2$Region1$toolbarButtom$pagesize", "1");
        params.put("__box_page_state_changed", "false");
        params.put("__2_collapsed", "false");
        params.put("__6_selectedRows", "");
        params.put("__box_disabled_control_before_postback", "__10");
        params.put("__box_ajax_mark", "true");
        response = post(url + "UserAccountment/AccountDetails2.aspx?id=75", headers, params);
        content = getContent(response);
        Pattern pattern = Pattern.compile("box.__6_store.loadData.*?]]");
        String temp = "";
        Matcher m = pattern.matcher(content);
        if (m.find()) {
            temp = m.group(0);
        }
        if (!temp.equals("")) {
            temp = temp.split("\\(")[1];
            JSONArray list = JSONArray.fromObject(temp);
            Iterator it = list.iterator();
            ArrayList southList = new ArrayList();
            float balance = southPowerDetail.getBalance();
            while (it.hasNext()) {
                JSONArray list2 = JSONArray.fromObject(it.next().toString());
                SouthPowerUseData southPowerUseData = new SouthPowerUseData();
                southPowerUseData.setBuildingId(southPowerDetail.getBuildingId());
                southPowerUseData.setRoomId(southPowerDetail.getRoomId());
                southPowerUseData.setMoney(Float.parseFloat(list2.get(5).toString()));
                southPowerUseData.setResidue(balance / cost);
                southPowerUseData.setUseTime(list2.get(9).toString());
                southPowerUseData.setUsePower(Float.parseFloat(list2.get(5).toString()) / cost);
                balance = balance + Float.parseFloat(list2.get(5).toString());
                southList.add(southPowerUseData);
            }
            return southList;
        } else {
            return null;
        }

    }

    public static List obtainUseHistoryData(HashMap headers, String buildingId, String roomId, String beforeTime) {
        CloseableHttpResponse response = get(url + "UserAccountment/AccountDetails2.aspx?id=75", headers);
        String content = getContent(response);
        Document doc = Jsoup.parse(content);
        HashMap params = new HashMap();
        String __EVENTTARGET = doc.select("#__EVENTTARGET").val();
        String __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val();
        String __VIEWSTATE = doc.select("#__VIEWSTATE").val();
        String __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val();
        String hidJZ = doc.select("#hidJZ").val();
        Calendar cal = Calendar.getInstance();
        //设置当前时间
        cal.setTime(new Date());
        String startTime = beforeTime;
        String endTime = sdf.format(cal.getTime());
        params.put("__EVENTTARGET", "RegionPanel2$Region1$Toolbar1$ContentPanel1$btnSelect");
        params.put("__EVENTARGUMENT", __EVENTARGUMENT);
        params.put("__VIEWSTATE", __VIEWSTATE);
        params.put("__EVENTVALIDATION", __EVENTVALIDATION);
        params.put("hidJZ", hidJZ);
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$TextBox1", startTime);
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$TextBox2", endTime);
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$txtDBBH", "");
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$ddlCZFS", "----全部----");
        params.put("RegionPanel2$Region1$toolbarButtom$pagesize", "1");
        params.put("__box_page_state_changed", "false");
        params.put("__2_collapsed", "false");
        params.put("__6_selectedRows", "");
        params.put("__box_disabled_control_before_postback", "__10");
        params.put("__box_ajax_mark", "true");
        response = post(url + "UserAccountment/AccountDetails2.aspx?id=75", headers, params);
        content = getContent(response);
        Pattern pattern = Pattern.compile("box.__6_store.loadData.*?]]");
        String temp = "";
        Matcher m = pattern.matcher(content);
        if (m.find()) {
            temp = m.group(0);
        }
        temp = temp.split("\\(")[1];
        JSONArray list = JSONArray.fromObject(temp);
        Iterator it = list.iterator();
        ArrayList southList = new ArrayList();
        while (it.hasNext()) {
            JSONArray list2 = JSONArray.fromObject(it.hasNext());
            SouthPowerUseData southPowerUseData = new SouthPowerUseData();
            southPowerUseData.setBuildingId(buildingId);
            southPowerUseData.setRoomId(roomId);
            southPowerUseData.setMoney(Float.parseFloat(list2.get(5).toString()));
            southPowerUseData.setResidue(0);
            southPowerUseData.setUseTime(list2.get(9).toString());
            southPowerUseData.setUsePower(Float.parseFloat(list2.get(5).toString()) / cost);
            southList.add(southPowerUseData);
        }
        return southList;
    }

    public static List obtainBuyData(HashMap headers, String buildingId, String roomId) {
        CloseableHttpResponse response = get(url + "UserAccountment/manCZCX2.aspx?id=76", headers);
        String content = getContent(response);
        Document doc = Jsoup.parse(content);
        String __EVENTTARGET = doc.select("#__EVENTTARGET").val();
        String __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val();
        String __VIEWSTATE = doc.select("#__VIEWSTATE").val();
        String __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val();
        String hidJZ = doc.select("#hidJZ").val();
        String start = "2018-03-01";
        String end = "2018-08-25";
        HashMap params = new HashMap();
        params.put("__EVENTTARGET", "RegionPanel1$Region2$Panel1$Panel8$Toolbar1$ContentPanel1$btnSelect");
        params.put("__EVENTARGUMENT", __EVENTARGUMENT);
        params.put("__VIEWSTATE", __VIEWSTATE);
        params.put("__EVENTVALIDATION", __EVENTVALIDATION);
        params.put("hidJZ", hidJZ);
        params.put("RegionPanel1$Region2$Panel1$Panel8$Toolbar1$ContentPanel1$TextBox1", start);
        params.put("RegionPanel1$Region2$Panel1$Panel8$Toolbar1$ContentPanel1$TextBox2", end);
        params.put("RegionPanel1$Region2$Panel1$Panel8$Toolbar1$ContentPanel1$ddljllx", "3");
        params.put("RegionPanel1$Region2$Panel1$Panel8$Toolbar1$ContentPanel1$ddlZLLX", "全部");
        params.put("RegionPanel1$Region2$Panel1$Panel8$Toolbar2$pagesize", "1");
        params.put("__box_page_state_changed", "false");
        params.put("__14_collapsed", "false");
        params.put("__20_collapsed", "false");
        params.put("__44_hidden", "true");
        params.put("__44_collapsed", "false");
        params.put("__box_disabled_control_before_postback", "__25");
        params.put("__box_ajax_mark", "true");
        response = post(url + "UserAccountment/manCZCX2.aspx?id=76", headers, params);
        content = getContent(response);
        Pattern pattern = Pattern.compile("box.__20_store.loadData.*?]]");
        String temp = "";
        Matcher m = pattern.matcher(content);
        if (m.find()) {
            temp = m.group(0);
        }
        if (!temp.equals("")) {
            temp = temp.split("\\(")[1];
            JSONArray list = JSONArray.fromObject(temp);
            Iterator it = list.iterator();
            ArrayList southList = new ArrayList();
            while (it.hasNext()) {
                JSONArray list2 = JSONArray.fromObject(it.next().toString());
                SouthPowerBuyData southPowerBuyData = new SouthPowerBuyData();
                southPowerBuyData.setRoomName(list2.get(0).toString());
                southPowerBuyData.setAmmeter(list2.get(1).toString());
                southPowerBuyData.setBuyType(list2.get(2).toString());
                southPowerBuyData.setNumber(Integer.parseInt(list2.get(3).toString()));
                southPowerBuyData.setMoney(Float.parseFloat(list2.get(4).toString()));
                southPowerBuyData.setBuyer(list2.get(5).toString());
                southPowerBuyData.setBuyTime(list2.get(6).toString());
                southPowerBuyData.setDown(list2.get(7).toString());
                southPowerBuyData.setDownTime(list2.get(8).toString());
                southList.add(southPowerBuyData);
            }
            return southList;
        } else {
            return null;
        }
    }

    public static CloseableHttpResponse get(String url, HashMap<String, String> headers) {
        HttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            if (headers != null) {
                Set<Map.Entry<String, String>> entrySet = headers.entrySet();
                Iterator<Map.Entry<String, String>> it = entrySet.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    httpGet.setHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }

            response = (CloseableHttpResponse) client.execute(httpGet);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static CloseableHttpResponse post(String url, HashMap<String, String> headers, HashMap<String, String> params) {
        HttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
            }
        }

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            Iterator<Map.Entry<String, String>> it = entrySet.iterator();
            while (it.hasNext()) {
                Map.Entry entry = it.next();
                httpPost.setHeader(entry.getKey().toString(), entry.getValue().toString());
            }
            response = (CloseableHttpResponse) client.execute(httpPost);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public static String getContent(CloseableHttpResponse response) {
        //4.获取响应的实体内容，就是我们所要抓取得网页内容
        HttpEntity entity = response.getEntity();

        //5.将其打印到控制台上面
        //方法一：使用EntityUtils
        String content = "";
        if (entity != null) {
            try {
                content += EntityUtils.toString(entity, "utf-8");
                //释放实体
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

}
