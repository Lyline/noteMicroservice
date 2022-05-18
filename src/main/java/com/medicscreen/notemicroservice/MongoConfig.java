package com.medicscreen.notemicroservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

  @Value("${spring.data.mongodb.uri}")
  private String uri;

  @Bean
  public MongoDatabaseFactory mongoDbFactory() {
    return new SimpleMongoClientDatabaseFactory(com.mongodb.client.MongoClients.create(uri),"notes");
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

    return mongoTemplate;

  }

}
