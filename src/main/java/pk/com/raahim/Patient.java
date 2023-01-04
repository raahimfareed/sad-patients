/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pk.com.raahim;

/**
 *
 * @author yeet
 */
public class Patient {
    private int mId;
    private String mName;
    public Patient(int id, String name) {
        mId = id;
        mName = name;
    }
    public Patient() {
        mId = 0;
        mName = "";
    }
    public int getId() {
        return mId;
    }
    public String getName() {
        return mName;
    }
    public void setId(int id) {
        mId = id;
    }
    public void setName(String name) {
        mName = name;
    }

    static Patient Patient404() {
        return new Patient(0, "Patient Not Found");
    }
}
