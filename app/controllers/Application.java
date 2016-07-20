package controllers;

import services.ItemService;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import javax.inject.Inject;

import views.html.index;
import views.html.main;

import forms.ItemForm;

import org.springframework.stereotype.Component;
import entities.Item;

@Component
public class Application extends Controller {

    @Inject
    private ItemService itemSrv;

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result listItems(){
        List<Item> items = getAllItems();
        return ok(play.libs.Json.toJson(items));
        //return ok(index.render("getting list...."));
    }

    public Result addItem(){
        Form<ItemForm> form = Form.form(ItemForm.class).bindFromRequest();
        ItemForm itemForm = form.get();
        Item item = new Item(itemForm.getName(), itemForm.getDesc());
        itemSrv.addItem(item);
        return redirect(routes.Application.index());
    }

    private List<Item> getAllItems(){
        if(itemSrv == null){
            System.out.println(">>>>>>>>>>>>>>>>>>>>itemSrv is null");
            return null;
        }else{
            System.out.println("itemSrv is not null");
            List<Item> items = itemSrv.getAllItems();
            return items;
        }
    }
}
