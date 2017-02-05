PortChecker
===========

A simple multi-threaded TCP Port Checker utility. Accepts a .csv or a set of args containing a list of IP's and TCP ports and attempts to open a new socket in each

 simple run  
````java
java -jar PortChecker-1.0.jar ports.csv
````

Releases:
<ul>
<li>Branch 0.1 is the first working version; it takes the input from the CSV and outputs everything to the console and logfile.</li>
<li>Branch 0.5 contains the ThreadController and multi-threaded processing of ports.</li>
<li>Branch 1.0 is the "release candidate" and contains the completed reportBuilder().</li>
</ul>

Post Release: 
<ul>
<li>Add a UI(web-based?) and report options?</li>
<li>Processing and condensation of results to be more meaningful</li>
<li>Parameterization of performance values (socket timeout, max threads, chunk size, etc)</li>
</ul>
