package com.mongodb.lyric_semantic_search.transformer;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.lyric_semantic_search.model.DocumentRequest;
import com.mongodb.lyric_semantic_search.model.Song;

public class SongToDocumentTransformer {

    public static DocumentRequest transform(Song song) {
        if (song == null || song.getLyrics() == null || song.getLyrics()
            .trim()
            .isEmpty()) {
            return null;
        }

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("title", song.getSongTitle());
        metadata.put("artist", song.getArtist());
        metadata.put("year", song.getYear());

        return new DocumentRequest(song.getLyrics(), metadata);
    }
}
