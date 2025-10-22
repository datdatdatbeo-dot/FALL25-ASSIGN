
package model.iam;

import java.util.ArrayList;
import model.BaseModel;


public class Role extends BaseModel {
    private String name;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Feature> features = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }
    
}
