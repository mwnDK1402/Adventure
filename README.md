# Project: Adventure (Group 7)

Our implementation of the [course project assignment](https://github.com/EK-DAT-GBG-1SEM-E26AB/DAT-GBG-DA-E26AB/tree/main/projekter/adventure), based on the 1977 text adventure Colossal Cave Adventure by Willie Crowther.

For reference, the original game's [C source code](https://github.com/vattam/BSDGames/tree/master/adventure) is bundled with a Linux port of BSD Games.

## Build and run

```bash
mvn package                           # Build target/adventure.jar
java -jar target/adventure.jar        # Run the game
```

> [!TIP]
> The jar's manifest names `Main` as its entry point, so
> `java -jar` needs no classpath and no class name.

`target/adventure.jar` is the whole game in one 15 KB file. Copy it
wherever you like and run it the same way; it works on Windows, macOS
and Linux alike, as long as the machine has a Java 21 or newer runtime
installed. The jar is platform-independent, but the Java runtime is not
bundled into it.

## Commands

| Command | Aliases | Description |
| --- | --- | --- |
| `go north` | `north`, `n` | Move to the room to the north |
| `go south` | `south`, `s` | Move to the room to the south |
| `go east` | `east`, `e` | Move to the room to the east |
| `go west` | `west`, `w` | Move to the room to the west |
| `look` | | Describe the current room and the items in it |
| `inventory` | | List the items you are carrying |
| `take <item>` | | Pick up an item from the current room |
| `drop <item>` | | Put down an item from your inventory |
| `eat <item>` | | Eat an item, either from the current room or from your inventory |
| `health` | | Show your current health and how you are feeling |
| `exit` | | Quit the game |

You start out with 100 health. Only food can be eaten, and eating it changes your
health by the food's health points, which can be negative.

## Releasing

Each submission is a tagged release:

```bash
tools/release.sh
```

On Windows, double-click **`release.cmd`** instead; it finds Git Bash for you.

The script asks a few questions and does the rest, ending with a GitHub release
holding `adventure.jar` and `class-diagram-light.pdf`, and a copy of both in
`dist/`. Every question but the release summary has a default you can accept
with Enter.

`part-1`, `part-2`, … are pre-releases. The finished submission is tagged
`final`.

```bash
tools/release.sh --dry-run     # check everything, change nothing
tools/release.sh --help        # all options
```

### The submission PDF

The class diagram also has to be submitted as a PDF. PlantUML renders one
directly, with no extra tooling:

```bash
plantuml -tpdf docs/class-diagram-light.puml
```

This writes `docs/class-diagram-light.pdf`. It is a build artifact and is
ignored by Git, so it has to be regenerated locally before submitting.
`tools/release.sh` does this for you whenever it is missing or older than its
source, which is the only point at which PlantUML is needed.

Only the light variant is generated. PlantUML's PDF output does not paint a
page background, so the dark variant would come out with near-white text on a
white page. For on-screen viewing, use the SVGs in [Diagrams](#diagrams).

### What you need installed

Java 21+, Maven, Git and the GitHub CLI ([cli.github.com](https://cli.github.com/)),
which needs `gh auth login` once. PlantUML
([plantuml.com/pdf](https://plantuml.com/pdf)) is only needed if the PDF has to
be rendered; the shared `plantuml.zip` works by unzipping it anywhere and either
adding it to `PATH` or setting `PLANTUML_JAR` to the jar inside it.

## Diagrams

### Class diagram

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/class-diagram-dark.svg">
  <img alt="Class diagram" src="docs/class-diagram-light.svg">
</picture>

### Room layout

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/room-diagram-dark.svg">
  <img alt="Room layout" src="docs/room-diagram-light.svg">
</picture>

The diagrams are generated from source files in [`docs/`](docs): the class
diagram from PlantUML and the room layout from Graphviz. Each diagram has a
light and a dark variant that are identical apart from their theme colours.

Edit the `.puml` or `.dot` source to match the code, then re-render:

```bash
plantuml -tsvg docs/class-diagram-light.puml docs/class-diagram-dark.puml
dot -Tsvg docs/room-diagram-light.dot -o docs/room-diagram-light.svg
dot -Tsvg docs/room-diagram-dark.dot  -o docs/room-diagram-dark.svg
```
