import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created on 2023-03-24
 * Project 2023JavaStudy
 */
public class Chapter_02_DataStructure {


    @Test
    public void cloneArray(){
        int[] origin = {1,2,6,1,3,3,4,5,1,23};
        int[] target = new int [origin.length];

        for ( int i = 0; i < origin.length; i++ ) {
            target[i] = origin[i];
        }
    }

    @Test
    public void greatestValue(){
        int[] array = {172, 153, 192, 140, 165};

        int max = array[0];
        for ( int piece : array ){
            if(max < piece) max = piece;
        }

        System.out.println("MAX : "+ max);
    }

    @Test
    public void reverseArray(){
        int[] array = {22,57,11,32,91,68,70};
//        int[] reverse = new int[array.length];

       for(int i = 0; i < array.length / 2; i++){
           int temp = array[i];
           array[i] = array[array.length - (1 + i)];
           array[array.length - (1 + i)] = temp;
       }

        System.out.println("array : " + Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.joining(",")));
//        System.out.println("reverse : " + Arrays.stream(reverse).mapToObj(String::valueOf).collect(Collectors.joining(",")));
    }

    @Test
    public void sumOf(){
        int[] array = {1,5,2,3,1,6,2,0};
        int sum = 0;
        for ( int piece : array ){
            sum += piece;
        }

        System.out.println(sum);
    }
//ARRAY
    @Test
    public void sameArray(){
        int[] a = {1,5,2,3,4,1,2};
        int[] b = {1,5,2,3,4,1,2};
        System.out.println(isSame(a,b));
    }
    Boolean isSame(int[] a, int[] b){
        if(a.length != b.length) return false;
        for(int i = 0; i < a .length; i++){
            if(a[i] != b[i]) return false;
        }

        return true;
    }

    @Test
    void reverseCopy(){
        int[] a = {5,2,3,1,9,2,0};
        int[] b = new int[a.length];

        for( int i = (a.length - 1); i >= 0; i-- ){
            b[b.length -  ( i + 1 )] = a[i];
        }
        System.out.println(Arrays.stream(b).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
    }

    @Test
    void carConvRev () {
        int target = 59;
        String dChar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 2; i <= 36; i++){
            String result = "";
            int temp = target;
            do {
            result =dChar.charAt( temp % i )+result;
             temp /= i;
            } while (temp > 0);

            System.out.println(i+" :: "+ result);
        }
    }

    @Test
    void primeNumber() {
        long divCount = 0;
        for (int n = 2; n <= 1000; n++){
            int count = 0;
            for(int j = 2; j <= 1000; j++){
                if(n!=j && n>j && n % j == 0){
                    count ++;
                    divCount++;
                    break;
                }
            }

            if(count == 0){
                System.out.println(n);
            }
        }
        System.out.println("DIVCOUNT : "+ divCount);
    }

    @Test
    void int2DArray() {
        int[][] x = new int[2][4];
        x[0][1] = 37;
        x[0][3] = 54;
        x[1][2] = 65;

        for (int i = 0; i < x.length; i++){
            for (int j = 0; j < x[i].length; j++){
                System.out.println("("+i+", "+j+") = "+x[i][j]);
            }
        }
    }

    @Test
    void dayOfYear () {
        int[][] mDays = {
            {31,28,31,30,31,30,31,31,30,31,30,31},
            {31,29,31,30,31,30,31,31,30,31,30,31}
        };
        int year = 2031;
        int month = 3;
        int day = 15;
        int total = 0;
        int isLeap = 0;

        if(year % 4 == 0 && (year & 100) != 0 || year % 400 == 0) isLeap = 1;

        for( int i = 0; i < month - 1; i++){
            total += mDays[isLeap][i];
        }
        total += day;

        System.out.println(total);
        System.out.println(( isLeap == 1? 366 : 365 ) - total);
    }

    @Test
    void arraySumForIn () {
        double[] a = { 1.0, 2.0, 3.0, 4.0, 5.0 };
        double total = 0.0;
        for (int i = 0; i < a.length; i++){
            System.out.println(String.format("a[%d] = %.1f", i, a[i]));
            total += a[i];
        }
        System.out.println(total);
    }
// CLASS

    class PhyscData {
        String name;
        int height;
        double vision;

        public PhyscData(String name, int height, double vision) {
            this.name = name;
            this.height = height;
            this.vision = vision;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public double getVision() {
            return vision;
        }

        public void setVision(double vision) {
            this.vision = vision;
        }
    }
    @Test
    public void avgHeight(){
        PhyscData[] array = {new PhyscData("Kim", 170, 1.5), new PhyscData("Lee", 180, 2.0), new PhyscData("Jay", 190, 0.8)};
        Double total = 0.0;
        for ( PhyscData piece : array ){
            total += piece.getHeight()*1.0;
        }
        System.out.println(String.format("TOTAL : %.1f, AVG : %.1f", total, total / array.length));
    }

    @Test
    public void visionDistribution(){
        PhyscData[] array = {
                new PhyscData("Park", 162, 0.3),
                new PhyscData("Ham", 173, 0.7),
                new PhyscData("Choi", 175, 2.0),
                new PhyscData("Hong", 171, 1.5),
                new PhyscData("Lee", 168, 0.4),
                new PhyscData("Kim", 174, 1.2),
                new PhyscData("Jang", 169, 0.8)
        };

        int total = 0;
        System.out.println(String.format("%-8s%3s%5s", "이름","키","시력"));
        for (PhyscData piece : array){
            System.out.println(String.format("%-8s%5d%5.1f", piece.getName(), piece.getHeight(), piece.getVision()));
            total += piece.getHeight();
        }

        System.out.println("AVG Height : "+Math.floor(((total*1.0)/array.length)*10)/10);
    }

}
