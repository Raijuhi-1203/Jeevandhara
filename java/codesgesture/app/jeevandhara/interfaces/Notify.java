package codesgesture.app.jeevandhara.interfaces;

import codesgesture.app.jeevandhara.Models.ProductModel;

public interface Notify {
    void onAdd(ProductModel data);
    void onRemove(ProductModel data);
}
