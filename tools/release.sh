#!/usr/bin/env bash
#
# Cut a new submission release.
#
#   tools/release.sh                  # interactive, every prompt has a default
#   tools/release.sh --dry-run        # build and check, then stop before committing
#   tools/release.sh --tag part-4 --title "Weapons!" --prerelease
#
# Diagrams are not generated here. Run tools/update-diagrams.sh after changing
# the Java sources; this script only warns you if they have fallen behind.
#
# On Windows, run release.cmd instead of opening Git Bash yourself.

set -euo pipefail

cd "$(dirname "$0")/.."

PUML=docs/class-diagram-light.puml
PDF=docs/class-diagram-light.pdf
JAR=target/adventure.jar
DIST=dist

# --------------------------------------------------------------------- output

say() { printf '\n== %s\n' "$*"; }
note() { printf '   %s\n' "$*"; }
warn() { printf '\n!! %s\n' "$*" >&2; }
die() {
    printf '\n!! %s\n' "$*" >&2
    exit 1
}

# ------------------------------------------------------------------ arguments

TAG=""
TITLE=""
SUMMARY=""
NOTES=""
PRERELEASE=""
ASSUME_YES=""
DRY_RUN=""

usage() {
    sed -n '2,12p' "$0" | cut -c 3-
    cat <<'EOF'

Options:
  --tag <name>       Tag to create. Default is the next part-N, or "final".
  --title <text>     Release title. Default is "Part N" or "Final".
  --summary <text>   One-line note for the release page. Asked for otherwise.
  --notes <text>     Replace the whole release body. Overrides --summary.
  --prerelease       Mark the GitHub release as a pre-release.
  --no-prerelease    Publish it as a full release.
  --yes              Accept every default without prompting.
  --dry-run          Stop after the checks, before anything is committed.
  -h, --help         This text.
EOF
}

while [[ $# -gt 0 ]]; do
    case "$1" in
        --tag)
            [[ -n "${2:-}" ]] || die "--tag needs a value."
            TAG="$2"
            shift 2
            ;;
        --title)
            [[ -n "${2:-}" ]] || die "--title needs a value."
            TITLE="$2"
            shift 2
            ;;
        --summary)
            [[ -n "${2:-}" ]] || die "--summary needs a value."
            SUMMARY="$2"
            shift 2
            ;;
        --notes)
            [[ -n "${2:-}" ]] || die "--notes needs a value."
            NOTES="$2"
            shift 2
            ;;
        --prerelease)
            PRERELEASE=yes
            shift
            ;;
        --no-prerelease)
            PRERELEASE=no
            shift
            ;;
        --yes | -y)
            ASSUME_YES=1
            shift
            ;;
        --dry-run)
            DRY_RUN=1
            shift
            ;;
        -h | --help)
            usage
            exit 0
            ;;
        *)
            echo "Unknown option: $1" >&2
            echo "Run tools/release.sh --help for the usage." >&2
            exit 2
            ;;
    esac
done

# ask PROMPT DEFAULT -> $REPLY
#
# read returns non-zero at end of input. Under `set -e` that would abort the
# script silently, and in a retry loop it would spin, so treat it as a real
# failure with a way out.
ask() {
    local prompt="$1" default="${2:-}" reply
    if [[ -n "$ASSUME_YES" ]]; then
        REPLY="$default"
        printf '   %s %s\n' "$prompt" "$default"
        return
    fi
    if [[ -n "$default" ]]; then
        read -r -p "   $prompt [$default]: " reply || no_input
    else
        read -r -p "   $prompt: " reply || no_input
    fi
    REPLY="${reply:-$default}"
}

# confirm PROMPT -> 0 for yes
confirm() {
    local prompt="$1" reply
    if [[ -n "$ASSUME_YES" ]]; then
        printf '   %s yes\n' "$prompt"
        return 0
    fi
    read -r -p "   $prompt [y/N]: " reply || no_input
    [[ "$reply" =~ ^[Yy] ]]
}

