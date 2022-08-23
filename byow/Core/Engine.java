package byow.Core;


import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Random;



public class Engine {
    public TERenderer bruh = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int xOFF = 5, yOFF = 5;
    public HashMap<Integer, Room> roomHash = new HashMap<>();
    public Random generator;
    public boolean gameOver;
    private KeyboardInputSource keyTaker = new KeyboardInputSource();
    public String finalSeed;
    public int avatarX;
    public int avatarY;
    public String moveStringOld; // moves processed at the time of quitting
    public String moveStringNew;
    private TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
    private File file = new File("save.txt");
    public String currentSeed = "";
    public Boolean wasCol = false;
    public Boolean debugMode = true;
    public boolean replaySave = false;
    public TETile CHARACTER = Tileset.AVATAR;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */

    public static void main(String[] args) {
        Engine game = new Engine();
        TERenderer bruh = new TERenderer();
        //bruh.initialize(WIDTH + xOFF + yOFF, HEIGHT + xOFF + yOFF, xOFF, yOFF);
        //TETile[][] world = game.interactWithInputString("LAAAAAAAAA:Q");
        //game.mainMenu();
        game.interactWithKeyboard();
    }



    public void interactWithKeyboard() {
        if (debugMode) {
            bruh.initialize(WIDTH + xOFF + yOFF, HEIGHT + xOFF + yOFF, xOFF, yOFF);
            mainMenu();
            StdDraw.clear(Color.BLACK);
            bruh.initialize(WIDTH + xOFF + yOFF, HEIGHT + xOFF + yOFF, xOFF, yOFF);
            bruh.renderFrame(finalWorldFrame);
            StdDraw.pause(200);
            if (replaySave) {
                movementReplay(moveStringOld);
            }
            while (!gameOver) {
                hud();
                movement();
            }
            System.exit(0);
        }

    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world N23612547236S
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        long generatorInput = 123456789;
        if(input.substring(0,1).equals("n") || input.substring(0,1).equals("N")) { //input = N.....S.....
            int end = 1;

            for(int point=1; point<input.length();point++){
                if(input.substring(point,point+1).equals("s") || input.substring(point,point+1).equals("S")){
                end = point;
                break;
                }
            }
            generatorInput = Long.parseLong(input.substring(1,end));//Long.parseLong(input.substring(1, input.length() - 1));
            moveStringOld = input.substring(end+1);
        } else { //input = L......
                generatorInput = Load(); //should get seed from previous game
            if (input.length() > 1) {
                moveStringOld = moveStringOld + input.substring(1);
            }
        }

        generator = new Random(generatorInput);
        currentSeed = Long.toString(generatorInput);
        finalWorldFrame = makeEverythingNothing(finalWorldFrame);
        Quadrant[] quadrantArray = quadrantMaker();
        finalWorldFrame = roomMaker(finalWorldFrame, quadrantArray);

        //making hallways
        for (int i = 0; i < roomHash.size() - 1; i++) {
            Hallway(finalWorldFrame, generator, roomHash.get(i), roomHash.get(i + 1),RandomUtils.uniform(generator,1,2));
        }
        spawn();
        if (!replaySave) {
            moveStringNew = "";
            processOld(moveStringOld);
        }
        System.out.println(TETile.toString(finalWorldFrame));
        return finalWorldFrame;
    }

