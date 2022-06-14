package tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.ingrediente;
import tacos.ingrediente.Type;
import tacos.Taco;
import tacos.TacoOrder;

@Slf4j
//@Controller
@RequestMapping("/desing")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<ingrediente> ingredientes = Arrays.asList(
            new ingrediente("FLTO","Flour Tortilla",Type.WRAP),
            new ingrediente("COTO","Corn Tortilla",Type.WRAP),
            new ingrediente("GRBF","Grounf Beef",Type.PROTEN),
            new ingrediente("CARN","Carnitas",Type.PROTEN),
            new ingrediente("TMTO","Diced tomatos",Type.VEGGIES),
            new ingrediente("LETC","Lettuce",Type.VEGGIES),
            new ingrediente("CHED","Cheddar",Type.CHEESE),
            new ingrediente("JACK","Monterrey Jack",Type.CHEESE),
            new ingrediente("SLSA","Salsa",Type.SAUCE),
            new ingrediente("SRCR","Sour Cream",Type.SAUCE) 
            
        );

        Type[] types = ingrediente.Type.values();
        for (Type type: types){
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredientes,type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesingForm(){
        return "desing";
    }

    private Iterable<ingrediente> filterByType(List<ingrediente>ingredientes,Type type){
        return ingredientes.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
