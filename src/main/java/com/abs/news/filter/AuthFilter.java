//package com.abs.news.filter;
//
//import com.abs.news.exception.JwtException;
//import com.abs.news.utilities.JwtProvider;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//
//@Component
//@Slf4j
//public class AuthFilter extends OncePerRequestFilter {
////    @Autowired
////    private AuthService authService;
//
//    @Autowired
//    JwtProvider jwtProvider;
//
//    @Autowired
//    @Qualifier("handlerExceptionResolver")
//    private HandlerExceptionResolver resolver;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        log.debug("---Entered Auth filter---");
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        if (authentication != null) {
//        String accessToken = null;
//        if (httpServletRequest.getParameter("token") != null) {
//            accessToken = httpServletRequest.getParameter("token");
//        } else if (httpServletRequest.getHeader("Authorization") != null) {
//            accessToken = httpServletRequest.getHeader("Authorization");
//        }
//
//        if (accessToken != null && accessToken.startsWith("Bearer ")) {
//            try {
//                if (jwtProvider.validateToken(accessToken.split(" ")[1])) {
//                    String username = jwtProvider.getUsernameFromToken(accessToken.split(" ")[1]);
//                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//                    SecurityContextHolder.getContext().setAuthentication(token);
//                }
//            } catch (JwtException e) {
//                log.error("Spring Security Filter Chain Exception:");
//                resolver.resolveException(httpServletRequest, httpServletResponse, null, e);
//                return;
//            }
//        }
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }
//
//}