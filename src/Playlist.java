import java.io.Serializable;
import java.util.ArrayList;

public class Playlist implements Serializable {
    private final String name;
    private final String description;
    private final ArrayList<Song> songs;

    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
        this.songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Song getSong(int index) {
        return songs.get(index);
    }

    public int size() {
        return songs.size();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(int index) {
        songs.remove(index);
    }

    public void showSongs() {
        if(songs.isEmpty()){
            System.out.println("Плейлист пуст");
            return;
        }
        for(int i = 1; i < songs.size() + 1; i++) {
            System.out.println(i + " - " + getSong(i - 1));
        }
    }

    @Override
    public String toString() {
        return name + " \nОписание: " + description;
    }
}
