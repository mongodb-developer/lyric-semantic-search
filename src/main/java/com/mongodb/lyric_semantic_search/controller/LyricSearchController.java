package com.mongodb.lyric_semantic_search.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.lyric_semantic_search.model.DocumentRequest;
import com.mongodb.lyric_semantic_search.service.LyricSearchService;

/**
 * REST controller for handling lyric search requests.
 */
@RestController
public class LyricSearchController {

    @Autowired
    private LyricSearchService lyricSearchService;

    @GetMapping("/status")
    public String status() {
        return lyricSearchService.getStatus();
    }

    @GetMapping("/search")
    public List<Map<String, Object>> searchDocuments(@RequestParam String query, @RequestParam int topK, @RequestParam double similarityThreshold) {
        return lyricSearchService.searchDocuments(query, topK, similarityThreshold);
    }

    @GetMapping("/searchWithFilter")
    public List<Map<String, Object>> searchDocumentsWithFilter(@RequestParam String query, @RequestParam int topK, @RequestParam double similarityThreshold, @RequestParam String artist) {
        return lyricSearchService.searchDocumentsWithFilter(query, topK, similarityThreshold, artist);
    }

    @PostMapping("/addDocuments")
    public String addDocuments(@RequestBody List<DocumentRequest> documents) {
        lyricSearchService.addDocuments(documents);
        return "Documents added";
    }

    @PostMapping("/addSampleData")
    public String addSampleData() {
        lyricSearchService.importSongs("archive/all_songs_data.json");
        return "Sample data added";
    }

    @DeleteMapping("/delete")
    public String deleteDocuments(List<String> ids) {
        lyricSearchService.deleteDocuments(ids);
        return "Documents deleted";
    }
}
