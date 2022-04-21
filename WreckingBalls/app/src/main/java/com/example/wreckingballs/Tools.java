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

    public static float distanceBetweenPoints(float x1, float y1, float x2, float y2){
        return (float)Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public static float phoneYtoCartesianY(float y){
        return -y;
    }

    //https://math.stackexchange.com/questions/190111/how-to-check-if-a-point-is-inside-a-rectangle
    //https://stackoverflow.com/questions/2752725/finding-whether-a-point-lies-inside-a-rectangle-or-not#:~:text=In%20any%20case%2C%20for%20any,test%20%2D%20the%20point%20is%20inside.
    public static boolean isWithinRectangle(float x, float y, float ax, float ay, float bx, float by, float dx, float dy){
        float bax = bx - ax;
        float bay = by - ay;
        float dax = dx - ax;
        float day = dy - ay;

        if ((x - ax) * bax + (y - ay) * bay < 0.0) return false;
        if ((x - bx) * bax + (y - by) * bay > 0.0) return false;
        if ((x - ax) * dax + (y - ay) * day < 0.0) return false;
        if ((x - dx) * dax + (y - dy) * day > 0.0) return false;

        return true;
    }
}
