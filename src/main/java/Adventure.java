public class Adventure
{
    private Player player;
    private Map map;

    public Adventure()
    {
        this.map = new Map();
        map.buildMap();
        this.player = new Player(map.getInitialRoom());
    }

    public String look()
    {
        return player.getCurrentRoom().getDescription();
    }

    public String getRoomName()
    {
        return player.getCurrentRoom().getName();
    }

    public boolean go(String direction)
    {
        return player.move(direction);
    }
}
