package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CraftOutput {
    /* Output class */
    private CraftInput buyerList;
    private String category;
    private CraftInput supplierList;

}
