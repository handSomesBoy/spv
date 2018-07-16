package com.linkspring.spv.restful;

import com.linkspring.spv.restful.pojo.Tokens;
import com.linkspring.spv.restful.util.JwtToken;
import com.linkspring.spv.common.ResponseResult;
import com.linkspring.spv.common.enums.IStatusMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

//restful接口示例
//符合auth 2.0 密码模式
@RestController
public class RestTokenController {
    /**
     * 
     * 根据用户id 得到token
     */
    @RequestMapping(value="/getToken",method=RequestMethod.POST,produces = "application/json")
    public @ResponseBody
    ResponseResult getToken(@RequestParam String client_id, @RequestParam String username,
                            @RequestParam String password){
        ResponseResult responseResult=new ResponseResult();
        Tokens tokens=new Tokens();
        JwtToken jwtToken=new JwtToken();
        //TODO 待完善帐号密码验证
        try {
           String access_token= jwtToken.createJWT("access_token", "{id:100,username:"+username+"}", 60000);//毫秒
           tokens.setAccess_token(access_token);
           tokens.setExpires_in("60000");
           String refresh_token= jwtToken.createJWT("refresh_token", "{id:100,username:"+username+"}", 600000);
           tokens.setRefresh_token(refresh_token);
           tokens.setToken_type("Bearer");//这个Bearer 还不太理解
           responseResult.setObj(tokens);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
            responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getMessage());
        }
        return responseResult;
    }

    /**
     *
     * @description : 更新user
     * @author :laiyao
     * @date :2015年8月17日
     */
    @RequestMapping(value="/checkToken/{token}",method=RequestMethod.GET,produces = "application/json")
    public @ResponseBody ResponseResult checkToken(@PathVariable String username,HttpServletRequest request ){
        ResponseResult responseResult=new ResponseResult();
        JwtToken jwtToken=new JwtToken();
        String token= request.getHeader("x-access-token");
        //token="eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJEU1NGQVdEV0FEQVMuLi4iLCJzdWIiOiJ7aWQ6MTAwLG5hbWU6eGlhb2hvbmd9IiwidXNlcl9uYW1lIjoiYWRtaW4iLCJuaWNrX25hbWUiOiJEQVNEQTEyMSIsImV4cCI6MTUyNzY0OTI4MywiaWF0IjoxNTI3NjQ4NjgzLCJqdGkiOiJqd3QifQ.oVkHRkWGDxYDnqWoIH9XtZyTcQ7IHwea7TvjGss-bZo";
        try {
            System.err.println(token);
            Claims c=jwtToken.parseJWT(token);
            if(c!=null)
            {
                responseResult.setObj(c);
            }
        }catch(MalformedJwtException e){//token错误 不是token
            e.printStackTrace();
            responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
            responseResult.setMessage(e.getMessage());
        }catch(SignatureException e){//token错误 不存在
            e.printStackTrace();
            responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
            responseResult.setMessage(e.getMessage());
        }catch (ExpiredJwtException e) {            //超时
            e.printStackTrace();
            responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
            responseResult.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
            responseResult.setMessage(IStatusMessage.SystemStatus.ERROR.getMessage());
        }
        return responseResult;
    }
}