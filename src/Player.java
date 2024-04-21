import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    public ArrayList<Playlist> playlists = new ArrayList<>();
    public Playlist loadedPlaylist = null;
    public int playingSong = -1;
    public Scanner scanner;

    public Player(Scanner scanner){
        this.scanner = scanner;
    }

    public void createPlaylist(){
        String name, description;

        clearInput();
        System.out.print("Введите название плейлиста: ");
        name = scanner.nextLine();
        System.out.print("Введите описание плейлиста: ");
        description = scanner.nextLine();
        loadedPlaylist = new Playlist(name, description);
        playingSong = -1;
        System.out.print("Плейлист успешно создан\n");
    }

    public void loadPlaylist(){
        if(playlists.isEmpty()){
            System.out.println("Плейлистов нету");
            return;
        }

        int number = inputNumber("Введите номер плейлиста: ");

        if(number < 1 || number > playlists.size()){
            System.out.println("Введено неверное число!");
            return;
        }

        loadedPlaylist = playlists.get(number - 1);
        playingSong = -1;
        System.out.println("Плейлист успешно загружен");
    }

    public void savePlaylist(){
        if(loadedPlaylist == null) return;

        for(int i = 0; i < playlists.size(); i++){
            if(loadedPlaylist.getName() == playlists.get(i).getName()){
                playlists.set(i, loadedPlaylist);
                System.out.println("Плейлист успешно сохранен");
                return;
            }
        }
        playlists.add(loadedPlaylist);
        System.out.println("Плейлист успешно сохранен");
    }

    public void deletePlaylist(){
        if(playlists.isEmpty()){
            System.out.println("Плейлистов нету");
            return;
        }

        int number = inputNumber("Введите номер плейлиста: ");

        if(number < 1 || number > playlists.size()){
            System.out.println("Введено неверное число!");
            return;
        }

        playlists.remove(number - 1);
        System.out.println("Плейлист успешно удален");
    }

    public void addSong(){
        if(loadedPlaylist == null) return;

        String name, artist;
        int duration;

        clearInput();
        System.out.print("Введите название песни: ");
        name = scanner.nextLine();

        System.out.print("Введите имя исполнителя: ");
        artist = scanner.nextLine();

        do{
            System.out.print("Введите длительность песни в секундах: ");
            try{
                duration = scanner.nextInt();
            } catch(InputMismatchException e){
                clearInput();
                System.out.println("Введено неверное число! Попробуйте еще раз");
                continue;
            }
            break;
        } while(true);

        loadedPlaylist.addSong(new Song(name, artist, duration));
        System.out.print("Песня успешно добавлена\n");
    }

    public void deleteSong(){
        if(loadedPlaylist == null) return;
        if(loadedPlaylist.size() == 0){
            System.out.println("Плейлист пуст");
            return;
        }

        int number = inputNumber("Введите номер песни: ");

        if(number < 1 || number > loadedPlaylist.size()){
            System.out.println("Введено неверное число!");
            return;
        }

        loadedPlaylist.removeSong(number - 1);
        if(loadedPlaylist.size() == 0){
            playingSong = -1;
        } else {
            playingSong = playingSong % loadedPlaylist.size();
        }
        System.out.println("Песня успешно удалена");
    }

    public void playSong(){
        if(loadedPlaylist == null) return;
        if(loadedPlaylist.size() == 0){
            System.out.println("Плейлист пуст");
            return;
        }

        int number = inputNumber("Введите номер песни: ");

        if(number < 1 || number > loadedPlaylist.size()){
            System.out.println("Введено неверное число!");
            return;
        }

        playingSong = number - 1;
    }

    public void nextSong(){
        if(loadedPlaylist == null || playingSong == -1) return;
        if(loadedPlaylist.size() == 0){
            System.out.println("Плейлист пуст");
            return;
        }

        playingSong = (playingSong + 1) % loadedPlaylist.size();
    }

    public void prevSong(){
        if(loadedPlaylist == null || playingSong == -1) return;
        if(loadedPlaylist.size() == 0){
            System.out.println("Плейлист пуст");
            return;
        }

        playingSong = (playingSong - 1) % loadedPlaylist.size();
        if(playingSong < 0) playingSong = loadedPlaylist.size() + playingSong;
    }

    public void exportPlaylists(){
        if(playlists.isEmpty()){
            System.out.println("Плейлистов нету");
            return;
        }

        clearInput();
        System.out.print("Введите имя файла: ");
        String fileName = scanner.nextLine();

        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(playlists);

            System.out.println("Плейлисты были сохранены в файл с именем " + fileName);
            fileOut.close();
            objectOut.close();
        }
        catch (IOException e) {
            System.out.println("Произошла ошибка во время сохранения в файл");
        }
    }

    public void importPlaylists(){
        clearInput();
        System.out.print("Введите имя файла: ");
        String fileName = scanner.nextLine();

        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            playlists = (ArrayList<Playlist>) objectIn.readObject();

            System.out.println("Плейлисты были загружены из файла");
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Произошла ошибка во время загрузки из файла");
        }
    }

    public void clearConsole(){
        for(int i = 0; i < 50; i++) System.out.print("\n");
    }

    public void clearInput(){
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    public int inputNumber(String inputMessage){
        int number;
        clearInput();
        System.out.print(inputMessage);
        try{
            number = scanner.nextInt();
        } catch(InputMismatchException e){
            clearInput();
            System.out.println("Введено неверное число!");
            return 0;
        }
        return number;
    }
}
