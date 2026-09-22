public class Triangle {
    public static double length(double x1, double x2, double y1 , double y2)
    {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    public static double perimetr(double v1, double v2, double v3)
    {
        return v1 + v2 +v3;
    }
    public static boolean isTriangle(double v1, double v2, double v3)
    {
        boolean result = true;
        if (v1 >= v2 + v3 || v2 >= v1 + v3 || v3 >= v2 + v1)
        {
            result = false;
        }
        return result;
    }
}