    public void movementReplay(String oldMoves) {
        int start = 0;
        for (int i = 0; i < oldMoves.length(); i++) {
            if (!isNumeric(oldMoves.substring(i, i + 1))) {
                start = i;
                break;
            }
        }
        for (char c : oldMoves.substring(start).toCharArray()) {
            if (c == 'w' || c == 'W') {
                if (finalWorldFrame[avatarX][avatarY + 1] == Tileset.FLOOR) {
                    finalWorldFrame[avatarX][avatarY] = Tileset.FLOOR;
                    finalWorldFrame[avatarX][avatarY + 1] = CHARACTER;
                    avatarY = avatarY + 1;
                    moveStringNew = moveStringNew + c;
                }
            } else if (c == 's' || c == 'S') {
                if(avatarY-1>=0){
                    if (finalWorldFrame[avatarX][avatarY - 1] == Tileset.FLOOR) {
                        finalWorldFrame[avatarX][avatarY] = Tileset.FLOOR;
                        finalWorldFrame[avatarX][avatarY - 1] = CHARACTER;
                        avatarY = avatarY - 1;
                        moveStringNew = moveStringNew + c;
                    }}
            } else if (c == 'a' || c == 'A') {
                if(avatarX-1>=0){
                    if (finalWorldFrame[avatarX - 1][avatarY] == Tileset.FLOOR) {
                        finalWorldFrame[avatarX][avatarY] = Tileset.FLOOR;
                        finalWorldFrame[avatarX - 1][avatarY] = CHARACTER;
                        avatarX = avatarX - 1;
                        moveStringNew = moveStringNew + c;
                    }}
            } else if (c == 'd' || c == 'D') {
                if (finalWorldFrame[avatarX + 1][avatarY] == Tileset.FLOOR) {
                    finalWorldFrame[avatarX][avatarY] = Tileset.FLOOR;
                    finalWorldFrame[avatarX + 1][avatarY] = CHARACTER;
                    avatarX = avatarX + 1;
                    moveStringNew = moveStringNew + c;
                }
            }
            bruh.renderFrame(finalWorldFrame);
            StdDraw.pause(200);
        }
    }


    public void processOld(String oldMoves) {
        for (int i = 0; i < oldMoves.length(); i++) {
            char c = oldMoves.charAt(i);
            if (c == 'w' || c == 'W') {
                if (finalWorldFrame[avatarX][avatarY + 1] == Tileset.FLOOR) {
                    finalWorldFrame[avatarX][avatarY] = Tileset.FLOOR;
                    finalWorldFrame[avatarX][avatarY + 1] = CHARACTER;
                    avatarY = avatarY + 1;
                    moveStringNew = moveStringNew + c;
                }
            } else if (c == 's' || c == 'S') {
                if(avatarY-1>=0){
                if (finalWorldFrame[avatarX][avatarY - 1] == Tileset.FLOOR) {
                    finalWorldFrame[avatarX][avatarY] = Tileset.FLOOR;
                    finalWorldFrame[avatarX][avatarY - 1] = CHARACTER;
                    avatarY = avatarY - 1;
                    moveStringNew = moveStringNew + c;
                }}
            } else if (c == 'a' || c == 'A') {
                if(avatarX-1>=0){
                if (finalWorldFrame[avatarX - 1][avatarY] == Tileset.FLOOR) {
                    finalWorldFrame[avatarX][avatarY] = Tileset.FLOOR;
                    finalWorldFrame[avatarX - 1][avatarY] = CHARACTER;
                    avatarX = avatarX - 1;
                    moveStringNew = moveStringNew + c;
                }}
            } else if (c == 'd' || c == 'D') {
                if (finalWorldFrame[avatarX + 1][avatarY] == Tileset.FLOOR) {
                    finalWorldFrame[avatarX][avatarY] = Tileset.FLOOR;
                    finalWorldFrame[avatarX + 1][avatarY] = CHARACTER;
                    avatarX = avatarX + 1;
                    moveStringNew = moveStringNew + c;
                }
            } else if (c == 'q' || c == 'Q') {
                if (oldMoves.charAt(i - 1) == ':' ) {
                    Quit();
                }
            }
        }
    }

