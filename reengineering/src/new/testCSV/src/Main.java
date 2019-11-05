import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private ArrayList[][] map = new ArrayList[28][31];
    private static final String SAMPLE_CSV_FILE_PATH = "src\\map.csv";


    public static void main(String[] args) {

        File file = new File(".");
        for(String fileNames : file.list()) System.out.println(fileNames);





        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader);
        ) {
            // Reading Records One by One in a String array
            String[] nextRecord;
            int count = 0;
            while ((nextRecord = csvReader.readNext()) != null) {
                count++;
                for(String number: nextRecord)
                System.out.println(number);

                if(count ==2)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
