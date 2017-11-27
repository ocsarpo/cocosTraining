/****************************************************************************
Copyright (c) 2015-2017 Chukong Technologies Inc.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.cpp;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import org.cocos2dx.lib.Cocos2dxActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AppActivity extends Cocos2dxActivity {
    public static Activity acti;

    //Call C++ Func.
    private native void setAlbumArt(String art);

    private HashMap<Integer, String> map = new HashMap<Integer, String>();
    private ArrayList<MusicDto> m_musics = new ArrayList<MusicDto>();
    private static int curIndex = 0;
    private MediaPlayer m_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setEnableVirtualButton(false);
        super.onCreate(savedInstanceState);
        // Workaround in https://stackoverflow.com/questions/16283079/re-launch-of-activity-on-home-button-but-only-the-first-time/16447508
        if (!isTaskRoot()) {
            // Android launched another instance of the root activity into an existing task
            //  so just quietly finish and go away, dropping the user back into the activity
            //  at the top of the stack (ie: the last state of this task)
            // Don't need to finish it again since it's finished in super.onCreate .
            return;
        }
        // DO OTHER INITIALIZATION BELOW
        acti = this;

        albumartMapping();
        listupMusics();
    }

    private void listupMusics() {
        String[] music_columns = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.YEAR,
        };
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                music_columns, null, null, null);

        if(cursor != null){
            while(cursor.moveToNext()){
                int album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);    //name of album
                int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int displayName = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                int track = cursor.getColumnIndex(MediaStore.Audio.Media.TRACK);
                int data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int year = cursor.getColumnIndex(MediaStore.Audio.Media.YEAR);
                int id = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                String albumArt = "";
                if(map.containsKey(Integer.parseInt(cursor.getString(id))))
                    albumArt = map.get(Integer.parseInt(cursor.getString(id)));

                MusicDto dto = new MusicDto(
                        cursor.getString(data),
                        cursor.getString(title),
                        cursor.getString(artist),
                        cursor.getString(album),
                        cursor.getString(year),
                        cursor.getString(duration),
                        cursor.getString(track),
                        cursor.getString(displayName),
                        albumArt
                        );
                m_musics.add(dto);
            }

            cursor.close();
        }
    }

    private void albumartMapping(){
         String[] album_art ={
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums._ID
        };
        Cursor artCursor = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                album_art,null,null,null);
        if(artCursor != null){
            while(artCursor.moveToNext()){
                int art = artCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                int id  = artCursor.getColumnIndex(MediaStore.Audio.Albums._ID);
                Log.d("Read art", artCursor.getString(id));
                if(!map.containsKey(Integer.parseInt(artCursor.getString(id)))){
                    map.put(Integer.parseInt(artCursor.getString(id)), artCursor.getString(art));
                }
            }
        }
        artCursor.close();
    }
    public static Object getThisActivity(){
        return acti;
    }
    public String HelloJNI() {
        return "안녕";
    }

    public String album(){
        String art = m_musics.get(curIndex).getAlbumArt();
        if(m_player != null){
            m_player.release();
        }
        m_player = MediaPlayer.create(this, Uri.parse(m_musics.get(curIndex).getDirectory()));
        m_player.start();
        m_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                try{
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    ++curIndex;
                    mediaPlayer.setDataSource(m_musics.get(curIndex).getDirectory());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    String nextArt = m_musics.get(curIndex).getAlbumArt();
                    setAlbumArt(nextArt);
                }catch (Exception e){
                    Log.e("MUSIC PLAYER EXCEPTION", e.getMessage());
                }
            }
        });
        curIndex++;
        if(curIndex >= m_musics.size())
            curIndex = 0;
        return art;
    }
//    public String albumart() {
//        String songt=null;
//        String song=null;
//        Uri sAlbumArtUri = null;
//        String albumArt = null;
//        if(m_player != null) {
//            m_player.release();
//        }
//        if(m_cursor.moveToNext()){
//            songt = m_cursor.getString(m_cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
//            song = m_cursor.getString(m_cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
//            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
//            long albumid = m_cursor.getLong(m_cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
//            sAlbumArtUri = ContentUris.withAppendedId(sArtworkUri, albumid);
//        }else{
//            Log.e(this.getClass().getName(), "엘스다");
//            m_cursor.moveToFirst();
//            return "file:///android_asset/k.jpg";
//        }
//        cursor.close();
//        if(m_artCursor != null){
//            if(m_artCursor.moveToNext()){
//                int albumart = m_artCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
//                int albumid = m_artCursor.getColumnIndex(MediaStore.Audio.Albums._ID);
//                albumArt = m_artCursor.getString(albumart);
//            }else{
//                Log.e(this.getClass().getName(), "엘스다");
//                m_artCursor.moveToFirst();
//                int albumart = m_artCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
//                albumArt = m_artCursor.getString(albumart);
//            }
//        }
//
//        m_player = MediaPlayer.create(this, Uri.parse(song));
//        m_player.start();
//        m_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                if(m_cursor.moveToNext()){
//                    String song2 = m_cursor.getString(m_cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
//                    try{
//                        mediaPlayer.stop();
//                        mediaPlayer.reset();
//                        mediaPlayer.setDataSource(song2);
//                        mediaPlayer.prepare();
//                        mediaPlayer.start();}
//                    catch (Exception e){
//
//                        Log.e("TAG", "예외다~~~~~"+e.getMessage()+", ");
//                    }
//                }
//            }
//        });
//        return albumArt;
//    }
}
