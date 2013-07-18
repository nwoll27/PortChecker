import java.io.*;

public class Logger {
	private File outputFile;
	private BufferedWriter bufferedWriter;

	public Logger(File outputFile) {
		setOutputFile(outputFile);
		try {
			outputFile.createNewFile();
			bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
		} catch (FileNotFoundException fnf) {
			System.out.println("ERROR! File " + outputFile.getName()
					+ " not found.");
			fnf.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public void println(String aString) {
		System.out.println(aString);
		try {
			bufferedWriter.write(aString);
			bufferedWriter.newLine();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			try {
				bufferedWriter.flush();
				bufferedWriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void closeWriter() {
		try {
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	public File getOutputFile() {
		return outputFile;
	}

}
