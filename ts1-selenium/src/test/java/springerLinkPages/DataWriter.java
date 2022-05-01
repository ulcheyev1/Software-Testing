package springerLinkPages;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class DataWriter {

    CSVWriter outputFile;

    public DataWriter(String path) throws IOException {
        outputFile = new CSVWriter(new FileWriter(path, true));
    }

    public void writeData(String text, String date, String doi) {
        try {
            String[] line = {text, date, doi};
            outputFile.writeNext(line);
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
