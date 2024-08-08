package com.mongodb.lyric_semantic_search.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.MongoDBAtlasVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@SpringBootConfiguration
@EnableAutoConfiguration
public class Config {

    @Value("${spring.ai.openai.api-key}")
    private String openAiKey;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.ai.vectorstore.mongodb.collection-name:vector_store}")
    private String collectionName;

    @Value("${spring.ai.vectorstore.mongodb.indexName:vector_index}")
    private String indexName;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public VectorStore mongodbVectorStore(MongoTemplate mongoTemplate, EmbeddingModel embeddingModel) {
        return new MongoDBAtlasVectorStore(mongoTemplate, embeddingModel, MongoDBAtlasVectorStore.MongoDBVectorStoreConfig.builder()
            .build(), true);
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        return new OpenAiEmbeddingModel(new OpenAiApi(openAiKey));
    }
}
