import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static ArrayList<Playlist> playlists = new ArrayList<>();
    public static Playlist loadedPlaylist = null;
    public static int playingSong = -1;
    public static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        int choice;

        do {
            if(loadedPlaylist != null){
                System.out.println("Текущий плейлист: " + loadedPlaylist.getName());
                if(playingSong > -1){
                    System.out.println("Играет песня: " + loadedPlaylist.getSong(playingSong).getName());
                }
            } else {
                System.out.println("Плейлист не загружен");
            }

            System.out.print(
                    " 0 - Выйти\n" +
                    " 1 - Показать список песен\n" +
                    " 2 - Показать список плейлистов\n" +
                    " 3 - Создать плейлист\n" +
                    " 4 - Включить плейлист\n" +
                    " 5 - Сохранить плейлист\n" +
                    " 6 - Удалить плейлист\n" +
                    " 7 - Добавить песню в плейлист\n" +
                    " 8 - Удалить песню из плейлиста\n" +
                    " 9 - Включить песню по номеру\n" +
                    " 10 - Включить предыдущий трек\n" +
                    " 11 - Включить следующий трек\n" +
                    " 12 - Сохранить плейлисты в файл\n" +
                    " 13 - Загрузить плейлисты из файла\n" +
                    " > "
            );

            try{
                choice = scanner.nextInt();
                clearConsole();
            } catch(InputMismatchException e){
                clearInput();
                clearConsole();
                System.out.println("Введено неверное число!");
                continue;
            }

            if(choice == 0) break;

            switch(choice){
                case 1:
                    if(loadedPlaylist != null){
                        loadedPlaylist.showSongs();
                    }
                    break;
                case 2:
                    if(playlists.isEmpty()){
                        System.out.println("Плейлистов нету");
                        continue;
                    }
                    for(int i = 1; i < playlists.size() + 1; i++){
                        System.out.println(i + " - " + playlists.get(i - 1) +
                                "\nКоличество песен: " + playlists.get(i - 1).size() + "\n");
                    }
                    break;
                case 3:
                    createPlaylist();
                    break;
                case 4:
                    loadPlaylist();
                    break;
                case 5:
                    savePlaylist();
                    break;
                case 6:
                    deletePlaylist();
                    break;
                case 7:
                    addSong();
                    break;
                case 8:
                    deleteSong();
                    break;
                case 9:
                    playSong();
                    break;
                case 10:
                    prevSong();
                    break;
                case 11:
                    nextSong();
                    break;
                case 12:
                    exportPlaylists();
                    break;
                case 13:
                    importPlaylists();
                    break;

                default:
                    System.out.println("Введено неверное число!");
                    break;
            }
        } while(true);
    }

    public static void createPlaylist(){
        String name, description;

        clearInput();
        System.out.print("Введите название плейлиста: ");
        name = scanner.nextLine();
        System.out.print("Введите описание плейлиста: ");
        description = scanner.nextLine();
        loadedPlaylist = new Playlist(name, description);
        System.out.print("Плейлист успешно создан\n");
    }

    public static void loadPlaylist(){
        int number;

        if(playlists.isEmpty()){
            System.out.println("Плейлистов нету");
            return;
        }

        clearInput();
        System.out.print("Введите номер плейлиста: ");
        try{
            number = scanner.nextInt();
        } catch(InputMismatchException e){
            clearInput();
            System.out.println("Введено неверное число!");
            return;
        }

        if(number < 1 || number > playlists.size()){
            System.out.println("Введено неверное число!");
            return;
        }

        loadedPlaylist = playlists.get(number - 1);
        System.out.println("Плейлист успешно загружен");
    }

    public static void savePlaylist(){
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

    public static void deletePlaylist(){
        int number;

        if(playlists.isEmpty()){
            System.out.println("Плейлистов нету");
            return;
        }

        clearInput();
        System.out.print("Введите номер плейлиста: ");
        try{
            number = scanner.nextInt();
        } catch(InputMismatchException e){
            clearInput();
            System.out.println("Введено неверное число!");
            return;
        }

        if(number < 1 || number > playlists.size()){
            System.out.println("Введено неверное число!");
            return;
        }

        playlists.remove(number - 1);
        System.out.println("Плейлист успешно удален");
    }

    public static void addSong(){
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

    public static void deleteSong(){
        if(loadedPlaylist == null) return;
        if(loadedPlaylist.size() == 0){
            System.out.println("Плейлист пуст");
            return;
        }

        int number;

        clearInput();
        System.out.print("Введите номер песни: ");
        try{
            number = scanner.nextInt();
        } catch(InputMismatchException e){
            clearInput();
            System.out.println("Введено неверное число!");
            return;
        }

        if(number < 1 || number > loadedPlaylist.size()){
            System.out.println("Введено неверное число!");
            return;
        }

        loadedPlaylist.removeSong(number - 1);
        playingSong = playingSong % loadedPlaylist.size();
        System.out.println("Песня успешно удалена");
    }

    public static void playSong(){
        if(loadedPlaylist == null) return;
        if(loadedPlaylist.size() == 0){
            System.out.println("Плейлист пуст");
            return;
        }

        int number;

        clearInput();
        System.out.print("Введите номер песни: ");
        try{
            number = scanner.nextInt();
        } catch(InputMismatchException e){
            clearInput();
            System.out.println("Введено неверное число!");
            return;
        }

        if(number < 1 || number > loadedPlaylist.size()){
            System.out.println("Введено неверное число!");
            return;
        }

        playingSong = number - 1;
    }

    public static void nextSong(){
        if(loadedPlaylist == null) return;
        if(loadedPlaylist.size() == 0){
            System.out.println("Плейлист пуст");
            return;
        }

        playingSong = (playingSong + 1) % loadedPlaylist.size();
    }

    public static void prevSong(){
        if(loadedPlaylist == null) return;
        if(loadedPlaylist.size() == 0){
            System.out.println("Плейлист пуст");
            return;
        }

        playingSong = (playingSong - 1) % loadedPlaylist.size();
        if(playingSong < 0) playingSong = loadedPlaylist.size() + playingSong;
    }

    public static void exportPlaylists(){
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

    public static void importPlaylists(){
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

    public static void clearConsole(){
        for(int i = 0; i < 50; i++) System.out.print("\n");
    }

    public static void clearInput(){
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}