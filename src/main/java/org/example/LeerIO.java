package org.example;

import java.io.*;

public class LeerIO {
    static void main() {

        File f = new File("pom.xml");

        try( BufferedReader bfr = new BufferedReader( new FileReader(f))){

           var lineas = bfr.readAllLines();
           lineas.forEach( (s)->{
               System.out.println(s);
           });

            String s;
            while( (s=bfr.readLine()) != null ){
                System.out.println(s);
            }



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
