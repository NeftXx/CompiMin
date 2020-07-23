@echo off

REM Tener agregado JFlex en variables de entorno

color 0A
echo "Ejecutando JFLEX"
call jflex Lexer.jflex
echo.
pause
exit