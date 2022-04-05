package service;

import csvparsers.CsvParser;
import lombok.extern.slf4j.Slf4j;
import model.CraftInput;
import model.CraftOutput;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CraftService {

    /* Function containing the logic for categorising the transactions data*/
    public void categoriseTrans() {
        try {
            CsvParser csvParser = new CsvParser();
            CraftService service = new CraftService();
            List<CraftInput> buyerList = csvParser.readCSV("/src/main/resources/Buyer.csv");
            List<CraftInput> sellerList = csvParser.readCSV("/src/main/resources/Supplier.csv");
            List<CraftOutput> craftOutput = new ArrayList<>();
            log.info("Categorise process started");
            for (int index = 0; index < buyerList.size(); index++) {

                if (buyerList.get(index).equals(sellerList.get(index)))
                    craftOutput.add(new CraftOutput(buyerList.get(index), "Exact Match", sellerList.get(index)));
                else if (service.partialMatch(buyerList.get(index), sellerList.get(index)))
                    craftOutput.add(new CraftOutput(buyerList.get(index), "Partial Match", sellerList.get(index)));
                else {
                    craftOutput.add(new CraftOutput(new CraftInput(), "Only in supplier", sellerList.get(index)));
                    craftOutput.add(new CraftOutput(buyerList.get(index), "Only in buyer", new CraftInput()));
                }
            }
            craftOutput.forEach(System.out::println);
            log.info("Categorise process ended");
            csvParser.writeCSV(craftOutput);
        }catch (Exception e){
            log.error("Error in categorise process: {}", e.getMessage());
        }
    }

    /* Function used to check for partial match using java reflection API*/
    private boolean partialMatch(CraftInput buyerList, CraftInput sellerList) {

        boolean dateBool = true, stringBool = true, numberBool = true;
        try {
            Field[] buyerFields = buyerList.getClass().getDeclaredFields();
            for (Field buyer : buyerFields) {
                Field seller = sellerList.getClass().getDeclaredField(buyer.getName());
                PropertyDescriptor pd = new PropertyDescriptor(buyer.getName(), buyerList.getClass());
                PropertyDescriptor pd1 = new PropertyDescriptor(buyer.getName(), sellerList.getClass());
                if (buyer.getType() == float.class && seller.getType() == float.class) {
                    float buyerValue = (float) pd.getReadMethod().invoke(buyerList);
                    float sellerValue = (float) pd1.getReadMethod().invoke(sellerList);
                    if (Math.abs(buyerValue - sellerValue) > 10)
                        numberBool = false;
                }
                if (buyer.getType() == String.class && seller.getType() == String.class) {
                    String buyerValue = (String) pd.getReadMethod().invoke(buyerList);
                    String sellerValue = (String) pd1.getReadMethod().invoke(sellerList);
                    if (!buyerValue.equals(sellerValue))
                        stringBool = false;
                }
                if (buyer.getType() == LocalDate.class && seller.getType() == LocalDate.class) {
                    LocalDate buyerValue = (LocalDate) pd.getReadMethod().invoke(buyerList);
                    LocalDate sellerValue = (LocalDate) pd1.getReadMethod().invoke(sellerList);
                    if (Math.abs(ChronoUnit.DAYS.between(buyerValue, sellerValue)) > 3)
                        dateBool = false;
                }
            }
        }catch (Exception e){
            log.error("Error in partial match logic:{} ",e.getMessage());

        }
        return stringBool || dateBool || numberBool;
    }
}
