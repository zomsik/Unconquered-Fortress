package com.game.Manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.game.Screens.GameScreen;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.List;

public class WorldManager {
    private Random random;
    static int[][] dirs = {{1, 0},{0, 1},{-1, 0},{0, -1}};

    static int[][] generateWater(int[][] arr, int randomI, int randomJ, int randomWaterSize, int randomAxis, int randomCorner, int randomDeep, int seed){
        Random random = new Random(seed);
        boolean isReturn = false;
        switch (randomCorner) {
            case 1 -> {
                if (randomAxis == 0) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 15; j++) {
                            if ((j >= randomJ && j < randomJ + randomWaterSize && i <= randomDeep)) {
                                arr[i][j] = 2;
                            }
                        }
                        randomDeep--;
                        if (randomDeep < 0) {
                            isReturn = true;
                        }
                        int checker = 0;
                        int newPosition = random.nextInt(randomJ - 1, randomJ + randomWaterSize + 2);
                        while (newPosition > 14) {
                            checker++;
                            newPosition = random.nextInt(randomJ - 1, randomJ + randomWaterSize + 2);
                            if(checker > 100){
                                newPosition = random.nextInt(0, 13);
                            }
                        }
                        randomJ = newPosition;
                        checker = 0;
                        int newWaterSize = random.nextInt(randomWaterSize - 1, randomWaterSize + 2);
                        while (newWaterSize > randomWaterSize || newWaterSize < 0) {
                            checker++;
                            newWaterSize = random.nextInt(randomWaterSize - 1, randomWaterSize + 2);
                            if(checker > 100){
                                newWaterSize = 0;
                            }
                        }
                        randomWaterSize = newWaterSize;
                    }
                } else {
                    for (int j = 0; j < 15; j++) {
                        for (int i = 0; i < 10; i++) {
                            if ((i >= randomI && i < randomI + randomWaterSize && j < randomDeep)) {
                                arr[i][j] = 2;
                            }
                        }

                        randomDeep--;
                        if (randomDeep <= 0) {
                            isReturn = true;
                        }
                        int checker = 0;
                        int newPosition = random.nextInt(randomI - 1, randomI + randomWaterSize + 2);
                        while (newPosition > 9) {
                            checker++;
                            newPosition = random.nextInt(randomI - 1, randomI + randomWaterSize + 2);
                            if(checker > 100){
                                newPosition = random.nextInt(0, 9);
                            }
                        }
                        randomI = newPosition;
                        checker = 0;
                        int newWaterSize = random.nextInt(randomWaterSize - 1, randomWaterSize + 2);
                        while (newWaterSize > randomWaterSize || newWaterSize < 0) {
                            checker++;
                            newWaterSize = random.nextInt(randomWaterSize - 1, randomWaterSize + 2);
                            if(checker > 100){
                                newWaterSize = 0;
                            }
                        }
                        randomWaterSize = newWaterSize;
                    }
                }
            }
            case 2 -> {
                if (randomAxis == 0) {
                    for (int i = 9; i >=0; i--) {
                        for (int j = 0; j < 15; j++) {
                            if ((j >= randomJ && j < randomJ + randomWaterSize && i > randomDeep)) {
                                arr[i][j] = 2;
                            }
                        }
                        if (i == 0) {
                            isReturn = true;
                        }
                        int checker = 0;
                        int newPosition = random.nextInt(randomJ - 1, randomJ + randomWaterSize + 2);
                        while (newPosition > 14) {
                            checker++;
                            newPosition = random.nextInt(randomJ - 1, randomJ + randomWaterSize + 2);
                            if(checker > 100){
                                newPosition = random.nextInt(0, 13);
                            }
                        }
                        randomJ = newPosition;
                        checker = 0;
                        int newWaterSize = random.nextInt(randomWaterSize - 1, randomWaterSize + 2);
                        while (newWaterSize > randomWaterSize || newWaterSize < 0) {
                            checker++;
                            newWaterSize = random.nextInt(randomWaterSize - 1, randomWaterSize + 2);
                            if(checker > 100){
                                newWaterSize = 0;
                            }
                        }
                        randomWaterSize = newWaterSize;
                    }
                } else {
                    for (int j = 14; j >= 0; j--) {
                        for (int i = 0; i < 10; i++) {
                            if ((i >= randomI && i < randomI + randomWaterSize && j > randomDeep)) {
                                arr[i][j] = 2;
                            }
                        }
                        if (j == 0) {
                            isReturn = true;
                        }
                        int checker = 0;
                        int newPosition = random.nextInt(randomI - 1, randomI + randomWaterSize + 2);
                        while (newPosition > 9) {
                            checker++;
                            newPosition = random.nextInt(randomI - 1, randomI + randomWaterSize + 2);
                            if(checker > 100){
                                newPosition = random.nextInt(0,9);
                            }
                        }
                        randomI = newPosition;
                        checker = 0;
                        int newWaterSize = random.nextInt(randomWaterSize - 1, randomWaterSize + 2);
                        while (newWaterSize > randomWaterSize || newWaterSize < 0) {
                            checker++;
                            newWaterSize = random.nextInt(randomWaterSize - 1, randomWaterSize + 2);
                            if(checker > 100){
                                newWaterSize = 0;
                            }
                        }
                        randomWaterSize = newWaterSize;
                    }
                }
            }
        }
        if(isReturn){
            return arr;
        }else{
            return generateWater(arr, randomI, randomJ, randomWaterSize, randomAxis, randomCorner, randomDeep, seed);
        }
    }
    static List<int[]> ramdomWalk(int[][] arr, int[] start, int[] end, int seed, int difficulty) {
        shuffleDirs(seed);
        boolean[][] visited = new boolean[arr.length][arr[0].length];
        List<int[]> path = new ArrayList<>();
        dfs(arr, visited, start, end, path, seed, difficulty);

        return path;
    }
    static void shuffleDirs(int seed) {
        Random random = new Random(seed);
        for(int i=0;i<dirs.length;i++) {
            int j = random.nextInt(i+1);
            int[] t = dirs[i];
            dirs[i] = dirs[j];
            dirs[j] = t;
        }
    }
    static boolean dfs(int[][] grid, boolean[][] visited, int[] cur, int[] end, List<int[]> res, int seed, int difficulty) {

            if (cur[0] == end[0] && cur[1] == end[1]) {
                res.add(new int[]{cur[0], cur[1]});
                return true;
            }
            visited[cur[0]][cur[1]] = true;
            res.add(new int[]{cur[0], cur[1]});
            shuffleDirs(seed);
            for (int[] dir : dirs) {
                int ni = cur[0] + dir[0];
                int nj = cur[1] + dir[1];
                if (ni >= 0 && ni < grid.length && nj >= 0 && nj < grid[0].length && !visited[ni][nj] && res.size() < difficulty)
                    if (dfs(grid, visited, new int[]{ni, nj}, end, res, seed, difficulty))
                        return true;
            }
            visited[cur[0]][cur[1]] = false;
            res.remove(res.size() - 1);
            return false;

    }

    static int[][] generatePath(int[][] arr, List<int[]> res){
        for(int i=0; i<10; i++){
            for(int j=0; j<15; j++){
                for(int k=0; k<res.size(); k++){
                    if(i == res.get(k)[0] && j == res.get(k)[1]){
                        arr[i][j] = 1;
                    }
                }
            }
        }
        return arr;
    }

    static int[][] generateObstacles(int[][] arr, int seed, int difficulty){
        Random random = new Random(seed);
        switch (difficulty){
            case 51:{
                for(int i=0; i<10; i++){
                    for(int j=0; j<15; j++){
                        int randomValue = random.nextInt(0,100);
                        if(randomValue> 6 && randomValue < 17 && arr[i][j] == 0){
                            arr[i][j] = 5; //B
                        }if(randomValue < 6 && arr[i][j]==0){
                            arr[i][j] = 6; //M
                        }
                    }
                }
            }break;
            case 46:{
                for(int i=0; i<10; i++){
                    for(int j=0; j<15; j++){
                        int randomValue = random.nextInt(0,100);
                        if(randomValue > 12 && randomValue < 25 && arr[i][j] == 0){
                            arr[i][j] = 5; //B
                        }if(randomValue < 12 && arr[i][j]==0){
                            arr[i][j] = 6; //M
                        }
                    }
                }
            }break;
            case 36:{
                for(int i=0; i<10; i++){
                    for(int j=0; j<15; j++){
                        int randomValue = random.nextInt(0,100);
                        if(randomValue > 21 && randomValue < 38 && arr[i][j] == 0){
                            arr[i][j] = 5; //B
                        }if(randomValue < 21 && arr[i][j]==0){
                            arr[i][j] = 6; //M
                        }
                    }
                }
            }break;
        }
        return arr;
    }

    static int[][] overwritePath(int[][] arr, List<int[]> res){
        for(int i=0; i<res.size(); i++){
                if(i==res.size()-1){
                if((res.get(i-1)[0] < res.get(i)[0] && res.get(i-1)[1] == res.get(i)[1]) && (res.get(i-1)[0] == res.get(i-2)[0] && res.get(i-1)[1] > res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 16;
                }else if((res.get(i-1)[0] == res.get(i)[0] && res.get(i-1)[1] > res.get(i)[1]) && (res.get(i-1)[0] < res.get(i-2)[0] && res.get(i-1)[1] == res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 16;
                }
                else if((res.get(i-1)[0] > res.get(i)[0] && res.get(i-1)[1] == res.get(i)[1]) && (res.get(i-1)[0] == res.get(i-2)[0] && res.get(i-1)[1] > res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 14;
                }else if((res.get(i-1)[0] == res.get(i)[0] && res.get(i-1)[1] > res.get(i)[1]) && (res.get(i-1)[0] > res.get(i-2)[0] && res.get(i-1)[1] == res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 14;
                }
                else if((res.get(i-1)[0] < res.get(i)[0] && res.get(i-1)[1] == res.get(i)[1]) && (res.get(i-1)[0] == res.get(i-2)[0] && res.get(i-1)[1] < res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 15;
                }else if((res.get(i-1)[0] == res.get(i)[0] && res.get(i-1)[1] < res.get(i)[1]) && (res.get(i-1)[0] < res.get(i-2)[0] && res.get(i-1)[1] == res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 15;
                }
                else if((res.get(i-1)[0] > res.get(i)[0] && res.get(i-1)[1] == res.get(i)[1]) && (res.get(i-1)[0] == res.get(i-2)[0] && res.get(i-1)[1] < res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 13;
                }else if((res.get(i-1)[0] == res.get(i)[0] && res.get(i-1)[1] < res.get(i)[1]) && (res.get(i-1)[0] > res.get(i-2)[0] && res.get(i-1)[1] == res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 13;
                }
                else if((res.get(i-1)[0] > res.get(i)[0] && res.get(i-1)[1] == res.get(i)[1]) && (res.get(i-1)[0] < res.get(i-2)[0] && res.get(i-1)[1] == res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 12;
                }else if((res.get(i-1)[0] < res.get(i)[0] && res.get(i-1)[1] == res.get(i)[1]) && (res.get(i-1)[0] > res.get(i-2)[0] && res.get(i-1)[1] == res.get(i-2)[1])){ ////
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 12;
                }
                else if((res.get(i-1)[0] == res.get(i)[0] && res.get(i-1)[1] > res.get(i)[1]) && (res.get(i-1)[0] == res.get(i-2)[0] && res.get(i-1)[1] < res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 11;
                }else if((res.get(i-1)[0] == res.get(i)[0] && res.get(i-1)[1] < res.get(i)[1]) && (res.get(i-1)[0] == res.get(i-2)[0] && res.get(i-1)[1] > res.get(i-2)[1])){
                    arr[res.get(i-1)[0]][res.get(i-1)[1]] = 11;
                }
            }else if(i<res.size()-2){
                if((res.get(i+1)[0] < res.get(i)[0] && res.get(i+1)[1] == res.get(i)[1]) && (res.get(i+1)[0] > res.get(i+2)[0] && res.get(i+1)[1] == res.get(i+2)[1])){
                    arr[res.get(i+1)[0]][res.get(i+1)[1]] = 12;
                }else if((res.get(i+1)[0] > res.get(i)[0] && res.get(i+1)[1] == res.get(i)[1]) && (res.get(i+1)[0] < res.get(i+2)[0] && res.get(i+1)[1] == res.get(i+2)[1])){
                    arr[res.get(i+1)[0]][res.get(i+1)[1]] = 12;
                }
                else if((res.get(i+1)[0] == res.get(i)[0] && res.get(i+1)[1] < res.get(i)[1]) && (res.get(i+1)[0] == res.get(i+2)[0] && res.get(i+1)[1] > res.get(i+2)[1])){
                    arr[res.get(i+1)[0]][res.get(i+1)[1]] = 11;
                }else if((res.get(i+1)[0] == res.get(i)[0] && res.get(i+1)[1] > res.get(i)[1]) && (res.get(i+1)[0] == res.get(i+2)[0] && res.get(i+1)[1] < res.get(i+2)[1])){
                    arr[res.get(i+1)[0]][res.get(i+1)[1]] = 11;
                }
                else if((res.get(i+1)[0] == res.get(i)[0] && res.get(i+1)[1] > res.get(i)[1]) && (res.get(i+1)[0] < res.get(i+2)[0] && res.get(i+1)[1] == res.get(i+2)[1])){
                    arr[res.get(i+1)[0]][res.get(i+1)[1]] = 16;
                } else if((res.get(i+1)[0] < res.get(i)[0] && res.get(i+1)[1] == res.get(i)[1]) && (res.get(i+1)[0] == res.get(i+2)[0] && res.get(i+1)[1] > res.get(i+2)[1])){
                    arr[res.get(i+1)[0]][res.get(i+1)[1]] = 16;
                }
                else if((res.get(i+1)[0] == res.get(i)[0] && res.get(i+1)[1] < res.get(i)[1]) && (res.get(i+1)[0] < res.get(i+2)[0] && res.get(i+1)[1] == res.get(i+2)[1])){
                    arr[res.get(i+1)[0]][res.get(i+1)[1]] = 15;
                }else if((res.get(i+1)[0] < res.get(i)[0] && res.get(i+1)[1] == res.get(i)[1]) && (res.get(i+1)[0] == res.get(i+2)[0] && res.get(i+1)[1] < res.get(i+2)[1])) {
                    arr[res.get(i + 1)[0]][res.get(i + 1)[1]] = 15;
                }
                else if((res.get(i+1)[0] == res.get(i)[0] && res.get(i+1)[1] > res.get(i)[1]) && (res.get(i+1)[0] > res.get(i+2)[0] && res.get(i+1)[1] == res.get(i+2)[1])) {
                    arr[res.get(i + 1)[0]][res.get(i + 1)[1]] = 14;
                }else if((res.get(i+1)[0] > res.get(i)[0] && res.get(i+1)[1] == res.get(i)[1]) && (res.get(i+1)[0] == res.get(i+2)[0] && res.get(i+1)[1] > res.get(i+2)[1])) {
                    arr[res.get(i + 1)[0]][res.get(i + 1)[1]] = 14;
                }
                else if((res.get(i+1)[0] > res.get(i)[0] && res.get(i+1)[1] == res.get(i)[1]) && (res.get(i+1)[0] == res.get(i+2)[0] && res.get(i+1)[1] < res.get(i+2)[1])) {
                    arr[res.get(i + 1)[0]][res.get(i + 1)[1]] = 13;
                }else if((res.get(i+1)[0] == res.get(i)[0] && res.get(i+1)[1] < res.get(i)[1]) && (res.get(i+1)[0] > res.get(i+2)[0] && res.get(i+1)[1] == res.get(i+2)[1])) {
                    arr[res.get(i + 1)[0]][res.get(i + 1)[1]] = 13;
                }
            }
        }
        return arr;
    }

    public static Image[][] createWorld(GameScreen gameScreen, int seed, int difficulty){
        //wygenerowanie seeda
        //final ThreadLocal<Random> RANDOM_THREAD_LOCAL = ThreadLocal.withInitial(Random::new);
        Random random = new Random(seed);
        //random.setSeed(seed);
        //System.out.println("Seeded Thread Local Random Integer: " + random.nextInt(0, 100));

        //random losuje od min do max + 1
        //Utworzenie tablicy dla terenu
        int[][] arr = new int[10][15];
        //Wypełnienie tablicy trawą
        for(int i=0; i<10; i++){
            for(int j=0; j<15; j++){
                arr[i][j] = 0;
            }
        }

        //wylosowanie punktu z obrzeży tablicy, przypisanie wartości początkowej
        int randomJ;
        int randomI;
        int randomIE;
        int randomJE;
        int[] start;
        int[] end;
        //od 1 do 2
        int randomCorner = random.nextInt(1, 3);

        //Wylosowanie osi po której będzie tworzona woda
        //od 0 do 1
        int randomAxis = random.nextInt(0, 2);

        //Wylosowanie wielkości wody dla pierwszego wiersza/kolumny
        int randomWaterSize = 0;
        if(randomAxis == 1){
            //od 1 do 10
            randomWaterSize = random.nextInt(1, 10);
        }else{
            //od 1 do 7
            randomWaterSize = random.nextInt(1, 8);
        }

        //Wylosowanie głębi wody
        int randomDeep;
        if(randomAxis == 0){
            randomDeep = random.nextInt(3, 6);
        }else{
            randomDeep = random.nextInt(5, 8);
        }
        System.out.println("corner: " + randomCorner);
        System.out.println("axis: " + randomAxis);
        System.out.println("deep: " + randomDeep);
        System.out.println("water size: " + randomWaterSize);
        //Wypełnienie tablicy
        //Axis == 0 poziomo | Axis == 1 pionowo
        switch (randomCorner) {
            case 1 -> {
                //baza z lewej lub u dołu
                //0,0
                if (randomAxis == 0) {
                    randomI = 0;
                    randomJ = random.nextInt(0, 15-randomWaterSize);
                    System.out.println("1");
                    /*while (randomJ + randomWaterSize > 14) {
                        //random.setSeed(randomJ);
                        randomJ = random.nextInt(1, 14);
                    }*/
                    System.out.println("2");
                    generateWater(arr, randomI, randomJ, randomWaterSize, randomAxis, randomCorner, randomDeep, seed);
                    System.out.println("3");
                    randomI = random.nextInt((randomDeep + 2), 10);
                    if (randomI < 9) {
                        System.out.println("4.1");
                        randomJ = random.nextInt(0, 2);
                        if (randomJ == 1) {
                            randomJ = 14;
                        }
                        System.out.println("5.1");
                        //wygenerowanie bazy
                        start = new int[]{randomI, randomJ};
                        //generowanie bazy przeciwnika
                        randomIE = random.nextInt(randomDeep + 1, 10);
                        //po przeciwnej stronie
                        if (randomJ == 0) {
                            randomJE = 14;
                        } else {
                            randomJE = 0;
                        }
                        System.out.println("6.1");
                        end = new int[]{randomIE, randomJE};
                    } else {
                        System.out.println("4.2");
                        randomJ = random.nextInt(0, 14);
                        //wygenerowanie bazy i=0; j od 0-13
                        start = new int[]{randomI, randomJ};
                        randomIE = 0;
                        randomJE = random.nextInt(0, 14);
                        end = new int[]{randomIE, randomJE};
                        System.out.println("5.2");
                    }
                    System.out.println("6");
                    List<int[]> res = ramdomWalk(arr, start, end, seed, difficulty);
                    System.out.println("7");
                    while (res.size() == 0) {
                        res = ramdomWalk(arr, start, end, seed, difficulty);
                    }
                    System.out.println("8");
                    for (int j = 0; j < res.size(); j++) {
                        System.out.print(Arrays.toString(res.get(j)) + (j == res.size() - 1 ? "" : "->"));
                    }
                    System.out.println("9");
                    System.out.println();
                    System.out.println("-----------------------------------------");
                    System.out.println("Path length: " + res.size());
                    generatePath(arr, res);
                    System.out.println("10");
                    arr[randomI][randomJ] = 9;
                    arr[randomIE][randomJE] = 8;
                    generateObstacles(arr, seed, difficulty);
                    System.out.println("11");
                    overwritePath(arr, res);
                    System.out.println("12");
                } else {
                    randomI = random.nextInt(0, 10-randomWaterSize);
                    randomJ = 0;
                    System.out.println("1");
                    /*while (randomWaterSize + randomI > 9) {
                        //random.setSeed(randomI); //TODO dodać randomI do seeda i w reszcie też ale to jak będzie seed
                        randomI = random.nextInt(1, 9);
                    }*/
                    System.out.println("2");
                    generateWater(arr, randomI, randomJ, randomWaterSize, randomAxis, randomCorner, randomDeep, seed);
                    System.out.println("3");
                    randomJ = random.nextInt((randomDeep + 2), 15);
                    if (randomJ < 14) {
                        System.out.println("4.1");
                        randomI = random.nextInt(0, 2);
                        if (randomI == 1) {
                            randomI = 9;
                        }
                        System.out.println("5.1");
                        start = new int[]{randomI, randomJ};
                        //generowanie bazy przeciwnika
                        randomJE = random.nextInt(randomDeep + 1, 15);
                        if (randomI == 0) {
                            randomIE = 9;
                        } else {
                            randomIE = 0;
                        }
                        System.out.println("6.1");
                        end = new int[]{randomIE, randomJE};
                    } else {
                        System.out.println("4.2");
                        //wygenerowanie bazy
                        randomI = random.nextInt(0, 10);
                        start = new int[]{randomI, randomJ};
                        randomJE = 0;
                        randomIE = random.nextInt(0, 10);
                        end = new int[]{randomIE, randomJE};
                        System.out.println("5.2");
                    }
                    System.out.println("6");
                    List<int[]> res = ramdomWalk(arr, start, end, seed, difficulty);
                    System.out.println("7");
                    while (res.size() == 0) {
                        res = ramdomWalk(arr, start, end, seed, difficulty);
                    }
                    System.out.println("8");
                    for (int j = 0; j < res.size(); j++) {
                        System.out.print(Arrays.toString(res.get(j)) + (j == res.size() - 1 ? "" : "->"));
                    }
                    System.out.println("9");
                    System.out.println();
                    System.out.println("-----------------------------------------");
                    System.out.println("Path length: " + res.size());
                    generatePath(arr, res);
                    System.out.println("10");
                    arr[randomI][randomJ] = 9;
                    arr[randomIE][randomJE] = 8;
                    generateObstacles(arr, seed, difficulty);
                    System.out.println("11");
                    overwritePath(arr, res);
                    System.out.println("12");
                }
            }
            case 2 -> {
                //10,15
                if (randomAxis == 0) {
                    randomJ = random.nextInt(0, 15-randomWaterSize);
                    randomI = 9;
                    System.out.println("1");
                    /*while (randomWaterSize + randomJ > 14) {
                        randomJ = random.nextInt(1, 14);
                    }*/
                    System.out.println("2");
                    generateWater(arr, randomI, randomJ, randomWaterSize, randomAxis, randomCorner, randomDeep, seed);
                    System.out.println("3");
                    randomI = random.nextInt(0, 10 - (randomDeep + 1));
                    if (randomI > 0) {
                        System.out.println("4.1");
                        randomJ = random.nextInt(0, 2);
                        if (randomJ == 1) {
                            randomJ = 14;
                        }
                        System.out.println("5.1");
                        //wygenerowanie bazy
                        start = new int[]{randomI, randomJ};
                        randomIE = random.nextInt(0, 10 - (randomDeep + 1));
                        if (randomJ == 0) {
                            randomJE = 14;
                        } else {
                            randomJE = 0;
                        }
                        System.out.println("6.1");
                        end = new int[]{randomIE, randomJE};


                    } else {
                        System.out.println("4.2");
                        randomJ = random.nextInt(0, 14);
                        //wygenerowanie bazy
                        start = new int[]{randomI, randomJ};
                        randomIE = 9;
                        randomJE = random.nextInt(0, 14);
                        end = new int[]{randomIE, randomJE};
                        System.out.println("5.2");

                    }
                    System.out.println("6");
                    List<int[]> res = ramdomWalk(arr, start, end, seed, difficulty);
                    System.out.println("7");
                    while (res.size() == 0) {
                        res = ramdomWalk(arr, start, end, seed, difficulty);
                    }
                    System.out.println("8");
                    for (int j = 0; j < res.size(); j++) {
                        System.out.print(Arrays.toString(res.get(j)) + (j == res.size() - 1 ? "" : "->"));
                    }
                    System.out.println("9");
                    System.out.println();
                    System.out.println("-----------------------------------------");
                    System.out.println("Path length: " + res.size());
                    generatePath(arr, res);
                    System.out.println("10");
                    arr[randomI][randomJ] = 9;
                    arr[randomIE][randomJE] = 8;
                    generateObstacles(arr, seed, difficulty);
                    System.out.println("11");
                    overwritePath(arr, res);
                    System.out.println("12");
                } else {
                    randomI = random.nextInt(0, 10-randomWaterSize);
                    randomJ = 14;
                    System.out.println("1");
                    /*while (randomWaterSize + randomI > 9) {
                        //random.setSeed(randomI);
                        randomI = random.nextInt(1, 9);
                    }*/
                    System.out.println("2");
                    generateWater(arr, randomI, randomJ, randomWaterSize, randomAxis, randomCorner, randomDeep, seed);
                    System.out.println("3");
                    randomJ = random.nextInt(0, 15 - (randomDeep + 1));
                    if (randomJ > 0) {
                        System.out.println("4.1");
                        randomI = random.nextInt(0, 2);
                        if (randomI == 1) {
                            randomI = 9;
                        }
                        System.out.println("5.1");
                        start = new int[]{randomI, randomJ};
                        randomJE = random.nextInt(0, 15 - (randomDeep + 1));
                        if (randomI == 0) {
                            randomIE = 9;
                        } else {
                            randomIE = 0;
                        }
                        System.out.println("6.1");
                        end = new int[]{randomIE, randomJE};

                    } else {
                        System.out.println("4.2");
                        randomI = random.nextInt(1, 10);
                        start = new int[]{randomI, randomJ};
                        randomJE = 0;
                        randomIE = random.nextInt(0, 10);
                        end = new int[]{randomIE, randomJE};
                        System.out.println("5.2");
                    }
                    System.out.println("6");
                    List<int[]> res = ramdomWalk(arr, start, end, seed, difficulty);
                    System.out.println("7");
                    while (res.size() == 0) {
                        res = ramdomWalk(arr, start, end, seed, difficulty);
                    }
                    System.out.println("8");
                    for (int j = 0; j < res.size(); j++) {
                        System.out.print(Arrays.toString(res.get(j)) + (j == res.size() - 1 ? "" : "->"));
                    }
                    System.out.println("9");
                    System.out.println();
                    System.out.println("-----------------------------------------");
                    System.out.println("Path length: " + res.size());
                    generatePath(arr, res);
                    System.out.println("10");
                    arr[randomI][randomJ] = 9;
                    arr[randomIE][randomJE] = 8;
                    generateObstacles(arr, seed, difficulty);
                    System.out.println("11");
                    overwritePath(arr, res);
                    System.out.println("12");
                }
            }
        }
        //wygenerowanie bazy przeciwników

        //sout
        System.out.println("randomCorner " + randomCorner);
        System.out.println("randomAxis " + randomAxis);
        System.out.println("randomWaterSize " + randomWaterSize);
        System.out.println("randomDeep " + randomDeep);

        for (int[] x : arr)
        {
            for (int y : x)
            {
                if (y<10)
                    System.out.print(y + "  ");
                else
                    System.out.print(y + " ");
            }
            System.out.println();
        }

        Skin images_map = new Skin(new TextureAtlas("assets/icons/map_sprites.pack"));
        Table t = new Table();
        t.setBounds(500, 200, 64*15, 64*10);



        Image[][] imageArr = new Image[10][15];
        int i = 0;
        for (int[] x : arr)
        {
            int j = 0;
            for (int y : x)
            {
                if (y==0) {
                    imageArr[i][j] = new Image(images_map, "grass");
                    imageArr[i][j].setName("grass");
                }
                else if (y==2) {
                    imageArr[i][j] = new Image(images_map, "water");
                    imageArr[i][j].setName("water");
                }
                else if (y==5) {
                    imageArr[i][j] = new Image(images_map, "obstacle");
                    imageArr[i][j].setName("obstacle");
                }
                else if (y==6) {
                    imageArr[i][j] = new Image(images_map, "mountain");
                    imageArr[i][j].setName("mountain");
                }
                else if (y==8){
                    imageArr[i][j] = new Image(images_map, "enemy");
                    imageArr[i][j].setName("enemy");
                }
                else if (y==9){
                    imageArr[i][j] = new Image(images_map, "base");
                    imageArr[i][j].setName("base");
                }
                else if (y==11)
                    imageArr[i][j] = new Image(images_map, "pathLeftRight");
                else if (y==12)
                    imageArr[i][j] = new Image(images_map, "pathUpDown");
                else if (y==13)
                    imageArr[i][j] = new Image(images_map, "pathUpRight");
                else if (y==14)
                    imageArr[i][j] = new Image(images_map, "pathLeftUp");
                else if (y==15)
                    imageArr[i][j] = new Image(images_map, "pathDownRight");
                else if (y==16)
                    imageArr[i][j] = new Image(images_map, "pathLeftDown");

                imageArr[i][j].addListener(new ImageClickListener(j,i,imageArr[i][j].getName()){
                    public void clicked(InputEvent event, float x, float y) {
                        this.setLastClickedTile(gameScreen.lastClickedMapTile);
                        gameScreen.mouseClickMapTile();
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        this.setLastClickedTile(gameScreen.lastClickedMapTile);
                        gameScreen.mouseEnterMapTile();

                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        //this.setLastClickedTile();
                        gameScreen.mouseExitMapTile();
                    }



                });


                j++;
            }
            i++;

        }


        return imageArr;
    }


    public static Image[][] loadTerrainModifications (GameScreen gameScreen, Image[][] mapArr, JSONArray terrArr)
    {
        Skin images_map = new Skin(new TextureAtlas("assets/icons/map_sprites.pack"));
        System.out.println(terrArr.length());
        for (int i = 0; i< terrArr.length(); i++) {
            JSONObject j = terrArr.getJSONObject(i);

            mapArr[j.getInt("y")][j.getInt("x")].setDrawable(images_map, j.getString("tileName"));
            mapArr[j.getInt("y")][j.getInt("x")].setName(j.getString("tileName"));
            mapArr[j.getInt("y")][j.getInt("x")].clearListeners();


            mapArr[j.getInt("y")][j.getInt("x")].addListener(new ImageClickListener(j.getInt("x"), j.getInt("y"),  mapArr[j.getInt("y")][j.getInt("x")].getName()) {
                public void clicked(InputEvent event, float x, float y) {
                    this.setLastClickedTile(gameScreen.lastClickedMapTile);
                    gameScreen.mouseClickMapTile();
                }

                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    this.setLastClickedTile(gameScreen.lastClickedMapTile);
                    gameScreen.mouseEnterMapTile();

                }

                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    //this.setLastClickedTile();
                    gameScreen.mouseExitMapTile();
                }

            });

        }


        return mapArr;
    }


    public static Table changeTileAndRedrawWorld (GameScreen gameScreen, Image[][] mapArr, int x, int y, String tileName)
    {
        Skin images_map = new Skin(new TextureAtlas("assets/icons/map_sprites.pack"));
        mapArr[y][x].setDrawable(images_map, tileName);
        mapArr[y][x].setName(tileName);
        mapArr[y][x].clearListeners();

        mapArr[y][x].addListener(new ImageClickListener(x,y,mapArr[y][x].getName()){
            public void clicked(InputEvent event, float x, float y) {
                this.setLastClickedTile(gameScreen.lastClickedMapTile);
                gameScreen.mouseClickMapTile();
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                this.setLastClickedTile(gameScreen.lastClickedMapTile);
                gameScreen.mouseEnterMapTile();

            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //this.setLastClickedTile();
                gameScreen.mouseExitMapTile();
            }

        });


        return drawWorld(mapArr);
    }


    public static Table drawWorld(Image[][] imageArr){

        //Skin images_map = new Skin(new TextureAtlas("assets/icons/map_sprites.pack"));
        Table t = new Table();
        t.setBounds(500, 200, 64*15, 64*10);

        for (int i = 0; i<10; i++)
        {
            for (int j = 0; j<15; j++)
            {
                t.add(imageArr[i][j]);
            }

            t.row();

        }


        return t;
    }



}