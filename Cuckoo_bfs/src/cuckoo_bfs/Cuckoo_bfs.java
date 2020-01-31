/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuckoo_bfs;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/**
 *
 * @author srcoem
 */
public class Cuckoo_bfs {

  final static int MAXN = 10;   
    final static int ver = 2; 
    static long timeElapsed;
    static int [][]hashtable = new int[ver][MAXN]; 
    static int [][]matrix = new int[MAXN][MAXN]; 
    static Instant start ,finish;
    static ArrayList<Integer> al1=new ArrayList<>();
    static ArrayList<Integer> al2=new ArrayList<>();
    static int []pos = new int[ver]; 
    
static void initTable() 
{ 
    for (int j = 0; j < MAXN; j++) 
        for (int i = 0; i < ver; i++) 
            hashtable[i][j] = Integer.MIN_VALUE; 
}

static int hash(int function, int key) 
{ 
    switch (function) 
    { 
        case 1: return key % 5; 
        case 2: return key % 7; 
    } 
    return Integer.MIN_VALUE; 
} 
  
static void place(int key, int tableID, int cnt, int n) 
{ 
    for (int i = 0; i < ver; i++) 
    { 
        pos[i] = hash(i + 1, key); 
    } 
    if (hashtable[tableID][pos[tableID]] != Integer.MIN_VALUE) 
    { 
        int dis = hashtable[tableID][pos[tableID]]; 
        hashtable[tableID][pos[tableID]] = key; 
        place(dis, (tableID + 1) % ver, cnt + 1, n); 
        al1.add(pos[tableID]);       
    } 
    else {
    hashtable[tableID][pos[tableID]] = key; 
    if(tableID==1){
       al2.add(pos[tableID]);
    }   
 }
    for(int i=0;i<al1.size();i++)
            matrix[al1.get(i)][al2.get(i)]=1;
} 
  
/* function to print hash table contents */
static void printTable() 
{ 
    System.out.printf("Final hash tables:\n"); 
    for (int i = 0; i < ver; i++) {
        System.out.println("TABLE "+(i+1));
        for (int j = 0; j < MAXN; j++) 
            if(hashtable[i][j] == Integer.MIN_VALUE)  
                System.out.printf(" -"+'\n'); 
            else
                System.out.printf(" "+hashtable[i][j]+'\n'); 
    }
            
    System.out.printf("\n"); 
} 
static void cuckoo(int keys[], int n) 
{ 
    initTable(); 
    for (int i = 0, cnt = 0; i < n; i++, cnt = 0) 
        place(keys[i], 0, cnt, n); 
    printTable(); 
} 
public static void main(String[] args)  
{ 
    int keys_1[] = {15, 21, 34, 23, 65,  
                    78, 19, 42, 74, 92}; 
    int n = keys_1.length; 
    cuckoo(keys_1, n); 
    System.out.println("Adjacency Matrix");
    for(int i=0;i<MAXN;i++){
        for(int j=0;j<MAXN;j++){
            System.out.print(matrix[i][j]);
        }
       System.out.println();      
    }
    System.out.println("Search key :");
    Scanner sc=new Scanner(System.in);
    int val=sc.nextInt();
    start = Instant.now();
    for(int j=0;j<keys_1.length;j++)
      breadthFirstSearch(matrix,j,val);
    System.out.println("Search Duration with Breadth First Search "+timeElapsed+" nanoseconds"+'\n');
    boolean status=false;
    start = Instant.now();
    for (int i = 0; i < ver; i++) {
        for (int j = 0; j < MAXN; j++) 
            if(hashtable[i][j] == val) { 
                System.out.println("Element found at table "+(i+1)+" position at "+(j+1)); 
                status=true;
            }    
    }
    if(!status)
        System.out.println("Search unsuccessful");
    finish = Instant.now();
    timeElapsed = Duration.between(start, finish).toNanos();
    System.out.println("Search Duration of linear search "+timeElapsed+" nanoseconds");
}
static void breadthFirstSearch(int[][] matrix, int source,int value){
        boolean[] visited = new boolean[matrix.length];
        visited[source] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        while(!queue.isEmpty()){
            int x = queue.poll();
            if(x==value)
                break;
            int i;
            for(i=0; i<matrix.length;i++){
                if(matrix[x][i] == 1 && visited[i] == false){
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
      finish = Instant.now();
      timeElapsed = Duration.between(start, finish).toNanos();
    }
}


/*OUTPUT
Final hash tables:
TABLE 1
 65
 21
 92
 78
 74
 -
 -
 -
 -
 -
TABLE 2
 42
 15
 23
 -
 -
 19
 34
 -
 -
 -

Adjacency Matrix
0100000000
0000000000
1000000000
0010000000
0000011000
0000000000
0000000000
0000000000
0000000000
0000000000
Search key :
42
Search Duration with Breadth First Search 68000000 nanoseconds

Element found at table 2 position at 1
Search Duration of linear search 2000000 nanoseconds
BUILD SUCCESSFUL (total time: 8 seconds)

*/
