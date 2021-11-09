package com.example.cobalagi.model;

public class User {



    String name, alamat, lat, lng, sumber, img, deskripsi, logo, img1, img2, img3;
    private double placeDistance;

        public String getname() {
            return name;
        }

        public String getalamat() { return alamat; }

    public String getSumber() {
        return sumber;
    }

        public String getImg1(){
            return img1;
        }

        public String getImg2(){
            return img2;
        }

        public String getImg3(){
            return img3;
        }


    public String getImg() {
        return img;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getLat() {
        return  lat;
    }


    public String getLng() {
        return lng;
    }

    public double getPlaceDistance() {
        return placeDistance;
    }
    public void setPlaceDistance(double placeDistance) {
        this.placeDistance = placeDistance;
    }



}
