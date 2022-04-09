package function;

public class Arctg {

    public double getArctg(double x, double e) {
        double startX = x;
        int cnt = 0;
        if (x < 0) {
            x = x * (-1);
        } else if (x > 1) {
            x = 1 / x;
        }
        while (x * 12 / Math.PI > 0) {
            cnt++;
            x = (x * Math.sqrt(3) - 1) / (x + Math.sqrt(3));
        }

        double a = x, y = a;
//        for (int i = 1; a > e; i++) {
//            b = b * x * (-x);
//            a = a * b / (2 * i + 1);
//            y = y + a;
//        }
        y = y + cnt * Math.PI / 6;
        if (startX > 1) {
            return Math.PI / 2 - y;
        } else if (startX < 0) {
            return -1 * y;
        }
        return y;
    }
}
