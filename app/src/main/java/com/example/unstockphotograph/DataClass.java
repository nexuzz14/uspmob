package com.example.unstockphotograph;

public class DataClass {
    String judul;
    String deskripsi;
    String imageUrl;

    // Constructor
    public DataClass() {
        // Diperlukan untuk Firebase atau pengambilan data kosong
    }

    public DataClass(String judul, String deskripsi, String imageUrl) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.imageUrl = imageUrl;
    }

    // Getter dan Setter
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
