package Models;

public class Trainee {
    private long id;
    private String name;
    private String email;
    private String phone;
    private String gender;

    // Constructor dùng khi tạo mới (không có id)
    public Trainee(String name, String email, String gender, String phone) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
    }

    // Constructor dùng khi cập nhật (có id)
    public Trainee(long id, String name, String email, String gender, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
