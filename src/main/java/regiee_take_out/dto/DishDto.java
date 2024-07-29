package regiee_take_out.dto;


import lombok.Data;
import regiee_take_out.entity.Dish;
import regiee_take_out.entity.DishFlavor;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
