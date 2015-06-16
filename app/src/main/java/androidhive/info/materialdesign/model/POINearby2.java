package androidhive.info.materialdesign.model;

import java.util.ArrayList;

/**
 * Created by Khusnan on 6/14/15.
 */
public class POINearby2 {
    private String tanggal, isi, headlines, link;
    public ArrayList list;

    public POINearby2(String tanggal, String isi, String headlines, String link) {
        this.tanggal = tanggal;
        this.isi = isi;
        this.headlines = headlines;
        this.link = link;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getHeadlines() {
        return headlines;
    }

    public void setHeadlines(String headlines) {
        this.headlines = headlines;
    }

    public String getKonten() {
        return link;
    }

    public void setKonten(String link) {
        this.link = link;
    }
}
