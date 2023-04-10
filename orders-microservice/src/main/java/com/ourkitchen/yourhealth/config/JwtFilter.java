package com.ourkitchen.yourhealth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.SecurityContext;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

//@Component
//@Order(1)
public class JwtFilter implements Filter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request;
        HttpServletResponse resposne;

        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            request = (HttpServletRequest) req;
            resposne = (HttpServletResponse) res;
        } else throw new ServletException();

        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            String jwtToken = authHeader.substring(TOKEN_PREFIX.length());

            try {
                String[] chunks = jwtToken.split("\\.");
                Base64.Decoder decoder = Base64.getUrlDecoder();

                String headerJsonString = new String(decoder.decode(chunks[0]));
                String payloadJsonString = new String(decoder.decode(chunks[1]));

                JSONObject obj = new JSONObject(payloadJsonString);
                String clientId = obj.getString("clientId");
                Principal principal = new Principal() {
                    @Override
                    public String getName() {
                        return clientId;
                    }
                };

                request.getSession().setAttribute("userPrincipal", principal);
                request.authenticate(resposne);

            } catch (Exception e) {
                // handle exception
            }
        }

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }



    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
