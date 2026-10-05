@echo off
REM Double-click this file to cut a release. It finds Git Bash for you and
REM runs tools/release.sh from the repository root, so there is no need to
REM navigate anywhere or type a path.
REM
REM Any argument is passed straight through:
REM     release.cmd --dry-run
REM     release.cmd --tag part-4 --title "Weapons!" --prerelease
REM
REM It needs Git for Windows (https://git-scm.com/download/win) and the
REM GitHub CLI (https://cli.github.com/).

setlocal

set "SCRIPT_DIR=%~dp0"

REM Prefer bash on PATH; fall back to a Git for Windows install elsewhere.
set "BASH="
for %%I in (bash.exe) do if not "%%~$PATH:I"=="" set "BASH=%%~$PATH:I"

if not defined BASH if exist "C:\Program Files\Git\bin\bash.exe" set "BASH=C:\Program Files\Git\bin\bash.exe"
if not defined BASH if exist "C:\Program Files (x86)\Git\bin\bash.exe" set "BASH=C:\Program Files (x86)\Git\bin\bash.exe"

if not defined BASH (
    echo.
    echo Git Bash was not found. Install Git for Windows:
    echo     https://git-scm.com/download/win
    echo.
    pause
    exit /b 1
)

"%BASH%" -c "cd '`cygpath -u '%SCRIPT_DIR%'`' && exec bash tools/release.sh %*"
set "EXITCODE=%ERRORLEVEL%"

if not "%EXITCODE%"=="0" (
    echo.
    echo Release failed with exit code %EXITCODE%.
)

pause
exit /b %EXITCODE%