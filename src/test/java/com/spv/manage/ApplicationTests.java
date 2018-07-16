package com.spv.manage;

import com.linkspring.spv.restful.UserTestController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// junit 测试

@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest(classes = MockServletContext.class) 
@WebAppConfiguration 
public class ApplicationTests { 
 
	private MockMvc mvc; 
 
	@Before 
	public void setUp() throws Exception { 
		mvc = MockMvcBuilders.standaloneSetup(new UserTestController()).build();
	} 
 
	@Test 
	public void testUserController() throws Exception { 
		/**Junit 单元测试1*/
		String url = "/users/getHello";//访问url
	    String expectedResult = "Hello";//预期返回结果
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
	            .andReturn();      
	      //访问返回状态
	    int status = mvcResult.getResponse().getStatus();
	      //接口返回结果
	    String content = mvcResult.getResponse().getContentAsString();
	      //打印结果和状态
	      //System.out.println(status);
	      //System.out.println(content);
	      //断言预期结果和状态
	      Assert.assertTrue("错误", status == 200);
	      Assert.assertFalse("错误", status != 200);
	      Assert.assertTrue("数据一致", expectedResult.equals(content));
	      Assert.assertFalse("数据不一致", !expectedResult.equals(content));
		
		
       /* // 测试UserController 
		RequestBuilder request = null; 
 
		// 1、get查一下user列表，应该为空 
		request = get("/users/"); 
		mvc.perform(request) 
				.andExpect(status().isOk()) 
				.andExpect(content().string(equalTo("[]"))); 
 
		// 2、post提交一个user 
		request = post("/users/") 
				.param("id", "1") 
				.param("name", "测试大师") 
				.param("age", "20"); 
		mvc.perform(request) 
		        .andExpect(content().string(equalTo("success"))); 
 
		// 3、get获取user列表，应该有刚才插入的数据 
		request = get("/users/"); 
		mvc.perform(request) 
				.andExpect(status().isOk()) 
				.andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]"))); 
 
		// 4、put修改id为1的user 
		request = put("/users/1") 
				.param("name", "测试终极大师") 
				.param("age", "30"); 
		mvc.perform(request) 
				.andExpect(content().string(equalTo("success"))); 
 
		// 5、get一个id为1的user 
		request = get("/users/1"); 
		mvc.perform(request) 
				.andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}"))); 
 
		// 6、del删除id为1的user 
		request = delete("/users/1"); 
		mvc.perform(request) 
				.andExpect(content().string(equalTo("success"))); 
 
		// 7、get查一下user列表，应该为空 
		request = get("/users/"); 
		mvc.perform(request) 
				.andExpect(status().isOk()) 
				.andExpect(content().string(equalTo("[]"))); */
 
	} 
 
}
