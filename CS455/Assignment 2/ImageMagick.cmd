pushd  C:\ImageMagick-7.0.10-31-portable-Q8-x64
set PATH=%CD%;%PATH%
popd
convert.exe  -delay 1x30  -loop 0  PPM_*.ppm  animation.gif
pause
