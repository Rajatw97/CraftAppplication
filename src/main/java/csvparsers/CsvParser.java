package csvparsers;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.extern.slf4j.Slf4j;
import model.CraftInput;
import model.CraftOutput;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* Class used to perform read and write operations on CSV file*/
@Slf4j
public class CsvParser {

   /* Function used to read CSV file*/
    public List<CraftInput> readCSV(String path) {

        log.info("Reading CSV file...");
        try {
            Reader reader = Files.newBufferedReader(Paths.get(new File("").getAbsolutePath()+path));
            List<CraftInput> records = new CsvToBeanBuilder(reader)
                    .withType(CraftInput.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build().parse();
            reader.close();
            log.info("CSV Read complete");
            return records;
        } catch (IOException e) {
            log.error("Error in reading CSV file:{}", e.getMessage());
        }
        return new ArrayList<>();
    }

    /* Function used to write to CSV file*/
    public void writeCSV(List<CraftOutput> users) {
        try {
            log.info("Writing to CSV file...");
            Writer writer = Files.newBufferedWriter(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Output1.csv"));
            writer.write("GSTIN,Date,Bill no,GST rate(%),Taxable value,IGST,CGST,SGST,Total, Category, GSTIN,Date,Bill no,GST rate(%),Taxable value,IGST,CGST,SGST,Total");
            StatefulBeanToCsv<CraftOutput> csvWriter = new StatefulBeanToCsvBuilder<CraftOutput>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withApplyQuotesToAll(false)
                    .withOrderedResults(true)
                    .build();
            csvWriter.write(users);
            writer.close();
            log.info("CSV Write complete");
        } catch (Exception ex) {
            log.error("Error in writing to CSV file:{}", ex.getMessage());
        }
    }


}
