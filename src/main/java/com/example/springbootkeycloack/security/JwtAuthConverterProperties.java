package com.example.springbootkeycloack.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;


@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class JwtAuthConverterProperties {

    /*Esta línea define una propiedad llamada resourceId de tipo String en la clase JwtAuthConverterProperties.
     Esta propiedad almacenará el identificador de recurso utilizado para validar los tokens JWT
     */
    private String resourceId;

    /*Esta línea define otra propiedad llamada principalAttribute de tipo String en la clase JwtAuthConverterProperties.
     Esta propiedad almacenará el nombre del atributo principal que se utilizará para extraer el nombre de usuario del token JWT.
     */
    private String principalAttribute;

    public String getResourceId() {
        return resourceId;
    }

    public String getPrincipalAttribute() {
        return principalAttribute;
    }
}

/*
@Data: Esta anotación es parte del proyecto Lombok y genera automáticamente los métodos toString(), equals(), hashCode(), getter y setter para las propiedades de la clase. Simplifica la escritura del código y mejora la legibilidad.

@Validated: Esta anotación indica que se aplicarán validaciones en esta clase. Esto permite definir restricciones y validaciones en las propiedades de la clase, como comprobar que no sean nulas, tengan un formato específico, etc.

@Configuration: Esta anotación indica que esta clase es una clase de configuración de Spring. Spring reconocerá esta clase y realizará la configuración correspondiente según los valores definidos en ella.

@ConfigurationProperties(prefix = "jwt.auth.converter"): Esta anotación se utiliza para vincular las propiedades definidas en esta clase con las propiedades configuradas en el archivo de configuración de la aplicación. El prefijo "jwt.auth.converter" indica que se buscarán
 las propiedades que comiencen con esa cadena en el archivo de configuración.





En resumen, esta clase JwtAuthConverterProperties se utiliza para almacenar las propiedades de configuración relacionadas con la autenticación y autorización basadas en JWT. Las propiedades resourceId y principalAttribute se utilizan para definir y acceder a valores específicos necesarios para la autenticación
y autorización de la aplicación, como el identificador de recurso y el atributo principal del token JWT. Al anotar la clase con @ConfigurationProperties y especificar el prefijo correspondiente, se permite la configuración externa de estas propiedades a través de un archivo de configuración de la aplicación.
*/