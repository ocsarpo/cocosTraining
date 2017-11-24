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

public class AppActivity extends Cocos2dxActivity {
    public static Activity acti;
    private String[] title = {
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST};
    private Cursor m_cursor;
    MediaPlayer m_player;
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
        m_cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                title, null, null, null);
    }
    public static Object getThisActivity(){
        return acti;
    }

    public String HelloJNI() {
        return "안녕새꺄";
    }

    public ArrayList<MusicDto> SongTitle() {
        ArrayList<MusicDto> list = new ArrayList<MusicDto>();
        String[] title = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST};
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                title, null, null, null);
        while(cursor.moveToNext()){
            MusicDto musicDto = new MusicDto();
            musicDto.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicDto.setAlbumid(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicDto.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicDto.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            list.add(musicDto);

        }
        cursor.close();

        return list;
    }

    public String Songt() {
        String[] title = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST};
        String songt=null;
        String song=null;
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                title, null, null, null);
        while(cursor.moveToNext()){
            MusicDto musicDto = new MusicDto();
            musicDto.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicDto.setAlbumid(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicDto.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicDto.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));

            songt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            long albumid = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            Uri sAlbumArtUri = ContentUris.withAppendedId(sArtworkUri, albumid);
        }
        cursor.close();

        MediaPlayer mp = MediaPlayer.create(this, Uri.parse(song));
        mp.start();

        return songt;
    }
    public String albumart() {
        String songt=null;
        String song=null;
        Uri sAlbumArtUri = null;
        if(m_player != null) {
            m_player.release();
        }
        if(m_cursor.moveToNext()){
            MusicDto musicDto = new MusicDto();
            musicDto.setId(m_cursor.getString(m_cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicDto.setAlbumid(m_cursor.getString(m_cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicDto.setTitle(m_cursor.getString(m_cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicDto.setArtist(m_cursor.getString(m_cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));

            songt = m_cursor.getString(m_cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            song = m_cursor.getString(m_cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            long albumid = m_cursor.getLong(m_cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            sAlbumArtUri = ContentUris.withAppendedId(sArtworkUri, albumid);
        }else{
            Log.e(this.getClass().getName(), "엘스다");
            m_cursor.moveToFirst();
            return "file:///android_asset/k.jpg";
        }
//        cursor.close();

        m_player = MediaPlayer.create(this, Uri.parse(song));
        m_player.start();
        m_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(m_cursor.moveToNext()){
                    String song2 = m_cursor.getString(m_cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    try{
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(song2);
                        mediaPlayer.prepare();
                        mediaPlayer.start();}
                    catch (Exception e){

                        Log.e("TAG", "예외다~~~~~"+e.getMessage()+", ");
                    }
                }
            }
        });
        return sAlbumArtUri.toString();
    }
}
