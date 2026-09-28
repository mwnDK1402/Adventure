public class Room
{
    private String name;
    private String description;

    private Room north;
    private Room south;
    private Room east;
    private Room west;


    public Room (String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public void setNorth(Room room)
    {
        this.north = room;
    }

    public void setSouth(Room room)
    {
        this.south = room;
    }

    public void setWest(Room room)
    {
        this.west = room;
    }

    public void setEast(Room room)
    {
        this.east = room;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getName()
    {
        return this.name;
    }

    public Room getNorth()
    {
        return north;
    }

    public Room getSouth()
    {
        return south;
    }

    public Room getEast()
    {
        return east;
    }

    public Room getWest()
    {
        return west;
    }

    public void setWestEast(Room west, Room east)
    {
        west.setEast(east);
        east.setWest(west);
    }

    public void setNorthSouth(Room north, Room south)
    {
        north.setSouth(south);
        south.setNorth(north);
    }


}
