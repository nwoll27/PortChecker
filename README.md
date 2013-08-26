PortChecker
===========

A simple TCP Port Checker utility. Accepts a .csv containing a list of IP's and TCP ports and attempts to open a new socket in each

Releases:
<ul>
<li>Branch 0.1 is the first working version; it takes the input from the CSV and outputs everything to the console and logfile.</li>
<li>Branch 0.5 will contain the ThreadController and multi-threaded processing of ports.</li>
<li>Branch 1.0 will be the "release candidate" and will contain the completed ReportBuilder and condensed results</li>
</ul>

TODO: This project has been refactored from its original build. A list of some pending changes:
<ul>
<li>Introduction of multi-threading to speed up process time for larger lists of ports</li>
<li>More robust handling of input values (parsing of CSV? command line args)</li>
<li>Processing and condensation of results to be more meaningful</li>
<li>Creation of ReportBuilder code create a more readable (and more useful) report</li>
</ul>

Post Release: 
<ul>
<li>Add a UI(web-based?) and report options?</li>
</ul>
