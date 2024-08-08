package com.mongodb.lyric_semantic_search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Song {
    @JsonProperty("Album")
    private String album;

    @JsonProperty("Album URL")
    private String albumUrl;

    @JsonProperty("Artist")
    private String artist;

    @JsonProperty("Featured Artists")
    private String featuredArtists; // Change to String

    @JsonProperty("Lyrics")
    private String lyrics;

    @JsonProperty("Media")
    private String media; // Change to String

    @JsonProperty("Rank")
    private int rank;

    @JsonProperty("Release Date")
    private String releaseDate;

    @JsonProperty("Song Title")
    private String songTitle;

    @JsonProperty("Song URL")
    private String songUrl;

    @JsonProperty("Writers")
    private String writers; // Change to String

    @JsonProperty("Year")
    private int year;

    // Getters and setters

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumUrl() {
        return albumUrl;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFeaturedArtists() {
        return featuredArtists;
    }

    public void setFeaturedArtists(String featuredArtists) {
        this.featuredArtists = featuredArtists;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
