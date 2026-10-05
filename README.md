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

Each submission is a tagged release. To cut one, run:

```bash
tools/release.sh
```

On Windows, double-click **`release.cmd`** in the repository root instead. It
finds Git Bash for you, so there is no need to open a terminal or type a path.

The script asks a few questions, then it:

1. builds the jar with `mvn package`;
2. warns you if the Java sources have changed since the diagrams were last
   updated, and offers to stop (see [Diagrams](#diagrams));
3. renders `docs/class-diagram-light.pdf` if it is missing or older than its
   source, so a stale diagram never reaches the teachers;
4. asks what the part is about, which becomes the heading on the release page;
5. commits any uncommitted changes, tags the commit and pushes both;
6. creates the GitHub release with the jar and the PDF attached;
7. copies both files into `dist/`;
8. prints the link to send the teachers.

Most questions have a default you can accept by pressing Enter. The summary in
step 4 is the exception: it wants a real sentence, like *"Food and Health!"* on
`part-3`, because it is the first thing a reader sees.

`part-1`, `part-2`, … are pre-releases. The finished submission uses the tag
`final` and is published as a full release. The script asks which one you want
and suggests the next free number.

Re-running the script after a failure is safe: if the tag is already on the
commit being released, it is reused rather than refused.

```bash
tools/release.sh --dry-run              # check everything, change nothing
tools/release.sh --help                 # all options
```

Useful options: `--tag <name>` to choose the tag, `--summary <text>` for the
release heading, `--title <text>` and `--notes <text>` for the release title
and a complete replacement body, `--prerelease` or `--no-prerelease` to skip
that question, and `--yes` to accept every default without prompting.

### What you need installed

| Tool | Why | Where |
| --- | --- | --- |
| Java 21+ | Runs Maven and PlantUML | [adoptium.net](https://adoptium.net/) |
| Maven | Builds the jar | [maven.apache.org](https://maven.apache.org/download.cgi) |
| Git | Commits, tags, pushes | [git-scm.com](https://git-scm.com/download/win) |
| GitHub CLI | Creates the release | [cli.github.com](https://cli.github.com/) |
| PlantUML | Renders the submission PDF | [plantuml.com/pdf](https://plantuml.com/pdf) |

Log the GitHub CLI in once with `gh auth login`. The script checks all of these
up front and reports everything that is missing at once, with a link for each.

PlantUML only has to be able to write a PDF, which is why the shared
`plantuml.zip` works: unzip it anywhere, then either add it to `PATH` or point
the script at the jar with `PLANTUML_JAR`. Graphviz is *not* needed to release;
only the diagram script uses it.

### Sending it in

Email the teachers the `tree/<tag>` link the script prints, together with
`dist/adventure.jar` and `dist/class-diagram-light.pdf`. Both are also attached
to the release, so the link alone is enough for them to download them.

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

To bring them back in sync with the Java sources after changing them, run:

```bash
tools/update-diagrams.sh
```

This finds the last commit that touched `docs/`. If nothing in `src/main/java`
has changed since then — including uncommitted edits — it reports that the
diagrams are already up to date and exits without calling the model. Otherwise
it asks the `diagram-updater` agent in [`.opencode/agents/`](.opencode/agents) to
reconcile the diagram sources with the whole source tree, then render and commit
the result. The script prints the commits that were made at the end so you can
review them.

```bash
tools/update-diagrams.sh --no-run     # only list the relevant commits
tools/update-diagrams.sh --force      # reconcile even if src/ looks unchanged
tools/update-diagrams.sh --help       # full usage
```

The agent may only edit files under `docs/` and is denied `git push`, so it
cannot change the game itself or publish anything.

The script runs it on `opencode/big-pickle`. Use `--model <provider>/<model>`,
or set `DIAGRAM_MODEL`, to run it on something else; `opencode models` lists
what you have available.

Rendering the diagrams by hand, without the agent (needs Graphviz):

```bash
plantuml -tsvg docs/class-diagram-light.puml docs/class-diagram-dark.puml
dot -Tsvg docs/room-diagram-light.dot -o docs/room-diagram-light.svg
dot -Tsvg docs/room-diagram-dark.dot  -o docs/room-diagram-dark.svg
```

### PDF for submission

The class diagram also has to be submitted as a PDF. PlantUML renders one
directly, with no extra tooling:

```bash
plantuml -tpdf docs/class-diagram-light.puml
```

This writes `docs/class-diagram-light.pdf`. It is a build artifact and is
ignored by Git, so it has to be regenerated locally before submitting.
`tools/release.sh` does this for you and copies the result into `dist/`.

Only the light variant is generated. PlantUML's PDF output does not paint a
page background, so the dark variant would come out with near-white text on a
white page. For on-screen viewing, use the SVGs above instead.
