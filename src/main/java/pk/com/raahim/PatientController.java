/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package pk.com.raahim;
import java.util.*;

/**
 *
 * @author yeet
 */
public class PatientController {
    private PatientRegister mData;
    private int mCurrentIndex;
    
    public PatientController() {
        mData = new PatientRegister(new ArrayList(), 0);
        mCurrentIndex = 0;
    }
    
    public void addPatient(int id, String name) {
        mData.insertPatient(new Patient(id, name));
        // mRegister.setNumberOfPatients(mRegister.getNumberOfPatients() + 1);
    }
    
    public void deletePatient(String name) {
        mData.deletePatient(name);
    }
    
    public void updatePatient(String oldName, int id, String name) {
        mData.updatePatient(oldName, id, name);
    }
    
    public Patient nextPatient() {
        if (mCurrentIndex + 1 < mData.mPatients.size()) {
            ++mCurrentIndex;
        }
        
        return mData.mPatients.get(mCurrentIndex);
    }

    public Patient prevPatient() {
        if (mCurrentIndex - 1 >= 0) {
            --mCurrentIndex;
        }
        
        return mData.mPatients.get(mCurrentIndex);
    }

    public Patient firstPatient() {
        mCurrentIndex = 0;
        return mData.mPatients.get(mCurrentIndex);
    }

    public Patient lastPatient() {
        mCurrentIndex = mData.mPatients.size() - 1;
        return mData.mPatients.get(mCurrentIndex);
    }
    
    public Patient findPatient(String name) {
        for (Patient p : mData.mPatients) {
            if (name.equals(p.getName())) {
                return p;
            }
        }
        return new Patient(0, "Not Found");
    }
}
