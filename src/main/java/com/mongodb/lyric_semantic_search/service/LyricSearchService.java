package com.mongodb.lyric_semantic_search.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.lyric_semantic_search.model.DocumentRequest;
import com.mongodb.lyric_semantic_search.model.Song;
import com.mongodb.lyric_semantic_search.repository.LyricSearchRepository;
import com.mongodb.lyric_semantic_search.transformer.SongToDocumentTransformer;

/**
 * Service class for handling lyric search operations.
 */
@Service
public class LyricSearchService {

    private static final int MAX_TOKENS = (int) (8192 * 0.80); // OpenAI model's maximum content length + BUFFER for when one word > 1 token

    @Autowired
    private LyricSearchRepository lyricSearchRepository;

    /**
     * Adds documents to the repository after filtering out null or overly long documents.
     *
     * @param documents List of document requests to add
     */
    public void addDocuments(List<DocumentRequest> documents) {
        if (documents == null || documents.isEmpty()) {
            return; // Nothing to add
        }

        List<Document> docs = documents.stream()
            .filter(doc -> doc != null && doc.getContent() != null && !doc.getContent()
                .trim()
                .isEmpty())
            .map(doc -> new Document(doc.getContent(), doc.getMetadata()))
            .filter(doc -> {
                int wordCount = doc.getContent()
                    .split("\\s+").length;
                return wordCount <= MAX_TOKENS;
            })
            .collect(Collectors.toList());

        if (!docs.isEmpty()) {
            lyricSearchRepository.addDocuments(docs);
        }
    }

    /**
     * Searches documents based on the query, topK, and similarity threshold.
     *
     * @param query The search query
     * @param topK The number of top results to return
     * @param similarityThreshold The similarity threshold
     * @return List of search results
     */
    public List<Map<String, Object>> searchDocuments(String query, int topK, double similarityThreshold) {
        SearchRequest searchRequest = SearchRequest.query(query)
            .withTopK(topK)
            .withSimilarityThreshold(similarityThreshold);

        List<Document> results = lyricSearchRepository.semanticSearchByLyrics(searchRequest);

        return results.stream()
            .map(doc -> Map.of("content", doc.getContent(), "metadata", doc.getMetadata()))
            .collect(Collectors.toList());
    }

    /**
     * Searches documents with metadata filtering based on the artist.
     *
     * @param query The search query
     * @param topK The number of top results to return
     * @param similarityThreshold The similarity threshold
     * @param artist The artist to filter by
     * @return List of search results
     */
    public List<Map<String, Object>> searchDocumentsWithFilter(String query, int topK, double similarityThreshold, String artist) {
        FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();
        Expression filterExpression = filterBuilder.eq("artist", artist)
            .build();

        SearchRequest searchRequest = SearchRequest.query(query)
            .withTopK(topK)
            .withSimilarityThreshold(similarityThreshold)
            .withFilterExpression(filterExpression);

        List<Document> results = lyricSearchRepository.semanticSearchByLyrics(searchRequest);

        return results.stream()
            .map(doc -> Map.of("content", doc.getContent(), "metadata", doc.getMetadata()))
            .collect(Collectors.toList());
    }

    /**
     * Imports songs from a JSON file.
     *
     * @param jsonFilePath The path to the JSON file containing songs
     */
    public void importSongs(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Song> songs = objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<Song>>() {
            });

            List<DocumentRequest> documentRequests = songs.stream()
                .map(SongToDocumentTransformer::transform)
                .filter(doc -> doc != null)
                .collect(Collectors.toList());

            addDocuments(documentRequests);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes documents by their IDs.
     *
     * @param ids List of document IDs to delete
     */
    public void deleteDocuments(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return; // Nothing to delete
        }

        lyricSearchRepository.deleteDocuments(ids);
    }

    /**
     * Gets the status of the application.
     *
     * @return The application status
     */
    public String getStatus() {
        return "Application is running!";
    }
}
