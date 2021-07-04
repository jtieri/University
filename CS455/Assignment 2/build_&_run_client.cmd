javac -g -Xlint -Xdiags:verbose  -cp .;renderer_1.jar  %1
java                             -cp .;renderer_1.jar  %~n1
pause
