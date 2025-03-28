package br.gov.mt.seplag.api.services;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    /**
     * Envia uma foto para o bucket configurado no MinIO
     * @param pessoaId id da pessoa
     * @param foto arquivo de imagem enviado via base 64
     * @return hash (nome do objeto no bucket)
     */
    public String uploadFotoBase64(Long pessoaId, String foto) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(foto.split(",")[1]); // remove prefixo data:image/png;base64,
            String nomeArquivo = "foto_" + pessoaId + "_" + UUID.randomUUID();
            InputStream input = new ByteArrayInputStream(decodedBytes);

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(nomeArquivo)
                    .stream(input, decodedBytes.length, -1)
                    .contentType("image/png")
                    .build());

            return nomeArquivo;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar imagem para MinIO", e);
        }
    }


    /**
     * Gera uma URL temporária (expira em 5 minutos)
     * @param hash nome do objeto salvo no bucket
     * @return URL de acesso temporário
     */
    public String getUrlTemporaria(String hash) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(hash)
                            .expiry(5, TimeUnit.MINUTES)
                            .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException("Erro ao gerar URL temporária do MinIO", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao gerar URL do MinIO", e);
        }
    }
}
