import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {
    private static int countIt = 1;
    public static void main(String[] args) {
        int k;
        String claster;
        ArrayList<Vector<Double>> inputs = new ArrayList<>();
        ArrayList<Vector<Double>> clusters = new ArrayList<>();
        File file = new File("iris.data");
        Scanner scanner = new Scanner(System.in);
        System.out.println(new File(".").getAbsolutePath());
        System.out.println("Enter the number of centroids : ");
        k = Integer.parseInt(scanner.nextLine().trim());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file)); // going through the test file
            String line;
            while ((line = reader.readLine()) != null) {
                Vector<Double> vector = parseVector(line);
                inputs.add(vector);
//                for (Double d: vector) {
//                    System.out.print(d + " ");
//                }
//                System.out.println();
            }
            for (int i = 1; i < k + 1; i++) {
                System.out.println("Enter the " + i + " cluster parameters");
                claster = scanner.nextLine();
                Vector<Double> vector = parseVector(claster);
                clusters.add(vector);
            }
//            for (Vector<Double> d: clusters) {
//                System.out.print(d + " ");
//            }

            clustering(inputs,clusters);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void clustering( ArrayList<Vector<Double>> inputs, ArrayList<Vector<Double>> clusters){
        ArrayList<Vector<Vector<Double>>> centr = distributeInputsToClusters(inputs, clusters);
        ArrayList<Vector<Double>> newCentroids = new ArrayList<>();
        int i = 0;
        System.out.println("ITERATION : " + countIt++);
        for (Vector<Vector<Double>> v : centr
        ) {
            System.out.println("Centroid : " + ++i);

            for (Vector<Double> v1 : v
            ) {
                System.out.print(v1);
            }
            System.out.println();
            Vector<Double> avgs = findAvg(v);
            System.out.println("Average: " + avgs);
            newCentroids.add(avgs);
        }
        System.out.println("-----------------------------------------------------------------");
        if (!clusters.equals(newCentroids)) {
            clustering(inputs, newCentroids);
        }
    }

    private static Vector<Double> findAvg(Vector<Vector<Double>> centroid) {
        Vector<Double> res = new Vector<>();
        for (int i = 0; i < centroid.get(0).size(); i++) {
            double sum = 0;
            for (int j = 0; j < centroid.size(); j++) {
                sum += centroid.get(j).get(i);
            }
            res.add(sum / centroid.size());
        }
        return res;
    }

    private static ArrayList<Vector<Vector<Double>>> distributeInputsToClusters(ArrayList<Vector<Double>> inputs, ArrayList<Vector<Double>> centroids) {
        ArrayList<Vector<Vector<Double>>> clusterMembers = new ArrayList<>(); // 3d array of k centroids with iris data on each centroid
        for (int i = 0; i < centroids.size(); i++) {
            clusterMembers.add(new Vector<>());
        }
        for (int i = 0; i < inputs.size(); i++) {
            List<Double> distances = new ArrayList<>();
            for (int j = 0; j < centroids.size(); j++) {
                double dst = 0;
                for (int k = 0; k < centroids.get(j).size(); k++) {
                    dst += Math.pow(inputs.get(i).get(k) - centroids.get(j).get(k), 2);

                }
                distances.add(Math.sqrt(dst));
            }
           // System.out.println(distances);
            int id = distances.indexOf(Collections.min(distances)); // index of min element
           // System.out.println(inputs.get(i) + " belongs to " + (id + 1) + " centroid");
            clusterMembers.get(id).add(inputs.get(i));
        }
        return clusterMembers;
    }

    private static Vector<Double> parseVector(String line) {
        Vector<Double> resVector = new Vector<>();
        String[] data = line.split(",");
        for (int i = 0; i < data.length - 1; i++) {
            resVector.add(Double.parseDouble(data[i]));
        }
        return resVector;
    }
}
