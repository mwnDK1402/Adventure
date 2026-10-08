public class Map {
    private Room initialRoom;

    public void buildMap() {
        // Startområde, evt. simpelt item? (Introduktion)
        Room room1 = new Room("Room 1", """
                You stand at the entrance of a dark cave.
                Cold, damp air flows out from the darkness ahead.
                Old footprints disappear into the cave, mixed with loose stones and patches of mud.
                A worn path disappears into the cave.""");

        // Eventuelt første lille enemy? (Narrow tunnel)
        Room room2 = new Room("Room 2", """
                A tunnel becomes narrower as you move deeper inside.
                The walls are covered in deep scratches, and loose stones cover the ground.
                You hear something moving somewhere in the darkness ahead, but you cannot tell what it is.""");

        // Items? (Abandoned camp)
        Room room3 = new Room("Room 3", """
                An old camp lies hidden between rocky walls.
                A dusty tent still stands beside a burned-out campfire.
                An empty backpack and several forgotten belongings have been left behind.
                Whoever stayed here did not take everything with them...""");

        // Enemy / udfordring / puzzle? (Deep cavern)
        Room room4 = new Room("Room 4", """
                The tunnel opens into a large cavern.
                The ceiling disappears into the darkness, while water slowly drips from the rocks above.
                A faint metallic sound echoes from somewhere deeper inside.
                When you stop moving, the cavern becomes completely silent.""");

        // Final enemy (Final chamber)
        Room room5 = new Room("Room 5", """
                This is the deepest place you've reached so far.
                The chamber is enormous, and your footsteps echo between the walls.
                In the middle of the floor are tracks left by something large.
                You cannot see where they lead.
                Somewhere in the darkness, you hear slow, heavy breathing and you notice something move.
                Two glowing eyes slowly open...""");

        // Puzzle + item? (Underground lake)
        Room room6 = new Room("Room 6", """
                A dark lake fills most of the cavern.
                The water is completely still, despite the constant dripping from the ceiling.
                An old wooden walkway follows the edge of the lake, but parts of it have collapsed.
                Something glimmers faintly beneath the surface...""");

        // Sværere enemies? (Bone chamber)
        Room room7 = new Room("Room 7", """
                The floor is covered in old bones.
                While some are small, others are far to large to be human.
                Deep marks scar the stone floor, as if something heavy has been dragged through the chamber.
                A low growl echoes somewhere beyond the darkness.""");

        // Puzzle / key item? (Ancient ruins)
        Room room8 = new Room("Room 8", """
                The remains of an old ancient stone structure stands in the middle of the room.
                A nearly faded inscription covers one of its walls.
                Among the ruins, you notice fresh marks in the dust, as if something has passed through recently.
                A narrow passage leads deeper into the cave.""");

        // Vigtigt item, måske nødvendigt for at komme ind i room 5? (Forgotten passage)
        Room room9 = new Room("Room 9", """
                The passage ends in a small chamber hidden deep withing the cave.
                At the far end stands an ancient stone door covered in strange symbols.
                In front of it, you find a heavy object resting on a stone pedestal.
                Whatever this item is, it seems to have been placed here for a reason.
                Beyond the door, you can hear a faint, distant sound.""");

        initialRoom = room1;

        //Lock doors
        room2.setLock(true);

        // Room connections
        room2.setWestEast(room1, room2);
        room1.setNorthSouth(room1, room4);
        room3.setWestEast(room2, room3);
        room3.setNorthSouth(room3, room6);
        room4.setNorthSouth(room4, room7);
        room7.setWestEast(room7, room8);
        room5.setNorthSouth(room5, room8);
        room8.setWestEast(room8, room9);
        room6.setNorthSouth(room6, room9);

        // Items for rooms
        Item lamp = new Item("Lamp", "A shiny brass lamp");
        Item key = new Item("Key", "An old rusty key");
        Item backpack = new Item("Backpack", "An old abandoned backpack");
        Item rope = new Item("Rope", "A long, sturdy rope");
        Item torch = new Item("Torch", "An old torch that could still be useful");
        Item hook = new Item("Hook", "A long iron hook used for retrieving objects from the water");
        Item ancientKey = new Item("Ancient Key", "An ancient key covered in strange markings");

        // Add items to room
        room1.addItem(backpack);
        room2.addItem(key);
        room2.addItem(lamp);
        room3.addItem(rope);
        room4.addItem(torch);
        room6.addItem(hook);
        room8.addItem(ancientKey);

        // Food for rooms
        Food beans = new Food("Canned beans", "A dusty can of preserved beans", 15);
        Food mushroom = new Food("Mushroom", "A glowing mushrooom", -20);
        Food trailMix = new Food("Trail mix", "A small bag of old trail mix", 5);
        Food fish = new Food("Fish", "An old, rotten fish", -15);
        Food driedMeat = new Food("Dried meat", "A strip of dried meat left among old bones", 10);
        Food energyBar = new Food("Energy bar", "An old energy bar that is still surprisingly edible", 10);

        // Add food to rooms
        room2.addItem(beans);
        room3.addItem(trailMix);
        room4.addItem(mushroom);
        room6.addItem(fish);
        room7.addItem(driedMeat);
        room9.addItem(energyBar);

        // Weapons for room
        Weapon woodenSword = new MeleeWeapon("Wooden sword", "A mossy wooden sword", 5);
        Weapon slingshot = new RangedWeapon("Slingshot", "A wooden slingshot with a worn leather pouch", 8, 3);
        Weapon bowNarrow = new RangedWeapon("Bow & Arrow", "An old bow with rusty but sharp arrows", 10, 10);
        Weapon rustyDagger = new MeleeWeapon("Rusty dagger", "A crude dagger covered in rust and scratches", 5);
        Weapon tailSpike = new MeleeWeapon("Tail spike", "A jagged spike taken from a stone scorpion's tail", 2);
        Weapon boneClub = new MeleeWeapon("Bone club", "A club made from a large animal bone", 3);
        Weapon stoneFist = new MeleeWeapon("Stone fist", "A heavy stone fist from an ancient guardian", 10);
        Weapon steelSword = new MeleeWeapon("Steel sword", "A well-preserved steel sword with a sharp blade", 15);
        Weapon crushingClaw = new MeleeWeapon("Crushing Claw", "A massive claw capable of crushing bone", 20);

        // Add weapons to rooms
        room1.addItem(woodenSword);
        room3.addItem(slingshot);
        room7.addItem(boneClub);
        room8.addItem(stoneFist);
        room9.addItem(steelSword);

        //Enemies in room
        Enemy skeleton = new Enemy("Skeleton", "Enraged Ranged Skeleton", "A living skeleton with a bow and arrow", 40, bowNarrow, room7);
        Enemy goblin = new Enemy("Goblin", "Cave Goblin", "A small creature carrying a crude weapon and wearing scraps of old clothing", 20, rustyDagger, room2);
        Enemy scorpion = new Enemy("Scorpion", "Stone scorpion", "A hostile scorpion with a hard shell and a heavy tail covered in jagged spikes", 15, tailSpike, room4);
        Enemy serpent = new Enemy("Serpent", "Venomous Serpent", "A venomous serpent that moves silently beneath the dark water", 20, rustyDagger, room6);
        Enemy stoneGuardian = new Enemy("Guardian", "Stone Guardian", "An ancient stone creature that awakens as you approach the ruins", 50, stoneFist, room8);
        Enemy cursedBeast = new Enemy("Beast", "Cursed Beast", "A massive creature corrupted by the darkness of the cave. Its eyes are glowing in the darkness, and the powerful body is covered in scars", 75, crushingClaw, room5);

        // Add enemies to rooms
        room2.addEnemy(goblin);
        room4.addEnemy(scorpion);
        room5.addEnemy(cursedBeast);
        room6.addEnemy(serpent);
        room7.addEnemy(skeleton);
        room8.addEnemy(stoneGuardian);

    }

    public Room getInitialRoom() {
        return initialRoom;
    }
}
