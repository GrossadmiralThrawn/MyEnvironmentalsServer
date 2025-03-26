package setups




/*import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.core.io.ClassPathResource
import org.apache.catalina.connector.Connector
import java.security.KeyStore
import java.security.cert.Certificate
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain




@Configuration
class SecuritySetup {

    @Bean
    fun servletContainer(): TomcatServletWebServerFactory {
        val tomcat = TomcatServletWebServerFactory()
        tomcat.port = 8443
        tomcat.addConnectorCustomizers(::customizeConnector)
        return tomcat
    }

    private fun customizeConnector(connector: Connector) {
        connector.secure = true
        connector.scheme = "https"
        connector.setProperty("keyAlias", "mycert")
        connector.setProperty("keystorePass", "changeit")
        connector.setProperty("keystoreType", "JKS")
        // Der Keystore wird aus dem Classpath geladen
        val keystoreFile = ClassPathResource("keystore.jks").file.absolutePath
        connector.setProperty("keystoreFile", keystoreFile)
    }

    fun getCertificate(): Certificate? {
        val keyStore = KeyStore.getInstance("JKS")
        val resource = ClassPathResource("keystore.jks")
        keyStore.load(resource.inputStream, "changeit".toCharArray())
        return keyStore.getCertificate("mycert")
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { authz ->
            authz.anyRequest().permitAll()
        }
        http.csrf { csrf -> csrf.disable() }
        // Deaktiviert Basic-Authentifizierung
        http.httpBasic { httpBasic -> httpBasic.disable() }
        return http.build()
    }
}*/
