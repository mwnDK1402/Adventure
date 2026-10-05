#!/usr/bin/env bash
#
# Refresh the diagrams in docs/ from the Java sources, then show what was committed.
#
#   tools/update-diagrams.sh            # update, render and commit
#   tools/update-diagrams.sh --no-run   # only show the relevant commit range
#
# The diagram agent is scoped to docs/ and is denied `git push`, so this is safe
# to run without further supervision: it can only ever change documentation.

set -euo pipefail

cd "$(dirname "$0")/.."

# The last commit that touched the diagrams. Everything in src/ after it is
# source work the diagrams have not caught up with yet.
if ! BASE=$(git log -1 --format=%H -- docs/); then
    BASE=$(git rev-list --max-parents=0 HEAD)
fi

BEFORE=$(git rev-parse HEAD)

echo "Diagrams last updated in $BASE ($(git log -1 --format=%s "$BASE"))"
echo

if [[ "${1:-}" == "--no-run" ]]; then
    git log --oneline "$BASE"..HEAD -- src/
    exit 0
fi

opencode run --agent diagram-updater --auto \
    "Update the diagram sources in docs/ to match the Java sources in src/main/java.

Start from what changed since commit $BASE:

    git log --oneline $BASE..HEAD -- src/
    git diff $BASE..HEAD -- src/

That range is a hint about where to look, not the whole job. Read the full
sources and reconcile every class, field, method and room against the
diagrams, so that anything missed in earlier runs is caught too.

Then render the diagrams and commit the result."

echo
echo "Commits made:"
git log --oneline --stat "$BEFORE"..HEAD || true

if [[ "$BEFORE" == "$(git rev-parse HEAD)" ]]; then
    echo "(nothing was committed -- the diagrams may already be up to date)"
fi