package com.example.Project_1.Filter;

import com.example.Project_1.Services.Logout;
import com.example.Project_1.Services.implement.EmployeeServicesImpl;
import com.example.Project_1.Services.JWTServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTServices jwtServices;

    @Autowired
    ApplicationContext context;

    @Autowired
    private Logout logout;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String Header = request.getHeader("Authorization");
        String token = null;
        String username =null;

        if (Header == null || !Header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
            token = Header.substring(7);
            username = jwtServices.First(token);
            List<String> roles = jwtServices.extraRole(token);

        if(logout.isLogoutToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails =
                    context.getBean(EmployeeServicesImpl.class).loadUserByUsername(username);

            if (jwtServices.validateToken(token,userDetails)){

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,roles
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList());

                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}