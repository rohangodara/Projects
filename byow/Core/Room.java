package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Random;
//import RandomUtils.java;

public class Room {
    public int x;
    public int y;

    public Room (TETile[][] tiles, Random rand, int quadx, int quady) {
        //for sq
        //Create random instance
        int heightbase = RandomUtils.uniform(rand,6,10);
        int Startx = RandomUtils.uniform(rand,quadx,quadx+20-heightbase);
        int Starty = RandomUtils.uniform(rand,quady,quady+15-heightbase);

        for(int x = Startx; x<=Startx+heightbase; x++ ){
            for(int y = Starty; y<=Starty+heightbase; y++ ){
                if(x==Startx || x==Startx+heightbase ){
                    //if(tiles[x][y] == Tileset.FLOOR) {continue;}
                    tiles[x][y] = Tileset.WALL;
                }
                else if (y==Starty || y==Starty+heightbase){
                    //if(tiles[x][y] == Tileset.FLOOR) {continue;}
                    tiles[x][y] = Tileset.WALL;
                }
                else{
                    tiles[x][y] = Tileset.FLOOR;
                }
            }
        }

        this.x=((heightbase+Startx)-Startx)/2 + Startx;
        this.y=((heightbase+Starty)-Starty)/2 + Starty;
    }

    public Room (TETile[][] tiles,Random rand, String rec, int quadx, int quady) {
        //for rec
        int height  = RandomUtils.uniform(rand,4,15);
        int length = RandomUtils.uniform(rand,2,20);
        int Startx = RandomUtils.uniform(rand, quadx,quadx+20-length);
        int Starty =  RandomUtils.uniform(rand,quady,quady+15-height);


        for(int x = Startx; x<=Startx+length; x++ ){
            for(int y = Starty; y<=Starty+height; y++ ){
                if(x==Startx || x==Startx+length ){
                    if(tiles[x][y] == Tileset.FLOOR) {continue;}
                    tiles[x][y] = Tileset.WALL;
                }
                else if (y==Starty || y==Starty+height){
                    if(tiles[x][y] == Tileset.FLOOR) {continue;}
                    tiles[x][y] = Tileset.WALL;
                }
                else{
                    tiles[x][y] = Tileset.FLOOR;
                }
            }
        }
        this.x=((length+Startx)-Startx)/2 + Startx;
        this.y=((height+Starty)-Starty)/2 + Starty;
    }


/*
    public Room (TETile[][] tiles, Random rand) {
        //for sq
        //Create random instance
        int heightbase = 5;
        int Startx = 70;
        int Starty = 5;

        for(int x = Startx; x<=Startx+heightbase; x++ ){
            for(int y = Starty; y<=Starty+heightbase; y++ ){
                if(x==Startx || x==Startx+heightbase ){
                    if(tiles[x][y] == Tileset.FLOOR) {continue;}
                    tiles[x][y] = Tileset.WALL;
                }
                else if (y==Starty || y==Starty+heightbase){
                    if(tiles[x][y] == Tileset.FLOOR) {continue;}
                    tiles[x][y] = Tileset.WALL;
                }
                else{
                    tiles[x][y] = Tileset.FLOOR;
                }
            }
        }

        this.x=((heightbase+Startx)-Startx)/2 + Startx;
        this.y=((heightbase+Starty)-Starty)/2 + Starty;
    }

    public Room (TETile[][] tiles, Random rand, String g) {
        //for sq
        //Create random instance
        int heightbase = 5;
        int Startx = 5;
        int Starty = 16;

        for(int x = Startx; x<=Startx+heightbase; x++ ){
            for(int y = Starty; y<=Starty+heightbase; y++ ){
                if(x==Startx || x==Startx+heightbase ){
                    if(tiles[x][y] == Tileset.FLOOR) {continue;}
                    tiles[x][y] = Tileset.WALL;
                }
                else if (y==Starty || y==Starty+heightbase){
                    if(tiles[x][y] == Tileset.FLOOR) {continue;}
                    tiles[x][y] = Tileset.WALL;
                }
                else{
                    tiles[x][y] = Tileset.FLOOR;
                }
            }
        }

        this.x=((heightbase+Startx)-Startx)/2 + Startx;
        this.y=((heightbase+Starty)-Starty)/2 + Starty;
    }*/








}