    public long Load() {
        long generatorInput = 68654864;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String previousGame = reader.readLine(); //  .....S..... (:Q not included)
            int end = 0;
            for (int i = 0; i < previousGame.length(); i++) {
                if (!isNumeric(previousGame.substring(i, i + 1))) {
                    end = i;
                    break;
                }
            }
            generatorInput = Long.parseLong(previousGame.substring(0, end));
            moveStringOld = previousGame.substring(end + 1);
        } catch (IOException e) {
            System.out.print("IOException");
        }
        return generatorInput;
    }

    public boolean isNumeric(String str){
        try{
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }



    public void spawn() {
        int Pointy = RandomUtils.uniform(generator, 1, 25);
        int Pointx = RandomUtils.uniform(generator,1,75);
        for(int y = Pointy; y<30 ; y++) {
            for(int x=Pointx; x<80; x++){
                if(finalWorldFrame[x][y]==Tileset.FLOOR){
                    finalWorldFrame[x][y]= CHARACTER;
                    avatarX= x;
                    avatarY= y;
                    return;
                }
            }
        }
    }

    public TETile[][] makeEverythingNothing(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        return tiles;
    }

    private void hud() {
        int x = (int) Math.floor(StdDraw.mouseX()) - xOFF;
        int y = (int) Math.floor(StdDraw.mouseY()) - yOFF;
        StdDraw.clear(StdDraw.BLACK);
        String display = "";
        if (x >= xOFF && x < WIDTH - xOFF && y >= yOFF && y < HEIGHT - yOFF) {
            TETile currentTile = finalWorldFrame[x][y];
                display = currentTile.description();
        }
        bruh.renderFrame(finalWorldFrame);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(15, 36, display);
        StdDraw.show();
        StdDraw.pause(50);
        return;
    }

    private void movement() {
        if (!StdDraw.hasNextKeyTyped()) {
            return;
        }
        char c = keyTaker.getNextKey();
        if (c == 'w' || c == 'W'){
            if (finalWorldFrame[avatarX][avatarY+1]==Tileset.FLOOR) {
                finalWorldFrame[avatarX][avatarY]=Tileset.FLOOR;
                finalWorldFrame[avatarX][avatarY+1] = CHARACTER;
                avatarY= avatarY+1;
                moveStringNew = moveStringNew + c;
                wasCol= false;
            }
        } else if(c == 's' || c == 'S'){
            if(finalWorldFrame[avatarX][avatarY-1]==Tileset.FLOOR){
                finalWorldFrame[avatarX][avatarY]=Tileset.FLOOR;
                finalWorldFrame[avatarX][avatarY-1] = CHARACTER;
                avatarY= avatarY-1;
                moveStringNew = moveStringNew + c;
                wasCol= false;
            }
        } else if(c == 'a' || c == 'A'){
            if(finalWorldFrame[avatarX-1][avatarY]==Tileset.FLOOR){
                finalWorldFrame[avatarX][avatarY]=Tileset.FLOOR;
                finalWorldFrame[avatarX-1][avatarY] = CHARACTER;
                avatarX= avatarX-1;
                moveStringNew = moveStringNew + c;
                wasCol= false;
            }
        } else if(c == 'd' || c == 'D'){
            if(finalWorldFrame[avatarX+1][avatarY]==Tileset.FLOOR){
                finalWorldFrame[avatarX][avatarY]=Tileset.FLOOR;
                finalWorldFrame[avatarX+1][avatarY] = CHARACTER;
                avatarX= avatarX+1;
                moveStringNew = moveStringNew + c;
                wasCol= false;
            }
        } else if (c == ':' ){
            wasCol= true;
        }
        else if (c == 'q' || c == 'Q') {
            if (wasCol) {
                wasCol=false;
                Quit();
            }
        }
        return;
    }


    public void Quit() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            String serializeString = currentSeed + "S" + moveStringNew; //  .....S..... (:Q not included)
            fileWriter.write(serializeString);
            fileWriter.close();
            gameOver = true;
        } catch (IOException e) {
            System.out.print("IOException");
        }
    }



    //creates main menu
    private void mainMenu() {
        drawMainMenu(CHARACTER);
        while (true) {
            char entry = keyTaker.getNextKey();
            if (entry == 'N' || entry == 'n') {
                 newGame();
                 break;
            } else if (entry == 'L' || entry == 'l') {
                loadGame();
                break;
            } else if (entry == 'X' || entry == 'x') {
                if (CHARACTER == Tileset.AVATAR) {
                    CHARACTER = Tileset.FLOWER;
                } else if (CHARACTER == Tileset.FLOWER) {
                    CHARACTER = Tileset.MOUNTAIN;
                } else if (CHARACTER == Tileset.MOUNTAIN) {
                    CHARACTER = Tileset.AVATAR;
                }
                drawMainMenu(CHARACTER);
                continue;
            } else if (entry == 'R' || entry == 'r') {
                replaySave = true;
                loadGame();
                break;
            } else if (entry == 'Q' || entry == 'q') {
                System.exit(0);
                break;
            }
        }
    }

    public void drawMainMenu(TETile player) {
         StdDraw.clear(StdDraw.BLACK);
         StdDraw.setPenColor(StdDraw.WHITE);
         Font fontBig = new Font("Monaco", Font.BOLD, 30);
         StdDraw.setFont(fontBig);
         StdDraw.text(WIDTH / 2 + xOFF, HEIGHT / 2 + yOFF * 2, "CS61BL: THE GAME");
         Font fontSmall = new Font("Monaco", Font.BOLD, 20);
         StdDraw.setFont(fontSmall);
         StdDraw.text(WIDTH / 2 + xOFF, HEIGHT / 3 + 2, "New Game (N)");
         StdDraw.text(WIDTH / 2 + xOFF, HEIGHT / 3, "Load Game (L)");
        if (player == Tileset.AVATAR) {
            StdDraw.text(WIDTH / 2 + xOFF, HEIGHT / 3 - 2, "Character Change(X): DEFAULT");
        } else if (player == Tileset.FLOWER) {
            StdDraw.text(WIDTH / 2 + xOFF, HEIGHT / 3 - 2, "Character Change(X): FLOWER");
        } else if (player == Tileset.MOUNTAIN) {
            StdDraw.text(WIDTH / 2 + xOFF, HEIGHT / 3 - 2, "Character Change(X): MOUNTAIN");
        }
         StdDraw.text(WIDTH / 2 + xOFF, HEIGHT / 3 - 4, "Replay Last Save(R)");
         StdDraw.text(WIDTH / 2 + xOFF, HEIGHT / 3 - 6, "Quit (Q)");
         StdDraw.show();
    }


    private void loadGame() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String previousGame = reader.readLine(); //  .....S..... (:Q not included)
            int end = 0;
            for (int i = 0; i < previousGame.length(); i++) {
                if (!isNumeric(previousGame.substring(i, i + 1))) {
                    end = i;
                    break;
                }
            }
            long generatorInput = Long.parseLong(previousGame.substring(0, end));
            moveStringOld = previousGame.substring(end + 1);
            finalWorldFrame = interactWithInputString("N" + generatorInput + "S" + moveStringOld);
        } catch (IOException e) {
            System.out.print("IOException");
        }

    }

    private void newGame() {
        String seed = "";
        while (true) {
            StdDraw.clear(StdDraw.BLACK);
            Font fontBig = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(fontBig);
            StdDraw.text(WIDTH / 2 + xOFF, HEIGHT / 2 + yOFF * 2, "Enter a seed. Press 'S' to end.");
            Font fontSmall = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(fontSmall);
            StdDraw.text(WIDTH / 2 + xOFF, HEIGHT / 3, seed);
            StdDraw.show();
            char entry = keyTaker.getNextKey();
            if (entry == 'S' && !seed.equals("")) {
                finalSeed = "N" + seed + "S";
                break;
            } else {
                seed = seed + entry;
            }
        }
        finalWorldFrame = interactWithInputString(finalSeed);
    }


    public Quadrant[] quadrantMaker() {
        Quadrant One = new Quadrant(0, 0);
        Quadrant Two = new Quadrant(0, 15);
        Quadrant Three = new Quadrant(20, 0);
        Quadrant Four = new Quadrant(20, 15);
        Quadrant Five = new Quadrant(40, 0);
        Quadrant Six = new Quadrant(40, 15);
        Quadrant Seven = new Quadrant(60, 0);
        Quadrant Eight = new Quadrant(60, 15);
        Quadrant[] quadrantArray = new Quadrant[8];
        quadrantArray[0] = One;
        quadrantArray[1] = Two;
        quadrantArray[2] = Three;
        quadrantArray[3] = Four;
        quadrantArray[4] = Five;
        quadrantArray[5] = Six;
        quadrantArray[6] = Seven;
        quadrantArray[7] = Eight;

        for (int i = 0; i < quadrantArray.length; i++) {
            int randomIndexToSwap = generator.nextInt(quadrantArray.length);
            Quadrant temp = quadrantArray[randomIndexToSwap];
            quadrantArray[randomIndexToSwap] = quadrantArray[i];
            quadrantArray[i] = temp;
        }
        return quadrantArray;
    }

    public TETile[][] roomMaker(TETile[][] finalWorldFrame, Quadrant[] quadrantArray) {
        int rec1 = RandomUtils.uniform(generator, 0, 3);
        int rec2 = RandomUtils.uniform(generator, 4, 7);

        for (int i = 0; i < quadrantArray.length; i++) {
            if (i == rec1 || i == rec2) {
                roomHash.put(i, new Room(finalWorldFrame, generator, "rectangle",quadrantArray[i].getX(),quadrantArray[i].getY()));
            } else {
                int chooser = RandomUtils.uniform(generator, 0, 3);
                if(chooser==0 || chooser==1) { //makes 1 room in the quadrant
                    roomHash.put(i, new Room(finalWorldFrame, generator, quadrantArray[i].getX(),quadrantArray[i].getY()));
                } else if (chooser==2) { //makes no rooms in the quadrant
                    i --;
                } else { // makes 2 rooms in the quadrant
                    roomHash.put(i, new Room(finalWorldFrame, generator, "rectangle",quadrantArray[i].getX(),quadrantArray[i].getY()));
                    roomHash.put(i + 1, new Room(finalWorldFrame, generator, quadrantArray[i].getX(),quadrantArray[i].getY()));
                    i ++;
                }
            }
        }
        return finalWorldFrame;
    }





    public void Hallway(TETile[][] tiles, Random rand, Room room1, Room room2, int width) {
        int v; //0 for same x and same y of room, 1 for otherwise
        int allower; //chooses for loop, allower 3 for l shaped hallways
        Room start;
        Room end;
        if (room1.x == room2.x) {
            v = 0;
            start = room1;
            end = room2;
            allower = 1;
        } else {
            if(room1.y == room2.y) {
                v = 0;
                allower = 2;
            } else {
                v = 1;
                allower = 3;
            }

            if (room1.x < room2.x) {
                start = room1;
                end = room2;
            } else {
                start=room2;
                end=room1;
            }
        }

        if(width==1){
            if(allower==2 || allower==3){
                for(int x=start.x; x<=end.x+v; x++){
                if(x==end.x+v){}
                else{tiles[x][start.y] = Tileset.FLOOR;}

                if(tiles[x][start.y+1]==Tileset.FLOOR) {
                    //what do i put here :(
                }
                else{tiles[x][start.y+1]=Tileset.WALL;}

                if(tiles[x][start.y-1]==Tileset.FLOOR) {
                    //what do i put here :(
                }
                else{tiles[x][start.y-1]=Tileset.WALL;}
            }}

            if(allower==1 || allower==3){
                for(int y=start.y; y!=end.y;){ //y+1
                    tiles[end.x][y] = Tileset.FLOOR;
                    if(tiles[end.x+1][y]==Tileset.FLOOR) {
                        //what do i put here :(
                        } else {
                        tiles[end.x+1][y]=Tileset.WALL;
                    }
                    if(tiles[end.x-1][y]==Tileset.FLOOR) {
                        //what do i put here :(
                        } else {
                        tiles[end.x-1][y]=Tileset.WALL;
                    }
                    if (start.y<end.y) {
                        y++;
                    } else {
                        y--;
                    }
                }
            }
        }
        else {
            if(allower==2 || allower==3){
                for(int x=start.x; x<=end.x+v+v; x++){ //the +1 is fishy
                if( x==end.x+v+v){} //x==end.x+v
                else{tiles[x][start.y] = Tileset.FLOOR;
                tiles[x][start.y+1] = Tileset.FLOOR;}

                if(tiles[x][start.y+2]==Tileset.FLOOR) {
                    //what do i put here :(
                }
                else{tiles[x][start.y+2]=Tileset.WALL;}

                if(tiles[x][start.y-1]==Tileset.FLOOR) {
                    //what do i put here :(
                }
                else{tiles[x][start.y-1]=Tileset.WALL;}
            }
            }


            if(allower==1 || allower==3){
                for(int y=start.y+1; y!=end.y;){ //y+1

                tiles[end.x][y] = Tileset.FLOOR;
                tiles[end.x+1][y] = Tileset.FLOOR;


                if(tiles[end.x+2][y]==Tileset.FLOOR) {
                    //what do i put here :(
                }
                else{tiles[end.x+2][y]=Tileset.WALL;}

                if(tiles[end.x-1][y]==Tileset.FLOOR) {
                    //what do i put here :(
                }
                else{tiles[end.x-1][y]=Tileset.WALL;}

                if(start.y<end.y){y++;}
                else{y--;}
            }
            }
        }

    }



    }
    //hella 2 width seed 234278
    //phase 2 autogRader - 3c487f9c483a549289436c3d2a38aca6a902d637

