package controllers;

import services.ItemService;

import play.data.Form;
import play.libs.Json;
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

    @Inject private ItemService itemSrv;

    public static Result index() {
        return ok(index.render("Your new application is ready.", Form.form(forms.ItemForm.class)));
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

    private List<Item> getAllItems() {
        if (itemSrv == null) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>itemSrv is null");
            return null;
        } else {
            System.out.println("itemSrv is not null");
            List<Item> items = itemSrv.getAllItems();
            return items;
        }
    }
}
