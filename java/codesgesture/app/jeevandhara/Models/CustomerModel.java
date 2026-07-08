package codesgesture.app.jeevandhara.Models;

import java.io.Serializable;

public class CustomerModel implements Serializable {
    private String id;
    private String customer_temp_id;
    private String customer_id;
    private String customer_name;
    private String customer_email = null;
    private String customer_mobileno;
    private String customer_gender = null;
    private String customer_dob = null;
    private String customer_password;
    private String customer_profilephoto = null;
    private String email_verification_code = null;
    private String email_verified = null;
    private String mobileno_verified = null;
    private String otp = null;
    private String customer_date;
    private String customer_time;
    private String referral_code = null;
    private String customer_status;
    private String unique_id;

    public String getReferall_code() {
        return referall_code;
    }

    public void setReferall_code(String referall_code) {
        this.referall_code = referall_code;
    }

    private String referall_code;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getCustomer_temp_id() {
        return customer_temp_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getCustomer_mobileno() {
        return customer_mobileno;
    }

    public String getCustomer_gender() {
        return customer_gender;
    }

    public String getCustomer_dob() {
        return customer_dob;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public String getCustomer_profilephoto() {
        return customer_profilephoto;
    }

    public String getEmail_verification_code() {
        return email_verification_code;
    }

    public String getEmail_verified() {
        return email_verified;
    }

    public String getMobileno_verified() {
        return mobileno_verified;
    }

    public String getOtp() {
        return otp;
    }

    public String getCustomer_date() {
        return customer_date;
    }

    public String getCustomer_time() {
        return customer_time;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public String getCustomer_status() {
        return customer_status;
    }

    public String getUnique_id() {
        return unique_id;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomer_temp_id(String customer_temp_id) {
        this.customer_temp_id = customer_temp_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public void setCustomer_mobileno(String customer_mobileno) {
        this.customer_mobileno = customer_mobileno;
    }

    public void setCustomer_gender(String customer_gender) {
        this.customer_gender = customer_gender;
    }

    public void setCustomer_dob(String customer_dob) {
        this.customer_dob = customer_dob;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }

    public void setCustomer_profilephoto(String customer_profilephoto) {
        this.customer_profilephoto = customer_profilephoto;
    }

    public void setEmail_verification_code(String email_verification_code) {
        this.email_verification_code = email_verification_code;
    }

    public void setEmail_verified(String email_verified) {
        this.email_verified = email_verified;
    }

    public void setMobileno_verified(String mobileno_verified) {
        this.mobileno_verified = mobileno_verified;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setCustomer_date(String customer_date) {
        this.customer_date = customer_date;
    }

    public void setCustomer_time(String customer_time) {
        this.customer_time = customer_time;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public void setCustomer_status(String customer_status) {
        this.customer_status = customer_status;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }
}