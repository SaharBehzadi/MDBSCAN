/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compare;

/**
 *
 * @author boehm
 */


public class AMD {

    public long [] bucky = {Long.parseLong("000000000000000000000000000000000000000000000000000000110011",2),
                            Long.parseLong("000000000000000000000000000000000000000000000000010000000111",2),
                            Long.parseLong("000000000000000000000000000000000000000000001000000000001110",2),
                            Long.parseLong("000000000000000000000000000000000000000100000000000000011100",2),
                            Long.parseLong("000000000000000000000000000000000010000000000000000000011001",2),
                            Long.parseLong("000000000000000000000000000000000000000000000000001001100001",2),
                            Long.parseLong("000000000000000000000000000000100000000000000000000011100000",2),
                            Long.parseLong("000000000000000000100000000000000000000000000000000111000000",2),
                            Long.parseLong("000000000000000000000010000000000000000000000000001110000000",2),
                            Long.parseLong("000000000000000000000000000000000000000000000000101100100000",2),
                            Long.parseLong("000000000000000000000000000000000000000000000100110000000010",2),
                            Long.parseLong("000000000000000000000000000000000000000000000001111000000000",2),
                            Long.parseLong("000000000000000000000001000000000000000000000011100000000000",2),
                            Long.parseLong("000000000000000000000000000100000000000000000111000000000000",2),
                            Long.parseLong("000000000000000000000000000000000000000000010110010000000000",2),
                            Long.parseLong("000000000000000000000000000000000000000010011000000000000100",2),
                            Long.parseLong("000000000000000000000000000000000000000000111100000000000000",2),
                            Long.parseLong("000000000000000000000000000010000000000001110000000000000000",2),
                            Long.parseLong("000000010000000000000000000000000000000011100000000000000000",2),
                            Long.parseLong("000000000000000000000000000000000000001011001000000000000000",2),
                            Long.parseLong("000000000000000000000000000000000001001100000000000000001000",2),
                            Long.parseLong("000000000000000000000000000000000000011110000000000000000000",2),
                            Long.parseLong("000000001000000000000000000000000000111000000000000000000000",2),
                            Long.parseLong("000000000000100000000000000000000001110000000000000000000000",2),
                            Long.parseLong("000000000000000000000000000000000101100100000000000000000000",2),
                            Long.parseLong("000000000000000000000000000000100110000000000000000000010000",2),
                            Long.parseLong("000000000000000000000000000000001111000000000000000000000000",2),
                            Long.parseLong("000000000000010000000000000000011100000000000000000000000000",2),
                            Long.parseLong("000000000000000001000000000000111000000000000000000000000000",2),
                            Long.parseLong("000000000000000000000000000000110010000000000000000001000000",2),
                            Long.parseLong("000000100000000000000000010011000000000000000000000000000000",2),
                            Long.parseLong("000000000000000000000000000111000000000000100000000000000000",2),
                            Long.parseLong("000000000000000000000000001110000000000000000010000000000000",2),
                            Long.parseLong("000000000000000000000000111100000000000000000000000000000000",2),
                            Long.parseLong("000010000000000000000000011001000000000000000000000000000000",2),
                            Long.parseLong("000000000000000000001001101000000000000000000000000000000000",2),
                            Long.parseLong("000000000000000000000011100000000000000000000001000000000000",2),
                            Long.parseLong("000000000000000000000111000000000000000000000000000100000000",2),
                            Long.parseLong("000000000000000000011110000000000000000000000000000000000000",2),
                            Long.parseLong("000100000000000000001100100000000000000000000000000000000000",2),
                            Long.parseLong("000000000000000100110100000000000000000000000000000000000000",2),
                            Long.parseLong("000000000000000001110000000000000000000000000000000010000000",2),
                            Long.parseLong("000000000000000011100000000000010000000000000000000000000000",2),
                            Long.parseLong("000000000000001111000000000000000000000000000000000000000000",2),
                            Long.parseLong("001000000000000110010000000000000000000000000000000000000000",2),
                            Long.parseLong("000000000010011010000000000000000000000000000000000000000000",2),
                            Long.parseLong("000000000000111000000000000000001000000000000000000000000000",2),
                            Long.parseLong("000000000001110000000000000000000000100000000000000000000000",2),
                            Long.parseLong("000000000111100000000000000000000000000000000000000000000000",2),
                            Long.parseLong("010000000011001000000000000000000000000000000000000000000000",2),
                            Long.parseLong("000001001101000000000000000000000000000000000000000000000000",2),
                            Long.parseLong("000000011100000000000000000000000000010000000000000000000000",2),
                            Long.parseLong("000000111000000000000000000000000000000001000000000000000000",2),
                            Long.parseLong("000001110000000000000000000001000000000000000000000000000000",2),
                            Long.parseLong("100001100100000000000000000000000000000000000000000000000000",2),
                            Long.parseLong("100110000000000000000000010000000000000000000000000000000000",2),
                            Long.parseLong("001110000000000000001000000000000000000000000000000000000000",2),
                            Long.parseLong("011100000000000100000000000000000000000000000000000000000000",2),
                            Long.parseLong("111000000010000000000000000000000000000000000000000000000000",2),
                            Long.parseLong("110011000000000000000000000000000000000000000000000000000000",2)} ;

