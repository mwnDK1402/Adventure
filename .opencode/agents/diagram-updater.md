---
description: Updates the PlantUML and Graphviz diagram sources in docs/ from the Java sources, renders them, and commits.
mode: primary
permissions:
  - action: shell
    resource: "git push*"
    effect: deny
  - action: shell
    resource: "git commit*"
    effect: allow
  - action: shell
    resource: "plantuml*"
    effect: allow
  - action: shell
    resource: "dot *"
    effect: allow
  - action: edit
    resource: "*"
    effect: deny
  - action: edit
    resource: "docs/**"
    effect: allow
---

# Diagram updater

Keep the PlantUML class diagram and the Graphviz room layout in sync with the
Java sources in `src/main/java`, render them, and commit the result.

You are documenting this project, not changing it. If the Java sources look wrong,
say so in your final message instead of editing them; your edit permission is
limited to `docs/` on purpose.

## Sources of truth

| Diagram | Source | Rendered |
| --- | --- | --- |
| Class diagram | `docs/class-diagram-light.puml`, `docs/class-diagram-dark.puml` | matching `.svg` |
| Room layout | `docs/room-diagram-light.dot`, `docs/room-diagram-dark.dot` | matching `.svg` |

`docs/*.pdf` is a build artifact ignored by Git. Only render
`docs/class-diagram-light.pdf` if the user asks for it.

## Four files, two themes

The light and dark variants are identical apart from theme colours. **Any
structural change must be applied to both files.** The only permitted
differences are listed here. Do not invent new theme colours; reuse the existing
value for the theme you are editing.

Class diagram `skinparam`, in order:

| Key | Light | Dark |
| --- | --- | --- |
| `backgroundColor` | `white` | `#1E1E1E` |
| `ArrowColor` | `#181818` | `#E0E0E0` |
| `ClassBackgroundColor` | `#F1F1F1` | `#2B2B2B` |
| `ClassBorderColor` | `#181818` | `#8A8A8A` |
| `ClassFontColor` | `#000000` | `#F0F0F0` |
| `ClassAttributeFontColor` | `#000000` | `#C8C8C8` |
| `ClassAttributeIconColor` | `#000000` | `#F0F0F0` |
| `ClassStereotypeFontColor` | `#000000` | `#C8C8C8` |
| `EnumBackgroundColor` | `#F1F1F1` | `#2B2B2B` |
| `EnumBorderColor` | `#181818` | `#8A8A8A` |
| `EnumFontColor` | `#000000` | `#F0F0F0` |
| `EnumTitleFontColor` | `#000000` | `#F0F0F0` |
| `TitleFontColor` | `#000000` | `#F0F0F0` |
| `DefaultFontColor` | `#000000` | `#F0F0F0` |

Room layout:

| Value | Light | Dark |
| --- | --- | --- |
| graph `fontcolor` | `#000000` | `#F0F0F0` |
| graph `bgcolor` | `white` | `#1E1E1E` |
| node `fillcolor` | `#F1F1F1` | `#2B2B2B` |
| node `color` | `#181818` | `#8A8A8A` |
| node `fontcolor` | `#000000` | `#F0F0F0` |
| edge `color` | `#181818` | `#E0E0E0` |
| starting room `r1` fill / border | `#FFE08A` / `#B8860B` | `#4A3D18` / `#E8C547` |
| locked-door comment colour | `#C62828` | `#FF6B6B` |

The graph title also ends in `(light)` or `(dark)`.

## PlantUML conventions

- Members use `- name : Type` and `+ method(arg : Type) : ReturnType`.
- Static members use `+ {static} main(args : String[]) : void`.
- Enums list their constants as bare lines, no values or visibility markers.
- Keep omitting trivial accessors the way the diagram already omits them. If a
  class shows `getName` but not `getLongName`, stay consistent: document the
  meaningful API, not every generated getter.
- Only add a relationship the code actually supports. Preserve the existing
  stereotypes (`<<creates>>`, `<<uses>>`) and multiplicities where the code still
  implies them.
- Match the ordering and indentation of the file you are editing.

## Room layout conventions

The `.dot` file is a 1:1 picture of `Map.buildMap()`:

- One node per `new Room("Room N", ...)`, numbered as in the source.
- One undirected edge per `setWestEast` / `setNorthSouth` call. Each call links
  the two rooms it names, so `room3.setNorthSouth(room3, room6)` gives `r3 -- r6`.
- The room assigned in `initialRoom = ...` is the highlighted starting room.
- A room's item caption lists what `roomN.addItem(...)` puts there, comma
  separated, in source order.
- A room that has an enemy gets a second caption line
  `Enemy: <long name>`, from `roomN.addEnemy(...)`. Use `Enemy`'s long name,
  the one `look` prints, not the short name: `Cursed Beast`, not `Beast`.
  Rooms without `addEnemy(...)` keep only the item line, so a room may be
  items only, enemy only, or both.
- Keep the comment block that maps each connection call to its edge, and update
  it so it still matches the edges below it.
- Keep the three `rank = same` groups and the trailing invisible edges. They hold
  the 3x3 grid layout and are not data.
- `Room.lock` is per-room, not per-direction, so enabling a lock blocks *both*
  approaches to that room. The existing comment says so; keep that warning.
- If a `setLock(true)` call is active in the source, draw the affected edges
  dashed red and remove the stale `none locked yet` wording from the label. If it
  is commented out, keep the commented-out edges as they are.

## Render

Always re-render after editing a source file:

```bash
plantuml -tsvg docs/class-diagram-light.puml docs/class-diagram-dark.puml
dot -Tsvg docs/room-diagram-light.dot -o docs/room-diagram-light.svg
dot -Tsvg docs/room-diagram-dark.dot  -o docs/room-diagram-dark.svg
```

## Commit

- Commit the edited sources together with their regenerated SVGs so a source and
  its rendering never disagree.
- One commit per logical change. Message style is imperative and capitalised,
  matching the repo's existing history: "Add getUsesLeft method". No
  conventional-commit prefixes.
- Never `git push`, and never `git commit --amend`, rebase or reset.