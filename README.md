PortChecker
===========

A simple multi-threaded TCP Port Checker utility. Accepts a .csv containing a list of IP's and TCP ports and attempts to open a new socket in each

Release 0.5:
<ul>
<li>Branch 0.5 contains the ThreadController and multi-threaded processing of ports.</li>
</ul>

TODO: This project has been refactored from its original build. A list of some pending changes:
<ul>
<li>More robust handling of input values (parsing of CSV? command line args)</li>
<li>Processing and condensation of results to be more meaningful</li>
<li>Creation of ReportBuilder code create a more readable (and more useful) report</li>
</ul>

Post Release: 
<ul>
<li>Add a UI(web-based?) and report options?</li>
</ul>
