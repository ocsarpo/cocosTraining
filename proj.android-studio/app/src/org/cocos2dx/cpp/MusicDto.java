package org.cocos2dx.cpp;

/**
 * Created by home on 2017-11-23.
 */

public class MusicDto {
    private String id;
    private String albumid;
    private String title;
    private String artist;

    public MusicDto(){}

    public MusicDto(String id, String albumid, String title, String artist){
        this.id = id;
        this.albumid = albumid;
        this.title = title;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public String getAlbumid() {
        return albumid;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "MusicDto{" +
                "id='" + id + '\'' +
                ", albumid='" + albumid + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
