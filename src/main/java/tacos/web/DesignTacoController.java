package tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.TacoOrder;

import javax.validation.Valid;
import org.springframework.validation.Errors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredientes = Arrays.asList(
            new Ingredient("FLTO","Flour Tortilla",Type.WRAP),
            new Ingredient("COTO","Corn Tortilla",Type.WRAP),
            new Ingredient("GRBF","Grounf Beef",Type.PROTEN),
            new Ingredient("CARN","Carnitas",Type.PROTEN),
            new Ingredient("TMTO","Diced tomatos",Type.VEGGIES),
            new Ingredient("LETC","Lettuce",Type.VEGGIES),
            new Ingredient("CHED","Cheddar",Type.CHEESE),
            new Ingredient("JACK","Monterrey Jack",Type.CHEESE),
            new Ingredient("SLSA","Salsa",Type.SAUCE),
            new Ingredient("SRCR","Sour Cream",Type.SAUCE) 
            
        );

        Type[] types = Ingredient.Type.values();
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
        return "design";
    }


    @PostMapping
    public String processTaco(
            @Valid Taco taco, Errors errors,
            @ModelAttribute TacoOrder tacoOrder) {
  
      if (errors.hasErrors()) {
        return "design";
      }
  
      tacoOrder.addTaco(taco);
      log.info("Processing taco: {}", taco);
  
      return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient>ingredientes,Type type){
        return ingredientes.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
