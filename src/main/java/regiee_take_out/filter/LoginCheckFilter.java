package regiee_take_out.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.AntPathMatcher;
import regiee_take_out.common.BaseContext;
import regiee_take_out.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//检查用户是否登录
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    //    路径匹配器，支持通配符
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//            1获取本次请求url
        String url = httpServletRequest.getRequestURI();
        log.info("拦截到请求:{}", url);
//        定义不需要处理的路径
        String[] urls = new String[]{"/employee/login", "/employee/logout", "/backend/**", "/front/**", "/common/**",
                "/user/sendMsg", "/user/login"
        };

//            2判断本次请求是否需要处理
        boolean check = check(urls, url);
//            3不需要处理直接放行
        if (check) {
            log.info("本次请求{}不需要处理", url);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
//            4.1判断登录状态已登录放行
        if (httpServletRequest.getSession().getAttribute("employee") != null) {
            log.info("用户登录,id为:{}", httpServletRequest.getSession().getAttribute("employee"));
            Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
//            long id = Thread.currentThread().getId();
//            log.info("线程id:{}",id);
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
//               4.2判断移动端登录状态已登录放行
        if (httpServletRequest.getSession().getAttribute("user") != null) {
            log.info("用户登录,id为:{}", httpServletRequest.getSession().getAttribute("user"));
            Long userId = (Long) httpServletRequest.getSession().getAttribute("user");
//            long id = Thread.currentThread().getId();
//            log.info("线程id:{}",id);
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        log.info("用户已登录");
//            5未登录返回未登录结果，用输出流方式向客户端相应数据
        log.info("用户未登录");
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配
     *
     * @param urls
     * @param url
     * @return
     */
    public boolean check(String[] urls, String url) {
        for (String urll : urls) {
            boolean match = PATH_MATCHER.match(urll, url);
            if (match) {
                return true;
            }
        }
        return false;
    }

}
