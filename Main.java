package battleship;
public class Main {
    public static void main(String[] args) {
        Field field = new Field();
        for(var player:Players.values()){
            field.getCoordinates(player);
        }
        boolean endGame = true;
        while (endGame){
            for(var player:Players.values()){
                field.getShot(player);
                endGame=field.endGame(player);
                field.clean();
            }
        }
    }
}


