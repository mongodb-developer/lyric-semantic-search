package com.mongodb.lyric_semantic_search.repository;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;

public interface LyricSearchRepository {

    void addDocuments(List<Document> docs);

    void deleteDocuments(List<String> ids);

    List<Document> semanticSearchByLyrics(SearchRequest searchRequest);
}
