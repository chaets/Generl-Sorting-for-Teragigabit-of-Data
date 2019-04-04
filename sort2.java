import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.Files;
// package com.jeejava.file;

import java.util.Comparator;

public class sort2 
{
    static int fileCounter2=200;

    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */
    int partition(int arr[], int low, int high)
    {
        int pivot = arr[high]; 
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot)
            {
                i++;
 
                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
 
        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
 
        return i+1;
    }
 
 
    /* The main function that implements sort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    void sort(int arr[], int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is 
              now at right place */
            int pi = partition(arr, low, high);
 
            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }
 
    /* A utility function to print array of size n */
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }

    public static List<File> splitAndSortTempFiles(final String fileName, final String tempDirectory,
        final int noOfSplits, final StringComparator cmp) throws IOException
    {

        
        List<File> files = new ArrayList<>();
        // .....your program....
        
        
        RandomAccessFile raf = new RandomAccessFile("/input/data-2GB.in", "r");
        long numSplits = 20; //from user input, extract it from args
        long sourceSize = raf.length();
        // System.out.println(raf.length());
        long bytesPerSplit = sourceSize/numSplits ;
        long remainingBytes = sourceSize % numSplits;

        int maxReadBufferSize = 10000; // buffer size
        int fileCounter = 1;
        for(int destIx=1; destIx <= numSplits; destIx++) 
        {
            File file = new File("/tmp/tempsplit."+fileCounter);
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(file)); 
            // file create for ater splitting
            // BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("/home/chetan/cloud/pa2/64/tempsplit."+destIx));
            if(bytesPerSplit > maxReadBufferSize) 
            {
                // checking if file sze is buffer size is greater than max read buffer size
                long numReads = bytesPerSplit/maxReadBufferSize;
                long numRemainingRead = bytesPerSplit % maxReadBufferSize;
                for(int i=0; i<numReads; i++) {
                    readWrite(raf, bw, maxReadBufferSize);
                }
                if(numRemainingRead > 0) {
                    readWrite(raf, bw, numRemainingRead);
                }
            }
            else 
            {
                readWrite(raf, bw, bytesPerSplit);
            }
            double sizefilein = file.length();
            // System.out.println(sizefilein);
            
            file = sortFileContent(file, cmp);
            
            

            files.add(file);
            // System.out.println(fileCounter);
            // System.out.println(file);
            fileCounter++;



            bw.close();
        }
        if(remainingBytes > 0) 
        {
            File file = new File("/tmp/tempsplit."+fileCounter);
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(file));
            // BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("/home/chetan/cloud/pa2/64/tempsplit."+(numSplits+1)));
            readWrite(raf, bw, remainingBytes);
            file = sortFileContent(file, cmp);
            files.add(file);

            bw.close();
        }
            raf.close();
            // long endTime   = System.currentTimeMillis();
            // long totalTime = endTime - startTime;
            // System.out.println(totalTime);
            return files;
    }

    static void readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) throws IOException 
    {
        byte[] buf = new byte[(int) numBytes];
        int val = raf.read(buf);
        if(val != -1) {
            bw.write(buf);

        }
    }

    private static File sortFileContent(File file, StringComparator cmp) throws IOException 
    {
        // File fin = new File(file);
        double sizefilein = file.length();
        // System.out.println(sizefilein);
        File fout = new File("/tmp/tempsplit."+fileCounter2);
 
 
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(fout);
 
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
 
        String aLine;
        ArrayList<String> al = new ArrayList<String> ();
 
 
        int i = 0;
        while ((aLine = in.readLine()) != null) {
            //get the lines you want, here I don't want something starting with - or empty
            // if (!aLine.trim().startsWith("-") && aLine.trim().length() > 0) {
                al.add(aLine);
                i++;
            // }
        }
 
        Collections.sort(al);
        //output sorted content to a file
        for (String s : al) {
            // System.out.println(s);
            out.write(s);
            // out.write("\r\n");
            out.newLine();
            // // out.write("-----------------------------------------");
            // out.newLine();
        }
 
        in.close();
        out.close();
        fileCounter2++;
        double sizefile = fout.length();
        // System.out.println(sizefile);

        // String inputFile = file;
        // String outputFile = file;

        // FileReader fileReader = new FileReader(file);
        // BufferedReader bufferedReader = new BufferedReader(fileReader);
        // String inputLine;
        // List<String> lineList = new ArrayList<String>();
        // while ((inputLine = bufferedReader.readLine()) != null) {
        //     lineList.add(inputLine);
        // }
        // fileReader.close();

        // Collections.sort(lineList);

        // FileWriter fileWriter = new FileWriter("/home/chetan/cloud/pa2/64/tempsplit."+fileCounter2);
        // PrintWriter out = new PrintWriter(fileWriter);
        // for (String outputLine : lineList) {
        //     out.println(outputLine);
        // }
        // out.flush();
        // out.close();
        // fileWriter.close();







        // List<String> lines = new ArrayList<>();
        // try (Stream<String> ln = Files.lines(file.toPath())) {
        //     lines = ln.collect(Collectors.toList());
        // }

        // Collections.sort(lines, cmp);

        // try (BufferedWriter bw = Files.newBufferedWriter(file.toPath())) 
        // {
        //     for (String line : lines) 
        //     {
        //         bw.write(line);
        //         bw.write("\r\n");
        //     }
        // }
        return fout;
    }

    public static void mergeSortedFiles(final List<File> files, final String outputFile, final StringComparator cmp)
        throws IOException 
        {

                List<BufferedReader> brReaders = new ArrayList<>();
                TreeMap<String, BufferedReader> map = new TreeMap<>(cmp);

                File f = new File(outputFile);
                if (f.exists()) {
                    f.delete();
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true));

                try 
                    {
                        for (File file : files) {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            brReaders.add(br);
                            String line = br.readLine();
                            map.put(line, br);
                        }
                        while (!map.isEmpty()) 
                        {
                            Map.Entry<String, BufferedReader> nextToGo = map.pollFirstEntry();

                            bw.write(nextToGo.getKey());
                            bw.write("\r\n");

                            String line = nextToGo.getValue().readLine();
                            if (line != null) {
                                map.put(line, nextToGo.getValue());
                            }
                        }
                    } 
                finally 
                    {
                        if (brReaders != null) 
                        {
                        for (BufferedReader br : brReaders) {
                            br.close();
                        }

                        File dir = files.get(0).getParentFile();
                        for (File file : files) {
                            // file.delete();
                        }
                        // if (dir.exists()) {
                        //     dir.delete();
                        // }

                        }
                    if (bw != null) {
                        bw.close();
                    }
                }

    }




    public static void main(String[] args) throws Exception
    {
        Path fullPath = new File("/input/data-2GB.in").toPath();
        long startTime = System.currentTimeMillis();    
        List<File> files = splitAndSortTempFiles(fullPath.toAbsolutePath().toString(), "/tmp/", 25,
                new StringComparator());
        mergeSortedFiles(files, "/tmp/tfinal", new StringComparator());
        long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("total time take to sort 20 GB is "+totalTime);

    
    }   

 
    // Driver program
    
}




// public static void main(String args[]) throws IOException
//     {
//         // int arr[] = {10, 7, 8, 9, 1, 5};
//         // int n = arr.length;
 
//         // sort ob = new sort();
        
 
//         // System.out.println("sorted array");
//         // printArray(arr);



//         BufferedReader in = new BufferedReader(new FileReader("/home/chetan/cloud/pa2/64/temp"));
//         String str;
//         // while(in.readLine()!=null){
//         //     System.out.println(in.readLine());
//         // }

//         ArrayList<String> list1 = new ArrayList<String>();
//         while((str = in.readLine()) != null){
//             list1.add(str);
//         }

//         String[] stringArr = list1.toArray(new String[0]);
//         Collections.sort(list1);
//         // printArray(list);
//         for(int i=0; i<list1.size();i++){
//             // System.out.println(list1.get(i));
//         }
//         // int n = list.length;
//         // ob.sort(list, 0, n-1);
//         FileWriter writer = new FileWriter("/home/chetan/cloud/pa2/64/tempsort1"); 
//         // for(String str1: list1) {
//         //   writer.write(str1);
//         // }
//         for(int i=0; i<list1.size();i++){
//             System.out.println(list1.get(i));
//             writer.write(list1.get(i));
//         }
//         writer.close();





//     }