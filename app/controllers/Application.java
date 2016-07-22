package controllers;

import services.ItemService;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;


import entities.Item;
import forms.ItemForm;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import views.html.index;
import views.html.main;

import java.util.List;

import javax.inject.Inject;

@Component
public class Application extends Controller {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Inject private ItemService itemSrv;

    public static Result index() {
        return ok(index.render("Inventory List", Form.form(forms.ItemForm.class)));
    }

    public Result listItems() {
        List<Item> items = getAllItems();
        return ok(Json.toJson(items));
    }

    public Result addItem() {
        Form<ItemForm> form = Form.form(forms.ItemForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(index.render("hello, world", form));
        } else {
            ItemForm itemForm = form.get();
            Item item = new Item(itemForm.getName(), itemForm.getDesc());
            itemSrv.addItem(item);
            return redirect(routes.Application.index());
        }
    }

    public Result removeItem(Long id){
        itemSrv.deleteItemById(id);
        return redirect(routes.Application.index());
    }

    private List<Item> getAllItems() {
        if (itemSrv == null) {
            return null;
        } else {
            List<Item> items = itemSrv.getAllItems();
            return items;
        }
    }
}
