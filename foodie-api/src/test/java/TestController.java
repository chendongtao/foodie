import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.imooc.Application;
import com.imooc.controller.HelloWorld;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.Users;
import com.imooc.service.impl.TestTransactionImpl;
import com.imooc.utils.IMOOCJSONResult;
import com.sun.org.apache.xpath.internal.operations.Mult;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
public class TestController {
    private MockMvc mockMvc;

    @Autowired
    private TestTransactionImpl testTransactionImp;


    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloWorld()).build();
    }
    @Test
    public void getHello() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/postHello?name=cdt").accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print());
        mockMvc.perform(MockMvcRequestBuilders.post("/postHello?name=cdt").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().string("cdt"));
    }
    @Test
    public void testCalendar() throws ParseException {
        String start ="2020-01-01";
        String end ="2020-02-29";
        SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
        Date startDate =sf.parse(start);
        Date endDate =sf.parse(end);


        int monthInterval = getMonthInterval(startDate, endDate);
        System.out.println(monthInterval);

    }
    @Test
    public void testListSub(){
        List list=new ArrayList();
        list.add("323");
        list.add("3121223");
        List list1 = list.subList(0, 1);
        System.out.println(list1);
    }
    /**
     * 获得日期是一年的第几周，返回值从1开始.
     * 如果周日期跨年，则那周属于下一年的第一周
     * 与mysql的YEARWEEK(CREATED_TIME,1)按周分组查询一致
     * 已改为中国习惯，1 是Monday，而不是Sunday
     */
    public static int getWeekOfYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getMonthInterval(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }
    @Test
    public void testTransaction(){
        testTransactionImp.testTransaction();
    }
    @Test
    public void testPageHelperPage(){
        Page page =new Page();
        page.setPageNum(1);
        page.setPageSize(10);
        Page page1 =page;
        int pageNum = page1.getPageNum();
        System.out.println(pageNum);
    }
//    @Test
    public static void main(String[] args) throws Exception {
        String url ="http://localhost:8088/orders/testPostForObject";
        RestTemplate restTemplate =new RestTemplate();
        MultiValueMap multiValueMap =new LinkedMultiValueMap();
        multiValueMap.add("key","lskdfkjsdf");
        Map map = restTemplate.postForObject(url, multiValueMap, Map.class);
        System.out.println(map);
//        Map map =new TreeMap();
//        map.put("chen","ccccc");
//        map.put("zhong","zhhhhh");
//        map.put("zzhoo","zzzzzz");
//        map.put("he","hhhhhh");
//        Set set = map.entrySet();
//        Iterator iterator = set.iterator();
//        StringBuffer sb =new StringBuffer();
//        while (iterator.hasNext()){
//            Map.Entry entry = (Map.Entry)iterator.next();
//            String key =(String) entry.getKey();
//            Object value = entry.getValue();
//            if (Objects.nonNull(value) &&!"".equals(value)){
//                sb.append(key+"="+value+"&");
//            }
//        }
//        String s =sb.toString();
//        s = s.substring(0,s.length() - 1);
//        System.out.println(s);
    }

    public static String object2Xml(Object ro, Class<?> types) throws Exception {
        if (null == ro) {
            return null;
        }
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xstream.alias("xml", types);
        return xstream.toXML(ro);
    }
    @Test
    public void test01(){
        String creditlimit = mergeUrl("http://", "creditlimit","wfClient/shake?id={0}&signature={1}&timestamp={2}&random={3}");
        System.out.println(creditlimit);
    }
    public static  String mergeUrl(String... urls){
        StringBuilder sb = new StringBuilder();
        /*循环取出对应的url部分*/
        for(String url : urls){

            /*判断url部分是否为空，空就直接拼接下一个*/
            if(StringUtils.isBlank(url)) continue;
            /*判断是否是以“/”结尾，是的话就去掉这个结尾*/
            if(url.endsWith("/")){
                url = url.substring(0, url.length() - 1);
            }

            /*判断是否是第一次进来*/
            if(StringUtils.isBlank(sb)){
                sb.append(url);
            }
            else
            {
                /*判断是否是以“/”开始，是的话就直接进行连接，不是就加上*/
                if(url.startsWith("/")){
                    sb.append(url);
                }
                else
                {
                    sb.append("/").append(url);
                }
            }
        }
        return sb.toString();
    }
}
