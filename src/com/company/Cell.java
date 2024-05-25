package com.company;

public class Cell {
    private boolean north;
    private boolean south;
    private boolean east;
    private boolean west;
    private boolean visited;

    public Cell()
    {
        north = false;
        south = false;
        east = false;
        west = false;
        visited = false;
    }

    public boolean getNorth(){
        return north;
    }

    public boolean getSouth(){
        return south;
    }

    public boolean getEast(){
        return east;
    }

    public boolean getWest(){
        return west;
    }

    public boolean getStatus(){
        return visited;
    }

    public int getNumOpen()
    {
        int count = 0;
        if(north) count++;
        if(south) count++;
        if(east) count++;
        if(west) count++;
        return count;
    }

    public void setNorth(boolean boo){
        north = boo;
    }

    public void setSouth(boolean boo){
        south = boo;
    }

    public void setEast(boolean boo){
        east = boo;
    }

    public void setWest(boolean boo){
        west = boo;
    }

    public void setStatus(boolean boo){
        visited = boo;
    }
}
