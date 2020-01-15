import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.imooc.Application;
import com.imooc.controller.HelloWorld;
import com.imooc.service.impl.TestTransactionImpl;
import com.test.starter.TestService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestController {
    private MockMvc mockMvc;

    @Autowired
    private TestService testService;

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
    public void getMsgFromCustomStarter(){
        String cdt = testService.sayHello("cdt");
        System.out.println(cdt);
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
}
