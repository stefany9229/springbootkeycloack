package com.example.springbootkeycloack.paraRomper;

import com.example.springbootkeycloack.security.JwtAuthConverterProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.text.ParseException;

public class MyClass {
    public void interactWithToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);

        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuthenticationToken.getToken();
            String accessToken = jwt.getTokenValue();

            System.out.println("Access Token: " + accessToken);
        }else{
            JwtAuthConverterProperties tt= new JwtAuthConverterProperties();
            System.out.println("paso por aquí");
            System.out.println(tt.getResourceId());
        }


    }


    public static void main(String[] args) {

        String token="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOQ01QUXoyaERPdXZvam9sc21iZnVsZE9EV3ZwRnhUZTA4TjE1WXlHRVZnIn0.eyJleHAiOjE2ODUxMzczOTksImlhdCI6MTY4NTEzNzA5OSwianRpIjoiZDllNjAzZTctODJjOC00NjI2LTk4NTEtNzhmNmNlZDM3NzNlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9TcHJpbmdCb290S2V5Y2xvYWsiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiYTQwZWFiZDctNjcwZC00ZGNlLWIxMzktMjQwOGFlMDdjNDI5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic3ByaW5nYm9vdC1rZXljbG9hay1jbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiZGZhODRmY2ItOWEwNi00YWY1LThmZjctY2U0MDE1MzE0MWFkIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODEiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImFwcF91c2VyIiwib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLXNwcmluZ2Jvb3RrZXljbG9hayIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsic3ByaW5nYm9vdC1rZXljbG9hay1jbGllbnQiOnsicm9sZXMiOlsidXNlciJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiZGZhODRmY2ItOWEwNi00YWY1LThmZjctY2U0MDE1MzE0MWFkIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyMSIsImdpdmVuX25hbWUiOiIiLCJmYW1pbHlfbmFtZSI6IiJ9.JGNIsBY5KHkGpWINlBxFnjX8wwpEO-nqj0QKnh3lKwq7vZsNlKtKSH3l6bxlirmIgGwzp7n2RDTpEjA1t8rxRGqYNGt3WxcpSbp4IE_cPG-SZesVi3k5rdcnZqCteX3CcG761EAnzFk-CGrCeddslBIt7fqIZh-oPG7UI-MBTVMxTQu_fG5qRGfnHxzHq_xQKSMx75w9qaGEX0Vx5O5PtJtKPd9xFGi2UkhbZ7e9rKJ8m19YplVcmqVh8gOyz6afbmne3Ay4D_6xtsooyo4psXZhtEi6sIVZiSIJ_jXFfRezSXdGwBD3SjV8DpQ8_n7P-zMDD-TMxivXOThsJczglg";
        JWT jwt1 = null;
        try {
            jwt1 = JWTParser.parse(token);
            ObjectMapper objectMapper = new ObjectMapper();
            String jwtAsString = objectMapper.writeValueAsString(jwt1);
            System.out.println(jwtAsString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


}

