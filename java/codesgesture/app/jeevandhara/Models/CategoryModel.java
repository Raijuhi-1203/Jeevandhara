package codesgesture.app.jeevandhara.Models;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String id;
    private String category_name;
    private String parant_category_id;
    private String category_photo;
    private String auto_guid;
    private String create_date;
    private String create_time;
    private String last_update_date;
    private String last_update_time;
    private String position_no;
    private String category_status;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getParant_category_id() {
        return parant_category_id;
    }

    public String getCategory_photo() {
        return category_photo;
    }

    public String getAuto_guid() {
        return auto_guid;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public String getLast_update_time() {
        return last_update_time;
    }

    public String getPosition_no() {
        return position_no;
    }

    public String getCategory_status() {
        return category_status;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setParant_category_id(String parant_category_id) {
        this.parant_category_id = parant_category_id;
    }

    public void setCategory_photo(String category_photo) {
        this.category_photo = category_photo;
    }

    public void setAuto_guid(String auto_guid) {
        this.auto_guid = auto_guid;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }

    public void setPosition_no(String position_no) {
        this.position_no = position_no;
    }

    public void setCategory_status(String category_status) {
        this.category_status = category_status;
    }
}