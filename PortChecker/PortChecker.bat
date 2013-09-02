rem Run this file to launch PortChecker
rem Classpath info:

rem Run PortChecker without parameters to check ports in ports.csv
java -jar %~p0PortChecker.jar

rem Run PortChecker with parameters to ignore ports.csv
rem Parameters should follow the form [ipAddress] [port#] [port-range]
rem java -jar %~p0PortChecker.jar 127.0.0.1 50-100 500 600 3500

PAUSE