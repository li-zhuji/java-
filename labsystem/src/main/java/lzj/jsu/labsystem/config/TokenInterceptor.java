package lzj.jsu.labsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//token拦截器


@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtConfig jwtConfig;
    //预处理，request前端传来的一些请求，response前端返回的对象
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if(uri.contains("/login")){
            return true;
        }


        //处理token
        //读取header信息，如果为空就去读get和post的一些信息，如果读不到，就证明压根没有
        String token = request.getHeader(jwtConfig.getHeader());
        if(StringUtils.isEmpty(token)){
            token = request.getParameter(jwtConfig.getHeader());
        }

        //经过上面两个的判断，就可以确定token值为空

        if(StringUtils.isEmpty(token)){
            //返回401， token不能为空
            sendError(response,"token不能为空");
            return false;
        }



        try {
            jwtConfig.getTokenClaim(token);
        } catch (Exception e) {
            //返回401. token失效，
            sendError(response,"token无效");
            return false;
        }
        return true;
    }

    //发送错误信息
    private void sendError(HttpServletResponse response,String msg) throws IOException {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(msg);
    }
}
