package com.example.springbootkeycloack.paraRomper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExtractWithJson {


    private static Collection<? extends GrantedAuthority> extractResourceRoles(JWT jwt){
        Set<GrantedAuthority> resourceRoles=new HashSet<>();
        ObjectMapper objectMapper= new ObjectMapper();

        /*
        JavaTimeModule es un módulo proporcionado por la biblioteca Jackson que permite la serialización
         y deserialización de tipos de fecha y hora de Java 8 y posteriores. Al registrar este módulo en
          el ObjectMapper, se habilita el soporte para la serialización y deserialización automática de
          tipos de fecha y hora, como LocalDate, LocalTime, LocalDateTime, ZonedDateTime, entre otros.
         */
        objectMapper.registerModule(new JavaTimeModule());

        return resourceRoles;

    }

    private static JWT convertToJWT(String token) throws ParseException {
        JWT jwt1 = JWTParser.parse(token);
        return jwt1;
    }
    private static List<GrantedAuthority> extractResorceRoles1(JsonNode jwt){
        Set<String> rolesWithPrefix= new HashSet<>();

        jwt.path("jwtclaimSet")
                .elements()
                .forEachRemaining(e -> e.path("roles")
                        .elements()
                        .forEachRemaining(r -> rolesWithPrefix.add("ROLES_"+ r.asText())));

        return AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
    }

    public static void main(String[] args){
        String token="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOQ01QUXoyaERPdXZvam9sc21iZnVsZE9EV3ZwRnhUZTA4TjE1WXlHRVZnIn0.eyJleHAiOjE2ODUxNDQ5NzgsImlhdCI6MTY4NTE0NDY3OCwianRpIjoiMjliZDE5MDctMDFiZS00OWQzLThhZjAtYTExODgxNzkzNGJjIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9TcHJpbmdCb290S2V5Y2xvYWsiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMDkxMmViZmMtMWU0Ni00Yzg4LThjMDItMTcwNTFkOTFlZGRiIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic3ByaW5nYm9vdC1rZXljbG9hay1jbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiMjI0MDM1NGUtNjBlNy00ODBiLTk3NWMtYzhjY2Q4MjFjMDYyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODEiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImFwcF91c2VyIiwib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLXNwcmluZ2Jvb3RrZXljbG9hayIsInVtYV9hdXRob3JpemF0aW9uIiwiYXBwX2FkbWluIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsic3ByaW5nYm9vdC1rZXljbG9hay1jbGllbnQiOnsicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiIyMjQwMzU0ZS02MGU3LTQ4MGItOTc1Yy1jOGNjZDgyMWMwNjIiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIyIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.hOODNNxFe6wBKTH7tkWaA8FcikD1EwrQwaI1qbYmlgNow4Jcwq-m51JiBJM_RxS9BSbnjbbgstllLyHfz-1wxAD2Og5Yg9EjQTiA3-xCgnR4BpsAz3tBqRDpPTkz8o3xz102BZNcUYrJ95DiwjSItqlo2U6KGauxF0R8LaNm0mmvYwG0Q4nH8zKPkD1G0zzEAMexm3sp2J9vQMN0_pKowQNDQEJDla0EzxT4PgutsSrxEE4fKWxY6pgrVWb5wFs8YdpqK4orbsushXAJVwpruXlLJ12Qp_kvv6V_S5ZgdIOm-HegzbDxTThPmJhjkk30TKATiRN5AMLk9-jjDR-HNw";

        ObjectMapper objectMapper= new ObjectMapper();
        try {

            JWT token1= convertToJWT(token);
            String respuestaString =objectMapper.writeValueAsString(token1);
            System.out.println(respuestaString);
            JsonNode jsonNode= objectMapper.readTree(respuestaString);

            List<GrantedAuthority> we=extractResorceRoles1(jsonNode);
            System.out.println(we);
            //JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(token1)).get("claims");


        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }


}
