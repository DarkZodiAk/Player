import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    ArrayList<Playlist> playlistsToAdd = new ArrayList<>();
    ArrayList<Song> songsToAdd = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    @BeforeEach
    void setUp() {
        playlistsToAdd.clear();
        songsToAdd.clear();
        playlistsToAdd.add(new Playlist("p1","d1"));
        playlistsToAdd.add(new Playlist("p2","d2"));
        playlistsToAdd.add(new Playlist("p3","d3"));
        songsToAdd.add(new Song("s1", "art1", 5));
        songsToAdd.add(new Song("s2", "art2", 50));
        songsToAdd.add(new Song("s3", "art3", 89));
    }

    @Test
    void loadPlaylistFromEmptyArray() {
        Player player = new Player(scanner);
        player.loadPlaylist(1);
        assertNull(player.loadedPlaylist);
    }

    @Test
    void loadPlaylist() {
        Player player = new Player(scanner);
        player.playlists = playlistsToAdd;
        player.loadPlaylist(2);
        assertEquals(player.loadedPlaylist, playlistsToAdd.get(1));
    }

    @Test
    void loadPlaylistWithWrongNumber() {
        Player player = new Player(scanner);
        player.loadPlaylist(4);
        assertNull(player.loadedPlaylist);
        player.loadPlaylist(-1);
        assertNull(player.loadedPlaylist);
    }

    @Test
    void deletePlaylistFromEmptyArray() {
        Player player = new Player(scanner);
        player.deletePlaylist(1);
        assertEquals(player.playlists.size(), 0);
    }

    @Test
    void deletePlaylist() {
        Player player = new Player(scanner);
        player.playlists = playlistsToAdd;
        player.deletePlaylist(1);
        assertEquals(player.playlists.size(), 2);
    }

    @Test
    void savePlaylist() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        player.savePlaylist();
        assertEquals(player.playlists.size(), 1);
    }

    @Test
    void saveNullPlaylist() {
        Player player = new Player(scanner);
        player.savePlaylist();
        assertEquals(player.playlists.size(), 0);
    }

    @Test
    void deleteSongFromEmptyPlaylist() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        player.deleteSong(1);
        assertEquals(player.loadedPlaylist.size(), 0);
    }

    @Test
    void deleteSongFromPlaylist() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        player.loadedPlaylist.addSong(songsToAdd.get(0));
        assertEquals(player.loadedPlaylist.size(), 1);
        player.deleteSong(1);
        assertEquals(player.loadedPlaylist.size(), 0);
    }

    @Test
    void playSongFromEmptyPlaylist() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        player.playSong(1);
        assertEquals(player.playingSong, -1);
    }

    @Test
    void playSong() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        for(int i = 0; i < 3; i++){
            player.loadedPlaylist.addSong(songsToAdd.get(i));
        }
        player.playSong(1);
        assertEquals(player.playingSong, 0);
    }

    @Test
    void deletePlayingSong() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        for(int i = 0; i < 3; i++){
            player.loadedPlaylist.addSong(songsToAdd.get(i));
        }
        player.playSong(3);
        player.deleteSong(3);
        assertEquals(player.playingSong, 0);
    }

    @Test
    void deleteOnlyOnePlayingSong() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        player.loadedPlaylist.addSong(songsToAdd.get(0));
        player.playSong(1);
        player.deleteSong(1);
        assertEquals(player.playingSong, -1);
    }

    @Test
    void switchToNewPlaylistWhenPlayingSong() {
        Player player = new Player(scanner);
        player.playlists = playlistsToAdd;
        player.loadedPlaylist = playlistsToAdd.get(0);
        player.loadedPlaylist.addSong(songsToAdd.get(0));
        player.playSong(1);
        player.loadPlaylist(2);
        assertEquals(player.playingSong, -1);
    }

    @Test
    void nextSongOnEmptyPlaylist() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        player.loadedPlaylist.addSong(songsToAdd.get(0));
        player.nextSong();
        assertEquals(player.playingSong, -1);
    }

    @Test
    void nextSongOnPlaylistWithOneSong() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        player.loadedPlaylist.addSong(songsToAdd.get(0));
        player.playSong(1);
        player.nextSong();
        assertEquals(player.playingSong, 0);
    }

    @Test
    void nextSong() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        for(int i = 0; i < 3; i++){
            player.loadedPlaylist.addSong(songsToAdd.get(i));
        }
        player.playSong(2);
        player.nextSong();
        assertEquals(player.playingSong, 2);
    }

    @Test
    void nextSongOnLastInPlaylist() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        for(int i = 0; i < 3; i++){
            player.loadedPlaylist.addSong(songsToAdd.get(i));
        }
        player.playSong(3);
        player.nextSong();
        assertEquals(player.playingSong, 0);
    }

    @Test
    void prevSongOnEmptyPlaylist() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        player.loadedPlaylist.addSong(songsToAdd.get(0));
        player.nextSong();
        assertEquals(player.playingSong, -1);
    }

    @Test
    void prevSongOnPlaylistWithOneSong() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        player.loadedPlaylist.addSong(songsToAdd.get(0));
        player.playSong(1);
        player.prevSong();
        assertEquals(player.playingSong, 0);
    }

    @Test
    void prevSong() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        for(int i = 0; i < 3; i++){
            player.loadedPlaylist.addSong(songsToAdd.get(i));
        }
        player.playSong(3);
        player.prevSong();
        assertEquals(player.playingSong, 1);
    }

    @Test
    void prevSongOnFirstInPlaylist() {
        Player player = new Player(scanner);
        player.loadedPlaylist = playlistsToAdd.get(0);
        for(int i = 0; i < 3; i++){
            player.loadedPlaylist.addSong(songsToAdd.get(i));
        }
        player.playSong(1);
        player.prevSong();
        assertEquals(player.playingSong, 2);
    }
}