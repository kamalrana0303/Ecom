package com.generic.ecom.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generic.ecom.dto.RoleDto;
import com.generic.ecom.dto.UserDto;
import com.generic.ecom.entity.User;
import com.generic.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
public class UtilsService {
    @Autowired
    UserService userService;
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String authorizationHeader= req.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refreshToken=authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm= Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username=decodedJWT.getSubject();
                UserDto user= userService.getUser(username);
                String tokenUsername= user.getUsername();
                List<String> tokenRoles=user.getRoles().stream().map(RoleDto::getName).collect(Collectors.toList());
                Date expiredAt=new Date(System.currentTimeMillis()+ 30*60*1000);
                String accessToken=createAccessToken(req.getRequestURL().toString(), tokenUsername,tokenRoles, algorithm,expiredAt);
                Map<String, String> token= new HashMap<>();
                token.put("refresh_token", refreshToken);
                token.put("access_token", accessToken);
                res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(res.getOutputStream(), token);
            }catch(Exception e){
                res.setHeader("error", e.getMessage());
                res.setStatus(FORBIDDEN.value());
                res.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                Map<String , String> error= new HashMap<>();
                error.put("error_message", e.getMessage());
                new ObjectMapper().writeValue(res.getOutputStream(),error);
            }
        }
    }
    public String createAccessToken(String issuer, String username, List<String> roles, Algorithm algorithm, Date expiredAt){
        String access_token=JWT.create().withSubject(username)
                .withExpiresAt(expiredAt)
                .withIssuer(issuer)
                .withClaim("roles",roles )
                .sign(algorithm);
        return access_token;
    }


}
