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

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
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
    @Test
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
}
