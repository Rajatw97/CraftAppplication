package converters;

import com.opencsv.bean.AbstractBeanField;
import lombok.extern.slf4j.Slf4j;
import model.CraftInput;
import java.text.NumberFormat;
import java.text.ParseException;

/* Class used to parse String input fields to float format*/
@Slf4j
public class NumberConverter extends AbstractBeanField<CraftInput> {

    @Override
    protected Object convert (String value){

        if(!value.isEmpty()) {
            try {
                return NumberFormat.getIntegerInstance().parse(value).floatValue();
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        return -999;
    }
}
