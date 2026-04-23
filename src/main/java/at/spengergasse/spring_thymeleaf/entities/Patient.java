package at.spengergasse.spring_thymeleaf.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private LocalDate birth;
    private char gender;
    private long svnr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.equalsIgnoreCase("admin") || name.equalsIgnoreCase("administrator")){
            throw new IllegalArgumentException("Name  cant be any variation of admin");
        }
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        if(birth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("The birth date cannot be in the future.");
        }
        this.birth = birth;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public long getSvnr() {
        return svnr;
    }

    public void setSvnr(long svnr) {
        if(!checkSVNR(svnr)) {
            throw new IllegalArgumentException("SVNR number is invalid.");
        }
        this.svnr = svnr;
    }

    public int getId() {
        return id;
    }

    private boolean checkSVNR(long svnr) {
        boolean correctSVNR = false;
        List<Integer> svnrlist = new ArrayList<Integer>();

        String s = String.valueOf(svnr);

        for (char c : s.toCharArray()) {
            int ziffer = c - '0';
            svnrlist.add(ziffer);
        }
        int checksum = svnrlist.get(0)*3;
        checksum += svnrlist.get(1)*7;
        checksum += svnrlist.get(2)*9;
        checksum += svnrlist.get(4)*5;
        checksum += svnrlist.get(5)*8;
        checksum += svnrlist.get(6)*4;
        checksum += svnrlist.get(7)*2;
        checksum += svnrlist.get(8);
        checksum += svnrlist.get(9)*6;
        checksum = checksum % 11;

        if (checksum == svnrlist.get(3)) {
            correctSVNR = true;
        }
        return correctSVNR;
    }
}