no_input() {
    die "No input to read. Re-run with --yes to accept every default, or pass the answers as options."
}

# ---------------------------------------------------------------- requirements

# Report every missing tool at once, so a fresh Windows machine needs one trip
# to the download pages rather than one per run.
MISSING=()

need() {
    local tool="$1" why="$2" remedy="$3"
    if ! command -v "$tool" >/dev/null 2>&1; then
        MISSING+=("$tool"$'\n'"      why: $why"$'\n'"      fix: $remedy")
    fi
}

need java "runs Maven and PlantUML" \
    "Install a Java 21+ runtime and make sure java is on PATH."
need mvn "builds the jar" \
    "Install Maven, or add the Maven wrapper and use ./mvnw package."
need git "commits, tags and pushes" \
    "Install Git for Windows: https://git-scm.com/download/win"
need gh "creates the GitHub release" \
    "Install the GitHub CLI: https://cli.github.com/"

# PlantUML renders the submission PDF. Prefer the command, but fall back to a
# plantuml.jar so the shared zip works without installing anything. It is not a
# hard requirement: only a missing or out-of-date PDF needs it, so it is looked
# for at that point rather than up front.
PLANTUML=()
detect_plantuml() {
    local jar
    if command -v plantuml >/dev/null 2>&1; then
        PLANTUML=(plantuml)
        return 0
    fi
    for jar in \
        "${PLANTUML_JAR:-}" \
        tools/plantuml/plantuml.jar \
        plantuml/plantuml.jar \
        ../plantuml/plantuml.jar \
        "$HOME/Downloads/plantuml/plantuml.jar"; do
        if [[ -n "$jar" && -f "$jar" ]]; then
            PLANTUML=(java -jar "$jar")
            return 0
        fi
    done
    return 1
}

render_pdf() {
    if ! detect_plantuml; then
        warn "PlantUML is needed to render the PDF, but it was not found."
        note "PlantUML can export to PDF: https://plantuml.com/pdf"
        note "Unzip plantuml.zip anywhere, then put it on PATH or set PLANTUML_JAR"
        note "to the jar inside it. Or render the PDF yourself with:"
        note "    plantuml -tpdf $PUML"
        die "Then run this again."
    fi
    "${PLANTUML[@]}" -tpdf "$PUML"
    [[ -f "$PDF" ]] || die "PlantUML did not produce $PDF."
}