    public  long [] test = {Long.parseLong("0000101000", 2),
                            Long.parseLong("0100110000", 2),
                            Long.parseLong("0001110000", 2),
                            Long.parseLong("0011000001", 2),
                            Long.parseLong("0101000110", 2),
                            Long.parseLong("0000000111", 2),
                            Long.parseLong("1110011100", 2),
                            Long.parseLong("1101001000", 2),
                            Long.parseLong("1011010010", 2),
                            Long.parseLong("0111000000", 2)
                           } ;

    public final long [] pow2 = {0x1L,0x2L,0x4L,0x8L,                                                           //  0
                                 0x10L,0x20L,0x40L,0x80L,                                                       //  4
                                 0x100L,0x200L,0x400L,0x800L,                                                   //  8
                                 0x1000L,0x2000L,0x4000L,0x8000L,                                               // 12
                                 0x10000L,0x20000L,0x40000L,0x80000L,                                           // 16
                                 0x100000L,0x200000L,0x400000L,0x800000L,                                       // 20
                                 0x1000000L,0x2000000L,0x4000000L,0x8000000L,                                   // 24
                                 0x10000000L,0x20000000L,0x40000000L,0x80000000L,                               // 28
                                 0x100000000L,0x200000000L,0x400000000L,0x800000000L,                           // 32
                                 0x1000000000L,0x2000000000L,0x4000000000L,0x8000000000L,                       // 36
                                 0x10000000000L,0x20000000000L,0x40000000000L,0x80000000000L,                   // 40
                                 0x100000000000L,0x200000000000L,0x400000000000L,0x800000000000L,               // 44
                                 0x1000000000000L,0x2000000000000L,0x4000000000000L,0x8000000000000L,           // 48
                                 0x10000000000000L,0x20000000000000L,0x40000000000000L,0x80000000000000L,       // 52
                                 0x100000000000000L,0x200000000000000L,0x400000000000000L,0x800000000000000L,   // 56
                                 0x1000000000000000L,0x2000000000000000L,0x4000000000000000L} ;                 // 60

