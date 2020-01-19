public class L2Distance {
    public static double euclideanNormalized(double[] p, double[] q) {
        double distance = 0.0D;
        if (p.length != q.length) {
            System.err.println("Not same length vector");
            return -1.0D;
        } else {
            for(int i = 0; i < p.length; ++i) {
                distance += (p[i] - q[i]) * (p[i] - q[i]);
            }

            return Math.sqrt(distance / (double)p.length);
        }
    }
}
