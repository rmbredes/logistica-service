# Imagem enxuta com o Java 21 necessário
# para executar uma aplicação Spring Boot.
FROM eclipse-temurin:21-jre-jammy


# Define o diretório de trabalho dentro da imagem.
WORKDIR /app


# Copia o JAR gerado pelo Maven para dentro da imagem.
#
# O arquivo recebe o nome simplificado app.jar
# dentro do container.
COPY target/logistica-service-0.0.1-SNAPSHOT.jar app.jar


# Documenta a porta utilizada pelo serviço.
#
# A publicação real será definida pelo Compose.
EXPOSE 8084


# Comando executado quando o container for iniciado.
ENTRYPOINT ["java", "-jar", "app.jar"]