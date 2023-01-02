package pk.com.raahim;

import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yeet
 */
public class PatientRegister {
    public List<Patient> mPatients;
    private int mNumberOfPatients;

    public int getNumberOfPatients() {
        return mNumberOfPatients;
    }

    public void setNumberOfPatients(int mNumberOfPatients) {
        this.mNumberOfPatients = mNumberOfPatients;
    }
    
    public PatientRegister(List<Patient> patients, int numberOfPatients) {
        mPatients = patients;
        mNumberOfPatients = numberOfPatients;
    }
    
    public boolean insertPatient(Patient patient) {
        for (Patient p: mPatients) {
            if (p.getId() == patient.getId()) {
                return false;
            }
        }
        mPatients.add(patient);
        mNumberOfPatients++;
        return true;
    }
    
    public Patient readPatient(String name) {
        for (Patient p: mPatients) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        
        return new Patient(0, "");
    }
    
    public boolean updatePatient(String oldName, int id, String name) {
        for (int i = 0; i < mNumberOfPatients; ++i) {
            if (mPatients.get(i).getName().equals(oldName)) {
                mPatients.set(i, new Patient(id, name));
                return true;
            }
        }
        
        return false;
    }
    
    public boolean deletePatient(String name) {
        for (int i = 0; i < mNumberOfPatients; ++i) {
            if (mPatients.get(i).getName().equals(name)) {
                mPatients.remove(i);
                return true;
            }
        }
        
        return false;
    }
}
