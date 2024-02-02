package com.grup.pokerdaw.api_rest_pokerdaw.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.grup.pokerdaw.api_rest_pokerdaw.service.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res,@NonNull FilterChain filterChain)
            throws ServletException, IOException {
     
    final String authHeader = req.getHeader("Authorization");
    final String jwt;
    final String nickname;

        if (authHeader == null || !authHeader.startsWith("Bearer")){
        filterChain.doFilter(req, res);
        return;
        }

        jwt = authHeader.substring(7);
   
        try {
            nickname = jwtService.getNicknameUsuarioFromToken (jwt);
            
            if (nickname != null && SecurityContextHolder.getContext().getAuthentication() == null) {
               
            UserDetails userdetails = userDetailsService.loadUserByUsername (nickname);

                if (jwtService.isTokenValid(jwt, userdetails)) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                SecurityContextHolder.getContext().setAuthentication(authToken) ; 

                }
         }
        }catch (MalformedJwtException e) {

        logger.error("Token mal formado");
        }catch (UnsupportedJwtException e) {
        logger .error("Token soportado");
        } catch (ExpiredJwtException e) {
        logger.error( "Token expirado");
        } catch (IllegalArgumentException e) {
        logger.error ("Token vacio");
        } catch (SecurityException e) {
         logger.error("Fallo en la firma");
        }
        filterChain.doFilter(req, res);
        }
}
        

