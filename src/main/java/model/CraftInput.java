package model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import converters.DateConverter;
import converters.NumberConverter;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CraftInput {

    @CsvBindByName(column = "GSTIN")
    private String gstin="";

    @CsvCustomBindByName(column = "Date", converter= DateConverter.class)
    private LocalDate date=null;

    @CsvBindByName(column = "Bill no")
    private String billNo="";

    @CsvCustomBindByName(column = "GST rate(%)", converter= NumberConverter.class)
    private float gstRate;

    @CsvCustomBindByName(column = "Taxable Value", converter= NumberConverter.class)
    private float taxableValue;

    @CsvCustomBindByName(column = "IGST", converter= NumberConverter.class)
    private float igst;

    @Override
    public String toString() {
        return gstin+','+
                date+','+
                billNo+','+
                gstRate +','+
               taxableValue +','+
                 igst +','+
                 cgst +','+
                 sgst +','+
                 total;
    }

    @CsvCustomBindByName(column = "CGST", converter= NumberConverter.class)
    private float cgst;

    @CsvCustomBindByName(column = "SGST", converter= NumberConverter.class)
    private float sgst;

    @CsvCustomBindByName(column = "Total", converter= NumberConverter.class)
    private float total;
}
