package com.ch4assignment.service;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.cloudfront.CloudFrontUtilities;
import software.amazon.awssdk.services.cloudfront.model.CannedSignerRequest;
import software.amazon.awssdk.services.cloudfront.url.SignedUrl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private static final Duration PRESIGNED_URL_EXPIRATION = Duration.ofDays(7);

    private final S3Template s3Template;
    private final ResourceLoader resourceLoader;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.cloudfront.domain}")
    private String cloudFrontDomain;

    @Value("${aws.cloudfront.key-pair-id}")
    private String keyPairId;

    @Value("${aws.cloudfront.private-key-path}")
    private String privateKeyPath;

    public String upload(MultipartFile file) {
        try {
            String key = "profiles/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            s3Template.upload(bucket, key, file.getInputStream());
            return key;
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    public URL getDownloadUrl(String key) {
        try {
            String resourceUrl = cloudFrontDomain + "/" + key;
            Instant expirationTime = Instant.now().plus(Duration.ofHours(1));

            Resource resource = resourceLoader.getResource(privateKeyPath);
            PrivateKey privateKey = loadPrivateKey(resource);

            CannedSignerRequest signerRequest = CannedSignerRequest.builder()
                    .resourceUrl(resourceUrl)
                    .keyPairId(keyPairId)
                    .privateKey(privateKey)
                    .expirationDate(expirationTime)
                    .build();

            CloudFrontUtilities cloudFrontUtilities = CloudFrontUtilities.create();
            SignedUrl signedUrl = cloudFrontUtilities.getSignedUrlWithCannedPolicy(signerRequest);

            return new URL(signedUrl.url());

        } catch (IOException e) {
            throw new IllegalStateException("프라이빗 키 파일을 읽을 수 없습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("CloudFront 서명 URL 생성 중 오류가 발생했습니다.", e);
        }
    }

    private PrivateKey loadPrivateKey(Resource resource) throws Exception {
        try (InputStream is = resource.getInputStream()) {
            String keyContent = new String(is.readAllBytes())
                    .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replace("-----END RSA PRIVATE KEY-----", "")
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] keyBytes = Base64.getDecoder().decode(keyContent);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        }
    }
}