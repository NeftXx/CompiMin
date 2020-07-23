@echo off

REM Tener agregado Cup en variables de entorno

set OPTIONS_PARSER=-locations -interface -parser Parser -symbols Sym

color 0A
echo "Ejecutando CUP"
call java_cup %OPTIONS_PARSER% Parser.cup
echo.
pause
exit