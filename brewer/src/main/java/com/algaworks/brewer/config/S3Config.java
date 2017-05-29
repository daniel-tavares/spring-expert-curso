package com.algaworks.brewer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
@PropertySource(value = { "file://${HOME}/.brewer-mail.properties" }, ignoreResourceNotFound = true)//sobrescreve as propriedades do primeiro arquivo
public class S3Config {

    @Autowired
    private Environment env;
    
    @Bean
    public AmazonS3 amazons3() {
        AWSCredentials credentials = new BasicAWSCredentials(env.getProperty("AWS_ACCESS_KEY_ID"), env.getProperty("AWS_SECRET_KEY_ID"));
        AmazonS3 amazons3 = new AmazonS3Client(credentials, new ClientConfiguration());
        Region regiao = Region.getRegion(Regions.US_EAST_1);
        amazons3.setRegion(regiao);
        
        return amazons3;
    }
}
