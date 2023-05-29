package com.example.springbootkeycloack.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
En resumen, este código se encarga de convertir un token JWT (JSON Web Token) en un objeto de autenticación
(AbstractAuthenticationToken) utilizado por el sistema de seguridad de Spring.

 */
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    /*
    Declara una instancia de JwtGrantedAuthoritiesConverter, que se utiliza para
    convertir las autoridades () del token JWT en objetos GrantedAuthority utilizados por Spring Security */
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();


    /*Define una instancia de JwtAuthConverterProperties en el constructor de la clase.
    Es posible que esta clase contenga propiedades de configuración específicas para este convertidor
     */
    private final JwtAuthConverterProperties properties;


    public JwtAuthConverter(JwtAuthConverterProperties properties) {
        this.properties = properties;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()).collect(Collectors.toSet());
        String authoritiesString = jwtGrantedAuthoritiesConverter.convert(jwt).stream()
                .map(GrantedAuthority::getAuthority) // Obtener la representación en cadena de cada GrantedAuthority
                .collect(Collectors.joining(", ")); // Unir las cadenas separadas por una coma y un espacio

        Collection<GrantedAuthority> authorities1 = jwtGrantedAuthoritiesConverter.convert(jwt);

        System.out.println(authorities1);
        // aquí extraigo el valor, lo deserilizo  y lo convierto a JWT con la libreria para manejar JWT con firmas (nimbsds)
        // es un paso mas largo y trae redundacia pq convierto el stoken en string y leugo otra vez a token
        //System.out.println(jwt.getTokenValue());
        String token =jwt.getTokenValue();
        try {
            JWT jwt1 = JWTParser.parse(token);
            JWTClaimsSet claims = jwt1.getJWTClaimsSet();
            String subject = claims.getSubject();
            LinkedTreeMap<String, Object> generic= (LinkedTreeMap<String, Object>) claims.getClaim("realm_access");

            //System.out.println(subject);
            //System.out.println(generic);
            //System.out.println(jwt1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // Aquí lo relixo convirtierndo a un objeto JSON y leugo de eso a un string
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jwtAsString;
        try {
            JWT jwt1 = JWTParser.parse(token);
            jwtAsString = objectMapper.writeValueAsString(jwt1);
            String jwtAsString1=objectMapper.writeValueAsString(jwt);
            System.out.println(jwtAsString1);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // aquí extraigo los permisos de una dez de jwt
        String resourceAccess = jwt.getClaim("scope");
        //System.out.println(resourceAccess);

        Map<String, Object> claims = jwt.getClaims();

        // Recorrer y acceder a los datos del JSON
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // Acceder a las propiedades
            //System.out.println("Key: " + key);
            //System.out.println("Value: " + value);
        }

        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
    }


    /*
    se utiliza para obtener el nombre del claim principal de un objeto Jwt en el contexto
     de la clase en la que se encuentra.
     */
    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (properties.getPrincipalAttribute() != null) {
            claimName = properties.getPrincipalAttribute();
        }
        //System.out.println(claimName);
        return jwt.getClaim(claimName);
    }
    /*
    se utiliza para extraer los roles de recursos de un objeto
    Jwt (JSON Web Token) en el contexto de la clase en la que se encuentra.
     */
        private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (resourceAccess == null
                || (resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId())) == null
                || (resourceRoles = (Collection<String>) resource.get("roles")) == null) {
            return Set.of();
        }
        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}





