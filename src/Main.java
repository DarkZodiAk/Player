import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static Player player = new Player(scanner);

    public static void main(String[] args) {

        int choice;

        do {
            if(player.loadedPlaylist != null){
                System.out.println("Текущий плейлист: " + player.loadedPlaylist.getName());
                if(player.playingSong > -1){
                    System.out.println("Играет песня: " + player.loadedPlaylist.getSong(player.playingSong).getName());
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
                player.clearConsole();
            } catch(InputMismatchException e){
                player.clearInput();
                player.clearConsole();
                System.out.println("Введено неверное число!");
                continue;
            }

            if(choice == 0) break;

            switch(choice){
                case 1:
                    if(player.loadedPlaylist != null){
                        player.loadedPlaylist.showSongs();
                    }
                    break;
                case 2:
                    if(player.playlists.isEmpty()){
                        System.out.println("Плейлистов нету");
                        continue;
                    }
                    for(int i = 1; i < player.playlists.size() + 1; i++){
                        System.out.println(i + " - " + player.playlists.get(i - 1) +
                                "\nКоличество песен: " + player.playlists.get(i - 1).size() + "\n");
                    }
                    break;
                case 3:
                    player.createPlaylist();
                    break;
                case 4:
                    player.loadPlaylist();
                    break;
                case 5:
                    player.savePlaylist();
                    break;
                case 6:
                    player.deletePlaylist();
                    break;
                case 7:
                    player.addSong();
                    break;
                case 8:
                    player.deleteSong();
                    break;
                case 9:
                    player.playSong();
                    break;
                case 10:
                    player.prevSong();
                    break;
                case 11:
                    player.nextSong();
                    break;
                case 12:
                    player.exportPlaylists();
                    break;
                case 13:
                    player.importPlaylists();
                    break;

                default:
                    System.out.println("Введено неверное число!");
                    break;
            }
        } while(true);
    }
}