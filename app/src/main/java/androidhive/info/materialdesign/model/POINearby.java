package androidhive.info.materialdesign.model;

import java.util.ArrayList;

/**
 * Created by Khusnan on 6/12/15.
 */
public class POINearby {
    String id_poi, foto_poi, nama_poi, date_added, ratting, jumlah_review, id_kategori_poi, nama_kategori, latitude, longitude, jarak;

    public POINearby(String id_poi, String foto_poi, String nama_poi, String date_added, String ratting, String jumlah_review, String id_kategori_poi, String nama_kategori, String latitude, String longitude, String jarak) {
        this.id_poi = id_poi;
        this.foto_poi = foto_poi;
        this.nama_poi = nama_poi;
        this.date_added = date_added;
        this.ratting = ratting;
        this.jumlah_review = jumlah_review;
        this.id_kategori_poi = id_kategori_poi;
        this.nama_kategori = nama_kategori;
        this.latitude = latitude;
        this.longitude = longitude;
        this.jarak = jarak;
    }

    public POINearby() {
    }

    public String getId_poi() {
        return id_poi;
    }

    public void setId_poi(String id_poi) {
        this.id_poi = id_poi;
    }

    public String getFoto_poi() {
        return foto_poi;
    }

    public void setFoto_poi(String foto_poi) {
        this.foto_poi = foto_poi;
    }

    public String getNama_poi() {
        return nama_poi;
    }

    public void setNama_poi(String nama_poi) {
        this.nama_poi = nama_poi;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
        this.ratting = ratting;
    }

    public String getJumlah_review() {
        return jumlah_review;
    }

    public void setJumlah_review(String jumlah_review) {
        this.jumlah_review = jumlah_review;
    }

    public String getId_kategori_poi() {
        return id_kategori_poi;
    }

    public void setId_kategori_poi(String id_kategori_poi) {
        this.id_kategori_poi = id_kategori_poi;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }
}
