package br.com.exemplo.logisticaservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança do logistica-service.
 *
 * Este serviço não realiza login e não gera tokens.
 * Ele apenas recebe e valida tokens emitidos pelo Keycloak.
 */
@Configuration
public class SecurityConfig {

    /**
     * Define as regras de segurança aplicadas às requisições HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                /*
                 * Nossa API não utiliza formulário nem sessão do navegador.
                 * Por isso, a proteção CSRF não é necessária neste fluxo REST.
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * Cada requisição precisa carregar seu próprio JWT.
                 * O servidor não mantém sessão do usuário em memória.
                 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                /*
                 * O health check continua público.
                 * Todos os demais endpoints exigem um JWT válido.
                 */
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/actuator/health",
                                        "/actuator/health/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )

                /*
                 * Informa que a aplicação receberá tokens Bearer JWT.
                 *
                 * O Spring buscará a chave pública no jwk-set-uri
                 * configurado no application.yaml.
                 */
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults())
                );

        return http.build();
    }
}