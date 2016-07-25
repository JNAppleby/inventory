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

import errcodes.ErrLevel;

import errcodes.ErrCode;

import java.util.List;

import javax.inject.Inject;

@Component
public class Application extends Controller {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Inject private ItemService itemSrv;

    public Result index() {
        return ok(index.render(Form.form(forms.ItemForm.class), itemSrv.getAllItems(), null));
    }

    public Result listAllItems() {
        final List<Item> items = itemSrv.getAllItems();
        return ok(Json.toJson(items));
    }

    public Result addItem() {
        final Form<ItemForm> form = Form.form(forms.ItemForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(index.render(form, itemSrv.getAllItems(), null));
        }

        ItemForm itemForm = form.get();
        Item item = new Item(itemForm.getName(), itemForm.getDesc());

        ErrCode result = itemSrv.addItem(item);

        if (result.getLevel() == ErrLevel.SUCCESS) {
            return ok(index.render(form, itemSrv.getAllItems(), result));
        } else {
            if (result == ErrCode.ADD_DUPLICATE_ITEM) {
                form.reject("name", result.toString());
                return badRequest(index.render(form, itemSrv.getAllItems(), null));
            }
            return badRequest(index.render(form, itemSrv.getAllItems(), result));
        }
    }

    public Result removeItem(Long id) {
        ErrCode result = itemSrv.removeItemById(id);
        if (result.getLevel() == ErrLevel.SUCCESS) {
            return ok(index.render(Form.form(forms.ItemForm.class), itemSrv.getAllItems(), result));
        } else {
            return badRequest(index.render(Form.form(forms.ItemForm.class), itemSrv.getAllItems(), result));
        }
    }
}
