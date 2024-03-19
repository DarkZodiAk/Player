import java.io.Serializable;

public class Song implements Serializable {
    private final String name;
    private final String artist;
    private final int duration;

    public Song(String name, String artist, int duration) {
        this.name = name;
        this.artist = artist;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ". Исполнитель: " + artist + ". Длительность: " + duration + " секунд";
    }
}
