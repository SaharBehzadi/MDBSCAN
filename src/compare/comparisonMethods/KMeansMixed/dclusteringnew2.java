package compare.comparisonMethods.KMeansMixed;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

// programme for clustering all attributes for continouus attrinbute
public class dclusteringnew2 {
    double runtime;

    public dclusteringnew2() {
    }

    class node1 {

        Vector k, l;
        float p;

        node1(Vector t, float h) {
            k = t;

            l = new Vector();
            p = h;
        }
    }

    class node2 {

        int j2;

        node2(int h) {
            j2 = h;
        }
    }

    class node3 {

        int t1;
        float p1;

        node3(int i2, float p2) {
            t1 = i2;
            p1 = p2;
        }
    }

    class nodet1 {

        int t1;
        Vector v1;

        nodet1(int i2, Vector v2) {
            t1 = i2;
            v1 = v2;
        }
    }

    class nodet2 {

        String t2;
        Vector v2;

        nodet2(String i2, Vector v3) {
            t2 = i2;
            v2 = v3;
        }
    }

    class nodet3 {

        String t3;
        float v3;

        nodet3(String t4, float v4) {
            t3 = t4;
            v3 = v4;
        }
    }

    class nodet4 {

        int t4;
        Vector v40;
        Vector v50;

        nodet4(int t5, Vector v44, Vector v55) {
            t4 = t5;
            v40 = v44;
            v50 = v55;
        }
    }

    class node1g {

        Vector vr;

        node1g() {
            vr = new Vector();
        }
    }

    class node2g {

        Vector vr1;

        node2g() {
            vr1 = new Vector();
        }
    }

    public int Searcht1(String s1, Vector v1) {
        int j = 0;
        for (int k = 0; k < v1.size(); k++) {
            if ((((nodet2) (v1.elementAt(k))).t2).equals(s1)) {
                j = k;
            }
        }
        return j;
    }

    public float Searcht2(String s1, Vector v1) {
        float j = 0;
        for (int k = 0; k < v1.size(); k++) {
            if ((((nodet3) (v1.elementAt(k))).t3).equals(s1)) {
                j = (((nodet3) (v1.elementAt(k))).v3);
            }
        }
        return j;
    }

    /* Vector v contains node1 nodes. Initially it has n nodes if
    number of elements in set is n. It will grow as a tree and
    finally it will have (2**n - 1) nodes.
    Vector v1 contains node1 nodes. Initially it has n nodes if
    number of elements in set is n. Size of v1 will increase,
    finally it will have (2**n - 1) nodes.
    node1(subset(k) , probability(p) , children(l))
    v3 has n node3 nodes. node3(element of set, probability)*/
    public void sets(Vector v, Vector v1, Vector v2) {
        if (v.size() > 1) {
            for (int i = 0; i < v.size() - 1; i++) {
                for (int j = i + 1; j < v.size(); j++) {
                    Vector k1 = new Vector();
                    float p4 = 0;
                    int j10 = 0;

                    for (int l1 = 0; l1 < (((node1) (v.elementAt(i))).k).size(); l1++) {
                        k1.addElement((node2) ((((node1) (v.elementAt(i))).k).elementAt(l1)));
                    }
                    k1.addElement((node2) ((((node1) (v.elementAt(j))).k).lastElement()));
                    j10 = (((node2) ((((node1) (v.elementAt(j))).k).lastElement())).j2) - 1;
                    p4 = (((node1) (v.elementAt(i))).p) + (((node3) (v2.elementAt((j10)))).p1);
                    node1 z = this.new node1(k1, p4);

                    v1.addElement((node1) (z));
                    (((node1) (v.elementAt(i))).l).addElement((node1) (z));



                    int t = ((((node1) (v.elementAt(i))).k).size() - 1);
                }


                this.sets(((node1) (v.elementAt(i))).l, v1, v2);
            }
        } else {
            ;

        }

    }

    /* v1 is input vector. It has node1 nodes.for A(i,r)
    v3 has n node2 nodes. ( w + (-w) )
    v4 has node3 nodes for A(i,s)
    n number of elements in set.
    v5 has node1 nodes. (subset for A(i,r)=w , (p(i,r,w) + p(i,s,-w)) , children)*/
    public void fin(Vector v1, Vector v3, Vector v4, int n, Vector v5) {
        for (int i = 0; i < v1.size(); i++) {
            if (((((node1) (v1.elementAt(i))).k).size()) == n) {
                ;
            } else {
                Vector v10 = new Vector();
                for (int z = 0; z < v3.size(); z++) {
                    v10.addElement((node2) (v3.elementAt(z)));
                }
                for (int y = 0; y < ((((node1) (v1.elementAt(i))).k).size()); y++) {
                    v10.removeElement((node2) ((((node1) (v1.elementAt(i))).k).elementAt(y)));
                }
                float sum = (((node1) (v1.elementAt(i))).p);
                for (int k10 = 0; k10 < v10.size(); k10++) {
                    int j10 = ((node2) (v10.elementAt(k10))).j2;
                    sum = sum + ((node3) (v4.elementAt((j10 - 1)))).p1;
                }
                node1 z1 = new node1(((node1) (v1.elementAt(i))).k, sum);
                v5.addElement((node1) (z1));
            }
        }
    }

    public class node {

        String attr, res;
        int y, n, m;

        node(String t, String d) {
            attr = t;
            res = d;
            y = 0;
            m = 0;
            n = 0;
        }
    }

    class node5 {

        String attr1;
        Vector vec;

        node5(String t) {
            attr1 = t;
            vec = new Vector();
        }
    }

    class node11 {

        int nu;
        float pr;

        node11(int h, float g) {
            nu = h;
            pr = g;
        }
    }

    class node12 {

        float sig;
        Vector v12;

        node12() {
            v12 = new Vector();
            sig = 0;
        }
    }

    class node14 {

        String att;
        Vector v14;
        float sig1;

        node14(String s) {
            att = s;
            v14 = new Vector();
            sig1 = 0;
        }
    }

    class node6 {

        int no;
        float prob;

        node6() {
            no = 0;
            prob = 0;
        }
    }

    public int find1(int i, int[] c, int n) {
        int j = 0;
        for (int k = 0; k < n; k++) {
            if (i == c[k]) {
                j = 1;
            }
        }
        return j;
    }

    public void pushstring(Vector v7, String x) {
        v7.addElement(new String(x));
    }

    public void search(Vector v1, Vector v2) {

        int t = 0, k = 0;
        node5 z = this.new node5(((node) (v2.elementAt(0))).attr);
        v1.addElement(z);
        for (t = 1; t < v2.size(); t++) {
            for (int i = 0; i < v1.size(); i++) {
                if ((((node) v2.elementAt(t)).attr).equals(((node5) v1.elementAt(i)).attr1)) {
                    k = 1;
                }
            }
            if (k == 0) {
                node5 z1 = this.new node5(((node) (v2.elementAt(t))).attr);
                v1.addElement((node5) (z1));
            }
            k = 0;
        }
    }

    public void search1(Vector v1, Vector v2) {

        int t = 0, k = 0;
        v1.addElement((node) v2.elementAt(0));
        for (t = 1; t < v2.size(); t++) {
            for (int i = 0; i < v1.size(); i++) {
                if ((((node) v2.elementAt(t)).res).equals(((node) v1.elementAt(i)).res)) {
                    k = 1;
                }
            }
            if (k == 0) {
                v1.addElement((node) v2.elementAt(t));
            }
            k = 0;
        }
    }

    public int search3(String s, Vector v1) {
        int j = 0;
        for (int i = 0; i < v1.size(); i++) {
            if ((((node) v1.elementAt(i)).res).equals(s)) {
                j = i;
            }
        }
        return j;
    }

    public int search4(String s, Vector v) {
        int j = 0;
        for (int i = 0; i < v.size(); i++) {
            if (s.equals(((node14) (v.elementAt(i))).att)) {
                j = i;
            }
        }
        return j;
    }

    public float search5(int k, Vector v) {
        float j = 0;
        for (int i = 0; i < v.size(); i++) {
            if (((node11) (v.elementAt(i))).nu == k) {
                j = ((node11) (v.elementAt(i))).pr;
            }
        }
        return j;

    }

    public int maxi(double[] a, int c) {
        int k = 1;
        double max = 999990;
        for (int j = 1; j <= c; j++) {
            if (a[j] < max) {
                max = a[j];
                k = j;
            }
        }
        return k;
    }

    public void count(Vector v1, Vector v2, Vector v3) {
        for (int t = 0; t < v2.size(); t++) {
            for (int i = 0; i < v1.size(); i++) {
                if ((((node5) v2.elementAt(t)).attr1).equals(((node) v1.elementAt(i)).attr)) {
                    int k = search3(((node) v1.elementAt(i)).res, v3);
                    //if((((node)v1.elementAt(i)).res).equals("1"))
                    ((node6) ((((node5) v2.elementAt(t)).vec).elementAt(k))).no = ((node6) ((((node5) v2.elementAt(t)).vec).elementAt(k))).no + 1;
                }   /*            else
            if((((node)v1.elementAt(i)).res).equals("2"))
            ((node)v2.elementAt(t)).m=((node)v2.elementAt(t)).m+1 ;
            else
            ((node)v2.elementAt(t)).n=((node)v2.elementAt(t)).n+1 ;  */
            }
        }
    }

    public void sum1(Vector v1, Vector v2) {
        for (int t1 = 0; t1 < (((node5) (v1.elementAt(0))).vec).size(); t1++) {
            for (int t = 0; t < v1.size(); t++) {
                ((node6) (v2.elementAt(t1))).no = ((node6) (v2.elementAt(t1))).no + ((node6) ((((node5) (v1.elementAt(t))).vec).elementAt(t1))).no;
            }
        }
    }

    public void proba(Vector v1, Vector v2, Vector v3) {
        float sum = 0;
        float sum1 = 0;
        int t1 = 0;
        for (t1 = 0; t1 < v1.size(); t1++) {
            sum = sum + ((node6) (v1.elementAt(t1))).no;
        }
        //  System.out.println(sum) ;
        if (sum != 0) {
            for (t1 = 0; t1 < v1.size(); t1++) {
                ((node6) (v1.elementAt(t1))).prob = (((node6) (v1.elementAt(t1))).no) / sum;
            }
        }
        //     System.out.println( ((node6)(v1.elementAt(t1))).prob ) ; }
        for (t1 = 0; t1 < v2.size(); t1++) {
            ((node6) (v2.elementAt(t1))).no = ((node6) (v3.elementAt(t1))).no - ((node6) (v1.elementAt(t1))).no;
        }
        sum = 0;

        for (t1 = 0; t1 < v2.size(); t1++) {
            sum = sum + ((node6) (v2.elementAt(t1))).no;
        }
        //   System.out.println(sum) ;
        if (sum != 0) {
            for (t1 = 0; t1 < v2.size(); t1++) {
                ((node6) (v2.elementAt(t1))).prob = (((node6) (v2.elementAt(t1))).no) / sum;
            //  System.out.println( ((node6)(v2.elementAt(t1))).prob ) ;
            }
        }
    }

    public void probat1(Vector v1, Vector v2) {
        float sum = 0;
        float sum1 = 0;
        int t1 = 0;
        for (t1 = 0; t1 < v1.size(); t1++) {
            sum = sum + ((node6) (v1.elementAt(t1))).no;
        }
        //  System.out.println(sum) ;
        if (sum != 0) {
            for (t1 = 0; t1 < v1.size(); t1++) {
                ((node6) (v1.elementAt(t1))).prob = (((node6) (v1.elementAt(t1))).no) / sum;
            }
        }
        /*    //     System.out.println( ((node6)(v1.elementAt(t1))).prob ) ; }
        for( t1=0 ; t1 < v2.size() ; t1++)
        { ((node6)(v2.elementAt(t1))).no =    ((node6)(v3.elementAt(t1))).no -  ((node6)(v1.elementAt(t1))).no  ;
        }   */
        sum1 = 0;
        for (t1 = 0; t1 < v2.size(); t1++) {
            sum1 = sum1 + ((node6) (v2.elementAt(t1))).no;
        }
        //   System.out.println(sum) ;
        if (sum1 != 0) {
            for (t1 = 0; t1 < v2.size(); t1++) {
                ((node6) (v2.elementAt(t1))).prob = (((node6) (v2.elementAt(t1))).no) / sum1;
            //  System.out.println( ((node6)(v2.elementAt(t1))).prob ) ;
            }
        }
    }

    public float calwt(Vector v1, Vector v2) {
        float j = 0;
        for (int t1 = 0; t1 < v2.size(); t1++) {
            if ((((node6) (v1.elementAt(t1))).prob) >= ((((node6) (v2.elementAt(t1))).prob))) {
                node2 n2 = new node2((t1 + 1));
                j = j + (((node6) (v1.elementAt(t1))).prob);
            } else {
                j = j + (((node6) (v2.elementAt(t1))).prob);
            // System.out.println((((node6)(v2.elementAt(t1))).prob)+"P");
            }
        }
        return j;
    }

    public float calw(Vector v1, Vector v2, Vector v3) {
        float j = 0;
        for (int t1 = 0; t1 < v2.size(); t1++) {
            if ((((node6) (v1.elementAt(t1))).prob) > ((((node6) (v2.elementAt(t1))).prob))) {
                node2 n2 = new node2((t1 + 1));
                v3.addElement((node2) (n2));
                j = j + (((node6) (v1.elementAt(t1))).prob);
            } else {
                j = j + (((node6) (v2.elementAt(t1))).prob);
            }
        }
        return j;
    }

