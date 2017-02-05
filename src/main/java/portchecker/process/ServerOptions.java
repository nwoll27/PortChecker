package portchecker.process;

/**
 * Created by Mishin737 on 10.01.2017.
 */

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 * Command-line options definition for example server.
 */
public class ServerOptions extends OptionsBase {

    @Option(
            name = "help",
            abbrev = 'h',
            help = "Prints usage info.",
            defaultValue = "true"
    )
    public boolean help;

    @Option(
            name = "in_file",
            abbrev = 'i',
            help = "Input file.",
            category = "startup",
            defaultValue = "" //ports.csv
    )
    public String inFile;

    @Option(
            name = "out_file",
            abbrev = 'o',
            help = "Output file.",
            category = "startup",
            defaultValue = ""//port_log.txt
    )
    public String outFile;


}