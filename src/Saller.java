public class Saller {
    private String name, email, phone;
    private int id;

    
    Saller(){
        setId(0);
        setName("");
        setEmail("");
        setPhone("");
    }

    Saller(int id, String name, String email) {
        setId(id);
        setName(name);
        setEmail(email);
        setPhone(phone);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
         return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String toString() {
        return this.id + ","  + this.name + "," + this.email + "," + this.phone;
    }


}
