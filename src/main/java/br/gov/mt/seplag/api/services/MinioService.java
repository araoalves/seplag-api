package br.gov.mt.seplag.api.services;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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
     * @param pessoaId usado para identificar o dono da imagem (opcional para salvar com o nome)
     * @param file arquivo de imagem enviado via multipart
     * @return hash (nome do objeto no bucket)
     */
    public String uploadFoto(Long pessoaId, MultipartFile file) {
        try {
            String hash = UUID.randomUUID().toString();

            // Cria bucket se não existir
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

            try (InputStream is = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucket)
                                .object(hash)
                                .stream(is, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }

            return hash;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload da imagem para o MinIO", e);
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
