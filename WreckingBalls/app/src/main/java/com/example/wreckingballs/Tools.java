package com.example.wreckingballs;

public class Tools {

    //https://stackoverflow.com/questions/3365171/calculating-the-angle-between-two-lines-without-having-to-calculate-the-slope
    public static double getAngle(float l1x1, float l1y1, float l1x2, float l1y2, float l2x1, float l2y1, float l2x2, float l2y2){
        double angle1 = Math.atan2(l1y1 - l1y2, l1x1 - l1x2);
        double angle2 = Math.atan2(l2y1 - l2y2, l2x1 - l2x2);
        return angle1-angle2;
    }

    public static int xyToInt(int x, int y, int width, int height){
        return (y * width) + x;
    }

//    public static int intToXy(int i, int width, int height) {
//        int y = (i/width)
//    }

//    public static int phoneXtoCartesianX(){
//
//    }

    public static float phoneYtoCartesianY(float y){
        return -y;
    }
}
