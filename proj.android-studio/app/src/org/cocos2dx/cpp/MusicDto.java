package org.cocos2dx.cpp;

/**
 * Created by home on 2017-11-23.
 */

public class MusicDto {
    private String directory;
    private String name;
    private String artist;
    private String album;
    private String genre;
    private String year;
    private String duration;
    private String track;
    private String displayName;
    private String albumArt;

    public MusicDto(){}
    public MusicDto(String directory, String name, String artist, String album, String year, String duration, String track, String displayName, String albumArt) {
        this.directory = directory;
        this.name = name;
        this.artist = artist;
        this.album = album;
//        this.genre = genre;
        this.year = year;
        this.duration = duration;
        this.track = track;
        this.displayName = displayName;
        this.albumArt = albumArt;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

//    public String getGenre() {
//        return genre;
//    }
//
//    public void setGenre(String genre) {
//        this.genre = genre;
//    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }
}
