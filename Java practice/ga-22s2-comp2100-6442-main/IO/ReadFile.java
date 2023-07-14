import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.concurrent.ThreadLocalRandom;

/**
 * This {@link ReadFile} class is design for generate json configuration for Firebase,
 * which converts the semi-structured data (text) to correlated format.
 * The whole program is designed based on Java I/O.
 * @author Zeyu Zhang u7394442
 */

public class ReadFile {
    public static void main(String[] args) {
        String tag = "women";
        try {
            ArrayList<String> name = new ArrayList<String>();
            File myObj = new File("./semi-structured_data/text/" + tag + "_name.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                name.add(data);
            }
            myReader.close();

            ArrayList<String> money = new ArrayList<String>();
            File myObj2 = new File("./semi-structured_data/text/" + tag + "_money.txt");
            Scanner myReader2 = new Scanner(myObj2);
            while (myReader2.hasNextLine()) {
                String data2 = myReader2.nextLine();
                money.add(data2);
            }
            myReader2.close();


            File myObj3 = new File("./output/" + tag + ".txt");

            int i = 0;
            FileWriter writer = new FileWriter(myObj3);
            BufferedWriter bw = new BufferedWriter(writer);
            while (name.size() > i) {
                bw.newLine();
                bw.write("\"" + tag + (i+1)+ "\": {");
                bw.newLine();
                bw.write("\t\"name\": \"" + name.get(i) + "\",");
                bw.newLine();
                bw.write("\t\"seller\": \"testseller@gmail.com\",");
                bw.newLine();
                bw.write("\t\"pic\": \"" + tag + "/" + (i+1) + ".jpg\",");
                bw.newLine();
                bw.write("\t\"price\": \"" + money.get(i) + "\",");
                bw.newLine();
                bw.write("\t\"price_d\": \"" + money.get(i).replace("$", "").replace(",", "") + "\",");
                bw.newLine();
                bw.write("\t\"rating\": \"" + ThreadLocalRandom.current().nextInt(0, 5 + 1) + "\",");
                bw.newLine();
                bw.write("\t\"SN\": \"" + (tag+(i+1)).hashCode() + "\",");
                bw.newLine();
                bw.write("\t\"sales\": \"" + ThreadLocalRandom.current().nextInt(0, 1000 + 1) + "\",");
                bw.newLine();
                bw.write("\t\"tag\": \"" + tag + "\"");
                bw.newLine();


                bw.write("},");
                i++;
            }
            bw.close();


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
