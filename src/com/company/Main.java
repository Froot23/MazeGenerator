package com.company;

import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        int x = 0;
        Cell[][] maze = new Cell[20][40];
        while(x == 0)
        {
         maze = new Cell[20][40];
        for(int i =0; i< maze.length; i++)
            for(int j =0; j < maze[i].length;j++)
                maze[i][j] = new Cell();
        x = makeMaze(maze,0,0);
        System.out.println(x);
        //x=1;
        }
        finishMaze(maze);
        emptyFix(maze);
        //printMaze(maze);
        export(maze);
       printMaze(solveMaze(importMaze("maze.txt")));
       printMaze(importMaze("maze.txt"));

    //TODO use visited status for color when using solving method



    }

    public static int makeMaze(Cell[][] maze, int row, int col)
    {
        if(row == maze.length-1 && col == maze[0].length-1)
            return 1;
        //if((row !=0 && maze[row-1][col].getStatus()) && (col !=0 && maze[row][col-1].getStatus()) && (row != maze.length-1 && maze[row+1][col].getStatus()) && (col != maze[0].length-1 && maze[row][col+1].getStatus()))
            //return 0;
        else
        {
            String[] dir = {"north", "south" , "east", "west"};
            maze[row][col].setStatus(true);
            boolean decided = false;
            int exhausted = 0;
            while(!decided) {
                int rng = (int) (Math.random() * (4-exhausted));
                if(exhausted == 4)
                    return 0;
                else if (dir[rng].equals("north"))
                    if (row != 0 && !maze[row - 1][col].getStatus())
                    {
                        maze[row][col].setNorth(true);
                        maze[row-1][col].setSouth(true);
                        row = row-1;
                        decided = true;
                    }
                    else
                    {
                        dir[rng] = dir[4-exhausted-1];
                        dir[4-exhausted-1] = null;
                        exhausted++;
                    }
                else if (dir[rng].equals("south"))
                    if (row != maze.length-1 && !maze[row+1][col].getStatus())
                    {
                        maze[row][col].setSouth(true);
                        maze[row+1][col].setNorth(true);
                        row = row +1;
                        decided = true;
                    }
                    else
                    {
                        dir[rng] = dir[4-exhausted-1];
                        dir[4-exhausted-1] = null;
                        exhausted++;
                    }
                else if (dir[rng].equals("east"))
                    if (col != maze[0].length-1 && !maze[row][col+1].getStatus())
                    {
                        maze[row][col].setEast(true);
                        maze[row][col+1].setWest(true);
                        col = col+1;
                        decided = true;
                    }
                    else
                    {
                        dir[rng] = dir[4-exhausted-1];
                        dir[4-exhausted-1] = null;
                        exhausted++;
                    }
                else if (dir[rng].equals("west"))
                    if (col !=0 && !maze[row][col-1].getStatus()) {
                        maze[row][col].setWest(true);
                        maze[row][col - 1].setEast(true);
                        col = col-1;
                        decided = true;
                    }
                    else
                    {
                        dir[rng] = dir[4-exhausted-1];
                        dir[4-exhausted-1] = null;
                        exhausted++;
                    }
            }
            return makeMaze(maze, row, col);
        }
    }

    public static void printMaze(Cell[][] maze)
    {
        new mazeWindow(maze);
    }

    public static void printMaze(String maze) {printMaze(convertToMaze(maze));}

    private static Cell[][] convertToMaze(String maze)
    {
        int i = 0;
        String temp = "";
        while(maze.charAt(i) != ';') {
            temp += maze.charAt(i);
            i++;
        }
        i++;
        String temp2 = "";
        while(maze.charAt(i) != ':')
        {
            temp2 += maze.charAt(i);
            i++;
        }
        i++;
        Cell[][] maize = new Cell[Integer.parseInt(temp)][Integer.parseInt(temp2)];
        for(int j = 0; j < maize.length; j++)
        {
            for(int k = 0; k < maize[j].length;k++)
            {
                int cell = maze.charAt(i) - 65;
                Cell tile = new Cell();
                if((cell&1) == 1)
                    tile.setNorth(true);
                if((cell & 2) == 2)
                    tile.setEast(true);
                if((cell&4) == 4)
                    tile.setSouth(true);
                if((cell&8) == 8)
                    tile.setWest(true);
                maize[j][k] = tile;
                i++;
            }
        }
        return maize;
    }

    public static void finishMaze(Cell[][] maze)
    {
        ArrayList<int[]> tiles = new ArrayList<int[]>();
        for(int i = 0; i < maze.length; i++)
            for(int j = 0; j < maze[0].length; j++)
                if(maze[i][j].getStatus())
                    tiles.add(new int[]{i,j});
        int run = (int) (Math.random() * 7) + 3;
        while(run >= 0)
        {
            int rng = (int) (Math.random() * tiles.size());
            int dis = (int) (Math.random() * 20) +1;
            helpFinish(maze, tiles.get(rng)[0],tiles.get(rng)[1], dis , 0);
            run--;
        }
    }

    public static int helpFinish(Cell[][] maze, int row, int col, int ran, int go) {
        if (go == ran)
            return 1;
        else {
            String[] dir = {"north", "south", "east", "west"};
            boolean decided = false;
            int exhausted = 0;
            while (!decided) {
                int rng = (int) (Math.random() * (4 - exhausted));
                if (exhausted == 4)
                    return 0;
                else if (dir[rng].equals("north"))
                    if (row != 0 && !maze[row - 1][col].getStatus()) {
                        maze[row][col].setNorth(true);
                        maze[row - 1][col].setSouth(true);
                        row = row - 1;
                        decided = true;
                    } else {
                        dir[rng] = dir[4 - exhausted - 1];
                        dir[4 - exhausted - 1] = null;
                        exhausted++;
                    }
                else if (dir[rng].equals("south"))
                    if (row != maze.length - 1 && !maze[row + 1][col].getStatus()) {
                        maze[row][col].setSouth(true);
                        maze[row + 1][col].setNorth(true);
                        row = row + 1;
                        decided = true;
                    } else {
                        dir[rng] = dir[4 - exhausted - 1];
                        dir[4 - exhausted - 1] = null;
                        exhausted++;
                    }
                else if (dir[rng].equals("east"))
                    if (col != maze[0].length - 1 && !maze[row][col + 1].getStatus()) {
                        maze[row][col].setEast(true);
                        maze[row][col + 1].setWest(true);
                        col = col + 1;
                        decided = true;
                    } else {
                        dir[rng] = dir[4 - exhausted - 1];
                        dir[4 - exhausted - 1] = null;
                        exhausted++;
                    }
                else if (dir[rng].equals("west"))
                    if (col != 0 && !maze[row][col - 1].getStatus()) {
                        maze[row][col].setWest(true);
                        maze[row][col - 1].setEast(true);
                        col = col - 1;
                        decided = true;
                    } else {
                        dir[rng] = dir[4 - exhausted - 1];
                        dir[4 - exhausted - 1] = null;
                        exhausted++;
                    }
            }
            return helpFinish(maze, row, col,ran,go+1);
        }
    }

    public static void emptyFix(Cell[][] maze)
    {
        for(int i = 1; i<maze.length-1;i++)
        {
            for(int j =1; j < maze[i].length-1;j++)
            {
                if(maze[i][j].getNumOpen() == 0)
                {
                    int rng = (int) (Math.random()*8) + 1;
                    if(rng == 1)
                        rng = 1;
                    else if(rng == 8)
                        rng = 3;
                    else
                        rng =2;
                    while(rng != 0)
                    {
                        int side = (int) (Math.random()*4);
                        if(side == 0 && !maze[i][j].getNorth()) {
                            maze[i][j].setNorth(true);
                            if(!maze[i-1][j].getStatus())
                                maze[i-1][j].setSouth(true);
                            else
                                maze[i][j].setNorth(false);
                        }
                        else if(side == 1 && !maze[i][j].getEast()) {
                            maze[i][j].setEast(true);
                            if(!maze[i][j+1].getStatus())
                                maze[i][j+1].setWest(true);
                            else
                                maze[i][j].setEast(false);
                        }
                        else if(side == 2 && !maze[i][j].getSouth()) {
                            maze[i][j].setSouth(true);
                            if(!maze[i+1][j].getStatus())
                                maze[i+1][j].setNorth(true);
                            else
                                maze[i][j].setSouth(false);
                        }
                        else if(side == 3 && !maze[i][j].getWest()) {
                            maze[i][j].setWest(true);
                            if(!maze[i][j-1].getStatus())
                                maze[i][j-1].setEast(true);
                            else
                                maze[i][j].setWest(false);
                        }
                        else
                            rng++;
                        rng--;
                    }
                }
            }
        }
    }

    public static void export(Cell[][] maze)
    {
        try {
            new File("maze.txt").createNewFile();
            FileWriter writey = new FileWriter("maze.txt");
            writey.write(maze.length + ";" + maze[0].length + ":");
            int num = 0;
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j].getNorth())
                        num += 1;
                    if (maze[i][j].getEast())
                        num += 2;
                    if (maze[i][j].getSouth())
                        num += 4;
                    if (maze[i][j].getWest())
                        num += 8;
                    writey.write(65+num);
                    num=0;
                }
            }
            writey.close();
        }
        catch(Exception e)
            {

            }

    }

    public static String importMaze(String fileName)
    {
        try {
            Scanner tanner = new Scanner(new File(fileName));
            String maze = tanner.nextLine();
            return maze;
        }
        catch(Exception e)
        {
            return "";
        }
    }

    public static Cell[][] solveMaze(String file)
    {
        Cell[][] maze = convertToMaze(file);
        Stack<Cell> stack = new Stack<Cell>();
        int row = 0, col = 0;
        while(row != maze.length-1 || col != maze[0].length-1)
        {
            maze[row][col].setStatus(true);
            if(stack.empty() || stack.peek() != maze[row][col])
                stack.push(maze[row][col]);
            if(maze[row][col].getSouth() && !maze[row+1][col].getStatus())
                row++;
            else if(maze[row][col].getEast() && !maze[row][col+1].getStatus())
                col++;
            else if(maze[row][col].getNorth() && !maze[row-1][col].getStatus())
                row--;
            else if(maze[row][col].getWest() && !maze[row][col-1].getStatus())
                col--;
            else
            {
                stack.pop();
                Cell thing = stack.peek();
                if( row != 0 && stack.peek() == maze[row-1][col])
                    row--;
                else if( col != 0 && stack.peek() == maze[row][col-1])
                    col--;
                else if( row != maze.length-1 && stack.peek() == maze[row+1][col])
                    row++;
                else if( col != maze[0].length-1 && stack.peek() == maze[row][col+1])
                    col++;
                else
                    return null;
            }
        }
        stack.push(maze[row][col]);
        maze[row][col].setStatus(true);

        for(Cell[] rows: maze)
        {
            for(Cell cell: rows)
            {
                cell.setStatus(false);
            }
        }
        while(!stack.empty())
        {
            stack.pop().setStatus(true);
        }
        return maze;
    }
}