    public final int [] sze  = {0,1,1,2,1,2,2,3,1,2,2,3,2,3,3,4,
                                1,2,2,3,2,3,3,4,2,3,3,4,3,4,4,5,
                                1,2,2,3,2,3,3,4,2,3,3,4,3,4,4,5,
                                2,3,3,4,3,4,4,5,3,4,4,5,4,5,5,6,
                                1,2,2,3,2,3,3,4,2,3,3,4,3,4,4,5,
                                2,3,3,4,3,4,4,5,3,4,4,5,4,5,5,6,
                                2,3,3,4,3,4,4,5,3,4,4,5,4,5,5,6,
                                3,4,4,5,4,5,5,6,4,5,5,6,5,6,6,7,
                                1,2,2,3,2,3,3,4,2,3,3,4,3,4,4,5,
                                2,3,3,4,3,4,4,5,3,4,4,5,4,5,5,6,
                                2,3,3,4,3,4,4,5,3,4,4,5,4,5,5,6,
                                3,4,4,5,4,5,5,6,4,5,5,6,5,6,6,7,
                                2,3,3,4,3,4,4,5,3,4,4,5,4,5,5,6,
                                3,4,4,5,4,5,5,6,4,5,5,6,5,6,6,7,
                                3,4,4,5,4,5,5,6,4,5,5,6,5,6,6,7,
                                4,5,5,6,5,6,6,7,5,6,6,7,6,7,7,8} ;
    public int sz (long x) {
        return sze[(int)((x >>  0) & 255)] + sze[(int)((x >>  8) & 255)] + sze[(int)((x >> 16) & 255)] + sze[(int)((x >> 24) & 255)]
             + sze[(int)((x >> 32) & 255)] + sze[(int)((x >> 40) & 255)] + sze[(int)((x >> 48) & 255)] + sze[(int)((x >> 52) & 255)] ;
    }

    public int[] algorithm1 (long [] a) {
        int n = a.length ;
        int [] r = new int [n] ;
        long v = pow2[n] - 1 ;
        //long vbar = 0 ;
        long [] e = new long [n] ;
        int [] d = new int [n] ;
        long [] ii = new long [n] ;
        long [] l = new long [n] ;
        for (int i = 0 ; i < n ; i++) {
            d [i] = sz(a[i]) ;
            ii [i] = pow2 [i] ;
        }
        int k = 0 ;
        while (k <n) {
            // select variable that minimizes dp
            int p = 0 ;
            int mind = d[0] ;
            for (int i=1 ; i<n ; i++)
                if (d[i]<mind) {
                    mind = d[i] ;
                    p = i ;
                }
            // lp = ap union union ...
            l[p] = a[p] ;
            for (int i = 0 ; i<n ; i++)
                if (((e[p] >> i) & 1) == 1)
                    l[p] |= l[i] ;
            // ... minus fat p
            l[p] &= ~ ii[p] ;
            // for each fat i in lp do...
            long alli = 0 ;
            for (int i = 0 ; i<n ; i++)
                if (((l[p] >> i) & 1) == 1)
                    alli |= ii[i] ;
            for (int i = 0 ; i<n ; i++)
                if (((alli >> i) & 1) == 1) {
                    // remove redundant entries
                    a[i] &= ~ (l[p] | ii[p]) ;
                    // element absorption
                    e[i] = (e[i] & (~ e[p])) | pow2[p] ;
                    // compute external degree
                    long allle = 0 ;
                    for (int j = 0 ; j<n ; j++)
                        if (((e[i] >> j) & 1) == 1) {
                            allle |= l[j] ;
                        }
                    d[i] = sz(a[i] & ~ ii[i]) + sz (allle & ~ ii[i]) ;
                }
            // supervariable detection
            // for each pair in lp do...
            for (int i = 0 ; i< n ; i++)
                if (((l[p]>>i)&1)==1)
                    for (int j=i+1 ; j<n ; j++)
                        if (((l[p]>>j)&1)==1)
                            if ((a[i]|e[i]|pow2[i]) == (a[j]|e[j]|pow2[j])) { //indistinguishable

                            // remove the supervariable j
                            ii[i] |= ii[j] ;
                            d[i] -= sz(ii[j]) ;
                            v &= ~ pow2[j] ;
                            a[j] = 0 ;
                            e[j] = 0 ;
                        }
            // convert variable p to element p
            //vbar = (vbar | pow2[p]) & ~ e[p] ;
            v &= ~ ii[p] ;
            a[p] = 0 ;
            e[p] = 0 ;
            // k += sz(ii[p]) ;
            // put them to output
            for (int i=0 ; i<n ; i++)
                if (((ii[p]>>i)&1)==1){
                    r[k++] = i ;
                    d[i] = Integer.MAX_VALUE ;
                }
        }


        return r;
    }
}