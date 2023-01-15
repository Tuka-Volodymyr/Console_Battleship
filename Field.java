package battleship;
import java.io.Console;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
public class Field {

    private final int[][] firstPlayerFl = new int[10][10];
    private final int[][] secondPlayerFl = new int[10][10];

    private final int FOG = 0, SHIP = 1, HIT = -1, MISS = -2;
    private boolean boolMISS=false;
    private boolean boolHIT=false;
    Scanner scanner=new Scanner(System.in);
    public Field(){
        for(var i:firstPlayerFl){
            Arrays.fill(i,FOG);
        }
        for(var i:secondPlayerFl){
            Arrays.fill(i,FOG);
        }
    }
    public String fogOfWar(Players player){
        String builder;
        if(player.num==1){
             builder= toString(firstPlayerFl);
        }else {
             builder= toString(secondPlayerFl);
        }
        builder=builder.replace('O','~');
        builder=builder.replace('X','~');
        builder=builder.replace('M','~');
        return builder;
    }
    public void getCoordinates(Players player) {
        System.out.printf("Player %d, place your ships on the game field\n\n", player.num);
        System.out.println(fogOfWar(player));
        for (var ship : Ship.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.name, ship.numberOfCells);

            if(player.num==1){
                placeShips(ship,firstPlayerFl);
                System.out.println(toString(firstPlayerFl));
            }else {
                placeShips(ship,secondPlayerFl);
                System.out.println(toString(secondPlayerFl));
            }

            System.out.println();
        }
        clean();
    }
    public boolean endGame(Players player){
        if(player.num==1){
            if(toString(secondPlayerFl).indexOf('O')==-1){
                System.out.println("You sank the last ship. You won. Congratulations!");
                return false;
            }
        }else {
            if(toString(firstPlayerFl).indexOf('O')==-1){
                System.out.println("You sank the last ship. You won. Congratulations!");
                return false;
            }
        }
        return true;
    }
    public void getShot(Players player) {
        System.out.println(fogOfWar(player));
        System.out.println("---------------------");
        if (player.num == 1) {
            System.out.println(toString(firstPlayerFl));
        } else {
            System.out.println(toString(secondPlayerFl));
        }
        System.out.println();
        System.out.printf("Player %d, it's your turn:\n", player.num);
        if (player.num == 1) {
            shot(secondPlayerFl);
        } else {
            shot(firstPlayerFl);
        }
        if(boolHIT){
            System.out.println("You hit a ship!");
        } else if(boolMISS){
            System.out.println("You missed!");
        }
    }
    public void placeShips(Ship ship,int[][] field){
        while (true){
            try {
                String[] coordinates=new String[]{scanner.next(), scanner.next()};
                var x1= coordinates[0].charAt(0) - 65;
                var y1 = Integer.parseInt(coordinates[0].substring(1)) - 1;
                var x2 = coordinates[1].charAt(0) - 65;
                var y2 = Integer.parseInt(coordinates[1].substring(1)) - 1;
                int[] start = new int[]{Math.min(x1, x2), Math.min(y1, y2)};
                int[] end = new int[]{Math.max(x1, x2), Math.max(y1, y2)};
                if(start[0]!=end[0]&&start[1]!=end[1]) {
                    throw new IllegalArgumentException("Error! Wrong ship location! Try again:");
                }
                int alignment = (start[0] == end[0]) ? 1 : 0;
                if (Math.abs(start[alignment] - end[alignment]) + 1 != ship.numberOfCells)
                    throw new IllegalArgumentException("Error! Wrong length of the Submarine! Try again:");
                neighbors(start,end,field);
                if (alignment == 1) {
                    for (int i = start[1]; i <= end[1]; i++) {
                        field[start[0]][i] = SHIP;
                    }
                }else {
                    for (int i = start[0]; i <= end[0]; i++) {
                        field[i][start[1]] = SHIP;
                    }
                }
                break;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

        }
    }
    public void shot(int[][] field){
        while (true){
            try {
                boolHIT=false;
                boolMISS=false;
                System.out.println();
                String coordinates=scanner.next();
                int x=coordinates.charAt(0) - 65;
                int y=Integer.parseInt(coordinates.substring(1))-1;
                if(x>9||x<0||y>9||y<0)throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
                if(field[x][y]==SHIP) {
                    boolHIT=true;
                    field[x][y] = HIT;
                }
                if(field[x][y]==FOG){
                    boolMISS=true;
                    field[x][y]=MISS;
                }
                break;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
    private void neighbors(int[] start, int[] end,int[][] field) {
        int startPosX = (start[0] - 1 < 0) ? start[0] : start[0] - 1;
        int startPosY = (start[1] - 1 < 0) ? start[1] : start[1] - 1;
        int endPosX = (end[0] + 1 > 9) ? end[0] : end[0] + 1;
        int endPosY = (end[1] + 1 > 9) ? end[1] : end[1] + 1;
        for (int a = startPosX; a <= endPosX; a++) {
            for (int b = startPosY; b <= endPosY; b++) {
                if (field[a][b] == SHIP)
                    throw new IllegalArgumentException("Error! You placed it too close to another one. Try again:");
            }
        }
    }
    public String toString(int[][] field) {
        var builder = new StringBuilder();
        builder.append("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (j == 0) builder.append((char) (65 + i)).append(" ");
                switch (field[i][j]) {
                    case FOG:
                        builder.append('~');
                        break;
                    case SHIP:
                        builder.append('O');
                        break;
                    case HIT:
                        builder.append('X');
                        break;
                    case MISS:
                        builder.append('M');
                        break;
                }
                if (j < 9) builder.append(" ");
            }
            if (i < 9) builder.append("\n");
        }
        return builder.toString();
    }
    Scanner sc =new Scanner(System.in);
    public void clean(){
        System.out.println("Press Enter and pass the move to another player\n");
        String clean=sc.nextLine();
        if(clean.equals("\n")){
            System.out.println("\n\n\n");
        }
    }
}