    public float max(Vector v1) {
        float j = 0;
        float k1 = 0;
        int k = 0;
        for (int i = 0; i < v1.size(); i++) {
            if ((((node1) (v1.elementAt(i))).p) > j) {
                j = ((node1) (v1.elementAt(i))).p;
                // System.out.println("j") ;

                //  System.out.println(j) ;
                k = i;
            }
        }
        k1 = j;
        return k1;
    }

    public int max1(Vector v1) {
        float j = 0;
        float k1 = 0;
        int k = 0;
        for (int i = 0; i < v1.size(); i++) {
            if ((((node1) (v1.elementAt(i))).p) > j) {
                j = ((node1) (v1.elementAt(i))).p;
                // System.out.println("j") ;

                //  System.out.println(j) ;
                k = i;
            }
        }
        k1 = j;
        return k;
    }

    public void coun1t(Vector v1, Vector v2, Vector v3) {
        for (int t = 0; t < v2.size(); t++) {
            for (int i = 0; i < v1.size(); i++) {
                if ((((node) v2.elementAt(t)).attr).equals(((node) v1.elementAt(i)).attr)) {
                    if ((((node) v1.elementAt(i)).res).equals("1")) {
                        ((node) v2.elementAt(t)).y = ((node) v2.elementAt(t)).y + 1;
                    } else if ((((node) v1.elementAt(i)).res).equals("2")) {
                        ((node) v2.elementAt(t)).m = ((node) v2.elementAt(t)).m + 1;
                    } else {
                        ((node) v2.elementAt(t)).n = ((node) v2.elementAt(t)).n + 1;
                    }
                }
            }
        }
    }

