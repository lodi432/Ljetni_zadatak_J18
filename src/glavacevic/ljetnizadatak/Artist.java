/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glavacevic.ljetnizadatak;

/**
 *
 * @author domagoj
 */
public class Artist {
    
    private int id;
     private String naziv;
     private String opis;
     private String godine;
     private String ostalo;
     private String zanrA;
     private byte[] picture;

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getZanrA() {
        return zanrA;
    }

    public void setZanrA(String zanrA) {
        this.zanrA = zanrA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getGodine() {
        return godine;
    }

    public void setGodine(String godine) {
        this.godine = godine;
    }

    public String getOstalo() {
        return ostalo;
    }

    public void setOstalo(String ostalo) {
        this.ostalo = ostalo;
    }
    
       @Override
    public String toString() {
        return getNaziv();
    }
     
       


}
