package org.example;

import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainIO {
    static void main() {

        // IO
        File f = new File(".");


        System.out.println( f.exists() );
        System.out.println( f.canRead() );
        System.out.println( f.canWrite() );
        System.out.println( f.isFile() );
        System.out.println( f.isDirectory() );
        System.out.println( f.canExecute() );
        System.out.println( f.length() );

        for( String filename : f.list()){
            System.out.println( filename );
        }



    }
}
