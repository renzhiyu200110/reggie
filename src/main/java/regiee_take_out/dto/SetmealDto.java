package regiee_take_out.dto;


import lombok.Data;
import regiee_take_out.entity.Setmeal;
import regiee_take_out.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