    public void run() throws IOException {
        long startTime = System.currentTimeMillis();
        dclusteringnew2 ci = new dclusteringnew2();
        float[][] l1 = new float[100][100];
        BufferedReader stdin2e = new BufferedReader(new FileReader("data6"));
        //    String s61121 = stdin2.readLine() ;
        String s611e = stdin2e.readLine();
        String s61111e = stdin2e.readLine();
        Vector v112e = new Vector(1, 1);
        Vector v1127e = new Vector();
        StringTokenizer tokens11e = new StringTokenizer(s611e, " ");

        while (tokens11e.hasMoreTokens()) {
            ci.pushstring(v112e, tokens11e.nextToken());
        }

        StringTokenizer tokens111e = new StringTokenizer(s61111e, " ");
        while (tokens111e.hasMoreTokens()) {
            ci.pushstring(v1127e, tokens111e.nextToken());
        }

        //    System.out.println(v112.size());
        int[] yye = new int[(v112e.size())];
        for (int hg = 0; hg < (v112e.size()); hg++) {
            yye[hg] = -1;
        }
        float[][] l1e = new float[(v1127e.size() - 1)][2];
        for (int hg1 = 0; hg1 < (v112e.size()); hg1++) {
            String x88e = (String) v112e.elementAt(hg1);
            yye[hg1] = ((Integer.valueOf(x88e)).intValue()) - 1;
        }


        for (int g1 = 0; g1 < (v1127e.size() - 1); g1++) {  //    System.out.println("g" + g1) ;
            //    node12  am  = ci.new node12() ;
            //   dv.addElement((node12)(am)) ;
            //  String x8 = (String)v112.elementAt(g1) ;
            //   int ind11 = (Integer.valueOf(x8)).intValue() ;
            float mine = 999999999;
            float maxe = -99999999;


            Vector v2e = new Vector(1, 1);
            Vector v4e = new Vector(1, 1);

            if (ci.find1(g1, yye, (v112e.size())) != 0) {        //   System.out.println("O");
                FileInputStream fil1e = new FileInputStream("data6");
                BufferedReader stdin1e = new BufferedReader(new FileReader("data6"));
                String s111e = stdin1e.readLine();
                /*    int ind11 = (Integer.valueOf(s111)).intValue() ;
                //    String   s211 = stdin1.readLine() ;
                int ind21e = (Integer.valueOf(s211e)).intValue() ; */
                String s61e = ";";
                //do
                while ((s61e = stdin1e.readLine()) != null) {

                    Vector v111e = new Vector(1, 1);

                    //         st = s6 ;
                    // if(!st.equals("quit"))
                    StringTokenizer tokens2e = new StringTokenizer(s61e, " ");

                    while (tokens2e.hasMoreTokens()) {
                        ci.pushstring(v111e, tokens2e.nextToken());
                    }
                    //  int k341 = ind11 - 1 ;
                    //    System.out.println(v111.size());
                    String xe = (String) v111e.elementAt(g1);
                    float f12e = (Float.valueOf(xe)).floatValue();
                    if (f12e > maxe) {
                        maxe = f12e;
                    }
                    if (f12e < mine) {
                        mine = f12e;
                    }

                }
            }


            l1[g1][0] = maxe;
            l1[g1][1] = mine;

        }


        FileWriter f00e = new FileWriter("data6b");
        f00e.write("1000");
        f00e.write("\r\n");
        FileInputStream fil21e = new FileInputStream("data6");
        BufferedReader stdin21e = new BufferedReader(new FileReader("data6"));
        //   String s6112 = stdin21.readLine() ;
        //      div = (Integer.valueOf(s6112)).intValue() ;
        String s611110e = stdin21e.readLine();
        String s6e = ";";
        while ((s6e = stdin21e.readLine()) != null) {

            Vector v1e = new Vector(1, 1);

            //         st = s6 ;
            // if(!st.equals("quit"))
            StringTokenizer tokense = new StringTokenizer(s6e, " ");

            while (tokense.hasMoreTokens()) {
                ci.pushstring(v1e, tokense.nextToken());
            }

            Vector v1441e = new Vector();

            for (int hyi = 0; hyi < (v1e.size() - 1); hyi++) {
                if (ci.find1(hyi, yye, v112e.size()) != 0) {
                    String x12e = (String) (v1e.elementAt(hyi));
                    float f121e = (Float.valueOf(x12e)).floatValue();
                    float m11e = (((f121e - l1[hyi][1]) * 6) / (l1[hyi][0] - l1[hyi][1]));
                    int m1e = (int) m11e;
                    String m81e = ";"; //((INTEGER)(m1)).toString() ;
                    if (m1e == 0) {
                        m81e = "0";
                    }
                    if (m1e == 1) {
                        m81e = "1";
                    }
                    if (m1e == 2) {
                        m81e = "2";
                    }
                    if (m1e == 3) {
                        m81e = "3";
                    }
                    if (m1e == 4) {
                        m81e = "4";
                    }
                    if (m1e > 4) {
                        m81e = "5";
                    }
                    /*    if(m1==6)
                    { m81 = "6" ;}
                    if(m1==7)
                    { m81 = "7" ;}
                    if(m1==8)
                    { m81 = "8" ;}
                    if(m1==9)
                    { m81 = "9" ;}
                    if(m1==10)
                    { m81 = "10" ;}
                    if(m1==11)
                    { m81 = "11" ;}
                    if(m1==12)
                    { m81 = "12" ;}

                    if(m1==13)
                    { m81 = "13" ;}
                    if(m1==14)
                    { m81 = "14" ;}
                    if(m1==15)
                    { m81 = "15" ;}
                    if(m1==16)
                    { m81 = "16" ;}
                    if(m1==17)
                    { m81 = "17" ;}
                    if(m1==18)
                    { m81 = "18" ;}
                    if(m1==19)
                    { m81 = "19" ;}
                    if(m1==20)
                    { m81 = "20" ;}

                    if(m1==21)
                    { m81 = "21" ;}
                    if(m1==22)
                    { m81 = "22" ;}
                    if(m1==23)
                    { m81 = "23" ;}
                    if(m1==24)
                    { m81 = "24" ;}
                    if(m1==25)
                    { m81 = "25" ;}
                    if(m1>25)
                    { m81 = "26" ;}    */

                    v1441e.addElement(m81e);
                } else {
                    v1441e.addElement((String) (v1e.elementAt(hyi)));
                }
            }






            /*      for(int i=0; i<v1441.size()-1;i++)
            {  for(int j=i+1; j<v1441e.size();j++)
            { System.out.print((String)v1441.elementAt(i) + ";");
            System.out.print((String)v1441.elementAt(j) + " ");
            } }  */

            for (int i = 0; i < v1441e.size(); i++) {
                f00e.write((String) v1441e.elementAt(i) + " ");
            }
            f00e.write((String) v1e.elementAt(v1e.size() - 1));
            f00e.write("\r\n");
        }
        f00e.close();


















        double[] sig = new double[100];
        String st = "quit1";
        Vector dv = new Vector();
        Vector dv1 = new Vector();
        float[][] ddd = new float[25][50];
        float[][] ddd2 = new float[25][50];
        float[] ddd1 = new float[100];
        Vector ttv4 = new Vector();
        Vector ttv2 = new Vector();
        float fo[] = new float[100];

        long start1 = System.currentTimeMillis();
        int l2 = 0;
        int div = 0;

        FileInputStream fil21 = new FileInputStream("data6b");
        BufferedReader stdin21 = new BufferedReader(new FileReader("data6b"));
        //   String s6112 = stdin21.readLine() ;
        //      div = (Integer.valueOf(s6112)).intValue() ;
        String s6111 = stdin21.readLine();
        String s6113 = stdin21.readLine();
        Vector v1127 = new Vector(1, 1);
        StringTokenizer tokens1 = new StringTokenizer(s6113, " ");

        while (tokens1.hasMoreTokens()) {
            ci.pushstring(v1127, tokens1.nextToken());
        }
        //   System.out.println(v1127.size());
        FileInputStream fil2 = new FileInputStream("data6b");
        BufferedReader stdin2 = new BufferedReader(new FileReader("data6b"));
        //    String s61121 = stdin2.readLine() ;
        String s611 = stdin2.readLine();
        Vector v112 = new Vector(1, 1);
        StringTokenizer tokens11 = new StringTokenizer(s611, " ");

        while (tokens11.hasMoreTokens()) {
            ci.pushstring(v112, tokens11.nextToken());
        }
        //    System.out.println(v112.size());
        int[] yy = new int[(v112.size())];
        for (int hg = 0; hg < (v112.size()); hg++) {
            yy[hg] = -1;
        }
        //    float[][] l1 = new float[(v1127.size()-1)][2] ;
        for (int hg1 = 0; hg1 < (v112.size()); hg1++) {
            String x88 = (String) v112.elementAt(hg1);
            yy[hg1] = ((Integer.valueOf(x88)).intValue()) - 1;
        }

        Vector nnn = new Vector();
        Vector nnn1 = new Vector();
        Vector nnn2 = new Vector();
        Vector nnn3 = new Vector();
        for (int uui = 0; uui < (v1127.size() - 1); uui++) {
            node1g nodex = ci.new node1g();
            nnn.addElement(nodex);
        }
        for (int uui = 0; uui < (v1127.size() - 1); uui++) {
            for (int uui1 = 0; uui1 < (v1127.size() - 1); uui1++) {
                node2g nodex = ci.new node2g();
                (((node1g) (nnn.elementAt(uui))).vr).addElement(nodex);
            }
        }
        for (int uui = 0; uui < (v1127.size() - 1); uui++) {
            node1g nodex = ci.new node1g();
            nnn1.addElement(nodex);
        }
        for (int uui = 0; uui < (v1127.size() - 1); uui++) {
            node1g nodex = ci.new node1g();
            nnn2.addElement(nodex);
        }
        for (int uui = 0; uui < (v1127.size() - 1); uui++) {
            node1g nodex = ci.new node1g();
            nnn3.addElement(nodex);
        }






        FileInputStream ffil = new FileInputStream("data6b");
        // BufferedReader  stdin8 = new BufferedReader( new InputStreamReader(fil)) ;
        BufferedReader stdin8 = new BufferedReader(new FileReader("data6b"));
        String s9 = stdin8.readLine();
        //   int ind1 = (Integer.valueOf(s)).intValue() ;
        //   String   s29 = stdin8.readLine() ;
      /*   int ind2 = (Integer.valueOf(s2)).intValue() ; */
        String s69 = ";";
        //do
        while ((s69 = stdin8.readLine()) != null) {

            Vector v19 = new Vector(1, 1);

            //         st = s6 ;
            // if(!st.equals("quit"))
            StringTokenizer tokens12 = new StringTokenizer(s69, " ");

            while (tokens12.hasMoreTokens()) {
                ci.pushstring(v19, tokens12.nextToken());
            }
            //  int k34 = ind11 - 1 ;
            //  System.out.println(v19.size());
            for (int g15 = 0; g15 < (v1127.size() - 1); g15++) {
                for (int g1 = 0; g1 < (v1127.size() - 1); g1++) {
                    String x131 = (String) v19.elementAt(g15);
                    int g101 = v19.size() - 1;
                    String x1 = (String) v19.elementAt(g101);
                    String v1 = (String) v19.elementAt(g1);
                    //     System.out.println("X!"+ x1);
                    {
                        node n1 = ci.new node(x131, v1);
                        (((node2g) (((Vector) (((node1g) (nnn.elementAt(g15))).vr)).elementAt(g1))).vr1).addElement(n1);
                    // v2.addElement(n1) ;
                    }

                    if (g1 == 0) {
                        {
                            node n12 = ci.new node(x131, x1);
                            ((Vector) (((node1g) (nnn1.elementAt(g15))).vr)).addElement(n12);
                        // v22.addElement(n12) ;
                        }

                        node n11 = ci.new node(x131, x1);
                        ((Vector) (((node1g) (nnn2.elementAt(g15))).vr)).addElement(n11);
                        //  v21.addElement(n11) ;

                        {
                            node n1w = ci.new node(x1, x131);
                            ((Vector) (((node1g) (nnn3.elementAt(g15))).vr)).addElement(n1w);
                        // v2w.addElement(n1w) ;
                        }

                    }
                }
            }
        }

        for (int g15 = 0; g15 < (v1127.size() - 1); g15++) {
            Vector ttv1 = new Vector();


            for (int g1 = 0; g1 < (v1127.size() - 1); g1++) {   //   System.out.println("g" + g1) ;
                node12 am = ci.new node12();
                dv.addElement((node12) (am));
                //  String x8 = (String)v112.elementAt(g1) ;
                //   int ind11 = (Integer.valueOf(x8)).intValue() ;


                Vector v21 = (Vector) (((node2g) (((Vector) (((node1g) (nnn.elementAt(g15))).vr)).elementAt(g1))).vr1);
                Vector v333 = new Vector(1, 1);
                Vector v4 = new Vector(1, 1);
                Vector v2w = ((Vector) (((node1g) (nnn1.elementAt(g15))).vr));
                Vector v4w = new Vector(1, 1);
                Vector v2 = ((Vector) (((node1g) (nnn2.elementAt(g15))).vr));
                Vector v41 = new Vector(1, 1);
                Vector v22 = ((Vector) (((node1g) (nnn1.elementAt(g15))).vr));
                Vector v42 = new Vector(1, 1);


                {






                    // while (!st.equals("quit")) ;
                    Vector v5 = new Vector();
                    Vector v5w = new Vector();
                    Vector v51 = new Vector();
                    Vector v52 = new Vector();
                    ci.search(v4, v2);
                    ci.search(v4w, v2w);
                    ci.search(v41, v21);
                    ci.search(v42, v22);
                    for (int gy = 0; gy < v4.size(); gy++) {
                        String giu = ((node5) (v4.elementAt(gy))).attr1;

                        node14 nnode1 = ci.new node14(giu);
                        (((node12) (dv.elementAt(g1))).v12).addElement((node14) (nnode1));
                    }
                    ci.search1(v5, v2);
                    ci.search1(v5w, v2w);
                    ci.search1(v51, v21);
                    ci.search1(v52, v22);
                    /*      for(int iu=0;iu<v2.size();iu++)
                    { for(int grr=0;grr<v5.size();grr++)
                    { if((((node)v2.elementAt(iu)).res).equals(((node)v5.elementAt(grr)).res))
                    { String x131 = (String)(((node)v333.elementAt(iu)).attr) ;
                    float  f121 = (Float.valueOf(x131)).floatValue() ;
                    //      System.out.println(f121);
                    ddd[g1][grr]=ddd[g1][grr] + f121 ;
                    ddd2[g1][grr] = ddd2[g1][grr] + 1 ; }
                    }
                    }

                    for( int iii=0;iii<30;iii++)
                    {  if(ddd2[g1][iii]!=0)
                    ddd[g1][iii]= (ddd[g1][iii])/(ddd2[g1][iii]) ;
                    }
                    for( int iii=0;iii<4;iii++)
                    { System.out.println(ddd[g1][iii]);
                    } */



                    if (g15 == 0) {
                        if (g1 == 0) {
                            ci.search1(dv1, v2);
                        }
                    }
                    Vector v6 = new Vector();
                    Vector v7 = new Vector();

                    for (int t4 = 0; t4 < v4.size(); t4++) {
                        for (int t11 = 0; t11 < v5.size(); t11++) {
                            node6 z10 = ci.new node6();
                            (((node5) (v4.elementAt(t4))).vec).addElement((node6) (z10));
                        }
                    }

                    for (int t4 = 0; t4 < v4w.size(); t4++) {
                        for (int t11 = 0; t11 < v5w.size(); t11++) {
                            node6 z10 = ci.new node6();
                            (((node5) (v4w.elementAt(t4))).vec).addElement((node6) (z10));
                        }
                    }
                    for (int t4 = 0; t4 < v41.size(); t4++) {
                        for (int t11 = 0; t11 < v51.size(); t11++) {
                            node6 z10 = ci.new node6();
                            (((node5) (v41.elementAt(t4))).vec).addElement((node6) (z10));
                        }
                    }

                    /*       for (int t111=0 ; t111 < v5w.size() ; t111++ )
                    { node6  z101 = ci.new node6() ;
                    v6.addElement((node6)(z101)) ;
                    v7.addElement((node6)(z101)) ;
                    }  */

                    for (int t4 = 0; t4 < v42.size(); t4++) {
                        for (int t11 = 0; t11 < v52.size(); t11++) {
                            node6 z10 = ci.new node6();
                            (((node5) (v42.elementAt(t4))).vec).addElement((node6) (z10));
                        }
                    }

                    for (int t111 = 0; t111 < v52.size(); t111++) {
                        node6 z101 = ci.new node6();
                        v6.addElement((node6) (z101));
                        v7.addElement((node6) (z101));
                    }




                    //    System.out.println((((node5)(v4.elementAt(0))).vec).size()) ;
                    //  System.out.println(v6.size()) ;
                    //      System.out.println(v7.size()) ;


                    ci.count(v2, v4, v5);
                    ci.count(v2w, v4w, v5w);
                    ci.count(v21, v41, v51);
                    ci.count(v22, v42, v52);
                    if (g1 == 0) {
                        nodet4 rtr = ci.new nodet4(g1, v4, v4w);
                        ttv2.addElement((nodet4) (rtr));
                    }
                    Vector vvv = new Vector();
                    for (int x11 = 0; x11 < v41.size(); x11++) {
                        Vector vvv1 = new Vector();
                        for (int z1 = 0; z1 < v41.size(); z1++) {
                            ci.probat1((((node5) (v41.elementAt(x11))).vec), (((node5) (v41.elementAt(z1))).vec));
                            float sss1 = ci.calwt((((node5) (v41.elementAt(x11))).vec), (((node5) (v41.elementAt(z1))).vec)) - 1;
                            //   System.out.println(sss1) ;
                            //    System.out.println(g1+"g1") ;
                            nodet3 ggh = ci.new nodet3(((node5) (v41.elementAt(z1))).attr1, sss1);
                            vvv1.addElement((nodet3) (ggh));
                        }
                        nodet2 ggh1 = ci.new nodet2(((node5) (v41.elementAt(x11))).attr1, vvv1);
                        vvv.addElement((nodet2) (ggh1));
                    }
                    nodet1 ggh11 = ci.new nodet1(g1, vvv);
                    ttv1.addElement((nodet1) (ggh11));


                    ci.sum1(v42, v6);
                    float sum = 0;
                    float sum1 = 0;
                    if (g1 == 0) {
                        for (int x1 = 0; x1 < v42.size(); x1++) {
                            int s1 = 1;
                            Vector y1 = new Vector();
                            Vector y2 = new Vector();
                            Vector y3 = new Vector();
                            Vector y4 = new Vector();
                            Vector y6 = new Vector();
                            Vector y8 = new Vector();
                            Vector y9 = new Vector();
                            Vector y10 = new Vector();
                            for (int t115 = 0; t115 < v52.size(); t115++) {
                                node6 z105 = ci.new node6();
                                y6.addElement((node6) (z105));
                            }
                            ci.proba((((node5) (v42.elementAt(x1))).vec), y6, v6);
                            for (int s21 = 0; s21 < v6.size(); s21++) {
                                node2 n1 = ci.new node2(s1);
                                y1.addElement((node2) (n1));
                                float f1 = ((node6) ((((node5) (v42.elementAt(x1))).vec).elementAt(s21))).prob;
                                node3 n2 = ci.new node3(s1, f1);
                                y2.addElement((node3) (n2));
                                Vector y5 = new Vector();
                                y5.addElement((node2) (n1));
                                node1 n3 = ci.new node1(y5, f1);
                                y3.addElement((node1) (n3));
                                y4.addElement((node1) (n3));
                                s1 = s1 + 1;
                            }
                            //   ci.sets(y3,y4 ,y2) ;
  /*  for(int i=0;i<y4.size();i++)
                            { {for(int i1=0;i1<(((node1)(y4.elementAt(i))).k).size();i1++)
                            { //System.out.println((((node1)(n11.elementAt(i))).k).size());
                            System.out.print(((node2)((((node1)(y4.elementAt(i))).k).elementAt(i1))).j2) ;}}
                            System.out.print(" ") ;
                            System.out.print(((node1)(y4.elementAt(i))).p);
                            System.out.print(" ") ;} */


                            Vector y7 = new Vector();
                            int s5 = 1;
                            //     ci.proba((((node5)(v4.elementAt(x1))).vec),y6,v6) ;
                            // System.out.println(y7.si) ;

                            for (int s3 = 0; s3 < v6.size(); s3++) {
                                float f3 = ((node6) (y6.elementAt(s3))).prob;
                                //   System.out.println(f3) ;
                                node3 n4 = ci.new node3(s5, f3);
                                y8.addElement((node3) (n4));
                                s5 = s5 + 1;
                            }
                            //  ci.fin(y4,y1,y8,v6.size(),y9) ;
                            float sss = ci.calw((((node5) (v42.elementAt(x1))).vec), y6, y7);
                            sum1 = sum1 + sss;
                            for (int h11 = 0; h11 < y7.size(); h11++) {
                                int h2 = ((node2) (y7.elementAt(h11))).j2 - 1;
                                float gr = ((node6) ((((node5) (v42.elementAt(x1))).vec).elementAt(h2))).prob;
                                int h5 = h2 + 1;
                                node11 nnode11 = ci.new node11(h5, gr);
                                (((node14) ((((node12) (dv.elementAt(g1))).v12).elementAt(x1))).v14).addElement(nnode11);
                                (((node14) ((((node12) (dv.elementAt(g1))).v12).elementAt(x1))).sig1) = sss;
                            }
                            int x2 = 0;
                            float x3 = 0;
                            //  x3 = ci.max(y9) ;
                            //   x2 = ci.max1(y9) ;
                            sum = sum + x3;
                        /* System.out.println("attribute value1"+ g15) ;
                        System.out.println("attribute value") ;
                        System.out.println(((node5)v42.elementAt(x1)).attr1) ;
                        System.out.println("max") ;
                        System.out.println("w  p(i,r,w) + p(i,-r,-w)") ;
                        for(int h11=0;h11<y7.size() ; h11++)
                        { int h2 = ((node2)(y7.elementAt(h11))).j2 - 1 ;
                        System.out.print(((node)(v52.elementAt(h2))).res+" ") ; }
                        System.out.print("  " ) ;
                        System.out.println(ci.calw((((node5)(v42.elementAt(x1))).vec),y6,y7)) ;
                        // System.out.println("attribute value") ;
                        //System.out.println(((node5)(v4.elementAt(x1))).attr1) ;
                        for(int i=0;i<y9.size();i++)
                        { {for(int i1=0;i1<(((node1)(y9.elementAt(i))).k).size();i1++)
                        { //System.out.println((((node1)(n11.elementAt(i))).k).size());
                        int g101 = (((node2)((((node1)(y9.elementAt(i))).k).elementAt(i1))).j2) - 1 ;
                        System.out.print(((node)(v52.elementAt(g101))).res) ; }}
                        // System.out.print(((node2)((((node1)(y9.elementAt(i))).k).elementAt(i1))).j2) ;}}
                        System.out.print(" ") ;
                        System.out.print(((node1)(y9.elementAt(i))).p);
                        System.out.print(" ") ;}
                        System.out.println("") ;   */



                        /* for(int i1=0;i1<(((node1)(y9.elementAt(x2))).k).size();i1++)
                        { //System.out.println((((node1)(n11.elementAt(i))).k).size());
                        int j10 = (((node2)((((node1)(y9.elementAt(x2))).k).elementAt(i1))).j2) - 1 ;
                        System.out.print(((node)(v5w.elementAt(j10))).res) ; }      // System.out.print(((node2)((((node1)(y9.elementAt(x2))).k).elementAt(i1))).j2) ;}
                        System.out.print(" ") ;
                        System.out.print(x3);
                        System.out.print(" ") ;
                        System.out.println(" ") ; */

                        }


                        /*
                        for(int t=0 ; t < v7.size() ; t++ )
                        {   System.out.println( ((node6)((((node5)(v4.elementAt(0))).vec).elementAt(t))).prob); }

                        for(int t=0 ; t < v7.size() ; t++ )
                        { System.out.println( ((node6)(v7.elementAt(t))).prob ) ;
                        }   */

                        /*    for (int t3=0 ; t3 < v4.size() ; t3++ )
                        { for(int t1=0 ; t1 < (((node5)(v4.elementAt(t3))).vec).size() ; t1++)
                        { System.out.println( ((node6)((((node5)(v4.elementAt(t3))).vec).elementAt(t1))).no );
                        }}  */
                        // System.out.println("Attribute") ;

                        //   System.out.println((g1+1)) ;
                        //   System.out.println("SIGNIFICANCE");
                        //    System.out.println(((sum1/(v42.size()))-1));
                        ((node12) (dv.elementAt(g1))).sig = ((sum1 / (v42.size())) - 1);
                        fo[g15] = ((sum1 / (v42.size())) - 1);
                    }
                }
            }    //   System.out.println("k") ;
            Vector vr = new Vector();
            float frr = 0;
            //    System.out.println((((nodet1)(ttv1.elementAt(0))).v1).size());
            for (int u1 = 0; u1 < (((nodet1) (ttv1.elementAt(0))).v1).size(); u1++) {
                Vector vr1 = new Vector();
                for (int u2 = 0; u2 < (((nodet2) ((((nodet1) (ttv1.elementAt(0))).v1).elementAt(0))).v2).size(); u2++) {
                    float sume = 0;
                    for (int u3 = 0; u3 < (ttv1).size(); u3++) {
                        sume = sume + ((nodet3) ((((nodet2) ((((nodet1) (ttv1.elementAt(u3))).v1).elementAt(u1))).v2).elementAt(u2))).v3;
                    }
                    if (sume != 0) {
                        //            sume = (sume-((((nodet1)(ttv1.elementAt(0))).v1).size()-1)/(((nodet1)(ttv1.elementAt(0))).v1).size())/((ttv1).size()-1)  ;
                    }
                    sume = sume / ((ttv1).size());
                    frr = frr + sume;
                    nodet3 dt3 = ci.new nodet3(((nodet3) ((((nodet2) ((((nodet1) (ttv1.elementAt(0))).v1).elementAt(u1))).v2).elementAt(u2))).t3, sume);
                    vr1.addElement(dt3);
                }
                nodet2 dt2 = ci.new nodet2(((nodet2) ((((nodet1) (ttv1.elementAt(0))).v1).elementAt(u1))).t2, vr1);
                vr.addElement(dt2);
            }
            nodet1 dt3 = ci.new nodet1(0, vr);
            ttv4.addElement(dt3);
            frr = frr / (((((nodet1) (ttv1.elementAt(0))).v1).size()) * ((((nodet1) (ttv1.elementAt(0))).v1).size() - 1));
            sig[g15] = frr;
        //      System.out.println("am" + frr) ;
        }

        double jk = 100;
        int jk1 = 0;

        while ((jk != 0) && (jk1 < 100)) {
            //    double[]    sig = new double[100] ;
  /*     sig[0] = .26 ;
            sig[3] = .32 ;
            sig[4] = .38 ;
            sig[7] = .63 ;
            sig[9] = .37 ;
            sig[1] = .0 ;
            sig[2] = .25 ;
            sig[6] = .40 ;
            sig[5] = .25 ;
            sig[7] = .32 ;
            sig[10] = .39 ;
            sig[12] = .42 ;
            sig[13] = .53 ; */

            float[][] dddd = new float[100][100];
            float[][] dddd1 = new float[100][100];
            float[][] dddd2 = new float[100][100];

            BufferedReader stdin210 = new BufferedReader(new FileReader("data6"));
            //   String s61120 = stdin210.readLine() ;
            //      div = (Integer.valueOf(s6112)).intValue() ;
            String s61110 = stdin210.readLine();
            String s61130 = stdin210.readLine();
            Vector v11270 = new Vector(1, 1);
            StringTokenizer tokens10 = new StringTokenizer(s61130, " ");

            while (tokens10.hasMoreTokens()) {
                ci.pushstring(v11270, tokens10.nextToken());
            }
            System.out.println(v11270.size());
            //   //   FileInputStream fil20 = new FileInputStream("data6a");
            BufferedReader stdin20 = new BufferedReader(new FileReader("DATA10.txt"));
            //    String s61121 = stdin2.readLine() ;
            String s6110 = stdin20.readLine();
            Vector v1120 = new Vector(1, 1);
            StringTokenizer tokens110 = new StringTokenizer(s6110, " ");

            while (tokens110.hasMoreTokens()) {
                ci.pushstring(v1120, tokens110.nextToken());
            }
            System.out.println(v1120.size() + " l");
            int[] yy0 = new int[(v1120.size())];
            for (int hg = 0; hg < (v112.size()); hg++) {
                yy0[hg] = -1;
            }
            float[][] l10 = new float[(v11270.size() - 1)][2];
            for (int hg1 = 0; hg1 < (v1120.size()); hg1++) {
                String x880 = (String) v1120.elementAt(hg1);
                yy0[hg1] = ((Integer.valueOf(x880)).intValue()) - 1;
            }







            Vector see = new Vector();
            Vector see1 = new Vector();
            Vector see2 = new Vector();
            Vector see3 = new Vector();
            for (int uui = 0; uui < (v1127.size() - 1); uui++) {
                node1g nodex = ci.new node1g();
                see.addElement(nodex);
            }
            for (int uui = 0; uui < (v1127.size() - 1); uui++) {
                node1g nodex = ci.new node1g();
                see1.addElement(nodex);
            }
            for (int uui = 0; uui < (v1127.size() - 1); uui++) {
                node1g nodex = ci.new node1g();
                see2.addElement(nodex);
            }
            for (int uui = 0; uui < (v1127.size() - 1); uui++) {
                node1g nodex = ci.new node1g();
                see3.addElement(nodex);
            }


            Vector ttv2d = new Vector();
            Vector dv1d = new Vector();
            /*   for(int g1d=0 ; g1d<(v1127.size()-1) ; g1d++)
            {   //   System.out.println("g" + g1d) ;

            Vector v2d = new Vector(1,1) ;
            Vector v333d = new Vector(1,1) ;
            Vector v4d = new Vector(1,1) ;
            Vector v2wd = new Vector(1,1) ;
            Vector v4wd = new Vector(1,1) ;
            Vector v21d = new Vector(1,1) ;
            Vector v41d = new Vector(1,1) ;
            Vector v22d = new Vector(1,1) ;
            Vector v42d = new Vector(1,1) ; */

            //  FileInputStream ffil = new FileInputStream("data6");
            // BufferedReader  stdin8 = new BufferedReader( new InputStreamReader(fil)) ;
            BufferedReader stdin8d = new BufferedReader(new FileReader("data6"));
            String s9d = stdin8d.readLine();
            //   int ind1 = (Integer.valueOf(s)).intValue() ;
            //   String   s29 = stdin8.readLine() ;
      /*   int ind2 = (Integer.valueOf(s2)).intValue() ; */
            String s69d = ";";
            //do
            while ((s69d = stdin8d.readLine()) != null) {

                Vector v19d = new Vector(1, 1);

                //         st = s6 ;
                // if(!st.equals("quit"))
                StringTokenizer tokens12d = new StringTokenizer(s69d, " ");

                while (tokens12d.hasMoreTokens()) {
                    ci.pushstring(v19d, tokens12d.nextToken());
                }
                //  int k34 = ind11 - 1 ;
                //   System.out.println(v19d.size());
                for (int g1d = 0; g1d < (v1127.size() - 1); g1d++) {
                    String x131d = (String) v19d.elementAt(g1d);
                    int g101d = v19d.size() - 1;
                    String x1d = (String) v19d.elementAt(g101d);
                    String v1d = (String) v19d.elementAt(g1d);
                    //     System.out.println("X!"+ x1);
                    {
                        node n1d = ci.new node(x131d, x1d);
                        ((Vector) (((node1g) (see.elementAt(g1d))).vr)).addElement(n1d);
                    //  v2d.addElement(n1d) ;
                    }

                    node n12d = ci.new node(x131d, x1d);
                    //    v22d.addElement(n12d) ; }
                    ((Vector) (((node1g) (see1.elementAt(g1d))).vr)).addElement(n12d);
                    node n11d = ci.new node(x131d, v1d);
                    ((Vector) (((node1g) (see2.elementAt(g1d))).vr)).addElement(n11d);
                    // v21d.addElement(n11d) ;

                    {
                        node n1wd = ci.new node(x1d, x131d);
                        ((Vector) (((node1g) (see3.elementAt(g1d))).vr)).addElement(n1wd);
                    //   v2wd.addElement(n1wd) ;
                    }
                }

            }



            for (int g1d = 0; g1d < (v1127.size() - 1); g1d++) {   //   System.out.println("g" + g1d) ;

                Vector v2d = (Vector) (((node1g) (see.elementAt(g1d))).vr);
                //   System.out.println(v2d.size()) ;
                Vector v333d = new Vector(1, 1);
                Vector v4d = new Vector(1, 1);
                Vector v2wd = (Vector) (((node1g) (see3.elementAt(g1d))).vr);
                Vector v4wd = new Vector(1, 1);
                Vector v21d = (Vector) (((node1g) (see2.elementAt(g1d))).vr);
                Vector v41d = new Vector(1, 1);
                Vector v22d = (Vector) (((node1g) (see1.elementAt(g1d))).vr);
                Vector v42d = new Vector(1, 1);


                // while (!st.equals("quit")) ;
                Vector v5d = new Vector();
                Vector v5wd = new Vector();
                Vector v51d = new Vector();
                Vector v52d = new Vector();
                ci.search(v4d, v2d);
                ci.search(v4wd, v2wd);
                ci.search(v41d, v21d);
                ci.search(v42d, v22d);
                ci.search1(v5d, v2d);
                ci.search1(v5wd, v2wd);
                ci.search1(v51d, v21d);
                ci.search1(v52d, v22d);
                if ((ci.find1(g1d, yy0, (v1120.size())) != 0)) {
                    for (int iu = 0; iu < v2d.size(); iu++) {
                        for (int grr = 0; grr < v5d.size(); grr++) {
                            if ((((node) v2d.elementAt(iu)).res).equals(((node) v5d.elementAt(grr)).res)) {
                                String x131 = (String) (((node) v2d.elementAt(iu)).attr);
                                float f121 = (Float.valueOf(x131)).floatValue();
                                //      System.out.println(f121);
                                dddd[g1d][grr] = dddd[g1d][grr] + f121;
                                dddd2[g1d][grr] = dddd2[g1d][grr] + 1;
                            }
                        }
                    }

                    for (int iii = 0; iii < 30; iii++) {
                        if (dddd2[g1d][iii] != 0) {
                            dddd[g1d][iii] = (dddd[g1d][iii]) / (dddd2[g1d][iii]);
                        }
                    }
                }


                Vector v6d = new Vector();
                Vector v7d = new Vector();

                for (int t4 = 0; t4 < v4d.size(); t4++) {
                    for (int t11 = 0; t11 < v5d.size(); t11++) {
                        node6 z10d = ci.new node6();
                        (((node5) (v4d.elementAt(t4))).vec).addElement((node6) (z10d));
                    }
                }

                for (int t4 = 0; t4 < v4wd.size(); t4++) {
                    for (int t11 = 0; t11 < v5wd.size(); t11++) {
                        node6 z10d = ci.new node6();
                        (((node5) (v4wd.elementAt(t4))).vec).addElement((node6) (z10d));
                    }
                }
                for (int t4 = 0; t4 < v41d.size(); t4++) {
                    for (int t11 = 0; t11 < v51d.size(); t11++) {
                        node6 z10d = ci.new node6();
                        (((node5) (v41d.elementAt(t4))).vec).addElement((node6) (z10d));
                    }
                }

                /*       for (int t111=0 ; t111 < v5w.size() ; t111++ )
                { node6  z101 = ci.new node6() ;
                v6.addElement((node6)(z101)) ;
                v7.addElement((node6)(z101)) ;
                }  */

                for (int t4 = 0; t4 < v42d.size(); t4++) {
                    for (int t11 = 0; t11 < v52d.size(); t11++) {
                        node6 z10d = ci.new node6();
                        (((node5) (v42d.elementAt(t4))).vec).addElement((node6) (z10d));
                    }
                }

                for (int t111 = 0; t111 < v52d.size(); t111++) {
                    node6 z101d = ci.new node6();
                    v6d.addElement((node6) (z101d));
                    v7d.addElement((node6) (z101d));
                }




                //    System.out.println((((node5)(v4.elementAt(0))).vec).size()) ;
                //  System.out.println(v6.size()) ;
                //      System.out.println(v7.size()) ;
                if (g1d == 0) {
                    ci.search1(dv1d, v2d);
                }
                ci.count(v2d, v4d, v5d);
                ci.count(v2wd, v4wd, v5wd);
                ci.count(v21d, v41d, v51d);
                ci.count(v22d, v42d, v52d);








                {
                    nodet4 rtrd = ci.new nodet4(g1d, v4d, v4wd);
                    ttv2d.addElement((nodet4) (rtrd));
                }

            }

            //    System.out.println("am" + dv1d.size()) ;



            //   System.out.println("am" + " ") ;





            //    ddd1[g1] = ((sum1/(v4.size()))-1) ;
            //      System.out.println((sum/(v4.size()))) ;
            //  ci.cal(v4) ;
            //  System.out.println(ci.cal(v4)) ;
            long start2 = System.currentTimeMillis();
            System.out.println("TIME");
            System.out.println((start2 - start1));
            /*  for(int jk=0;jk<dv.size();jk++)
            {for(int jk1=0;jk1<(((node12)(dv.elementAt(jk))).v12).size();jk1++)
            {  System.out.println(  (((node14)((((node12)(dv.elementAt(jk))).v12).elementAt(jk1))).att)) ;
            for(int jk2=0; jk2<(((node14)((((node12)(dv.elementAt(jk))).v12).elementAt(jk1))).v14).size();jk2++)
            { System.out.println( ((node11)((((node14)((((node12)(dv.elementAt(jk))).v12).elementAt(jk1))).v14).elementAt(jk2))).nu) ;
            System.out.println( ((node11)((((node14)((((node12)(dv.elementAt(jk))).v12).elementAt(jk1))).v14).elementAt(jk2))).pr) ;
            } }} */

            int er = 0;
            int hhh = 0;
            FileWriter f0 = new FileWriter("data100");
            FileInputStream fil = new FileInputStream("data6");
            // BufferedReader  stdin = new BufferedReader( new InputStreamReader(fil)) ;
            BufferedReader stdin = new BufferedReader(new FileReader("data6"));
            String s44 = stdin.readLine();
            /*   int ind1 = (Integer.valueOf(s)).intValue() ;
            //    String   s25 = stdin.readLine() ;
            //     int ind2 = (Integer.valueOf(s2)).intValue() ; */
            String s66 = ";";
            //do
            while ((s66 = stdin.readLine()) != null) {
                hhh = hhh + 1;
                //  System.out.println(hhh) ;
                int class2 = dv1d.size();
                int class1 = class2 + 1;
                double[] a8 = new double[class1];
                double[] a81 = new double[30];
                double[] a88 = new double[30];
                //    a8[4]=.1 ;
                int iw = 0;
                iw = iw + 1;
                //   System.out.println(a8[1]) ;
                //     System.out.println("p");
                int su = 0;
                int hj = 0;

                int cl = 0;
                Vector v1441 = new Vector(1, 1);
                Vector v1451 = new Vector(1, 1);
                //         st = s6 ;
                // if(!st.equals("quit"))
                StringTokenizer tokens = new StringTokenizer(s66, " ");

                while (tokens.hasMoreTokens()) {
                    ci.pushstring(v1451, tokens.nextToken());
                }

                for (int hyi = 0; hyi < (v1451.size() - 1); hyi++) {
                    if (ci.find1(hyi, yy, v112.size()) != 0) {
                        String x12 = (String) (v1451.elementAt(hyi));
                        float f121 = (Float.valueOf(x12)).floatValue();
                        float m11 = (((f121 - l1[hyi][1]) * 6) / (l1[hyi][0] - l1[hyi][1]));
                        int m1 = (int) m11;
                        String m81 = ";"; //((INTEGER)(m1)).toString() ;
                        if (m1 == 0) {
                            m81 = "0";
                        }
                        if (m1 == 1) {
                            m81 = "1";
                        }
                        if (m1 == 2) {
                            m81 = "2";
                        }
                        if (m1 == 3) {
                            m81 = "3";
                        }
                        if (m1 == 4) {
                            m81 = "4";
                        }
                        if (m1 > 4) {
                            m81 = "5";
                        }
                        /*    if(m1==6)
                        { m81 = "6" ;}
                        if(m1==7)
                        { m81 = "7" ;}
                        if(m1>7)
                        { m81 = "8" ;}   */
                        v1441.addElement(m81);
                    } else {
                        v1441.addElement((String) (v1451.elementAt(hyi)));
                    }
                }



                for (int iij = 1; iij <= class2; iij++) {
                    for (int ii = 0; ii < (v1441.size()); ii++) {
                        int n1 = ci.search4((String) (v1441.elementAt(ii)), (((node12) (dv.elementAt(ii))).v12));
                        float p111 = ci.search5(iij, (((node14) ((((node12) (dv.elementAt(ii))).v12).elementAt(n1))).v14));
                        //  System.out.println("L" + p111) ;
                        //    String  x12111 = (String)(v1451.elementAt(ii)) ;
                        //    float  f12111 = (Float.valueOf(x12111)).floatValue() ;
                        int ht1 = ci.Searcht1(((String) (v1451.elementAt(ii))), ((nodet1) (ttv4.elementAt(ii))).v1);
                        float sumi = 0;
                        float sumi2 = 0;
                        float kkk = 0;
                        if ((ci.find1(ii, yy0, (v1120.size())) == 0)) {
                            for (int yu = 0; yu < (((nodet4) (ttv2d.elementAt(ii))).v40).size(); yu++) {  // if((ci.find1(ii,yy0,(v1120.size()))==0))
                                kkk = ci.Searcht2(((node5) ((((nodet4) (ttv2.elementAt(ii))).v40).elementAt(yu))).attr1, ((nodet2) ((((nodet1) (ttv4.elementAt(ii))).v1).elementAt(ht1))).v2);
                                //((node5)((((nodet4)(ttv2.elementAt(ii))).v40).elementAt(yu))).attr1
                                int hhh6 = ((node6) ((((node5) ((((nodet4) (ttv2d.elementAt(ii))).v50).elementAt(iij - 1))).vec).elementAt(yu))).no;
                                sumi = sumi + kkk * hhh6;
                                sumi2 = sumi2 + hhh6;
                            }
                            a88[iij] = a88[iij] + (sumi / sumi2) * (sumi / sumi2);
                        } else {
                            String x12111 = (String) (v1451.elementAt(ii));
                            float f12111 = (Float.valueOf(x12111)).floatValue();
                            // (l1[ii][0]-l1[ii][1])
                            //  kkk =(float)(sig[ii]*(1/5.0*(f12111 - (Float.valueOf(((node5)((((nodet4)(ttv2d.elementAt(ii))).v40).elementAt(yu))).attr1).floatValue())))) ;
                            //   System.out.println("sig" + sig[ii]) ;
                            float kkk5 = (float) (sig[ii] * (1 / ((l1[ii][0] - l1[ii][1])) * (f12111 - dddd[ii][iij - 1])));
                            a88[iij] = a88[iij] + kkk5 * kkk5;
                        }
                    }
                }
                if (su == 1000) {
                    cl = hj;
                } else {
                    cl = ci.maxi(a88, class2);
                }
                // System.out.println("cl" + cl) ;
   /*     for(int yyy=0;yyy<class1;yyy++)
                System.out.print(a88[yyy] + " ") ; */

                /*      System.out.print((String)v1451.elementAt((v1451.size()-1)) + " ") ;
                System.out.println((((node)dv1d.elementAt(cl-1)).res)) ;
                System.out.println("error") ; */
                // iiii =iiii + 1;
                for (int h = 0; h < v1451.size() - 1; h++) {
                    f0.write((String) (v1451.elementAt(h)) + " ");
                }

                f0.write((String) (((node) dv1d.elementAt(cl - 1)).res));
                f0.write("\r\n");
                //   System.out.println("error") ;
                //   String ddd = (String)v145.elementAt((v1451.size()-1)) ;
                // if(ddd.equal(
                if (((String) v1451.elementAt((v1451.size() - 1))).equals((((node) dv1d.elementAt(cl - 1)).res))) {
                    ;
                } else {
                    er = er + 1;
                }
            //     System.out.println(iw) ;
            }
            f0.close();
            //  System.out.println("error") ;
            float err = er * 100 / (float) (hhh);
            System.out.println(err);
            jk = err;
            jk1 = jk1 + 1;
            System.out.println(err);
            FileWriter f00 = new FileWriter("data6");
            f00.write("1000");
            f00.write("\r\n");
            BufferedReader stdin21a = new BufferedReader(new FileReader("data100"));
            //    String s = stdin21.readLine() ;
            String s61a = " ;";
            while ((s61a = stdin21a.readLine()) != null) {
                Vector v1127a = new Vector(1, 1);
                StringTokenizer tokens1a = new StringTokenizer(s61a, " ");

                while (tokens1a.hasMoreTokens()) {
                    ci.pushstring(v1127a, tokens1a.nextToken());
                }


                //  BufferedReader  stdin21 = new BufferedReader( new FileReader("data10")) ;
                //  OutputStream f0 = new FileOutputStream("DATA100") ;
                //    FileWriter f0 = new FileWriter("data100") ;
                for (int h = 0; h < v1127a.size(); h++) {
                    f00.write((String) (v1127a.elementAt(h)) + " ");
                }
                f00.write("\r\n");
            //    f0.println() ;
            }
            f00.close();
        }

        long start3 = System.currentTimeMillis();
        System.out.println("TIME");
        //     System.out.println((start3 - start2)) ;

        System.out.println(jk1 + " number of iteration");
        long endTime = System.currentTimeMillis();
        runtime = (double)(endTime-startTime);

    }

