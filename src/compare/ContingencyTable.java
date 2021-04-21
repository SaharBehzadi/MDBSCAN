/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compare;

/**
 *
 * @author claudia
 */
public class ContingencyTable {
    int rowAtt;
    int colAtt;
    int[][] values;
    int[] rowSum;
    int[] colSum;
   

    public ContingencyTable(int id_Att1, int id_Att2, int[][] values) {
        this.rowAtt = id_Att1;
        this.colAtt = id_Att2;
        this.values = values;
        rowSum = new int[values.length];
        colSum = new int[values[0].length];
        for(int i = 0; i < rowSum.length; i++){
            for(int j = 0; j < colSum.length; j++)
                rowSum[i]+= values[i][j];
        }
         for(int i = 0; i < colSum.length; i++){
            for(int j = 0; j < rowSum.length; j++)
                colSum[i]+= values[j][i];
        }
    }

}
