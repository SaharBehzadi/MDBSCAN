package compare.comparisonMethods.KMeansMixed;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Random;

public class RanCluster {
    double runtime;

    public void pushstring(Vector v7, String x) {
        v7.addElement(new String(x));
    }

    public RanCluster() {
    }



    public void run(int numClusters) throws IOException {
        long startTime = System.currentTimeMillis();
//        System.out.println(" enter the number of classes ");
//        BufferedReader stdin2d9 = new BufferedReader(new InputStreamReader(System.in));
//        String s6119 = stdin2d9.readLine();
        int yy = numClusters;
        Random rand = new Random();
        String a[] = new String[10000];
        String a111 = new String();
        int j = 0;
        for (int i = 0; i < 10000; i++) {
            j = (int) (100 * rand.nextDouble());
            if (j != 100) {
                j = j + 1;
            }
            String b121 = ";";
            for (int i1 = 1; i1 <= yy; i1++) {
                if ((j <= i1 * 100 / yy) && j > ((i1 - 1) * 100 / yy)) {
                    b121 = a111.valueOf(i1);
                }
                a[i] = b121;
            }
        /*     if(0<=j&&j<33)
        { a[i] = '1' ;}
        if(33<=j&&j<66)
        { a[i] = '2' ;}
        if(66<=j&&j<100)
        { a[i] = '3' ; }
        if(75<=j&&j<100)
        { a[i] = '2' ;}
        if(60<=j&&j<86)
        { a[i] = '5' ;}
        if(86<=j&&j<=100)
        { a[i] = '6' ; } */
        }

        int k = 0;
//  BufferedReader  stdin21 = new BufferedReader( new FileReader("data10")) ;
        int uj = 0;
        FileWriter f0 = new FileWriter("data6");
        //  f0.write("10000") ;
        //  f0.write("\n") ;
        BufferedReader stdin21 = new BufferedReader(new FileReader("DATA10.txt"));
        //    String s = stdin21.readLine() ;
        String s61 = " ;";
        while ((s61 = stdin21.readLine()) != null) {
            Vector v1127 = new Vector(1, 1);
            StringTokenizer tokens1 = new StringTokenizer(s61, " ");

            while (tokens1.hasMoreTokens()) {
                pushstring(v1127, tokens1.nextToken());
            }


            //  BufferedReader  stdin21 = new BufferedReader( new FileReader("data10")) ;
            //  OutputStream f0 = new FileOutputStream("DATA100") ;
            //    FileWriter f0 = new FileWriter("data100") ;
            for (int h = 0; h < v1127.size(); h++) {
                f0.write((String) (v1127.elementAt(h)) + " ");
            }
            if (uj != 0) {
                f0.write(a[k]);
            }
            uj = uj + 1;
            k = k + 1;
            f0.write("\n");
        //    f0.println() ;
        }
        f0.close();
    /*   stdin21.writeLine
    int i ;
    FileInputStream fin ;
    FileOutputStream fout ;
    do {
    i = fin.read() ;
    if(i!=-1)
    foutwrite(i) ;
    } while(i!=-1) ;
    fin.close() ;
    fout.close() ; */


    /*  for(int i=1;i<2;i++)
    { for(int j=1;j<=50;j=j+5)
    { for(int kk=10;kk<=2000;kk=kk+100)
    {  double d1 = ((8.86*9*9)/(j*j))*kk*.1 ;
    //  System.out.println("length 9 * 9" + i + "mm" + "gap " + j + "micron " + "change in gap Ag." + kk + " Capacitance diferrence " + d1 + "ff");
    }} } */
long endTime = System.currentTimeMillis();
runtime = (double) (endTime - startTime);
    }

    public static void main(String[] argv) throws IOException {
        RanCluster ci = new RanCluster();
//  String a= new String() ;
        //   int c = 20 ;
        //   String b = a.valueOf(c);
        System.out.println(" enter the number of classes ");
        BufferedReader stdin2d9 = new BufferedReader(new InputStreamReader(System.in));
        String s6119 = stdin2d9.readLine();
        int yy = ((Integer.valueOf(s6119)).intValue());
        Random rand = new Random();
        String a[] = new String[10000];
        String a111 = new String();
        int j = 0;
        for (int i = 0; i < 10000; i++) {
            j = (int) (100 * rand.nextDouble());
            if (j != 100) {
                j = j + 1;
            }
            String b121 = ";";
            for (int i1 = 1; i1 <= yy; i1++) {
                if ((j <= i1 * 100 / yy) && j > ((i1 - 1) * 100 / yy)) {
                    b121 = a111.valueOf(i1);
                }
                a[i] = b121;
            }
        /*     if(0<=j&&j<33)
        { a[i] = '1' ;}
        if(33<=j&&j<66)
        { a[i] = '2' ;}
        if(66<=j&&j<100)
        { a[i] = '3' ; }
        if(75<=j&&j<100)
        { a[i] = '2' ;}
        if(60<=j&&j<86)
        { a[i] = '5' ;}
        if(86<=j&&j<=100)
        { a[i] = '6' ; } */
        }

        int k = 0;
//  BufferedReader  stdin21 = new BufferedReader( new FileReader("data10")) ;
        int uj = 0;
        FileWriter f0 = new FileWriter("data6");
        //  f0.write("10000") ;
        //  f0.write("\n") ;
        BufferedReader stdin21 = new BufferedReader(new FileReader("DATA10.txt"));
        //    String s = stdin21.readLine() ;
        String s61 = " ;";
        while ((s61 = stdin21.readLine()) != null) {
            Vector v1127 = new Vector(1, 1);
            StringTokenizer tokens1 = new StringTokenizer(s61, " ");

            while (tokens1.hasMoreTokens()) {
                ci.pushstring(v1127, tokens1.nextToken());
            }


            //  BufferedReader  stdin21 = new BufferedReader( new FileReader("data10")) ;
            //  OutputStream f0 = new FileOutputStream("DATA100") ;
            //    FileWriter f0 = new FileWriter("data100") ;
            for (int h = 0; h < v1127.size(); h++) {
                f0.write((String) (v1127.elementAt(h)) + " ");
            }
            if (uj != 0) {
                f0.write(a[k]);
            }
            uj = uj + 1;
            k = k + 1;
            f0.write("\n");
        //    f0.println() ;
        }
        f0.close();
    /*   stdin21.writeLine
    int i ;
    FileInputStream fin ;
    FileOutputStream fout ;
    do {
    i = fin.read() ;
    if(i!=-1)
    foutwrite(i) ;
    } while(i!=-1) ;
    fin.close() ;
    fout.close() ; */


    /*  for(int i=1;i<2;i++)
    { for(int j=1;j<=50;j=j+5)
    { for(int kk=10;kk<=2000;kk=kk+100)
    {  double d1 = ((8.86*9*9)/(j*j))*kk*.1 ;
    //  System.out.println("length 9 * 9" + i + "mm" + "gap " + j + "micron " + "change in gap Ag." + kk + " Capacitance diferrence " + d1 + "ff");
    }} } */
    }
}