    public static void main(String[] argv) throws IOException {
        dclusteringnew2 ci = new dclusteringnew2();
        float[][] l1 = new float[100][100];
        BufferedReader stdin2e = new BufferedReader(new FileReader("data6"));
        //    String s61121 = stdin2.readLine() ;
        String s611e = stdin2e.readLine();
        String s61111e = stdin2e.readLine();
        Vector v112e = new Vector(1, 1);
        Vector v1127e = new Vector();
        StringTokenizer tokens11e = new StringTokenizer(s611e, " ");

        while (tokens11e.hasMoreTokens()) {
            ci.pushstring(v112e, tokens11e.nextToken());
        }

        StringTokenizer tokens111e = new StringTokenizer(s61111e, " ");
        while (tokens111e.hasMoreTokens()) {
            ci.pushstring(v1127e, tokens111e.nextToken());
        }

        //    System.out.println(v112.size());
        int[] yye = new int[(v112e.size())];
        for (int hg = 0; hg < (v112e.size()); hg++) {
            yye[hg] = -1;
        }
        float[][] l1e = new float[(v1127e.size() - 1)][2];
        for (int hg1 = 0; hg1 < (v112e.size()); hg1++) {
            String x88e = (String) v112e.elementAt(hg1);
            yye[hg1] = ((Integer.valueOf(x88e)).intValue()) - 1;
        }


        for (int g1 = 0; g1 < (v1127e.size() - 1); g1++) {  //    System.out.println("g" + g1) ;
            //    node12  am  = ci.new node12() ;
            //   dv.addElement((node12)(am)) ;
            //  String x8 = (String)v112.elementAt(g1) ;
            //   int ind11 = (Integer.valueOf(x8)).intValue() ;
            float mine = 999999999;
            float maxe = -99999999;


            Vector v2e = new Vector(1, 1);
            Vector v4e = new Vector(1, 1);

            if (ci.find1(g1, yye, (v112e.size())) != 0) {        //   System.out.println("O");
                FileInputStream fil1e = new FileInputStream("data6");
                BufferedReader stdin1e = new BufferedReader(new FileReader("data6"));
                String s111e = stdin1e.readLine();
                /*    int ind11 = (Integer.valueOf(s111)).intValue() ;
                //    String   s211 = stdin1.readLine() ;
                int ind21e = (Integer.valueOf(s211e)).intValue() ; */
                String s61e = ";";
                //do
                while ((s61e = stdin1e.readLine()) != null) {

                    Vector v111e = new Vector(1, 1);

                    //         st = s6 ;
                    // if(!st.equals("quit"))
                    StringTokenizer tokens2e = new StringTokenizer(s61e, " ");

                    while (tokens2e.hasMoreTokens()) {
                        ci.pushstring(v111e, tokens2e.nextToken());
                    }
                    //  int k341 = ind11 - 1 ;
                    //    System.out.println(v111.size());
                    String xe = (String) v111e.elementAt(g1);
                    float f12e = (Float.valueOf(xe)).floatValue();
                    if (f12e > maxe) {
                        maxe = f12e;
                    }
                    if (f12e < mine) {
                        mine = f12e;
                    }

                }
            }


            l1[g1][0] = maxe;
            l1[g1][1] = mine;

        }


        FileWriter f00e = new FileWriter("data6b");
        f00e.write("1000");
        f00e.write("\r\n");
        FileInputStream fil21e = new FileInputStream("data6");
        BufferedReader stdin21e = new BufferedReader(new FileReader("data6"));
        //   String s6112 = stdin21.readLine() ;
        //      div = (Integer.valueOf(s6112)).intValue() ;
        String s611110e = stdin21e.readLine();
        String s6e = ";";
        while ((s6e = stdin21e.readLine()) != null) {

            Vector v1e = new Vector(1, 1);

            //         st = s6 ;
            // if(!st.equals("quit"))
            StringTokenizer tokense = new StringTokenizer(s6e, " ");

            while (tokense.hasMoreTokens()) {
                ci.pushstring(v1e, tokense.nextToken());
            }

            Vector v1441e = new Vector();

            for (int hyi = 0; hyi < (v1e.size() - 1); hyi++) {
                if (ci.find1(hyi, yye, v112e.size()) != 0) {
                    String x12e = (String) (v1e.elementAt(hyi));
                    float f121e = (Float.valueOf(x12e)).floatValue();
                    float m11e = (((f121e - l1[hyi][1]) * 6) / (l1[hyi][0] - l1[hyi][1]));
                    int m1e = (int) m11e;
                    String m81e = ";"; //((INTEGER)(m1)).toString() ;
                    if (m1e == 0) {
                        m81e = "0";
                    }
                    if (m1e == 1) {
                        m81e = "1";
                    }
                    if (m1e == 2) {
                        m81e = "2";
                    }
                    if (m1e == 3) {
                        m81e = "3";
                    }
                    if (m1e == 4) {
                        m81e = "4";
                    }
                    if (m1e > 4) {
                        m81e = "5";
                    }
                    /*    if(m1==6)
                    { m81 = "6" ;}
                    if(m1==7)
                    { m81 = "7" ;}
                    if(m1==8)
                    { m81 = "8" ;}
                    if(m1==9)
                    { m81 = "9" ;}
                    if(m1==10)
                    { m81 = "10" ;}
                    if(m1==11)
                    { m81 = "11" ;}
                    if(m1==12)
                    { m81 = "12" ;}

                    if(m1==13)
                    { m81 = "13" ;}
                    if(m1==14)
                    { m81 = "14" ;}
                    if(m1==15)
                    { m81 = "15" ;}
                    if(m1==16)
                    { m81 = "16" ;}
                    if(m1==17)
                    { m81 = "17" ;}
                    if(m1==18)
                    { m81 = "18" ;}
                    if(m1==19)
                    { m81 = "19" ;}
                    if(m1==20)
                    { m81 = "20" ;}

                    if(m1==21)
                    { m81 = "21" ;}
                    if(m1==22)
                    { m81 = "22" ;}
                    if(m1==23)
                    { m81 = "23" ;}
                    if(m1==24)
                    { m81 = "24" ;}
                    if(m1==25)
                    { m81 = "25" ;}
                    if(m1>25)
                    { m81 = "26" ;}    */

                    v1441e.addElement(m81e);
                } else {
                    v1441e.addElement((String) (v1e.elementAt(hyi)));
                }
            }






            /*      for(int i=0; i<v1441.size()-1;i++)
            {  for(int j=i+1; j<v1441e.size();j++)
            { System.out.print((String)v1441.elementAt(i) + ";");
            System.out.print((String)v1441.elementAt(j) + " ");
            } }  */

            for (int i = 0; i < v1441e.size(); i++) {
                f00e.write((String) v1441e.elementAt(i) + " ");
            }
            f00e.write((String) v1e.elementAt(v1e.size() - 1));
            f00e.write("\r\n");
        }
        f00e.close();


















        double[] sig = new double[100];
        String st = "quit1";
        Vector dv = new Vector();
        Vector dv1 = new Vector();
        float[][] ddd = new float[25][50];
        float[][] ddd2 = new float[25][50];
        float[] ddd1 = new float[100];
        Vector ttv4 = new Vector();
        Vector ttv2 = new Vector();
        float fo[] = new float[100];

        long start1 = System.currentTimeMillis();
        int l2 = 0;
        int div = 0;

        FileInputStream fil21 = new FileInputStream("data6b");
        BufferedReader stdin21 = new BufferedReader(new FileReader("data6b"));
        //   String s6112 = stdin21.readLine() ;
        //      div = (Integer.valueOf(s6112)).intValue() ;
        String s6111 = stdin21.readLine();
        String s6113 = stdin21.readLine();
        Vector v1127 = new Vector(1, 1);
        StringTokenizer tokens1 = new StringTokenizer(s6113, " ");

        while (tokens1.hasMoreTokens()) {
            ci.pushstring(v1127, tokens1.nextToken());
        }
        //   System.out.println(v1127.size());
        FileInputStream fil2 = new FileInputStream("data6b");
        BufferedReader stdin2 = new BufferedReader(new FileReader("data6b"));
        //    String s61121 = stdin2.readLine() ;
        String s611 = stdin2.readLine();
        Vector v112 = new Vector(1, 1);
        StringTokenizer tokens11 = new StringTokenizer(s611, " ");

        while (tokens11.hasMoreTokens()) {
            ci.pushstring(v112, tokens11.nextToken());
        }
        //    System.out.println(v112.size());
        int[] yy = new int[(v112.size())];
        for (int hg = 0; hg < (v112.size()); hg++) {
            yy[hg] = -1;
        }
        //    float[][] l1 = new float[(v1127.size()-1)][2] ;
        for (int hg1 = 0; hg1 < (v112.size()); hg1++) {
            String x88 = (String) v112.elementAt(hg1);
            yy[hg1] = ((Integer.valueOf(x88)).intValue()) - 1;
        }

        Vector nnn = new Vector();
        Vector nnn1 = new Vector();
        Vector nnn2 = new Vector();
        Vector nnn3 = new Vector();
        for (int uui = 0; uui < (v1127.size() - 1); uui++) {
            node1g nodex = ci.new node1g();
            nnn.addElement(nodex);
        }
        for (int uui = 0; uui < (v1127.size() - 1); uui++) {
            for (int uui1 = 0; uui1 < (v1127.size() - 1); uui1++) {
                node2g nodex = ci.new node2g();
                (((node1g) (nnn.elementAt(uui))).vr).addElement(nodex);
            }
        }
        for (int uui = 0; uui < (v1127.size() - 1); uui++) {
            node1g nodex = ci.new node1g();
            nnn1.addElement(nodex);
        }
        for (int uui = 0; uui < (v1127.size() - 1); uui++) {
            node1g nodex = ci.new node1g();
            nnn2.addElement(nodex);
        }
        for (int uui = 0; uui < (v1127.size() - 1); uui++) {
            node1g nodex = ci.new node1g();
            nnn3.addElement(nodex);
        }






        FileInputStream ffil = new FileInputStream("data6b");
        // BufferedReader  stdin8 = new BufferedReader( new InputStreamReader(fil)) ;
        BufferedReader stdin8 = new BufferedReader(new FileReader("data6b"));
        String s9 = stdin8.readLine();
        //   int ind1 = (Integer.valueOf(s)).intValue() ;
        //   String   s29 = stdin8.readLine() ;
      /*   int ind2 = (Integer.valueOf(s2)).intValue() ; */
        String s69 = ";";
        //do
        while ((s69 = stdin8.readLine()) != null) {

            Vector v19 = new Vector(1, 1);

            //         st = s6 ;
            // if(!st.equals("quit"))
            StringTokenizer tokens12 = new StringTokenizer(s69, " ");

            while (tokens12.hasMoreTokens()) {
                ci.pushstring(v19, tokens12.nextToken());
            }
            //  int k34 = ind11 - 1 ;
            //  System.out.println(v19.size());
            for (int g15 = 0; g15 < (v1127.size() - 1); g15++) {
                for (int g1 = 0; g1 < (v1127.size() - 1); g1++) {
                    String x131 = (String) v19.elementAt(g15);
                    int g101 = v19.size() - 1;
                    String x1 = (String) v19.elementAt(g101);
                    String v1 = (String) v19.elementAt(g1);
                    //     System.out.println("X!"+ x1);
                    {
                        node n1 = ci.new node(x131, v1);
                        (((node2g) (((Vector) (((node1g) (nnn.elementAt(g15))).vr)).elementAt(g1))).vr1).addElement(n1);
                    // v2.addElement(n1) ;
                    }

                    if (g1 == 0) {
                        {
                            node n12 = ci.new node(x131, x1);
                            ((Vector) (((node1g) (nnn1.elementAt(g15))).vr)).addElement(n12);
                        // v22.addElement(n12) ;
                        }

                        node n11 = ci.new node(x131, x1);
                        ((Vector) (((node1g) (nnn2.elementAt(g15))).vr)).addElement(n11);
                        //  v21.addElement(n11) ;

                        {
                            node n1w = ci.new node(x1, x131);
                            ((Vector) (((node1g) (nnn3.elementAt(g15))).vr)).addElement(n1w);
                        // v2w.addElement(n1w) ;
                        }

                    }
                }
            }
        }

        for (int g15 = 0; g15 < (v1127.size() - 1); g15++) {
            Vector ttv1 = new Vector();


            for (int g1 = 0; g1 < (v1127.size() - 1); g1++) {   //   System.out.println("g" + g1) ;
                node12 am = ci.new node12();
                dv.addElement((node12) (am));
                //  String x8 = (String)v112.elementAt(g1) ;
                //   int ind11 = (Integer.valueOf(x8)).intValue() ;


                Vector v21 = (Vector) (((node2g) (((Vector) (((node1g) (nnn.elementAt(g15))).vr)).elementAt(g1))).vr1);
                Vector v333 = new Vector(1, 1);
                Vector v4 = new Vector(1, 1);
                Vector v2w = ((Vector) (((node1g) (nnn1.elementAt(g15))).vr));
                Vector v4w = new Vector(1, 1);
                Vector v2 = ((Vector) (((node1g) (nnn2.elementAt(g15))).vr));
                Vector v41 = new Vector(1, 1);
                Vector v22 = ((Vector) (((node1g) (nnn1.elementAt(g15))).vr));
                Vector v42 = new Vector(1, 1);


                {






                    // while (!st.equals("quit")) ;
                    Vector v5 = new Vector();
                    Vector v5w = new Vector();
                    Vector v51 = new Vector();
                    Vector v52 = new Vector();
                    ci.search(v4, v2);
                    ci.search(v4w, v2w);
                    ci.search(v41, v21);
                    ci.search(v42, v22);
                    for (int gy = 0; gy < v4.size(); gy++) {
                        String giu = ((node5) (v4.elementAt(gy))).attr1;

                        node14 nnode1 = ci.new node14(giu);
                        (((node12) (dv.elementAt(g1))).v12).addElement((node14) (nnode1));
                    }
                    ci.search1(v5, v2);
                    ci.search1(v5w, v2w);
                    ci.search1(v51, v21);
                    ci.search1(v52, v22);
                    /*      for(int iu=0;iu<v2.size();iu++)
                    { for(int grr=0;grr<v5.size();grr++)
                    { if((((node)v2.elementAt(iu)).res).equals(((node)v5.elementAt(grr)).res))
                    { String x131 = (String)(((node)v333.elementAt(iu)).attr) ;
                    float  f121 = (Float.valueOf(x131)).floatValue() ;
                    //      System.out.println(f121);
                    ddd[g1][grr]=ddd[g1][grr] + f121 ;
                    ddd2[g1][grr] = ddd2[g1][grr] + 1 ; }
                    }
                    }

                    for( int iii=0;iii<30;iii++)
                    {  if(ddd2[g1][iii]!=0)
                    ddd[g1][iii]= (ddd[g1][iii])/(ddd2[g1][iii]) ;
                    }
                    for( int iii=0;iii<4;iii++)
                    { System.out.println(ddd[g1][iii]);
                    } */



                    if (g15 == 0) {
                        if (g1 == 0) {
                            ci.search1(dv1, v2);
                        }
                    }
                    Vector v6 = new Vector();
                    Vector v7 = new Vector();

                    for (int t4 = 0; t4 < v4.size(); t4++) {
                        for (int t11 = 0; t11 < v5.size(); t11++) {
                            node6 z10 = ci.new node6();
                            (((node5) (v4.elementAt(t4))).vec).addElement((node6) (z10));
                        }
                    }

                    for (int t4 = 0; t4 < v4w.size(); t4++) {
                        for (int t11 = 0; t11 < v5w.size(); t11++) {
                            node6 z10 = ci.new node6();
                            (((node5) (v4w.elementAt(t4))).vec).addElement((node6) (z10));
                        }
                    }
                    for (int t4 = 0; t4 < v41.size(); t4++) {
                        for (int t11 = 0; t11 < v51.size(); t11++) {
                            node6 z10 = ci.new node6();
                            (((node5) (v41.elementAt(t4))).vec).addElement((node6) (z10));
                        }
                    }

                    /*       for (int t111=0 ; t111 < v5w.size() ; t111++ )
                    { node6  z101 = ci.new node6() ;
                    v6.addElement((node6)(z101)) ;
                    v7.addElement((node6)(z101)) ;
                    }  */

                    for (int t4 = 0; t4 < v42.size(); t4++) {
                        for (int t11 = 0; t11 < v52.size(); t11++) {
                            node6 z10 = ci.new node6();
                            (((node5) (v42.elementAt(t4))).vec).addElement((node6) (z10));
                        }
                    }

                    for (int t111 = 0; t111 < v52.size(); t111++) {
                        node6 z101 = ci.new node6();
                        v6.addElement((node6) (z101));
                        v7.addElement((node6) (z101));
                    }




                    //    System.out.println((((node5)(v4.elementAt(0))).vec).size()) ;
                    //  System.out.println(v6.size()) ;
                    //      System.out.println(v7.size()) ;


                    ci.count(v2, v4, v5);
                    ci.count(v2w, v4w, v5w);
                    ci.count(v21, v41, v51);
                    ci.count(v22, v42, v52);
                    if (g1 == 0) {
                        nodet4 rtr = ci.new nodet4(g1, v4, v4w);
                        ttv2.addElement((nodet4) (rtr));
                    }
                    Vector vvv = new Vector();
                    for (int x11 = 0; x11 < v41.size(); x11++) {
                        Vector vvv1 = new Vector();
                        for (int z1 = 0; z1 < v41.size(); z1++) {
                            ci.probat1((((node5) (v41.elementAt(x11))).vec), (((node5) (v41.elementAt(z1))).vec));
                            float sss1 = ci.calwt((((node5) (v41.elementAt(x11))).vec), (((node5) (v41.elementAt(z1))).vec)) - 1;
                            //   System.out.println(sss1) ;
                            //    System.out.println(g1+"g1") ;
                            nodet3 ggh = ci.new nodet3(((node5) (v41.elementAt(z1))).attr1, sss1);
                            vvv1.addElement((nodet3) (ggh));
                        }
                        nodet2 ggh1 = ci.new nodet2(((node5) (v41.elementAt(x11))).attr1, vvv1);
                        vvv.addElement((nodet2) (ggh1));
                    }
                    nodet1 ggh11 = ci.new nodet1(g1, vvv);
                    ttv1.addElement((nodet1) (ggh11));


                    ci.sum1(v42, v6);
                    float sum = 0;
                    float sum1 = 0;
                    if (g1 == 0) {
                        for (int x1 = 0; x1 < v42.size(); x1++) {
                            int s1 = 1;
                            Vector y1 = new Vector();
                            Vector y2 = new Vector();
                            Vector y3 = new Vector();
                            Vector y4 = new Vector();
                            Vector y6 = new Vector();
                            Vector y8 = new Vector();
                            Vector y9 = new Vector();
                            Vector y10 = new Vector();
                            for (int t115 = 0; t115 < v52.size(); t115++) {
                                node6 z105 = ci.new node6();
                                y6.addElement((node6) (z105));
                            }
                            ci.proba((((node5) (v42.elementAt(x1))).vec), y6, v6);
                            for (int s21 = 0; s21 < v6.size(); s21++) {
                                node2 n1 = ci.new node2(s1);
                                y1.addElement((node2) (n1));
                                float f1 = ((node6) ((((node5) (v42.elementAt(x1))).vec).elementAt(s21))).prob;
                                node3 n2 = ci.new node3(s1, f1);
                                y2.addElement((node3) (n2));
                                Vector y5 = new Vector();
                                y5.addElement((node2) (n1));
                                node1 n3 = ci.new node1(y5, f1);
                                y3.addElement((node1) (n3));
                                y4.addElement((node1) (n3));
                                s1 = s1 + 1;
                            }
                            //   ci.sets(y3,y4 ,y2) ;
  /*  for(int i=0;i<y4.size();i++)
                            { {for(int i1=0;i1<(((node1)(y4.elementAt(i))).k).size();i1++)
                            { //System.out.println((((node1)(n11.elementAt(i))).k).size());
                            System.out.print(((node2)((((node1)(y4.elementAt(i))).k).elementAt(i1))).j2) ;}}
                            System.out.print(" ") ;
                            System.out.print(((node1)(y4.elementAt(i))).p);
                            System.out.print(" ") ;} */


                            Vector y7 = new Vector();
                            int s5 = 1;
                            //     ci.proba((((node5)(v4.elementAt(x1))).vec),y6,v6) ;
                            // System.out.println(y7.si) ;

                            for (int s3 = 0; s3 < v6.size(); s3++) {
                                float f3 = ((node6) (y6.elementAt(s3))).prob;
                                //   System.out.println(f3) ;
                                node3 n4 = ci.new node3(s5, f3);
                                y8.addElement((node3) (n4));
                                s5 = s5 + 1;
                            }
                            //  ci.fin(y4,y1,y8,v6.size(),y9) ;
                            float sss = ci.calw((((node5) (v42.elementAt(x1))).vec), y6, y7);
                            sum1 = sum1 + sss;
                            for (int h11 = 0; h11 < y7.size(); h11++) {
                                int h2 = ((node2) (y7.elementAt(h11))).j2 - 1;
                                float gr = ((node6) ((((node5) (v42.elementAt(x1))).vec).elementAt(h2))).prob;
                                int h5 = h2 + 1;
                                node11 nnode11 = ci.new node11(h5, gr);
                                (((node14) ((((node12) (dv.elementAt(g1))).v12).elementAt(x1))).v14).addElement(nnode11);
                                (((node14) ((((node12) (dv.elementAt(g1))).v12).elementAt(x1))).sig1) = sss;
                            }
                            int x2 = 0;
                            float x3 = 0;
                            //  x3 = ci.max(y9) ;
                            //   x2 = ci.max1(y9) ;
                            sum = sum + x3;
                        /* System.out.println("attribute value1"+ g15) ;
                        System.out.println("attribute value") ;
                        System.out.println(((node5)v42.elementAt(x1)).attr1) ;
                        System.out.println("max") ;
                        System.out.println("w  p(i,r,w) + p(i,-r,-w)") ;
                        for(int h11=0;h11<y7.size() ; h11++)
                        { int h2 = ((node2)(y7.elementAt(h11))).j2 - 1 ;
                        System.out.print(((node)(v52.elementAt(h2))).res+" ") ; }
                        System.out.print("  " ) ;
                        System.out.println(ci.calw((((node5)(v42.elementAt(x1))).vec),y6,y7)) ;
                        // System.out.println("attribute value") ;
                        //System.out.println(((node5)(v4.elementAt(x1))).attr1) ;
                        for(int i=0;i<y9.size();i++)
                        { {for(int i1=0;i1<(((node1)(y9.elementAt(i))).k).size();i1++)
                        { //System.out.println((((node1)(n11.elementAt(i))).k).size());
                        int g101 = (((node2)((((node1)(y9.elementAt(i))).k).elementAt(i1))).j2) - 1 ;
                        System.out.print(((node)(v52.elementAt(g101))).res) ; }}
                        // System.out.print(((node2)((((node1)(y9.elementAt(i))).k).elementAt(i1))).j2) ;}}
                        System.out.print(" ") ;
                        System.out.print(((node1)(y9.elementAt(i))).p);
                        System.out.print(" ") ;}
                        System.out.println("") ;   */



                        /* for(int i1=0;i1<(((node1)(y9.elementAt(x2))).k).size();i1++)
                        { //System.out.println((((node1)(n11.elementAt(i))).k).size());
                        int j10 = (((node2)((((node1)(y9.elementAt(x2))).k).elementAt(i1))).j2) - 1 ;
                        System.out.print(((node)(v5w.elementAt(j10))).res) ; }      // System.out.print(((node2)((((node1)(y9.elementAt(x2))).k).elementAt(i1))).j2) ;}
                        System.out.print(" ") ;
                        System.out.print(x3);
                        System.out.print(" ") ;
                        System.out.println(" ") ; */

                        }


                        /*
                        for(int t=0 ; t < v7.size() ; t++ )
                        {   System.out.println( ((node6)((((node5)(v4.elementAt(0))).vec).elementAt(t))).prob); }

                        for(int t=0 ; t < v7.size() ; t++ )
                        { System.out.println( ((node6)(v7.elementAt(t))).prob ) ;
                        }   */

                        /*    for (int t3=0 ; t3 < v4.size() ; t3++ )
                        { for(int t1=0 ; t1 < (((node5)(v4.elementAt(t3))).vec).size() ; t1++)
                        { System.out.println( ((node6)((((node5)(v4.elementAt(t3))).vec).elementAt(t1))).no );
                        }}  */
                        // System.out.println("Attribute") ;

                        //   System.out.println((g1+1)) ;
                        //   System.out.println("SIGNIFICANCE");
                        //    System.out.println(((sum1/(v42.size()))-1));
                        ((node12) (dv.elementAt(g1))).sig = ((sum1 / (v42.size())) - 1);
                        fo[g15] = ((sum1 / (v42.size())) - 1);
                    }
                }
            }    //   System.out.println("k") ;
            Vector vr = new Vector();
            float frr = 0;
            //    System.out.println((((nodet1)(ttv1.elementAt(0))).v1).size());
            for (int u1 = 0; u1 < (((nodet1) (ttv1.elementAt(0))).v1).size(); u1++) {
                Vector vr1 = new Vector();
                for (int u2 = 0; u2 < (((nodet2) ((((nodet1) (ttv1.elementAt(0))).v1).elementAt(0))).v2).size(); u2++) {
                    float sume = 0;
                    for (int u3 = 0; u3 < (ttv1).size(); u3++) {
                        sume = sume + ((nodet3) ((((nodet2) ((((nodet1) (ttv1.elementAt(u3))).v1).elementAt(u1))).v2).elementAt(u2))).v3;
                    }
                    if (sume != 0) {
                        //            sume = (sume-((((nodet1)(ttv1.elementAt(0))).v1).size()-1)/(((nodet1)(ttv1.elementAt(0))).v1).size())/((ttv1).size()-1)  ;
                    }
                    sume = sume / ((ttv1).size());
                    frr = frr + sume;
                    nodet3 dt3 = ci.new nodet3(((nodet3) ((((nodet2) ((((nodet1) (ttv1.elementAt(0))).v1).elementAt(u1))).v2).elementAt(u2))).t3, sume);
                    vr1.addElement(dt3);
                }
                nodet2 dt2 = ci.new nodet2(((nodet2) ((((nodet1) (ttv1.elementAt(0))).v1).elementAt(u1))).t2, vr1);
                vr.addElement(dt2);
            }
            nodet1 dt3 = ci.new nodet1(0, vr);
            ttv4.addElement(dt3);
            frr = frr / (((((nodet1) (ttv1.elementAt(0))).v1).size()) * ((((nodet1) (ttv1.elementAt(0))).v1).size() - 1));
            sig[g15] = frr;
        //      System.out.println("am" + frr) ;
        }

        double jk = 100;
        int jk1 = 0;

        while ((jk != 0) && (jk1 < 100)) {
            //    double[]    sig = new double[100] ;
  /*     sig[0] = .26 ;
            sig[3] = .32 ;
            sig[4] = .38 ;
            sig[7] = .63 ;
            sig[9] = .37 ;
            sig[1] = .0 ;
            sig[2] = .25 ;
            sig[6] = .40 ;
            sig[5] = .25 ;
            sig[7] = .32 ;
            sig[10] = .39 ;
            sig[12] = .42 ;
            sig[13] = .53 ; */

            float[][] dddd = new float[100][100];
            float[][] dddd1 = new float[100][100];
            float[][] dddd2 = new float[100][100];

            BufferedReader stdin210 = new BufferedReader(new FileReader("data6"));
            //   String s61120 = stdin210.readLine() ;
            //      div = (Integer.valueOf(s6112)).intValue() ;
            String s61110 = stdin210.readLine();
            String s61130 = stdin210.readLine();
            Vector v11270 = new Vector(1, 1);
            StringTokenizer tokens10 = new StringTokenizer(s61130, " ");

            while (tokens10.hasMoreTokens()) {
                ci.pushstring(v11270, tokens10.nextToken());
            }
            System.out.println(v11270.size());
            //   //   FileInputStream fil20 = new FileInputStream("data6a");
            BufferedReader stdin20 = new BufferedReader(new FileReader("DATA10.txt"));
            //    String s61121 = stdin2.readLine() ;
            String s6110 = stdin20.readLine();
            Vector v1120 = new Vector(1, 1);
            StringTokenizer tokens110 = new StringTokenizer(s6110, " ");

            while (tokens110.hasMoreTokens()) {
                ci.pushstring(v1120, tokens110.nextToken());
            }
            System.out.println(v1120.size() + " l");
            int[] yy0 = new int[(v1120.size())];
            for (int hg = 0; hg < (v112.size()); hg++) {
                yy0[hg] = -1;
            }
            float[][] l10 = new float[(v11270.size() - 1)][2];
            for (int hg1 = 0; hg1 < (v1120.size()); hg1++) {
                String x880 = (String) v1120.elementAt(hg1);
                yy0[hg1] = ((Integer.valueOf(x880)).intValue()) - 1;
            }







            Vector see = new Vector();
            Vector see1 = new Vector();
            Vector see2 = new Vector();
            Vector see3 = new Vector();
            for (int uui = 0; uui < (v1127.size() - 1); uui++) {
                node1g nodex = ci.new node1g();
                see.addElement(nodex);
            }
            for (int uui = 0; uui < (v1127.size() - 1); uui++) {
                node1g nodex = ci.new node1g();
                see1.addElement(nodex);
            }
            for (int uui = 0; uui < (v1127.size() - 1); uui++) {
                node1g nodex = ci.new node1g();
                see2.addElement(nodex);
            }
            for (int uui = 0; uui < (v1127.size() - 1); uui++) {
                node1g nodex = ci.new node1g();
                see3.addElement(nodex);
            }


            Vector ttv2d = new Vector();
            Vector dv1d = new Vector();
            /*   for(int g1d=0 ; g1d<(v1127.size()-1) ; g1d++)
            {   //   System.out.println("g" + g1d) ;

            Vector v2d = new Vector(1,1) ;
            Vector v333d = new Vector(1,1) ;
            Vector v4d = new Vector(1,1) ;
            Vector v2wd = new Vector(1,1) ;
            Vector v4wd = new Vector(1,1) ;
            Vector v21d = new Vector(1,1) ;
            Vector v41d = new Vector(1,1) ;
            Vector v22d = new Vector(1,1) ;
            Vector v42d = new Vector(1,1) ; */

            //  FileInputStream ffil = new FileInputStream("data6");
            // BufferedReader  stdin8 = new BufferedReader( new InputStreamReader(fil)) ;
            BufferedReader stdin8d = new BufferedReader(new FileReader("data6"));
            String s9d = stdin8d.readLine();
            //   int ind1 = (Integer.valueOf(s)).intValue() ;
            //   String   s29 = stdin8.readLine() ;
      /*   int ind2 = (Integer.valueOf(s2)).intValue() ; */
            String s69d = ";";
            //do
            while ((s69d = stdin8d.readLine()) != null) {

                Vector v19d = new Vector(1, 1);

                //         st = s6 ;
                // if(!st.equals("quit"))
                StringTokenizer tokens12d = new StringTokenizer(s69d, " ");

                while (tokens12d.hasMoreTokens()) {
                    ci.pushstring(v19d, tokens12d.nextToken());
                }
                //  int k34 = ind11 - 1 ;
                //   System.out.println(v19d.size());
                for (int g1d = 0; g1d < (v1127.size() - 1); g1d++) {
                    String x131d = (String) v19d.elementAt(g1d);
                    int g101d = v19d.size() - 1;
                    String x1d = (String) v19d.elementAt(g101d);
                    String v1d = (String) v19d.elementAt(g1d);
                    //     System.out.println("X!"+ x1);
                    {
                        node n1d = ci.new node(x131d, x1d);
                        ((Vector) (((node1g) (see.elementAt(g1d))).vr)).addElement(n1d);
                    //  v2d.addElement(n1d) ;
                    }

                    node n12d = ci.new node(x131d, x1d);
                    //    v22d.addElement(n12d) ; }
                    ((Vector) (((node1g) (see1.elementAt(g1d))).vr)).addElement(n12d);
                    node n11d = ci.new node(x131d, v1d);
                    ((Vector) (((node1g) (see2.elementAt(g1d))).vr)).addElement(n11d);
                    // v21d.addElement(n11d) ;

                    {
                        node n1wd = ci.new node(x1d, x131d);
                        ((Vector) (((node1g) (see3.elementAt(g1d))).vr)).addElement(n1wd);
                    //   v2wd.addElement(n1wd) ;
                    }
                }

            }



            for (int g1d = 0; g1d < (v1127.size() - 1); g1d++) {   //   System.out.println("g" + g1d) ;

                Vector v2d = (Vector) (((node1g) (see.elementAt(g1d))).vr);
                //   System.out.println(v2d.size()) ;
                Vector v333d = new Vector(1, 1);
                Vector v4d = new Vector(1, 1);
                Vector v2wd = (Vector) (((node1g) (see3.elementAt(g1d))).vr);
                Vector v4wd = new Vector(1, 1);
                Vector v21d = (Vector) (((node1g) (see2.elementAt(g1d))).vr);
                Vector v41d = new Vector(1, 1);
                Vector v22d = (Vector) (((node1g) (see1.elementAt(g1d))).vr);
                Vector v42d = new Vector(1, 1);


                // while (!st.equals("quit")) ;
                Vector v5d = new Vector();
                Vector v5wd = new Vector();
                Vector v51d = new Vector();
                Vector v52d = new Vector();
                ci.search(v4d, v2d);
                ci.search(v4wd, v2wd);
                ci.search(v41d, v21d);
                ci.search(v42d, v22d);
                ci.search1(v5d, v2d);
                ci.search1(v5wd, v2wd);
                ci.search1(v51d, v21d);
                ci.search1(v52d, v22d);
                if ((ci.find1(g1d, yy0, (v1120.size())) != 0)) {
                    for (int iu = 0; iu < v2d.size(); iu++) {
                        for (int grr = 0; grr < v5d.size(); grr++) {
                            if ((((node) v2d.elementAt(iu)).res).equals(((node) v5d.elementAt(grr)).res)) {
                                String x131 = (String) (((node) v2d.elementAt(iu)).attr);
                                float f121 = (Float.valueOf(x131)).floatValue();
                                //      System.out.println(f121);
                                dddd[g1d][grr] = dddd[g1d][grr] + f121;
                                dddd2[g1d][grr] = dddd2[g1d][grr] + 1;
                            }
                        }
                    }

                    for (int iii = 0; iii < 30; iii++) {
                        if (dddd2[g1d][iii] != 0) {
                            dddd[g1d][iii] = (dddd[g1d][iii]) / (dddd2[g1d][iii]);
                        }
                    }
                }


                Vector v6d = new Vector();
                Vector v7d = new Vector();

                for (int t4 = 0; t4 < v4d.size(); t4++) {
                    for (int t11 = 0; t11 < v5d.size(); t11++) {
                        node6 z10d = ci.new node6();
                        (((node5) (v4d.elementAt(t4))).vec).addElement((node6) (z10d));
                    }
                }

                for (int t4 = 0; t4 < v4wd.size(); t4++) {
                    for (int t11 = 0; t11 < v5wd.size(); t11++) {
                        node6 z10d = ci.new node6();
                        (((node5) (v4wd.elementAt(t4))).vec).addElement((node6) (z10d));
                    }
                }
                for (int t4 = 0; t4 < v41d.size(); t4++) {
                    for (int t11 = 0; t11 < v51d.size(); t11++) {
                        node6 z10d = ci.new node6();
                        (((node5) (v41d.elementAt(t4))).vec).addElement((node6) (z10d));
                    }
                }

                /*       for (int t111=0 ; t111 < v5w.size() ; t111++ )
                { node6  z101 = ci.new node6() ;
                v6.addElement((node6)(z101)) ;
                v7.addElement((node6)(z101)) ;
                }  */

                for (int t4 = 0; t4 < v42d.size(); t4++) {
                    for (int t11 = 0; t11 < v52d.size(); t11++) {
                        node6 z10d = ci.new node6();
                        (((node5) (v42d.elementAt(t4))).vec).addElement((node6) (z10d));
                    }
                }

                for (int t111 = 0; t111 < v52d.size(); t111++) {
                    node6 z101d = ci.new node6();
                    v6d.addElement((node6) (z101d));
                    v7d.addElement((node6) (z101d));
                }




                //    System.out.println((((node5)(v4.elementAt(0))).vec).size()) ;
                //  System.out.println(v6.size()) ;
                //      System.out.println(v7.size()) ;
                if (g1d == 0) {
                    ci.search1(dv1d, v2d);
                }
                ci.count(v2d, v4d, v5d);
                ci.count(v2wd, v4wd, v5wd);
                ci.count(v21d, v41d, v51d);
                ci.count(v22d, v42d, v52d);








                {
                    nodet4 rtrd = ci.new nodet4(g1d, v4d, v4wd);
                    ttv2d.addElement((nodet4) (rtrd));
                }

            }

            //    System.out.println("am" + dv1d.size()) ;



            //   System.out.println("am" + " ") ;





            //    ddd1[g1] = ((sum1/(v4.size()))-1) ;
            //      System.out.println((sum/(v4.size()))) ;
            //  ci.cal(v4) ;
            //  System.out.println(ci.cal(v4)) ;
            long start2 = System.currentTimeMillis();
            System.out.println("TIME");
            System.out.println((start2 - start1));
            /*  for(int jk=0;jk<dv.size();jk++)
            {for(int jk1=0;jk1<(((node12)(dv.elementAt(jk))).v12).size();jk1++)
            {  System.out.println(  (((node14)((((node12)(dv.elementAt(jk))).v12).elementAt(jk1))).att)) ;
            for(int jk2=0; jk2<(((node14)((((node12)(dv.elementAt(jk))).v12).elementAt(jk1))).v14).size();jk2++)
            { System.out.println( ((node11)((((node14)((((node12)(dv.elementAt(jk))).v12).elementAt(jk1))).v14).elementAt(jk2))).nu) ;
            System.out.println( ((node11)((((node14)((((node12)(dv.elementAt(jk))).v12).elementAt(jk1))).v14).elementAt(jk2))).pr) ;
            } }} */

            int er = 0;
            int hhh = 0;
            FileWriter f0 = new FileWriter("data100");
            FileInputStream fil = new FileInputStream("data6");
            // BufferedReader  stdin = new BufferedReader( new InputStreamReader(fil)) ;
            BufferedReader stdin = new BufferedReader(new FileReader("data6"));
            String s44 = stdin.readLine();
            /*   int ind1 = (Integer.valueOf(s)).intValue() ;
            //    String   s25 = stdin.readLine() ;
            //     int ind2 = (Integer.valueOf(s2)).intValue() ; */
            String s66 = ";";
            //do
            while ((s66 = stdin.readLine()) != null) {
                hhh = hhh + 1;
                //  System.out.println(hhh) ;
                int class2 = dv1d.size();
                int class1 = class2 + 1;
                double[] a8 = new double[class1];
                double[] a81 = new double[30];
                double[] a88 = new double[30];
                //    a8[4]=.1 ;
                int iw = 0;
                iw = iw + 1;
                //   System.out.println(a8[1]) ;
                //     System.out.println("p");
                int su = 0;
                int hj = 0;

                int cl = 0;
                Vector v1441 = new Vector(1, 1);
                Vector v1451 = new Vector(1, 1);
                //         st = s6 ;
                // if(!st.equals("quit"))
                StringTokenizer tokens = new StringTokenizer(s66, " ");

                while (tokens.hasMoreTokens()) {
                    ci.pushstring(v1451, tokens.nextToken());
                }

                for (int hyi = 0; hyi < (v1451.size() - 1); hyi++) {
                    if (ci.find1(hyi, yy, v112.size()) != 0) {
                        String x12 = (String) (v1451.elementAt(hyi));
                        float f121 = (Float.valueOf(x12)).floatValue();
                        float m11 = (((f121 - l1[hyi][1]) * 6) / (l1[hyi][0] - l1[hyi][1]));
                        int m1 = (int) m11;
                        String m81 = ";"; //((INTEGER)(m1)).toString() ;
                        if (m1 == 0) {
                            m81 = "0";
                        }
                        if (m1 == 1) {
                            m81 = "1";
                        }
                        if (m1 == 2) {
                            m81 = "2";
                        }
                        if (m1 == 3) {
                            m81 = "3";
                        }
                        if (m1 == 4) {
                            m81 = "4";
                        }
                        if (m1 > 4) {
                            m81 = "5";
                        }
                        /*    if(m1==6)
                        { m81 = "6" ;}
                        if(m1==7)
                        { m81 = "7" ;}
                        if(m1>7)
                        { m81 = "8" ;}   */
                        v1441.addElement(m81);
                    } else {
                        v1441.addElement((String) (v1451.elementAt(hyi)));
                    }
                }



                for (int iij = 1; iij <= class2; iij++) {
                    for (int ii = 0; ii < (v1441.size()); ii++) {
                        int n1 = ci.search4((String) (v1441.elementAt(ii)), (((node12) (dv.elementAt(ii))).v12));
                        float p111 = ci.search5(iij, (((node14) ((((node12) (dv.elementAt(ii))).v12).elementAt(n1))).v14));
                        //  System.out.println("L" + p111) ;
                        //    String  x12111 = (String)(v1451.elementAt(ii)) ;
                        //    float  f12111 = (Float.valueOf(x12111)).floatValue() ;
                        int ht1 = ci.Searcht1(((String) (v1451.elementAt(ii))), ((nodet1) (ttv4.elementAt(ii))).v1);
                        float sumi = 0;
                        float sumi2 = 0;
                        float kkk = 0;
                        if ((ci.find1(ii, yy0, (v1120.size())) == 0)) {
                            for (int yu = 0; yu < (((nodet4) (ttv2d.elementAt(ii))).v40).size(); yu++) {  // if((ci.find1(ii,yy0,(v1120.size()))==0))
                                kkk = ci.Searcht2(((node5) ((((nodet4) (ttv2.elementAt(ii))).v40).elementAt(yu))).attr1, ((nodet2) ((((nodet1) (ttv4.elementAt(ii))).v1).elementAt(ht1))).v2);
                                //((node5)((((nodet4)(ttv2.elementAt(ii))).v40).elementAt(yu))).attr1
                                int hhh6 = ((node6) ((((node5) ((((nodet4) (ttv2d.elementAt(ii))).v50).elementAt(iij - 1))).vec).elementAt(yu))).no;
                                sumi = sumi + kkk * hhh6;
                                sumi2 = sumi2 + hhh6;
                            }
                            a88[iij] = a88[iij] + (sumi / sumi2) * (sumi / sumi2);
                        } else {
                            String x12111 = (String) (v1451.elementAt(ii));
                            float f12111 = (Float.valueOf(x12111)).floatValue();
                            // (l1[ii][0]-l1[ii][1])
                            //  kkk =(float)(sig[ii]*(1/5.0*(f12111 - (Float.valueOf(((node5)((((nodet4)(ttv2d.elementAt(ii))).v40).elementAt(yu))).attr1).floatValue())))) ;
                            //   System.out.println("sig" + sig[ii]) ;
                            float kkk5 = (float) (sig[ii] * (1 / ((l1[ii][0] - l1[ii][1])) * (f12111 - dddd[ii][iij - 1])));
                            a88[iij] = a88[iij] + kkk5 * kkk5;
                        }
                    }
                }
                if (su == 1000) {
                    cl = hj;
                } else {
                    cl = ci.maxi(a88, class2);
                }
                // System.out.println("cl" + cl) ;
   /*     for(int yyy=0;yyy<class1;yyy++)
                System.out.print(a88[yyy] + " ") ; */

                /*      System.out.print((String)v1451.elementAt((v1451.size()-1)) + " ") ;
                System.out.println((((node)dv1d.elementAt(cl-1)).res)) ;
                System.out.println("error") ; */
                // iiii =iiii + 1;
                for (int h = 0; h < v1451.size() - 1; h++) {
                    f0.write((String) (v1451.elementAt(h)) + " ");
                }

                f0.write((String) (((node) dv1d.elementAt(cl - 1)).res));
                f0.write("\r\n");
                //   System.out.println("error") ;
                //   String ddd = (String)v145.elementAt((v1451.size()-1)) ;
                // if(ddd.equal(
                if (((String) v1451.elementAt((v1451.size() - 1))).equals((((node) dv1d.elementAt(cl - 1)).res))) {
                    ;
                } else {
                    er = er + 1;
                }
            //     System.out.println(iw) ;
            }
            f0.close();
            //  System.out.println("error") ;
            float err = er * 100 / (float) (hhh);
            System.out.println(err);
            jk = err;
            jk1 = jk1 + 1;
            System.out.println(err);
            FileWriter f00 = new FileWriter("data6");
            f00.write("1000");
            f00.write("\r\n");
            BufferedReader stdin21a = new BufferedReader(new FileReader("data100"));
            //    String s = stdin21.readLine() ;
            String s61a = " ;";
            while ((s61a = stdin21a.readLine()) != null) {
                Vector v1127a = new Vector(1, 1);
                StringTokenizer tokens1a = new StringTokenizer(s61a, " ");

                while (tokens1a.hasMoreTokens()) {
                    ci.pushstring(v1127a, tokens1a.nextToken());
                }


                //  BufferedReader  stdin21 = new BufferedReader( new FileReader("data10")) ;
                //  OutputStream f0 = new FileOutputStream("DATA100") ;
                //    FileWriter f0 = new FileWriter("data100") ;
                for (int h = 0; h < v1127a.size(); h++) {
                    f00.write((String) (v1127a.elementAt(h)) + " ");
                }
                f00.write("\r\n");
            //    f0.println() ;
            }
            f00.close();
        }

        long start3 = System.currentTimeMillis();
        System.out.println("TIME");
        //     System.out.println((start3 - start2)) ;

        System.out.println(jk1 + " number of iteration");

    }
}



