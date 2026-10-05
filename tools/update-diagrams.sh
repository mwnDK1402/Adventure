#!/usr/bin/env bash
#
# Refresh the diagrams in docs/ from the Java sources, then show what was committed.
#
#   tools/update-diagrams.sh                     # update, render and commit
#   tools/update-diagrams.sh --no-run            # only show the relevant commit range
#   tools/update-diagrams.sh --force             # reconcile even if src/ looks unchanged
#   tools/update-diagrams.sh --model <id>        # override the model
#
# The diagram agent is scoped to docs/ and is denied `git push`, so this is safe
# to run without further supervision: it can only ever change documentation.

set -euo pipefail

cd "$(dirname "$0")/.."

MODEL=""
while [[ $# -gt 0 ]]; do
    case "$1" in
        --no-run)
            NO_RUN=1
            ;;
        --force | -f)
            FORCE=1
            ;;
        --model)
            MODEL="$2"
            shift
            ;;
        -h | --help)
            sed -n '2,11p' "$0" | cut -c 3-
            exit 0
            ;;
        *)
            echo "Unknown option: $1" >&2
            exit 2
            ;;
    esac
    shift
done

# The last commit that touched the diagrams. Everything in src/ after it is
# source work the diagrams have not caught up with yet.
if ! BASE=$(git log -1 --format=%H -- docs/); then
    BASE=$(git rev-list --max-parents=0 HEAD)
fi

echo "Diagrams last updated in $BASE ($(git log -1 --format=%s "$BASE"))"

if [[ -n "${NO_RUN:-}" ]]; then
    echo
    git log --oneline "$BASE"..HEAD -- src/
    exit 0
fi

# Compare $BASE against the working tree, so uncommitted src/ edits count too.
# Reconciling costs a model call and a few minutes, so never pay for it when
# there is provably nothing to reconcile.
if git diff --quiet "$BASE" -- src/ && [[ -z "${FORCE:-}" ]]; then
    echo
    echo "src/ is unchanged since then, so the diagrams are already up to date."
    echo "Pass --force to reconcile the whole source tree anyway."
    exit 0
fi

echo
git diff --stat "$BASE" -- src/
echo

BEFORE=$(git rev-parse HEAD)

# Optional model override, passed through only when given.
MODEL_ARGS=()
if [[ -n "$MODEL" ]]; then
    MODEL_ARGS=(--model "$MODEL")
    echo "Using model $MODEL"
    echo
fi

if ! opencode run --agent diagram-updater --auto "${MODEL_ARGS[@]}" \
    "Update the diagram sources in docs/ to match the Java sources in src/main/java.

Start from what changed since commit $BASE:

    git log --oneline $BASE..HEAD -- src/
    git diff $BASE..HEAD -- src/

That range is a hint about where to look, not the whole job. Read the full
sources and reconcile every class, field, method and room against the
diagrams, so that anything missed in earlier runs is caught too.

Then render the diagrams and commit the result.

If after reconciling you find nothing to change, say so in one sentence and
commit nothing."; then
    echo >&2
    echo "The diagram run failed. The usual cause is a model that is not available" >&2
    echo "to you. List what you have, then retry with one of those:" >&2
    echo >&2
    echo "    opencode models" >&2
    echo "    tools/update-diagrams.sh --model <provider>/<model>" >&2
    exit 1
fi

echo
echo "Commits made:"
git log --oneline --stat "$BEFORE"..HEAD || true

if [[ "$BEFORE" == "$(git rev-parse HEAD)" ]]; then
    echo "(nothing was committed -- the diagrams may already be up to date)"
fi