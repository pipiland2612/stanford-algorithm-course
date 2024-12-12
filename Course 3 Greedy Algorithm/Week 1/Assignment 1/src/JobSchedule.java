import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Job{
    int weight, length;
    public Job(int weight, int length) {
        this.weight = weight;
        this.length = length;
    }

    public double getRatio() {
        return (double) weight / length;
    }
}
public class JobSchedule {

    public static void main(String[] args) {
        String path = "/Users/batman/Desktop/Stanford Algorithm/Course 3 Greedy Algorithm/Week 1/Assignment 1/src/jobs.txt";
        List<Job> jobs = readJobFromFiles(path);
        jobs.sort((o1, o2) -> {
            double diff1 = o1.getRatio();
            double diff2 = o2.getRatio();

            if (diff1 != diff2) {
                return Double.compare(diff2, diff1);
            } else {
                return Integer.compare(o2.weight, o1.weight);
            }
        });

        long total = 0;
        long completedTime = 0;
        for (Job job : jobs){
            completedTime += job.length;
            total += (long) job.weight * completedTime;
        }
        System.out.println(total);
    }

    private static List<Job> readJobFromFiles(String path){
        List<Job> res = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line = reader.readLine();
            int numberOfJobs = Integer.parseInt(line);

            for (int i = 0; i < numberOfJobs; i++) {
                line = reader.readLine();
                if(line == null)break;

                String[] parts = line.split(" ");
                int w = Integer.parseInt(parts[0]);
                int l = Integer.parseInt(parts[1]);
                res.add(new Job(w, l));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return res;
    }
}