if [[ ${#MISSING[@]} -gt 0 ]]; then
    warn "Missing tools:"
    printf '   %s\n' "${MISSING[@]}" >&2
    die "Install the above, then run this again."
fi

if ! gh auth status >/dev/null 2>&1; then
    warn "The GitHub CLI is installed but not logged in."
    note "Run: gh auth login"
    die "Log in, then run this again."
fi

REPO_SLUG=$(gh repo view --json nameWithOwner -q .nameWithOwner 2>/dev/null || true)
if [[ -z "$REPO_SLUG" ]]; then
    REPO_SLUG=$(git remote get-url origin 2>/dev/null |
        sed -E 's#^(https://github\.com/|git@github\.com:)##; s#\.git$##' || true)
fi
[[ -n "$REPO_SLUG" ]] || die "Could not work out which GitHub repository this is."

say "Repository"
BRANCH=$(git rev-parse --abbrev-ref HEAD)
if [[ "$BRANCH" == HEAD ]]; then
    die "You are on a detached HEAD, so there is no branch to push. Check out a branch first."
fi
note "$REPO_SLUG on branch $BRANCH"

# ------------------------------------------------------------------ 1. build

say "Building"
mvn -B package
[[ -f "$JAR" ]] || die "mvn package finished but $JAR is missing."
note "built $JAR"

# ------------------------------------------------- 2. are the diagrams current?
#
# Mirrors the staleness test in tools/update-diagrams.sh: find the last commit
# that touched docs/, then see whether src/ has moved on since. Comparing
# against the working tree means uncommitted Java edits count too.

BASE=$(git log -1 --format=%H -- docs/ || true)
if [[ -z "$BASE" ]]; then
    warn "No commit has touched docs/ yet, so diagram freshness is unknown."
elif git diff --quiet "$BASE" -- src/; then
    note "diagrams are up to date with src/"
else
    warn "src/ has changed since the last diagram update."
    git diff --stat "$BASE" -- src/ | sed 's/^/   /' >&2
    note ""
    note "The diagrams in docs/ may be out of date. Update them, or re-run this"
    note "once they are, unless the change did not affect them."
    note ""
    if ! confirm "Release anyway?"; then
        die "Stopped."
    fi
fi

# ------------------------------------------------------------- 3. the PDF
#
# A build artifact that git ignores, so its freshness cannot be seen in
# git status. Render it here when it is missing or older than its source, so a
# stale diagram never reaches the teachers as a release asset.

if [[ ! -f "$PDF" ]]; then
    say "Rendering the submission PDF"
    note "$PDF is missing, generating it from $PUML"
    render_pdf
elif [[ "$PDF" -ot "$PUML" ]]; then
    say "Rendering the submission PDF"
    warn "$PDF is older than $PUML, regenerating"
    render_pdf
else
    say "Submission PDF"
    note "$PDF is up to date"
fi

# ------------------------------------------------------------- 4. tag choice

tags=()
while IFS= read -r t; do tags+=("$t"); done < <(git tag --list 'part-*' --sort=-v:refname)
LAST_PART="${tags[0]:-part-0}"
NEXT_NUMBER=$(( ${LAST_PART#part-} + 1 ))

if [[ -z "$PRERELEASE" ]]; then
    say "Release type"
    note "part-* tags are pre-releases; the finished submission uses 'final'."
    ask "Is this a pre-release?" "yes"
    PRERELEASE="$REPLY"
fi

if [[ -z "$TAG" ]]; then
    if [[ "$PRERELEASE" == yes ]]; then
        TAG="part-$NEXT_NUMBER"
    else
        TAG="final"
    fi
fi
[[ -n "$TAG" ]] || die "The tag name cannot be empty."

# A tag that is already on the commit being released is fine: it is what you
# get after `gh release create` failed on a previous run, and the retry needs to
# reuse it rather than stop. Anything else means the name is taken.
REUSE_TAG=""
if git rev-parse -q --verify "refs/tags/$TAG" >/dev/null 2>&1; then
    if [[ "$(git rev-list -n 1 "refs/tags/$TAG")" == "$(git rev-parse HEAD)" ]]; then
        REUSE_TAG=1
    else
        die "Tag $TAG already exists on an earlier commit. Delete it or pass a different --tag."
    fi
fi
if gh release view "$TAG" >/dev/null 2>&1; then
    die "A GitHub release for $TAG already exists."
fi

if [[ -z "$TITLE" ]]; then
    if [[ "$PRERELEASE" == yes ]]; then
        TITLE="Part $(( ${TAG#part-} ))"
    else
        TITLE="Final"
    fi
    [[ "$TAG" == part-* ]] || TITLE="Final"
fi

# The release body opens with this line, so it is worth a sentence rather than a
# repeat of the tag. Only skipped when --notes replaces the body outright.
if [[ -z "$SUMMARY" && -z "$NOTES" ]]; then
    if [[ -n "$ASSUME_YES" ]]; then
        SUMMARY="$TITLE"
    else
        say "Release notes"
        note "This opens the release page, like \"Food and Health!\" on part-3."
        while :; do
            ask "What is this part about?" ""
            [[ -n "$REPLY" ]] && break
            warn "A blank summary would just repeat the tag, so please write a line."
        done
        SUMMARY="$REPLY"
        note ""
    fi
fi

# ------------------------------------------------------ 5. what will be tagged

say "About to release"
note "tag:     $TAG"
note "title:   $TITLE"
if [[ -n "$NOTES" ]]; then
    note "body:    replaced by --notes"
elif [[ -n "$SUMMARY" ]]; then
    note "summary: $SUMMARY"
fi
if [[ "$PRERELEASE" == yes ]]; then
    note "type:    pre-release"
else
    note "type:    full release"
fi

say "Working tree"
STATUS=$(git status --short)
if [[ -z "$STATUS" ]]; then
    note "clean, nothing to commit"
else
    printf '%s\n' "$STATUS" | sed 's/^/   /'
    if ! confirm "Commit these changes?"; then
        die "Stopped before committing."
    fi
    ask "Commit message" "Release $TAG"
    git add -A
    git commit -m "$REPLY"
    note "committed"
fi

if [[ -n "$DRY_RUN" ]]; then
    say "Dry run"
    note "Nothing was tagged, pushed or published."
    note "Re-run without --dry-run to finish the release."
    exit 0
fi

# -------------------------------------------------------- 6. tag, push, release

say "Tagging and pushing"
if [[ -n "$REUSE_TAG" ]]; then
    note "reusing the existing tag $TAG"
else
    git tag "$TAG"
    note "created tag $TAG"
fi
# Fully qualify both refs. A bare name is ambiguous when a branch and a tag
# share it, which the "final" tag makes likely.
git push origin "HEAD:refs/heads/$BRANCH"
note "pushed $BRANCH"
git push origin "refs/tags/$TAG:refs/tags/$TAG"
note "pushed tag $TAG"

# --match keeps git from picking up a branch that shares the tag's name.
PREV=$(git describe --tags --abbrev=0 --match 'part-*' "$TAG^" 2>/dev/null || true)

NOTES_FILE=$(mktemp)
trap 'rm -f "$NOTES_FILE"' EXIT
if [[ -n "$NOTES" ]]; then
    printf '%s\n' "$NOTES" >"$NOTES_FILE"
else
    {
        printf '# %s\n\n' "$SUMMARY"
        if [[ -n "$PREV" ]]; then
            printf '**Full Changelog**: https://github.com/%s/compare/%s...%s\n' \
                "$REPO_SLUG" "$PREV" "$TAG"
        else
            printf '**Full Changelog**: https://github.com/%s/commits/%s\n' \
                "$REPO_SLUG" "$TAG"
        fi
    } >"$NOTES_FILE"
fi

say "Creating the GitHub release"
note "attaching $(basename "$JAR") and $(basename "$PDF")"

RELEASE_ARGS=("$TAG" "$JAR" "$PDF" --verify-tag --title "$TITLE" --notes-file "$NOTES_FILE")
if [[ "$PRERELEASE" == yes ]]; then
    RELEASE_ARGS+=(--prerelease)
fi
if ! gh release create "${RELEASE_ARGS[@]}"; then
    warn "The GitHub release could not be created."
    note "The commit and tag $TAG are already pushed, so nothing was lost."
    note "Fix the problem above and run this again; it will reuse tag $TAG."
    exit 1
fi

# ----------------------------------------------------------- 7. hand-off files

say "Collecting the files to send"
mkdir -p "$DIST"
cp "$JAR" "$DIST/"
cp "$PDF" "$DIST/"
note "$(pwd)/$DIST/adventure.jar"
note "$(pwd)/$DIST/class-diagram-light.pdf"

say "Done"
note "release: https://github.com/$REPO_SLUG/releases/tag/$TAG"
note "send this: https://github.com/$REPO_SLUG/tree/$TAG"
if [[ -n "$PREV" ]]; then
    note "changes:  https://github.com/$REPO_SLUG/compare/$PREV...$TAG"
fi
note ""
note "Email the tree link, the PDF and the jar to the teachers."