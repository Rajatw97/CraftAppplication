package converters;

import com.opencsv.bean.AbstractBeanField;
import lombok.extern.slf4j.Slf4j;
import model.CraftInput;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* Class used to parse String input fields to LocalDate format*/
@Slf4j
public class DateConverter extends AbstractBeanField<CraftInput> {


    @Override
    protected Object convert (String value)  {
        try {
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("[d/M/yy][dd-MM-yyyy]");
            return LocalDate.parse(value, parser);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}

